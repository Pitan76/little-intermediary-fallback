package net.minecraft;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public interface class_2591<T extends BlockEntity> {
    default BlockEntityType<T> asBlockEntityType() {
        return (BlockEntityType<T>) this;
    }
}
