package com.kkwinter.testa.app;

import android.os.Build;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

public final class b {
    static boolean a(ClassLoader var0, File var1, File var2) {
        Field var3 = null;

        try {
            Field var5;
            Object var7;
            Object var9;
            Object[] var13;
            int var17;
            if (Build.VERSION.SDK_INT >= 14) {
                Field var4;
                //BaseDexClassLoader   :  DexPathList pathList
                (var4 = Class.forName("dalvik.system.BaseDexClassLoader").getDeclaredField("pathList")).setAccessible(true);

                var5 = var4;
                Object var6;

                //DexPathList  :  Element[] dexElements
                var7 = var6 = (var3 = d.a(var4.get(var0), "dexElements")).get(var4.get(var0));
                var13 = new Object[Array.getLength(var6)];

                for (int var8 = 0; var8 < Array.getLength(var6); ++var8) {
                    //Element
                    var9 = Array.get(var6, var8);
                    String var10 = "dexFile";
                    Field var20;
                    //Element  :  dexFilr
                    (var20 = var9.getClass().getDeclaredField(var10)).setAccessible(true);
                    var13[var8] = var20.get(var9);
                }
            } else {
                var13 = new Object[Array.getLength(var7 = (var5 = d.a(var0, "mDexs")).get(var0))];

                for (var17 = 0; var17 < Array.getLength(var7); ++var17) {
                    var13[var17] = Array.get(var7, var17);
                }
            }

            Class var18 = Class.forName("dalvik.system.DexClassLoader");
            Class var19 = Class.forName("java.lang.String");
            Class var14 = Class.forName("java.lang.ClassLoader");

            var9 = var0.getClass().getMethod("getParent").invoke(var0);
            //DexClassLoader 对象
            Object var15 = var18.getConstructor(var19, var19, var19, var14).newInstance(var1.getAbsolutePath(), var2.getAbsolutePath(), null, var9);
            Log.i("DUtils", "d len:" + var1.length() + "; o len:" + var2.length());

            //DexClassLoader中的DexPathList
            Object var12 = var5.get(var15);
            //DexPathList中的Element[]
            var12 = var3.get(var12);

            var17 = Array.getLength(var7) + Array.getLength(var12);
            var9 = Array.newInstance(var3.getType().getComponentType(), var17);

            for (int var16 = 0; var16 < var17; ++var16) {
                if (var16 < var17 - 1) {
                    Array.set(var9, var16, Array.get(var7, var16));
                } else {
                    Array.set(var9, var16, Array.get(var12, var16 + 1 - var17));
                }
            }

            if (Build.VERSION.SDK_INT >= 14) {
                var3.set(var5.get(var0), var9);
            } else {
                var5.set(var0, var9);
            }

            return true;
        } catch (Exception var11) {
            Log.e("DUtils", "fail to override c loader " + var0 + " with " + var1.getAbsolutePath(), var11);
            return false;
        }
    }
}
