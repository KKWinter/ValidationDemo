package com.jumpraw.gcmob.core;




import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class c {
    private static char[] c = "0123456789ABCDEF".toCharArray();

    public static String a(String var0) {
        try {
            MessageDigest var1;
            (var1 = MessageDigest.getInstance("MD5")).reset();
            var1.update(var0.getBytes());
            byte[] var6 = var1.digest();
            StringBuilder var7 = new StringBuilder(var6.length << 1);
            int var2 = (var6 = var6).length;

            for(int var3 = 0; var3 < var2; ++var3) {
                byte var4 = var6[var3];
                var7.append(Integer.toHexString((var4 & 240) >>> 4));
                var7.append(Integer.toHexString(var4 & 15));
            }

            return var7.toString().toLowerCase(Locale.US);
        } catch (NoSuchAlgorithmException var5) {
            var5.printStackTrace();
            return "";
        }
    }

    public static String b(String var0) {
        try {
            MessageDigest var1;
            (var1 = MessageDigest.getInstance("MD5")).reset();
            var1.update(var0.getBytes());
            byte[] var6 = var1.digest();
            StringBuilder var7 = new StringBuilder(var6.length << 1);
            int var2 = (var6 = var6).length;

            for(int var3 = 0; var3 < var2; ++var3) {
                byte var4 = var6[var3];
                var7.append(Integer.toHexString((var4 & 240) >>> 4));
                var7.append(Integer.toHexString(var4 & 15));
            }

            return var7.toString().toUpperCase(Locale.US);
        } catch (NoSuchAlgorithmException var5) {
            var5.printStackTrace();
            return "";
        }
    }

    private static String a(String var0, String var1) {
        try {
            byte[] var5 = a.a(var0);
            IvParameterSpec var2 = new IvParameterSpec("1269571569321021".getBytes());
            SecretKeySpec var6 = new SecretKeySpec(var1.getBytes(), "AES");
            Cipher var3;
            (var3 = Cipher.getInstance("AES/CBC/PKCS5Padding")).init(2, var6, var2);
            var5 = var3.doFinal(var5);
            return new String(var5, "utf-8");
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static String c(String var0) {
        return a(var0, "BijI04JVhi8LjdGX");
    }

    public static String d(String var0) {
        try {
            MessageDigest var1;
            (var1 = MessageDigest.getInstance("MD5")).update(var0.getBytes());
            byte[] var4 = var1.digest();
            StringBuilder var5 = new StringBuilder(var4.length << 1);

            for(int var2 = 0; var2 < var4.length; ++var2) {
                var5.append(c[(var4[var2] & 240) >>> 4]);
                var5.append(c[var4[var2] & 15]);
            }

            return var5.toString().toLowerCase();
        } catch (Exception var3) {
            return "";
        }
    }
}

