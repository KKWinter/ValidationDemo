package org.mass.core;

import android.app.Service;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.mass.codec.MD5;
import org.mass.content.Content;
import org.mass.content.ContentConfig;
import org.mass.content.ContentFormat;
import org.mass.content.ContentParser;

import java.io.File;
import java.lang.reflect.Method;


/**
 * Created by zhj_7 on 2016/11/18.
 */

public class ContentManager {
	private final static String TAG = "CM";
    private Service service;
    private Context context;
    private Content content;
    private ContentFormat contentFormat;
    private Object contentObject;
    private Class<?> cls;    
    
    public ContentManager(Service service){
        this.service = service;
        ProcessContext.clear(this.getClass().getName());
        context = service.getApplicationContext();
    }
    
    private Handler mHandler = new Handler(){
    	public void handleMessage(Message msg){
    		switch(msg.what){
    		case 1:
    			load();
    			break;
    			
    		case 2:
    			break;
    		}
    	}
    };
    
    private void close(){
    	service.stopSelf();
    	android.os.Process.killProcess(android.os.Process.myPid());
    }

    public boolean isContentExist(){
        File file = new File(content.path + File.separator + content.fileName);
        return file.exists()&&file.isFile();
    }

    public void analyze() throws Exception {
        if(isContentExist()){
            String src = content.path + File.separator + content.fileName;
            String release = content.release + File.separator + content.rfName;
            ContentParser parser = new ContentParser(context, src, release);
            parser.parse();
            if(parser.isSuccess()) {
                contentFormat = parser.getFormat();
            }else{
            	close();
                //throw new InvalidPropertiesFormatException("parse file failed");
            }
        }else{
        	close();
//            throw new IllegalStateException("can not find content file.");
        }
    }

    public void loadContent(){    
    
    	mHandler.post(new Runnable(){
			@Override
			public void run() {
            	Log.i(TAG, "start to load content");
                content = getContent();

                if(content!=null){
                    try {
                        analyze();
                        mHandler.sendEmptyMessage(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        //parse error, clear content config, load original dynamic file.
                        ContentConfig.clearConfig(context);
                        close();
                    }
                }else{
                	close();
                }            
			}});
    }

    private void load(){
    	try {
    		Loader loader = new Loader(service.getApplicationContext(), content.path + File.separator + content.rfName);
    		ProcessContext.setLoader(this.getClass().getName(), loader);
	        //Loader loader = Loader.getInstance(service.getApplicationContext(), content.path + File.separator + content.rfName);
//    		Log.i(TAG, "load class: " + contentFormat.cls);
	        cls = loader.load(contentFormat.cls);
//	        Log.i(TAG, "class loaded: " + cls);
	        
	        contentObject = cls.newInstance();
//	        Log.i(TAG, "class instance created.");
	        
//	        Log.i(TAG, "get class method: " + contentFormat.entry);
	        Method entry = cls.getDeclaredMethod(contentFormat.entry, new Class<?>[]{Service.class});
//	        Log.i(TAG, "method find: " + entry);
	        
	        entry.invoke(contentObject, new Object[]{service});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ContentConfig.clearConfig(context);
			close();
		}
    }

    public void releaseContent(){
        try {
            Method finallizer = cls.getDeclaredMethod(contentFormat.finallizer, new Class<?>[]{});
            finallizer.invoke(contentObject, new Object[]{});
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    private Content getContent(){
        Content content = null;

        String path = ContentConfig.getContentPath(context);
        String filename = ContentConfig.getContentFileName(context);
        String md = ContentConfig.getContentMd5(context);
        long length = ContentConfig.getContentLength(context);
        
        content = getCust(path, filename, md, length);

        if(content==null){
        	close();
//            throw new IllegalStateException("can not find local data.");
        }

        return content;
    }

    private Content getCust(String path, String filename, String md5, long length){
//    	Log.i(TAG, "get cust: " + path + "|" + filename + "|" + md5 + "|" + length);
        Content content = null;

        if(path!=null && filename!=null && !md5.equals("") && length !=-1L){
            String absPath = path + File.separator + filename;
            File file = new File(absPath);
            if(file.exists() && file.isFile()) {
                if(file.length()==length) {
                    String realMd = MD5.md5sum(absPath);
                    if (realMd != null && realMd.equals(md5)) {
                        content = new Content();
                        content.path = path;
                        content.fileName = filename;
                        content.release = context.getFilesDir().getAbsolutePath();
                        content.rfName = ".content.jar";
                    }else{
//                    	Log.e(TAG, "md5 check failed" + realMd + "?="+md5);
                    }
                }else{
//                	Log.e(TAG, "length is not the same " + file.length() + "?=" + length);
                }
            }else{
//            	Log.e(TAG, absPath + " is not file or not exist");
            }
        }

        return content;
    }
}
