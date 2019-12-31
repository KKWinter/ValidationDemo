package com.jumpraw.mraiddemo.Utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.Uri;
import android.util.DisplayMetrics;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {

    public static String getAssets(Context context, String fileName) {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open(fileName);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int length;
            while ((length = is.read(bytes, 0, 1024)) != -1) {
                baos.write(bytes, 0, length);
                baos.flush();
            }

            return baos.toString();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static final String GP_HOST = "play.google.com";
    private static final String GP_MARKET_SCHEMA = "market";
    public final static String GOOGLE_PLAY_PKG_NAME = "com.android.vending";

    //检查是不是gp地址
    public static boolean isGooglePlayUrl(String url) {
        try {
            Uri uri = Uri.parse(url);

            return GP_MARKET_SCHEMA.equalsIgnoreCase(uri.getScheme())
                    || GP_HOST.equalsIgnoreCase(uri.getHost());

        } catch (Exception e) {
            return false;
        }
    }


    public static int pxToDp(float px) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return (int) (px / displayMetrics.density + .5f);
    }


    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return (int) (dp * displayMetrics.density + .5f);
    }

}
