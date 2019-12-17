package com.jumpraw.mraid.mraid;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.jumpraw.mraid.Utils.Defaults;
import com.jumpraw.mraid.gp.GpsHelper;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


//js-native调用
public class Bridge {
    public final WebView webView;
    public final Handler handler;
    private static final String TAG = "Bridge";

    /**
     * 构造方法，传入webview，还有handler回调，各种mraid事件
     */
    public Bridge(WebView webView, Handler handler) {
        if (webView == null) {
            throw new IllegalArgumentException("webView null");
        }

        if (handler == null) {
            throw new IllegalArgumentException("handler null");
        }
        this.webView = webView;
        this.handler = handler;
    }


    /**
     * 错误信息，通过js发给h5
     */
    public void sendErrorMessage(String message, String action) {
        Log.e(TAG, "sendErrorMessage: message -> " + message + ", action -> " + action);
        String script = "mraid.fireErrorEvent('" + message + "','" + action + "');";
        webView.injectJavascript(script);
    }


    private Consts.PlacementType placementType = Consts.PlacementType.Inline;  //广告位类型

    /**
     * 获取广告位类型
     */
    public Consts.PlacementType getPlacementType() {
        Log.e(TAG, "getPlacementType: " + placementType);
        return placementType;
    }

    /**
     * 设置广告位类型，通过js告诉h5需要什么类型的广告
     *
     * @param placementType
     */
    public void setPlacementType(Consts.PlacementType placementType) {
        Log.e(TAG, "setPlacementType: " + placementType);
        this.placementType = placementType;

        String placementTypeString = Consts.PlacementTypeInline;
        if (placementType == Consts.PlacementType.Interstitial) {
            placementTypeString = Consts.PlacementTypeInterstitial;
        }

        String script = "mraid.setPlacementType('" + placementTypeString + "');";
        webView.injectJavascript(script);
    }


    private Consts.State state = Consts.State.Loading;      //广告加载状态


    /**
     * @return 获取当前状态
     */
    public Consts.State getState() {
        Log.e(TAG, "getState: " + state);
        return state;
    }

    /**
     * 通过js告诉h5当前广告状态
     *
     * @param state
     */
    public void setState(Consts.State state) {
        Log.e(TAG, "setState: " + state);
        this.state = state;

        String stateString = Consts.StateLoading;
        switch (state) {
            case Loading:
                stateString = Consts.StateLoading;
                break;
            case Default:
                stateString = Consts.StateDefault;
                break;
            case Hidden:
                stateString = Consts.StateHidden;
                break;
            case Resized:
                stateString = Consts.StateResized;
                break;
            case Expanded:
                stateString = Consts.StateExpanded;
                break;
        }

        String script = "mraid.setState('" + stateString + "');";
        webView.injectJavascript(script);
    }


    /**
     * 硬件设备是否支持某项功能，通过js告诉h5
     *
     * @param feature
     * @param supported
     */
    public void setSupportedFeature(Consts.Feature feature, boolean supported) {
        String supportedString = supported ? Consts.True : Consts.False;

        String featureString = null;
        switch (feature) {
            case SMS:
                featureString = Consts.FeatureSMS;
                break;
            case Tel:
                featureString = Consts.FeatureTel;
                break;
            case Calendar:
                featureString = Consts.FeatureCalendar;
                break;
            case StorePicture:
                featureString = Consts.FeatureStorePicture;
                break;
            case InlineVideo:
                featureString = Consts.FeatureInlineVideo;
                break;
            case VPAID:
                featureString = Consts.FeatureVpaid;
                break;
        }

        Log.e(TAG, "setSupportedFeature: feature -> " + feature + ", supported -> " + supported);
        String script = "mraid.setSupports('" + featureString + "', '" + supportedString + "');";
        webView.injectJavascript(script);
    }


    /**
     * 主机显示 MRAID 库状况
     */
    public void sendReady() {
        Log.e(TAG, "sendReady: ");
        String script = "mraid.fireEvent('" + Consts.EventReady + "');";
        webView.injectJavascript(script);
    }

