package com.mass.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mass.jush.MassEService;

import org.mass.content.ContentFormat;
import org.mass.content.ContentParser;
import org.mass.core.ContentManager;

import java.io.File;

public class MainActivity extends Activity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();

        startService(new Intent(MainActivity.this, MassEService.class));
//		finish();

    }

}
