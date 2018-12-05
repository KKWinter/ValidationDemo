package com.phone.ndkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * Created by huangdong on 2018/11/1.
 * antony.huang@yeahmobi.com
 */
public class MainActivity extends Activity {

    private static final String TAG = "ndk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.ndk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String str = JNITest.get();

                Log.i(TAG, "onClick: >>>>>> " + str);


            }
        });
    }
}
