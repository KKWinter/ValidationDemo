package com.kkwinter.thirdsdk.advworkflow.util;

import android.os.Handler;
import android.os.Looper;


/**
 *  开启runnable任务
 */
public final class j {
    public static final Handler a = new Handler(Looper.getMainLooper());

    public static void a(Runnable var0) {
        a.post(var0);
    }

    public static void a(Runnable var0, long var1) {
        a.postDelayed(var0, var1);
    }

    public static void b(Runnable var0) {
        a.removeCallbacks(var0);
    }
}
