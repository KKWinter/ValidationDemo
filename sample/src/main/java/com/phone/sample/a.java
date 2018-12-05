package com.phone.sample;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangdong on 2018/11/23.
 * antony.huang@yeahmobi.com
 */
public final class a {
    private static Map<Character, Character> a = null;
    private static Map<Character, Character> b = null;

    public static String a(String var0) {
        String var1 = "";

        try {
            char[] var6;
            if (!TextUtils.isEmpty(var0) && (var6 = com.mintegral.msdk.base.utils.b.a(var0.getBytes()).toCharArray()) != null && var6.length > 0) {
                char[] var2 = new char[var6.length];

                for(int var3 = 0; var3 < var6.length; ++var3) {
                    char var4 = var6[var3];
                    if (a == null) {
                        (a = new HashMap()).put('A', 'u');
                        a.put('B', 'V');
                        a.put('C', 'U');
                        a.put('D', 'o');
                        a.put('E', 'X');
                        a.put('F', 'c');
                        a.put('G', '3');
                        a.put('H', 'p');
                        a.put('I', 'C');
                        a.put('J', 'n');
                        a.put('K', 'D');
                        a.put('L', 'F');
                        a.put('M', 'v');
                        a.put('N', 'b');
                        a.put('O', '8');
                        a.put('P', 'l');
                        a.put('Q', 'N');
                        a.put('R', 'J');
                        a.put('S', 'j');
                        a.put('T', '9');
                        a.put('U', 'Z');
                        a.put('V', 'H');
                        a.put('W', 'E');
                        a.put('X', 'i');
                        a.put('Y', 'a');
                        a.put('Z', '7');
                        a.put('a', 'Q');
                        a.put('b', 'Y');
                        a.put('c', 'r');
                        a.put('d', 'f');
                        a.put('e', 'S');
                        a.put('f', 'm');
                        a.put('g', 'R');
                        a.put('h', 'O');
                        a.put('i', 'k');
                        a.put('j', 'G');
                        a.put('k', 'K');
                        a.put('l', 'A');
                        a.put('m', '0');
                        a.put('n', 'e');
                        a.put('o', 'h');
                        a.put('p', 'I');
                        a.put('q', 'd');
                        a.put('r', 't');
                        a.put('s', 'z');
                        a.put('t', 'B');
                        a.put('u', '6');
                        a.put('v', '4');
                        a.put('w', 'M');
                        a.put('x', 'q');
                        a.put('y', '2');
                        a.put('z', 'g');
                        a.put('0', 'P');
                        a.put('1', '5');
                        a.put('2', 's');
                        a.put('3', 'y');
                        a.put('4', 'T');
                        a.put('5', 'L');
                        a.put('6', '1');
                        a.put('7', 'w');
                        a.put('8', 'W');
                        a.put('9', 'x');
                        a.put('+', '+');
                        a.put('/', '/');
                    }

                    var2[var3] = a.containsKey(var4) ? (Character)a.get(var4) : var4;
                }

                var1 = new String(var2);
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return var1;
    }

    public static String b(String var0) {
        String var1 = "";

        try {
            if (!TextUtils.isEmpty(var0)) {
                char[] var6;
                if ((var6 = var0.toCharArray()) != null && var6.length > 0) {
                    char[] var2 = new char[var6.length];

                    for(int var3 = 0; var3 < var6.length; ++var3) {
                        char var4 = var6[var3];
                        if (b == null) {
                            (b = new HashMap()).put('u', 'A');
                            b.put('V', 'B');
                            b.put('U', 'C');
                            b.put('o', 'D');
                            b.put('X', 'E');
                            b.put('c', 'F');
                            b.put('3', 'G');
                            b.put('p', 'H');
                            b.put('C', 'I');
                            b.put('n', 'J');
                            b.put('D', 'K');
                            b.put('F', 'L');
                            b.put('v', 'M');
                            b.put('b', 'N');
                            b.put('8', 'O');
                            b.put('l', 'P');
                            b.put('N', 'Q');
                            b.put('J', 'R');
                            b.put('j', 'S');
                            b.put('9', 'T');
                            b.put('Z', 'U');
                            b.put('H', 'V');
                            b.put('E', 'W');
                            b.put('i', 'X');
                            b.put('a', 'Y');
                            b.put('7', 'Z');
                            b.put('Q', 'a');
                            b.put('Y', 'b');
                            b.put('r', 'c');
                            b.put('f', 'd');
                            b.put('S', 'e');
                            b.put('m', 'f');
                            b.put('R', 'g');
                            b.put('O', 'h');
                            b.put('k', 'i');
                            b.put('G', 'j');
                            b.put('K', 'k');
                            b.put('A', 'l');
                            b.put('0', 'm');
                            b.put('e', 'n');
                            b.put('h', 'o');
                            b.put('I', 'p');
                            b.put('d', 'q');
                            b.put('t', 'r');
                            b.put('z', 's');
                            b.put('B', 't');
                            b.put('6', 'u');
                            b.put('4', 'v');
                            b.put('M', 'w');
                            b.put('q', 'x');
                            b.put('2', 'y');
                            b.put('g', 'z');
                            b.put('P', '0');
                            b.put('5', '1');
                            b.put('s', '2');
                            b.put('y', '3');
                            b.put('T', '4');
                            b.put('L', '5');
                            b.put('1', '6');
                            b.put('w', '7');
                            b.put('W', '8');
                            b.put('x', '9');
                            b.put('+', '+');
                            b.put('/', '/');
                        }

                        var2[var3] = b.containsKey(var4) ? (Character)b.get(var4) : var4;
                    }

                    var1 = new String(var2);
                }

                var1 = new String(com.mintegral.msdk.base.utils.b.a(var1));
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return var1;
    }
}

