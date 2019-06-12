package com.kkwinter.thirdsdk.advworkflow.util;

import android.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class c {
    public static byte[] a = new byte[]{1, 2, 3, 4, 5, 6, 7, 9};
    public static byte[] b = new byte[]{115, 106, 119, 111, 114, 107, 56, 56};

    public static final String a(String var0, byte[] var1) {
        try {
            IvParameterSpec var2 = new IvParameterSpec(a);
            SecretKeySpec var5 = new SecretKeySpec(var1, "DES");
            Cipher var3 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            var3.init(1, var5, var2);
            var0 = new String(Base64.encode(var3.doFinal(var0.getBytes()), 0));
            return var0;
        } catch (Exception var4) {
            return null;
        }
    }

    public static final String b(String var0, byte[] var1) {
        try {
            byte[] var5 = Base64.decode(var0, 0);
            IvParameterSpec var2 = new IvParameterSpec(a);
            SecretKeySpec var6 = new SecretKeySpec(var1, "DES");
            Cipher var3 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            var3.init(2, var6, var2);
            var0 = new String(var3.doFinal(var5));
            return var0;
        } catch (Exception var4) {
            return null;
        }
    }
}
