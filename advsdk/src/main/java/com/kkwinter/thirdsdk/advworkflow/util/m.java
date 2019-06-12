package com.kkwinter.thirdsdk.advworkflow.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public final class m {
    private static long a = 14400L;
    private static String b = com.kkwinter.thirdsdk.advworkflow.util.d.a("S1U6s48NUV0yoynBHzdbJOlnCs2R/KbYfuk=");
    private static String c = com.kkwinter.thirdsdk.advworkflow.util.d.a("S1U6s48NUV0yoynBHzdbJOlnCteU+LPR");
    private static String d = com.kkwinter.thirdsdk.advworkflow.util.d.a("S1U6s48NUV0yoynBHzdbJOlnCsOE+rHHbPkWBccAQPs=");

    public static boolean a(Context var0) {
        boolean var1 = false;
        if (a(var0, b)) {
            try {
                var0.startActivity(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse(com.kkwinter.thirdsdk.advworkflow.util.d.a("WlAvh5oLUzg=") + var0.getPackageName())));
                p.a(var0, b, String.valueOf(System.currentTimeMillis() / 1000L));
            } catch (Exception var2) {
                return false;
            }

            var1 = true;
        }

        return var1;
    }

    private static boolean a(Context var0, String var1) {
        long var2;
        try {
            var2 = Long.parseLong(p.b(var0, var1, com.kkwinter.thirdsdk.advworkflow.util.d.a("Gg==")));
        } catch (Exception var4) {
            var2 = 0L;
        }

        return System.currentTimeMillis() / 1000L - var2 > a;
    }
}
