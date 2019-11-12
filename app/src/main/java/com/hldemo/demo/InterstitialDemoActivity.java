package com.hldemo.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hldemo.R;
import com.pub18828.adapters.admob.interstitial.InterstitialAdConfig;
import com.pub18828.component.interstitial.api.InterstitialAd;
import com.pub18828.component.interstitial.api.InterstitialAdListener;
import com.pub18828.component.interstitial.api.InterstitialConfig;
import com.hldemo.demo.extra.AdmobAdManager;
import com.hldemo.demo.extra.MopubAdManager;

public class InterstitialDemoActivity extends Activity {

    final String DESC = "1. SDK provides Interstitial Ads( Support Dialog、Half screen and Full screen 3 styles), you can setup the display time and Ads style according to your App's demands.<br>" +
            "2. You can customize the Interstitial Ads according to your App's UI styles.<br>" +
            "3. Please pre-load Interstitial Ads by \"<b><tt>FILL</tt></b>\" , before \"<b><tt>SHOW</tt></b>\".";
    public static String TAG = InterstitialDemoActivity.class.getSimpleName();

    private RadioGroup mStyleGroup;
    private InterstitialAd mInterstitialAd;
    int mType;

    MopubAdManager mopubAdManager;
    AdmobAdManager admobAdManager;


    final int MP_TYPE = 0;
    final int MOPUB_TYPE = 1;
    final int ADMOB_TYPE = 2;

    int platformType = MP_TYPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);

        View titleBarView = findViewById(R.id.interstitial_title_bar);
        titleBarView.findViewById(R.id.back_icon).setVisibility(View.VISIBLE);
        titleBarView.findViewById(R.id.back_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mopubAdManager = new MopubAdManager();
        mopubAdManager.initInterstitialMopub(this);

        admobAdManager = new AdmobAdManager();
        admobAdManager.initInterstitialAd(this);

        mInterstitialAd = new InterstitialAd(InterstitialDemoActivity.this, DemoApplication.INTER);
        InterstitialConfig config = new InterstitialConfig();
        config.setAdChoiceSwitch(1);
        mInterstitialAd.setInterstitialConfig(config);
        mInterstitialAd.setAdListener(new InterstitialAdListener() {

            @Override
            public void onLoadError(int errCode) {
                Log.i("SDK", "onLoadError:" + errCode);
            }

            @Override
            public void onAdLoaded() {
//                        interstitialAd.show();
                Log.i("SDK", "onAdLoaded");
            }

            @Override
            public void onAdShowed() {
                Log.i("SDK", "onAdShowed");
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


        ((TextView) titleBarView.findViewById(R.id.title_text)).setText("Interstitial");

        TextView descTextView = (TextView) findViewById(R.id.interstitial_desc);

        descTextView.setText(Html.fromHtml(DESC));

        mStyleGroup = (RadioGroup) findViewById(R.id.style_select_group);
        mStyleGroup.check(R.id.dialog_select_btn);
        mType = InterstitialAd.Type.DIALOG;

        mStyleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.dialog_select_btn:
                        mType = InterstitialAd.Type.DIALOG;
                        break;
                    case R.id.half_screen_select_btn:
                        mType = InterstitialAd.Type.HALFSCREEN;
                        break;
                    case R.id.full_screen_select_btn:
                        mType = InterstitialAd.Type.FULLSCREEN;
                        break;
                }
            }
        });


        findViewById(R.id.load_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (platformType) {
                    case MOPUB_TYPE:
                        mopubAdManager.showInterstitialAd(InterstitialDemoActivity.this);
                        break;
                    case ADMOB_TYPE:
                        admobAdManager.showInterstitialAd(InterstitialDemoActivity.this);
                        break;
                    default:
                        mInterstitialAd.setType(mType);
                        mInterstitialAd.show();
                        break;
                }

            }
        });

        findViewById(R.id.fill_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (platformType) {
                    case MOPUB_TYPE:
                        mopubAdManager.loadInterstitialAd(mType);
                        break;
                    case ADMOB_TYPE:
                        InterstitialAdConfig UIConfig = new InterstitialAdConfig();
                        UIConfig.setButtonBgColor(R.color.hartlion_videoad_test_red);
                        admobAdManager.loadInterstitialAd(mType, UIConfig);
                        break;
                    default:
//                        InterstitialAd interstitialAd = new InterstitialAd(InterstitialDemoActivity.this, MyApplication.INTER);
                        mInterstitialAd.fill();
                        Toast.makeText(InterstitialDemoActivity.this, "Fill Ads Successful", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });


    }


    @Override
    protected void onDestroy() {
        mInterstitialAd.destory();
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
