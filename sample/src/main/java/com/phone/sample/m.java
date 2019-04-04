package com.phone.sample;

import java.io.UnsupportedEncodingException;

public class m
{
    public static final byte[] a = { 65, 66, 69, 78, 50, 48, 49, 50, 48, 57, 51, 48, 49, 56, 51, 48 };
    private static final byte[] b = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
    private static final byte[] c = { -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9 };

    public static String a(String paramString)
    {
        try
        {
            byte[] arrayOfByte = a(b(paramString), a);
            return new String(arrayOfByte, "utf-8");
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
        }
        return null;
    }

    public static byte[] a(byte[] paramArrayOfByte, byte paramByte)
    {
        try
        {
            byte[] arrayOfByte = new byte[paramArrayOfByte.length];
            for (int i = 0; i < paramArrayOfByte.length; i++) {
                arrayOfByte[i] = ((byte)(paramArrayOfByte[i] ^ paramByte));
            }
            return arrayOfByte;
        }
        catch (Exception localException) {}
        return null;
    }

    public static byte[] a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    {
        byte[] arrayOfByte = paramArrayOfByte1;
        for (int i = 0; i < paramArrayOfByte2.length; i++) {
            arrayOfByte = a(arrayOfByte, paramArrayOfByte2[i]);
        }
        return arrayOfByte;
    }

    public static byte[] b(String paramString) throws UnsupportedEncodingException {
        byte[] arrayOfByte = paramString.getBytes("iso-8859-1");
        return a(arrayOfByte, 0, arrayOfByte.length);
    }

    private static byte[] a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
        int i = paramInt2 * 3 / 4;
        byte[] arrayOfByte1 = new byte[i];
        int j = 0;
        byte[] arrayOfByte2 = new byte[4];
        int k = 0;
        int m = 0;
        int n = 0;
        int i1 = 0;
        for (m = 0; m < paramInt2; m++)
        {
            n = (byte)(paramArrayOfByte[m] & 0x7F);
            i1 = c[n];
            if (i1 >= -5)
            {
                if (i1 >= -1)
                {
                    arrayOfByte2[(k++)] = (byte) n;
                    if (k > 3)
                    {
                        j += a(arrayOfByte2, 0, arrayOfByte1, j);
                        k = 0;
                        if (n == 61) {
                            break;
                        }
                    }
                }
            }
            else {
                return null;
            }
        }
        byte[] arrayOfByte3 = new byte[j];
        System.arraycopy(arrayOfByte1, 0, arrayOfByte3, 0, j);
        return arrayOfByte3;
    }

    private static int a(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
    {
        int i;
        if (paramArrayOfByte1[(paramInt1 + 2)] == 61)
        {
            i = (c[paramArrayOfByte1[paramInt1]] & 0xFF) << 18 | (c[paramArrayOfByte1[(paramInt1 + 1)]] & 0xFF) << 12;
            paramArrayOfByte2[paramInt2] = ((byte)(i >>> 16));
            return 1;
        }
        if (paramArrayOfByte1[(paramInt1 + 3)] == 61)
        {
            i = (c[paramArrayOfByte1[paramInt1]] & 0xFF) << 18 | (c[paramArrayOfByte1[(paramInt1 + 1)]] & 0xFF) << 12 | (c[paramArrayOfByte1[(paramInt1 + 2)]] & 0xFF) << 6;
            paramArrayOfByte2[paramInt2] = ((byte)(i >>> 16));
            paramArrayOfByte2[(paramInt2 + 1)] = ((byte)(i >>> 8));
            return 2;
        }
        try
        {
            i = (c[paramArrayOfByte1[paramInt1]] & 0xFF) << 18 | (c[paramArrayOfByte1[(paramInt1 + 1)]] & 0xFF) << 12 | (c[paramArrayOfByte1[(paramInt1 + 2)]] & 0xFF) << 6 | c[paramArrayOfByte1[(paramInt1 + 3)]] & 0xFF;
            paramArrayOfByte2[paramInt2] = ((byte)(i >> 16));
            paramArrayOfByte2[(paramInt2 + 1)] = ((byte)(i >> 8));
            paramArrayOfByte2[(paramInt2 + 2)] = ((byte)i);
            return 3;
        }
        catch (Exception localException) {}
        return -1;
    }
}