    /**
     * 是否可见
     *
     * @param viewable
     */
    public void setViewable(boolean viewable) {
        Log.e(TAG, "setViewable: " + viewable);
        String viewableString = viewable ? Consts.True : Consts.False;

        String script = "mraid.setViewable('" + viewableString + "');";
        webView.injectJavascript(script);
    }


    /**
     * 屏幕尺寸
     */
    public void setScreenSize(int width, int height) {
        Log.e(TAG, "setScreenSize: width:" + width + ",height:" + height);
        String script = "mraid.setScreenSize({width:" + width + ",height:" + height + "});";
        webView.injectJavascript(script);
    }

    /**
     * 设置尺寸
     */
    public void setMaxSize(int width, int height) {
        Log.e(TAG, "setMaxSize: width:" + width + ",height:" + height);
        String script = "mraid.setMaxSize({width:" + width + ",height:" + height + "});";
        webView.injectJavascript(script);
    }

    /**
     * 当前坐标
     */
    public void setCurrentPosition(int x, int y, int width, int height) {
        Log.e(TAG, "setCurrentPosition: width:" + width + ",height:" + height);
        String script = "mraid.setCurrentPosition({x:" + x + ",y:" + y + ",width:" + width + ",height:" + height + "});";
        webView.injectJavascript(script);
    }


    public void setCurrentExposure(int percentage, int viewPortW, int viewPortH, int x, int y) {

        String value = "{exposedPercentage: %d, viewport: {width: %d, height: %d}, visibleRectangle: {x: %d, y: %d, width: %d, height: %d, occlusionRectangle: {x: %d,y: %d,width: %d,height: %d}}}";
        value = String.format(Locale.US, value, percentage, viewPortW, viewPortH, x, y, viewPortW, viewPortH, 0, 0, 0, 0);
        Log.e(TAG, "setCurrentExposure: value " + value);
        webView.injectJavascript(String.format("mraid.setExposureChange(%s);", value));
    }

    /**
     * 音量的改变
     */
    public void setAudioVolume(float volume) {
        String script = "mraid.setAudioVolumeChange(" + volume + ");";
        webView.injectJavascript(script);
    }

    /**
     * 应用程序的当前方向
     */
    public void setCurrentAppOrientation(String orientation, boolean locked) {
        String script = "mraid.setCurrentAppOrientation({orientation:'" + orientation + "',locked:" + locked + "});";
        webView.injectJavascript(script);
    }


    public void setLocation(double lat, double lon, int type, String accuracy, int lastfix, String ipservice) {
        String value = "{lat: %f, lon: %f, type: %d, accuracy: '%s', lastfix: %d, ipservice: '%s'}";
        value = String.format(Locale.US, value, lat, lon, type, accuracy, lastfix, ipservice);
        Log.e(TAG, "setLocation: value " + value);
        String format = String.format("mraid.setLocation(%s);", value);
        Log.e(TAG, "setLocation: " + format);
        webView.injectJavascript(format);
    }


    /**
     * 默认坐标
     */
    public void setDefaultPosition(int x, int y, int width, int height) {
        Log.e(TAG, "setDefaultPosition: width:" + width + ",height:" + height);
        String script = "mraid.setDefaultPosition({x:" + x + ",y:" + y + ",width:" + width + ",height:" + height + "});";
        webView.injectJavascript(script);
    }


    private ExpandProperties expandProperties = new ExpandProperties();   //广告展开属性


    /**
     * 获取广告展开属性
     */
    public ExpandProperties getExpandProperties() {
        Log.e(TAG, "getExpandProperties: " + expandProperties);
        return expandProperties;
    }


    /**
     * js向h5中设置新的展开属性
     */
    public void setExpandProperties(ExpandProperties expandProperties) {
        String arg = expandProperties.toString();
        Log.e(TAG, "setExpandProperties: " + arg);
        String script = "mraid.setExpandProperties(" + arg + ");";
        webView.injectJavascript(script);
    }


