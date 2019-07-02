package org.mass.content;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;
import org.mass.codec.BCD;
import org.mass.codec.MD5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zhj_7 on 2016/12/6.
 */

/**
 * file format:
 * ------------------------------------------------
 * file head:
 * byte[0-3]:   file definition, 4 characters;
 * byte[4-7]:   head length;
 * byte[8-9]:   head description decryption key;
 * byte[10-n]:  head description body.
 * ------------------------------------------------
 * byte[n+1-size-1] file content.
 *
 *
 *
 * description format:
 * ------------------------------------------------
 * scl          cls
 * yek          key
 * tryen        entry
 * rezillanif   finallizer
 * noisrev      version
 * musckech     checksum
 * zesi         size
 */
public class ContentParser {
	private final static String TAG = "ContentParser";
    private Context context;
    private String path;
    private String release;

    private final static int DEF_LEN = 6;
    private final static int LEN_LEN = 4;
    private final static int DK_LEN = 2;

    private byte[] definitionBytes = new byte[DEF_LEN];
    private byte[] headLengthBytes = new byte[LEN_LEN];
    private byte[] dKeyBytes = new byte[DK_LEN];
    private byte[] descriptionBytes;

    private final static String K_CLS = "rank";
    private final static String K_KEY = "eky";
    private final static String K_ENTRY = "latrop";
    private final static String K_FINAL = "gresse";
    private final static String K_VER = "noitedi";
    private final static String K_CS = "querej";
    private final static String K_SIZE = "rebalic";

    private ContentFormat format;
    private boolean success = false;

    public ContentParser(Context context, String path, String release){
        this.context = context;
        this.path = path;
        this.release = release;
    }

    public void parse(){
        synchronized (ContentParser.class) {
            FileInputStream fis = null;
            FileOutputStream fos = null;
            File file = new File(path);
            if (file.exists() && file.isFile() && file.canRead()) {
                try {
                    fis = new FileInputStream(file);
                    if (fis.read(definitionBytes) == DEF_LEN) {
                        if (fis.read(headLengthBytes) == LEN_LEN) {
                            if (fis.read(dKeyBytes) == DK_LEN) {
                                int len = BCD.L4BCD2INT(headLengthBytes);
                                descriptionBytes = new byte[len];
                                if (fis.read(descriptionBytes) == len) {
                                    wrap(descriptionBytes, dKeyBytes);
                                    String des = new String(descriptionBytes);
                                    format = wrapFormat(des);
                                    if (format != null) {
                                        File dest = new File(release);
                                        if (dest.exists() && dest.isFile()) {
                                            String cs = MD5.md5sum(release);
                                            if (dest.length() != format.size || !cs.equals(format.checksum)) {
                                                dest.delete();
                                                fos = new FileOutputStream(dest);
                                                byte[] buffer = new byte[10 * 1024];
                                                int read;
                                                while ((read = fis.read(buffer)) != -1) {
                                                    byte[] wraped = wrap(buffer, 0, read, dKeyBytes);
                                                    fos.write(wraped, 0, read);
                                                }
                                                success = true;
                                            }else{
                                            	success = true;
                                            }
                                        }else{
                                            fos = new FileOutputStream(dest);
                                            byte[] buffer = new byte[10 * 1024];
                                            int read;
                                            while ((read = fis.read(buffer)) != -1) {
                                                byte[] wraped = wrap(buffer, 0, read, dKeyBytes);
                                                fos.write(wraped, 0, read);
                                            }
                                            success = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
                } catch (IOException e) {
//                    e.printStackTrace();
                } finally {
                    try {
                        fis.close();
                    } catch (Exception e) {
//                        e.printStackTrace();
                    }

                    try {
                        fos.close();
                    } catch (Exception e) {
//                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public ContentFormat getFormat(){
        return format;
    }

    public boolean isSuccess(){
        return success;
    }

    private static byte[] wrap(byte[] bytes, int start, int end, byte[] key){
        byte mask = 0;
        if(key==null || key.length==0){
            return bytes;
        }else if(key.length>1){
            for(int i=0; i<key.length; i++){
                mask = (byte) (mask^key[i]);
            }
        }else{
            mask = key[0];
        }

        for(int i=start; i<end; i++){
            byte tmp = (byte) (bytes[i]^mask);
            bytes[i] = tmp;
        }

        return bytes;
    }

    private byte[] wrap(byte[] bytes, byte[] key){
        byte mask = 0;
        if(key==null || key.length==0){
            return bytes;
        }else if(key.length>1){
            for(int i=0; i<key.length; i++){
                mask = (byte) (mask^key[i]);
            }
        }else{
            mask = key[0];
        }

        for(int i=0; i<bytes.length; i++){
            byte tmp = (byte) (bytes[i]^mask);
            bytes[i] = tmp;
        }

        return bytes;
    }

    private ContentFormat wrapFormat(String paramString){
        ContentFormat format = null;
//        Log.i(TAG, "ContentFormat: " + paramString);
        try {
            JSONObject obj = new JSONObject(paramString);
            if(obj.has(K_CLS) && obj.has(K_KEY) && obj.has(K_ENTRY) && obj.has(K_FINAL)
                    && obj.has(K_VER) && obj.has(K_CS) && obj.has(K_SIZE)){
                format = new ContentFormat();
                format.cls = obj.getString(K_CLS);
                format.key = obj.getString(K_KEY);
                format.entry = obj.getString(K_ENTRY);
                format.finallizer = obj.getString(K_FINAL);
                format.version = obj.getString(K_VER);
                format.checksum = obj.getString(K_CS);
                format.size = obj.getLong(K_SIZE);
            }
        } catch (JSONException e) {
//            e.printStackTrace();
        }

        return format;
    }

}
