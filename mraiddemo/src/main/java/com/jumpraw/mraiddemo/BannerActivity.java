package com.jumpraw.mraiddemo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jumpraw.mraid.MRAIDNativeFeature;
import com.jumpraw.mraid.MRAIDNativeFeatureListener;
import com.jumpraw.mraid.MRAIDView;
import com.jumpraw.mraid.MRAIDViewListener;

public class BannerActivity extends Activity implements MRAIDViewListener, MRAIDNativeFeatureListener {

    private final static String TAG = "BannerActivity";
    private MRAIDView mraidView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String baseUrl = bundle.getString("baseUrl");
        String content = bundle.getString("content");

        ((TextView) findViewById(R.id.name)).setText(name);

        if (baseUrl == null) {
            baseUrl = "file:///android_asset/";
        } else if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthInDip = 320;
        int heightInDip = 50;
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, widthInDip, metrics);
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, heightInDip, metrics);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);

        // For demo purposes, we will support all the MRAID native features.
        // The demo's Android manifest file also needs to contain the associated
        // uses-permission elements.
        String[] supportedNativeFeatures = {
                MRAIDNativeFeature.CALENDAR,
                MRAIDNativeFeature.INLINE_VIDEO,
                MRAIDNativeFeature.SMS,
                MRAIDNativeFeature.STORE_PICTURE,
                MRAIDNativeFeature.TEL,
        };

        mraidView = new MRAIDView(this, baseUrl, content, supportedNativeFeatures, this, this);
        mraidView.setLayoutParams(params);

        RelativeLayout rootView = findViewById(R.id.root_view);
        rootView.addView(mraidView);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    // MRAIDViewListener implementation
    @Override
    public void mraidViewLoaded(MRAIDView mraidView) {
        Log.d(TAG, "mraidViewAdReady");
    }

    @Override
    public void mraidViewExpand(MRAIDView mraidView) {
        Log.d(TAG, "mraidViewExpand");
    }

    @Override
    public void mraidViewClose(MRAIDView mraidView) {
        Log.d(TAG, "mraidViewClose");
    }

    @Override
    public boolean mraidViewResize(MRAIDView mraidView, int width, int height, int offsetX, int offsetY) {
        Log.d(TAG, "mraidViewResize [" + offsetX + "," + offsetY + "] (" + width + "x" + height + ")");
        return true;
    }

    // MRAIDNativeFeatureListener implementation
    @Override
    public void mraidNativeFeatureCallTel(String url) {
        Log.d(TAG, "mraidNativeFeatureCallTel " + url);
    }

    @Override
    public void mraidNativeFeatureCreateCalendarEvent(String eventJSON) {
        Log.d(TAG, "mraidNativeFeatureCreateCalendarEvent " + eventJSON);
    }

    @Override
    public void mraidNativeFeaturePlayVideo(String url) {
        Log.d(TAG, "mraidNativeFeaturePlayVideo " + url);

        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    @Override
    public void mraidNativeFeatureOpenBrowser(String url) {
        Log.d(TAG, "mraidNativeFeatureOpenBrowser " + url);

        // Demo will open the URL in an external browser
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    @Override
    public void mraidNativeFeatureStorePicture(String url) {
        Log.d(TAG, "mraidNativeFeatureStorePicture " + url);
    }

    @Override
    public void mraidNativeFeatureSendSms(String url) {
        Log.d(TAG, "mraidNativeFeatureSendSms " + url);
    }

}