    private OrientationProperties orientationProperties = new OrientationProperties();   //设备方向属性


    /**
     * 获取方向属性
     *
     * @return Configured orientation properties.
     */
    public OrientationProperties getOrientationProperties() {
        Log.e(TAG, "getOrientationProperties: " + orientationProperties);
        return orientationProperties;
    }


    /**
     * js向h5中设置设备方向属性
     * The class member will be set when the javascript bridge calls back a native invoke.
     */
    public void setOrientationProperties(OrientationProperties orientationProperties) {
        String arg = orientationProperties.toString();
        Log.e(TAG, "setOrientationProperties: " + arg);
        String script = "mraid.setOrientationProperties(" + arg + ");";
        webView.injectJavascript(script);
    }


    private ResizeProperties resizeProperties = new ResizeProperties();      //调整大小的属性


    /**
     * To set properties call setResizeProperties() with the same or a new
     * object as changing members of the returned object will NOT update the
     * MRAID javascript bridge.
     *
     * @return Configured resize properties.
     */
    public ResizeProperties getResizeProperties() {
        Log.e(TAG, "getResizeProperties: " + resizeProperties);
        return resizeProperties;
    }


    // The class member will be set when the javascript bridge calls back a native invoke.
    public void setResizeProperties(ResizeProperties resizeProperties) {
        String arg = resizeProperties.toString();
        Log.e(TAG, "setResizeProperties: " + arg);
        String script = "mraid.setResizeProperties(" + arg + ");";
        webView.injectJavascript(script);
    }


    public void sendPictureAdded(boolean success) {
        String successString = Consts.False;
        if (success) {
            successString = Consts.True;
        }

        String script = "mraid.firePictureAddedEvent('" + successString + "');";
        webView.injectJavascript(script);
    }


    /**
     * 声明 MRAID 环境详细信息
     *
     * @param slotId
     */
    public void setEnv(String slotId) {
        final String format = "{version:%s,sdk:'%s',sdkVersion:'%s',appId:'%s',ifa:'%s',limitAdTracking:%b,coppa:%b}";
        String version = "3.0";
        String sdk = "zcoup";
        String sdkVersion = Defaults.SDK_VERSION;
        String appId = slotId;

        String ifa = GpsHelper.getAdvertisingId();
        boolean limitAdTrackingEnabled = GpsHelper.isLimitAdTrackingEnabled();
        Log.e(TAG, "advertisingId: " + ifa);
        Log.e(TAG, "limitAdTrackingEnabled: " + limitAdTrackingEnabled);

        String value = String.format(format, version, sdk, sdkVersion, appId, ifa, limitAdTrackingEnabled, false);
        webView.injectJavascript(String.format("mraid.setEnv(%s)", value));
    }


    public void vpaidInit() {
        Log.e(TAG, "AD subscribe events");
        webView.injectJavascript("mraid.subscribe(\"AdClickThru\")");
        webView.injectJavascript("mraid.subscribe(\"AdError\")");
        webView.injectJavascript("mraid.subscribe(\"AdImpression\")");
        webView.injectJavascript("mraid.subscribe(\"AdPaused\")");
        webView.injectJavascript("mraid.subscribe(\"AdPlaying\")");
        webView.injectJavascript("mraid.subscribe(\"AdVideoStart\")");
        webView.injectJavascript("mraid.subscribe(\"AdVideoFirstQuartile\")");
        webView.injectJavascript("mraid.subscribe(\"AdVideoMidpoint\")");
        webView.injectJavascript("mraid.subscribe(\"AdVideoThirdQuartile\")");
        webView.injectJavascript("mraid.subscribe(\"AdVideoComplete\")");
    }


