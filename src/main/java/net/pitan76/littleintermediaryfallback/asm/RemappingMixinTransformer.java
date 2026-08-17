package net.pitan76.littleintermediaryfallback.asm;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.IMixinTransformer;
import org.spongepowered.asm.mixin.transformer.ext.IExtensionRegistry;

import java.util.List;

/**
 * Knotが保持しているIMixinTransformerをラップし、Mixin適用後のバイトコードに
 * インターメディアリのフォールバック変換をかける。
 *
 * KnotClassDelegate#getPostMixinClassByteArray がKnot経由で読まれる全クラスについて
 * transformClassBytes を呼ぶため、JVM agentを使わずに同等のカバレッジが得られる。
 */
public class RemappingMixinTransformer implements IMixinTransformer {

    private final IMixinTransformer delegate;
    private final LittleIntermediaryFallbackTransformer2 transformer;
    private final ClassLoader loader;

    public RemappingMixinTransformer(IMixinTransformer delegate, List<String> targetPackages, ClassLoader loader) {
        this.delegate = delegate;
        this.transformer = new LittleIntermediaryFallbackTransformer2(targetPackages);
        this.loader = loader;
    }

    public IMixinTransformer getDelegate() {
        return delegate;
    }

    private byte[] fallback(String name, byte[] bytes) {
        if (name == null || bytes == null) return bytes;

        byte[] remapped = transformer.transform(loader, name.replace('.', '/'), null, null, bytes);
        // 変換が無かった場合はnullが返るので、元のバイトコードをそのまま使う
        return remapped != null ? remapped : bytes;
    }

    @Override
    public byte[] transformClassBytes(String name, String transformedName, byte[] basicClass) {
        return fallback(transformedName != null ? transformedName : name, delegate.transformClassBytes(name, transformedName, basicClass));
    }

    @Override
    public byte[] transformClass(MixinEnvironment environment, String name, byte[] classBytes) {
        return fallback(name, delegate.transformClass(environment, name, classBytes));
    }

    // 以下はKnotのクラスロード経路では使われないため、そのまま委譲する

    @Override
    public void audit(MixinEnvironment environment) {
        delegate.audit(environment);
    }

    @Override
    public List<String> reload(String mixinClass, ClassNode classNode) {
        return delegate.reload(mixinClass, classNode);
    }

    @Override
    public boolean computeFramesForClass(MixinEnvironment environment, String name, ClassNode classNode) {
        return delegate.computeFramesForClass(environment, name, classNode);
    }

    @Override
    public boolean transformClass(MixinEnvironment environment, String name, ClassNode classNode) {
        return delegate.transformClass(environment, name, classNode);
    }

    @Override
    public boolean couldTransformClass(MixinEnvironment environment, String name) {
        return delegate.couldTransformClass(environment, name);
    }

    @Override
    public byte[] generateClass(MixinEnvironment environment, String name) {
        return delegate.generateClass(environment, name);
    }

    @Override
    public boolean generateClass(MixinEnvironment environment, String name, ClassNode classNode) {
        return delegate.generateClass(environment, name, classNode);
    }

    @Override
    public IExtensionRegistry getExtensions() {
        return delegate.getExtensions();
    }
}
