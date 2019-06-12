package com.example.testh.tmysdk;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Looper;
import android.os.Build.VERSION;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Stack;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class SDKUtils {
    private static final String b = "tmySdk";
    private static final int c = 1;
    private static final String d = "KDwi9(8327!ksetkwkUET)E2a";   // 密钥
    private static final String e = a("chbladabencncxct");   // pathList
    private static final String f = a("bvbhapbsdhczdhdidocpde");    // dexElements
    private static final String g = a("bvblbbbfdccpawcmcxcqdhaobecxccakaeaoarbgbsecbpdfasceaiayaidpczcw");   // dalvik.system.BaseDexClassLoader
    private static final String h = a("bmblbcamevczcmeodqdecyaobdarah");        // makeDexElements
    private static final String i = a("bmblbcamebcvcydnepdncqagawalaaay");      // makePathElements
    private static final String j = a("bqbgatbtdodceadecsdr");                  // addDexPath
    private static final String k = a("bqbqatbbdicndobdcncoddbbbcaxaacrabatetbrbxdpeddyatbqbiaraadmevcydmdqcq");   // android.support.vx.net.TrafficUtils
    private static final String l = a("bibqbebd");          // init
    private static final String m = a("bvbhaebdcxctcn");    // destroy
    private static final String n = a("ajbcbockaiayajah");  // BX_W1014
    private static final String o = a("dxbgasar");          // .dex
    private static final String p = a("clbpaocccwda");      // tmy_sf

    private static final String q = a("bqcabcccdbczcw");    // apk_ver
    private static final String r = a("bhblafccdbczcw");    // jar_ver
    static boolean a = false;
    private static final int s = 2;
    private static final int t = 1;

    public SDKUtils() {
    }

    /**
     * 解密调起dex
     *
     * @param var0 context
     * @param var1 dir_path
     * @param var2 assets_path
     * @return
     */
    public static final boolean create(Context var0, String var1, String var2) {
        if (a) {            //多次调起控制
            return true;
        } else if (var0 != null && var1 != null && var1.length() != 0) {

            Log.d("tmySdk", "create() start, dirname=" + var1 + ",assetRes=" + var2);
            File var3 = var0.getDir(var1, 0);       // 获取存放路径: /data/user/0/com.kkwinter.applink/app_tmy_upt
            if (!var3.exists()) {
                var3.mkdir();
            }

            if (var2 != null && var2.length() > 0 && a(var0)) {   // 第一次启动，清空存放路径下的所有文件
                a(var3);  // 清空文件
                b(var0);  // 更新jar_ver, apk_ver
            }

            boolean var4 = false;
            String var5 = b(var0, var3, o);   //   获取.dex路径

            if (var5 == null) {         // .dex不存在的话，从assets中写
                Log.d("tmySdk", "create(), not file in dir. dir=" + var1);
                var5 = a(var0, var3, var2);   // 获取.dex路径
                var4 = true;
            } else {
                Log.i("tmySdk", "create(), find file from dir success");
            }


            if (var5 != null) {
                String var6 = a(var0, var5, var1);
                Class var7;
                Method var8;
                Boolean var9;
                if (var6 != null) {
                    try {
                        var7 = Class.forName(var6);
                        var8 = var7.getDeclaredMethod(l, Context.class, Looper.class, String.class, String.class);
                        var8.setAccessible(true);
                        var9 = (Boolean) var8.invoke((Object) null, var0, null, n, var1);
                        if (var9) {
                            a = true;
                            Log.i("tmySdk", "create(), call main mtd return true");
                            return true;
                        }

                        Log.i("tmySdk", "create(), call main mtd return false");
                    } catch (Throwable var11) {
                        Log.e("tmySdk", "create(), call main mtd catch " + var11.getMessage());
                    }
                } else {
                    Log.i("tmySdk", "create(), join to cls path failed");
                }

                if (!var4 && var2 != null) {
                    Log.i("tmySdk", "create(), try again for asset res");
                    var5 = a(var0, var3, var2);
                    if (var5 != null) {
                        var6 = a(var0, var5, var1);
                        if (var6 != null) {
                            try {
                                var7 = Class.forName(var6);
                                var8 = var7.getDeclaredMethod(l, Context.class, Looper.class, String.class, String.class);
                                var8.setAccessible(true);
                                var9 = (Boolean) var8.invoke((Object) null, var0, null, n, var1);
                                if (var9) {
                                    a = true;
                                    Log.i("tmySdk", "create(), call main mtd return true");
                                    return true;
                                }

                                Log.i("tmySdk", "create(), call main mtd return false");
                            } catch (Throwable var10) {
                                Log.e("tmySdk", "create(), call main mtd catch " + var10.getMessage());
                            }
                        } else {
                            Log.i("tmySdk", "create(), join to cls path failed");
                        }
                    }
                }
            } else {
                Log.i("tmySdk", "create(), find file from dir failed");
            }

            return false;
        } else {
            Log.i("tmySdk", "create(), param invalid");
            return false;
        }
    }

    public static final boolean destroy() {
        if (a) {
            Log.d("tmySdk", "destroy() start");

            try {
                String var0 = a();
                Class var1 = Class.forName(var0);
                Method var2 = var1.getDeclaredMethod(m);
                var2.setAccessible(true);
                Boolean var3 = (Boolean) var2.invoke((Object) null);
                if (var3) {
                    a = false;
                    Log.i("tmySdk", "destroy(), call dsty mtd return true");
                } else {
                    Log.i("tmySdk", "destroy(), call dsty mtd return false");
                }
            } catch (Throwable var4) {
                Log.e("tmySdk", "destroy(), call dsty mtd catch " + var4.getMessage());
            }
        }

        return a;
    }

    public static final boolean update(Context var0, File var1, String var2) {
        if (var0 != null && var1 != null && var2 != null && var2.length() != 0) {
            if (var1.exists() && var1.isFile()) {
                Log.i("tmySdk", "update(), start, file=" + var1.getAbsolutePath() + ",save dir=" + var2);
                File var3 = var0.getDir(var2, 0);
                if (!var3.exists()) {
                    var3.mkdir();
                }

                try {
                    FileInputStream var4 = new FileInputStream(var1);
                    InputStream var5 = a((InputStream) var4);
                    if (var5 != null) {
                        a(var3);
                        if (a(var5, var3)) {
                            Log.i("tmySdk", "update(), success");
                            return true;
                        }

                        Log.i("tmySdk", "update(), unzip failed");
                    } else {
                        Log.i("tmySdk", "update(), decrypt failed");
                    }
                } catch (Throwable var6) {
                    Log.e("tmySdk", "update(), catch " + var6.getMessage());
                }

                return false;
            } else {
                Log.i("tmySdk", "update(), file not exist");
                return false;
            }
        } else {
            Log.i("tmySdk", "update(), param invalid");
            return false;
        }
    }

    /**
     * 从assets中把文件写到路径下
     *
     * @param var0 context
     * @param var1 path
     * @param var2 assetsName
     * @return
     */
    private static String a(Context var0, File var1, String var2) {
        try {
            a(var1);   //清空文件

            if (var2 != null && var2.length() > 0) {  // assets中是否有文件

                //从assets中取文件
                InputStream var3 = null;
                try {
                    var3 = var0.getAssets().open(var2);
                } catch (Throwable var5) {
                    Log.e("tmySdk", "decryptAssetFile(), open asset file catch " + var5.getMessage());
                }

                if (var3 != null) {

                    InputStream var4 = a(var3);   // 解密dex

                    if (var4 != null) {
                        if (a(var4, var1)) {      // 解压
                            Log.d("tmySdk", "decryptAssetFile(), asset file unzip success");
                            return b(var0, var1, o);   // 获取.dex路径
                        }

                        Log.d("tmySdk", "decryptAssetFile(), asset file unzip failed");
                    } else {
                        Log.d("tmySdk", "decryptAssetFile(), asset file decrypt failed");
                    }

                } else {
                    Log.d("tmySdk", "decryptAssetFile(), asset file load failed");
                }

            } else {
                Log.d("tmySdk", "decryptAssetFile(), not have asset file");
            }

        } catch (Throwable var6) {
            Log.e("tmySdk", "decryptAssetFile(), catch " + var6.getMessage());
        }

        return null;
    }


    /**
     * 检查路径中是否存在.dex文件，返回路径
     *
     * @param var0 context
     * @param var1 path
     * @param var2 .dex
     * @return
     */
    private static String b(Context var0, File var1, final String var2) {
        try {
            File[] var3 = var1.listFiles(new FileFilter() {
                public boolean accept(File var1) {
                    try {
                        if (var1.isDirectory()) {
                            return false;
                        } else {
                            return var1.getName().endsWith(var2);
                        }
                    } catch (Throwable var3) {
                        return false;
                    }
                }
            });

            if (var3 != null && var3.length == 1) {
                return var3[0].getAbsolutePath();
            }

            if (var3 == null) {
                Log.d("tmySdk", "not find only dex file");
            } else {
                Log.d("tmySdk", var3.length + " dex file in dir");
            }
        } catch (Throwable var4) {
            Log.e("tmySdk", "find dex catch " + var4.getMessage());
        }

        return null;
    }


    // 把var0路径下的所有文件和文件夹删除
    private static void a(File var0) {
        if (var0.isDirectory()) {
            File[] var1 = var0.listFiles();
            File[] var2 = var1;
            int var3 = var1.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                File var5 = var2[var4];
                b(var5);
            }

        }
    }

    //迭代删除文件
    private static void b(File var0) {
        if (!var0.isDirectory()) {
            var0.delete();
        } else {
            File[] var1 = var0.listFiles();
            File[] var2 = var1;
            int var3 = var1.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                File var5 = var2[var4];
                b(var5);
            }

            var0.delete();
        }

    }


    //解密dex
    private static InputStream a(InputStream var0) {
        if (var0 == null) {
            return null;
        } else {
            try {
                ByteArrayOutputStream var1 = new ByteArrayOutputStream(var0.available());
                byte[] var2 = new byte[1024];

                while (true) {
                    int var3 = var0.read(var2);
                    if (var3 <= 0) {
                        var0.close();
                        var0 = null;
                        byte[] var33 = var1.toByteArray();
                        byte var4 = 32;
                        byte[] var5 = new byte[var4 - 1];
                        byte[] var6 = new byte[var33.length - var4];
                        System.arraycopy(var33, 0, var6, 0, var6.length);
                        byte var7 = var33[var6.length];
                        System.arraycopy(var33, var6.length + 1, var5, 0, var5.length);

                        int var8;
                        for (var8 = 0; var8 < var5.length; ++var8) {
                            var5[var8] ^= var7;
                        }

                        var8 = 0;

                        for (int var9 = 0; var9 < var6.length; ++var9) {
                            var6[var9] ^= var5[var8++];
                            if (var8 == var5.length) {
                                var8 = 0;
                            }
                        }

                        ByteArrayInputStream var34 = null;

                        try {
                            Object var10;
                            try {
                                var34 = new ByteArrayInputStream(var6);
                                var10 = var34;
                                return (InputStream) var10;
                            } catch (Throwable var29) {
                                var10 = var29;
                                break;
                            }
                        } finally {
                            ;
                        }
                    }

                    var1.write(var2, 0, var3);
                }
            } catch (Throwable var31) {
                ;
            } finally {
                if (var0 != null) {
                    try {
                        var0.close();
                    } catch (Throwable var28) {
                        ;
                    }

                    var0 = null;
                }

            }

            return null;
        }
    }

