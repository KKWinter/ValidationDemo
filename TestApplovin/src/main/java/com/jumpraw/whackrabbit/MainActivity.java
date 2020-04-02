package com.jumpraw.whackrabbit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.adview.AppLovinInterstitialAdDialog;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdSize;
import com.applovin.sdk.AppLovinSdk;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private AppLovinAd loadedAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();

        AppLovinSdk.initializeSdk(context);


        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Load an Interstitial Ad
                AppLovinSdk.getInstance(context).getAdService().loadNextAd(AppLovinAdSize.INTERSTITIAL, new AppLovinAdLoadListener() {

                    @Override
                    public void adReceived(AppLovinAd ad) {
                        Log.i("Test", "adReceived: >>>> succeed");
                        loadedAd = ad;
                    }

                    @Override
                    public void failedToReceiveAd(int errorCode) {
                        // Look at AppLovinErrorCodes.java for list of error codes.
                        Log.i("Test", "failedToReceiveAd: >>>" + errorCode);
                    }
                });


            }
        });


        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppLovinInterstitialAdDialog interstitialAd = AppLovinInterstitialAd.create(AppLovinSdk.getInstance(context), context);

                // Optional: Assign listeners
//                interstitialAd.setAdDisplayListener( ... );
//                interstitialAd.setAdClickListener( ... );
//                interstitialAd.setAdVideoPlaybackListener( ... );

                interstitialAd.showAndRender(loadedAd);

            }
        });
    }
}
