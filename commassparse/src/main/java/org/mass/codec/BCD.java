package org.mass.codec;

/**
 * Created by zhj_7 on 2016/12/7.
 */

public class BCD {

    public static int L4BCD2INT(byte[] bytes){
        int sum = 0;
        sum += (bytes[0] & 0x0F)*1000;
        sum += (bytes[1] & 0x0F)*100;
        sum += (bytes[2] & 0x0F)*10;
        sum += (bytes[3] & 0x0F);
        return sum;
    }

    public static byte[] INT2L4BCD(int i){
        byte[] bytes = new byte[4];
        int tmp = i%1000;
        bytes[0] = (byte) ((byte)(i/1000) | 0xF0);
        bytes[1] = (byte) ((byte)(tmp/100) | 0xF0);
        tmp = tmp%100;
        bytes[2] = (byte) ((byte)(tmp/10) | 0xF0);
        tmp = tmp%10;
        bytes[3] = (byte) ((byte)(tmp) | 0xF0);
        return bytes;
    }
}
