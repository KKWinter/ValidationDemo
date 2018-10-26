package com.cloudtech.antony;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by huangdong on 18/9/4.
 * antony.huang@yeahmobi.com
 */
public class FileWrite {


    private static String path = Environment.getExternalStorageDirectory().getPath() + "Log/log.txt";

    public static void writeToFile(String str) {

        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write(str.getBytes());
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static void writeToFile() {

        String str = "hello world!";

        FileWriter writer;
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }

            writer = new FileWriter(path);
            writer.write(str);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
