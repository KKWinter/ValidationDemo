package com.kkwinter.utils.aes;


import com.kkwinter.utils.WLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * @author Duan
 */
public final class RijindaelUtils {


    /**
     * 加密算法
     *
     * @param content：明文
     * @param password：密码
     * @return
     */
    public static String encrypt(String content, String password) {
        if (isEmpty(content) || isEmpty(password)) {
            return null;
        }

        try {
            ArrayList<byte[]> ptList = new ArrayList<byte[]>();//明文的byte字节块列表
            ArrayList<byte[]> ctList = new ArrayList<byte[]>();//密文的byte字节块列表

            //拿到明文之后进行分块(每16个字节为一块)，不足16个字节的块使用随机数填充
            byte[] contentBytes = null;
            int len = content.getBytes().length;//明文的字节长度(非中文的)
            int mod = len % 16;

            if (mod != 0) {
                Random random = new Random();
                String sub = "";
                for (int i = 0; i < 16 - mod; i++) {
                    sub += (char) (random.nextInt(10) + 48) + "";//转成ASCII码
                }
                content = content + sub;
            }
            contentBytes = content.getBytes();//获取到明文的字节

            /**将上面操作完的明文字节数组进行分块，并且存到列表中**/
            int block = contentBytes.length / 16;
            for (int i = 0; i < block; i++) {
                byte[] temp = new byte[16];
                int k = 0;
                for (int j = i * 16; j < i * 16 + 16; j++) {
                    temp[k] = contentBytes[j];
                    k++;
                }
                ptList.add(temp);
            }

            /**对每一个块进行加密，加密之后的内容存到ctList列表中*/
            for (int i = 0; i < ptList.size(); i++) {
                ctList.add(encrypt(password, ptList.get(i)));
            }

            String lenStr = String.valueOf(len);
            StringBuilder sbs = new StringBuilder();
            for (int i = 0; i < lenStr.length(); i++) {
                sbs.append('0');
                sbs.append(lenStr.charAt(i));
            }

            byte[] failByte = new byte[16];
            String failStr = "00000000000000000000000000000000";
            //比如:长度为12映射为:00000000000...0012,共16个字节
            failStr = failStr.substring(0, (failStr.length() - sbs.toString().length())) + sbs.toString();
            failByte = hexStr2Bytes(failStr);
            for (int i = 0; i < failByte.length; i++) {
                failByte[i] += 48;
            }

            //将明文的长度的字节数据进行加密存到ctList的尾部
            ctList.add(encrypt(password, failByte));

            /**将密文转化成十六进制字符串*/
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < ctList.size(); i++) {
                sb.append(byte2HexStr(ctList.get(i)));
                sb.append("");
            }

            return sb.toString().trim();
        } catch (Exception e) {
            WLog.d("加密失败");
        }

