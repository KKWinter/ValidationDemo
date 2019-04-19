package com.kkwinter.thirdsdk.advsdk.b;

import android.content.Context;
import android.telephony.TelephonyManager;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * telIMEI/telIMSI 工具类
 */
public final class w {


    public static com.kkwinter.thirdsdk.advsdk.b.w.a a(Context var0) {
        com.kkwinter.thirdsdk.advsdk.b.w.a var1 = new com.kkwinter.thirdsdk.advsdk.b.w.a();

        try {
            Class var2;
            Field var3;
            (var3 = (var2 = Class.forName("com.android.internal.telephony.Phone")).getField("GEMINI_SIM_1")).setAccessible(true);
            int var11 = (Integer)var3.get((Object)null);
            Field var9;
            (var9 = var2.getField("GEMINI_SIM_2")).setAccessible(true);
            int var10 = (Integer)var9.get((Object)null);
            TelephonyManager var7 = (TelephonyManager)var0.getSystemService(Context.TELEPHONY_SERVICE);
            Method var4;
            String var5 = (String)(var4 = TelephonyManager.class.getDeclaredMethod("getSubscriberIdGemini", Integer.TYPE)).invoke(var7, var11);
            String var12 = (String)var4.invoke(var7, var10);
            var1.a = var5;
            var1.b = var12;
            var5 = (String)(var4 = TelephonyManager.class.getDeclaredMethod("getDeviceIdGemini", Integer.TYPE)).invoke(var7, var11);
            var12 = (String)var4.invoke(var7, var10);
            var1.c = var5;
            var1.d = var12;
            var11 = (Integer)(var4 = TelephonyManager.class.getDeclaredMethod("getPhoneTypeGemini", Integer.TYPE)).invoke(var7, var11);
            int var8 = (Integer)var4.invoke(var7, var10);
            var1.e = var11;
            var1.f = var8;
        } catch (Exception var6) {
            return null;
        }

        return var1.c != null && var1.c.length() != 0 || var1.d != null && var1.d.length() != 0 ? var1 : null;
    }

    public static com.kkwinter.thirdsdk.advsdk.b.w.a b(Context var0) {
        com.kkwinter.thirdsdk.advsdk.b.w.a var1 = new com.kkwinter.thirdsdk.advsdk.b.w.a();

        try {
            TelephonyManager var6 = (TelephonyManager)var0.getSystemService(Context.TELEPHONY_SERVICE);
            Class var2;
            Field var3;
            (var3 = (var2 = Class.forName("com.android.internal.telephony.Phone")).getField("GEMINI_SIM_1")).setAccessible(true);
            int var11 = (Integer)var3.get((Object)null);
            Field var8;
            (var8 = var2.getField("GEMINI_SIM_2")).setAccessible(true);
            int var9 = (Integer)var8.get((Object)null);
            Method var4;
            TelephonyManager var12 = (TelephonyManager)(var4 = TelephonyManager.class.getMethod("getDefault", Integer.TYPE)).invoke(var6, var11);
            var6 = (TelephonyManager)var4.invoke(var6, var9);
            String var10 = var12.getSubscriberId();
            String var13 = var6.getSubscriberId();
            var1.a = var10;
            var1.b = var13;
            var10 = var12.getDeviceId();
            var13 = var6.getDeviceId();
            var1.c = var10;
            var1.d = var13;
            var9 = var12.getPhoneType();
            int var7 = var6.getPhoneType();
            var1.e = var9;
            var1.f = var7;
        } catch (Exception var5) {
            return null;
        }

        return var1.c != null && var1.c.length() != 0 || var1.d != null && var1.d.length() != 0 ? var1 : null;
    }

    public static com.kkwinter.thirdsdk.advsdk.b.w.a c(Context var0) {
        com.kkwinter.thirdsdk.advsdk.b.w.a var1 = new com.kkwinter.thirdsdk.advsdk.b.w.a();

        try {
            TelephonyManager var2 = (TelephonyManager)var0.getSystemService(Context.TELEPHONY_SERVICE);
            Class var3 = Class.forName("android.telephony.MSimTelephonyManager");
                Object var7 = var0.getSystemService("phone_msim");
            Method var4;
            String var5 = (String)(var4 = var3.getMethod("getSubscriberId", Integer.TYPE)).invoke(var7, 0);
            String var11 = (String)var4.invoke(var7, 1);
            var1.a = var5;
            var1.b = var11;
            var5 = (String)(var4 = var3.getMethod("getDeviceId", Integer.TYPE)).invoke(var7, 0);
            var11 = (String)var4.invoke(var7, 1);
            var1.c = var5;
            var1.d = var11;
            Method var10 = var3.getMethod("getDataState");
            int var9 = var2.getDataState();
            int var8 = (Integer)var10.invoke(var7);
            var1.e = var9;
            var1.f = var8;
        } catch (Exception var6) {
            return null;
        }

        return var1.c != null && var1.c.length() != 0 || var1.d != null && var1.d.length() != 0 ? var1 : null;
    }

    public static com.kkwinter.thirdsdk.advsdk.b.w.a d(Context var0) {
        com.kkwinter.thirdsdk.advsdk.b.w.a var1 = new com.kkwinter.thirdsdk.advsdk.b.w.a();

        try {
            TelephonyManager var2;
            String var3 = (var2 = (TelephonyManager)var0.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
            String var4 = var2.getDeviceId();
            int var8 = var2.getPhoneType();
            var1.a = var3;
            var1.c = var4;
            var1.e = var8;
            Class var9;
            Method var11;
            (var11 = (var9 = Class.forName("com.android.internal.telephony.PhoneFactory")).getMethod("getServiceName", String.class, Integer.TYPE)).setAccessible(true);
            String var10 = (String)var11.invoke(var9, "phone", 1);
            TelephonyManager var6;
            var10 = (var6 = (TelephonyManager)var0.getSystemService(var10)).getSubscriberId();
            var3 = var6.getDeviceId();
            int var7 = var6.getPhoneType();
            var1.b = var10;
            var1.d = var3;
            var1.f = var7;
        } catch (Exception var5) {
            return null;
        }

        return var1.c != null && var1.c.length() != 0 || var1.d != null && var1.d.length() != 0 ? var1 : null;
    }

    public static class a {
        public String a = "";
        public String b = "";
        public String c = "";
        public String d = "";
        public int e = 0;
        public int f = 0;

        public a() {
        }

        public final String toString() {
            return "TeleInfo{imsi_1='" + this.a + '\'' + ", imsi_2='" + this.b + '\'' + ", imei_1='" + this.c + '\'' + ", imei_2='" + this.d + '\'' + ", phoneType_1=" + this.e + ", phoneType_2=" + this.f + '}';
        }
    }
}

