package com.jumpraw.mdstd.hxx;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.os.Process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class a {
//    public static File a(Context paramContext, String paramString1, String paramString2) throws Exception {
//
//        InputStream localInputStream = paramContext.getAssets().open(paramString1);
//        paramString1 = Math.abs(paramString1.hashCode() + Process.myPid()) + "";
//
//        File paramContextFile = new File(paramContext.getFilesDir(), Math.abs(paramString1.hashCode()) + "");
//        paramContextFile.mkdirs();
//        paramContextFile = new File(paramContextFile, paramString1 + a(paramString2, "8? 18 5< 3: "));   // .jar
//
//        OutputStream outputStream = new FileOutputStream(paramContextFile);
//        if (paramString2.hashCode() > 0) {
//        }
//
//        for (int i = paramString2.hashCode(); ; i = -paramString2.hashCode()) {
//            a(localInputStream, outputStream, i % 256);
//            return paramContextFile;
//        }
//    }

    public static Class<?> a(Context paramContext, File paramFile, String paramString1, String paramString2) throws Exception {


//        return ((ClassLoader) Class.forName(a(paramString2, "88 29 4; 99 19 28 :A 4: 7: 6: 8= 28 59 9> :8 28 8: 2< 4; 68 79 6: 28 29 38 59 1< 3: ")).getConstructor(new Class[]{String.class, String.class, String.class, ClassLoader.class}).newInstance(new Object[]{paramFile.getPath(), paramFile.getParent(), paramContext.getApplicationInfo().nativeLibraryDir, paramContext.getClassLoader().getParent()})).loadClass(paramString1);

        return ((ClassLoader) Class.forName("dalvik.system.DexClassLoader").
                    getConstructor(new Class[]{String.class, String.class, String.class, ClassLoader.class}).
                        newInstance(new Object[]{paramFile.getPath(), paramFile.getParent(), paramContext.getApplicationInfo().nativeLibraryDir, paramContext.getClassLoader().getParent()})).loadClass("sdk.hfn.mvp.RunInvoker");

    }


    //解密方法
    public static String a(String paramString1, String paramString) {
        byte[] arrayOfByte = paramString.getBytes();

        byte[] paramString2 = new byte[paramString.length() / 3];
        int i = 0;
        while (i < arrayOfByte.length / 3) {
            paramString2[i] = ((byte) ((arrayOfByte[(i * 3 + 2)] - 32) * 100 + (arrayOfByte[(i * 3 + 1)] - 56) * 10 + (arrayOfByte[(i * 3)] - 49)));
            paramString2[i] = ((byte) (paramString2[i] ^ (byte) paramString1.charAt(i % paramString1.length())));
            i += 1;
        }
        return new String(paramString2);
    }

    public static void a(Context paramContext, String paramString1, Class<?> paramClass, String paramString2, String paramString3) throws Exception {
        paramClass.getMethod("run", new Class[]{Context.class, String.class, String.class}).invoke(null, new Object[]{paramContext, paramString2, paramString3});
    }

//    private static void a(InputStream paramInputStream, OutputStream paramOutputStream, int paramInt) throws Exception {
//        byte[] localObject1 = new byte[4];
//        paramInputStream.read(localObject1);
//        int i = localObject1[3];
//        int j = localObject1[0];
//        int k = localObject1[1];
//
//        i = paramInt ^ ((localObject1[2] & 0xFF) << 8) + (((j & 0xFF) << 24) + ((k & 0xFF) << 16)) + (i & 0xFF);
//        paramInputStream.read(new byte[(byte) paramInputStream.read() + 222]);
//        Object localObject2 = new ArrayList();
//        long l = i;
//        paramInt = 0;
//        while (paramInt < 256) {
//            ((List) localObject2).add(Integer.valueOf(paramInt));
//            paramInt += 1;
//        }
//        localObject1 = new int[];
//        paramInt = 0;
//        while (paramInt < localObject1.length) {
//            l = (int) ((l * 1051377566L + 172L & 0xFFFFFFFF) >>> 16);
//            localObject1[((Integer) localObject2.remove((int) (Math.abs(l) % localObject2.size()))).intValue()] = paramInt;
//            paramInt += 1;
//        }
//        localObject2 = new byte['��'];
//        for (; ; ) {
//            j = paramInputStream.read((byte[]) localObject2);
//            if (j <= 0) {
//                return;
//            }
//            paramInt = 0;
//            while (paramInt < j) {
//                localObject2[paramInt] = ((byte) localObject1[((localObject2[paramInt] ^ i) & 0xFF)]);
//                paramInt += 1;
//            }
//            paramOutputStream.write((byte[]) localObject2, 0, j);
//        }
//    }
}

