package net.pitan76.littleobffallback.compat;

import net.minecraft.core.Direction;

// Direction
public interface class_2350_bridge {
    default Direction asDirection() {
        return (Direction) (Object) this;
    }

    default Direction method_10153() {
        return asDirection().getOpposite();
    }

    static class_2350_bridge[] values() {
        return (class_2350_bridge[]) (Object[]) Direction.values();
    }
}
