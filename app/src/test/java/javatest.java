
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by huangdong on 18/3/16.
 * antony.huang@yeahmobi.com
 */

public class javatest {

    @Test
    public void test() {


    }


    public static void writeInFile() {
        File f = new File("/Users/huangdong/Desktop/test.txt");
        String content = "aaaaaaaa";
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            if (!f.exists()) {
                f.mkdir();
            }
            fw = new FileWriter(f.getAbsoluteFile(), true);  //true表示可以追加新内容
            bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //按照字节读取文件内容
    public static String readFileByByte() {
        String s = "";
        File f = new File("/Users/huangdong/Desktop/test.txt");

        InputStream in = null;
        try {
            in = new FileInputStream(f);
            int tempByte;
            while ((tempByte = in.read()) != -1) {
                System.out.println(tempByte);
                s += tempByte;
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("content:" + s);
        return s;
    }


    private byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len = 0;
        while ((len = is.read(b, 0, 1024)) != -1) {
            baos.write(b, 0, len);
            baos.flush();
        }
        return baos.toByteArray();
    }

}