    /**
     * js调用native的vapidInvoke接口， 传来invoke
     */
    @JavascriptInterface
    public void vpaidInvoke(String invoke) {
        if (TextUtils.isEmpty(invoke)) {
            return;
        }

        if (invoke.startsWith("console")) {
            // String message = invoke.split("\\?")[1];
            // if (!TextUtils.isEmpty(message)) {
            //     Log.e(TAG, "js log messages --->> " + message);
            // }
            return;
        }

        URI uri = null;
        try {
            uri = new URI(invoke);
        } catch (URISyntaxException e) {
            Log.e(TAG, "vpaidInvoke: " + e);
            return;
        }

        Log.e(TAG, "vpaidInvoke: " + invoke);
        String scheme = uri.getScheme().toLowerCase(Locale.US);

        //"vpaid".equal(), 表示invoke是vpaid命令
        if (Consts.VPAIDScheme.equals(scheme)) {
            String command = uri.getHost();
            String query = uri.getRawQuery();

            //取出uri中的参数
            Map<String, String> args = new HashMap<String, String>(10);
            if (query != null) {
                try {
                    String[] queryItems = query.split("\\&");
                    for (String queryItem : queryItems) {
                        String[] queryItemParts = queryItem.split("\\=");
                        if (queryItemParts.length == 2) {
                            String key = URLDecoder.decode(queryItemParts[0], "UTF-8");
                            String value = URLDecoder.decode(queryItemParts[1], "UTF-8");
                            args.put(key, value);
                        }
                    }
                } catch (Exception ignore) {
                }
            }

            if (Consts.CommandVpaidInit.equals(command)) {
                vpaidInit();
            } else if (Consts.CommandVpaidAdClickThru.equals(command)) {
                String url = args.get(Consts.CommandArgUrl);
                Log.e(TAG, "vpaidInvoke: VPAIDAdClickThru -> " + url);
            } else if (Consts.CommandVpaidAdError.equals(command)) {
                String message = args.get(Consts.CommandArgMessage);
                Log.e(TAG, "vpaidInvoke: vpaidAdError -> " + message);
            } else if (Consts.CommandVpaidAdError.equals(command)) {
                String message = args.get(Consts.CommandArgMessage);
                Log.e(TAG, "vpaidInvoke: vpaidAdError -> " + message);
            } else if (Consts.CommandVpaidAdImp.equals(command)) {
                Log.e(TAG, "vpaidInvoke: vpaidAdImpression");
            } else if (Consts.CommandVpaidAdPaused.equals(command)) {
                Log.e(TAG, "vpaidInvoke: vpaidAdPaused");
            } else if (Consts.CommandVpaidAdPlaying.equals(command)) {
                Log.e(TAG, "vpaidInvoke: vpaidAdPlaying");
            } else if (Consts.CommandVpaidAdVideoStart.equals(command)) {
                Log.e(TAG, "vpaidInvoke: vpaidAdVideoStart");
            } else if (Consts.CommandVpaidAdVideoFirstQuartile.equals(command)) {
                Log.e(TAG, "vpaidInvoke: vpaidAdVideoFirstQuartile");
            } else if (Consts.CommandVpaidAdVideoMidpoint.equals(command)) {
                Log.e(TAG, "vpaidInvoke: vpaidAdVideoMidpoint");
            } else if (Consts.CommandVpaidAdVideoThirdQuartile.equals(command)) {
                Log.e(TAG, "vpaidInvoke: vpaidAdVideoThirdQuartile");
            } else if (Consts.CommandVpaidAdVideoComplete.equals(command)) {
                Log.e(TAG, "vpaidInvoke: vpaidAdVideoComplete");
            }
        }
    }


