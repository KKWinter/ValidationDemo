package com.kkwinter.thirdsdk.advworkflow.a;

import java.util.HashMap;
import java.util.Map;

final class i {
    private static final Map<String, Class<? extends h>> a;

    static {
        HashMap var0 = new HashMap();
        a = var0;
        var0.put("", j.class);
        a.put("gzip", b.class);
        a.put("deflate", a.class);
    }

    public static h a(String var0) {
        String var1 = var0;
        if (var0 == null) {
            var1 = "";
        }

        try {
            h var4 = (h)((Class)a.get(var1.toLowerCase())).newInstance();
            return var4;
        } catch (InstantiationException var2) {
            return null;
        } catch (IllegalAccessException var3) {
            return null;
        }
    }
}