package net.minecraft;

import net.minecraft.network.chat.MutableComponent;

// MutableComponent (MutableText)
public interface class_5250 extends class_2561 {
    default MutableComponent asMutableComponent() {
        return (MutableComponent) this;
    }
}
