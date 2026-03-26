package net.minecraft;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

// Level (World)
public interface class_1937 {
    default Level asLevel() {
        return (Level) this;
    }

    default boolean method_8608() {
        return asLevel().isClientSide();
    }

    default @Nullable BlockEntity method_8321(BlockPos pos) {
        return asLevel().getBlockEntity(pos);
    }

    default @Nullable BlockEntity method_8321(class_2338 pos) {
        return asLevel().getBlockEntity((BlockPos) (Object) pos);
    }

    default BlockState method_8320(BlockPos pos) {
        return asLevel().getBlockState(pos);
    }

    default BlockState method_8320(class_2338 pos) {
        return asLevel().getBlockState((BlockPos) (Object) pos);
    }

    default boolean method_8501(BlockPos pos, BlockState state) {
        return asLevel().setBlock(pos, state, 3);
    }

    default boolean method_8652(BlockPos pos, BlockState state, int flags) {
        return asLevel().setBlock(pos, state, flags);
    }

    default boolean method_8501(class_2338 pos, class_2680 state) {
        return asLevel().setBlock((BlockPos) (Object) pos, (BlockState) (Object) state, 3);
    }

    default boolean method_8652(class_2338 pos, class_2680 state, int flags) {
        return asLevel().setBlock((BlockPos) (Object) pos, (BlockState) (Object) state, flags);
    }
}
