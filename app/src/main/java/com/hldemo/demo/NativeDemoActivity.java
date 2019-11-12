package com.hldemo.demo;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hldemo.R;
import com.pub18828.ads.nativead.api.NativeAd;
import com.pub18828.ads.nativead.api.NativeAdListener;
import com.pub18828.core.api.Ad;
import com.pub18828.core.api.AdError;
import com.hldemo.demo.extra.AdmobAdManager;
import com.hldemo.demo.extra.MopubAdManager;

import java.util.ArrayList;
import java.util.List;


public class NativeDemoActivity extends Activity {
    NativeAd nativeAds = null;
    TextView loadingStatus = null;
    List<Ad> mAdList = null;

    View mNativeAdView;
    SimpleDraweeView mAdIcon;
    SimpleDraweeView mFaceBookAdIcon;
    TextView mTitleView;
    TextView mDescView;
    TextView mInstallBtn;

    RelativeLayout mNativeAdContainer;


    final String DESC = "Native Ads Demo, you can customize the ad according to your App's demands.<br>";

    /**使用mopub模式，fb广告会失效，且必须引入mopub的依赖包才能正常使用**/
    MopubAdManager mopubAdManager;
    AdmobAdManager mAdmobAdManager;
    final int MP_TYPE = 0;
    final int MOPUB_TYPE = 1;

    int platformType = MP_TYPE;

    public static String TAG = NativeDemoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mopubAdManager = new MopubAdManager();
        mAdmobAdManager = new AdmobAdManager();

        View titleBarView = findViewById(R.id.native_title_bar);
        titleBarView.findViewById(R.id.back_icon).setVisibility(View.VISIBLE);
        titleBarView.findViewById(R.id.back_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ((TextView) titleBarView.findViewById(R.id.title_text)).setText("Native");

        TextView descTextView = (TextView) findViewById(R.id.native_desc);

        descTextView.setText(Html.fromHtml(DESC));

        mNativeAdContainer = (RelativeLayout) findViewById(R.id.native_ad_area);

        if (platformType == MOPUB_TYPE) {
            mopubAdManager.initMopubNativeAd(this,mNativeAdContainer, R.layout.activity_native_layout
                    , R.id.native_ad_image, R.id.native_ad_title, R.id.native_ad_desc, R.id.native_ad_install_btn);
        } else {
            mNativeAdView = LayoutInflater.from(this).inflate(R.layout.activity_native_layout,mNativeAdContainer, false);
            mAdIcon = (SimpleDraweeView) mNativeAdView.findViewById(R.id.native_ad_image);
            mTitleView = (TextView) mNativeAdView.findViewById(R.id.native_ad_title);
            mDescView = (TextView) mNativeAdView.findViewById(R.id.native_ad_desc);
            mInstallBtn = (TextView) mNativeAdView.findViewById(R.id.native_ad_install_btn);
            mFaceBookAdIcon = (SimpleDraweeView) mNativeAdView.findViewById(R.id.fb_native_ad_logo);
            mNativeAdView.setVisibility(View.INVISIBLE);
            mNativeAdContainer.addView(mNativeAdView);
        }




        findViewById(R.id.fill_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {

                if (platformType == MOPUB_TYPE) {
                    return;
                }

                NativeAd nativeAds = new NativeAd(NativeDemoActivity.this, DemoApplication.NATIVE);

                nativeAds.setAdListener(new NativeAdListener() {

                    @Override
                    public void onLoadError(final AdError error) {
                        Toast.makeText(NativeDemoActivity.this, "Fill Ads Failure", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdLoaded(final List<Ad> ad) {

                    }

                    @Override
                    public void onAdClicked(final Ad ad) {
                        Log.i(TAG, "onAdClicked:" + ad.getId());
                    }

                    @Override
                    public void onAdClickStart(final Ad ad) {
                        Log.i(TAG, "AdClickStart:" + ad.getId());
                    }

                    @Override
                    public void onAdClickEnd(final Ad ad) {
                        Log.i(TAG, "AdClickEnd:" + ad.getId());
                    }


                });
                nativeAds.loadAd(2);
            }
        });
//
        findViewById(R.id.load_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (platformType == MOPUB_TYPE) { //mopub广告请求
                    mopubAdManager.loadNativeAd();
                    return;
                }



                if (nativeAds == null) {
                    nativeAds = new NativeAd(NativeDemoActivity.this, DemoApplication.NATIVE);
                }

                nativeAds.setAdListener(new NativeAdListener() {

                    @Override
                    public void onLoadError(final AdError error) {
                        Log.i(TAG, "error:" + error.getCode() + " msg:" + error.getMessage());
                    }

                    @Override
                    public void onAdLoaded(final List<Ad> ad) {
                        Log.i(TAG, "adLoaded size:" + ad.size());
                        if (ad == null || ad.size() < 0) {
                            return;
                        }
                        mAdList = ad;
//                        loadingStatus.setText("ad loaded, size:" + ad.size());
                        //注册
                        if (ad != null && ad.size() > 0) {
                            Ad adReg = ad.get(0);
                            if(mFaceBookAdIcon!=null) {
                                mFaceBookAdIcon.setVisibility(View.GONE);
                            }

                            mTitleView.setText(adReg.getTitle());
                            mDescView.setText(adReg.getBody());
                            List<View> viewList = new ArrayList<View>();
                            viewList.add(mDescView);
                            viewList.add(mTitleView);
                            viewList.add(mAdIcon);
                            viewList.add(mInstallBtn);
                            nativeAds.registerView(adReg, mNativeAdView, viewList);
                            Uri uri = Uri.parse(adReg.getImageUrl());
                            Uri uri1 = Uri.parse(adReg.getIconUrl());
                            mAdIcon.setImageURI(uri1);
                            mNativeAdView.setVisibility(View.VISIBLE);
                            mInstallBtn.setText(TextUtils.isEmpty(adReg.getCta()) ? "Install" : adReg.getCta());

                        }
                    }

                    @Override
                    public void onAdClicked(final Ad ad) {
                        Log.i(TAG, "onAdClicked:" + ad.getId());
                    }


                    @Override
                    public void onAdClickStart(final Ad ad) {
                        Log.i(TAG, "AdClickStart:" + ad.getId());
                    }

                    @Override
                    public void onAdClickEnd(final Ad ad) {
                        Log.i(TAG, "AdClickEnd:" + ad.getId());
                    }
                });
                nativeAds.loadAd(2);
                Toast.makeText(NativeDemoActivity.this, "Native Ads Showing...", Toast.LENGTH_SHORT).show();

            }
        });


    }


    @Override
    protected void onDestroy() {
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
