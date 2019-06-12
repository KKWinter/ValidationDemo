package com.kkwinter.thirdsdk.advworkflow.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class b {
//    public static final String a = b.class.getSimpleName();

    public b() {
    }

    static byte[] a(String var0) {
        try {
            byte[] var1 = var0.getBytes("UTF-8");
            return var1;
        } catch (UnsupportedEncodingException var2) {
            return var0.getBytes();
        }
    }

    public static final class a {
        private static final char[] a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        private static final char[] b = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        public static String a(String var0) {
            byte[] var1 = com.kkwinter.thirdsdk.advworkflow.util.b.a(var0);
            return new String(a(b("MD5").digest(var1), a));
        }

        private static char[] a(byte[] var0, char[] var1) {
            int var3 = 0;
            int var4 = var0.length;
            char[] var6 = new char[var4 << 1];

            for(int var2 = 0; var2 < var4; ++var2) {
                int var5 = var3 + 1;
                var6[var3] = var1[(var0[var2] & 240) >>> 4];
                var3 = var5 + 1;
                var6[var5] = var1[var0[var2] & 15];
            }

            return var6;
        }

        private static MessageDigest b(String var0) {
            try {
                MessageDigest var2 = MessageDigest.getInstance(var0);
                return var2;
            } catch (NoSuchAlgorithmException var1) {
                throw new IllegalArgumentException(var1);
            }
        }
    }
}
