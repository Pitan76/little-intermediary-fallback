package net.pitan76.littleobffallback.transformer;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

public class ClassFallbackVisitor extends ClassVisitor {
    private final String className;
    private boolean skipOutput = false;

    public ClassFallbackVisitor(int api, ClassVisitor classVisitor, String className) {
        super(api, classVisitor);
        this.className = className;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        String newName = (name != null && name.startsWith("net/minecraft/class_")) ? AutoRemap.autoRemap(name) : name;
        // 既にOfficial名で存在するクラスは完全にスキップ
        if (!newName.equals(name) && classExists(newName)) {
            System.out.println("[LittleObfFallback] Skipping remap and output: " + name + " -> " + newName + " (Official already exists)");
            skipOutput = true;
            return;
        }
        String newSuper = (superName != null && superName.startsWith("net/minecraft/class_")) ? AutoRemap.autoRemap(superName) : superName;
        String[] newInterfaces = interfaces;
        if (interfaces != null) {
            newInterfaces = new String[interfaces.length];
            for (int i = 0; i < interfaces.length; i++) {
                newInterfaces[i] = (interfaces[i] != null && interfaces[i].startsWith("net/minecraft/class_")) ? AutoRemap.autoRemap(interfaces[i]) : interfaces[i];
            }
        }
        if (!java.util.Objects.equals(name, newName) || !java.util.Objects.equals(superName, newSuper) || (interfaces != null && !java.util.Arrays.equals(interfaces, newInterfaces))) {
            System.out.println("[LittleObfFallback] visit(class): " + name + " -> " + newName + ", super: " + superName + " -> " + newSuper + ", interfaces: " + java.util.Arrays.toString(interfaces) + " -> " + java.util.Arrays.toString(newInterfaces));
        }
        super.visit(version, access, newName, signature, newSuper, newInterfaces);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        if (skipOutput) return null;
        String newDesc = descriptor;
        String newSig = signature;
        if (descriptor != null && (descriptor.contains("class_") || descriptor.contains("field_") || descriptor.contains("method_"))) {
            newDesc = remapType(descriptor);
        }
        if (signature != null && signature.contains("class_")) {
            newSig = remapType(signature);
        }
        if (!java.util.Objects.equals(descriptor, newDesc) || !java.util.Objects.equals(signature, newSig)) {
            System.out.println("[LittleObfFallback] visitField(def): " + name + " : " + descriptor + ", " + signature + " => " + newDesc + ", " + newSig);
        }
        return super.visitField(access, name, newDesc, newSig, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (skipOutput) return null;
        String newDesc = descriptor;
        String newSig = signature;
        if (descriptor != null && descriptor.contains("class_")) {
            newDesc = remapType(descriptor);
        }
        if (signature != null && signature.contains("class_")) {
            newSig = remapType(signature);
        }
        if (!java.util.Objects.equals(descriptor, newDesc) || !java.util.Objects.equals(signature, newSig)) {
            System.out.println("[LittleObfFallback] visitMethod(def): " + name + " : " + descriptor + ", " + signature + " => " + newDesc + ", " + newSig);
        }
        MethodVisitor mv = super.visitMethod(access, name, newDesc, newSig, exceptions);
        return new MethodFallbackVisitor(api, mv, className);
    }

    private boolean classExists(String internalName) {
        try {
            String dotted = internalName.replace('/', '.');
            Class.forName(dotted, false, getClass().getClassLoader());
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    private String remapType(String type) {
        if (type == null) return null;
        if (type.startsWith("[")) {
            return "[" + remapType(type.substring(1));
        }
        if (type.startsWith("L") && type.endsWith(";")) {
            String internal = type.substring(1, type.length() - 1);
            if (internal.startsWith("net/minecraft/class_")) {
                String mapped = AutoRemap.autoRemap(internal);
                if (mapped != null && !mapped.equals(internal)) {
                    return "L" + mapped + ";";
                }
            }
            return "L" + internal + ";";
        }
        return type;
    }
}
