package com.kkwinter.thirdsdk.advworkflow.entry;

import android.content.Context;
import java.util.HashMap;
import org.json.JSONObject;

public class WorkFlowEntry {
    private static WorkFlowEntry _a = null;
    private com.kkwinter.thirdsdk.advworkflow.util.r _b = new com.kkwinter.thirdsdk.advworkflow.util.r();
    private boolean _c = false;

    protected WorkFlowEntry() {
    }

    public static WorkFlowEntry shareInstance() {
        synchronized(WorkFlowEntry.class){}

        WorkFlowEntry var0;
        try {
            if (_a != null) {
                var0 = _a;
            } else {
                var0 = new WorkFlowEntry();
                _a = var0;
            }
        } finally {
            ;
        }

        return var0;
    }

    /**
     *
     * @param var1  contxt
     * @param var2  壳中调起dex的classLoader对象
     * @param var3  模块名
     * @param var4  machineID
     * @param var5  requestID
     * @param var6  设备信息，壳中的l
     * @param var7  topActivity
     * @return
     */
    public boolean run(final Context var1, final ClassLoader var2, final String var3, final String var4, final String var5, final JSONObject var6, final HashMap<String, Object> var7) {

        com.kkwinter.thirdsdk.advworkflow.util.j.a(new Runnable() {
            public void run() {

                if (!WorkFlowEntry.this._c) {
                    WorkFlowEntry.this._c = true;
                    WorkFlowEntry.this._b.a(var1, var2, var3, var4, var5, var6, var7);
                }

            }
        }, 1L);
        return true;
    }
}
