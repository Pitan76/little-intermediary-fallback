package net.minecraft;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

// Slot
public interface class_1735 {
    default Slot asSlot() {
        return (Slot) this;
    }

    default void method_53512(ItemStack stack) {
        asSlot().set(stack);
    }

    default ItemStack method_7677() {
        return asSlot().getItem();
    }

    default boolean method_7680(ItemStack stack) {
        return asSlot().mayPlace(stack);
    }
}
