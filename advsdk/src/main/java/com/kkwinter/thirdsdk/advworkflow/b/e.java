package com.kkwinter.thirdsdk.advworkflow.b;

import android.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class e {
    static String a = "ARCFOUR";
    static String b = "qhwVhfjVp4nqrRQx";

    public static String a(String var0) {
        try {
            Cipher var1 = Cipher.getInstance(a);
            var1.init(2, new SecretKeySpec(b.getBytes(), a));
            var1.init(2, new SecretKeySpec(var1.doFinal(Base64.decode("QB1dhPveaxgS1AzrkpDH4w==", 0)), a));
            var0 = new String(var1.doFinal(Base64.decode(var0, 0)));
            return var0;
        } catch (Exception var2) {
            return "";
        }
    }
}
