package com.kkwinter.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class PreferenceUtils {

    private static final String PREF_DEFAULT_FILE = "default";

    public static void persistString(Context context, String key, String value) {
        persistString(context, PREF_DEFAULT_FILE, key, value);
    }

    public static void persistString(Context context, String namespace, String key, String value) {
        try {
            SharedPreferences sp = context.getSharedPreferences(namespace, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, value);
            editor.apply();
        } catch (Exception e) {
            WLog.printStackTrace(e);
        }
    }

    public static String getString(Context context, String key) {
        return getString(context, key, "");
    }

    public static String getString(Context context, String key, String defVal) {
        return getString(context, PREF_DEFAULT_FILE, key, defVal);
    }

    public static String getString(Context context, String namespace, String key, String defVal) {
        try {
            SharedPreferences sp = context.getSharedPreferences(namespace, MODE_PRIVATE);
            return sp.getString(key, defVal);
        } catch (Exception e) {
            WLog.printStackTrace(e);
        }
        return defVal;
    }


    //判断某个key是否存在
    public static boolean isExist(Context context, String namespace, String key) {
        try {
            SharedPreferences sp = context.getSharedPreferences(namespace, MODE_PRIVATE);
            return sp.contains(key);
        } catch (Exception e) {
            return false;
        }
    }

    //默认存储long类型
    public static void persistLong(Context context, String key, long value) {
        persistLong(context, PREF_DEFAULT_FILE, key, value);
    }

    public static void persistLong(Context context, String namespace, String key, long value) {
        try {
            SharedPreferences sp = context.getSharedPreferences(namespace, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putLong(key, value);
            editor.apply();
        } catch (Exception e) {
            WLog.printStackTrace(e);
        }
    }


    public static long getLong(Context context, String key) {
        return getLong(context, key, -1);
    }

    public static long getLong(Context context, String key, long defVal) {
        return getLong(context, PREF_DEFAULT_FILE, key, defVal);
    }

    public static long getLong(Context context, String namespace, String key, long defVal) {
        try {
            SharedPreferences sp = context.getSharedPreferences(namespace, MODE_PRIVATE);
            return sp.getLong(key, defVal);
        } catch (Exception e) {
            WLog.printStackTrace(e);
        }
        return defVal;
    }
}
