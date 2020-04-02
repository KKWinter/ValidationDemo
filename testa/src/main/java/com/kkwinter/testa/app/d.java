package com.kkwinter.testa.app;

import java.lang.reflect.Field;

public final class d {
    public static Field a(Object var0, String var1) {
        Field var2 = null;
        try {
            (var2 = var0.getClass().getDeclaredField(var1)).setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return var2;
    }
}
