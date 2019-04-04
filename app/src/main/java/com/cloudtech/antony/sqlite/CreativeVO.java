package com.cloudtech.antony.sqlite;

import android.text.TextUtils;

/**
 * Created by huangdong on 17/12/5.
 *
 */

public class CreativeVO {

    //服务端获取的数据
    public String cid;
    private String url;
    private int status;    //下载状态，0未下载； 1下载成功


    public CreativeVO() {

    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CreativeVO{" +
                "cid='" + cid + '\'' +
                ", url='" + url + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof CreativeVO){
            CreativeVO creative = (CreativeVO) obj;
            return cid.equals(creative.cid);
        }
        return false;
    }


    public enum CreativeType {

        jpg,
        mp4,
        unknown;

        public static CreativeType getType(String strType) {

            if (!TextUtils.isEmpty(strType)) {
                if(contains(strType)) {
                    return CreativeType.valueOf(strType);
                }
            }
            return unknown;
        }
        /*
        判断字符串是否存在于枚举类型中
         */
        public static boolean contains(String value){
            CreativeType[] values = CreativeType.values();
            for (CreativeType type : values) {
                if (value.equals(type.name())){
                    return true;
                }
            }
            return false;
        }
    }
}
