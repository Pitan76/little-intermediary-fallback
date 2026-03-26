package net.pitan76.littleobffallback.transformer;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class ClassFallbackVisitor extends ClassVisitor {
    private final String className;
    private int fieldInsnCount = 0;
    private int methodInsnCount = 0;

    public ClassFallbackVisitor(int api, ClassVisitor classVisitor, String className) {
        super(api, classVisitor);
        this.className = className;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        return new MethodFallbackVisitor(api, mv, className) {
            @Override
            public void visitFieldInsn(int opcode, String owner, String fieldName, String desc) {
                fieldInsnCount++;
                super.visitFieldInsn(opcode, owner, fieldName, desc);
            }
            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                methodInsnCount++;
                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
            }
        };
    }

    @Override
    public org.objectweb.asm.FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        // フィールド型もリマップ
        String newDesc = descriptor;
        if (descriptor != null && descriptor.startsWith("Lnet/minecraft/")) {
            // 型記述子からクラス名部分だけ抜き出してリマップ
            String internal = descriptor.substring(1, descriptor.length() - 1);
            String remapped = new MethodFallbackVisitor(api, null, className).remapClass(internal);
            if (remapped != null && !remapped.equals(internal)) {
                newDesc = "L" + remapped + ";";
                System.out.println("[LittleObfFallback] visitField(def): " + name + " : " + descriptor + " => " + newDesc);
            }
        }
        return super.visitField(access, name, newDesc, signature, value);
    }

    @Override
    public void visitEnd() {
        if (fieldInsnCount == 0 && methodInsnCount == 0) {
            System.out.println("[LittleObfFallback] No obf field/method access found in: " + className);
        }
        super.visitEnd();
    }
}
