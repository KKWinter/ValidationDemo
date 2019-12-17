package com.jumpraw.mraid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.media.AudioManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.jumpraw.mraid.Utils.Assets;
import com.jumpraw.mraid.Utils.Background;
import com.jumpraw.mraid.Utils.BrowserDialog;
import com.jumpraw.mraid.Utils.Defaults;
import com.jumpraw.mraid.Utils.ImageRequest;
import com.jumpraw.mraid.Utils.PageAdVO;
import com.jumpraw.mraid.Utils.Utils;
import com.jumpraw.mraid.mraid.Bridge;
import com.jumpraw.mraid.mraid.Consts;
import com.jumpraw.mraid.mraid.OrientationProperties;
import com.jumpraw.mraid.mraid.ResizeProperties;
import com.jumpraw.mraid.mraid.WebView;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.jumpraw.mraid.Utils.Utils.GOOGLE_PLAY_PKG_NAME;
import static com.jumpraw.mraid.Utils.Utils.dpToPx;
import static com.jumpraw.mraid.Utils.Utils.pxToDp;

public class ZCAdView extends ViewGroup {

    private static final String TAG = "CTAdView";
    private boolean isResumed;
    private ZCAdViewDelegate.ActivityListener interstitialListener;

    public void setListener(ZCAdViewDelegate.ActivityListener listener) {
        this.interstitialListener = listener;
    }

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public enum LogLevel {
        None,
        Error,
        Debug,
    }


    final private int CloseAreaSizeDp = 32;
    final private int OrientationReset = Short.MIN_VALUE;
    private static int CLOSE_DURATION = 5;

    // User agent used for all requests
    private String userAgent = null;

    // Configuration
    private String zone;
    private boolean test = false;
    private int updateInterval = 0;
    private boolean useInternalBrowser = false;
    private LogLevel logLevel = LogLevel.Error;
    private Consts.PlacementType placementType = Consts.PlacementType.Inline;

    // Ad containers (render ad content)
    private WebView webView = null;

    // Close button
    private boolean showCloseButton = false;
    private int closeButtonDelay = 0;
    private Drawable closeButtonCustomDrawable = null;
    private ScheduledFuture<?> closeButtonFuture = null;

    // Interstitial configuration
    private ViewGroup interstitialLayout;
    private ScheduledFuture<?> interstitialDelayFuture = null;

    // MRAID support
    private Bridge mraidBridge = null;
    private boolean mraidBridgeInit = false;
    private Bridge.Handler mraidBridgeHandler = new MRAIDHandler();
    private ExpandDialog mraidExpandDialog = null;
    private RelativeLayout mraidResizeLayout = null;
    private View mraidResizeCloseArea = null;
    private boolean mraidTwoPartExpand = false;
    private Bridge mraidTwoPartBridge = null;
    private boolean mraidTwoPartBridgeInit = false;
    private WebView mraidTwoPartWebView = null;
    private int mraidOriginalOrientation = OrientationReset;

    // Handles WebViewClient callbacks for MRAID or other WebView based ads.
    private WebView.Handler webViewHandler = new WebViewHandler();

    private ScheduledFuture<?> adUpdateIntervalFuture = null;


    // Internal browser
    private BrowserDialog browserDialog = null;

    // Location support， 获取当前位置
    private LocationManager locationManager = null;
    private LocationListener locationListener = null;

    // Delegates
    private ZCAdViewDelegate.FeatureSupportHandler featureSupportHandler;
    private ZCAdViewDelegate.InternalBrowserListener internalBrowserListener;
    private ZCAdViewDelegate.LogListener logListener;

    private SettingsContentObserver mSettingsContentObserver;

    private OrientationBroadcastReceiver mOrientationBroadcastReceiver;

    /**
     * Used to create instances for placement in code.  Produces inline or interstitial instances.
     *
     * @param interstitial set to true to produce interstitial instances.  Interstitial instances should
     *                     never be added to any view group parent.
     */
    public ZCAdView(Context context, boolean interstitial) {
        super(context);
        init(interstitial);
    }

    protected void init(boolean interstitial) {
        placementType = interstitial ? Consts.PlacementType.Interstitial : Consts.PlacementType.Inline;

        //创建webview
        initUserAgent();
        //获取位置
        setLocationDetectionEnabled(true);

        //开始marid
        mraidBridgeInit = false;
        mraidBridge = new Bridge(webView, mraidBridgeHandler);
    }

    //初始化时创建的webview对象，并且设置webviewclient回调
    public android.webkit.WebView getWebView() {
        if (webView == null) {
            webView = new WebView(getContext());
            webView.setHandler(webViewHandler);
        }
        return webView;
    }


    private void initUserAgent() {
        if (TextUtils.isEmpty(userAgent)) {
            userAgent = getWebView().getSettings().getUserAgentString();
            if (TextUtils.isEmpty(userAgent)) {
                userAgent = Defaults.USER_AGENT;
            }
        }
    }

    /**
     * 启动或禁止SDK位置检测
     * Enables or disable SDK location detection.  If enabled with this method the most
     * battery optimized settings are used.  For more fine tuned control over location detection
     * settings use enableLocationDetection().  This method is used to disable location detection
     * for either method of enabling location detection.
     * <p>
     * Permissions for coarse or fine location detection may be required.
     */

    @SuppressLint("MissingPermission")
    public void setLocationDetectionEnabled(boolean locationDetectionEnabled) {
        if (!locationDetectionEnabled) {
            if (locationManager != null) {
                locationManager.removeUpdates(locationListener);
                locationManager = null;
                locationListener = null;
            }

            return;
        }

        Criteria criteria = new Criteria();
        criteria.setCostAllowed(false);
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setAltitudeRequired(false);
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);

