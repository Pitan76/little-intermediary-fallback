package net.minecraft;

import net.minecraft.core.NonNullList;
import org.jetbrains.annotations.Nullable;

// NonNullList (DefaultedList)
public interface class_2371 {
    static NonNullList method_10211() {
        return NonNullList.create(); // of()
    }

    static NonNullList method_37434(int size) {
        return NonNullList.createWithCapacity(size); // ofSize(int size)
    }

    static NonNullList method_10213(int size, @Nullable Object defaultValue) {
        return NonNullList.withSize(size, defaultValue); // ofSize(int size, T defaultValue)
    }

    static NonNullList method_10212(Object defaultValue, Object... elements) {
        return NonNullList.of(defaultValue, elements); // copyOf(T defaultValue, T... elements)
    }
}
