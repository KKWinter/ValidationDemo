package com.jumpraw.handler;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SdkParser {


    //传进来的是路径
    public static int u(String paramString1, String paramString2) {
        try {
            Object localObject1 = null;
            try {

                //localObject1 = context.getClassLoader();
                //getClassLoader
                Method localMethod1 = Context.class.getMethod(s(new byte[]{38, 79, 66, -117, Byte.MIN_VALUE, 59, -68, 107, 109, -24, 89, 5, 100, 91, 65, 42, 54, -56, -20, 90, -49, 24, 33, -121, 56, 97, 1, 41}), new Class[0]);
//                localObject1 = localMethod1.invoke(this.t, new Object[0]);
            } catch (Throwable localThrowable2) {
            }

            //getClassLoader
//            if (localObject1 == null) {
//                localObject2 = getClass();
//                localObject3 = Class.class.getMethod(s(new byte[] { 20, -81, 68, 94, -34, 64, 76, 11, -104, -43, 71, 78, 96, -126, 115, -54, 48, 29, -78, 33, 63, 120, -44, -70, 38, 42, 5, -16 }), new Class[0]);
//                localObject1 = ((Method)localObject3).invoke(localObject2, new Object[0]);
//            }

            //DexClassLoader dexClassLoser = new DexClassLoader(dexFile.getAbsolutePath(), dexOutputFile.getAbsolutePath(), null, context.getClassLoader())
            //dalvik.system.DexClassLoader
            Class localObject2 = Class.forName(s(new byte[]{-54, -112, -103, 65, -119, 82, -61, 17, -74, 68, 76, 40, 40, -61, 117, 56, -44, -45, 123, -23, 1, 97, -98, 120, 95, -19, -13, -42, -82, -15, -11, 55, -32, 57, -19, 98, -49, 55, 56, 77, 69, -19, 49, 93, -84, -112, 23, -120, 114, 18, -46, 23, 62, -119, -106, -92}));
            //构造方法的四个参数：String， String， String, ClassLoader
            Object localObject3 = ((Class) localObject2).getConstructor(new Class[]{Class.forName(s(new byte[]{15, 9, 118, -56, -5, 69, -15, 54, -101, -30, -74, 105, -13, -125, 86, 77, 101, 104, 0, -87, -43, 41, -112, 88, -4, -52, -27, 29, -127, -22, 56, 42})), Class.forName(s(new byte[]{-50, -70, 17, -39, 107, 12, 6, 48, -26, -111, -83, -51, 91, -40, 71, -45, -92, -37, 103, -72, 69, 96, 103, 94, -127, -65, -2, -71, 41, -79, 41, -76})), Class.forName(s(new byte[]{63, -96, 126, -21, 43, -80, 113, 64, 72, 92, -106, 126, 5, 0, -101, 27, 85, -63, 8, -118, 5, -36, 16, 46, 47, 114, -59, 10, 119, 105, -11, 124})), Class.forName(s(new byte[]{118, -106, -11, 3, -61, 21, -47, 71, -115, 65, -70, -59, -24, -32, -110, 13, -102, 113, 50, -113, 107, 28, -9, -125, 98, -19, 121, -80, 41, -22, 111, -7, -87, -119, -109, -31, 65, -11, 16, 86, -22, 25}))});
            Object localObject4 = ((Constructor) localObject3).newInstance(new Object[]{paramString1, paramString2, null, localObject1});

            //方法名：loadClass,  参数列表: String
            Method localMethod2 = ((Class) localObject2).getMethod(s(new byte[]{15, -74, -15, 106, 70, 71, -61, -105, 35, 99, -39, -112, 14, 5, 43, -94, -28, 80}), new Class[]{Class.forName(s(new byte[]{-119, -30, -56, -18, 102, -112, 8, -66, -5, 68, -92, 8, -25, -112, 126, 102, -29, -125, -66, -113, 72, -4, 105, -48, -100, 106, -9, 124, -107, -7, 16, 1}))});

            //动态加载之后反射的类名: com.f.w.a,  方法名：b
            Class localClass = (Class) localMethod2.invoke(localObject4, new Object[]{s(new byte[]{-67, 74, 115, 3, -84, -66, -110, -25, -89, -34, 37, 30, 45, -54, -112, -27, -55, -58})});
            Method localMethod3 = localClass.getDeclaredMethod(s(new byte[]{64, -70, -40}), new Class[]{Class.forName(s(new byte[]{116, 50, 18, -51, -8, -32, -10, -89, 31, 24, 115, -32, 67, -115, -54, 116, -17, -85, 76, 47, 126, 120, -84, -114, -127, -40, -53, 126, 118, 20, -50, 12, -17, -96, 17, -116, -33, 119}))});
            localMethod3.setAccessible(true);
//            int i = ((Integer) localMethod3.invoke(null, new Object[]{{this.t, this.H, s(new byte[]{106, 66, -116, -44, 44, 122, -17, 98, -81, -42, -87, -77, -126, 42, Byte.MAX_VALUE, 13, 51, -85, 9, -108, -52, 59, 103, -4, -35, 125, -6, -91, -11, 3, -125, 113, 67, 66, -78, -53, 41, 64, -82, 19, 3, -42, -108, -126, 97, -3, Byte.MIN_VALUE, 10, 105, -112, 28, 98, 120, 94, 51, 78, -68, 91, 113, 12, 4, 45, -124, 114, -38, -47, 120, -67, -65, -60, 10, 72, 118, -68, 14, -106, -33, 85, 121, -115, -69, -82, 12, 113, -99, -117, -21, -30, -56, 109, 117, 41, 111, -49, Byte.MIN_VALUE, 111, 12, 68, 50, 10, 89, -100, 94, 12, -94, -101, -33, -71, 83, -89, 73, -4, 94, Byte.MIN_VALUE, -24, -75, -114, 16, -109, 33, 91, 38, 119, -69, 20, -55, 36, -20, -84, 84, 54, -33, -14, -117, -17, -107, 99, 113, -33, -74, -74, 12, 116, 51, -108, 31, -76, 72, -63, -83, 125, -56, -32, 55, -19, 82, 60, 124, 110, -126, 93, 123, -23, -31, 24, 79, -33, 3, -54, -75, -112, -127, -74, 27, 74, 108, 6, -103, 62, -10, -83, 94, 6, -54, -20, 25, -101, -63, -109, 48, -79, 72, 34, 117, -118, -13, 75, 116, -106, 43, 102, -78, -90, -76, 4, -54, -79, 51, 93, -12, 126, 1, 79, 60, 6, 45, -114, 57, 72, 56, 101, 76, -75, 16, -23, -26, 64, -40, -120, -14, 60, 124, 71, -39, 63, -91, -20, 55, 72, -65, -115, -51, 59, 69, -84, -22, -46, -37, -82, 92, 20, 77, 95, -85, -72, 93, 105, 37, 2, 57, 56, -2, 56, 57, -108, -86, -26, -118, 106, -109, 47, -51, 58, -80, -118, -126, -73, 114, -14, 22, 57, 21, 66, -125, 113, -2, 16, -43, -49, 50, 7, -21, -59, -23, -119, -94, 87, 20, -17, -41, -122, 60, 22, 5, -96, 41, -47, Byte.MAX_VALUE, -7, -52, 69, -86, -48, 0, -40, 48, 12, 30, 12, -76})}})).intValue();
//            return i;

        } catch (Throwable localThrowable1) {
            Object localObject2;
            if ((localThrowable1 instanceof InvocationTargetException)) {
                localObject2 = ((InvocationTargetException) localThrowable1).getTargetException();
            }
        }
        return 60536;
    }


    public static String s(byte[] paramArrayOfByte) {
        try {
            if ((paramArrayOfByte == null) || (paramArrayOfByte.length <= 1)) {
                throw new f("");
            }
            int i = paramArrayOfByte.length / 2;
            int j = paramArrayOfByte.length - i;
            int n = j - i;
            byte[] arrayOfByte = new byte[i];
            int i1 = 0;
            while (i1 < i) {
                int i2 = paramArrayOfByte[(j + i1)];
                int i3 = paramArrayOfByte[n];
                arrayOfByte[i1] = ((byte) ((i2 ^ i3) & 0xFF));
                i1++;
                n++;
            }
            String str = x.s(arrayOfByte);
            if (!x.u(str)) {
                throw new f("");
            }
            return str;
        } catch (Throwable localThrowable) {
        }
        return null;
    }


    private static class f extends Exception {
        private f(String paramString) {
            super();
        }
    }

    private static final class x {
        private static boolean u(String paramString) {
            try {
                return (paramString != null) && (!paramString.equals(""));
            } catch (Throwable localThrowable) {
            }
            return false;
        }

        private static String H(String paramString) {
            try {
                if (paramString == null) {
                    return "";
                }
                return paramString;
            } catch (Throwable localThrowable) {
            }
            return "";
        }

        private static String s(byte[] paramArrayOfByte) {
            if (paramArrayOfByte == null) {
                return null;
            }
            try {
                return new String(paramArrayOfByte, "UTF-8");
            } catch (Throwable localThrowable) {
            }
            return null;
        }

        private static byte[] t(String paramString) {
            if (paramString == null) {
                return null;
            }
            try {
                return paramString.getBytes("UTF-8");
            } catch (Throwable localThrowable) {
            }
            return null;
        }
    }



    /**
     * 解密工作？？
     */
    public static final class a {

        private static final char[] k = "0123456789abcdef".toCharArray();
        private static final char[] s = "0123456789ABCDEF".toCharArray();

        public static String u(byte[] paramArrayOfByte) {
            return s(paramArrayOfByte, true);
        }

        private static String s(byte[] paramArrayOfByte, boolean paramBoolean) {
            try {
                char[] arrayOfChar = paramBoolean ? k : s;
                StringBuilder localStringBuilder = new StringBuilder();
                for (int m : paramArrayOfByte) {
                    localStringBuilder.append(arrayOfChar[(m >> 4 & 0xF)]);
                    localStringBuilder.append(arrayOfChar[(m & 0xF)]);
                }
                return localStringBuilder.toString();
            } catch (Throwable localThrowable) {
                localThrowable.printStackTrace();
            }
            return null;
        }

        private static void s(Closeable paramCloseable) {
            try {
                if (paramCloseable != null) {
                    paramCloseable.close();
                }
            } catch (Throwable localThrowable) {
                localThrowable.printStackTrace();
            }
        }

        private static byte[] s(File paramFile) {
            Object localObject1 = null;
            byte[] arrayOfByte1;
            byte[] arrayOfByte2;
            try {
                if (paramFile == null) {
                    arrayOfByte1 = null;
                    return arrayOfByte1;
                }

                localObject1 = new FileInputStream(paramFile);
                arrayOfByte1 = k((InputStream) localObject1);

                if (arrayOfByte1 == null) {
                    arrayOfByte2 = null;
                    return arrayOfByte2;
                }

                s((Closeable) localObject1);
                localObject1 = null;

                arrayOfByte2 = arrayOfByte1;
                return arrayOfByte2;
            } catch (FileNotFoundException localFileNotFoundException) {
                arrayOfByte2 = null;
                return arrayOfByte2;
            } catch (Throwable localThrowable) {
                arrayOfByte2 = null;
                return arrayOfByte2;
            } finally {
                s((Closeable) localObject1);
            }
        }

        private static byte[] k(InputStream paramInputStream) {
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            int i = s(paramInputStream, localByteArrayOutputStream);
            if (i < 0) {
                return null;
            }
            return localByteArrayOutputStream.toByteArray();
        }

        private static int s(InputStream paramInputStream, OutputStream paramOutputStream) {
            try {
                if (paramInputStream == null) {
                    return -1;
                }
                if (paramOutputStream == null) {
                    return -1;
                }
                byte[] arrayOfByte = new byte[1024];
                int i = 0;
                int j = 0;
                while ((i = paramInputStream.read(arrayOfByte)) > 0) {
                    paramOutputStream.write(arrayOfByte, 0, i);
                    j += i;
                }
                return j;
            } catch (Throwable localThrowable) {
            }
            return -1;
        }

        private static byte[] H(byte[] paramArrayOfByte) {
            return s(paramArrayOfByte, null, null);
        }

        private static byte[] s(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3) {
            try {
                MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
                if (paramArrayOfByte2 != null) {
                    localMessageDigest.update(paramArrayOfByte2);
                }
                localMessageDigest.update(paramArrayOfByte1);
                if (paramArrayOfByte3 != null) {
                    localMessageDigest.update(paramArrayOfByte3);
                }
                return localMessageDigest.digest();
            } catch (Throwable localThrowable) {
            }
            return null;
        }

        public static String t(byte[] paramArrayOfByte) {
            try {
                byte[] arrayOfByte = H(paramArrayOfByte);
                if ((arrayOfByte == null) || (arrayOfByte.length <= 0)) {
                    return null;
                }
                return u(arrayOfByte);
            } catch (Throwable localThrowable) {
            }
            return null;
        }

        private static byte[] s(String paramString, byte[] paramArrayOfByte) {
            try {
                MessageDigest localMessageDigest = MessageDigest.getInstance(paramString);
                return localMessageDigest.digest(paramArrayOfByte);
            } catch (Throwable localThrowable) {
            }
            return null;
        }

        private static SecureRandom s() {
            try {
                return SecureRandom.getInstance("SHA1PRNG");
            } catch (Throwable localThrowable) {
            }
            return new SecureRandom();
        }
    }

}
