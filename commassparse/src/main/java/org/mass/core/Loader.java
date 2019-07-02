package org.mass.core;

import android.content.Context;

import dalvik.system.BaseDexClassLoader;

public class Loader {
	private final static String TAG = "LD";
	
//	private static Loader sInstance;
	
	private BaseDexClassLoader loader;
	
//	private final static String CONTEXT = "WVc1a2NtOXBaQzVqYjI1MFpXNTBMa052Ym5SbGVIUT0=";//"YW5kcm9pZC5jb250ZW50LkNvbnRleHQ=";
//	private final static String LOADER = "WkdGc2RtbHJMbk41YzNSbGJTNUVaWGhEYkdGemMweHZZV1JsY2c9PQ==";//"ZGFsdmlrLnN5c3RlbS5EZXhDbGFzc0xvYWRlcg==";
//	private final static String GETLOADER = "WjJWMFEyeGhjM05NYjJGa1pYST0=";//"Z2V0Q2xhc3NMb2FkZXI=";
//	private final static String CLOADER = "YW1GMllTNXNZVzVuTGtOc1lYTnpURzloWkdWeQ==";//"amF2YS5sYW5nLkNsYXNzTG9hZGVy";
//	private final static String LOADC = "Ykc5aFpFTnNZWE56";//"bG9hZENsYXNz";
	
//	public static void reset(){
//		sInstance = null;
//	}
//	
//	public static synchronized Loader getInstance(Context context, String filePath){
//		synchronized(Loader.class){
//			if(sInstance == null){
//				sInstance = new Loader(context, filePath);
//			}
//			
//			return sInstance;
//		}
//	}
//	
//	public static Loader getInstance(Context context){
//		return sInstance;
//	}
	
//	private Object getContextLoader(Context context){
//		Object loader = null;
//		try {
//			Class<?> class_Context = Class.forName(Base64.decode(CONTEXT, "ct", "UTF-8"));
//			Method getClassLoader = class_Context.getDeclaredMethod(Base64.decode(GETLOADER, "gl", "UTF-8"), new Class<?>[]{});
//			getClassLoader.setAccessible(true);
//			loader = getClassLoader.invoke(context, new Object[]{});
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
////			e.printStackTrace();
//		}
//		return loader;
//	}
	
	public Loader(Context context, String filePath){
//		try {
//			class_DexClassLoader = Class.forName(Base64.decode(LOADER, "ld", "UTF-8"));
//			Constructor<?> constructor = class_DexClassLoader.getDeclaredConstructor(new Class<?>[]{String.class, String.class, String.class, Class.forName(Base64.decode(CLOADER, "cl", "UTF-8"))});
//			DexLoader = constructor.newInstance(new Object[]{filePath, context.getCacheDir().getAbsolutePath(), null, getContextLoader(context)});
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
////			e.printStackTrace();
//		}
//		Log.i(TAG, "new Loader: " + filePath);
//		loader = new DexClassLoader(filePath, context.getCacheDir().getAbsolutePath(), null, context.getClassLoader());
		try {
			loader = new BaseDexClassLoader(filePath, context.getCacheDir(), null, context.getClassLoader());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Class<?> load(String className){
		Class<?> clazz = null;
//		try {
//			Method loadClass = class_DexClassLoader.getMethod(Base64.decode(LOADC, "la", "UTF-8"), new Class<?>[]{String.class});
//			loadClass.setAccessible(true);
//			clazz = (Class<?>) loadClass.invoke(DexLoader, new Object[]{className});
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
////			e.printStackTrace();
//		}
		
		try {
			clazz = loader.loadClass(className);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return clazz;
	}
}
