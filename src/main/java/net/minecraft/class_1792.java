package net.minecraft;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

// Item
public interface class_1792 {
    default Item asItem() {
        return (Item) this;
    }

    default Component method_63680() {
        return asItem().getName(asItem().getDefaultInstance());
    }

    default ItemStack method_7854() {
        return asItem().getDefaultInstance();
    }

    default int method_7882() {
        return asItem().getDefaultMaxStackSize();
    }

    public static class class_1793 extends Item.Properties {
        public class_1793() {
            super();
        }
    }
}
