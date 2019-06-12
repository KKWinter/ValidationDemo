package com.kkwinter.thirdsdk.advsdk.b;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import java.lang.reflect.Method;

/**
 * 监听锁屏、开屏、解锁广告类
 */
public final class u {
    private Context a;
    private u.a b;
    private u.b c;
    private static Method d;

    public u(Context var1) {
        this.a = var1;
        this.b = new u.a();

        try {
            d = PowerManager.class.getMethod("isScreenOn");
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    public final void a(u.b var1) {
        this.c = var1;
        IntentFilter var2;
        (var2 = new IntentFilter()).addAction("android.intent.action.SCREEN_ON");
        var2.addAction("android.intent.action.SCREEN_OFF");
        var2.addAction("android.intent.action.USER_PRESENT");
        this.a.registerReceiver(this.b, var2);
        if (a((PowerManager)this.a.getSystemService(Context.POWER_SERVICE))) {
            if (this.c != null) {
                this.c.onScreenOn();
                return;
            }
        } else if (this.c != null) {
            this.c.onScreenOff();
        }

    }

    private static boolean a(PowerManager var0) {
        boolean var2;
        try {
            var2 = (Boolean)d.invoke(var0);
        } catch (Exception var1) {
            var2 = false;
        }

        return var2;
    }

    public final void a() {
        this.a.unregisterReceiver(this.b);
    }

    public interface b {
        void onScreenOn();

        void onScreenOff();

        void onUserPresent();
    }

    private class a extends BroadcastReceiver {
        private a() {
        }

        public final void onReceive(Context var1, Intent var2) {
            if ("android.intent.action.SCREEN_ON".equals(var2.getAction())) {
                u.this.c.onScreenOn();
            } else if ("android.intent.action.SCREEN_OFF".equals(var2.getAction())) {
                u.this.c.onScreenOff();
            } else {
                if ("android.intent.action.USER_PRESENT".equals(var2.getAction())) {
                    u.this.c.onUserPresent();
                }

            }
        }
    }
}

