package com.phone.sample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by huangdong on 18/10/9.
 * antony.huang@yeahmobi.com
 */
public class MainActivity extends Activity {


    private TextView textView;
    private TextView textView2;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        Button button = findViewById(R.id.bt);
        textView = findViewById(R.id.tv);


        Button button2 = findViewById(R.id.bt2);
        textView2 = findViewById(R.id.tv2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String icc = getNetworkCountryIso(context);

                textView.setText("当前sim国家是： " + icc);

            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String lang = Locale.getDefault().getLanguage();

                textView2.setText("当前系统语言是： " + lang);

            }
        });
    }


    public static String getNetworkCountryIso(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(
                Context.TELEPHONY_SERVICE);
        return telephonyManager.getNetworkCountryIso();
    }
}
