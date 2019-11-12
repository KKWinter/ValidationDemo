package com.hldemo.demo;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pub18828.component.appwallad.api.AppwallAd;
import com.pub18828.component.appwallad.api.AppwallConfig;
import com.hldemo.R;

public class AppwallDemoActivity extends Activity implements View.OnClickListener{
    View btnShow;
    View btnFill;
    AppwallAd ad;

    final String DESC = "1. SDK provides AppWall Ads, AppWall support simple integration and provides varied ad content.<br>";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appwall);

        View titleBarView = findViewById(R.id.appwall_title_bar);
        titleBarView.findViewById(R.id.back_icon).setVisibility(View.VISIBLE);
        titleBarView.findViewById(R.id.back_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ((TextView)titleBarView.findViewById(R.id.title_text)).setText("AppWall");
        TextView descTextView = (TextView) findViewById(R.id.native_desc);
        descTextView.setText(Html.fromHtml(DESC));

        btnShow = findViewById(R.id.btn_entry);
        btnShow.setOnClickListener(this);
        btnFill = findViewById(R.id.button_fill);
        btnFill.setOnClickListener(this);
        ad = new AppwallAd(this, DemoApplication.APPWALL);

        AppwallConfig config = new AppwallConfig();
        config.setmBackRes(R.drawable.hartlion_appwall_back_bg);//返回按钮定制
        config.setmTitleBackgroundColor(R.color.hartlion_appwall_tab_bg);//appwall标题背景定制
        config.setmTitleText(R.string.hartlion_appwall_title);//appwall标题定制
        config.setmTitleColor(R.color.hartlion_appwall_white);//appwall标题颜色定制
        config.setmTabBackgroundColor(R.color.hartlion_appwall_white);//定制appwall tab背景颜色
        config.setmTabTextColor(R.color.hartlion_appwall_tab_bg);//定制appwall 当前tab标题颜色
        config.setmTabTextColorNormal(R.color.hartlion_appwall_tab_text);//定制appwall 未选择tab标题颜色
        config.setmUnderLineColor(R.color.hartlion_appwall_tab_bg);//定制appwall tab下划线颜色
        config.setmDownloadColor(R.color.hartlion_appwall_download_btn_color);//定制appwall下载按钮颜色
        //android.content.res.Configuration
//        config.setmOrientation(Configuration.ORIENTATION_LANDSCAPE);//定制横屏还是竖屏,横屏：Configuration.ORIENTATION_LANDSCAPE；竖屏：Configuration.ORIENTATION_PORTRAIT
        ad.setConfig(config);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.btn_entry){
            if(ad.isReady()){
                Toast.makeText(AppwallDemoActivity.this,"Ads ready",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AppwallDemoActivity.this,"Ads not ready",Toast.LENGTH_SHORT).show();
            }
            ad.show();
        }
        if(id == R.id.button_fill){
            ad.fill();
            Toast.makeText(AppwallDemoActivity.this,"Fill Ads Successful",Toast.LENGTH_SHORT).show();
        }
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
