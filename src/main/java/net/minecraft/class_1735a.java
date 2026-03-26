package net.minecraft;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

// Slot
public class class_1735a extends Slot implements class_1735 {
    public class_1735a(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    public void method_53512(ItemStack stack) {
        super.set(stack);
    }

    public ItemStack method_7677() {
        return super.getItem();
    }

    public boolean method_7680(ItemStack stack) {
        return super.mayPlace(stack);
    }
}
