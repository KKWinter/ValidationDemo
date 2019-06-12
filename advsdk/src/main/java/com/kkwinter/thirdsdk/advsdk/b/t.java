package com.kkwinter.thirdsdk.advsdk.b;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 获取网络类型工具类
 */
public class t {
    private static final String c = t.class.getSimpleName();

    public t() {
    }

    public static t.a a(Context var0) {
        NetworkInfo var1;
        if ((var1 = ((ConnectivityManager)var0.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo()) != null) {
            switch(var1.getType()) {
                case 0:
                    return t.a.b;
                case 1:
                    return t.a.c;
                default:
                    return t.a.d;
            }
        } else {
            return t.a.a;
        }
    }

    public static enum a {
        a(1),
        b(2),
        c(4),
        d(8);

        public int e;

        private a(int var3) {
            this.e = var3;
        }
    }
}