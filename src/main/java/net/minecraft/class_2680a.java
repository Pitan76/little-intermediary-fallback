package net.minecraft;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;

// BlockState
public class class_2680a extends BlockState implements class_2680 {

    public class_2680a(Block owner, Property<?>[] propertyKeys, Comparable<?>[] propertyValues) {
        super(owner, propertyKeys, propertyValues);
    }

    public Block method_26204() {
        return super.getBlock();
    }

    public FluidState method_26227() {
        return super.getFluidState();
    }

    public <T extends Comparable<T>> T method_11654(Property<T> property) {
        return super.getValue(property);
    }

    public <T extends Comparable<T>> T method_11654(class_2769<T> property) {
        return super.getValue((Property<T>) (Object) property);
    }

    public <T extends Comparable<T>, V extends T> BlockState method_11657(Property<T> property, V value) {
        return super.setValue(property, value);
    }

    public <T extends Comparable<T>, V extends T> BlockState method_11657(class_2769<T> property, V value) {
        return super.setValue((Property<T>) (Object) property, value);
    }

    public <T extends Comparable<T>> boolean method_28498(Property<T> property) {
        return super.hasProperty(property);
    }

    public <T extends Comparable<T>> boolean method_28498(class_2769<T> property) {
        return super.hasProperty((Property<T>) (Object) property);
    }
}
