package net.pitan76.littleobffallback;

import java.lang.reflect.Field;

public class DynamicRemapper {
    /**
     * 実行時にインスタンスから最適なフィールドを探し出す
     * @param instance イベントオブジェクト (ItemUseOnEntityEvent)
     * @param oldName Modが探している名前 (livingEntity / entity 等)
     */
    public static Object getFieldValue(Object instance, String oldName, String owner) {
        if (instance == null) return null;

        String className = instance.getClass().getName();
        System.out.println("[LittleObf-Debug] Accessing Field: " + oldName + " in " + className + " (Owner: " + owner + ")");

        try {
            Class<?> clazz = instance.getClass();

            try {
                Field f = clazz.getField(oldName);
                return f.get(instance);
            } catch (NoSuchFieldException e) {
                // 名前が違っても諦めない
            }

            for (Field f : clazz.getFields()) {
                String typeName = f.getType().getName();
                if (typeName.contains("LivingEntity") || typeName.contains("class_1309")) {
                    return f.get(instance);
                }
            }
        } catch (Exception e) {
            System.err.println("[LittleObfFallback] Dynamic lookup failed for: " + oldName);
        }
        return null;
    }
}