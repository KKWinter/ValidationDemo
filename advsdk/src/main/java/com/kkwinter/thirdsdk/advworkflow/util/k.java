package com.kkwinter.thirdsdk.advworkflow.util;


import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * JSONObject 解析类
 */
public final class k {
    public static boolean a = false;

    public static int a(JSONObject var0, String var1, int var2) {
        return ainternal(var0, var1, var2);
    }

    private static int ainternal(JSONObject var0, String var1, int var2) {
        if (var0 != null && !TextUtils.isEmpty(var1)) {
            int var3;
            try {
                var3 = var0.getInt(var1);
            } catch (JSONException var4) {
                if (!a) {
                    return var2;
                }

                var4.printStackTrace();
                return var2;
            }

            return var3;
        } else {
            return var2;
        }
    }

    public static String a(JSONObject var0, String var1, String var2) {
        if (var0 != null && !TextUtils.isEmpty(var1)) {
            try {
                String var4 = var0.getString(var1);
                return var4;
            } catch (JSONException var3) {
                if (a) {
                    var3.printStackTrace();
                    return var2;
                }
            }
        }

        return var2;
    }

    public static JSONArray a(JSONObject var0, String var1, JSONArray var2) {
        if (var0 != null && !TextUtils.isEmpty(var1)) {
            try {
                JSONArray var4 = var0.getJSONArray(var1);
                return var4;
            } catch (JSONException var3) {
                if (a) {
                    var3.printStackTrace();
                    return var2;
                }
            }
        }

        return var2;
    }

    public static JSONObject a(JSONObject var0, String var1, JSONObject var2) {
        if (var0 != null && !TextUtils.isEmpty(var1)) {
            try {
                var0 = var0.getJSONObject(var1);
                return var0;
            } catch (JSONException var3) {
                if (a) {
                    var3.printStackTrace();
                    return var2;
                }
            }
        }

        return var2;
    }
}

