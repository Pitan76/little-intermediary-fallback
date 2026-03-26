package net.pitan76.littleobffallback.compat;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

// Entity
public interface class_1297_bridge {
    default Entity asEntity() {
        return (Entity) this;
    }

    default EntityType<?> method_5864() {
        return asEntity().getType();
    }

    default float method_36455() {
        return asEntity().getYRot();
    }

    default float method_36454() {
        return asEntity().getXRot();
    }

    default Level getWorld() {
        return asEntity().level();
    }

    default Level getEntityWorld() {
        return asEntity().level();
    }

    default BlockPos method_24515() {
        return asEntity().blockPosition();
    }

    default boolean method_5715() {
        return asEntity().isShiftKeyDown();
    }

    default void method_51502(Level world) {
        asEntity().setLevel(world);
    }

    default void method_5814(double x, double y, double z) {
        asEntity().setPos(x, y, z);
    }

    default Vec3 getPos() {
        return asEntity().position();
    }

    default Vec3 method_18798() {
        return asEntity().getDeltaMovement();
    }

    default void method_18799(Vec3 velocity) {
        asEntity().setDeltaMovement(velocity);
    }

    default void method_18800(double x, double y, double z) {
        asEntity().setDeltaMovement(x, y, z);
    }

    default boolean method_5805() {
        return asEntity().isAlive();
    }
}
