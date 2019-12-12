package com.hldemo.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hldemo.R;
import com.pub18828.core.api.SDK;
import com.hldemo.demo.adapter.DividerGridItemDecoration;
import com.hldemo.demo.adapter.HomeAdapter;
import com.hldemo.demo.adapter.HomeBean;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends Activity {

    RecyclerView mAdTypeSelectView;
    HomeAdapter mHomeAdapter;
    List<HomeBean> mDataList;
    final int[] IMAGE_IDS = new int[]{R.drawable.native_icon, R.drawable.appwall_icon, R.drawable.banner_icon, R.drawable.interstitial_icon, R.drawable.video_icon};
    final String[] ITEM_NAMES = new String[]{"Native", "AppWall", "Banner", "Interstitial", "Video"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        View titleBarView = findViewById(R.id.home_title_bar);
        titleBarView.findViewById(R.id.back_icon).setVisibility(View.GONE);
        ((TextView) titleBarView.findViewById(R.id.title_text)).setText(R.string.app_name);

        mAdTypeSelectView = (RecyclerView) findViewById(R.id.ad_type_select_view);
        mAdTypeSelectView.setLayoutManager(new GridLayoutManager(this, 2)); //设置两列

        mDataList = new ArrayList<>();
        for (int i = 0; i < IMAGE_IDS.length; i++) {
            HomeBean homeBean = new HomeBean();
            homeBean.mImageId = IMAGE_IDS[i];
            homeBean.mTitle = ITEM_NAMES[i];
            mDataList.add(homeBean);
        }
        mHomeAdapter = new HomeAdapter(this, mDataList);
        mAdTypeSelectView.setAdapter(mHomeAdapter);

        mHomeAdapter.setItemClickListener(new HomeAdapter.ItemClickListener() {
            @Override
            public void onClick(HomeBean homeBean) {
                if (homeBean.mTitle.equals("Native")) {
                    startActivity(new Intent(HomeActivity.this, NativeDemoActivity.class));
                }
                if (homeBean.mTitle.equals("AppWall")) {
                    startActivity(new Intent(HomeActivity.this, AppwallDemoActivity.class));
                }
                if (homeBean.mTitle.equals("Banner")) {
                    startActivity(new Intent(HomeActivity.this, BannerDemoActivity.class));
                }
                if (homeBean.mTitle.equals("Interstitial")) {
                    startActivity(new Intent(HomeActivity.this, InterstitialDemoActivity.class));
                }
                if (homeBean.mTitle.equals("Video")) {
                    startActivity(new Intent(HomeActivity.this, VideoDemoActivity.class));
                }


            }
        });


        mAdTypeSelectView.addItemDecoration(new DividerGridItemDecoration(this));


        Log.i("hartlion", "level:" + SDK.getUploadDataLevel(this));
    }

    static boolean installing = false;
    static boolean downloading = false;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
