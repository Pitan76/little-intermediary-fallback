package net.minecraft;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public interface class_2586 {
    default BlockEntity asBlockEntity() {
        return (BlockEntity) this;
    }

    default BlockEntityType<?> method_11017() {
        return asBlockEntity().getType();
    }

    default @Nullable Level method_10997() {
        return asBlockEntity().getLevel();
    }

    default BlockPos method_11016() {
        return asBlockEntity().getBlockPos();
    }

    default BlockState method_11010() {
        return asBlockEntity().getBlockState();
    }

    default void method_31662(Level world) {
        asBlockEntity().setLevel(world);
    }

    default boolean method_11015() {
        return asBlockEntity().isRemoved();
    }
}
