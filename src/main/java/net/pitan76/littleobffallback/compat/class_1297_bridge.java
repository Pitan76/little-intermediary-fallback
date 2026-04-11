package net.pitan76.littleobffallback.compat;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

// Entity
public interface class_1297_bridge {
    default Entity littleobffallback$asEntity() {
        return (Entity) this;
    }

    default Level getWorld() {
        return littleobffallback$asEntity().level();
    }

    default Level getEntityWorld() {
        return littleobffallback$asEntity().level();
    }

    default Vec3 getPos() {
        return littleobffallback$asEntity().position();
    }
}