        return null;
    }

    /**
     * 根据hex和binary类型，解密
     *
     * @param content：解密内容
     * @param password:解密密码
     * @return
     */
    public static byte[] decryptByType(byte[] content, String password, String type) throws Exception {

        //如果解密内容和解密密码无效就不进行解密，直接返回null
        if (content == null || isEmpty(password)) {
            return null;
        }

        //将解密的内容转成字节数组
        byte[] contentByte;
        if ("hex".equals(type)) {  //十六进制解密
            contentByte = hexStr2Bytes(new String(content));
        } else if ("binary".equals(type)) {  //二进制解密
            contentByte = content;
        } else {
            return null;
        }

        //得到密文的块数
        int block = contentByte.length / 16;

        ArrayList<byte[]> ct = new ArrayList<byte[]>();//密文的Bytes块列表
        ArrayList<byte[]> pt = new ArrayList<byte[]>();//明文的Bytes块列表

        /**将密文进行分块，并且存到ct列表中**/
        for (int i = 0; i < block; i++) {
            byte[] temp = new byte[16];
            for (int j = 0; j < 16; j++) {
                temp[j] = contentByte[i * 16 + j];
            }
            ct.add(temp);
            temp = null;
        }

        /**将密文块进行解密，然后存到pt列表中**/
        for (int i = 0; i < ct.size(); i++) {
            pt.add(decrypt(password, ct.get(i)));
        }

        //得到明文的长度
        for (int i = 0; i < pt.get(pt.size() - 1).length; i++) {
            pt.get(pt.size() - 1)[i] -= 48;
        }

        StringBuilder lenStr = new StringBuilder();
        byte[] lenByte = pt.get(pt.size() - 1);
        for (int i = 0; i < lenByte.length; i++) {
            lenStr.append(String.valueOf(lenByte[i]));
        }

        int lens = Integer.valueOf(lenStr.toString());

        /**取得有效的明文长度数据*/
        byte[] contentBytes = new byte[pt.size() * 16];
        int location = 0;
        for (int i = 0; i < pt.size(); i++) {
            byte[] temp = pt.get(i);
            for (int j = 0; j < temp.length; j++) {
                contentBytes[location] = temp[j];
                location++;
            }
            temp = null;
        }

        return Arrays.copyOf(contentBytes, lens);
    }


    /**
     * 解密算法
     *
     * @param content：解密内容, 十六进制
     * @param password:解密密码
     * @return
     */
    public static String decrypt(String content, String password) throws Exception {

        //如果解密内容和解密密码无效就不进行解密，直接返回null
        if (isEmpty(content) || isEmpty(password)) {
            return null;
        }

        //将解密的内容转成字节数组(因为加密之后的是十六进制的字符串)
        byte[] contentByte = hexStr2Bytes(content);
        //得到密文的块数
        int block = contentByte.length / 16;

        ArrayList<byte[]> ct = new ArrayList<byte[]>();//密文的Bytes块列表
        ArrayList<byte[]> pt = new ArrayList<byte[]>();//明文的Bytes块列表

        /**将密文进行分块，并且存到ct列表中**/
        for (int i = 0; i < block; i++) {
            byte[] temp = new byte[16];
            for (int j = 0; j < 16; j++) {
                temp[j] = contentByte[i * 16 + j];
            }
            ct.add(temp);
            temp = null;
        }

        /**将密文块进行解密，然后存到pt列表中**/
        for (int i = 0; i < ct.size(); i++) {
            pt.add(decrypt(password, ct.get(i)));
        }

        //得到明文的长度
        for (int i = 0; i < pt.get(pt.size() - 1).length; i++) {
            pt.get(pt.size() - 1)[i] -= 48;
        }

        StringBuilder lenStr = new StringBuilder();
        byte[] lenByte = pt.get(pt.size() - 1);
        for (int i = 0; i < lenByte.length; i++) {
            lenStr.append(String.valueOf(lenByte[i]));
        }

        int lens = Integer.valueOf(lenStr.toString());

        /**取得有效的明文长度数据*/
        byte[] contentBytes = new byte[pt.size() * 16];
        int location = 0;
        for (int i = 0; i < pt.size(); i++) {
            byte[] temp = pt.get(i);
            for (int j = 0; j < temp.length; j++) {
                contentBytes[location] = temp[j];
                location++;
            }
            temp = null;
        }
        return new String(contentBytes, 0, lens);
    }

    /**
     * Rijindael的加密算法
     *
     * @param passsword：加密密码
     * @param content:加密内容的byte数组
     * @return
     */
    private static byte[] encrypt(String passsword, byte[] content) throws Exception {
        Rijndael rijndael = new Rijndael();
        rijndael.makeKey(passsword.getBytes(), 256);//采用128的加密密钥
        byte[] ct = new byte[16];//对16bytes进行加密
        rijndael.encrypt(content, ct);
        return ct;
    }

    /**
     * Rijindael的解密算法
     *
     * @param passsword：解密密码
     * @param content：解密内容的byte数组
     * @return
     */
    private static byte[] decrypt(String passsword, byte[] content) throws Exception {
        Rijndael rijndael = new Rijndael();
        rijndael.makeKey(passsword.getBytes(), 256);
        byte[] ct = new byte[16];
        rijndael.decrypt(content, ct);
        return ct;
    }

    /**
     * bytes转换成十六进制字符串
     *
     * @param b b byte数组
     * @return String 每个Byte值之间空格分隔
     */
    public static String byte2HexStr(byte[] b) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
            sb.append("");
        }
        return sb.toString().toUpperCase().trim();
    }

    /**
     * bytes字符串转换为Byte值
     *
     * @param hexString src Byte字符串，每个Byte之间没有分隔符
     * @return byte[]
     */
    public static byte[] hexStr2Bytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * int类型转化成byte数组
     *
     * @param res
     * @return
     */
    public static byte[] int2byte(int res) {
        byte[] targets = new byte[4];
        targets[0] = (byte) (res & 0xff);//最低位
        targets[1] = (byte) ((res >> 8) & 0xff);//次低位
        targets[2] = (byte) ((res >> 16) & 0xff);//次高位
        targets[3] = (byte) (res >>> 24);//最高位,无符号右移。
        return targets;
    }

    /**
     * byte数组转成int类型
     *
     * @param res
     * @return
     */
    public static int byte2int(byte[] res) {
        int targets = 0;
        targets += res[3];
        targets += res[2] * (2 << 8);
        targets += res[1] * (2 << 16);
        targets += res[0] * (2 << 24);
        return targets;
    }

    /**
     * 将64位的十六进制的密钥库转成32位的字符串密钥
     *
     * @param hexStr
     * @return
     */
    public static String hexPasswordToStrPassword(String hexStr) {
        byte[] bytes = RijindaelUtils.hexStr2Bytes(hexStr);
        char[] charAry = new char[32];
        for (int i = 0; i < bytes.length; i++) {
            char a = (char) (bytes[i]);
            charAry[i] = a;
        }
        return String.valueOf(charAry);
    }

    /**
     * 判断字符串不为空
     *
     * @param content
     * @return
     */
    public static boolean isEmpty(String content) {
        return content == null || "".equals(content);
    }
}
