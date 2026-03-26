package net.minecraft;

import net.minecraft.core.NonNullList;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// NonNullList (DefaultedList)
public class class_2371a extends NonNullList {

    protected class_2371a(List list, @Nullable Object defaultValue) {
        super(list, defaultValue);
    }

    public static NonNullList method_10211() {
        return NonNullList.create(); // of()
    }

    public static NonNullList method_37434(int size) {
        return NonNullList.createWithCapacity(size); // ofSize(int size)
    }

    public static NonNullList method_10213(int size, @Nullable Object defaultValue) {
        return NonNullList.withSize(size, defaultValue); // ofSize(int size, T defaultValue)
    }

    public static NonNullList method_10212(Object defaultValue, Object... elements) {
        return NonNullList.of(defaultValue, elements); // copyOf(T defaultValue, T... elements)
    }
}
