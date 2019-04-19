package com.kkwinter.thirdsdk.advsdk.b;

import android.content.Context;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;


/**
 * screenResolution 工具类
 */
public final class m {
    public static int a(Context var0) {
        new DisplayMetrics();
        int var1 = var0.getResources().getDisplayMetrics().widthPixels;
        Display var5 = ((WindowManager)var0.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        if (VERSION.SDK_INT >= 14 && VERSION.SDK_INT < 17) {
            try {
                var1 = (Integer)Display.class.getMethod("getRawWidth").invoke(var5);
            } catch (Exception var4) {
                ;
            }
        } else if (VERSION.SDK_INT >= 17) {
            try {
                Point var2 = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(var5, var2);
                var1 = var2.x;
            } catch (Exception var3) {
                ;
            }
        }

        return var1;
    }

    public static int b(Context var0) {
        new DisplayMetrics();
        int var1 = var0.getResources().getDisplayMetrics().heightPixels;
        Display var5 = ((WindowManager)var0.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        if (VERSION.SDK_INT >= 14 && VERSION.SDK_INT < 17) {
            try {
                var1 = (Integer)Display.class.getMethod("getRawHeight").invoke(var5);
            } catch (Exception var4) {
                ;
            }
        } else if (VERSION.SDK_INT >= 17) {
            try {
                Point var2 = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(var5, var2);
                var1 = var2.y;
            } catch (Exception var3) {
                ;
            }
        }

        return var1;
    }
}

