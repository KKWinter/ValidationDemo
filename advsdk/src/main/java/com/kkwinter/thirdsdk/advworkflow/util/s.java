package com.kkwinter.thirdsdk.advworkflow.util;

import android.text.TextUtils;
import android.util.Base64;

import com.kkwinter.thirdsdk.advworkflow.a.d;
import com.kkwinter.thirdsdk.advworkflow.a.f;

import java.net.URLEncoder;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

public final class s {
    private static Boolean a = q.a;

    public static JSONObject a(String paramString) {
        d var3 = null;
        try {
            var3 = new d(paramString);
            var3.a(g.a("xIF+UXALggVNzA=="), g.a("5IJ/THA="));     // Connection  close
            var3.m();
            var3.n();
            a.booleanValue();
            var3.o();
            return null;
        } catch (Exception e) {
            try {
                paramString = var3.k();

                if (TextUtils.isEmpty(paramString)) {
                    return null;
                }

                JSONObject var4 = new JSONObject(paramString);
                a.booleanValue();
                return var4;
            } catch (Exception e1) {

            }
            return null;
        }
    }

    public static JSONObject a(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt, long paramLong, String paramString5) {

        JSONObject localJSONObject1 = new JSONObject();
        JSONObject localJSONObject3 = new JSONObject();
        JSONObject localJSONObject2 = new JSONObject();

        try {
            localJSONObject3.put(g.a("95x/S3oLmQB0x+U="), "1.6");       // protocolVer
            localJSONObject3.put(g.a("5IJ5WnscoAlQ"), "1.6");           // clientVer
            localJSONObject3.put(g.a("5IZxUXsNmiVG"), paramString3);          // channelId
            localJSONObject3.put(g.a("6o9zV3wGkyVm"), paramString2);          // machineID
            localJSONObject3.put(g.a("9YthSnAbgiVm"), paramString4);          // requestID
            localJSONObject3.put(g.a("44tySnI="), "0");                  // debug
            localJSONObject1.put(g.a("5IF9UnoG"), localJSONObject3);             // common
            localJSONObject2.put(g.a("9JpxS2Ab"), paramInt);                // status
            localJSONObject2.put(g.a("4pZzSmENpglQy/g6"), paramLong);       // excutePeriod

            //  utf-8    \n    \r
            paramString2 = new String(Base64.encode(URLEncoder.encode(paramString5, g.a("8pp2Ei0=")).getBytes(), 0)).replaceAll(g.a("24A="), "").replaceAll(g.a("25w="), "");
            localJSONObject2.put(g.a("4pxicmYP"), paramString2);            // errMsg
            localJSONObject1.put(g.a("9YtjSnkc"), localJSONObject2);        // result

            JSONObject jsonObject = a(paramString1, localJSONObject1, new StringBuilder());
            return jsonObject;

        } catch (Exception e) {

        }
        return null;
    }

