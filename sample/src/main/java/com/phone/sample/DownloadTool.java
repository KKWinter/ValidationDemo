package com.phone.sample;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

/**
 * Created by huangdong on 2018/11/20.
 * antony.huang@yeahmobi.com
 */
public class DownloadTool {


    public static void load(Context context) {

        Uri uri = Uri.parse("http://res.cloudmobi.net/sdk/zw-20180930.apk");
        // uri 是你的下载地址，可以使用Uri.parse("http://")包装成Uri对象
        DownloadManager.Request req = new DownloadManager.Request(uri);

// 通过setAllowedNetworkTypes方法可以设置允许在何种网络下下载，
// 也可以使用setAllowedOverRoaming方法，它更加灵活
        req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);

// 此方法表示在下载过程中通知栏会一直显示该下载，在下载完成后仍然会显示，
// 直到用户点击该通知或者消除该通知。还有其他参数可供选择
        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

// 设置下载文件存放的路径，同样你可以选择以下方法存放在你想要的位置。
// setDestinationUri
// setDestinationInExternalPublicDir
        req.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "zhongwei");

// 设置一些基本显示信息
        req.setTitle("Android.apk");
        req.setDescription("下载完后请点击打开");
        req.setMimeType("application/vnd.android.package-archive");

        DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        long downloadId = dm.enqueue(req);

    }

}
