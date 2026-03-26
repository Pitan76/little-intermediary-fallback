package net.minecraft;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

// Item
public class class_1792a extends Item implements class_1935 {

    public class_1792a(Properties properties) {
        super(properties);
    }

    public class_1792a(class_1792.class_1793 properties) {
        super(properties);
    }

    public Component method_63680() {
        return super.getName(getDefaultInstance());
    }

    @Override
    public Item method_8389() {
        return asItem();
    }

    public ItemStack method_7854() {
        return super.getDefaultInstance();
    }

    public int method_7882() {
        return super.getDefaultMaxStackSize();
    }
}
