package net.minecraft;

import net.minecraft.core.Direction;

// Direction
public interface class_2350 {

    Direction field_11043 = Direction.NORTH;
    Direction field_11035 = Direction.SOUTH;
    Direction field_11034 = Direction.EAST;
    Direction field_11039 = Direction.WEST;
    Direction field_11036 = Direction.UP;
    Direction field_11033 = Direction.DOWN;

    default Direction asDirection() {
        return (Direction) (Object) this;
    }

    default Direction method_10153() {
        return asDirection().getOpposite();
    }
}
