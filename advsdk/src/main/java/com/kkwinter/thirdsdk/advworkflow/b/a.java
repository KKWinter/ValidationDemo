package com.kkwinter.thirdsdk.advworkflow.b;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;
import android.view.WindowManager;
import android.webkit.WebView;

import java.net.URLDecoder;
import java.util.HashMap;

import org.json.JSONObject;

public abstract class a {
    protected Context a = null;
    protected String b = "";
    protected String c = "";
    protected String d = "";
    protected String e = "";
    protected String f = "";
    protected long g = 0L;
    protected aa h = null;
    protected JSONObject i = null;
    protected HashMap<String, Object> j = null;

    public a(Context var1, String var2, String var3, String var4, String var5, long var6, JSONObject var8, String var9, HashMap<String, Object> var10, aa var11) {
        this.a = var1;
        this.b = var2;
        this.c = var4;
        this.d = var3;
        this.e = var5;
        this.g = var6;
        this.f = var9;
        this.j = var10;
        this.h = var11;

        try {
            // content
            this.i = new JSONObject(URLDecoder.decode(new String(Base64.decode(com.kkwinter.thirdsdk.advworkflow.util.k.a(var8, com.kkwinter.thirdsdk.advworkflow.b.c.a("EmF6j7OsAg=="), new String(Base64.encode("".getBytes(), 0))), 0))));
        } catch (Exception var12) {
            this.i = new JSONObject();
        }
    }

    private static WindowManager.LayoutParams a(Context paramContext, b paramb, StringBuilder paramStringBuilder) {
        for (; ; ) {
            try {
                paramContext.getSystemService(Context.WINDOW_SERVICE);
                WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
                boolean bool = com.kkwinter.thirdsdk.advworkflow.util.a.a("android.permission.SYSTEM_ALERT_WINDOW", paramContext);
                if (Build.VERSION.SDK_INT >= 23) {
                    bool = Settings.canDrawOverlays(paramContext);
                }
                if (bool) {
                    if (Build.VERSION.SDK_INT >= 26) {
                        localLayoutParams.type = 2038;
                        localLayoutParams.alpha = paramb.c;
                        localLayoutParams.format = 1;
                        localLayoutParams.flags = 16777220;
                        if (!paramb.b) {
                            localLayoutParams.flags = (localLayoutParams.flags | 0x8 | 0x10);
                        }
                        localLayoutParams.gravity = 119;
                        return localLayoutParams;
                    }
                    localLayoutParams.type = 2003;
                    continue;
                }
                localLayoutParams.type = 2002;
            } catch (Exception e) {
                paramStringBuilder.append(com.kkwinter.thirdsdk.advworkflow.b.c.a("lIaPHm14OnKh6JibLE0kwR+geDtA4znQ1PQ=") + e.getMessage() + com.kkwinter.thirdsdk.advworkflow.b.c.a("LA=="));
                return null;
            }
            m.a(paramContext);
        }
    }


    public static WebView a(Context param0, a.c param1, StringBuilder param2) {
        // $FF: Couldn't be decompiled
        // TODO: 2019/4/22
        return null;
    }

    public abstract void a();

    public final void a(final int var1, final String var2) {

        long var3 = Math.round(Math.random() * 55.0D + 5.0D);

        com.kkwinter.thirdsdk.advworkflow.util.j.a(new Runnable() {
            public final void run() {

                b(var1, var2);

            }
        }, var3 * 1000L);
    }

    public final void b(final int var1, final String var2) {

        (new Thread(new Runnable() {
            public final void run() {

                long var1x = System.currentTimeMillis() / 1000L;

                long var3 = com.kkwinter.thirdsdk.advworkflow.b.a.this.g;

                com.kkwinter.thirdsdk.advworkflow.util.s.a(b, d, c, e, var1, var1x - var3, var2);

                com.kkwinter.thirdsdk.advworkflow.util.j.a(new Runnable() {
                    public final void run() {

                        aa var2x = h;

                        boolean var1x;
                        if (var1 == 0) {
                            var1x = false;
                        } else {
                            var1x = true;
                        }

                        var2x.a(var1x);
                    }
                });
            }
        })).start();
    }

    public interface aa {
        void a(boolean var1);
    }

    public class b {
        public boolean a = false;
        public boolean b = false;
        public float c = 1.0F;

        public b() {
        }
    }

    public final class c extends b {
        public String e = "";
        public int f = -1;
        public String g = null;
        public int h = 0;
        public boolean i = true;
        public boolean j = false;
        public boolean k = false;
        public HashMap<String, String> l = new HashMap();

        public c() {
            super();
        }
    }
}

