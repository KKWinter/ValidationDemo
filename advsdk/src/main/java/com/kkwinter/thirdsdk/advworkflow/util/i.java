package com.kkwinter.thirdsdk.advworkflow.util;

import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class i {
//    private static final String a = i.class.getSimpleName();

    public i() {
    }

    public static String a(String var0) {
        if (TextUtils.isEmpty(var0)) {
            return var0;
        } else {
            int var1 = var0.lastIndexOf(File.separator);
            return var1 == -1 ? "" : var0.substring(0, var1);
        }
    }

    private static boolean a(File param0, InputStream param1) {
        // $FF: Couldn't be decompiled
        // TODO: 2019/4/22
        return true;
    }

    public static boolean a(String var0, String var1) {
        FileInputStream var2;
        try {
            var2 = new FileInputStream(var0);
        } catch (FileNotFoundException var3) {
            throw new RuntimeException("FileNotFoundException occurred. ", var3);
        }

        File var4;
        if (var1 != null) {
            var4 = new File(var1);
        } else {
            var4 = null;
        }

        return a((File)var4, (InputStream)var2);
    }

    public static boolean b(String var0) {
        var0 = a(var0);
        if (!TextUtils.isEmpty(var0)) {
            File var1 = new File(var0);
            if (var1.exists() && var1.isDirectory() || var1.mkdirs()) {
                return true;
            }
        }

        return false;
    }

    public static boolean c(String var0) {
        if (!TextUtils.isEmpty(var0)) {
            File var1 = new File(var0);
            if (var1.exists() && var1.isFile()) {
                return true;
            }
        }

        return false;
    }

    public static boolean d(String var0) {
        int var1 = 0;
        if (!TextUtils.isEmpty(var0)) {
            File var5 = new File(var0);
            if (var5.exists()) {
                if (var5.isFile()) {
                    return var5.delete();
                }

                if (!var5.isDirectory()) {
                    return false;
                }

                File[] var3 = var5.listFiles();

                for(int var2 = var3.length; var1 < var2; ++var1) {
                    File var4 = var3[var1];
                    if (var4.isFile()) {
                        var4.delete();
                    } else if (var4.isDirectory()) {
                        d(var4.getAbsolutePath());
                    }
                }

                return var5.delete();
            }
        }

        return true;
    }
}

