package com.jumpraw.gcmob.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class a {
    private static int a(char var0) {
        if (var0 >= 'A' && var0 <= 'Z') {
            return var0 - 65;
        } else if (var0 >= 'a' && var0 <= 'z') {
            return var0 - 97 + 26;
        } else if (var0 >= '0' && var0 <= '9') {
            return var0 - 48 + 26 + 26;
        } else {
            switch(var0) {
                case '+':
                    return 62;
                case '/':
                    return 63;
                case '=':
                    return 0;
                default:
                    throw new RuntimeException("unexpected code: " + var0);
            }
        }
    }

    public static byte[] a(String var0) {
        ByteArrayOutputStream var1 = new ByteArrayOutputStream();

        try {
            a(var0, var1);
        } catch (IOException var3) {
            throw new RuntimeException();
        }

        byte[] var4 = var1.toByteArray();

        try {
            var1.close();
        } catch (IOException var2) {
            System.err.println("Error while decoding BASE64: " + var2.toString());
        }

        return var4;
    }

    private static void a(String var0, OutputStream var1) throws IOException {
        int var2 = 0;
        int var3 = var0.length();

        while(true) {
            while(var2 < var3 && var0.charAt(var2) <= ' ') {
                ++var2;
            }

            if (var2 == var3) {
                break;
            }

            int var4 = (a(var0.charAt(var2)) << 18) + (a(var0.charAt(var2 + 1)) << 12) + (a(var0.charAt(var2 + 2)) << 6) + a(var0.charAt(var2 + 3));
            var1.write(var4 >> 16 & 255);
            if (var0.charAt(var2 + 2) == '=') {
                break;
            }

            var1.write(var4 >> 8 & 255);
            if (var0.charAt(var2 + 3) == '=') {
                break;
            }

            var1.write(var4 & 255);
            var2 += 4;
        }

    }

    static {
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    }
}
