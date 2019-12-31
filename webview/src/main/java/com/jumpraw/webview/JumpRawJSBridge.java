package com.jumpraw.webview;

import android.util.Log;
import android.webkit.JavascriptInterface;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JumpRawJSBridge {

    private static final String TAG = "JumpRawJSBridge";

    public JumpRawJSBridge() {

    }

    @JavascriptInterface
    public static void call(){
        Log.i(TAG, "call: ======= from js");
    }

    @JavascriptInterface
    public void call(String methodName, String params) {

        Log.i(TAG, "call: >>" + methodName);
    }

    @JavascriptInterface
    public void call(String methodName) {
        Log.i(TAG, "call: ==" + methodName);
//        call(methodName, "null");

        MainActivity.compare(this);
    }


    @JavascriptInterface
    public void showSource(String html) {

        Log.d("HTML", html);

        MainActivity.html = html;

        writeHtml(MainActivity.path, html);

    }


    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;

    private void writeHtml(String path, String html) {

        Log.i(TAG, "writeHtml: >>" + path);
        File file = new File(path);

        try {
            if (fileWriter == null) {
                fileWriter = new FileWriter(file);
            }

            if (bufferedWriter == null) {
                bufferedWriter = new BufferedWriter(fileWriter);
            }

            bufferedWriter.write(html + "\n");

            bufferedWriter.close();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
