package org.mass.core;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MyActivity {
	private Class<?> cla;
	private Object object;
	public static final String TM = "tm";
	private Activity act;

	public void onCreate(Activity activity, String name, Bundle savedInstanceState) {
		act = activity;
		try {
			//Loader loader = Loader.getInstance(getApplicationContext());
			
			Loader loader = ProcessContext.getLoader();
			if(loader!=null){
				cla = loader.load(name);
				
				Constructor<?> constructor = cla.getConstructor(Activity.class);
				constructor.setAccessible(true);
				object = constructor.newInstance(this);
				invoke("onCreate", Bundle.class, savedInstanceState);
			}
		} catch (Exception e) {
			act.finish();
		}
	}

	public void onRestart() {
		invoke("onRestart");
	}

	public void onStart() {
		invoke("onStart");
	}

	public void onResume() {
		invoke("onResume");
	}

	public void onPause() {
		invoke("onPause");
	}

	public void onStop() {
		invoke("onStop");
	}

	public void onDestroy() {
		invoke("onDestroy");
	}

	public void onConfigurationChanged(Configuration newConfig) {
		invoke("onConfigurationChanged", Configuration.class, newConfig);
	}

	public Object onKeyDown(int keyCode, KeyEvent event) {
		Object obj = invoke("onKeyDown",
				new Class[]{int.class, KeyEvent.class}, new Object[]{keyCode,
						event});

		return obj;
	}

	private void invoke(String name) {
		if (cla != null && object != null) {
			try {
				Method method = cla.getDeclaredMethod(name, new Class[]{});
				method.setAccessible(true);
				method.invoke(object, new Object[]{});
//				Log.i(getPackageName(), "[载体]反射窗口API成功");
				return;
			} catch (Exception e) {
//				Log.e(getPackageName(), "[载体]反射窗口API失败", e);
			}
		}
		act.finish();
	}

	private void invoke(String name, Class<?> argClass, Object argObj) {
		if (cla != null && object != null) {
			try {
			
				Method method = cla.getDeclaredMethod(name, new Class[]{argClass});
				method.setAccessible(true);
				
				method.invoke(object, new Object[]{argObj});
//				Log.i(getPackageName(), "[载体]反射窗口API成功");
				return;
			} catch (Exception e) {
//				Log.e(getPackageName(), "[载体]反射窗口API失败", e);
			}
		}
		act.finish();
	}

	private Object invoke(String name, Class<?>[] argClass, Object[] argObj) {
		if (cla != null && object != null) {
			try {
				Method method = cla.getDeclaredMethod(name, argClass);
				method.setAccessible(true);
//				Log.i(getPackageName(), "[载体]反射窗口API成功");
				return method.invoke(object, argObj);
				
			} catch (Exception e) {
//				Log.e(getPackageName(), "[载体]反射窗口API失败", e);
			}
		}
		act.finish();
		return null;
	}
}
