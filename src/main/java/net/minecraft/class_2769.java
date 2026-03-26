package net.minecraft;

import net.minecraft.world.level.block.state.properties.Property;

public interface class_2769<T extends Comparable<T>> {
    default Property<T> asProperty() {
        return (Property<T>) this;
    }
}
