package com.jumpraw.webview.sense;

import java.util.Arrays;

public class SenseVo {
    //加载的url
    public String url;

    //获取数据的js
    public String[] jsStrs;


    public SenseVo() {
    }

    @Override
    public String toString() {
        return "SenseVo{" +
                "url='" + url + '\'' +
                ", jsStrs=" + Arrays.toString(jsStrs) +
                '}';
    }
}
