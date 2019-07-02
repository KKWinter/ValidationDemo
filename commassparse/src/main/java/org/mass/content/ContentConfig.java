package org.mass.content;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zhj_7 on 2017/1/4.
 */

public class ContentConfig {
    private final static String FILE = ".com.android.systemui.v2.content.config";

    public static String getContentPath(Context context){
        SharedPreferences spf = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        return spf.getString("CNTP", null);
    }

    public static void setContentPath(Context context, String value){
        SharedPreferences spf = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        spf.edit().putString("CNTP", value).commit();
    }

    public static void clearConfig(Context context){
//    	Log.i("ContentConfig", "data cleared");
        SharedPreferences spf = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        spf.edit().clear().commit();
    }

    public static String getContentMd5(Context context){
        SharedPreferences spf = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        return spf.getString("CNTM", "");
    }

    public static void setContentMd5(Context context, String value){
        SharedPreferences spf = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        spf.edit().putString("CNTM", value).commit();
    }

    public static long getContentLength(Context context){
        SharedPreferences spf = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        return spf.getLong("CNTS", -1L);
    }

    public static void setContentLength(Context context, long value){
        SharedPreferences spf = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        spf.edit().putLong("CNTS", value).commit();
    }

    public static String getContentFileName(Context context){
        SharedPreferences spf = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        return spf.getString("CNTN", null);
    }

    public static void setContentFileName(Context context, String value){
        SharedPreferences spf = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        spf.edit().putString("CNTN", value).commit();
    }
}
