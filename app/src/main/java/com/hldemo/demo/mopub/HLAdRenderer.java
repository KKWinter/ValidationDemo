package com.hldemo.demo.mopub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.pub18828.adapters.mopub.nativead.HLStaticNativeAd;
import com.mopub.nativeads.BaseNativeAd;
import com.mopub.nativeads.MoPubAdRenderer;
import com.mopub.nativeads.NativeClickHandler;
import com.mopub.nativeads.StaticNativeAd;

public class HLAdRenderer implements MoPubAdRenderer<HLStaticNativeAd>{


    private final HLViewBinder mViewBinder;
    NativeClickHandler nativeClickHandler;
    Context mContext;


    public HLAdRenderer(final HLViewBinder viewBinder){
        mViewBinder = viewBinder;
    }

    @NonNull
    @Override
    public View createAdView(@NonNull Context context, @Nullable ViewGroup parent) {

        this.mContext = context;
        return LayoutInflater
                .from(context)
                .inflate(mViewBinder.layoutId, parent, false);
    }

    /**
     * 在该方法中渲染视图
     * @param view
     * @param ad
     */
    @Override
    public void renderAdView(@NonNull View view, @NonNull HLStaticNativeAd ad) {

       TextView titleTv = (TextView) view.findViewById(mViewBinder.titleId);
        titleTv.setText(ad.getTitle());

        TextView contextTv = (TextView) view.findViewById(mViewBinder.textId);
        contextTv.setText(ad.getText());

        TextView ctaTV = (TextView) view.findViewById(mViewBinder.callToActionId);
        if(ctaTV != null){
            ctaTV.setText(ad.getCallToAction());
        }


        ad.prepare(view);
        SimpleDraweeView iconImg = (SimpleDraweeView) view.findViewById(mViewBinder.iconImageId);
        iconImg.setImageURI(ad.getIconImageUrl());

    }

    @Override
    public boolean supports(@NonNull BaseNativeAd nativeAd) {
        return nativeAd instanceof StaticNativeAd;
    }
}
