package com.kkwinter.thirdsdk.advsdk.b;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 工具类
 */
public final class e {
//    public static final String a = com.kkwinter.thirdsdk.advsdk.b.e.class.getSimpleName();

    public e() {
    }

    static byte[] a(String var0) {
        try {
            return var0.getBytes("UTF-8");
        } catch (UnsupportedEncodingException var1) {
            return var0.getBytes();
        }
    }

    public static class a {
        private static final char[] a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        private static final char[] b = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        public static String a(String var0) {
            byte[] var1 = com.kkwinter.thirdsdk.advsdk.b.e.a(var0);
            return new String(a(b("MD5").digest(var1), a));
        }

        private static MessageDigest b(String var0) {
            try {
                return MessageDigest.getInstance(var0);
            } catch (NoSuchAlgorithmException var1) {
                throw new IllegalArgumentException(var1);
            }
        }

        private static char[] a(byte[] var0, char[] var1) {
            int var2;
            char[] var3 = new char[(var2 = var0.length) << 1];
            int var4 = 0;

            for(int var5 = 0; var4 < var2; ++var4) {
                var3[var5++] = var1[(240 & var0[var4]) >>> 4];
                var3[var5++] = var1[15 & var0[var4]];
            }

            return var3;
        }
    }
}

