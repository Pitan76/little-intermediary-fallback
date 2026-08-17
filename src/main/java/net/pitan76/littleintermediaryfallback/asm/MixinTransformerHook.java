package net.pitan76.littleintermediaryfallback.asm;

import org.spongepowered.asm.mixin.transformer.IMixinTransformer;

import java.lang.reflect.Field;
import java.util.List;

/**
 * KnotClassDelegateが保持しているIMixinTransformerを{@link RemappingMixinTransformer}に差し替える。
 *
 * Knot.init()の順序は
 *   FabricMixinBootstrap.init -> finishMixinBootstrapping -> initializeTransformers -> preLaunchエントリーポイント
 * であり、preLaunchの時点でtransformerは既に生成済みなので、
 * MixinServiceKnotの静的フィールドではなくKnotClassDelegateのインスタンスフィールドを差し替える必要がある。
 */
public final class MixinTransformerHook {

    private MixinTransformerHook() {
    }

    public static boolean install(List<String> targetPackages) {
        try {
            ClassLoader loader = MixinTransformerHook.class.getClassLoader();

            Object delegate = findDelegate(loader);
            if (delegate == null) {
                System.err.println("[LittleIntermediaryFallback] Could not find KnotClassDelegate on " + loader.getClass().getName());
                return false;
            }

            Field field = findFieldOfType(delegate.getClass(), IMixinTransformer.class);
            if (field == null) {
                System.err.println("[LittleIntermediaryFallback] Could not find the IMixinTransformer field on " + delegate.getClass().getName());
                return false;
            }

            field.setAccessible(true);
            IMixinTransformer original = (IMixinTransformer) field.get(delegate);

            if (original == null) {
                System.err.println("[LittleIntermediaryFallback] The mixin transformer is not initialized yet");
                return false;
            }
            if (original instanceof RemappingMixinTransformer) return true; // 既に導入済み

            field.set(delegate, new RemappingMixinTransformer(original, targetPackages, loader));
            return true;
        } catch (Throwable t) {
            System.err.println("[LittleIntermediaryFallback] Failed to hook the mixin transformer");
            t.printStackTrace();
            return false;
        }
    }

    /**
     * KnotClassLoader / KnotCompatibilityClassLoader の delegate フィールドを取得する。
     * フィールド名ではなく型名で探すことで、両方のクラスローダーに対応する。
     */
    private static Object findDelegate(ClassLoader loader) throws IllegalAccessException {
        for (Class<?> c = loader.getClass(); c != null && c != Object.class; c = c.getSuperclass()) {
            for (Field field : c.getDeclaredFields()) {
                if (!field.getType().getName().endsWith("KnotClassDelegate")) continue;

                field.setAccessible(true);
                Object value = field.get(loader);
                if (value != null) return value;
            }
        }
        return null;
    }

    private static Field findFieldOfType(Class<?> owner, Class<?> type) {
        for (Class<?> c = owner; c != null && c != Object.class; c = c.getSuperclass()) {
            for (Field field : c.getDeclaredFields()) {
                if (field.getType() == type) return field;
            }
        }
        return null;
    }
}
