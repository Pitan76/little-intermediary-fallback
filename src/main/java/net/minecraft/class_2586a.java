package net.minecraft;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class class_2586a extends BlockEntity implements class_2586 {
    public class_2586a(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }

    public BlockEntityType<?> method_11017() {
        return super.getType();
    }

    public @Nullable Level method_10997() {
        return super.getLevel();
    }

    public BlockPos method_11016() {
        return super.getBlockPos();
    }

    public BlockState method_11010() {
        return super.getBlockState();
    }

    public void method_31662(Level world) {
        super.setLevel(world);
    }

    public boolean method_11015() {
        return super.isRemoved();
    }
}
