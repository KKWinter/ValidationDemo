package com.kkwinter.thirdsdk.advsdk.b;


import android.util.Base64;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * DES加密、解密类
 */
public final class f {
    private Key c;
    private Cipher d;
    private Cipher e;
    public static byte[] a = new byte[]{1, 2, 3, 4, 5, 6, 7, 9};
    public static byte[] b = new byte[]{115, 106, 119, 111, 114, 107, 56, 56};

    public f() throws Exception {
        this.c = new SecretKeySpec(b, "DES");
        IvParameterSpec var2 = new IvParameterSpec(a);
        this.e = Cipher.getInstance("DES/CBC/PKCS5Padding");
        this.e.init(1, this.c, var2);
        this.d = Cipher.getInstance("DES/CBC/PKCS5Padding");
        this.d.init(2, this.c, var2);
    }


    //把var1文件解密到var2路径下
    public final boolean a(String var1, String var2) throws Exception {
        return this.a((InputStream)(new FileInputStream(var1)), (String)var2);
    }

    private boolean a(InputStream var1, String var2) throws IOException {
        try {
            FileOutputStream var8 = new FileOutputStream(var2);
            CipherOutputStream var3 = new CipherOutputStream(var8, this.d);
            byte[] var4 = new byte[10240];

            int var5;
            while((var5 = var1.read(var4)) >= 0) {
                var3.write(var4, 0, var5);
            }

            try {
                var3.close();
                var1.close();
                var8.close();
            } catch (Exception var6) {
                var6.printStackTrace();
            }

            return true;
        } catch (Exception var7) {
            var7.printStackTrace();
            throw var7;
        }
    }



    //字符串加密
    public static final String a(String var0, byte[] var1) {
        try {
            IvParameterSpec var2 = new IvParameterSpec(a);
            SecretKeySpec var6 = new SecretKeySpec(var1, "DES");
            Cipher var3;
            (var3 = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(1, var6, var2);
            byte[] var5 = Base64.encode(var3.doFinal(var0.getBytes()), 0);
            return new String(var5);
        } catch (Exception var4) {
            return null;
        }
    }


    //字符串解密
    public static final String b(String var0, byte[] var1) {
        try {
            byte[] var5 = Base64.decode(var0, 0);
            IvParameterSpec var2 = new IvParameterSpec(a);
            SecretKeySpec var6 = new SecretKeySpec(var1, "DES");
            Cipher var3;
            (var3 = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, var6, var2);
            var5 = var3.doFinal(var5);
            return new String(var5);
        } catch (Exception var4) {
            return null;
        }
    }
}

