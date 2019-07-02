package com.mass.jush;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;

import org.mass.core.MyActivity;


public class MassActivity extends Activity {
	public static final String TM = "tm";
	private MyActivity myActivity = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		String name = intent.getStringExtra(TM);
		myActivity = new MyActivity();
		myActivity.onCreate(this, name, savedInstanceState);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		myActivity.onRestart();
	}

	@Override
	protected void onStart() {
		super.onStart();
		myActivity.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		myActivity.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		myActivity.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		myActivity.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		myActivity.onDestroy();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		myActivity.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Object obj = myActivity.onKeyDown(keyCode, event);
		if(obj!=null && obj instanceof Boolean){
			return (Boolean)obj;
		}
		return super.onKeyDown(keyCode, event);
	}
}
