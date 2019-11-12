package com.jumpraw.gcmob.core;


import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class b {
    private static Map<Character, Character> b;
    private static Map<Character, Character> c;
    private static Map<Character, Character> d;
    private static Map<Character, Character> e;
    private static char[] j;
    private static byte[] k;

    private b() {
    }

    private static String a(byte[] var0) {
        StringBuffer var1 = new StringBuffer();
        int var2 = var0.length;
        int var3 = 0;

        while(var3 < var2) {
            int var4 = var0[var3++] & 255;
            if (var3 == var2) {
                var1.append(j[var4 >>> 2]);
                var1.append(j[(var4 & 3) << 4]);
                var1.append("==");
                break;
            }

            int var5 = var0[var3++] & 255;
            if (var3 == var2) {
                var1.append(j[var4 >>> 2]);
                var1.append(j[(var4 & 3) << 4 | (var5 & 240) >>> 4]);
                var1.append(j[(var5 & 15) << 2]);
                var1.append("=");
                break;
            }

            int var6 = var0[var3++] & 255;
            var1.append(j[var4 >>> 2]);
            var1.append(j[(var4 & 3) << 4 | (var5 & 240) >>> 4]);
            var1.append(j[(var5 & 15) << 2 | (var6 & 192) >>> 6]);
            var1.append(j[var6 & 63]);
        }

        return var1.toString();
    }

    private static byte[] d(String var0) {
        byte[] var7;
        int var1 = (var7 = var0.getBytes()).length;
        ByteArrayOutputStream var2 = new ByteArrayOutputStream(var1);
        int var3 = 0;

        while(var3 < var1) {
            byte var4;
            do {
                var4 = k[var7[var3++]];
            } while(var3 < var1 && var4 == -1);

            if (var4 == -1) {
                break;
            }

            byte var5;
            do {
                var5 = k[var7[var3++]];
            } while(var3 < var1 && var5 == -1);

            if (var5 == -1) {
                break;
            }

            var2.write(var4 << 2 | (var5 & 48) >>> 4);

            do {
                if ((var4 = var7[var3++]) == 61) {
                    return var2.toByteArray();
                }

                var4 = k[var4];
            } while(var3 < var1 && var4 == -1);

            if (var4 == -1) {
                break;
            }

            var2.write((var5 & 15) << 4 | (var4 & 60) >>> 2);

            do {
                if ((var5 = var7[var3++]) == 61) {
                    return var2.toByteArray();
                }

                var5 = k[var5];
            } while(var3 < var1 && var5 == -1);

            if (var5 == -1) {
                break;
            }

            var2.write((var4 & 3) << 6 | var5);
        }

        var7 = var2.toByteArray();

        try {
            var2.close();
        } catch (Exception var6) {
        }

        return var7;
    }

    public static String a(String var0) {
        String var1 = "";

        try {
            char[] var7;
            if (!TextUtils.isEmpty(var0) && (var7 = a(var0.getBytes()).toCharArray()) != null && var7.length > 0) {
                char[] var2 = new char[var7.length];

                for(int var3 = 0; var3 < var7.length; ++var3) {
                    char var4 = var7[var3];
                    if (b == null) {
                        b = new HashMap();

                        for(int var5 = 0; var5 < 64; ++var5) {
                            b.put("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(var5), "K5fjSqGuT7R8x3BFkEr0JI92zYdMWnhvLNObeymlpZVciPHXa6Qw1goAstC4UD+/".charAt(var5));
                        }
                    }

                    var2[var3] = b.containsKey(var4) ? (Character)b.get(var4) : var4;
                }

                var1 = new String(var2);
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return var1;
    }

    public static String b(String var0) {
        String var1 = "";

        try {
            if (!TextUtils.isEmpty(var0)) {
                char[] var7;
                if ((var7 = var0.toCharArray()) != null && var7.length > 0) {
                    char[] var2 = new char[var7.length];
                    int var3 = 0;

                    while(true) {
                        if (var3 >= var7.length) {
                            var1 = new String(var2);
                            break;
                        }

                        char var4 = var7[var3];
                        if (e == null) {
                            e = new HashMap();

                            for(int var5 = 0; var5 < 64; ++var5) {
                                e.put("T7R8x3BFkEr0JIvLNO92zYdMWnhbeyK5fjSqGumlpZVciPHAstC4UXa6QDw1go+/".charAt(var5), "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(var5));
                            }
                        }

                        var2[var3] = e.containsKey(var4) ? (Character)e.get(var4) : var4;
                        ++var3;
                    }
                }

                var1 = new String(d(var1));
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return var1;
    }

    public static String c(String var0) {
        String var1 = "";

        try {
            char[] var7;
            if (!TextUtils.isEmpty(var0) && (var7 = a(var0.getBytes()).toCharArray()) != null && var7.length > 0) {
                char[] var2 = new char[var7.length];

                for(int var3 = 0; var3 < var7.length; ++var3) {
                    char var4 = var7[var3];
                    if (d == null) {
                        d = new HashMap();

                        for(int var5 = 0; var5 < 64; ++var5) {
                            d.put("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(var5), "T7R8x3BFkEr0JIvLNO92zYdMWnhbeyK5fjSqGumlpZVciPHAstC4UXa6QDw1go+/".charAt(var5));
                        }
                    }

                    var2[var3] = d.containsKey(var4) ? (Character)d.get(var4) : var4;
                }

                var1 = new String(var2);
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return var1;
    }

    static {
        b.class.getSimpleName();
        b = null;
        c = null;
        d = null;
        e = null;
        j = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
        k = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1};
        e = new HashMap();

        int var0;
        for(var0 = 0; var0 < 64; ++var0) {
            e.put("T7R8x3BFkEr0JIvLNO92zYdMWnhbeyK5fjSqGumlpZVciPHAstC4UXa6QDw1go+/".charAt(var0), "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(var0));
        }

        d = new HashMap();

        for(var0 = 0; var0 < 64; ++var0) {
            d.put("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(var0), "T7R8x3BFkEr0JIvLNO92zYdMWnhbeyK5fjSqGumlpZVciPHAstC4UXa6QDw1go+/".charAt(var0));
        }

        b = new HashMap();

        for(var0 = 0; var0 < 64; ++var0) {
            b.put("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(var0), "K5fjSqGuT7R8x3BFkEr0JI92zYdMWnhvLNObeymlpZVciPHXa6Qw1goAstC4UD+/".charAt(var0));
        }

        c = new HashMap();

        for(var0 = 0; var0 < 64; ++var0) {
            c.put("K5fjSqGuT7R8x3BFkEr0JI92zYdMWnhvLNObeymlpZVciPHXa6Qw1goAstC4UD+/".charAt(var0), "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(var0));
        }

    }
}

