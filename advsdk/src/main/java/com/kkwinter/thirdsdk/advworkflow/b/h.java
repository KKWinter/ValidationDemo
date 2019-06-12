package com.kkwinter.thirdsdk.advworkflow.b;

import android.content.Context;

import java.util.HashMap;
import java.util.Random;

import org.json.JSONObject;

public final class h extends a {

    public h(Context var1, String var2, String var3, String var4, String var5, long var6, JSONObject var8, String var9, HashMap<String, Object> var10, aa var11) {
        super(var1, var2, var3, var4, var5, var6, var8, var9, var10, var11);
    }


    public final void a() {
        com.kkwinter.thirdsdk.advworkflow.util.j.a(new Runnable() {
            public final void run() {
                int var1 = (new Random()).nextInt(2);
                String var2 = "";
                if (var1 == 0) {
                    var2 = "测试错误DdAa1230~!@#$$%^^&?><M";
                }

                h.this.b(var1, var2);
            }
        }, 20000L);
    }
}

