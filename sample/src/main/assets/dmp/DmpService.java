package com.kkwinter.thirdsdk.dmp;

import android.content.Context;

import com.kkwinter.sdkshell.core.ProductConfig;
import com.kkwinter.utils.CTLog;

/**
 * Created by huangdong on 18/7/30.
 * antony.huang@yeahmobi.com
 */
public class DmpService {

    private static final String TAG = "StackService";

    public static void initDmpFunction(Context context, ProductConfig.Dumpsys dumpsys) {
        CTLog.i(TAG, "initDmpFunction: Dumpsys >> " + dumpsys);

        try {
            if (dumpsys != null) {
                DumpManager.getInstance(context).startDump(dumpsys);
            }
        } catch (Exception e) {
            CTLog.printStackTrace(e);
        }

    }

}
