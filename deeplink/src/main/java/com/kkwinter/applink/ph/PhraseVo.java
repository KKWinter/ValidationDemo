package com.kkwinter.applink.ph;

import org.json.JSONObject;

public class PhraseVo {

    public String pCode;   //口令字符串
    public String pTime;     //口令延迟时间

    public PhraseVo(String pCode, String pTime) {
        this.pCode = pCode;
        this.pTime = pTime;
    }

    public PhraseVo(JSONObject jsonObject) {
        pCode = jsonObject.optString("p");
        pTime = jsonObject.optString("l");
    }


    @Override
    public String toString() {
        return "PhraseVo{" +
                "pCode='" + pCode + '\'' +
                ", pTime=" + pTime +
                '}';
    }
}
