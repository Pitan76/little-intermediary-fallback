package net.minecraft;

import net.minecraft.core.Vec3i;

// Vec3i
public interface class_2382 {
    default Vec3i asVec3i() {
        return (Vec3i) (Object) this;
    }

    default int method_10263() {
        return asVec3i().getX();
    }

    default int method_10264() {
        return asVec3i().getY();
    }

    default int method_10260() {
        return asVec3i().getZ();
    }
}
