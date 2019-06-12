package com.kkwinter.thirdsdk.advworkflow.util;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *  sp存储工具类
 */
public final class p {
    private static final Method a = a();

    //取long
    public static long a(Context var0, String var1) {
        return PreferenceManager.getDefaultSharedPreferences(var0).getLong(var1, 0L);
    }


    //取string
    public static String b(Context var0, String var1, String var2) {
        return PreferenceManager.getDefaultSharedPreferences(var0).getString(var1, var2);
    }


    private static Method a() {
        try {
            Method var0 = Editor.class.getMethod("apply");
            return var0;
        } catch (NoSuchMethodException var1) {
            return null;
        }
    }

    //存log
    public static void a(Context var0, String var1, long var2) {
        Editor var4 = PreferenceManager.getDefaultSharedPreferences(var0).edit();
        var4.putLong(var1, var2);
        a(var4);
    }

    //存string
    public static void a(Context var0, String var1, String var2) {
        Editor var3 = PreferenceManager.getDefaultSharedPreferences(var0).edit();
        var3.putString(var1, var2);
        a(var3);
    }


    private static void a(Editor var0) {
        if (a != null) {
            try {
                a.invoke(var0);
                return;
            } catch (InvocationTargetException var2) {
                ;
            } catch (IllegalAccessException var3) {
                ;
            }
        }

        var0.commit();
    }


}
