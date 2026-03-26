package net.minecraft;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;

// BlockState
public interface class_2680 {
    default BlockState asBlockState() {
        return (BlockState) (Object) this;
    }

    default Block method_26204() {
        return asBlockState().getBlock();
    }

    default FluidState method_26227() {
        return asBlockState().getFluidState();
    }

    default <T extends Comparable<T>> T method_11654(Property<T> property) {
        return asBlockState().getValue(property);
    }

    default <T extends Comparable<T>> T method_11654(class_2769<T> property) {
        return asBlockState().getValue((Property<T>) (Object) property);
    }

    default <T extends Comparable<T>, V extends T> BlockState method_11657(Property<T> property, V value) {
        return asBlockState().setValue(property, value);
    }

    default <T extends Comparable<T>, V extends T> BlockState method_11657(class_2769<T> property, V value) {
        return asBlockState().setValue((Property<T>) (Object) property, value);
    }

    default <T extends Comparable<T>> boolean method_28498(Property<T> property) {
        return asBlockState().hasProperty(property);
    }

    default <T extends Comparable<T>> boolean method_28498(class_2769<T> property) {
        return asBlockState().hasProperty((Property<T>) (Object) property);
    }
}
