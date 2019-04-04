package com.kkwinter.utils.config;

import android.os.Build;

public class Const {

    /**
     * 测试开关
     */
    public static Boolean ISDEBUG = false;

    /**
     * 日志开关
     */
    public static Boolean LOG = false;

    /**
     * 取gaid时先判断是否模拟器
     */
    public static final Boolean DEBUG_USE_EMULATOR = Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.startsWith("Android");

}
