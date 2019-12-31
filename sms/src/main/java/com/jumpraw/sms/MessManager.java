package com.jumpraw.sms;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MessManager {

    //短信的数据库地址
    private Uri SMS_INBOX = Uri.parse("content://sms/");
    //存储上次的读取id地址
    private final String TIME_FILE = "lastid.txt";
    private final String TIME_DIR = "彼德骚/测试/id";
    //存储地址
    private final String MESSAGE_FILE = "SMSSum.txt";
    private final String MESSAGE_DIR = "彼德骚/测试/sms";

    private int lastid;
    private SmsObserver smsObserver;
    private Context mcontext;
    private Handler mHandler;

    private ArrayList<ModuleMessage> newMessages;

    public ArrayList<ModuleMessage> getNewMessages() {
        return newMessages;
    }

    public MessManager(Context context, Handler handler) {
        this.mcontext = context;
        this.mHandler = handler;
        newMessages = getSmsFromPhone();
        saveMessage(newMessages);
        //注册监听器
        smsObserver = new SmsObserver(mcontext, mHandler);
        mcontext.getContentResolver().registerContentObserver(SMS_INBOX, true, smsObserver);

    }

    //内部类监听器
    class SmsObserver extends ContentObserver {

        public SmsObserver(Context context, Handler handler) {
            super(handler);
        }


        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            //每当有新短信到来时，使用我们获取短消息的方法,然后存储
            newMessages = getSmsFromPhone();
            System.out.println(newMessages.size());
            saveMessage(newMessages);
            Message message = mHandler.obtainMessage(1);
            message.obj = newMessages;
            mHandler.sendMessage(message);
        }
    }


    //存储短信
    public void saveMessage(ArrayList<ModuleMessage> moduleMessages) {
        System.out.print("执行----》");
        File dir = new File(Environment.getExternalStorageDirectory(), MESSAGE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, MESSAGE_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file, true);
            for (int i = 0; i < moduleMessages.size(); i++) {
                String content = new Gson().toJson(moduleMessages.get(i));
                fileOutputStream.write(content.getBytes());
                fileOutputStream.write("\r\n".getBytes());
                fileOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.print("执行完毕----》");
        }

    }


    //读取短信
    public ArrayList<ModuleMessage> getSmsFromPhone() {
        lastid = readLastId();//获取上次读取时间
        System.out.println("读取上次的时间--------》" + lastid);
        ContentResolver cr = mcontext.getContentResolver();
        //要查询的字段
        String[] projection = new String[]{"_id", "thread_id", "address", "person", "date", "type", "body"};
        //查询判断条件
        String where = "_id > " + lastid;
        Cursor cur = cr.query(SMS_INBOX, projection, where, null, "date desc");
        if (null == cur || cur.getColumnCount() == 0) {
            return null;
        }
        ArrayList<ModuleMessage> list = new ArrayList<>();
        while (cur.moveToNext()) {
            int id = cur.getInt(cur.getColumnIndex("_id"));//id
            int tid = cur.getInt(cur.getColumnIndex("thread_id"));//tid
            int type = cur.getInt(cur.getColumnIndex("type"));//type
            long date = cur.getLong(cur.getColumnIndex("date"));//date
            String address = cur.getString(cur.getColumnIndex("address"));//手机号
            String person = GetContactsByNumber(address);//联系人姓名列表
            String content = cur.getString(cur.getColumnIndex("body"));//短信内容
            ModuleMessage moduleMessage = new ModuleMessage(id, tid, address, person, date, type, content);
            list.add(moduleMessage);
            if (id > lastid) {
                lastid = id;
            }
        }
        cur.close();
        saveLastId(lastid);
        newMessages = list;
        return list;
    }


    //读取上次短信读取时间
    public int readLastId() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ptx" + File.separator + "测试" + File.separator + "a", TIME_FILE);
        if (!file.exists())
            return 0;

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] buff = new byte[1024];
            StringBuilder sb = new StringBuilder("");
            int len = 0;
            while ((len = fileInputStream.read(buff)) > 0) {
                sb.append(new String(buff, 0, len));
            }
            return Integer.parseInt(String.valueOf(sb));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    //存储新的id的函数
    public void saveLastId(int thisid) {
        File dir = new File(Environment.getExternalStorageDirectory(), TIME_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, TIME_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file);
            String value = String.valueOf(thisid);
            fileOutputStream.write(value.getBytes());
            fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据来电号码获取联系人名字
     */
    private String GetContactsByNumber(String number) {
        Uri uri = Uri.parse("content://com.android.contacts/data/phones/filter/" + number);
        ContentResolver resolver = mcontext.getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{"display_name"}, null, null, null);
        if (cursor.moveToFirst()) {
            String name = cursor.getString(0);
            Log.i("test", name);
            cursor.close();
            return name;
        }
        cursor.close();
        return null;
    }

}

