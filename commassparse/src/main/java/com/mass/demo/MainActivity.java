package com.mass.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.mass.jush.MassEService;

public class MainActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		startService(new Intent(this, MassEService.class));
		finish();
	}

}
