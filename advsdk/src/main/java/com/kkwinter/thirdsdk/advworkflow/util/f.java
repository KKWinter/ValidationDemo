package com.kkwinter.thirdsdk.advworkflow.util;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class f {
    static String a = "ARCFOUR";
    static String b = "kfjveAonUOsyDGrr";

    public static String a(String paramString) {
        try {
            Cipher localCipher = Cipher.getInstance(a);
            localCipher.init(2, new SecretKeySpec(b.getBytes(), a));
            localCipher.init(2, new SecretKeySpec(localCipher.doFinal(Base64.decode("HL6NeInWcRfudnvOdisXNg==", 0)), a));
            paramString = new String(localCipher.doFinal(Base64.decode(paramString, 0)));
            return paramString;
        } catch (Exception e) {

        }
        return "";
    }
}
