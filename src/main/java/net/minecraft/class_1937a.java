package net.minecraft;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.jetbrains.annotations.Nullable;

// Level (World)
public abstract class class_1937a extends Level implements class_1937 {

    protected class_1937a(WritableLevelData levelData, ResourceKey<Level> dimension, RegistryAccess registryAccess, Holder<DimensionType> dimensionTypeRegistration, boolean isClientSide, boolean isDebug, long biomeZoomSeed, int maxChainedNeighborUpdates) {
        super(levelData, dimension, registryAccess, dimensionTypeRegistration, isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
    }

    public boolean method_8608() {
        return super.isClientSide();
    }

    public @Nullable BlockEntity method_8321(BlockPos pos) {
        return super.getBlockEntity(pos);
    }

    public BlockState method_8320(BlockPos pos) {
        return super.getBlockState(pos);
    }

    public boolean method_8501(BlockPos pos, BlockState state) {
        return super.setBlock(pos, state, 3);
    }

    public boolean method_8652(BlockPos pos, BlockState state, int flags) {
        return super.setBlock(pos, state, flags);
    }
}
