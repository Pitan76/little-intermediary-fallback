package net.minecraft;

import net.minecraft.core.BlockPos;

// BlockPos
public interface class_2338 {
    default BlockPos asBlockPos() {
        return (BlockPos) (Object) this;
    }

    default int method_10263() {
        return asBlockPos().getX();
    }

    default int method_10264() {
        return asBlockPos().getY();
    }

    default int method_10260() {
        return asBlockPos().getZ();
    }

    default BlockPos method_30931() {
        return asBlockPos().above();
    }

    default BlockPos method_23228() {
        return asBlockPos().below();
    }

    default BlockPos method_35861() {
        return asBlockPos().north();
    }

    default BlockPos method_35859() {
        return asBlockPos().south();
    }

    default BlockPos method_35855() {
        return asBlockPos().east();
    }

    default BlockPos method_35857() {
        return asBlockPos().west();
    }

    default BlockPos method_35853(class_2382 vec3i) {
        return asBlockPos().offset((net.minecraft.core.Vec3i) (Object) vec3i);
    }

    default BlockPos method_34592(int i, int j, int k) {
        return asBlockPos().offset(i, j, k);
    }

    default BlockPos method_35852(class_2382 vec3i) {
        return asBlockPos().subtract((net.minecraft.core.Vec3i) (Object) vec3i);
    }

    default BlockPos method_35862(int i) {
        return asBlockPos().multiply(i);
    }
}
