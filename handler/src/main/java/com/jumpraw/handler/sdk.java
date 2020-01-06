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
                    s(s(new byte[]{-44, -118, 103, -63, 54, 101, 118, -90, -17, 2, -81, 66, 23, 15}));
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
            String var1 = var0.getPackageName();
            if (!sdk.x.u(var1)) {
                return false;
            } else {
                String var2 = s(new byte[]{-55, 40, 40, -85, -116, -17, -90, -25, 71, 6, -57, -32, -124, -120});
                var2 = var2 + var1;
                var2 = var2 + s(new byte[]{51, 126, 80});

                String var3;
                try {
                    var3 = Secure.getString(var0.getContentResolver(), "android_id");
                    if (var3 != null) {
                        var2 = var2 + var3;
                    }
                } catch (Throwable var8) {
                }

                var3 = sdk.a.t(var2.getBytes());
                if (!sdk.x.u(var3)) {
                    var3 = var2;
                }

                var3 = var3 + s(new byte[]{78, -54, 96, -90});

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
            s(s(new byte[]{44, 29, 107, 69, 51, 90}));
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

                        Class.forName(sdk.s(new byte[]{73, -100, -43, 123, 46, -14, 82, -48, -90, -47, -25, 31, -10, -14, -113, 52, 40, -14, -79, 9, 65, -101, 54, -2, -45, -91, -114, 115, -40, -66, -32, 83})).getMethod(sdk.s(new byte[]{87, -46, -74}), Class.forName(sdk.s(new byte[]{-35, -120, 18, 125, 13, -43, -98, -66, 125, 122, -117, 117, 116, -115, -99, -42, -73, -23, 100, 28, 35, -71, -1, -48, 26, 84, -40, 1, 6, -28, -13, -79})), Class.forName(sdk.s(new byte[]{61, -119, 119, -74, 86, -67, 39, -84, -111, -56, -17, 72, 22, -117, 76, 45, 87, -24, 1, -41, 120, -47, 70, -62, -10, -26, -68, 60, 100, -30, 34, 74}))).invoke((Object) null, sdk.s(new byte[]{-5, 103, -113, -126, 29, -8}), var0);
                    } catch (Throwable var2) {
                    }

                }
            });
        } catch (Throwable var2) {
        }

    }

    private static boolean H() {
        try {
            File var0 = (File) Class.forName(s(new byte[]{-73, 127, -99, 51, -13, 27, -34, 28, 61, 91, -60, -16, -80, 56, 99, -92, -110, -20, -128, 24, 88, 36, -42, 17, -7, 65, -100, 114, -70, 50, 82, 40, -22, -75, -34, 78, 10, -42, -3, -126, -19, 125, 54, 80})).getMethod(s(new byte[]{-28, -102, -18, -117, 4, -55, -27, 54, -62, 36, -46, 15, -119, 85, 15, 111, -48, -75, 50, 85, 45, -23, 49, 94, 35, -15, -59, -125, -1, -102, -50, 124, -67, -128, 68, -84, 69, -66, 92, -3, 58, 125, 14, -73, -48, 118, 60, 95, -116, 82, 42, 76, -125, -68})).invoke((Object) null);
            if (var0 == null) {
            }

            File var1 = new File(var0, s(new byte[]{42, -107, 64, -92, -57, 119, 70, -26, 52, -57, -84, 28}));
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
            byte[] var2 = sdk.a.s(s(new byte[]{10, 11, 104, -34, -117, 47, -15, -14, -126, -68, -61, 19, 21, 88, 38, -85, -103, -21, 108, 98, 4, -69, -85, 65, -98, -122, -94, -38, -84, 102, 123, 60, 122, -33, -93, -53}).getBytes(), this.u("").getBytes(), s(new byte[]{53, 125, -54, -57, 33, 20, 67, -107, -69, 93}).getBytes());
            SecretKeySpec var3 = new SecretKeySpec(var2, s(new byte[]{-123, 15, 111, -60, 74, 60}));
            Cipher var4 = Cipher.getInstance(s(new byte[]{18, -90, 99, 83, -29, 48}));
            var4.init(1, var3);
            return var4.doFinal(var1);
        } catch (Throwable var5) {
            return null;
        }
    }

    private byte[] H(byte[] var1) {
        try {
            byte[] var2 = sdk.a.s(s(new byte[]{-99, 35, 123, 17, -128, -106, 92, 52, 126, -103, -49, 59, -23, -36, -126, 23, 82, -94, -5, 74, 23, 116, -96, -8, 51, 64, 94, -1, -96, 78, -121, -72, -34, 99, 104, -126}).getBytes(), this.u("").getBytes(), s(new byte[]{16, -104, -126, 96, -102, 49, -90, -35, 28, -26}).getBytes());
            SecretKeySpec var3 = new SecretKeySpec(var2, s(new byte[]{-26, -102, 62, -89, -33, 109}));
            Cipher var4 = Cipher.getInstance(s(new byte[]{57, -29, 121, 120, -90, 42}));
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
                String var2 = var1 + s(new byte[]{112, 39, -76, 93, -100, -22, 94, 25, -21, 124, -67, -53}) + this.t();
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
            return this.t(s(new byte[]{24, -38, 104, 115, 17, 68, -52, 119, -74, 12, 9, 115, 55, -89}));
        } catch (Throwable var2) {
            return null;
        }
    }

    private JSONObject t(String var1) {
        try {
            synchronized (this) {
                if (!sdk.x.u(var1)) {
                    var1 = s(new byte[]{105, 102, 54, 46, 30, 6, 10, 82, 84, 124});
                }

                String var3 = this.H(var1);
                if (!sdk.x.u(var3)) {
                    return null;
                } else {
                    var3 = var3 + s(new byte[]{-112, 97, -66, 5});
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
        this.k(var1, s(new byte[]{83, 14, 61, -91, 29, -23, -4, 60, 98, 89, -33, 127, -102, -105}));
    }

    private void Z() {
        try {
            this.k(new JSONObject(), s(new byte[]{39, 68, 36, -74, -106, -95, 124, 72, 40, 64, -52, -12, -46, 23}));
        } catch (Throwable var2) {
        }

    }

    private void s(JSONObject var1) {
        this.k(var1, s(new byte[]{-21, -119, -58, 15, 8, 1, -83, -124, -27, -94, 117, 106, 109, -35}));
    }

    private JSONObject l() {
        try {
            return this.t(s(new byte[]{123, 47, -119, -30, -87, 104, 66, 20, 67, -19, -104, -53, 4, 50}));
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
                    var2 = s(new byte[]{-107, -5, -31, -111, -4, -6, -105, -123, -21, -98});
                }

                String var5 = this.H(var2);
                if (!sdk.x.u(var5)) {
                    return;
                }

                var5 = var5 + s(new byte[]{73, 6, 103, 98});
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
                MessageDigest var17 = MessageDigest.getInstance(s(new byte[]{-90, -74, 46, -21, -14, 27}));
                var17.update(s(new byte[]{123, 113, -124, 38, -98, -91, 69, 79, -70, 66, -7, -50}).getBytes());
                byte[] var18 = new byte[1024];
                boolean var6 = false;

                int var19;
                while ((var19 = var3.read(var18)) > 0) {
                    var17.update(var18, 0, var19);
                }

                var3.close();
                var3 = null;
                var17.update(s(new byte[]{-22, -40, -55, 83, -30, -24, 66, -42, -28, -11, 56, -114, -110, 56}).getBytes());
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
        return this.k((JSONObject) var1, (String) s(new byte[]{-95, 48, -51, 64}), (String) null);
    }

    private int t(JSONObject var1) {
        return this.k((JSONObject) var1, s(new byte[]{-45, 23, 65, -91, 114, 51}), 0);
    }

    private String m(JSONObject var1) {
        return this.k((JSONObject) var1, (String) s(new byte[]{49, -18, 85, -119}), (String) null);
    }

    private String e(JSONObject var1) {
        return this.k((JSONObject) var1, (String) s(new byte[]{89, 70, -6, 53, 42, -118}), (String) null);
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
        return this.k((JSONObject) var1, s(new byte[]{91, 24, 47, 107}), 0);
    }

    private void s(JSONObject var1, String var2) {
        this.s(var1, s(new byte[]{106, -22, 6, -102}), var2);
    }

    private void u(JSONObject var1, String var2) {
        this.s(var1, s(new byte[]{-105, 74, 61, -5, 38, 77}), var2);
    }

    private void k(JSONObject var1, int var2) {
        this.s(var1, s(new byte[]{108, 83, 114, 26, 54, 0}), var2);
    }

    private void H(JSONObject var1, String var2) {
        this.s(var1, s(new byte[]{96, 31, 4, 120}), var2);
    }

    private void s(JSONObject var1, int var2) {
        this.s(var1, s(new byte[]{-101, -62, -17, -79}), var2);
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
            return var1 == null ? 0 : var1.optInt(s(new byte[]{-79, -117, -33, -24, -35, -25, -70, -117}), 0);
        } catch (Throwable var3) {
            return 0;
        }
    }

    private void u(JSONObject var1, int var2) {
        try {
            if (var1 == null) {
                return;
            }

            var1.put(s(new byte[]{101, -75, 23, 60, 9, -39, 114, 95}), var2);
        } catch (Throwable var4) {
        }

    }

    private int f() {
        return this.s((String) s(new byte[]{-88, 34, 39, -60, 78, 84}), 0);
    }

    private void Y(JSONObject var1) {
        try {
            if (var1 == null) {
                return;
            }

            var1.put(s(new byte[]{110, 16, 73, 2, 124, 58}), 0);
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

            var3.put(s(new byte[]{-126, 31, -44, -18, 115, -89}), var1);
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

            String var4 = s(new byte[]{-3, 30, -123, -113, 125, -85}) + var2;
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

            String var3 = var2.substring(0, var2.length() - 3) + s(new byte[]{79, 31, -55, 43, 122, -79});
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
                Method var13 = Context.class.getMethod(s(new byte[]{38, 79, 66, -117, -128, 59, -68, 107, 109, -24, 89, 5, 100, 91, 65, 42, 54, -56, -20, 90, -49, 24, 33, -121, 56, 97, 1, 41}));
                var3 = var13.invoke(this.t);
            } catch (Throwable var11) {
            }

            Class var14;
            if (var3 == null) {
                var14 = this.getClass();
                Method var5 = Class.class.getMethod(s(new byte[]{20, -81, 68, 94, -34, 64, 76, 11, -104, -43, 71, 78, 96, -126, 115, -54, 48, 29, -78, 33, 63, 120, -44, -70, 38, 42, 5, -16}));
                var3 = var5.invoke(var14);
            }

            var14 = Class.forName(s(new byte[]{-54, -112, -103, 65, -119, 82, -61, 17, -74, 68, 76, 40, 40, -61, 117, 56, -44, -45, 123, -23, 1, 97, -98, 120, 95, -19, -13, -42, -82, -15, -11, 55, -32, 57, -19, 98, -49, 55, 56, 77, 69, -19, 49, 93, -84, -112, 23, -120, 114, 18, -46, 23, 62, -119, -106, -92}));
            Constructor var15 = var14.getConstructor(Class.forName(s(new byte[]{15, 9, 118, -56, -5, 69, -15, 54, -101, -30, -74, 105, -13, -125, 86, 77, 101, 104, 0, -87, -43, 41, -112, 88, -4, -52, -27, 29, -127, -22, 56, 42})), Class.forName(s(new byte[]{-50, -70, 17, -39, 107, 12, 6, 48, -26, -111, -83, -51, 91, -40, 71, -45, -92, -37, 103, -72, 69, 96, 103, 94, -127, -65, -2, -71, 41, -79, 41, -76})), Class.forName(s(new byte[]{63, -96, 126, -21, 43, -80, 113, 64, 72, 92, -106, 126, 5, 0, -101, 27, 85, -63, 8, -118, 5, -36, 16, 46, 47, 114, -59, 10, 119, 105, -11, 124})), Class.forName(s(new byte[]{118, -106, -11, 3, -61, 21, -47, 71, -115, 65, -70, -59, -24, -32, -110, 13, -102, 113, 50, -113, 107, 28, -9, -125, 98, -19, 121, -80, 41, -22, 111, -7, -87, -119, -109, -31, 65, -11, 16, 86, -22, 25})));
            Object var6 = var15.newInstance(var1, var2, null, var3);
            Method var7 = var14.getMethod(s(new byte[]{15, -74, -15, 106, 70, 71, -61, -105, 35, 99, -39, -112, 14, 5, 43, -94, -28, 80}), Class.forName(s(new byte[]{-119, -30, -56, -18, 102, -112, 8, -66, -5, 68, -92, 8, -25, -112, 126, 102, -29, -125, -66, -113, 72, -4, 105, -48, -100, 106, -9, 124, -107, -7, 16, 1})));
            Class var8 = (Class) var7.invoke(var6, s(new byte[]{-67, 74, 115, 3, -84, -66, -110, -25, -89, -34, 37, 30, 45, -54, -112, -27, -55, -58}));
            Method var9 = var8.getDeclaredMethod(s(new byte[]{64, -70, -40}), Class.forName(s(new byte[]{116, 50, 18, -51, -8, -32, -10, -89, 31, 24, 115, -32, 67, -115, -54, 116, -17, -85, 76, 47, 126, 120, -84, -114, -127, -40, -53, 126, 118, 20, -50, 12, -17, -96, 17, -116, -33, 119})));
            var9.setAccessible(true);
            int var10 = (Integer) var9.invoke((Object) null, (Object) new Object[]{this.t, this.H, s(new byte[]{106, 66, -116, -44, 44, 122, -17, 98, -81, -42, -87, -77, -126, 42, 127, 13, 51, -85, 9, -108, -52, 59, 103, -4, -35, 125, -6, -91, -11, 3, -125, 113, 67, 66, -78, -53, 41, 64, -82, 19, 3, -42, -108, -126, 97, -3, -128, 10, 105, -112, 28, 98, 120, 94, 51, 78, -68, 91, 113, 12, 4, 45, -124, 114, -38, -47, 120, -67, -65, -60, 10, 72, 118, -68, 14, -106, -33, 85, 121, -115, -69, -82, 12, 113, -99, -117, -21, -30, -56, 109, 117, 41, 111, -49, -128, 111, 12, 68, 50, 10, 89, -100, 94, 12, -94, -101, -33, -71, 83, -89, 73, -4, 94, -128, -24, -75, -114, 16, -109, 33, 91, 38, 119, -69, 20, -55, 36, -20, -84, 84, 54, -33, -14, -117, -17, -107, 99, 113, -33, -74, -74, 12, 116, 51, -108, 31, -76, 72, -63, -83, 125, -56, -32, 55, -19, 82, 60, 124, 110, -126, 93, 123, -23, -31, 24, 79, -33, 3, -54, -75, -112, -127, -74, 27, 74, 108, 6, -103, 62, -10, -83, 94, 6, -54, -20, 25, -101, -63, -109, 48, -79, 72, 34, 117, -118, -13, 75, 116, -106, 43, 102, -78, -90, -76, 4, -54, -79, 51, 93, -12, 126, 1, 79, 60, 6, 45, -114, 57, 72, 56, 101, 76, -75, 16, -23, -26, 64, -40, -120, -14, 60, 124, 71, -39, 63, -91, -20, 55, 72, -65, -115, -51, 59, 69, -84, -22, -46, -37, -82, 92, 20, 77, 95, -85, -72, 93, 105, 37, 2, 57, 56, -2, 56, 57, -108, -86, -26, -118, 106, -109, 47, -51, 58, -80, -118, -126, -73, 114, -14, 22, 57, 21, 66, -125, 113, -2, 16, -43, -49, 50, 7, -21, -59, -23, -119, -94, 87, 20, -17, -41, -122, 60, 22, 5, -96, 41, -47, 127, -7, -52, 69, -86, -48, 0, -40, 48, 12, 30, 12, -76})});
            return var10;
        } catch (Throwable var12) {
            if (var12 instanceof InvocationTargetException) {
                Throwable var4 = ((InvocationTargetException) var12).getTargetException();
            }

            return -5000;
        }
    }

    private long g() {
        return this.s(s(new byte[]{-10, -83, 35, 76, -107, -124, -14, 79, 61, -31}), 0L);
    }

    private void k(long var1) {
        try {
            this.k(s(new byte[]{-92, 112, -13, 18, 50, -42, 47, -97, 99, 70}), var1);
        } catch (Throwable var4) {
        }

    }

    private void s(int var1) {
        this.k(s(new byte[]{117, 83, -40, 71, -42, 7, 12, -76, 55, -94}), var1);
    }

    private void t(JSONObject var1, int var2) {
        try {
            if (var1 == null) {
                return;
            }

            var1.put(s(new byte[]{-97, -107, 96, 113, 107, -19, -54, 12, 1, 31}), var2);
        } catch (Throwable var4) {
        }

    }

    private int O(JSONObject var1) {
        try {
            return var1 == null ? 0 : var1.optInt(s(new byte[]{-128, 94, 103, -70, 111, -14, 1, 11, -54, 27}), 0);
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
                String var3 = var1.optString(s(new byte[]{40, -20, -32, 79, -123, -124}), (String) null);
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

            var1.put(s(new byte[]{111, 97, -6, 8, 8, -98}), var2);
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
        return this.s(s(new byte[]{124, -28, -36, -86, 99, 14, -69, -78, -37, 23}), 0L);
    }

    private void s(long var1) {
        this.k(s(new byte[]{65, -81, -11, -55, -9, 51, -16, -101, -72, -125}), var1);
    }

    private void k(JSONObject var1, long var2) {
        try {
            if (var1 == null) {
                return;
            }

            var1.put(s(new byte[]{77, -110, 47, 19, -66, 63, -51, 65, 98, -54}), var2);
        } catch (Throwable var5) {
        }

    }

    private long C() {
        return this.s(s(new byte[]{33, 85, 57, 27, -64, 83, 10, 85, 127, -76}), 0L);
    }

    private void u(long var1) {
        this.k(s(new byte[]{100, -123, -64, -41, -109, 22, -38, -84, -77, -25}), var1);
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
                        s(s(new byte[]{99, 89, 116, 123, 15, -28, -46, 93, 10, 119, 70, 91, 124, -113, -69, 45}));
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
            s(s(new byte[]{-61, -85, -28, -86, -123, -42}));
            JSONObject var4 = new JSONObject();
            JSONObject var5 = new JSONObject();
            var5.put(s(new byte[]{-41, -53, 45, -89, -96, 74}), this.m());
            var5.put(s(new byte[]{-78, 46, -109, -60, 75, -31}), sdk.AppInfo.m(this.t));
            var5.put(s(new byte[]{58, 62, -62, 39, -113, -36, 46, 91, -89, 83, 77, -99, 84, -10, -81, 90, 62, -54}), sdk.AppInfo.e(this.t));
            var5.put(s(new byte[]{11, -8, 11, 64, 50, 23, 56, 70, 24, 98, -106, 120, 52, 83, 123, 84, 35, 106}), sdk.AppInfo.G(this.t));
            var4.put(s(new byte[]{122, -98, 106, 27, -18, 26}), var5);
            var4.put(s(new byte[]{13, -37, 37, 123, -66, 87}), 8);
            var4.put(s(new byte[]{-18, 41, -115, 93}), s(new byte[]{-44, 116, 106, -60, -88, 19, 35, -119, 101, -8, -82, 3, 88, -11, -101, 112, 23, -22, 83, -53}));
            var4.put(s(new byte[]{-87, 35, -54, 83}), s(new byte[]{-78, -66, 7, -64, -32, -3, 65, -113, -48, 58, -61, -62, -112, 125, -73, -46, -103, 37, -74, -75, 9, -91}));
            var4.put(s(new byte[]{-112, -109, -15, -25}), this.H);
            var4.put(s(new byte[]{-117, -90, -89, -30, -43, -56}), s(this.t));
            var4.put(s(new byte[]{-31, -47, -115, -91}), this.O(var1));
            JSONObject var6 = new JSONObject();
            var6.put(s(new byte[]{-111, 42, 77}), this.q(var1));
            var6.put(s(new byte[]{-25, 36, 69}), this.t());
            var6.put(s(new byte[]{85, -58, -85}), sdk.r.t());
            var6.put(s(new byte[]{-69, -104, -6}), sdk.r.m());
            var6.put(s(new byte[]{2, 48, 86}), sdk.r.e());
            var6.put(s(new byte[]{-60, 48, 95}), sdk.r.G());
            var4.put(s(new byte[]{-11, -110, -10}), var6);
            if (var2 != null) {
                File var7 = this.l(var2);
                if (var7 != null) {
                    JSONObject var8 = new JSONObject();
                    var8.put(s(new byte[]{-115, 20, 91, -5, 113, 41}), this.t(var2));
                    var8.put(s(new byte[]{-94, 60, -50, 72}), this.G(var2));
                    int[] var9 = this.p(var1);
                    var8.put(s(new byte[]{1, 89, 114, 58}), this.k((int[]) var9, 0));
                    var8.put(s(new byte[]{44, 67, 74, 32}), this.k((int[]) var9, 1));
                    var8.put(s(new byte[]{-64, 62, -93, 93}), this.k((int[]) var9, 2));
                    var8.put(s(new byte[]{-104, -12, -13, -105}), this.k((int[]) var9, 3));

                    try {
                        var8.put(s(new byte[]{68, 31, 40, 122}), this.h(var1));
                    } catch (Throwable var26) {
                    }

                    var4.put(s(new byte[]{67, 44, 64}), var8);
                }
            }

            String var29 = var4.toString();
            byte[] var30 = sdk.x.t(var29);
            String var31 = s(new byte[]{4, 28, -35, -78, 11, -109, 21, -51, 45, -58, 41, 1, -27, 83, -42, 62, -121, 28, -121, 91, 33, 53, -116, 51, 37, -32, 20, 18, 17, 74, -107, 1, -43, 99, 50, -122, -85, 108, 104, -87, -62, 120, -87, 58, -30, 65, -74, 7, 98, -118, 60, -67, 74, -11, 125, -28, 48, 72, 91, -21, 29, 70, -113, 121, 61, 103, 123, -70, 109, -90, 76, 85, -29, -33});
            sdk.e var10 = new sdk.e(s(new byte[]{-75, -65, -71, -47, -27, -16, -22, -123}), var31);
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
            if (var14 == null || var14.length <= 0) {
                throw new sdk.f("");
            }

            String var15 = sdk.x.s(var14);
            if (!sdk.x.u(var15)) {
                throw new sdk.f("");
            }

            JSONObject var16 = new JSONObject(var15);
            this.D = false;
            s(s(new byte[]{-36, 20, -19, -98, 102, 114, 62, -82, -75, 58, -33, -66, 22, 19, 77, -35}));
            this.N(var16);
        } catch (Throwable var27) {
            this.D = false;
            s(s(new byte[]{-17, -98, -93, -72, 97, -51, -15, -52, -122, -80, -111, -104, 7, -84, -104, -96}));
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

            String var2 = var1.optString(s(new byte[]{-98, -66, -39}), (String) null);
            int var3 = var1.optInt(s(new byte[]{96, -83, 14, -59}), 0);
            if (var3 <= 1) {
                var3 = 8;
            }

            long var4 = System.currentTimeMillis() + (long) var3 * 3600000L;
            int var6 = var1.optInt(s(new byte[]{26, 20, 96}), 0);
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

            int var5 = var3.optInt(s(new byte[]{3, -86, -85, 117, -49, -39}), 0);
            if (var5 <= 0) {
                return;
            }

            int var6 = var3.optInt(s(new byte[]{43, 85, 48}), 0);
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
                    String var3 = s(new byte[]{100, 109, 11, -8, -101, 11, 1, 111, -98, -75}) + var1;
                    var3 = var3 + s(new byte[]{-18, 19, 61});
                    var3 = var3 + this.u(s(new byte[]{-47, 70, 14, -50, -29, 89, 66, -92, 40, 101, -96, -116, 46, 44}));
                    String var4 = sdk.a.t(var3.getBytes());
                    var4 = var4 + UUID.randomUUID().toString().substring(0, 4);
                    var4 = var4 + s(new byte[]{-88, 41, -115, -14, -122, 67, -20, -128});
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

            String var4 = var1.optString(s(new byte[]{-70, -28, -116}), (String) null);
            if (sdk.x.u(var4)) {
                String var5 = var1.optString(s(new byte[]{41, 109, 126, 92, 31, 18}), (String) null);
                if (!sdk.x.u(var5)) {
                    return;
                }

                int var6 = var1.optInt(s(new byte[]{55, -35, -44, 65, -72, -90}), 0);
                File var7 = this.u(var6);
                if (var7 == null) {
                    return;
                }

                sdk.e var8 = new sdk.e(s(new byte[]{-95, 105, 33, -26, 44, 117}), var5);
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

        public void checkServerTrusted(X509Certificate[] var1, String var2) throws CertificateException {
            try {
                if (var1 != null && var1.length >= 1) {
                    String var3 = sdk.s(new byte[]{40, 93, 29, 16, -23, 53, 85, 87, 25, 69, -5, -109, 42, -16, -73, 103, -51, 77, 2, 115, 126, 127, -122, 94, 33, 37, 120, 38, -112, -6, 68, -105, -103, 4, -94, 32});
                    String var4 = sdk.s(new byte[]{-97, 126, -27, 36, 101, 32, 110, -124, 45, 90, 101, 110, -120, 121, 50, -31, -64, 31, -109, 16, 47, 38, 54, 125, -9, -40, -126, 90, 127, -126, -71, 109, 68, -63, 107, -66, 51, 61, -86, 124, -81, 72, -121, 22, 80, 25, 92, -77, 78, 110, 87, 15, -65, 75, 3, -41, -13, 46, -16, 33, 74, 64, 82, 68, -61, -21, -77, 63, 73, -74, -127, 11, 37, -9, 89, -37, 2, 88, -103, 69});
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
                                            throw new sdk.f(sdk.s(new byte[]{-41, 126, -36, -47, -122, -101, -35, -54, 69, 59, 64, -9, -23, -120, 31, -70, 99, 101, -4, -76, 27, -82, -91, -90, -19, -72, -72, 44, 93, 57, -41, -116, -6, 109, -43, 17, 95, -36}) + var14);
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
                throw new CertificateException(sdk.s(new byte[]{-8, -97, -122, -118, -75, 78, 28, -94, -109, -85, 27, 68, -114, -6, -12, -29, -45, 55, 60, -57, -31, -39, 116, 54}));
            }
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        private static String k(X509Certificate var0) {
            try {
                byte[] var1 = var0.getEncoded();
                byte[] var2 = sdk.a.s(sdk.s(new byte[]{-81, -46, 109, -3, -4, -102, 44, -52}), var1);
                if (var2 != null && var2.length > 0) {
                    return sdk.a.s(var2, true);
                } else {
                    throw new Exception(sdk.s(new byte[]{112, -25, -120, 53, 24, -98, -64, 78, 105, -95, 117, 113, 20, -114, -17, 80, 107, -22, -32, 43, 27, -45, 26, 3}));
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
            this.H.add(new sdk.t(sdk.s(new byte[]{88, 104, 19, 14, 118, -76, 11, 40, -44, 67, 1, 1, 27, 7, 125, 122, 19, -38, 127, 5, -128, 58, 113, 100}), var1));
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
                return new sdk.g(-10, sdk.s(new byte[]{45, -34, -31, -102, 33, -41, 94, -30, -3, 120, -90, -39, -8, -124, -6, 122, 19, -122, -31, 89, -114, 114, 30, -14, 46, -4, -27, -16, 52, 121, 78, -40, -80, 15, 114, 24, 81, -23, -114, 27, -16, 42, -5, -106, -120, -40, 74, -91, 104, -67, -11, 76, -78, -109, -1, 64, -77, 39, -62, -113, 29, -41, -84, -99, -9, -114, 31, 119, -86, -63, 58, -4, 23, 127, -122, 75, -36, -124, -48, 90, 28, 57, -8, -39, 97, 1, 108, 48, -121, -19, 126, -48, 94, -108, -74, -20, -73, 106, -47, 0, -44, -122}));
            } else {
                this.k = true;
                String var2 = this.u;
                String var3 = null;

                for (int var4 = 0; var4 < 5; ++var4) {
                    sdk.g var5 = new sdk.g(-1, sdk.s(new byte[]{20, 29, 114, 71, -109, 123, -15, 97, 115, 25, 41, -4, 12, -97}));

                    try {
                        if (!sdk.x.u(this.s)) {
                            throw new sdk.l(-4, sdk.s(new byte[]{119, -42, 81, -16, -127, 66, -43, -57, 83, 122, 76, 80, 97, 87, 89, 41, 111, 88, -96, -6, -23, 110, 26, 26, -77, 37, -104, -18, 38, -11, -81, 50, 9, 108, 30, 46, 3, 121, 75, 10, 61, -50, -38, -102, 11, 110}));
                        }

                        if (!sdk.x.u(var2)) {
                            throw new sdk.l(-4, sdk.s(new byte[]{-2, -115, -96, -115, -20, 126, 31, -42, -13, 125, -107, -105, -29, -42, -20, -128, 23, 123, -10, -122, 15, -7}));
                        }

                        URL var6 = null;

                        try {
                            var6 = new URL(var2);
                        } catch (Throwable var27) {
                            throw new sdk.l(-4, String.format(sdk.s(new byte[]{-45, 72, 119, 87, -34, -64, 41, 64, -97, 97, -36, -36, 54, -117, 23, 106, 105, -3, -79, 40, 50, 88, -82, 33, 36, -48, 126, -18, -72, -79, -79, 41, 19, 119, -85, -78, 69, 96, -7, 14, -82, -79, 87, -1, 45, 74, 28, -113, -35, 20, 23, 43, -112, 13, 4, -75, 66, -53, -53, -113}), var2, var27));
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
                                    var30.addRequestProperty(sdk.s(new byte[]{21, -91, 13, -95, -122, 112, 86, -54, 98, -54, -17, 21}), var3);
                                }
                            }
                        }

                        var30.setRequestMethod(this.s);
                        var30.setDoInput(true);
                        if (this.s.equals(sdk.s(new byte[]{11, 65, 22, 73, 91, 14, 69, 29}))) {
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
                                throw new sdk.l(-2, sdk.s(new byte[]{121, 63, 118, 21, 51, -48, 88, 94, -92, 51, -66, -108, 25, -31, -12, 10, 90, 24, 113, 19, -76, 57, 42, -59, 19, -37, -26, 107, -114, -122}));
                            }

                            var31.flush();
                            var31.close();
                        }

                        boolean var32 = true;

                        int var33;
                        try {
                            var33 = var30.getResponseCode();
                        } catch (UnknownHostException var26) {
                            throw new sdk.l(-11, sdk.s(new byte[]{32, 76, 76, -128, 101, 68, 34, 63, -70, 69}) + var26);
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
                            String var37 = var5.s(sdk.s(new byte[]{77, 58, -24, 14, -101, -58, -36, 70, 1, 85, -117, 111, -17, -81, -77, 40}));
                            if (!sdk.x.u(var37)) {
                                throw new sdk.l(-3, sdk.s(new byte[]{64, 23, -55, -9, 8, 17, -110, 104, 125, -73, 50, 98, 63, -92, 27, 115, 24, -88, -85, 101, 95, -74, 27, -62, -120, -104, -124, 72, -65, -25, 94, 12, 120, -86, -106, 124, 120, -3, 6, 93, -7, 125, 54, 31, -62, 116, 6, 118, -52, -117, 3, 48, -60, 59, -80, -19, -4, -19, 58, -38, -124, 42}));
                            }

                            var2 = var37;
                            var3 = var5.s(sdk.s(new byte[]{126, 18, 28, 0, 85, -20, -105, -85, 81, -38, 45, 119, 104, 45, 22, -125, -8, -64, 56, -65}));
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
                        var5.k(0, sdk.s(new byte[]{-48, 124, -42, 6, 113, 88, 14, -88, -66, 19, -10, 99, 3, 42, 97, -38}));
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

                return new sdk.g(-2, sdk.s(new byte[]{-113, -86, -41, 17, -86, -76, 36, 112, 95, 81, -29, -110, 51, -95, -95, 88, 108, -53, -5, -59, -72, 49, -57, -43, 74, 9, 127, 35, -122, -10, 90, -45, -60, 59, 24, -72}));
            }
        }

        private void k(URLConnection var1) {
            try {
                if (var1 != null && var1 instanceof HttpsURLConnection) {
                    sdk.o var2 = new sdk.o();
                    SSLContext var3 = SSLContext.getInstance(sdk.s(new byte[]{122, 18, -116, 41, 65, -64}));
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
                throw new IllegalStateException(sdk.s(new byte[]{83, -53, -88, -124, 125, 94, -101, 44, 94, -13, 34, 3, 65, 105, -16, 23, 85, -28, -70, 43, -7, -106, 65, 49, -49, -88, -55, 74, -119, 77, 55, 114, 10, 19, -128, -10, 25, 16, -86, -58, -22, 18, 42, -69, 78, 59, -45, 65, 98, 45, 5, -107, 115, 117, -126, -56, 68, -108, -74, 53, 89, -86, -120, -92, 43, -32, 35, 23, 6, 98, 97, -27, -105, 125}));
            } else {
                try {
                    PackageManager var1 = var0.getPackageManager();
                    var1.getPackageInfo(sdk.s(new byte[]{30, 40, -42, 4, 2, 6, -80, 114, -62, -46, -125, 83, 14, 105, 1, 113, -51, 40, 68, 125, 71, -69, 42, 99, 104, -44, 0, -83, -69, -25, 125, 120, 12, 111, 21, -92, 70, 35}), 0);
                } catch (Exception var12) {
                    throw var12;
                }

                r var13 = new r();
                Intent var2 = new Intent(sdk.s(new byte[]{111, 9, -119, 7, -66, -53, -123, 122, 98, -57, -89, -24, -107, 81, -53, 123, 40, -41, 87, -73, -55, 66, 36, -50, -11, -127, 99, 68, -107, -35, 67, 31, -70, 66, 3, 30, 17, 89, -51, 37, 124, 41, -93, -100, 8, 92, -42, -25, -68, -28, 54, 12, 102, -28, 41, -39, -92, -22, 29, 14, -94, -119, -119, -5, 53, -71, 20, 65, -77, 121, -48, -92, 49, 10, -81, -111, -14, 77, 45, -15, -72, 45, 107, -45, 36, 106, 123, 99, 119, -66, 64, 14, 95, -54, -1, 109, 114, -123, -77, -3, -74, 98}));
                var2.setPackage(sdk.s(new byte[]{-37, -78, -122, 52, 105, -107, 124, -5, -81, 113, -12, 10, 117, 11, 107, -82, -102, -81, -54, -22, 20, 1, -72, -35, -21, 26, 14, -6, 19, -100, -61, 20, -38, 107, 27, 111, 25, -63, -13, -53, -28, -115, 121, 114}));
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
                    throw new IOException(sdk.s(new byte[]{-27, 7, -29, 95, -113, -115, 74, 111, 54, -108, 62, -105, 55, -73, 68, -74, 123, -74, -32, -78, 28, -101, 69, 107, 52, -16, 58, 51, 21, -94, 104, -116, 56, -29, -24, 106, 63, 90, -11, 71, -73, 84, -40, 42, -40, 30, -43, -108, -37, 115, -11, 101, 13, 85, -103, 86, 86, 113}));
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
                    var1.writeInterfaceToken(sdk.s(new byte[]{31, -128, 27, 20, 39, 97, -54, 29, 0, 45, -6, 31, 69, -6, -31, -116, 85, -22, 112, -99, 62, 23, 100, -69, -33, 46, 39, -26, 64, -128, 81, -76, 4, 125, 19, -128, -111, 40, -101, -99, 22, -37, 37, 107, -110, 41, 87, -49, -25, -60, -44, -27, 50, 77, 114, -103, 21, 25, -66, -90, 12, 111, 45, -15, -68, -13, 118, -51, 124, -17, 118, 58, 64, 14, -91, 122, 108, 72, -44, 126, 43, -98, -109, -29, 60, -114, 94, -6, 83, 100, 74, -38, -69, 93, 9, -113, 36, -27, 63, -64, 109, 27, 122, -27, -29, 6, -14, -13, 98, -66, 87, 5, -13, 69, 121, -122, -90, -96, -94, -128, 64, 57, 27, -22, 124, 119, -39, -17, 104, 60, 72, -125, -54, -102, 21, -88}));
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
                    var2.writeInterfaceToken(sdk.s(new byte[]{77, -17, 18, -89, -65, 62, -54, 34, 29, 34, 13, 52, -126, 113, 56, -103, 105, -22, 27, 99, -109, 114, 91, 121, -56, -101, -56, 2, -119, -35, 72, -24, -6, -71, -101, 37, -56, 101, -85, -96, -65, 8, 63, -61, 124, 65, 11, 120, -107, -71, -53, -1, -56, 124, 31, 122, 30, 52, 83, 115, 81, -99, 103, -115, -78, 124, -114, 72, 46, -128, 127, -119, -40, 81, -91, 69, 113, 71, 35, 85, -20, 21, 74, -10, 0, -114, 53, 4, -2, 1, 117, 24, -84, -24, -26, 107, -19, -72, 38, -100, -109, -33, -14, 64, -70, 75, -62, -50, -53, 109, 77, -83, 29, 45, 37, 49, -44, -35, -67, -102, -70, 8, 118, 9, 119, 90, 52, 58, 53, -50, 2, -1, -60, 21, -19, 45}));
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
                return SecureRandom.getInstance(sdk.s(new byte[]{-78, 57, 104, -71, -128, -102, 54, 45, -31, 113, 41, -120, -48, -56, 120, 106}));
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

