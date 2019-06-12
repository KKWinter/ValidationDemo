package com.kkwinter.thirdsdk.advsdk.b;


import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * File文件操作类
 */
public class p {

    public p() {
    }


    //读取var0路径下的文件
    public static String a(String var0) {
        StringBuilder var1;
        return (var1 = b(var0, "UTF-8")) == null ? "" : var1.toString();
    }

    //按行读取var0路径的文件中的内容
    private static StringBuilder b(String var0, String var1) {
        File var13 = new File(var0);
        StringBuilder var2 = new StringBuilder("");
        if (!var13.isFile()) {
            return null;
        } else {
            BufferedReader var3 = null;
            boolean var8 = false;

            StringBuilder var15;
            try {
                var8 = true;
                InputStreamReader var14 = new InputStreamReader(new FileInputStream(var13), var1);
                var3 = new BufferedReader(var14);

                while(true) {
                    if ((var0 = var3.readLine()) == null) {
                        var3.close();
                        var15 = var2;
                        var8 = false;
                        break;
                    }

                    if (!var2.toString().equals("")) {
                        var2.append("\r\n");
                    }

                    var2.append(var0);
                }
            } catch (IOException var11) {
                throw new RuntimeException("IOException occurred. ", var11);
            } finally {
                if (var8) {
                    if (var3 != null) {
                        try {
                            var3.close();
                        } catch (IOException var9) {
                            throw new RuntimeException("IOException occurred. ", var9);
                        }
                    }

                }
            }

            try {
                var3.close();
                return var15;
            } catch (IOException var10) {
                throw new RuntimeException("IOException occurred. ", var10);
            }
        }
    }



    //写文件
    private static boolean a(File var0, InputStream var1) {
        FileOutputStream var3 = null;
        boolean var9 = false;

        try {
            var9 = true;
            c(var0.getAbsolutePath());
            var3 = new FileOutputStream(var0, false);
            byte[] var15 = new byte[1024];

            while(true) {
                int var2;
                if ((var2 = var1.read(var15)) == -1) {
                    var3.flush();
                    var9 = false;
                    break;
                }

                var3.write(var15, 0, var2);
            }
        } catch (FileNotFoundException var12) {
            throw new RuntimeException("FileNotFoundException occurred. ", var12);
        } catch (IOException var13) {
            throw new RuntimeException("IOException occurred. ", var13);
        } finally {
            if (var9) {
                if (var3 != null) {
                    try {
                        var3.close();
                        var1.close();
                    } catch (IOException var10) {
                        throw new RuntimeException("IOException occurred. ", var10);
                    }
                }

            }
        }

        try {
            var3.close();
            var1.close();
            return true;
        } catch (IOException var11) {
            throw new RuntimeException("IOException occurred. ", var11);
        }
    }

    public static boolean a(String var0, String var1) {
        FileInputStream var3;
        try {
            var3 = new FileInputStream(var0);
        } catch (FileNotFoundException var2) {
            throw new RuntimeException("FileNotFoundException occurred. ", var2);
        }

        String var10000 = var1;
        FileInputStream var4 = var3;
        var0 = var10000;
        return a((File)(var10000 != null ? new File(var0) : null), (InputStream)var4);
    }


    //  /xx/yy/zz/adv.dex  获取adv.dex的父目录
    public static String b(String var0) {
        if (TextUtils.isEmpty(var0)) {
            return var0;
        } else {
            int var1;
            return (var1 = var0.lastIndexOf(File.separator)) == -1 ? "" : var0.substring(0, var1);
        }
    }

    //   /xx/yy/zz/adv.dex 创建adv.dex的父目录
    public static boolean c(String var0) {
        if (TextUtils.isEmpty(var0 = b(var0))) {
            return false;
        } else {
            File var1;
            return (var1 = new File(var0)).exists() && var1.isDirectory() || var1.mkdirs();
        }
    }

    //var0 路径的文件是否存在
    public static boolean d(String var0) {
        if (TextUtils.isEmpty(var0)) {
            return false;
        } else {
            File var1;
            return (var1 = new File(var0)).exists() && var1.isFile();
        }
    }

    // var0 路径的文件 删除
    public static boolean e(String var0) {
        if (TextUtils.isEmpty(var0)) {
            return true;
        } else {
            File var5;
            if (!(var5 = new File(var0)).exists()) {
                return true;
            } else if (var5.isFile()) {
                return var5.delete();
            } else if (!var5.isDirectory()) {
                return false;
            } else {
                File[] var1;
                int var2 = (var1 = var5.listFiles()).length;

                for(int var3 = 0; var3 < var2; ++var3) {
                    File var4;
                    if ((var4 = var1[var3]).isFile()) {
                        var4.delete();
                    } else if (var4.isDirectory()) {
                        e(var4.getAbsolutePath());
                    }
                }

                return var5.delete();
            }
        }
    }
}

