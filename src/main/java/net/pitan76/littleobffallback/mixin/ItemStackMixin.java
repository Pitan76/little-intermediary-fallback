package net.pitan76.littleobffallback.mixin;

import net.minecraft.world.item.ItemStack;
import net.pitan76.littleobffallback.compat.class_1799_bridge;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements class_1799_bridge {

}
