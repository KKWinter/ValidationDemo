package com.kkwinter.thirdsdk.advsdk.b;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class h {
    static String a = "ARCFOUR";
    static String b = "lIr2qyVSIiyvd8Of";

    public static String a(String var0) {
        String var1 = "m6ahmpvelL4WvWyi4vRFEg==";

        try {
            Cipher var2 = Cipher.getInstance(a);
            SecretKeySpec var3 = new SecretKeySpec(b.getBytes(), a);
            var2.init(2, var3);
            byte[] var6 = Base64.decode(var1, 0);
            var6 = var2.doFinal(var6);
            SecretKeySpec var7 = new SecretKeySpec(var6, a);
            var2.init(2, var7);
            byte[] var5 = Base64.decode(var0, 0);
            var5 = var2.doFinal(var5);
            var0 = new String(var5);
        } catch (Exception var4) {
            var0 = "";
        }

        return var0;
    }
}