    /**
     * js调用navtie的接口，传来invoke命令
     *
     * @param invoke URI from the MRAID Javascript with an encoded command and arguments.
     */
    @JavascriptInterface
    public void nativeInvoke(String invoke) {
        if (TextUtils.isEmpty(invoke)) {
            return;
        }

        if (invoke.startsWith("console")) {
            // String message = invoke.split("\\?")[1];
            // if (!TextUtils.isEmpty(message))
            //     Log.e(TAG, "js log messages --->> " + message);
            return;
        }

        URI uri = null;
        try {
            uri = new URI(invoke);
        } catch (URISyntaxException e) {
            Log.e(TAG, "nativeInvoke: " + e);
            return;
        }

        Log.e(TAG, "nativeInvoke: " + invoke);
        String scheme = uri.getScheme().toLowerCase(Locale.US);

        //"mraid".equal()； mraid命令
        if (Consts.Scheme.equals(scheme)) {
            String command = uri.getHost();
            String query = uri.getRawQuery();

            Map<String, String> args = new HashMap<String, String>(10);

            if (query != null) {
                try {
                    String[] queryItems = query.split("\\&");
                    for (String queryItem : queryItems) {
                        String[] queryItemParts = queryItem.split("\\=");
                        if (queryItemParts.length == 2) {
                            String key = URLDecoder.decode(queryItemParts[0], "UTF-8");
                            String value = URLDecoder.decode(queryItemParts[1], "UTF-8");
                            args.put(key, value);
                        }
                    }
                } catch (Exception ignore) {
                }
            }


            String url = args.get(Consts.CommandArgUrl);
            switch (command) {
                case Consts.CommandInit:
                    handler.mraidInit(this);
                    break;
                case Consts.CommandClose:
                    handler.mraidClose(this);
                    break;
                case Consts.CommandUnload:
                    handler.mraidUnload(this);
                    break;
                case Consts.CommandOpen:
                    handler.mraidOpen(this, url);
                    break;
                case Consts.CommandUpdateCurrentPosition:
                    handler.mraidUpdateCurrentPosition(this);
                    break;
                case Consts.CommandExpand:
                    handler.mraidExpand(this, url);
                    break;
                case Consts.CommandSetExpandProperties:
                    this.expandProperties = ExpandProperties.propertiesFromArgs(args);
                    handler.mraidUpdatedExpandProperties(this);
                    break;
                case Consts.CommandSetOrientationProperties:
                    this.orientationProperties = OrientationProperties.propertiesFromArgs(args);
                    handler.mraidUpdatedOrientationProperties(this);
                    break;
                case Consts.CommandResize:
                    handler.mraidResize(this);
                    break;
                case Consts.CommandSetResizeProperties:
                    this.resizeProperties = ResizeProperties.propertiesFromArgs(args);
                    handler.mraidUpdatedResizeProperties(this);
                    break;
                case Consts.CommandPlayVideo:
                    handler.mraidPlayVideo(this, url);
                    break;
                case Consts.CommandCreateCalendarEvent:
                    handler.mraidCreateCalendarEvent(this, args.get(Consts.CommandArgEvent));
                    break;
                case Consts.CommandSetCloseCounter:
                    handler.mraidSetCloseCounter(this, args.get(Consts.CommandArgSeconds));
                    break;
                case Consts.CommandStorePicture:
                    handler.mraidStorePicture(this, url);
                    break;
            }
        }
    }


    public interface VPAIDHandler {
        void vpaidAdClickThruIdPlayerHandles(Bridge bridge, String url, String id, boolean playerHandles);

        void vpaidAdError(Bridge bridge, String message);
    }


    public interface Handler {
        void mraidInit(Bridge bridge);

        void mraidClose(Bridge bridge);

        void mraidUnload(Bridge bridge);

        void mraidOpen(Bridge bridge, String url);

        void mraidUpdateCurrentPosition(Bridge bridge);

        void mraidUpdatedExpandProperties(Bridge bridge);

        void mraidExpand(Bridge bridge, String url);

        void mraidUpdatedOrientationProperties(Bridge bridge);

        void mraidUpdatedResizeProperties(Bridge bridge);

        void mraidResize(Bridge bridge);

        void mraidPlayVideo(Bridge bridge, String url);

        void mraidCreateCalendarEvent(Bridge bridge, String calendarEvent);

        void mraidSetCloseCounter(Bridge bridge, String count);

        void mraidStorePicture(Bridge bridge, String url);
    }
}
