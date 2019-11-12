package com.hldemo.demo;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hldemo.R;
import com.pub18828.component.banner.api.BannerAdListener;
import com.pub18828.component.banner.api.BannerAdView;
import com.hldemo.demo.extra.AdmobAdManager;
import com.hldemo.demo.extra.MopubAdManager;

public class BannerDemoActivity extends Activity {

    final String DESC = "1. SDK provides Banner Ads, the low integration cost and flexible ad style. We recommend that you place Banner ads at the top or at the bottom of your App.<br>" +
            "2. You can customize the Banner Ads according to your App's UI styles.<br>" +
            "3. We will \"<b><tt>SHOW</tt></b>\" the different ad for each time.";
    public static String TAG = BannerDemoActivity.class.getSimpleName();

    private BannerAdView bannerAdView;
    private RelativeLayout mAreaLayout;
    private View mShowTextView;

    MopubAdManager mopubAdManager;
    AdmobAdManager admobAdManager;

    final int MP_TYPE = 0;
    final int MOPUB_TYPE = 1;
    final int ADMOB_TYPE = 2;

    int platformType = MP_TYPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);


        View titleBarView = findViewById(R.id.banner_title_bar);
        titleBarView.findViewById(R.id.back_icon).setVisibility(View.VISIBLE);
        titleBarView.findViewById(R.id.back_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ((TextView)titleBarView.findViewById(R.id.title_text)).setText("Banner");

        TextView descTextView = (TextView) findViewById(R.id.banner_desc);

        descTextView.setText(Html.fromHtml(DESC));

        mAreaLayout = (RelativeLayout) findViewById(R.id.show_area);

        mShowTextView = findViewById(R.id.show_area_text);


        mopubAdManager = new MopubAdManager();
        mopubAdManager.initMopubBannerView(this, mAreaLayout);

        admobAdManager = new AdmobAdManager();
        admobAdManager.initBannerAd(this, mAreaLayout);


        bannerAdView = new BannerAdView(this, DemoApplication.BANNER);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        mAreaLayout.addView(bannerAdView,params);
        bannerAdView.setAdListener(new BannerAdListener() {
            @Override
            public void onLoadError(int i) {
                Log.i("SDK", "onLoadError:" + i);
            }

            @Override
            public void onAdLoaded() {
                Log.i("SDK", "onAdLoaded");
            }

            @Override
            public void onAdShowed() {
                Log.i("SDK", "onAdShowed");
                mShowTextView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAdClicked() {
                Log.i("SDK", "onAdClicked");
            }

            @Override
            public void onAdClose() {
                Log.i("SDK", "onAdClose");
            }
        });
        View loadView = findViewById(R.id.load_btn);
        loadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (platformType) {
                    case MOPUB_TYPE:
                        mopubAdManager.loadBannerAd();
                        break;
                    case ADMOB_TYPE:
                        admobAdManager.loadBannerAd();
                        break;
                    default:
                        bannerAdView.load();
                        break;
                }
            }
        });


    }



    @Override
    protected void onDestroy() {
        bannerAdView.destroy();
        mopubAdManager.destory();
        admobAdManager.destory();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
