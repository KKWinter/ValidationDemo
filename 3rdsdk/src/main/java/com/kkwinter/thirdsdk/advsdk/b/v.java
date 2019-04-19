package com.kkwinter.thirdsdk.advsdk.b;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * SharedPreferences 存取工具类
 */
public final class v {
    private static final Method a = a();

    public static void a(Context var0, String var1, String var2) {
        SharedPreferences.Editor var5;
        (var5 = PreferenceManager.getDefaultSharedPreferences(var0).edit()).putString(var1, var2);
        if (a != null) {
            try {
                a.invoke(var5);
                return;
            } catch (InvocationTargetException var3) {
                ;
            } catch (IllegalAccessException var4) {
                ;
            }
        }

        var5.commit();
    }

    public static String b(Context var0, String var1, String var2) {
        return PreferenceManager.getDefaultSharedPreferences(var0).getString(var1, var2);
    }

    private static Method a() {
        try {
            return SharedPreferences.Editor.class.getMethod("apply");
        } catch (NoSuchMethodException var0) {
            return null;
        }
    }
}
