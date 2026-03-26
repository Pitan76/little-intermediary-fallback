package net.pitan76.littleobffallback.transformer;

import net.fabricmc.loader.api.MappingResolver;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MethodFallbackVisitor extends MethodVisitor {

    protected String className;

    public MethodFallbackVisitor(int api, MethodVisitor methodVisitor, String className) {
        super(api, methodVisitor);
        this.className = className;
    }

    private String autoRemap(String name) {
        if (name == null) return null;

        // Intermediary -> Official
        if (name.contains("class_1297")) return "net/minecraft/world/entity/Entity";
        if (name.contains("class_1309")) return "net/minecraft/world/entity/LivingEntity";

        if (name.contains("class_2246")) return "net/minecraft/world/level/block/Blocks";
        if (name.contains("class_2248")) return "net/minecraft/world/level/block/Block";
        if (name.contains("class_4970")) return "net/minecraft/world/level/block/state/BlockBehaviour";
        if (name.contains("class_2251")) return "net/minecraft/world/level/block/state/BlockBehaviour$Properties";

        if (name.contains("class_2350")) return "net/minecraft/core/Direction";
        if (name.contains("class_1799")) return "net/minecraft/world/item/ItemStack";

        if (name.equals("field_11043")) return "NORTH";
        if (name.equals("field_11035")) return "SOUTH";
        if (name.equals("field_11034")) return "EAST";
        if (name.equals("field_11039")) return "WEST";
        if (name.equals("field_11036")) return "UP";
        if (name.equals("field_11033")) return "DOWN";

        if (name.contains("field_10124")) return name.replace("field_10124", "AIR");
        if (name.contains("field_10340")) return name.replace("field_10340", "STONE");
        if (name.contains("field_10474")) return name.replace("field_10474", "GRANITE");
        if (name.contains("field_10508")) return name.replace("field_10508", "DIORITE");
        if (name.contains("field_10115")) return name.replace("field_10115", "ANDESITE");
        if (name.contains("field_10219")) return name.replace("field_10219", "GRASS_BLOCK");
        if (name.contains("field_10566")) return name.replace("field_10566", "DIRT");
        if (name.contains("field_10253")) return name.replace("field_10253", "COARSE_DIRT");
        if (name.contains("field_10520")) return name.replace("field_10520", "PODZOL");
        if (name.contains("field_10445")) return name.replace("field_10445", "COBBLESTONE");
        if (name.contains("field_10161")) return name.replace("field_10161", "OAK_PLANKS");
        if (name.contains("field_9975")) return name.replace("field_9975", "SPRUCE_PLANKS");
        if (name.contains("field_10148")) return name.replace("field_10148", "BIRCH_PLANKS");
        if (name.contains("field_10334")) return name.replace("field_10334", "JUNGLE_PLANKS");
        if (name.contains("field_10218")) return name.replace("field_10218", "ACACIA_PLANKS");
        if (name.contains("field_42751")) return name.replace("field_42751", "CHERRY_PLANKS");
        if (name.contains("field_10075")) return name.replace("field_10075", "DARK_OAK_PLANKS");
        if (name.contains("field_54734")) return name.replace("field_54734", "PALE_OAK_WOOD");
        if (name.contains("field_54735")) return name.replace("field_54735", "PALE_OAK_PLANKS");
        if (name.contains("field_37577")) return name.replace("field_37577", "MANGROVE_PLANKS");
        if (name.contains("field_40294")) return name.replace("field_40294", "BAMBOO_PLANKS");
        if (name.contains("field_22126")) return name.replace("field_22126", "CRIMSON_PLANKS");
        if (name.contains("field_22127")) return name.replace("field_22127", "WARPED_PLANKS");
        if (name.contains("field_10033")) return name.replace("field_10033", "GLASS");
        if (name.contains("field_9979")) return name.replace("field_9979", "SANDSTONE");
        if (name.contains("field_10344")) return name.replace("field_10344", "RED_SANDSTONE");
        if (name.contains("field_10104")) return name.replace("field_10104", "BRICKS");
        if (name.contains("field_10056")) return name.replace("field_10056", "STONE_BRICKS");
        if (name.contains("field_10266")) return name.replace("field_10266", "NETHER_BRICKS");
        if (name.contains("field_9986")) return name.replace("field_9986", "RED_NETHER_BRICKS");
        if (name.contains("field_10462")) return name.replace("field_10462", "END_STONE_BRICKS");
        if (name.contains("field_10360")) return name.replace("field_10360", "SMOOTH_STONE");
        if (name.contains("field_10467")) return name.replace("field_10467", "SMOOTH_SANDSTONE");
        if (name.contains("field_10483")) return name.replace("field_10483", "SMOOTH_RED_SANDSTONE");
        if (name.contains("field_9978")) return name.replace("field_9978", "SMOOTH_QUARTZ");
        if (name.contains("field_10490")) return name.replace("field_10490", "YELLOW_WOOL");
        if (name.contains("field_10028")) return name.replace("field_10028", "LIME_WOOL");
        if (name.contains("field_10459")) return name.replace("field_10459", "PINK_WOOL");
        if (name.contains("field_10423")) return name.replace("field_10423", "GRAY_WOOL");
        if (name.contains("field_10222")) return name.replace("field_10222", "LIGHT_GRAY_WOOL");
        if (name.contains("field_10619")) return name.replace("field_10619", "CYAN_WOOL");
        if (name.contains("field_10259")) return name.replace("field_10259", "PURPLE_WOOL");
        if (name.contains("field_10514")) return name.replace("field_10514", "BLUE_WOOL");
        if (name.contains("field_10113")) return name.replace("field_10113", "BROWN_WOOL");
        if (name.contains("field_10170")) return name.replace("field_10170", "GREEN_WOOL");
        if (name.contains("field_10314")) return name.replace("field_10314", "RED_WOOL");
        if (name.contains("field_10146")) return name.replace("field_10146", "BLACK_WOOL");
        if (name.contains("field_10153")) return name.replace("field_10153", "QUARTZ_BLOCK");
        if (name.contains("field_10286")) return name.replace("field_10286", "PURPUR_BLOCK");

        return name;
    }

    private String remapType(String type) {
        if (type == null) return null;

        String result = autoRemap(type);
        if (type.startsWith("L") && type.endsWith(";")) {
            String internal = type.substring(1, type.length() - 1);
            return "L" + autoRemap(internal) + ";";
        }

        return result;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
//        String newOwner = remapType(owner);
//        String newDescriptor = remapType(descriptor);
//        String newName = autoRemap(name);
//
//        if (!className.startsWith("net/pitan76/") ||
//                className.startsWith("net/pitan76/littleobffallback/") ||
//                className.startsWith("net/pitan76/compatdatapacks76/") ||
//                className.startsWith("net/pitan76/legacyitemmodels/")
//        ) {
//            super.visitMethodInsn(opcode, newOwner, newName, newDescriptor, isInterface);
//            return;
//        }
//
//        // class_2246 -> Blocks
//        if (owner.equals("net/minecraft/class_2246"))
//            owner = "net/minecraft/world/level/block/Blocks";
//
//        // MCPitanLib BlockState.of(class_2248)
//        if (owner.equals("net/pitan76/mcpitanlib/midohra/block/BlockState") && name.equals("of")) {
//            if (descriptor.contains("Lnet/minecraft/class_2248;")) {
//                newDescriptor = descriptor.replace("Lnet/minecraft/class_2248;"
//                        , "Lnet/minecraft/world/level/block/Block;");
//
//                super.visitTypeInsn(Opcodes.CHECKCAST, "net/minecraft/world/level/block/Block");
//            }
//        }
//
//        if (descriptor.contains("Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)") ||
//                descriptor.contains("Lnet/minecraft/class_2251;)")) {
//
//            super.visitMethodInsn(Opcodes.INVOKESTATIC,
//                    "net/pitan76/littleobffallback/LittleObfFallbackPreLaunch",
//                    "unwrapSettings",
//                    "(Ljava/lang/Object;)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;",
//                    false);
//        }
//
//        super.visitMethodInsn(opcode, newOwner, newName, newDescriptor, isInterface);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String fieldName, String desc) {
        boolean isIntermediary = owner.contains("class_") || fieldName.startsWith("field_");
        if (!isIntermediary) {
            super.visitFieldInsn(opcode, owner, fieldName, desc);
            return;
        }

        System.out.println("[LittleObf-ASM] Visiting field instruction: " + owner + "." + fieldName + " : " + desc + " (Opcode: " + opcode + ")");

        String newOwner = remapType(owner);
        String newFieldName = autoRemap(fieldName);
        String newDesc = remapType(desc);

//        if (opcode == Opcodes.GETFIELD) {
//            System.out.println("[LittleObf-ASM] Intercepting field access: " + owner + "." + fieldName + " : " + desc);
//
//            super.visitLdcInsn(fieldName);
//            super.visitLdcInsn(owner);
//            super.visitMethodInsn(Opcodes.INVOKESTATIC, "net/pitan76/littleobffallback/DynamicRemapper", "getFieldValue", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;", false);
//
//            String remappedDesc = remapType(desc);
//            if (remappedDesc.startsWith("L") && remappedDesc.endsWith(";")) {
//                String targetInternalName = remappedDesc.substring(1, remappedDesc.length() - 1);
//                super.visitTypeInsn(Opcodes.CHECKCAST, targetInternalName);
//            } else if (remappedDesc.length() == 1) {
//                unbox(remappedDesc);
//            }
//
//            return;
//        }

        super.visitFieldInsn(opcode, newOwner, newFieldName, newDesc);
    }

    private void unbox(String type) {
        switch (type) {
            case "I": // int
                super.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Integer");
                super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
                break;
            case "Z": // boolean
                super.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Boolean");
                super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z", false);
                break;
            case "J": // long
                super.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Long");
                super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J", false);
                break;
            case "F": // float
                super.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Float");
                super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F", false);
                break;
            case "D": // double
                super.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Double");
                super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D", false);
                break;
        }
    }
}
