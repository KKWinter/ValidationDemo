package com.jumpraw.mraiddemo.Utils;


import android.os.Handler;
import android.os.Looper;

public class Defaults {

    public static Handler handler = new Handler(Looper.getMainLooper());

    public static final String SDK_VERSION = "1.0.0";

    // This is used if the WebView's value returned is empty.
    public static final String USER_AGENT = "CTAdView/" + SDK_VERSION + " (Android)";

    public static final int NETWORK_TIMEOUT_SECONDS = 5;

    public static final int LOCATION_DETECTION_MINTIME = 30 * 1000; // 10 Minutes in ms
    public static final int LOCATION_DETECTION_MINDISTANCE = 2; // Meters

    // public static final String AD_NETWORK_URL = "http://ads.moceanads.com/ad";

    // Default injection HTML rich media ads.
    // Improper modification to these strings can cause ad rendering failures.
    // IMPORTANT: These strings have specific format specifiers (%s).
    public static final String RICHMEDIA_FORMAT_API11 = "<html %s><head><meta name=\"viewport\" content=\"user-scalable=0\"/><style>body{margin:0;padding:0;}</style></head><body>%s</body></html>";
    public static final String RICHMEDIA_FORMAT = "<html %s><head><meta name=\"viewport\" content=\"user-scalable=0\"/><style>body{margin:0;padding:0;}</style><script type=\"text/javascript\">%s</script></head><body>%s</body></html>";
}
