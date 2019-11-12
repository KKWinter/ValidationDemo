package com.jumpraw.listview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.jumpraw.wrap.JRWrap;
import com.jumpraw.wrap.core.BannerView;
import com.jumpraw.wrap.core.callback.BannerListener;

public class MainActivity extends Activity {

    private Context context;
    private RelativeLayout container;
    private static final String TAG = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        context = this.getApplicationContext();

        container = findViewById(R.id.container);


        findViewById(R.id.wrap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JRWrap.initialize(getApplicationContext(), "10010");
            }
        });

        findViewById(R.id.pro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JRWrap.loadNewsBanner(context, "10010", new BannerListener() {

                    @Override
                    public void onLoadSucceed(BannerView bannerView) {
                        Log.i(TAG, "onLoadSucceed: ");
                        container.removeAllViews();
                        container.addView(bannerView);
                    }

                    @Override
                    public void onShowed() {
                        Log.i(TAG, "onShowed: ");
                    }

                    @Override
                    public void onClicked() {
                        Log.i(TAG, "onClicked: ");
                    }

                    @Override
                    public void onLoadFailed(String errMsg) {
                        Log.i(TAG, "onLoadFailed: " + errMsg);
                    }
                });

            }
        });

        findViewById(R.id.hent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JRWrap.showNewsFlow(context, "10010");

            }
        });

    }
}