        enableLocationDetection(Defaults.LOCATION_DETECTION_MINTIME, Defaults.LOCATION_DETECTION_MINDISTANCE, criteria, null);
    }

    /**
     * Enables location detection with specified criteria.  To disable location detection use
     * setLocationDetectionEnabled(false).
     *
     * @param criteria Criteria used to find an available provider.  Ignored if provider is non-null.
     * @param provider Named provider used by the LocationManager to obtain location updates.
     * @see LocationManager#requestLocationUpdates minTime
     * @see LocationManager#requestLocationUpdates minDistance
     */
    @SuppressLint("MissingPermission")
    public void enableLocationDetection(long minTime, float minDistance, Criteria criteria, String provider) {
        if ((provider == null) && (criteria == null)) {
            throw new IllegalArgumentException("criteria or provider required");
        }

        locationManager = (LocationManager) ZCAdView.this.getContext().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            try {
                if (provider == null) {
                    List<String> providers = locationManager.getProviders(criteria, true);
                    if ((providers != null) && (providers.size() > 0)) {
                        provider = providers.get(0);
                    }
                }

                if (provider != null) {
                    locationListener = new LocationListener();
                    if (getContext().checkCallingOrSelfPermission(
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED ||
                            getContext().checkCallingOrSelfPermission(ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_DENIED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationManager.requestLocationUpdates(provider, minTime, minDistance, locationListener);
                }
            } catch (Exception ex) {
                logEvent("Error requesting location updates.  Exception:" + ex, LogLevel.Error);

                locationManager.removeUpdates(locationListener);
                locationManager = null;
                locationListener = null;
            }
        }
    }


    /**
     * 获取UserAgent Accessor to the User-Agent header value the SDK will send to the ad network.
     */
    public String getUserAgent() {
        return userAgent;
    }


    /**
     * 检查广告类型是否是流内广告 Determines if the instance is configured as inline.
     */
    public boolean isInline() {
        return placementType == Consts.PlacementType.Inline;
    }


    /**
     * 检查广告类型是否是插屏   Determines if the instance is configured as interstitial.
     */
    public boolean isInterstitial() {
        return placementType == Consts.PlacementType.Interstitial;

    }


    /**
     * Sets the feature support handler.  This handler is used to control features of the SDK.
     * Set to override default behavior.
     *
     * @param featureSupportHandler MASTAdViewDelegate.FeatureSupportHandler implementation
     */
    public void setFeatureSupportHandler(ZCAdViewDelegate.FeatureSupportHandler featureSupportHandler) {
        this.featureSupportHandler = featureSupportHandler;
    }


    /**
     * Returns the currently configured handler.
     *
     * @return MASTAdViewDelegate.FeatureSupportHandler set with setFeatureSupportHandler().
     */
    public ZCAdViewDelegate.FeatureSupportHandler getFeatureSupportHandler() {
        return featureSupportHandler;
    }


    /**
     * Sets the internal browser listener.  This listener provides information on internal browser related events.
     *
     * @param internalBrowserListener MASTAdViewDelegate.InternalBrowserListener implementation
     */
    public void setInternalBrowserListener(ZCAdViewDelegate.InternalBrowserListener internalBrowserListener) {
        this.internalBrowserListener = internalBrowserListener;
    }


    /**
     * Returns the currently configured listener.
     *
     * @return MASTAdViewDelegate.InternalBrowserListener set with setInternalBrowserListener().
     */
    public ZCAdViewDelegate.InternalBrowserListener getInternalBrowserListener() {
        return internalBrowserListener;
    }


    /**
     * Sets the log listener.  This listener provides the ability to override default logging behavior.
     *
     * @param logListener MASTAdViewDelegate.LogListener implementation
     */
    public void setLogListener(ZCAdViewDelegate.LogListener logListener) {
        this.logListener = logListener;
    }


    /**
     * Returns the currently configured listener.
     *
     * @return MASTAdViewDelegate.LogListener set with setLogListener().
     */
    public ZCAdViewDelegate.LogListener getLogListener() {
        return logListener;
    }

    /**
     * Sets the interval between updates.
     * <p>
     * Invoke update() after setting for changes to apply immediately.
     *
     * @param updateInterval Time interval in seconds between ad requests.
     */
    public void setUpdateInterval(int updateInterval) {
        this.updateInterval = updateInterval;
    }


    /**
     * Returns the currently configured update interval.
     *
     * @return Time interval in seconds between ad requests.
     */
    public int getUpdateInterval() {
        return updateInterval;
    }


    /**
     * Sets the zone on the ad network to obtain ad content.
     * <p>
     * REQUIRED - If not set updates will fail.
     *
     * @param zone Ad network zone.
     */
    public void setZone(String zone) {
        this.zone = zone;
    }


    /**
     * Returns the currently configured ad network zone.
     *
     * @return Ad network zone.
     */
    public String getZone() {
        return zone;
    }


    /**
     * Sets the instance test mode.  If set to test mode the instance will request test ads for the configured zone.
     * <p>
     * Warning: This should never be enabled for application releases.
     *
     * @param test true to set test mode, false to disable test mode.
     */
    public void setTest(boolean test) {
        this.test = test;
    }


    /**
     * Access for test mode state of the instance.
     *
     * @return true if the instance is set to test mode, false if test mode is disabled.
     */
    public boolean isTest() {
        return test;
    }


    /**
     * Used with interstitial to show a close button.  If not set, users will not see a close button
     * on interstitial ads.  Does nothing if used with inline instances.
     *
     * @param showCloseButton true to show a close button, false to not show a close button.
     */
    public void setShowCloseButton(boolean showCloseButton) {
        this.showCloseButton = showCloseButton;

        // TODO: Make this apply immediately after the fact, vs on showInterstitialAd.
    }


    /**
     * Returns state of showing the close button for interstitial ads.
     *
     * @return true if showing close button, false if close button will not be shown.
     */
    public boolean getShowCloseButton() {
        return showCloseButton;
    }


    /**
     * Sets the delay time between showing an interstitial with showInterstitialAd() and showing the close
     * button.  A value of 0 indicates the button should be shown immediately.
     *
     * @param closeButtonDelay Time interval in seconds to delay showing a close button after showing interstitial ad.
     */
    public void setCloseButtonDelay(int closeButtonDelay) {
        this.closeButtonDelay = closeButtonDelay;
    }


    /**
     * Returns the currently configured close button delay.
     *
     * @return Time interval in seconds to delay showing a close button after showing interstitial.
     */
    public int getCloseButtonDelay() {
        return closeButtonDelay;
    }


    /**
     * Allows custom close buttons to override SDK default.  If set the provided drawable will be used
     * for the close button for interstitial and rich media ads (if ad uses SDK provided close button).
     *
     * @param closeButtonCustomDrawable Drawable used to override the default close button image or null to use the default.
     */
    public void setCloseButtonCustomDrawable(Drawable closeButtonCustomDrawable) {
        this.closeButtonCustomDrawable = closeButtonCustomDrawable;
    }


    /**
     * Returns the currently configured close button custom drawable.
     *
     * @return Returns the custom close button drawable set with setCloseButtonCustomDrawable() or null if one is not set.
     */
    public Drawable getCloseButtonCustomDrawable() {
        return closeButtonCustomDrawable;
    }


    /**
     * Controls enablement of the internal browser.  If used, a dialog will be used to show a browser in the
     * application for ads that are clicked on (that open URLs).  If not used an intent is started to invoke
     * the system browser (or whatever is configured to handle the intent).
     *
     * @param useInternalBrowser true to use the internal browser, false to not use the internal browser.
     */
    public void setUseInternalBrowser(boolean useInternalBrowser) {
        this.useInternalBrowser = useInternalBrowser;
    }


    /**
     * Returns the currently configured internal browser setting.
     *
     * @return true if using the internal browser, false if not using the internal browser.
     */
    public boolean getUseInternalBrowser() {
        return useInternalBrowser;
    }


    /**
     * Determines if the internal browser is open.
     *
     * @return true if the internal browser is open, false if not.
     */
    public boolean isInternalBrowserOpen() {
        return (browserDialog != null) && browserDialog.isShowing();

    }


    /**
     * Determines if location detection is enabled.  If enabled, the SDK will use the location services
     * of the device to determine the device's location ad add ad request parameters (lat/long) to the ad
     * request.  Location detection can be enabled with setLocationDetectionEnabled() or enableLocationDetection().
     *
     * @return true if location detection is enabled, false if not
     */
    public boolean isLocationDetectionEnabled() {
        return locationManager != null;

    }


    /**
     * Sets the log level of the instance.  Logging is done through console logging.
     *
     * @param logLevel LogLevel
     */
    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }


    /**
     * Returns the currently configured log level.
     *
     * @return currently configured LogLevel
     */
    public LogLevel getLogLevel() {
        return logLevel;
    }

    /**
     * Resets instance state to it's default (doesn't reset configured parameters).
     * Stops update interval timer, closes internal browser if open, disables location detection.
     * <p>
     * Invoke this method to stop any ad processing.  This should be done for ads that have a update
     * time interval set with setUpdateInterval() before the owning context/activity is destroyed.
     */
    public void reset() {

        removeContent();

        if (adUpdateIntervalFuture != null) {
            adUpdateIntervalFuture.cancel(true);
            adUpdateIntervalFuture = null;
        }

        if (interstitialDelayFuture != null) {
            interstitialDelayFuture.cancel(true);
            interstitialDelayFuture = null;
        }

        closeInternalBrowser();
        browserDialog = null;

        setLocationDetectionEnabled(false);
    }


    /**
     * 移除mraid广告view  Removes any displayed ad content.
     */
    public void removeContent() {

        Defaults.handler.post(new Runnable() {
            @Override
            public void run() {
                resetRichMediaAd();
                switch (placementType) {
                    case Inline:
                        ((ViewGroup) getParent()).removeAllViews();
                        break;
                }
            }
        });

    }


    public void showInterstitial() {
        showInterstitialWithDuration(0);
    }


    public void showInterstitialWithDuration(int durationSeconds) {
        if (!isInterstitial()) {
            throw new IllegalStateException("showInterstitialAd requires interstitial instance");
        }

        if (interstitialDelayFuture != null) {
            interstitialDelayFuture.cancel(true);
            interstitialDelayFuture = null;
        }

        registerVolumeChangeReceiver();

        // prepareCloseButton();

        if (durationSeconds > 0) {
            interstitialDelayFuture = Background.getExecutor().schedule(new Runnable() {
                @Override
                public void run() {
                    closeInterstitial();
                }

            }, durationSeconds, TimeUnit.SECONDS);
        }
    }


    // main/background thread
    public void closeInterstitial() {
        if (interstitialDelayFuture != null) {
            interstitialDelayFuture.cancel(true);
            interstitialDelayFuture = null;
        }

        interstitialMraidClose();
    }


    private void addContentView(View view, LayoutParams layoutParams) {
        switch (placementType) {
            case Inline:
                if (view.getParent() != this) {
                    if (view.getParent() != null) {
                        ViewGroup viewGroup = (ViewGroup) view.getParent();
                        viewGroup.removeView(view);
                    }

                    addView(view, layoutParams);
                }
                break;

            case Interstitial:
                break;
        }
    }

    /**
     * 开始渲染富媒体资源
     */
    public void renderRichMedia(PageAdVO pageAdVO) {
        getWebView().stopLoading();

        LayoutParams layoutParams = new LayoutParams(MATCH_PARENT, MATCH_PARENT);

        addContentView(webView, layoutParams);

        try {
            webView.loadFragment(pageAdVO, mraidBridge);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void resetRichMediaAd() {
        if (mraidBridge != null) {
            mraidBridgeHandler.mraidClose(mraidBridge);

            if (mraidExpandDialog != null) {
                mraidExpandDialog.dismiss();
                mraidExpandDialog = null;
            }

            if (mraidResizeLayout != null) {
                ViewGroup parent = (ViewGroup) mraidResizeLayout.getParent();
                if (parent != null) {
                    parent.removeView(mraidResizeLayout);
                }

                mraidResizeLayout = null;
                mraidResizeCloseArea = null;
            }

            mraidBridge = null;
        }

        if (webView != null) {
            webView.clearView();
            webView.clearHistory();
        }
    }


    // main thread
    private void renderTwoPartExpand(String url) {
        mraidTwoPartExpand = true;

        mraidTwoPartWebView = new WebView(getContext());
        mraidTwoPartWebView.setHandler(webViewHandler);
        mraidTwoPartBridgeInit = false;
        mraidTwoPartBridge = new Bridge(mraidTwoPartWebView, mraidBridgeHandler);

        mraidTwoPartWebView.loadUrl(url);

        mraidExpandDialog = new ExpandDialog(getContext());
        mraidExpandDialog.addView(mraidTwoPartWebView);
        mraidExpandDialog.show();
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    private void interstitialMraidClose() {
        if (mraidBridge != null) {
            switch (placementType) {
                case Interstitial:
                    mraidBridge.setState(Consts.State.Hidden);
                    mraidBridgeHandler.mraidClose(mraidBridge);
                    unregisterVolumeChangeReceiver();
                    break;
            }

        }

        resetMRAIDOrientation();
    }

    @Override
    protected void onDetachedFromWindow() {
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }

        if (activity.isFinishing()) {
            return;
        }

        if (mraidBridge != null) {
            switch (mraidBridge.getState()) {
                case Loading:
                case Hidden:
                case Default:
                    break;

                case Resized:
                case Expanded:
                    mraidBridgeHandler.mraidClose(mraidBridge);
                    break;
            }
        }
        super.onDetachedFromWindow();
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (webView != null) {
            if (webView.getParent() == this) {
                webView.layout(0, 0, getWidth(), getHeight());
            }

            if (mraidBridge != null) {
                if ((!changed) && webView.hasFocus()) {
                    return;
                }

                updateMRAIDLayoutForState(mraidBridge, mraidBridge.getState());
            }
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        super.measureChildren(widthMeasureSpec, heightMeasureSpec);
    }


    // background/main thread
    private void openUrl(final String url, final boolean bypassInternalBrowser) {
        runOnUiThread(new Runnable() {
            public void run() {
                if ((!bypassInternalBrowser) && useInternalBrowser) {
                    openInternalBrowser(url);
                    return;
                }

                boolean googlePlayUrl = Utils.isGooglePlayUrl(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (googlePlayUrl) {
                    intent.setPackage(GOOGLE_PLAY_PKG_NAME);
                }
                if (intentAvailable(intent)) {
                    getContext().startActivity(intent);
                } else {
                    logEvent("Unable to start activity for browsing URL.", LogLevel.Error);
                }
            }
        });
    }


    // main thread
    private void openInternalBrowser(String url) {
        if (browserDialog == null) {
            browserDialog = new BrowserDialog(getContext(), url, new BrowserDialog.Handler() {
                @Override
                public void browserDialogDismissed(BrowserDialog browserDialog) {
                    if (internalBrowserListener != null) {
                        internalBrowserListener.onInternalBrowserDismissed(
                                ZCAdView.this);
                    }
                }


                @Override
                public void browserDialogOpenUrl(BrowserDialog browserDialog, String url) {
                    openUrl(url, true);
                    browserDialog.dismiss();
                }
            });
        } else {
            browserDialog.loadUrl(url);
        }

        if (!browserDialog.isShowing()) {
            browserDialog.show();
        }

        if (internalBrowserListener != null) {
            internalBrowserListener.onInternalBrowserPresented(this);
        }
    }


    // main thread
    private void closeInternalBrowser() {
        if (browserDialog != null) {
            if (browserDialog.isShowing()) {
                browserDialog.dismiss();
            }
        }
    }


    private void initMRAIDBridge(Bridge bridge) {
        if (bridge == null) {
            return;
        }

        synchronized (bridge) {
            if ((bridge == mraidBridge) && (!mraidBridgeInit)) {
                return;
            } else if ((bridge == mraidTwoPartBridge) && (!mraidTwoPartBridgeInit)) {
                return;
            }

            if (!bridge.webView.isLoaded()) {
                return;
            }

            if (bridge.getState() != Consts.State.Loading) {
                return;
            }

            // Initialize the bridge.
            bridge.setPlacementType(placementType);
            bridge.setEnv(getZone());

            setMRAIDSupportedFeatures(bridge);

            if (bridge == mraidBridge) {
                switch (placementType) {
                    case Inline:
                        updateMRAIDLayoutForState(bridge, Consts.State.Default);
                        break;

                    case Interstitial:
                        updateMRAIDLayoutForState(bridge, Consts.State.Expanded);
                        break;
                }

                bridge.setState(Consts.State.Default);
            } else {
                updateMRAIDLayoutForState(bridge, Consts.State.Expanded);
                bridge.setState(Consts.State.Expanded);
            }

            bridge.sendReady();
        }
    }


    private void setMRAIDSupportedFeatures(Bridge bridge) {
        if (bridge == null) {
            return;
        }

        Boolean smsSupported = null;
        Boolean phoneSupported = null;
        Boolean calendarSupported = null;
        Boolean pictureSupported = null;
        Boolean vpaidSupported = null;

        if (featureSupportHandler != null) {
            smsSupported = featureSupportHandler.shouldSupportSMS(this);
            phoneSupported = featureSupportHandler.shouldSupportPhone(this);
            calendarSupported = featureSupportHandler.shouldSupportCalendar(this);
            pictureSupported = featureSupportHandler.shouldSupportStorePicture(this);
            vpaidSupported = featureSupportHandler.shouldSupportVPAID(this);
        }

        if (smsSupported == null) {
            smsSupported = getContext().checkCallingOrSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
        }
        if (phoneSupported == null) {
            phoneSupported = getContext().checkCallingOrSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
        }
        if (calendarSupported == null) {
            calendarSupported = ((getContext().checkCallingOrSelfPermission(Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) &&
                    (getContext().checkCallingOrSelfPermission(Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED));
        }
        if (pictureSupported == null) {
            pictureSupported = getContext().checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }

        vpaidSupported = true;

        bridge.setSupportedFeature(Consts.Feature.SMS, smsSupported);
        bridge.setSupportedFeature(Consts.Feature.Tel, phoneSupported);
        bridge.setSupportedFeature(Consts.Feature.Calendar, calendarSupported);
        bridge.setSupportedFeature(Consts.Feature.StorePicture, pictureSupported);
        bridge.setSupportedFeature(Consts.Feature.InlineVideo, false);
        bridge.setSupportedFeature(Consts.Feature.VPAID, vpaidSupported);
    }


    private void updateMRAIDLayoutForState(Bridge bridge, Consts.State state) {
        if (bridge == null) {
            return;
        }
        if (this.webView == null) {
            return;
        }
        WebView webView = this.webView;
        if (bridge == mraidTwoPartBridge) {
            webView = mraidTwoPartWebView;
        }

        boolean viewable = webView.isShown();

        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        View rootView = getRootView();

        float defaultWidthPx = getWidth();
        float defaultHeightPx = getHeight();
        int defaultWidthDp = pxToDp(defaultWidthPx);
        int defaultHeightDp = pxToDp(defaultHeightPx);

        float currentWidthPx = webView.getWidth();
        float currentHeightPx = webView.getWidth();
        int currentWidthDp = pxToDp(currentWidthPx);
        int currentHeightDp = pxToDp(currentHeightPx);

        int[] containerScreenLocation = new int[2];
        getLocationOnScreen(containerScreenLocation);
        int containerScreenX = pxToDp(containerScreenLocation[0]);
        int containerScreenY = pxToDp(containerScreenLocation[1]);

        int[] webViewScreenLocation = new int[2];
        if ((state == Consts.State.Resized) && (mraidResizeLayout != null)) {
            RelativeLayout.LayoutParams webViewLayoutParams = (RelativeLayout.LayoutParams) webView.getLayoutParams();
            webViewScreenLocation[0] = webViewLayoutParams.leftMargin;
            webViewScreenLocation[1] = webViewLayoutParams.topMargin;
        } else {
            webView.getLocationOnScreen(webViewScreenLocation);
        }
        int webViewScreenX = pxToDp(webViewScreenLocation[0]);
        int webViewScreenY = pxToDp(webViewScreenLocation[1]);

        int screenWidthDp = pxToDp(displayMetrics.widthPixels);
        int screenHeightDp = pxToDp(displayMetrics.heightPixels);
        int maxWidthDp = pxToDp(rootView.getWidth());
        int maxHeightDp = pxToDp(rootView.getHeight());

        // Android fails at notifying on post-presentation so we'll use
        // the crystal ball and foresee what it should do.
        switch (state) {
            case Loading:
                break;

            case Default:
                webViewScreenX = containerScreenX;
                webViewScreenY = containerScreenY;
                currentWidthDp = defaultWidthDp;
                currentHeightDp = defaultHeightDp;
                break;

            case Hidden:
            case Resized:
                break;

            case Expanded:
                webViewScreenX = 0;
                webViewScreenY = 0;
                currentWidthDp = screenWidthDp;
                currentHeightDp = screenHeightDp;
        }

        if (isInterstitial()) {
            containerScreenX = 0;
            containerScreenY = 0;
            maxWidthDp = screenWidthDp;
            maxHeightDp = screenHeightDp;
            defaultWidthDp = screenWidthDp;
            defaultHeightDp = screenHeightDp;
            currentWidthDp = screenWidthDp;
            currentHeightDp = screenHeightDp;
        }

        bridge.setScreenSize(screenWidthDp, screenHeightDp);
        bridge.setMaxSize(maxWidthDp, maxHeightDp);
        bridge.setDefaultPosition(containerScreenX, containerScreenY, defaultWidthDp,
                defaultHeightDp);
        bridge.setCurrentPosition(webViewScreenX, webViewScreenY, currentWidthDp, currentHeightDp);
        bridge.setViewable(viewable);
        int percentage = 0;
        if (state != Consts.State.Hidden) {
            if (viewable) {
                percentage = 100;
            }
        }
        bridge.setCurrentExposure(percentage, currentWidthDp, currentHeightDp, webViewScreenX,
                webViewScreenY);
        String orientation = getOrientation();

        boolean locked = getOrientationLocked();

        bridge.setCurrentAppOrientation(orientation, locked);
    }


    /**
     * 设备朝向锁定
     *
     * @return true 锁定，false 自由旋转
     */
    private boolean getOrientationLocked() {
        return Settings.System.getInt(getContext().getApplicationContext().getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 0;
    }


    private String getOrientation() {
        String orient = "portrait";
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == SCREEN_ORIENTATION_LANDSCAPE) {
            orient = "landscape";
        }
        return orient;
    }


    // main thread
    @SuppressLint("WrongConstant")
    private void setMRAIDOrientation() {
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }

        if (mraidOriginalOrientation == OrientationReset) {
            mraidOriginalOrientation = activity.getRequestedOrientation();
        }

        OrientationProperties orientationProperties = mraidBridge.getOrientationProperties();

        Consts.ForceOrientation forceOrientation = orientationProperties.getForceOrientation();
        switch (forceOrientation) {
            case Portrait:
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case Landscape:
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case None:
                break;
        }

        if (orientationProperties.getAllowOrientationChange()) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        } else {
            if (forceOrientation == Consts.ForceOrientation.None) {
                int currentOrientation = activity.getResources().getConfiguration().orientation;

                switch (currentOrientation) {
                    case Configuration.ORIENTATION_PORTRAIT:
                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        break;
                    case Configuration.ORIENTATION_LANDSCAPE:
                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        break;
                    default:
                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
                        break;
                }
            }
        }
    }


    // main thread
    @SuppressLint("WrongConstant")
    private void resetMRAIDOrientation() {
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }

        if (mraidOriginalOrientation != OrientationReset) {
            activity.setRequestedOrientation(mraidOriginalOrientation);
        }
    }


    // main thread
    private void prepareCloseButton() {
        if (mraidExpandDialog != null) {
            mraidExpandDialog.setCloseImage(null);
        }

        if (closeButtonFuture != null) {
            closeButtonFuture.cancel(true);
            closeButtonFuture = null;
        }

        if (mraidBridge != null) {
            switch (mraidBridge.getState()) {
                case Default:
                    if (isInterstitial()) {
                        if (!mraidBridge.getExpandProperties().useCustomClose()) {
                            showCloseButton();
                        }
                        return;
                    }
                    break;

                case Expanded:
                    // When expanded use the built in button or the custom one, else nothing else.
                    if (!mraidBridge.getExpandProperties().useCustomClose()) {
                        showCloseButton();
                    }
                    return;

                case Resized:
                    if (!mraidBridge.getExpandProperties().useCustomClose()) {
                        showCloseButton();
                    }
                    // The ad creative MUST supply it's own close button.
                    return;
                default:
                    break;
            }
        }

        if (closeButtonDelay < 0) {
            return;
        }

        if (closeButtonDelay == 0) {
            showCloseButton();
            return;
        }

        closeButtonFuture = Background.getExecutor().schedule(new Runnable() {
            @Override
            public void run() {
                showCloseButton();
            }

        }, closeButtonDelay, TimeUnit.SECONDS);
    }


    // main thread
    @SuppressWarnings("deprecation")
    private void showCloseButton() {
        Drawable closeButtonDrawable = closeButtonCustomDrawable;

        if (closeButtonDrawable == null) {
            Drawable closeButtonNormalDrawable = Assets.getDrawableFromBase64(getResources(),
                    Assets.close_button_normal);
            Drawable closeButtonPressedDrawable = Assets.getDrawableFromBase64(getResources(),
                    Assets.close_button_pressed);

            closeButtonDrawable = new StateListDrawable();
            ((StateListDrawable) closeButtonDrawable).addState(
                    new int[]{-android.R.attr.state_pressed}, closeButtonNormalDrawable);
            ((StateListDrawable) closeButtonDrawable).addState(
                    new int[]{android.R.attr.state_pressed}, closeButtonPressedDrawable);

        }

        if (mraidTwoPartExpand) {
            if (mraidTwoPartBridge != null) {
                if (mraidTwoPartBridge.getState() == Consts.State.Expanded) {
                    mraidExpandDialog.setCloseImage(closeButtonDrawable);
                }
            }
        }
        if (mraidBridge != null) {
            switch (mraidBridge.getState()) {
                case Loading:
                case Default:
                case Hidden:
                    // Like text or image ads just put the close button at the top of the stack
                    // on the ad view and not on the webview.
                    break;

                case Expanded:
                    mraidExpandDialog.setCloseImage(closeButtonDrawable);
                    return;

                case Resized:
                    // Supporting API8 and higher.  Avoiding reflection for now.
                    mraidResizeCloseArea.setBackgroundDrawable(closeButtonDrawable);
                    return;
            }
        }

        switch (placementType) {
            case Inline: {
                // TODO: Support inline close button?
                break;
            }

            case Interstitial: {
                break;
            }
        }
    }


    /**
     * WebView 加载状态的回调
     */
    private class WebViewHandler implements WebView.Handler {
        @Override
        public void webViewPageStarted(WebView webView) {

        }


        @Override
        public void webViewPageFinished(WebView webView) {
            if (mraidBridge != null) {
                if (webView == mraidBridge.webView) {
                    initMRAIDBridge(mraidBridge);
                    return;
                }
            }
            if ((mraidTwoPartBridge != null)) {
                if (webView == mraidTwoPartBridge.webView) {
                    initMRAIDBridge(mraidTwoPartBridge);
                }
            }
        }


        @Override
        public void webViewReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
            logEvent("Error loading rich media ad content.  Error code:" + String.valueOf(errorCode) + " Description:" + description, LogLevel.Error);

            removeContent();
        }


        @Override
        public boolean webViewShouldOverrideUrlLoading(WebView view, String url) {
            if (mraidTwoPartExpand) {
                if (mraidTwoPartWebView != null) {
                    mraidTwoPartWebView.loadUrl(url);
                }
                return true;
            }
            openUrl(url, false);

            return true;
        }


        @Override
        public void webViewExposureChange(WebView view, Bridge bridge, boolean visible) {
            if (visible) {
                if (mraidBridge != null && mraidBridge == bridge) {
                    updateMRAIDLayoutForState(mraidBridge, mraidBridge.getState());
                    return;
                }
                if (mraidTwoPartBridge != null && mraidTwoPartBridge == bridge) {
                    updateMRAIDLayoutForState(mraidTwoPartBridge, mraidTwoPartBridge.getState());
                }
            }
        }
    }


    /**
     * MRAID js调用native命令回调接口实现
     */
    private class MRAIDHandler implements Bridge.Handler {
        @Override
        public void mraidInit(final Bridge bridge) {
            if ((bridge != mraidBridge) && (bridge != mraidTwoPartBridge)) {
                return;
            }

            if (bridge == mraidBridge) {
                mraidBridgeInit = true;
            } else {
                mraidTwoPartBridgeInit = true;
            }

            initMRAIDBridge(bridge);
        }


        @Override
        public void mraidClose(final Bridge bridge) {
            if ((bridge != mraidBridge) && (bridge != mraidTwoPartBridge)) {
                return;
            }

            if (isInterstitial()) {
                if (interstitialListener != null) {
                    interstitialListener.onCloseButtonClick(ZCAdView.this);
                }
                resetMRAIDOrientation();
                return;
            }

            switch (bridge.getState()) {
                case Loading:
                case Hidden:
                    break;

                case Default:
                    // MRAID specification is weak in this case so ignoring.
                    break;

                case Resized:
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (mraidResizeLayout == null) {
                                return;
                            }

                            ViewGroup parent = (ViewGroup) webView.getParent();
                            if (parent != null) {
                                parent.removeView(webView);
                            }
                            parent = (ViewGroup) mraidResizeLayout.getParent();
                            if (parent != null) {
                                parent.removeView(mraidResizeLayout);
                            }
                            mraidResizeLayout = null;
                            mraidResizeCloseArea = null;

                            LayoutParams layoutParams = new LayoutParams(MATCH_PARENT,
                                    MATCH_PARENT);
                            addView(webView, layoutParams);

                            updateMRAIDLayoutForState(bridge, Consts.State.Default);
                            bridge.setState(Consts.State.Default);

                        }
                    });
                    break;

                case Expanded:
                    if (mraidExpandDialog == null) {
                        return;
                    }

                    mraidExpandDialog.dismiss();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            // TODO: Race condition... if the creative calls close
                            // this will invoke dismiss but the dismiss handler will
                            // also turn around and call close.  One of them is triggering
                            // an NPE.
                            if (!mraidTwoPartExpand) { // 自扩展
                                mraidExpandDialog.removeView(webView);
                                addView(webView);
                            } else { // 扩展url
                                mraidExpandDialog.removeView(mraidTwoPartWebView);

                                mraidTwoPartWebView = null;
                                mraidTwoPartBridge = null;
                                mraidTwoPartExpand = false;
                            }

                            mraidExpandDialog = null;

                            // For normal or two part expand the original bridge
                            // gets reset back to the default state.
                            updateMRAIDLayoutForState(mraidBridge, Consts.State.Default);
                            mraidBridge.setState(Consts.State.Default);

                            resetMRAIDOrientation();

                        }
                    });
                    break;
            }
        }


        @Override
        public void mraidUnload(Bridge bridge) {
            if ((bridge != mraidBridge) && (bridge != mraidTwoPartBridge)) {
                return;
            }

            if (isInterstitial()) {
                closeInterstitial();
                resetMRAIDOrientation();
                return;
            }

            removeContent();
        }


        @Override
        public void mraidOpen(final Bridge bridge, String url) {
            if ((bridge != mraidBridge) && (bridge != mraidTwoPartBridge)) {
                return;
            }

            openUrl(url, false);
        }


        @Override
        public void mraidUpdateCurrentPosition(final Bridge bridge) {
            if ((bridge != mraidBridge) && (bridge != mraidTwoPartBridge)) {
                return;
            }

            updateMRAIDLayoutForState(bridge, bridge.getState());
        }


        @Override
        public void mraidUpdatedExpandProperties(final Bridge bridge) {
            if ((bridge != mraidBridge) && (bridge != mraidTwoPartBridge)) {
                return;
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    prepareCloseButton();
                }
            });
        }


        @Override
        public void mraidExpand(final Bridge bridge, final String url) {
            if ((bridge != mraidBridge) && (bridge != mraidTwoPartBridge)) {
                return;
            }

            if (isInterstitial()) {
                bridge.sendErrorMessage("Can not expand with placementType interstitial.",
                        Consts.CommandExpand);
                return;
            }

            boolean hasUrl = false;
            if (!TextUtils.isEmpty(url)) {
                hasUrl = true;
            }

            switch (bridge.getState()) {
                case Loading:
                    if (mraidTwoPartExpand && (!hasUrl)) {
                        // This is the SDK setting the expand state when initializing
                        //  the two part expand operation.
                        break;
                    }

                    bridge.sendErrorMessage("Can not expand while state is loading.",
                            Consts.CommandExpand);
                    return;

                case Hidden:
                    // Expand from this state is a no-op.
                    return;

                case Expanded:
                    bridge.sendErrorMessage("Can not expand while state is expanded.",
                            Consts.CommandExpand);
                    return;

                case Default:
                case Resized:
                    break;
            }

            if (!hasUrl) {
                // Normal expand.
                runOnUiThread(new Runnable() {
                    public void run() {
                        setMRAIDOrientation();
                        ViewGroup parent = (ViewGroup) webView.getParent();
                        if (parent != null) {
                            parent.removeView(webView);
                        }
                        if (mraidResizeLayout != null) {
                            parent = (ViewGroup) mraidResizeLayout.getParent();
                            if (parent != null) {
                                parent.removeView(mraidResizeLayout);
                            }
                            mraidResizeLayout = null;
                            mraidResizeCloseArea = null;
                        }

                        mraidExpandDialog = new ExpandDialog(getContext());
                        mraidExpandDialog.addView(webView);
                        mraidExpandDialog.show();
                    }
                });
                return;
            }
            // Two part expand.
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (bridge.getState() == Consts.State.Resized) {
                        ViewGroup parent = (ViewGroup) webView.getParent();
                        if (parent != null) {
                            parent.removeView(webView);
                        }
                        if (mraidResizeLayout != null) {
                            parent = (ViewGroup) mraidResizeLayout.getParent();
                            if (parent != null) {
                                parent.removeView(mraidResizeLayout);
                            }
                            mraidResizeLayout = null;
                            mraidResizeCloseArea = null;
                        }
                        LayoutParams layoutParams = new LayoutParams(MATCH_PARENT,
                                MATCH_PARENT);
                        addView(webView, layoutParams);

                        updateMRAIDLayoutForState(bridge, Consts.State.Default);
                        bridge.setState(Consts.State.Default);
                    }
                    setMRAIDOrientation();
                    renderTwoPartExpand(url);
                }
            });
        }


        @Override
        public void mraidUpdatedOrientationProperties(final Bridge bridge) {
            if ((bridge != mraidBridge) && (bridge != mraidTwoPartBridge)) {
                return;
            }
            if (bridge.getState() == Consts.State.Expanded || isInterstitial()) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        setMRAIDOrientation();
                    }
                });
            }
        }


        @Override
        public void mraidUpdatedResizeProperties(final Bridge bridge) {
            // Nothing to act on here (bridge has properties updated).
        }


        @Override
        public void mraidResize(final Bridge bridge) {
            if ((bridge != mraidBridge) && (bridge != mraidTwoPartBridge)) {
                return;
            }

            if (isInterstitial()) {
                bridge.sendErrorMessage("Can not resize with placementType interstitial.",
                        Consts.CommandResize);
                return;
            }

            switch (bridge.getState()) {
                case Loading:
                case Hidden:
                case Expanded:
                    bridge.sendErrorMessage("Can not resize loading, hidden or expanded.",
                            Consts.CommandResize);
                    return;

                case Default:
                case Resized:
                    // Resize permitted.
                    break;
            }

            DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
            int screenWidth = pxToDp(displayMetrics.widthPixels);
            int screenHeight = pxToDp(displayMetrics.heightPixels);

            int[] currentScreenLocation = new int[2];
            getLocationOnScreen(currentScreenLocation);
            int currentX = pxToDp(currentScreenLocation[0]);
            int currentY = pxToDp(currentScreenLocation[1]);

            ResizeProperties resizeProperties = bridge.getResizeProperties();
            boolean allowOffscreen = resizeProperties.getAllowOffscreen();
            int x = currentX + resizeProperties.getOffsetX();
            int y = currentY + resizeProperties.getOffsetY();
            int width = resizeProperties.getWidth();
            int height = resizeProperties.getHeight();
            Consts.CustomClosePosition customClosePosition = resizeProperties.getCustomClosePosition();

            if ((width >= screenWidth) && (height >= screenHeight)) {
                bridge.sendErrorMessage("Size must be smaller than the max size.", Consts.CommandResize);
                return;
            } else if ((width < CloseAreaSizeDp) || (height < CloseAreaSizeDp)) {
                bridge.sendErrorMessage("Size must be at least the minimum close area size.",
                        Consts.CommandResize);
                return;
            }

            int minX = 0;
            int minY = statusBarHeightDp();

            if (!allowOffscreen) {
                int desiredScreenX = x;
                int desiredScreenY = y;
                int resultingScreenX = desiredScreenX;
                int resultingScreenY = desiredScreenY;

                if (width > screenWidth) {
                    width = screenWidth;
                }

                if (height > screenHeight) {
                    height = screenHeight;
                }

                if (desiredScreenX < minX) {
                    resultingScreenX = minX;
                } else if ((desiredScreenX + width) > screenWidth) {
                    double diff = desiredScreenX + width - screenWidth;
                    resultingScreenX -= diff;
                }

                if (desiredScreenY < minY) {
                    resultingScreenY = minY;
                } else if ((desiredScreenY + height) > screenHeight) {
                    double diff = desiredScreenY + height - screenHeight;
                    resultingScreenY -= diff;
                }

                double adjustedX = desiredScreenX - resultingScreenX;
                double adjustedY = desiredScreenY - resultingScreenY;
                x -= adjustedX;
                y -= adjustedY;
            }

            // Determine where the close control area will be.  This MUST be on screen.
            // By default it is in the top right but the ad can specify where it should be.
            // The ad MUST provide the graphic for it or some other means to close the resize.
            // These coordinates are relative to the container (the resized view).
            int closeControlX = width - CloseAreaSizeDp;
            int closeControlY = 0;

            switch (customClosePosition) {
                case TopRight:
                    // Already configured above.
                    break;

                case TopCenter:
                    closeControlX = width / 2 - CloseAreaSizeDp / 2;
                    closeControlY = 0;
                    break;

                case TopLeft:
                    closeControlX = 0;
                    closeControlY = 0;
                    break;

                case BottomLeft:
                    closeControlX = 0;
                    closeControlY = height - CloseAreaSizeDp;
                    break;

                case BottomCenter:
                    closeControlX = width / 2 - CloseAreaSizeDp / 2;
                    closeControlY = height - CloseAreaSizeDp;
                    break;

                case BottomRight:
                    closeControlX = width - CloseAreaSizeDp;
                    closeControlY = height - CloseAreaSizeDp;
                    break;

                case Center:
                    closeControlX = width / 2 - CloseAreaSizeDp / 2;
                    closeControlY = height / 2 - CloseAreaSizeDp / 2;
                    break;
            }

            int resultingCloseControlX = x + closeControlX;
            int resultingCloseControlY = y + closeControlY;
            int resultingCloseControlR = resultingCloseControlX + CloseAreaSizeDp;
            int resultingCloseControlB = resultingCloseControlY + CloseAreaSizeDp;

            if ((resultingCloseControlX < minX) || (resultingCloseControlY < minY) ||
                    (resultingCloseControlR > screenWidth) || (resultingCloseControlB > screenHeight)) {
                bridge.sendErrorMessage("Resize close control must remain on screen.",
                        Consts.CommandResize);
                return;
            }

            // Convert to pixel values.
            final int xPx = dpToPx(x);
            final int yPx = dpToPx(y);
            final int widthPx = dpToPx(width);
            final int heightPx = dpToPx(height);
            final int closeXPx = dpToPx(resultingCloseControlX);
            final int closeYPx = dpToPx(resultingCloseControlY);

            runOnUiThread(new Runnable() {
                public void run() {
                    Activity activity = getActivity();
                    ViewGroup windowDecorView = (ViewGroup) activity.getWindow().getDecorView();

                    RelativeLayout.LayoutParams webViewLayoutParams =
                            new RelativeLayout.LayoutParams(widthPx, heightPx);
                    webViewLayoutParams.setMargins(xPx, yPx, Integer.MIN_VALUE, Integer.MIN_VALUE);

                    RelativeLayout.LayoutParams closeControlLayoutParams =
                            new RelativeLayout.LayoutParams(dpToPx(CloseAreaSizeDp),
                                    dpToPx(CloseAreaSizeDp));
                    closeControlLayoutParams.setMargins(closeXPx, closeYPx, Integer.MIN_VALUE,
                            Integer.MIN_VALUE);

                    if (mraidResizeLayout == null) {
                        ViewGroup webViewParent = (ViewGroup) webView.getParent();
                        if (webViewParent != null) {
                            webViewParent.removeView(webView);
                        }

                        mraidResizeCloseArea = new View(getContext());
                        mraidResizeCloseArea.setBackgroundColor(0x00000000);

                        // 设置第三方的close，这个点击区域还有用否？
                        mraidResizeCloseArea.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (v != mraidResizeCloseArea) {
                                    return;
                                }

                                mraidBridgeHandler.mraidClose(bridge);
                            }
                        });

                        mraidResizeLayout = new RelativeLayout(getContext());
                        mraidResizeLayout.addView(webView, webViewLayoutParams);
                        mraidResizeLayout.addView(mraidResizeCloseArea, closeControlLayoutParams);

                        LayoutParams resizeLayoutParams =
                                new LayoutParams(MATCH_PARENT, MATCH_PARENT);
                        windowDecorView.addView(mraidResizeLayout, 0, resizeLayoutParams);
                        windowDecorView.bringChildToFront(mraidResizeLayout);
                    } else {
                        mraidResizeLayout.updateViewLayout(webView, webViewLayoutParams);
                        mraidResizeLayout.updateViewLayout(mraidResizeCloseArea,
                                closeControlLayoutParams);
                    }

                    updateMRAIDLayoutForState(bridge, Consts.State.Resized);
                    bridge.setState(Consts.State.Resized);

                    prepareCloseButton();

                }
            });
        }


        @Override
        public void mraidPlayVideo(final Bridge bridge, String url) {
            if ((bridge != mraidBridge) && (bridge != mraidTwoPartBridge)) {
                return;
            }

            openUrl(url, true);
        }


        @Override
        public void mraidCreateCalendarEvent(final Bridge bridge, String calendarEvent) {
            if ((bridge != mraidBridge) && (bridge != mraidTwoPartBridge)) {
                return;
            }

            if (featureSupportHandler != null) {
                if (!featureSupportHandler.shouldAddCalendarEntry(
                        ZCAdView.this, calendarEvent)) {
                    bridge.sendErrorMessage("Access denied.", Consts.CommandCreateCalendarEvent);
                    return;
                }
            }
            if (((getContext().checkCallingOrSelfPermission(
                    Manifest.permission.WRITE_CALENDAR) ==
                    PackageManager.PERMISSION_DENIED) &&
                    (getContext().checkCallingOrSelfPermission(
                            Manifest.permission.READ_CALENDAR) ==
                            PackageManager.PERMISSION_DENIED))) {
                bridge.sendErrorMessage("Access denied.", Consts.CommandCreateCalendarEvent);
                return;
            }
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ", Locale.US);

                JSONObject jsonEvent = new JSONObject(calendarEvent);

                final Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");

                if (jsonEvent.has("start")) {
                    String value = jsonEvent.getString("start");
                    long time = dateFormat.parse(value).getTime();
                    intent.putExtra("beginTime", time);
                }

                if (jsonEvent.has("end")) {
                    String value = jsonEvent.getString("end");
                    long time = dateFormat.parse(value).getTime();
                    intent.putExtra("endTime", time);
                }

                if (jsonEvent.has("description")) {
                    String value = jsonEvent.getString("description");
                    intent.putExtra("title", value);
                }

                if (jsonEvent.has("summary")) {
                    String value = jsonEvent.getString("summary");
                    intent.putExtra("description", value);
                }

                if (jsonEvent.has("location")) {
                    String value = jsonEvent.getString("location");
                    intent.putExtra("eventLocation", value);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (intentAvailable(intent)) {
                            getContext().startActivity(intent);

                        } else {
                            logEvent("Unable to start activity for calendary edit.",
                                    LogLevel.Error);
                        }
                    }
                });
            } catch (Exception ex) {
                bridge.sendErrorMessage("Error parsing event data.",
                        Consts.CommandCreateCalendarEvent);
            }
        }


        @Override
        public void mraidSetCloseCounter(Bridge bridge, String count) {
            if ((bridge != mraidBridge) && (bridge != mraidTwoPartBridge)) {
                return;
            }
            try {
                CLOSE_DURATION = Integer.parseInt(count);
            } catch (NumberFormatException ignored) {
            }
        }


        @Override
        public void mraidStorePicture(final Bridge bridge, String url) {
            if ((bridge != mraidBridge) && (bridge != mraidTwoPartBridge)) {
                return;
            }

            if (TextUtils.isEmpty(url)) {
                bridge.sendErrorMessage("Missing picture url.", Consts.CommandStorePicture);
                return;
            }

            if (featureSupportHandler != null) {
                if (!featureSupportHandler.shouldStorePicture(
                        ZCAdView.this, url)) {
                    bridge.sendErrorMessage("Access denied.", Consts.CommandStorePicture);
                    return;
                }
            }

            if (getContext().checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                bridge.sendErrorMessage("Access denied.", Consts.CommandStorePicture);
                return;
            }

            ImageRequest.create(
                    Defaults.NETWORK_TIMEOUT_SECONDS, url, getUserAgent(), false,
                    new ImageRequest.Handler() {
                        @Override
                        public void imageFailed(ImageRequest request, Exception ex) {
                            bridge.sendErrorMessage("Network error connecting to url.",
                                    Consts.CommandStorePicture);

                            logEvent("Error obtaining photo request to save to camera roll.  Exception:" +
                                    ex, LogLevel.Error);
                        }


                        @Override
                        public void imageReceived(ImageRequest request, Object imageObject) {
                            // TODO: android.permission.WRITE_EXTERNAL_STORAGE
                            final Bitmap bitmap = (Bitmap) imageObject;

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String errorMessage = "Error saving picture to device.";

                                    try {
                                        String insertedUrl = MediaStore.Images.Media.insertImage(
                                                getContext().getContentResolver(), bitmap, "AdImage",
                                                "Image created by rich media ad.");
                                        if (TextUtils.isEmpty(insertedUrl)) {
                                            bridge.sendErrorMessage(errorMessage,
                                                    Consts.CommandStorePicture);

                                            logEvent(errorMessage, LogLevel.Error);
                                            return;
                                        }

                                        MediaScannerConnection.scanFile(getContext(),
                                                new String[]{insertedUrl}, null, null);
                                    } catch (Exception ex) {
                                        bridge.sendErrorMessage(errorMessage,
                                                Consts.CommandStorePicture);

                                        logEvent(errorMessage + " Exception:" + ex, LogLevel.Error);
                                    }
                                }
                            });
                        }
                    });
        }
    }


    private class ExpandDialog extends Dialog {
        private ViewGroup container;

        public ExpandDialog(Context context) {
            super(context, android.R.style.Theme_NoTitleBar_Fullscreen);

            LayoutParams layoutParams = new LayoutParams(MATCH_PARENT,
                    MATCH_PARENT);
            container = new RelativeLayout(getContext());
            container.setBackgroundColor(0xff000000);
            setContentView(container, layoutParams);
            RelativeLayout.LayoutParams closeAreaLayoutParams =
                    new RelativeLayout.LayoutParams(dpToPx(CloseAreaSizeDp), dpToPx(CloseAreaSizeDp));
            closeAreaLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            closeAreaLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            closeAreaLayoutParams.rightMargin = dpToPx(15);
            closeAreaLayoutParams.topMargin = dpToPx(15);


            setOnDismissListener(new OnDismissListener() {
                // TODO: Resolve double close when ad invokes close (thus causing a dismiss and another close).
                // Possibly synchronize set/get state on the bridge.

                // TODO: Prevent back key dismissing while interstitial and button not displayed.

                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (mraidBridge != null) {
                        switch (placementType) {
                            case Inline:
                                if (mraidBridge.getState() == Consts.State.Expanded) {
                                    mraidBridgeHandler.mraidClose(mraidBridge);
                                }
                                if (mraidTwoPartBridge != null && mraidTwoPartBridge.getState() == Consts.State.Expanded) {
                                    mraidBridgeHandler.mraidClose(mraidTwoPartBridge);
                                }
                                break;
                        }

                    }

                    resetMRAIDOrientation();
                }
            });
        }

        @Override
        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
        }


        protected void onStart() {
            super.onStart();

            switch (placementType) {
                case Inline:
                    if (mraidTwoPartExpand) {
                        // two part
                        updateMRAIDLayoutForState(mraidTwoPartBridge, Consts.State.Expanded);
                        mraidTwoPartBridge.setState(Consts.State.Expanded);
                    } else {
                        updateMRAIDLayoutForState(mraidBridge, Consts.State.Expanded);
                        mraidBridge.setState(Consts.State.Expanded);
                    }
                    break;

                case Interstitial:
                    if (mraidBridge != null) {
                        updateMRAIDLayoutForState(mraidBridge, Consts.State.Expanded);
                        setMRAIDOrientation();
                        mraidBridge.setState(Consts.State.Default);
                    }
                    break;
            }


            prepareCloseButton();

        }

        public void addView(View view) {
            if (view.getParent() != container) {
                if (view.getParent() != null) {
                    ViewGroup viewGroup = (ViewGroup) view.getParent();
                    viewGroup.removeView(view);
                }

                LayoutParams layoutParams = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
                container.addView(view, layoutParams);
            }

        }


        public void removeView(View view) {
            container.removeView(view);
        }


        public void removeAllViews() {
            int childCount = container.getChildCount();
            for (int i = 0; i < childCount; ++i) {
                View child = container.getChildAt(i);
            }
        }


        @SuppressWarnings("deprecation")
        void setCloseImage(Drawable image) {
            // Supporting API8 and higher.  Avoiding reflection for now.
        }

        @Override
        public void show() {
            super.show();
        }
    }


    private class LocationListener implements android.location.LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            logEvent("LocationListener.onLocationChanged location:" + location.toString(), LogLevel.Debug);

            double lat = location.getLatitude();
            double lon = location.getLongitude();

            mraidBridge.setLocation(lat, lon, 1, "meters", 2, "");
        }


        @Override
        public void onProviderDisabled(String provider) {
            logEvent("LocationListener.onProviderDisabled provider:" + provider, LogLevel.Debug);
        }


        @Override
        public void onProviderEnabled(String provider) {
            logEvent("LocationListener.onProviderEnabled provider:" + provider, LogLevel.Debug);
        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            logEvent("LocationListener.onStatusChanged provider:" + provider + " status:" + String.valueOf(status), LogLevel.Debug);

            if (status == LocationProvider.AVAILABLE) {
                return;
            }
        }
    }

    // onPageLoaded gets fired once the html is loaded into the webView.
    private int getDisplayRotation() {
        WindowManager wm = (WindowManager) getContext().getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getRotation();
    }

    private void registerVolumeChangeReceiver() {
        if (getContext() != null && getContext().getApplicationContext() != null) {
            mSettingsContentObserver = new SettingsContentObserver(getContext(), new Handler());
            getContext().getApplicationContext().getContentResolver().registerContentObserver(Settings.System.CONTENT_URI, true, mSettingsContentObserver);
        }
    }


    private void unregisterVolumeChangeReceiver() {
        if (getContext() != null && getContext().getApplicationContext() != null && mSettingsContentObserver != null) {
            getContext().getApplicationContext().getContentResolver().unregisterContentObserver(mSettingsContentObserver);
        }
    }


    class OrientationBroadcastReceiver extends BroadcastReceiver {
        private Context mContext;

        // -1 until this gets set at least once
        private int mLastRotation = -1;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (mContext == null) {
                return;
            }

            if (!isResumed) {
                return;
            }
            if (Intent.ACTION_CONFIGURATION_CHANGED.equals(intent.getAction())) {
                int orientation = getDisplayRotation();

                if (orientation != mLastRotation) {
                    mLastRotation = orientation;
                    if (mraidBridge != null && webView != null) {
                        updateMRAIDLayoutForState(mraidBridge, mraidBridge.getState());
                        return;
                    }
                    if (mraidTwoPartBridge != null && mraidTwoPartWebView != null) {
                        updateMRAIDLayoutForState(mraidTwoPartBridge,
                                mraidTwoPartBridge.getState());
                    }
                }
            }
        }

        public void register(final Context context) {
            mContext = context.getApplicationContext();
            if (mContext != null) {
                mContext.registerReceiver(this,
                        new IntentFilter(Intent.ACTION_CONFIGURATION_CHANGED));
            }
        }

        public void unregister() {
            if (mContext != null) {
                mContext.unregisterReceiver(this);
                mContext = null;
            }
        }
    }

    private class SettingsContentObserver extends ContentObserver {
        Context context;


        public SettingsContentObserver(Context c, Handler handler) {
            super(handler);
            context = c;
        }


        @Override
        public boolean deliverSelfNotifications() {
            return super.deliverSelfNotifications();
        }


        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            int mediaVolume;
            if (audioManager != null) {
                int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                mediaVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                float volume = ((mediaVolume * 100) / maxVolume);
                if (mraidBridge != null) {
                    mraidBridge.setAudioVolume(volume);
                }
            }
        }
    }

    private void logEvent(String event, LogLevel eventLevel) {
        if (eventLevel.ordinal() > logLevel.ordinal()) {
            return;
        }

        if (logListener != null) {
            if (logListener.onLogEvent(this, event, eventLevel)) {
                return;
            }
        }

        switch (eventLevel) {
            case None:
                break;
            case Debug:
                Log.d(TAG, "eventLevel: " + eventLevel.name() + ", msg: " + event);
                break;
            case Error:
                Log.e(TAG, "eventLevel: " + eventLevel.name() + ", msg: " + event);
                break;
        }

    }

    private Activity getActivity() {
        Activity activity = null;

        Context context = this.context;
        if (context != null && context instanceof Activity) {
            activity = (Activity) context;
        }

        return activity;
    }

    private boolean intentAvailable(Intent intent) {
        PackageManager packageManager = getContext().getPackageManager();
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return (resolveInfoList != null) && (!resolveInfoList.isEmpty());
    }

    protected final void runOnUiThread(final Runnable runnable) {
        if (runnable == null) {
            return;
        }

        Runnable uiRunnable = new Runnable() {
            public void run() {
                try {
                    runnable.run();
                } catch (Exception ex) {
                    logEvent("Exception during runOnUiThread:" + ex, LogLevel.Error);
                }
            }
        };

        Defaults.handler.post(uiRunnable);

    }


    public int statusBarHeightDp() {
        View rootView = getRootView();

        int statusBarHeightDp = 25;
        if (rootView != null) {
            int resourceId = rootView.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusBarHeightDp = pxToDp(rootView.getResources().getDimensionPixelSize(resourceId));
            }
        }

        return statusBarHeightDp;
    }


}
