package com.phone.ndkdemo;

/**
 * Created by huangdong on 2018/11/1.
 * antony.huang@yeahmobi.com
 */
public class JNITest {


    // 动态导入 so 库
    static {
        System.loadLibrary("ndkdemo");
    }



    public native static String get();

}
