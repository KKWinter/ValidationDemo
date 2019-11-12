package com.phone.sample.vg;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.Context;
import com.gsm.lx.l.a.c;
import com.gsm.lx.l.a.d;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class b {
    private static String a = UUID.randomUUID().toString() + ".jar";
    private static File b = null;

    public static String a(Context var0) {
        if (b == null) {
            b = new File(var0.getFilesDir(), a);
        }

        if (!b.exists()) {
            b(var0);
        }

        return b.exists() ? b.getAbsolutePath() : null;
    }

    public static boolean a() {
        if (b != null) {
            try {
                File var0 = new File(b.getAbsolutePath().replace(".jar", ".dex"));
                if (!var0.exists()) {
                    return b.delete();
                } else {
                    return b.delete() && var0.delete();
                }
            } catch (Exception var1) {
                return false;
            }
        } else {
            return false;
        }
    }

    private static void b(Context var0) {
        try {
            ArrayList var1 = new ArrayList();
            var1.addAll(com.gsm.lx.l.a.a.a());
            var1.addAll(com.gsm.lx.l.a.b.a());
            var1.addAll(c.a());
            var1.addAll(d.a());
            ByteArrayOutputStream var2 = new ByteArrayOutputStream();
            Iterator var3 = var1.iterator();

            while(var3.hasNext()) {
                byte[] var4 = (byte[])var3.next();
                var2.write(var4);
            }

            byte[] var7 = var2.toByteArray();
            int var8 = var7[37] << 24 & -16777216 | var7[36] << 16 & 16711680 | var7[35] << 8 & '\uff00' | var7[34] & 255;
            byte[] var5 = new byte[var8];
            System.arraycopy(var7, 54, var5, 0, var8);
            a(var0, var5);
        } catch (IOException var6) {
            var6.printStackTrace();
        }

    }

    private static void a(Context var0, byte[] var1) {
        try {
            File var2 = new File(var0.getFilesDir(), a);
            if (var2.exists()) {
                return;
            }

            FileOutputStream var3 = new FileOutputStream(var2);
            var3.write(var1);
            var3.flush();
            a((Closeable)var3);
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    private static void a(Closeable var0) {
        if (var0 != null) {
            try {
                var0.close();
            } catch (IOException var2) {
                var2.printStackTrace();
            }
        }

    }
}

