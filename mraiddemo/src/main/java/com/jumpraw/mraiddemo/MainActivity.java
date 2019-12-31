package com.jumpraw.mraiddemo;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.jumpraw.mraid.internal.MRAIDLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    private String[] TAB_LABELS = {"Banner", "Interstitial", "About"};

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MRAIDLog.setLoggingLevel(MRAIDLog.LOG_LEVEL.verbose);
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

        });
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }


    public static String getAssets(Context context, String filename) {
        try {
            InputStream is = context.getAssets().open(filename);
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = bufferReader.readLine()) != null) {
                sb.append(line).append(System.getProperty("line.separator"));
            }
            bufferReader.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String[] getTestFiles(Context context, String PREFIX) {
        String[] files = null;
        List<String> testFiles = new ArrayList<String>();
        try {
            files = context.getAssets().list("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String file = files[i];
                if (file.startsWith(PREFIX)) {
                    String displayName = file.replace(PREFIX + ".", "");
                    displayName = displayName.replace(".html", "");
                    testFiles.add(displayName);
                }
            }
        }
        return testFiles.toArray(new String[testFiles.size()]);
    }
}
