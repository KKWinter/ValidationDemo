package com.jumpraw.mraiddemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jumpraw.mraid.MRAIDInterstitial;
import com.jumpraw.mraid.MRAIDInterstitialListener;
import com.jumpraw.mraid.MRAIDNativeFeature;
import com.jumpraw.mraid.MRAIDNativeFeatureListener;

import androidx.fragment.app.ListFragment;

public class InterstitialFragment extends ListFragment implements MRAIDInterstitialListener, MRAIDNativeFeatureListener {

    private final static String TAG = "InterstitialFragment";
    private final static String PREFIX = "interstitial";

    private MRAIDInterstitial mraidInterstitial;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(),
                android.R.layout.simple_list_item_1,
                MainActivity.getTestFiles(getContext(), PREFIX));
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String name = (String) getListAdapter().getItem(position);
        Log.d(TAG, name);
        String filename = PREFIX + "." + name + ".html";
        String content = MainActivity.getAssets(getContext(), filename);
        try {
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

            final String baseUrl = "file:///android_asset/";

            mraidInterstitial = new MRAIDInterstitial(
                    getActivity(),
                    baseUrl,
                    content,
                    supportedNativeFeatures,
                    this,
                    this);

            Log.d(TAG, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MRAIDInterstitialListener implementation
    @Override
    public void mraidInterstitialLoaded(MRAIDInterstitial mraidInterstitial) {
        Log.d(TAG, "mraidInterstitialLoaded");
        // Show the interstitial ad as soon as it is loaded.
        // In real life, we would probably wait until a more appropriate point of time.
        this.mraidInterstitial.show();
    }

    @Override
    public void mraidInterstitialShow(MRAIDInterstitial mraidInterstitial) {
        Log.d(TAG, "mraidInterstitialShow");
    }

    @Override
    public void mraidInterstitialHide(MRAIDInterstitial mraidInterstitial) {
        Log.d(TAG, "mraidInterstitialHide");
        // Help out the garbage collector.
        this.mraidInterstitial = null;
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
    }

    @Override
    public void mraidNativeFeatureOpenBrowser(String url) {
        Log.d(TAG, "mraidNativeFeatureOpenBrowser " + url);

        // Demo will open the URL in an external browser
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse(url)));
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
