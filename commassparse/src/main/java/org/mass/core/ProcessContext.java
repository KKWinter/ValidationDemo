package org.mass.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProcessContext {
	private final static String TAG = "ProcessContext";
	private static Map<String, L> loaderMap = new HashMap<String, L>(); 
	
	private static class L{
		public Loader loader;
		public int pid;
		public String tag;
	}
	
	public static void setLoader(String tag, Loader loader){		
		int pid = android.os.Process.myPid();
//		Log.i(TAG, tag + " bind loader to process " + pid);
		L l = new L();
		l.loader = loader;
		l.pid = pid;
		l.tag = tag;
		loaderMap.put(tag, l);
	}
	
	public static Loader getLoader(){
		int pid = android.os.Process.myPid();
		
		Collection<L> values = loaderMap.values();
		if(values!=null){
			for(L l: values){
				if(l.pid==pid){
					return l.loader;
				}
			}
		}
		
		return null;
	}
	
	public static void clear(String tag){
		loaderMap.remove(tag);
	}
}
