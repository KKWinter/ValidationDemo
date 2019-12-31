package com.jumpraw.hook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

public class SecondActivity extends Activity {

    private static final String TAG = "hook";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        Log.i(TAG, "onCreate: >>>" + url);
    }
}
