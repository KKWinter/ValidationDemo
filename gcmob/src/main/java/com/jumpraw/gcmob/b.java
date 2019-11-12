package com.jumpraw.gcmob;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import android.widget.Toast;
import java.util.List;

public final class b {
    public static boolean a(Context var0, String var1) {
        if (var1 != null && !"".equals(var1)) {
            try {
                PackageInfo var3;
                return (var3 = var0.getPackageManager().getPackageInfo(var1, 0)) != null ? var1.equals(var3.packageName) : false;
            } catch (Exception var2) {
                return false;
            }
        } else {
            return false;
        }
    }

    public static void b(Context var0, String var1) {
        try {
            Intent var4;
            List var5;
            ResolveInfo var6;
            if (!TextUtils.isEmpty(var1) && a(var0, var1) && (var4 = var0.getPackageManager().getLaunchIntentForPackage(var1)) != null && (var5 = var0.getPackageManager().queryIntentActivities(var4, 0)) != null && var5.size() > 0 && (var6 = (ResolveInfo)var5.iterator().next()) != null) {
                ComponentName var7 = new ComponentName(var6.activityInfo.packageName, var6.activityInfo.name);
                Intent var2;
                (var2 = new Intent()).setComponent(var7);
                var2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                var0.startActivity(var2);
            }

        } catch (Exception var3) {
            Toast.makeText(var0, "The app connot start up", 0).show();
            var3.printStackTrace();
        }
    }
}
