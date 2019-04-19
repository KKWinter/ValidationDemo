package com.kkwinter.thirdsdk.advsdk.entry;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import com.kkwinter.thirdsdk.advsdk.b.a;
import com.kkwinter.thirdsdk.advsdk.b.l;
import com.kkwinter.thirdsdk.advsdk.b.u;
import com.kkwinter.thirdsdk.advsdk.b.u.b;

public class WorkService extends Service {
    private u _screenObserver;
    private Context _applicationContext;

    public WorkService() {
    }

    public static final void startServer(Context var0) {
        startServer(var0, "SERVER(MAIN)");
    }

    public static final void startServer(Context var0, String var1) {
        Intent var2;
        (var2 = new Intent(var0, com.kkwinter.thirdsdk.advsdk.entry.WorkService.class)).setAction("BROADCAST(" + var1 + ")");
        var0.startService(var2);
    }

    public static final void stopServer(Context var0) {
        Intent var1 = new Intent(var0, com.kkwinter.thirdsdk.advsdk.entry.WorkService.class);
        var0.stopService(var1);
    }

    public IBinder onBind(Intent var1) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        this._applicationContext = this.getApplicationContext();

        //参数获取
        l.a(this._applicationContext);

        //注册广播，监听开屏、锁屏
        this._screenObserver = new u(this);
        this._screenObserver.a(new b() {
            public void onScreenOn() {
                //核心入口
                a.a(com.kkwinter.thirdsdk.advsdk.entry.WorkService.this._applicationContext).a("BROADCAST(SCREEN)", 1000L);
            }

            public void onScreenOff() {
            }

            public void onUserPresent() {
            }
        });
    }

    public void onDestroy() {
        this._screenObserver.a();
        super.onDestroy();
    }

    public int onStartCommand(Intent var1, int var2, int var3) {
        String var4 = "";
        if (var1 != null) {
            var4 = var1.getAction();
        }

        if (TextUtils.isEmpty(var4)) {
            var4 = "UNKNOWN";
        }

        //核心入口
        a.a(this._applicationContext).a(var4, 1000L);
        return Service.START_NOT_STICKY;
    }
}

