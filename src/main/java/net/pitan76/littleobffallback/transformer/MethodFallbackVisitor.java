package net.pitan76.littleobffallback.transformer;

import org.objectweb.asm.MethodVisitor;

public class MethodFallbackVisitor extends MethodVisitor {
    protected final String className;

    public MethodFallbackVisitor(int api, MethodVisitor methodVisitor, String className) {
        super(api, methodVisitor);
        this.className = className;
    }

    private String remapType(String type) {
        if (type == null) return null;
        if (type.startsWith("[")) {
            return "[" + remapType(type.substring(1));
        }
        if (type.startsWith("L") && type.endsWith(";")) {
            String internal = type.substring(1, type.length() - 1);
            String mapped = AutoRemap.autoRemap(internal);
            if (mapped != null && !mapped.equals(internal)) {
                return "L" + mapped + ";";
            }
            return "L" + internal + ";";
        }
        return type;
    }

    private String remapClass(String name) {
        if (name == null) return null;
        return AutoRemap.autoRemap(name);
    }

    private String remapField(String owner, String fieldName) {
        if (fieldName == null) return null;
        return AutoRemap.autoRemap(fieldName);
    }

    private String remapMethod(String owner, String methodName) {
        if (methodName == null) return null;
        return AutoRemap.autoRemap(methodName);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String fieldName, String desc) {
        boolean shouldRemap = 
            (owner != null && owner.contains("class_")) ||
            (fieldName != null && fieldName.contains("field_")) ||
            (desc != null && desc.contains("class_"));
        String newOwner = shouldRemap ? remapClass(owner) : owner;
        String newFieldName = shouldRemap ? remapField(owner, fieldName) : fieldName;
        String newDesc = shouldRemap ? remapType(desc) : desc;
        boolean changed = !java.util.Objects.equals(owner, newOwner) || !java.util.Objects.equals(fieldName, newFieldName) || !java.util.Objects.equals(desc, newDesc);
        if (changed) {
            System.out.println("[LittleObfFallback] visitFieldInsn: " + owner + "." + fieldName + " : " + desc +
                    " => " + newOwner + "." + newFieldName + " : " + newDesc);
        }
        super.visitFieldInsn(opcode, newOwner, newFieldName, newDesc);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        boolean shouldRemap = 
            (owner != null && owner.contains("class_")) ||
            (name != null && name.contains("method_")) ||
            (descriptor != null && descriptor.contains("class_"));
        String newOwner = shouldRemap ? remapClass(owner) : owner;
        String newName = shouldRemap ? remapMethod(owner, name) : name;
        String newDesc = shouldRemap ? remapType(descriptor) : descriptor;
        boolean changed = !java.util.Objects.equals(owner, newOwner) || !java.util.Objects.equals(name, newName) || !java.util.Objects.equals(descriptor, newDesc);
        if (changed) {
            System.out.println("[LittleObfFallback] visitMethodInsn: " + owner + "." + name + " : " + descriptor +
                    " => " + newOwner + "." + newName + " : " + newDesc);
        }
        super.visitMethodInsn(opcode, newOwner, newName, newDesc, isInterface);
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        String newType = (type != null && type.contains("class_")) ? remapClass(type) : type;
        if (!java.util.Objects.equals(type, newType)) {
            System.out.println("[LittleObfFallback] visitTypeInsn: " + type + " => " + newType);
        }
        super.visitTypeInsn(opcode, newType);
    }

    @Override
    public void visitLocalVariable(String name, String desc, String signature, org.objectweb.asm.Label start, org.objectweb.asm.Label end, int index) {
        String newDesc = (desc != null && desc.contains("class_")) ? remapType(desc) : desc;
        String newSig = (signature != null && signature.contains("class_")) ? remapType(signature) : signature;
        if (!java.util.Objects.equals(desc, newDesc) || !java.util.Objects.equals(signature, newSig)) {
            System.out.println("[LittleObfFallback] visitLocalVariable: " + name + " : " + desc + ", " + signature + " => " + newDesc + ", " + newSig);
        }
        super.visitLocalVariable(name, newDesc, newSig, start, end, index);
    }
}
