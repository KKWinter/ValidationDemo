package com.kkwinter.thirdsdk.advsdk.b;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 解析json的工具类
 */
public final class r {
    public static boolean a = false;

    public static String a(JSONObject var0, String var1, String var2) {
        if (var0 != null && !TextUtils.isEmpty(var1)) {
            try {
                return var0.getString(var1);
            } catch (JSONException var3) {
                if (a) {
                    var3.printStackTrace();
                }

                return var2;
            }
        } else {
            return var2;
        }
    }

    public static List<String> a(JSONObject var0, String var1, List<String> var2) {
        if (var0 != null && !TextUtils.isEmpty(var1)) {
            try {
                JSONArray var5;
                if ((var5 = var0.getJSONArray(var1)) == null) {
                    return var2;
                } else {
                    ArrayList var6 = new ArrayList();

                    for(int var3 = 0; var3 < var5.length(); ++var3) {
                        var6.add(var5.getString(var3));
                    }

                    return var6;
                }
            } catch (JSONException var4) {
                if (a) {
                    var4.printStackTrace();
                }

                return var2;
            }
        } else {
            return var2;
        }
    }

    public static JSONObject a(JSONObject var0, String var1) {
        if (var0 != null && !TextUtils.isEmpty(var1)) {
            try {
                return var0.getJSONObject(var1);
            } catch (JSONException var2) {
                if (a) {
                    var2.printStackTrace();
                }

                return null;
            }
        } else {
            return null;
        }
    }
}

