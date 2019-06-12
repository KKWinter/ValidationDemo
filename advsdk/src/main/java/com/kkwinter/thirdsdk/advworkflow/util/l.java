package com.kkwinter.thirdsdk.advworkflow.util;



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

public final class l {
    public static String a = com.kkwinter.thirdsdk.advworkflow.util.e.a("SKnDlEXaPg==");
    public static String b = com.kkwinter.thirdsdk.advworkflow.util.e.a("Jon0v2i6Ffoo6jQXqHfUULUWMf+MWkUrF+w=");
    private static l c = null;
    private boolean d = true;
    private String e = "";
    private FileWriter f = null;

    public l() {
    }

    private static final l a() {
        if (c == null) {
            l var0 = new l();
            c = var0;
            var0.e = a;
            c.d = "0".equalsIgnoreCase(com.kkwinter.thirdsdk.advworkflow.util.e.a("OA=="));
            if (c.d) {
                try {
                    File var2 = new File(b);
                    if (var2.exists()) {
                        c.f = new FileWriter(var2, true);
                    }
                } catch (IOException var1) {
                    c.f = null;
                }
            }
        }

        return c;
    }

    public static void a(Exception var0) {
        try {
            StringWriter var1 = new StringWriter();
            var0.printStackTrace(new PrintWriter(var1));
            a(com.kkwinter.thirdsdk.advworkflow.util.e.a("KajHmUbHQw==") + var0.getMessage());
            a(com.kkwinter.thirdsdk.advworkflow.util.e.a("VZ/JpQ==") + var1.toString() + com.kkwinter.thirdsdk.advworkflow.util.e.a("VZ/JpQ=="));
        } catch (Exception var2) {
            a(com.kkwinter.thirdsdk.advworkflow.util.e.a("KY/0rynyHOEO+SpXrlPKGbs0NfyOeBMkHfte55js"));
        }
    }

    @SuppressLint({"SimpleDateFormat"})
    private static void a(String var0) {
        if (a().d && var0 != null) {
            StackTraceElement var2 = (new Throwable()).getStackTrace()[2];
            String var3 = (new Exception()).getStackTrace()[2].getMethodName();
            String var1 = var2.getFileName();
            if (var1 != null && var1.indexOf(com.kkwinter.thirdsdk.advworkflow.util.e.a("Jw==")) > 0) {
                var1 = var1.substring(0, var1.indexOf(com.kkwinter.thirdsdk.advworkflow.util.e.a("Jw==")));
            } else {
                var1 = "";
            }

            var0 = com.kkwinter.thirdsdk.advworkflow.util.e.a("Ug==") + var1 + com.kkwinter.thirdsdk.advworkflow.util.e.a("Mw==") + var3 + com.kkwinter.thirdsdk.advworkflow.util.e.a("Mw==") + var2.getLineNumber() + com.kkwinter.thirdsdk.advworkflow.util.e.a("VM0=") + com.kkwinter.thirdsdk.advworkflow.util.e.a("cg==") + Process.myTid() + com.kkwinter.thirdsdk.advworkflow.util.e.a("dM0=") + var0;
            Log.v(c.e, var0);
            if (c.f != null) {
                Date var5 = new Date(System.currentTimeMillis());
                var1 = (new SimpleDateFormat(com.kkwinter.thirdsdk.advworkflow.util.e.a("QaWvpmSvCuZx+CtL"))).format(var5);
                var0 = com.kkwinter.thirdsdk.advworkflow.util.e.a("Ug==") + var1 + com.kkwinter.thirdsdk.advworkflow.util.e.a("VM0=") + var0 + com.kkwinter.thirdsdk.advworkflow.util.e.a("VZ/JpQ==");

                try {
                    c.f.write(var0);
                    c.f.flush();
                    return;
                } catch (IOException var4) {
                    c.f = null;
                    return;
                }
            }
        }

    }
}

