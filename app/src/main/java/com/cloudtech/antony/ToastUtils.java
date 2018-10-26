package com.cloudtech.antony;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by huangdong on 18/8/29.
 * antony.huang@yeahmobi.com
 */
public class ToastUtils {


    private static Toast toast;

    public static void show(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }

        toast.setText(msg);
        toast.show();
    }
}
