package com.box.app.news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fyber.FairBid;
import com.jumpraw.mediation.GCAdManager;
import com.jumpraw.mediation.GCAdNative;
import com.jumpraw.mediation.GCAdSlot;
import com.jumpraw.mediation.GCFullScreenVideoAd;
import com.jumpraw.mediation.GCMedSDK;

public class MainActivity extends AppCompatActivity {

    private GCAdNative gcAdNative;
    private static final String TAG = "MainActivity";
    private GCFullScreenVideoAd gcFullScreenVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            GCAdManager gcAdManager = GCMedSDK.getAdManager();
            gcAdNative = gcAdManager.createAdNative(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        findViewById(R.id.load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadInterstitial();

//                FairBid.showTestSuite(MainActivity.this);
            }
        });

        findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (gcFullScreenVideoAd != null) {
                    gcFullScreenVideoAd.showFullScreenVideoAd(MainActivity.this);
                }

            }
        });
    }


    private void loadInterstitial() {
        //设置⼴广告位参数
        GCAdSlot fullSlot = new GCAdSlot.Builder()
                .setSlotId("10143").build();

        gcAdNative.loadFullScreenVideoAd(fullSlot, new
                GCAdNative.FullScreenVideoAdListener() {
                    @Override
                    public void onError(String message) {
                        Log.i(TAG, "onError: >>>>>>>" + message);
                    }

                    @Override
                    public void onFullScreenVideoAdLoad(GCFullScreenVideoAd ad) {
                        Log.i(TAG, "onFullScreenVideoAdLoad: >>>>>>>");

                        gcFullScreenVideoAd = ad;
                        if (gcFullScreenVideoAd != null) {
                            gcFullScreenVideoAd.setFullScreenVideoAdInteractionListener(new GCFullScreenVideoAd.FullScreenVideoAdInteractionListener() {
                                @Override
                                public void onAdShow() {
                                    Log.i(TAG, "onAdShow: =========");
                                }

                                @Override
                                public void onAdVideoBarClick() {
                                    Log.i(TAG, "onAdVideoBarClick: ===========");
                                }


                                @Override
                                public void onAdClose() {
                                    Log.i(TAG, "onAdClose: ==========");
                                }

                                @Override
                                public void onVideoComplete() {
                                    Log.i(TAG, "onVideoComplete: ========");
                                }
                            });


                        }
                    }
                });
    }


}
