package net.pitan76.littleobffallback.transformer;

import net.fabricmc.loader.api.MappingResolver;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class MethodFallbackVisitor extends MethodVisitor {
    protected final String className;
    private final MappingResolver mappingResolver = net.fabricmc.loader.api.FabricLoader.getInstance().getMappingResolver();

    public MethodFallbackVisitor(int api, MethodVisitor methodVisitor, String className) {
        super(api, methodVisitor);
        this.className = className;
    }

    // 型名や記述子のリマップ
    private String remapType(String type) {
        if (type == null) return null;
        if (type.startsWith("L") && type.endsWith(";")) {
            String internal = type.substring(1, type.length() - 1);
            return "L" + AutoRemap.autoRemap(internal) + ";";
        }
        return AutoRemap.autoRemap(type);
    }

    // Intermediary→Officialの自動リマップ（MappingResolver利用）
    protected String remapClass(String intermediaryName) {
        if (intermediaryName == null) return null;
        // internalName例: net/minecraft/class_2248
        return mappingResolver.mapClassName("intermediary", intermediaryName.replace('.', '/'));
    }

    protected String remapField(String owner, String fieldName, String desc) {
        if (owner == null || fieldName == null) return fieldName;
        return mappingResolver.mapFieldName("intermediary", owner.replace('.', '/'), fieldName, desc);
    }

    protected String remapMethod(String owner, String methodName, String desc) {
        if (owner == null || methodName == null) return methodName;
        return mappingResolver.mapMethodName("intermediary", owner.replace('.', '/'), methodName, desc);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String fieldName, String desc) {
        boolean shouldRemap = owner.startsWith("net/minecraft/");
        String newOwner = shouldRemap ? remapClass(owner) : owner;
        String newFieldName = shouldRemap ? remapField(owner, fieldName, desc) : fieldName;
        String newDesc = shouldRemap ? remapType(desc) : desc;
        boolean changed = !owner.equals(newOwner) || !fieldName.equals(newFieldName) || !desc.equals(newDesc);
        if (changed) {
            System.out.println("[LittleObfFallback] visitFieldInsn: " + owner + "." + fieldName + " : " + desc +
                    " [remap attempted]" +
                    " => " + newOwner + "." + newFieldName + " : " + newDesc);
        }
        super.visitFieldInsn(opcode, newOwner, newFieldName, newDesc);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        boolean isIntermediary = owner.contains("class_") || name.startsWith("method_");
        String newOwner = isIntermediary ? remapClass(owner) : owner;
        String newName = isIntermediary ? remapMethod(owner, name, descriptor) : name;
        String newDesc = isIntermediary ? remapType(descriptor) : descriptor;
        boolean changed = !owner.equals(newOwner) || !name.equals(newName) || !descriptor.equals(newDesc);
        if (changed) {
            System.out.println("[LittleObfFallback] visitMethodInsn: " + owner + "." + name + " : " + descriptor +
                    " [remap attempted]" +
                    " => " + newOwner + "." + newName + " : " + newDesc);
        }
        super.visitMethodInsn(opcode, newOwner, newName, newDesc, isInterface);
    }
}
