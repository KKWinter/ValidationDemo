package com.jumpraw.sms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "sms";
    private Context context;


//    private String[] permissions = new String[]{Manifest.permission.READ_SMS,
//            Manifest.permission.RECEIVE_SMS,
//            Manifest.permission.SEND_SMS};

    private String[] permissions = new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_NUMBERS};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();
        checkPermission();

        //sms

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (checkPermission(Manifest.permission.RECEIVE_SMS)) {
                    Log.i(TAG, "register: >>>>>>>>");

                    IntentFilter filter = new IntentFilter();
                    filter.addAction("android.provider.Telephony.SMS_RECEIVED");
                    SmsReceiver receiver = new SmsReceiver();
                    registerReceiver(receiver, filter);
                }

                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String deviceid = tm.getDeviceId();//获取智能设备唯一编号
                String te1 = tm.getLine1Number();//获取本机号码
                String imei = tm.getSimSerialNumber();//获得SIM卡的序号
                String imsi = tm.getSubscriberId();//得到用户Id


                boolean result = checkPermission(Manifest.permission.READ_PHONE_NUMBERS);
                if (!result) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_NUMBERS}, 111);
                }

                Log.i(TAG, "onClick: " + result);
            }
        });


    }


    public class SmsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {


            Log.i(TAG, "onReceive: >>>>>>" + intent.getAction());


//            StringBuilder content = new StringBuilder();//用于存储短信内容
//            String sender = null;//存储短信发送方手机号
            Bundle bundle = intent.getExtras();//通过getExtras()方法获取短信内容
//            String format = intent.getStringExtra("format");
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");//根据pdus关键字获取短信字节数组，数组内的每个元素都是一条短信
                for (Object o : pdus) {

                    SmsMessage message = SmsMessage.createFromPdu((byte[]) o);
                    String msg = message.getMessageBody();

                    String address1 = message.getOriginatingAddress();
                    String address2 = message.getDisplayOriginatingAddress();
                    Log.i(TAG, "onReceive: ===" + msg);
                }

//                for (Object object : pdus) {
//                    SmsMessage message = SmsMessage.createFromPdu((byte[]) object, format);//将字节数组转化为Message对象
//                    sender = message.getOriginatingAddress();//获取短信手机号
//                    content.append(message.getMessageBody());//获取短信内容
//                }
            }
        }
    }


    public Handler smsHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            ArrayList<ModuleMessage> meslist = (ArrayList<ModuleMessage>) msg.obj;
            for (ModuleMessage moduleMessage : meslist) {

                if (moduleMessage.getAddress().equals("10086")) {
                    Log.i(TAG, "handleMessage: >>" + meslist.get(0).toString());
                }
            }


            Toast.makeText(context, "get sms", Toast.LENGTH_SHORT).show();

        }
    };


    public boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, permissions, 321);
                }
            }

        }
    }
}
