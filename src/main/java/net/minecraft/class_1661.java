package net.minecraft;

import net.minecraft.world.entity.player.Inventory;

// Inventory (PlayerInventory)
public interface class_1661 extends class_1263 {
    default Inventory asInventory() {
        return (Inventory) this;
    }
}
