package net.pitan76.littleobffallback;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.pitan76.littleobffallback.transformer.MethodFallbackVisitor;
import org.objectweb.asm.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class LittleObfFallbackPreLaunch implements PreLaunchEntrypoint {

    @Override
    public void onPreLaunch() {
        // JVMへエージェントをアタッチ
        Instrumentation inst = ByteBuddyAgent.install();

        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
                // modクラスのみ対象、特定パッケージは除外
                if (className == null
                        || !className.startsWith("net/pitan76/")
                        || className.startsWith("net/pitan76/littleobffallback/")
                        || className.startsWith("net/pitan76/compatdatapacks76/")
                        || className.startsWith("net/pitan76/legacyitemmodels/")
                        || className.startsWith("net/pitan76/mcpitanlib/")
                ) {
                    return null;
                }

                ClassReader cr = new ClassReader(classfileBuffer);
                ClassWriter cw = new ClassWriter(cr, 0);
                ClassVisitor cv = new ClassVisitor(Opcodes.ASM9, cw) {
                    @Override
                    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                        return new MethodFallbackVisitor(Opcodes.ASM9, mv, className);
                    }
                };
                cr.accept(cv, 0);
                return cw.toByteArray();
            }
        });

        System.out.println("[LittleObfFallback] Global ASM Transformer injected successfully.");
    }

    public static net.minecraft.world.level.block.state.BlockBehaviour.Properties unwrapSettings(Object obj) {
        // すでに Properties ならそのまま返す
        if (obj instanceof net.minecraft.world.level.block.state.BlockBehaviour.Properties) {
            return (net.minecraft.world.level.block.state.BlockBehaviour.Properties) obj;
        }
        try {
            // CompatibleBlockSettings の .build() をリフレクションで叩いて変換する
            java.lang.reflect.Method m = obj.getClass().getMethod("build");
            return (net.minecraft.world.level.block.state.BlockBehaviour.Properties) m.invoke(obj);
        } catch (Exception e) {
            System.err.println("[LittleObfFallback] Failed to unwrap settings: " + e.getMessage());
            return (net.minecraft.world.level.block.state.BlockBehaviour.Properties) obj;
        }
    }
}