package com.kkwinter.thirdsdk.advworkflow.util;

import android.text.TextUtils;
import java.io.File;

public final class h {


    private static String a(File param0) {
        // $FF: Couldn't be decompiled
        // TODO: 2019/4/22
        return "";
    }

    private static boolean a(String var0, String var1, String var2) {
        var0 = var0.replace("file://", "");
        if (i.c(var0)) {
            try {
                if (i.a(var0, var1)) {
                    if (!TextUtils.isEmpty(var2)) {
                        boolean var3 = a(new File(var1)).equalsIgnoreCase(var2);
                        return var3;
                    }

                    return true;
                }
            } catch (Exception var4) {
                return false;
            }
        }

        return false;
    }

    public static boolean a(String var0, String var1, String var2, String var3) {
        int var4;
        if (!TextUtils.isEmpty(var0)) {
            for(var4 = 0; var4 < 2; ++var4) {
                if (b(var0, var2, var3)) {
                    return true;
                }
            }
        }

        if (!TextUtils.isEmpty(var1)) {
            for(var4 = 0; var4 < 2; ++var4) {
                if (b(var1, var2, var3)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean b(String var0, String var1, String var2) {
        return !var0.startsWith("file://") && !var0.startsWith("FILE://") ? c(var0, var1, var2) : a(var0, var1, var2);
    }


    private static boolean c(String param0, String param1, String param2) {
        // $FF: Couldn't be decompiled
        // TODO: 2019/4/22
        return true;
    }
}

