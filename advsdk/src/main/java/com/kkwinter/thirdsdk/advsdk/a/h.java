package com.kkwinter.thirdsdk.advsdk.a;


import java.util.HashMap;
import java.util.Map;

final class h {
    private static final Map<String, Class<? extends g>> a;

    public static g a(String var0) {
        if (var0 == null) {
            var0 = "";
        }

        g var1 = null;

        try {
            var1 = (g)((Class)a.get(var0.toLowerCase())).newInstance();
        } catch (InstantiationException var2) {
            ;
        } catch (IllegalAccessException var3) {
            ;
        }

        return var1;
    }

    static {
        (a = new HashMap()).put("", i.class);
        a.put("gzip", b.class);
        a.put("deflate", a.class);
    }
}
