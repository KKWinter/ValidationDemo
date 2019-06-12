package com.jumpraw.mdstd;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.jumpraw.mdstd.hxx.a;

import java.util.Properties;
import java.util.Set;

import static com.jumpraw.mdstd.hxx.a.a;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private static final String TAG = "MainActivity";
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();


        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                sdk.hfn.mvp.RunInvoker.run(context, "aid", "cid");


                System.setProperty("name", "antony");

                String name = System.getProperty("name");
                Log.i(TAG, "onClick: >>" + name);

                Properties properties = System.getProperties();
                Set<String> names = properties.stringPropertyNames();
                for (String s : names) {
                    String result = properties.getProperty(s);
                    Log.i(TAG, "onClick: name> " + s + ", value> " + result);
                }
            }
        });


    }




    private void parselibBoot() {

        try {
//                    File localFile = a(context, "ancor", "cjMd");
//                    Log.i(TAG, "onClick: >>>>" + localFile.getAbsolutePath());

            String result1 = a("cjMd", "8? 18 5< 3: ");
            Log.i(TAG, "onClick: " + result1);

            String result2 = a.a("cjMd", "89 2; 6; ");
            Log.i(TAG, "onClick: " + result2);

            String result3 = a.a("cjMd", "79 59 9; 5? 29 39 6; 5? 59 9: 2> 5? :< 2; 6; 6< 49 9: 5; 69 78 5: ");
            Log.i(TAG, "onClick: " + result3);

            String result4 = a("cjMd", "88 29 4; 99 19 28 :A 4: 7: 6: 8= 28 59 9> :8 28 8: 2< 4; 68 79 6: 28 29 38 59 1< 3: ");
            Log.i(TAG, "onClick: " + result4);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /*
     * 判断设备 是否使用代理上网
     * */
    private boolean isWifiProxy(Context context) {

        final boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;

        String proxyAddress;

        int proxyPort;

        if (IS_ICS_OR_LATER) {

            proxyAddress = System.getProperty("http.proxyHost");

            String portStr = System.getProperty("http.proxyPort");

            proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));

        } else {

            proxyAddress = android.net.Proxy.getHost(context);

            proxyPort = android.net.Proxy.getPort(context);

        }

        return (!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1);

    }
}
