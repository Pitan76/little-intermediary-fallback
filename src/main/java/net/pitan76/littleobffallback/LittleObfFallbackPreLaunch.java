package net.pitan76.littleobffallback;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.pitan76.littleobffallback.transformer.ClassFallbackVisitor;
import org.objectweb.asm.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class LittleObfFallbackPreLaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        Instrumentation inst = ByteBuddyAgent.install();
        System.out.println("[LittleObfFallback] PreLaunch: Instrumentation attached");
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
                if (className == null || className.startsWith("net/pitan76/littleobffallback/")) {
                    return null;
                }
                System.out.println("[LittleObfFallback] Transforming: " + className);
                try {
                    ClassReader cr = new ClassReader(classfileBuffer);
                    ClassWriter cw = new ClassWriter(cr, 0);
                    ClassVisitor cv = new ClassFallbackVisitor(Opcodes.ASM9, cw, className);
                    cr.accept(cv, 0);
                    return cw.toByteArray();
                } catch (Throwable t) {
                    System.err.println("[LittleObfFallback] Transform failed: " + className + " : " + t);
                    return null;
                }
            }
        });
        System.out.println("[LittleObfFallback] Global ASM Transformer injected successfully.");
    }
}