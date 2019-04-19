package com.kkwinter.thirdsdk.advsdk.b;


import android.annotation.SuppressLint;
import android.os.Process;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;


/**
 * 错误信息处理类
 */
public final class s {
    public static String a = k.a("C6B9rfJgIQ==");
    private static s c = null;
    public static String b = k.a("ZYBKht8ACnFteHXTvCWBxc13bMufSm3j5Yc=");
    private boolean d = true;
    private String e = "";
    private FileWriter f = null;

    public s() {
    }

    private static final s a() {
        if (c == null) {
            (c = new s()).e = a;
            c.d = "0".equalsIgnoreCase(k.a("ew=="));
            if (c.d) {
                try {
                    File var0;
                    if ((var0 = new File(b)).exists()) {
                        c.f = new FileWriter(var0, true);
                    }
                } catch (IOException var1) {
                    c.f = null;
                }
            }
        }

        return c;
    }

    @SuppressLint({"SimpleDateFormat"})
    private static void a(String var0) {
        if (a().d) {
            if (var0 != null) {
                StackTraceElement var1 = (new Throwable()).getStackTrace()[2];
                String var2 = (new Exception()).getStackTrace()[2].getMethodName();
                String var3;
                if ((var3 = var1.getFileName()) != null && var3.indexOf(k.a("ZA==")) > 0) {
                    var3 = var3.substring(0, var3.indexOf(k.a("ZA==")));
                } else {
                    var3 = "";
                }

                var0 = k.a("EQ==") + var3 + k.a("cA==") + var2 + k.a("cA==") + var1.getLineNumber() + k.a("F8Q=") + k.a("MQ==") + Process.myTid() + k.a("N8Q=") + var0;
                Log.e(c.e, var0);
                if (c.f != null) {
                    Date var5 = new Date(System.currentTimeMillis());
                    String var6 = (new SimpleDateFormat(k.a("AqwRn9MVFW00amqP"))).format(var5);
                    var0 = k.a("EQ==") + var6 + k.a("F8Q=") + var0 + k.a("FpZ3nA==");

                    try {
                        c.f.write(var0);
                        c.f.flush();
                        return;
                    } catch (IOException var4) {
                        c.f = null;
                    }
                }

            }
        }
    }

    public static void a(Exception var0) {
        try {
            StringWriter var1 = new StringWriter();
            PrintWriter var2 = new PrintWriter(var1);
            var0.printStackTrace(var2);
            a(k.a("aqF5oPF9XA==") + var0.getMessage());
            a(k.a("FpZ3nA==") + var1.toString() + k.a("FpZ3nA=="));
        } catch (Exception var3) {
            a(k.a("aoZKlp5IA2pLa2uTugGfjMNVaMidaDvs75DNjK95"));
        }
    }
}


