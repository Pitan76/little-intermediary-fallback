package net.minecraft;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public interface class_1299<T extends Entity> {
    default EntityType<T> asEntityType() {
        return (EntityType<T>) this;
    }
}
