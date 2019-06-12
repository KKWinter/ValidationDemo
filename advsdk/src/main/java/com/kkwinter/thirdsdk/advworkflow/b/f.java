package com.kkwinter.thirdsdk.advworkflow.b;


import android.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class f {
    static String a = "ARCFOUR";
    static String b = "cJLwQFdsIy2DLb2e";

    public static String a(String var0) {
        try {
            Cipher var1 = Cipher.getInstance(a);
            var1.init(2, new SecretKeySpec(b.getBytes(), a));
            var1.init(2, new SecretKeySpec(var1.doFinal(Base64.decode("7/801vznkaA7GG3Wp9/PPw==", 0)), a));
            var0 = new String(var1.doFinal(Base64.decode(var0, 0)));
            return var0;
        } catch (Exception var2) {
            return "";
        }
    }
}