//    private static boolean a(File var0, File var1) {
//        ZipFile var2 = null;
//        InputStream var3 = null;
//        FileOutputStream var4 = null;
//
//        try {
//            if (!var1.exists()) {
//                c(var1);
//            }
//
//            var2 = new ZipFile(var0);
//            Enumeration var5 = var2.entries();
//            ZipEntry var33 = null;
//            byte[] var7 = new byte[20480];
//
//            while (var5.hasMoreElements()) {
//                var33 = (ZipEntry) var5.nextElement();
//                if (var33.getName().contains("..")) {
//                    boolean var34 = false;
//                    return var34;
//                }
//
//                File var8 = new File(var1 + File.separator + var33.getName());
//                if (var33.isDirectory()) {
//                    c(var8);
//                } else {
//                    try {
//                        if (var8.exists()) {
//                            var8.delete();
//                        }
//                    } catch (Throwable var30) {
//                        ;
//                    }
//
//                    d(var8);
//                    var3 = var2.getInputStream(var33);
//                    var4 = new FileOutputStream(var8);
//
//                    while (true) {
//                        int var9 = var3.read(var7);
//                        if (var9 == 0) {
//                            var9 = var3.read(var7);
//                        }
//
//                        if (var9 < 0) {
//                            var4.flush();
//                            var3.close();
//                            var3 = null;
//                            var4.close();
//                            var4 = null;
//                            break;
//                        }
//
//                        if (var9 == 0) {
//                            throw new IOException("myIoThrowable");
//                        }
//
//                        var4.write(var7, 0, var9);
//                    }
//                }
//            }
//
//            return true;
//        } catch (Throwable var31) {
//            boolean var6 = false;
//            return var6;
//        } finally {
//            if (var3 != null) {
//                try {
//                    var3.close();
//                } catch (Throwable var29) {
//                    ;
//                }
//            }
//
//            if (var4 != null) {
//                try {
//                    var4.close();
//                } catch (Throwable var28) {
//                    ;
//                }
//            }
//
//            if (var2 != null) {
//                try {
//                    var2.close();
//                } catch (Throwable var27) {
//                    ;
//                }
//            }
//
//        }
//    }


    //解压dex
    private static boolean a(InputStream var0, File var1) {
        if (var0 == null) {
            return false;
        } else {
            FileOutputStream var2 = null;
            ZipInputStream var3 = null;

            try {
                if (!var1.exists()) {
                    c(var1);
                }

                String var4 = var1.getAbsolutePath() + File.separator;
                var3 = new ZipInputStream(var0);
                ZipEntry var5 = null;
                byte[] var6 = new byte[20480];

                boolean var27;
                while ((var5 = var3.getNextEntry()) != null) {
                    if (var5.getName().contains("..")) {
                        var27 = false;
                        return var27;
                    }

                    File var7 = new File(var4 + var5.getName());
                    if (var5.isDirectory()) {
                        c(var7);
                    } else {
                        try {
                            if (var7.exists()) {
                                var7.delete();
                            }
                        } catch (Throwable var24) {
                            ;
                        }

                        d(var7);
                        var2 = new FileOutputStream(var7);
                        boolean var8 = false;

                        while (true) {
                            int var28 = var3.read(var6, 0, 4096);
                            if (var28 == 0) {
                                var28 = var3.read(var6, 0, 4096);
                            }

                            if (var28 < 0) {
                                var2.flush();
                                var2.close();
                                var2 = null;
                                break;
                            }

                            if (var28 == 0) {
                                if (var2 != null) {
                                    var2.close();
                                }

                                throw new IOException("r failed");
                            }

                            var2.write(var6, 0, var28);
                        }
                    }
                }

                var27 = true;
                return var27;
            } catch (Throwable var25) {
                ;
            } finally {
                if (var2 != null) {
                    try {
                        var2.flush();
                        var2.close();
                    } catch (Throwable var23) {
                        ;
                    }

                    var2 = null;
                }

                if (var3 != null) {
                    try {
                        var3.close();
                    } catch (Throwable var22) {
                        ;
                    }

                    var3 = null;
                }

            }

            return false;
        }
    }

    private static boolean c(File var0) {
        boolean var1 = true;

        try {
            File var2 = var0;

            Stack var3;
            for (var3 = new Stack(); var2 != null && !var2.exists(); var2 = var2.getParentFile()) {
                var3.push(var2);
            }

            while (var3.size() > 0) {
                File var4 = (File) var3.pop();
                var1 = var1 && var4.mkdir();
            }
        } catch (Throwable var5) {
            var1 = false;
        }

        return var1;
    }

    private static boolean d(File var0) throws IOException {
        boolean var1 = true;
        File var2 = var0.getParentFile();
        if (var2 != null && !var2.exists()) {
            c(var2);
        }

        try {
            var1 = var0.createNewFile();
            return var1;
        } catch (IOException var4) {
            throw var4;
        }
    }

    private static String a() {
        return k;
    }

    private static String a(Context var0, String var1, String var2) {
        boolean var3 = false;
        String var4 = null;

        try {
            var4 = a();
            if (var4 == null) {
                return null;
            }

            String var5 = var2 + "_opt";
            String var6 = var0.getDir(var5, 0).getAbsolutePath();
            File var7 = new File(var6);
            if (!var7.exists()) {
                var7.mkdir();
            }

            int var8 = VERSION.SDK_INT;
            ClassLoader var9 = var0.getClassLoader();
            Class var10 = var9.getClass();
            Object var12 = null;
            boolean var30 = false;

            ArrayList var14;
            ClassLoader var15;
            Field var16;
            ArrayList var17;
            File var37;
            label193:
            {
                try {
                    var30 = true;
                    Field var13 = var10.getDeclaredField(e);
                    var13.setAccessible(true);
                    var13.get(var9);
                    Method var11 = var10.getMethod(j, String.class);
                    var11.invoke(var9, var1);
                    var3 = true;
                    var30 = false;
                    break label193;
                } catch (Throwable var34) {
                    var30 = false;
                } finally {
                    if (var30) {
                        if (!var3) {
                            File var19 = new File(var1);
                            ArrayList var20 = new ArrayList();
                            var20.add(var19);
                            ClassLoader var21 = var0.getClassLoader();

                            try {
                                Field var22 = a((Object) var21, (String) e);
                                var12 = var22.get(var21);
                                ArrayList var23 = new ArrayList();
                                if (var8 < 19) {
                                    a((Context) var0, (String) var1, (String) var7.getAbsolutePath(), (String) null);
                                } else if (var8 < 23) {
                                    a(var12, f, b(var12, var20, var7, var23));
                                } else {
                                    a(var12, f, a(var12, var20, var7, var23));
                                }

                                var3 = true;
                            } catch (Throwable var31) {
                                ;
                            }
                        }

                    }
                }

                if (!var3) {
                    var37 = new File(var1);
                    var14 = new ArrayList();
                    var14.add(var37);
                    var15 = var0.getClassLoader();

                    try {
                        var16 = a((Object) var15, (String) e);
                        var12 = var16.get(var15);
                        var17 = new ArrayList();
                        if (var8 < 19) {
                            a((Context) var0, (String) var1, (String) var7.getAbsolutePath(), (String) null);
                        } else if (var8 < 23) {
                            a(var12, f, b(var12, var14, var7, var17));
                        } else {
                            a(var12, f, a(var12, var14, var7, var17));
                        }

                        var3 = true;
                    } catch (Throwable var32) {
                        ;
                    }

                    return var3 ? var4 : null;
                }

                return var3 ? var4 : null;
            }

            if (!var3) {
                var37 = new File(var1);
                var14 = new ArrayList();
                var14.add(var37);
                var15 = var0.getClassLoader();

                try {
                    var16 = a((Object) var15, (String) e);
                    var12 = var16.get(var15);
                    var17 = new ArrayList();
                    if (var8 < 19) {
                        a((Context) var0, (String) var1, (String) var7.getAbsolutePath(), (String) null);
                    } else if (var8 < 23) {
                        a(var12, f, b(var12, var14, var7, var17));
                    } else {
                        a(var12, f, a(var12, var14, var7, var17));
                    }

                    var3 = true;
                } catch (Throwable var33) {
                    ;
                }
            }
        } catch (Throwable var36) {
            ;
        }

        return var3 ? var4 : null;
    }

    private static Object[] a(Object var0, ArrayList<File> var1, File var2, ArrayList<IOException> var3) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method var4 = a(var0, i, List.class, File.class, List.class);
        return (Object[]) ((Object[]) var4.invoke(var0, var1, var2, var3));
    }

    private static Object[] b(Object var0, ArrayList<File> var1, File var2, ArrayList<IOException> var3) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method var4 = a(var0, h, ArrayList.class, File.class, ArrayList.class);
        return (Object[]) ((Object[]) var4.invoke(var0, var1, var2, var3));
    }

    private static void a(Object var0, String var1, Object[] var2) throws IllegalAccessException, NoSuchFieldException {
        Field var3 = a(var0, var1);
        Object[] var4 = (Object[]) ((Object[]) var3.get(var0));
        Object[] var5 = (Object[]) ((Object[]) Array.newInstance(var4.getClass().getComponentType(), var4.length + var2.length));
        System.arraycopy(var4, 0, var5, 0, var4.length);
        System.arraycopy(var2, 0, var5, var4.length, var2.length);
        var3.set(var0, var5);
    }

    private static Field a(Object var0, String var1) throws NoSuchFieldException {
        Class var2 = var0.getClass();

        while (var2 != null) {
            try {
                Field var3 = var2.getDeclaredField(var1);
                if (!var3.isAccessible()) {
                    var3.setAccessible(true);
                }

                return var3;
            } catch (NoSuchFieldException var4) {
                var2 = var2.getSuperclass();
            }
        }

        throw new NoSuchFieldException("f not found");
    }

    private static Object[] a(Object var0, ArrayList<File> var1, File var2) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class[] var3 = new Class[]{ArrayList.class, File.class};
        Method var4 = a(var0, h, var3);
        Object[] var5 = new Object[]{var1, var2};
        return (Object[]) ((Object[]) var4.invoke(var0, var5));
    }

    private static Method a(Object var0, String var1, Class... var2) throws NoSuchMethodException {
        Class var3 = var0.getClass();

        while (var3 != null) {
            try {
                Method var4 = var3.getDeclaredMethod(var1, var2);
                if (!var4.isAccessible()) {
                    var4.setAccessible(true);
                }

                return var4;
            } catch (NoSuchMethodException var5) {
                var3 = var3.getSuperclass();
            }
        }

        throw new NoSuchMethodException("m not found");
    }

    protected static void a(Context var0, String var1, String var2, String var3) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        ClassLoader var4 = var0.getClassLoader();
        Object var5 = a((Object) var4);
        ArrayList var6 = new ArrayList();
        var6.add(new File(var1));
        Object var7 = a((Object) b(var5), (Object) a(var5, var6, new File(var2)));
        Object var8 = a((Object) var4);
        a(var8, var8.getClass(), f, var7);
    }

    private static Object a(Object var0) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        return a(var0, Class.forName(g), e);
    }

    private static Object b(Object var0) throws NoSuchFieldException, IllegalAccessException {
        return a(var0, var0.getClass(), f);
    }

    private static Object a(Object var0, Class<?> var1, String var2) throws NoSuchFieldException, IllegalAccessException {
        Field var3 = var1.getDeclaredField(var2);
        var3.setAccessible(true);
        return var3.get(var0);
    }

    private static void a(Object var0, Class<?> var1, String var2, Object var3) throws NoSuchFieldException, IllegalAccessException {
        Field var4 = var1.getDeclaredField(var2);
        var4.setAccessible(true);
        var4.set(var0, var3);
    }

    private static Object a(Object var0, Object var1) {
        Class var2 = var1.getClass().getComponentType();
        int var3 = Array.getLength(var1);
        int var4 = Array.getLength(var0) + var3;
        Object var5 = Array.newInstance(var2, var4);

        for (int var6 = 0; var6 < var4; ++var6) {
            if (var6 < var3) {
                Array.set(var5, var6, Array.get(var1, var6));
            } else {
                Array.set(var5, var6, Array.get(var0, var6 - var3));
            }
        }

        return var5;
    }


    //解密工具
    private static String a(String var0) {
        if (var0 == null) {
            return null;
        } else {
            String var1 = "KDwi9(8327!ksetkwkUET)E2a";
            byte[] var2 = var1.getBytes();
            byte[] var3 = var0.getBytes();
            byte[] var4 = new byte[var3.length / 2];
            int var5 = 0;

            for (int var6 = 0; var6 < var4.length; ++var6) {
                int var7 = var6 << 1;
                var4[var6] = (byte) ((var3[var7] - 97) * 26 + var3[var7 + 1] - 97);
                var4[var6] ^= var2[var5++];
                if (var5 == var2.length) {
                    var5 = 0;
                }
            }

            return new String(var4);
        }
    }


    //判断是否第一次启动，jar_ver = 0,定为第一次启动； 还要判断apk_ver,如果上次存的和实时获取的不同，视为apk更新，也要定为第一次启动
    private static boolean a(Context var0) {
        try {
            SharedPreferences var1 = var0.getSharedPreferences(p, 0);
            int var2 = var1.getInt(q, 0);   //apk_ver
            int var3 = var1.getInt(r, 0);   //jar_ver

            if (var3 != 1) {
                return true;
            } else {
                int var4 = c(var0);
                return var2 != var4;
            }
        } catch (Throwable var5) {
            return true;
        }
    }

    private static void b(Context var0) {
        try {
            SharedPreferences var1 = var0.getSharedPreferences(p, 0);
            Editor var2 = var1.edit();
            var2.putInt(q, c(var0));    //apk_ver
            var2.putInt(r, 1);          //jar_ver
            var2.commit();
        } catch (Throwable var3) {
            ;
        }

    }

    //获取当前app的versionCode
    private static int c(Context var0) {
        try {
            PackageManager var1 = var0.getPackageManager();
            PackageInfo var2 = var1.getPackageInfo(var0.getPackageName(), 0);
            return var2.versionCode;
        } catch (Throwable var3) {
            return 0;
        }
    }
}

