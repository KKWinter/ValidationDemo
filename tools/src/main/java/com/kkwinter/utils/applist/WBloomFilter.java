package com.kkwinter.utils.applist;


import android.util.Base64;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.zip.CRC32;

public class WBloomFilter {


    private static final int hashCnt = 7;
    private static final int capacity = 512;

    public static String convert(List<String> alist) {
        //runTest();
        return convert(alist, false);
    }

    private static String convert(List<String> alist, boolean isTest) {


        BitSet bs = new BitSet(capacity);

        for (String one : alist) {
            for (int i = 1; i <= hashCnt; i++) {
                bs.set((int) hashWithCRC32(one, i));
            }
        }

        String res = Base64.encodeToString(bitSetToByteArray(bs), Base64.NO_WRAP);

        if (isTest) {
            android.util.Log.d(WBloomFilter.class.getSimpleName(), alist.toString());
            android.util.Log.d(WBloomFilter.class.getSimpleName(), bs.toString());
            android.util.Log.d(WBloomFilter.class.getSimpleName(), res);
            android.util.Log.d(WBloomFilter.class.getSimpleName(), bitSetToByteArray(bs).toString());
            byte[] print = bitSetToByteArray(bs);
            android.util.Log.d(WBloomFilter.class.getSimpleName(), String.valueOf((int) print[1]));

        }

        return res;
    }

    private static byte[] bitSetToByteArray(BitSet bits) {
        byte[] bytes = new byte[(bits.size() + 7) / 8];
        for (int i = 0; i < bits.length(); i++) {
            if (bits.get(i)) {
                bytes[i / 8] |= 1 << (7 - i % 8);
            }
        }
        return bytes;
    }

    private static long hashWithCRC32(String s, int offset) {
        CRC32 crc = new CRC32();
        crc.update((String.valueOf(offset) + s).getBytes());
        return crc.getValue() % capacity;
    }


    public static String[] testString = new String[]{
            "Life begins at the end of your comfort zone",
            "AAA.com",
            "aaa.com",
            "test.com",
            "a.b.c.d.e.f.g"
    };

    public static void runTest() {
        convert(Arrays.asList(testString), true);
    }
}
