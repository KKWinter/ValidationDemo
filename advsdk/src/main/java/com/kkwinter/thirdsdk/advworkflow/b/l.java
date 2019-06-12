package com.kkwinter.thirdsdk.advworkflow.b;


import android.annotation.SuppressLint;
import android.content.Context;

import com.kkwinter.thirdsdk.advworkflow.util.k;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public final class l extends a {

    public l(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, long paramLong, JSONObject paramJSONObject, String paramString5, HashMap<String, Object> paramHashMap, aa parama) {
        super(paramContext, paramString1, paramString2, paramString3, paramString4, paramLong, paramJSONObject, paramString5, paramHashMap, parama);
    }

    @SuppressLint("WrongConstant")
    private static boolean a(Context paramContext, String paramString) {
        if ((paramContext == null) || (paramString == null)) {
            return false;
        }
        try {
            paramContext.getPackageManager().getPackageInfo(paramString, 256);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public final void a() {
        Object localObject1 = k.a(this.i, "interval", new JSONObject());
        int i = k.a((JSONObject) localObject1, "min", 15);
        double d1 = k.a((JSONObject) localObject1, "min", 45);
        double d2 = i;
        long l = Math.round(d2 + (d1 - d2) * Math.random());
        for (; ; ) {
            Object localObject2;
            try {
                localObject2 = new JSONObject();
                ((JSONObject) localObject2).put("interval", l);
                localObject1 = k.a(this.i, "tasks", new JSONArray());
                ((JSONObject) localObject2).put("tasks", localObject1);
                m.a(this.a).a((JSONObject) localObject2);
                localObject2 = new StringBuilder();
                i = 0;
                if (i < ((JSONArray) localObject1).length()) {
                    String str = ((JSONArray) localObject1).getJSONObject(i).getString("packageName");
                    if (!a(this.a, str)) {
                        break;
                    }
                    if (((StringBuilder) localObject2).length() == 0) {
                        ((StringBuilder) localObject2).append(str);
                    } else {
                        ((StringBuilder) localObject2).append("," + str);
                    }
                }
            } catch (Exception localException) {
                a(0, "[Exception=" + localException.getMessage() + "]");
                m.a(this.a).a(new JSONObject());
                return;
            }
            a(1, ((StringBuilder) localObject2).toString());
            i += 1;
            return;
        }
    }
}