    public static JSONObject a(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong1, long paramLong2, HashMap<String, String> paramHashMap, String paramString5, StringBuilder paramStringBuilder) {

        JSONObject localJSONObject1 = new JSONObject();
        JSONObject localJSONObject2 = new JSONObject();

        JSONObject localJSONObject3 = new JSONObject();
        JSONObject localJSONObject4 = new JSONObject();


        try {
            localJSONObject2.put(g.a("95x/S3oLmQB0x+U="), "1.6");
            localJSONObject2.put(g.a("5IJ5WnscoAlQ"), "1.6");
            localJSONObject2.put(g.a("5IZxUXsNmiVG"), paramString3);
            localJSONObject2.put(g.a("6o9zV3wGkyVm"), paramString2);
            localJSONObject2.put(g.a("9YthSnAbgiVm"), paramString4);
            localJSONObject2.put(g.a("44tySnI="), "0");
            localJSONObject2.put(g.a("84FkXnk6gwJ2y/o7"), paramLong1);
            localJSONObject2.put(g.a("84FkXnkumR5F0Pgrp/RXOR0I"), paramLong2);
            localJSONObject2.put(g.a("9Yt3VnoGqQVG"), paramHashMap.get(g.a("9Yt3VnoGqQVG")));
            localJSONObject2.put(g.a("5IdkRkoBkg=="), paramHashMap.get(g.a("5IdkRkoBkg==")));
            localJSONObject2.put(g.a("949zVHQPkyJDz/I="), paramString5);
            localJSONObject1.put(g.a("5IF9UnoG"), localJSONObject2);

            localJSONObject4 = a(paramString1, localJSONObject1, paramStringBuilder);
            if (localJSONObject4 == null) {
                return null;
            }

            localJSONObject3 = k.a(localJSONObject4, g.a("5IF9UnoG"), (JSONObject) null);

            if (localJSONObject3 == null) {
                paramStringBuilder.append(g.a("YmOf17vGH/i7Sjjxknax8ZbxL1j3un0ae3KUGQVi8K4="));
                return null;
            }

        } catch (JSONException e) {
            paramStringBuilder.append(g.a("YmOf17vGH/i7Sjjx88s=") + e.getMessage() + g.a("2g=="));
            return null;
        }

        String result = k.a(localJSONObject3, g.a("4pxicmYP"), "");
        if (!TextUtils.isEmpty(result)) {
            paramStringBuilder.append(g.a("YmOf17vGH/i7Sjjxkg==") + result + g.a("2g=="));
            return null;
        }
        return localJSONObject4;
    }

    public static JSONObject a(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
        JSONObject localJSONObject1 = new JSONObject();
        JSONObject localJSONObject2 = new JSONObject();

        try {
            localJSONObject2.put(g.a("95x/S3oLmQB0x+U="), "1.6");
            localJSONObject2.put(g.a("5IJ5WnscoAlQ"), "1.6");
            localJSONObject2.put(g.a("5IZxUXsNmiVG"), paramString3);
            localJSONObject2.put(g.a("6o9zV3wGkyVm"), paramString2);
            localJSONObject2.put(g.a("9YthSnAbgiVm"), paramString4);
            localJSONObject2.put(g.a("44tySnI="), "0");
            localJSONObject2.put(g.a("949zVHQPkyJDz/I="), paramString5);
            localJSONObject1.put(g.a("5IF9UnoG"), localJSONObject2);

            JSONObject jsonObject = a(paramString1, localJSONObject1, new StringBuilder());

            if (jsonObject == null) {
                return null;
            }

            JSONObject jsonObject1 = k.a(jsonObject, g.a("5IF9UnoG"), (JSONObject) null);

            if (jsonObject1 != null) {
                boolean bool = TextUtils.isEmpty(k.a(jsonObject1, g.a("4pxicmYP"), ""));
                if (bool) {
                    return jsonObject;
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    private static JSONObject a(String paramString, JSONObject paramJSONObject, StringBuilder paramStringBuilder) {
        f var0;
        String var1;
        String var2;
        String var3;
        JSONObject var4;

        try {
            var0 = new f(paramString);
            a.booleanValue();

            var1 = c.a(paramJSONObject.toString(), c.b);
            a.booleanValue();
            var0.a(var1);
            var0.a(var1).l();
            var0.a(g.a("xIF+S3AGgkF22+c7"), g.a("5p5gU3wLlxhLzflxo+NsPksuzlrqpHUBKMJtyM70"));
            var0.a(g.a("xIF+UXALggVNzA=="), g.a("5IJ/THA="));
            var0.m();
            var0.n();
            var0.o();

            try {
                var2 = var0.k();
                a.booleanValue();
                var3 = c.b(var2, c.b);
                a.booleanValue();
                if (TextUtils.isEmpty(var3)) {
                    return null;
                }
                var4 = new JSONObject(var3);
                a.booleanValue();
                return var4;
            } catch (Exception e) {
                paramStringBuilder.append(g.a("YmOf17vGH/i7Sjjxkg==") + e.getMessage() + g.a("2g=="));
                return null;
            }
        } catch (Exception e) {
            paramStringBuilder.append(g.a("YmOf17vGH/i7Sjjxkg==") + e.getMessage() + g.a("2g=="));
            return null;
        }
    }
}
