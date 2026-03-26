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
        // Attach a dynamic agent to the running JVM
        Instrumentation inst = ByteBuddyAgent.install();

        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {

                // mod class only
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

//                ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES) {
//                    @Override
//                    protected String getCommonSuperClass(String type1, String type2) {
//                        return "java/lang/Object";
//                    }
//                };

                ClassVisitor cv = new ClassVisitor(Opcodes.ASM9, cw) {

//                    @Override
//                    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
//                        // class_2246 -> Blocks
//                        if (interfaces != null) {
//                            for (int i = 0; i < interfaces.length; i++) {
//                                if (interfaces[i].equals("net/minecraft/class_2246")) {
//                                    interfaces[i] = "net/minecraft/world/level/block/Blocks";
//                                }
//                            }
//                        }
//                        super.visit(version, access, name, signature, superName, interfaces);
//                    }

                    @Override
                    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                        return new MethodFallbackVisitor(Opcodes.ASM9, mv, className);
                    }
                };

                cr.accept(cv, 0);
                return cw.toByteArray(); // return the modified class bytes
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