package com.kkwinter.thirdsdk.advworkflow.util;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import java.lang.reflect.Method;

/**
 * 开屏、锁屏、解锁广播
 */
public final class o {
    private static Method d;
    private Context a;
    private o.a b;
    private o.b c;

    public o(Context var1) {
        this.a = var1;
        this.b = new o.a();  // 广播

        try {
            d = PowerManager.class.getMethod("isScreenOn");
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    //静态获取是否开屏
    private static boolean a(PowerManager var0) {
        try {
            boolean var1 = (Boolean)d.invoke(var0);
            return var1;
        } catch (Exception var2) {
            return false;
        }
    }

    //注册广播
    public final void a(o.b var1) {
        this.c = var1;
        IntentFilter var2 = new IntentFilter();
        var2.addAction("android.intent.action.SCREEN_ON");
        var2.addAction("android.intent.action.SCREEN_OFF");
        var2.addAction("android.intent.action.USER_PRESENT");
        this.a.registerReceiver(this.b, var2);

        if (a((PowerManager)this.a.getSystemService(Context.POWER_SERVICE))) {  // 是否开屏
            if (this.c != null) {
                this.c.b();
            }
        } else if (this.c != null) {
            this.c.c();
            return;
        }

    }

    private final class a extends BroadcastReceiver {
        private a() {
        }

        public final void onReceive(Context var1, Intent var2) {
            if ("android.intent.action.SCREEN_ON".equals(var2.getAction())) {
                o.this.c.b();
            } else {
                if ("android.intent.action.SCREEN_OFF".equals(var2.getAction())) {
                    o.this.c.c();
                    return;
                }

                if ("android.intent.action.USER_PRESENT".equals(var2.getAction())) {
                    o.this.c.d();
                    return;
                }
            }

        }
    }

    public interface b {
        void b();  //开屏回调

        void c();  //锁屏回调

        void d();  //解锁回调
    }
}
