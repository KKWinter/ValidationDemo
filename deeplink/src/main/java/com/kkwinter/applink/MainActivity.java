package com.kkwinter.applink;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "applinks";
    private Context context;
    private Handler handler = new Handler(Looper.getMainLooper());

    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    //deeplink
    private String tiantiannews = "qnreading://article_detail?nm=20190302A06ZAB00&from=LH_XWQD_9001602";
    private String aiqiyi1 = "iqiyi://mobile/home?ftype=27\\u0026subtype=lk2_527";
    private String aiqiyi2 = "iqiyi://mobile/home?ftype=27&subtype=shjsxx_855";
    private String youku = "youku://weex?source=00002205&url=https%3A%2F%2Fmarket.m.taobao.com%2Fyep%2Fweexmaker%2Fykpage%2Fpigspring_wmdt.js%3Fwh_weex%3Dtrue%26refer%3Dsanfang1903_operation.chunyue.l_00002205_7000_feQvAb_19022700&refer=sanfang1903_operation.chunyue.l_00002205_7000_feQvAb_19022700";
    private String tencentvideo = "tenvideo2://?action=1&&video_id=j08324h39q1&from=30177|2";
    private String tencentnews = "qqnews://article_9527?nm=20190307A0JVWQ00&from=xingt79";
    private String taobao = "https://h5.m.taobao.com/bcec/dahanghai-jump.html?spm=2014.ugdhh.150983840.1217&bc_fl_src=growth_dhh_150983840_1217";
    private String mafengwo = "travelguide://jump?source=lh_juwan_2019tgpsj_5&url=https%3A%2F%2Fimfw.cn%2Fl%2F27246444";
    private String pinduoduo = "pinduoduo://com.xunmeng.pinduoduo/duo_red_packet.html?pid=8253161_48142305&authDuoId=8253161&cpsSign=CR8253161_48142305_cdd913216972d0b3d92e8203aef6f723&duoduo_type=2";

    private String gpHttp = "https://play.google.com/store/apps/details?id=com.minidragon.pixelpetz";
    private String gpMarket = "market://details?id=com.minidragon.pixelpetz";


    private String toutiao = "snssdk143://detail?groupid=6681884126867358212&gd_label=click_schema_abab71";
    private String douyin = "snssdk1128://challenge/detail/1630497529464846?gd_label=click_schema_abab28";
    private String tbopen = "tbopen://m.taobao.com/tbopen/index.html?source=auto&action=ali.open.nav&module=h5&bootImage=0&spm=2014.ugdhh.3234525723.10008-1181&bc_fl_src=growth_dhh_3234525723_10008-1181&materialid=10008&h5Url=https%3A%2F%2Fh5.m.taobao.com%2Fbcec%2Fdahanghai-jump.html%3Fspm%3D2014.ugdhh.3234525723.10008-1181%26bc_fl_src%3Dgrowth_dhh_3234525723_10008-1181";
    private String koubei = "koubei://platformapi/startapp?appId=20000001&actionType=20000238&chInfo=ch_DAUyinliu__chsub_aguya4";



    private Spinner spinner;
    private List<String> data_list;
    private ArrayAdapter arr_adapter;
    private String finalUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();
//        checkPermission();

        data_list = new ArrayList<>();
        data_list.add(aiqiyi1);
        data_list.add(tbopen);
        data_list.add(koubei);


        spinner = findViewById(R.id.spinner);
        arr_adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, data_list);
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arr_adapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                finalUrl = data_list.get(arg2);
                Log.i(TAG, "onItemSelected: >>>" + arg2 + ">>>" + finalUrl);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        findViewById(R.id.click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Tools.openHome(context);
                        Tools.openDeepLink(context, douyin);

                    }
                }, 13000);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Tools.openHome(context);
                        Tools.openDeepLink(context, toutiao);

                    }
                }, 10000);




                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Tools.openHome(context);
                        Tools.openDeepLink(context, tbopen);

                    }
                }, 16000);
//
//
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        Tools.openHome(context);
//                        Tools.openDeepLink(context, koubei);
//
//                    }
//                }, 19000);


            }
        });


        findViewById(R.id.kouling).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Tools.openHome(context);
                        Tools.openDeepLink(context, toutiao);

                    }
                }, 10000);


            }
        });


        findViewById(R.id.deeplink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Tools.openHome(context);
                        Tools.openDeepLink(context, toutiao);

                    }
                }, 10000);




                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Tools.openHome(context);
                        Tools.openDeepLink(context, tbopen);

                    }
                }, 16000);


            }
        });

    }


    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (context.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(this, permissions, 111);
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
