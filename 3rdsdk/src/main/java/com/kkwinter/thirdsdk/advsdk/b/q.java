package com.kkwinter.thirdsdk.advsdk.b;

import android.os.Handler;
import android.os.Looper;

/**
 * 延迟启动，主线程
 */
public final class q {
    public static final Handler a = new Handler(Looper.getMainLooper());

    public static void a(Runnable var0, long var1) {
        a.postDelayed(var0, var1);
    }
}
