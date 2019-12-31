package com.jumpraw.mraiddemo.mraid;

public interface ZCAdViewDelegate {

    /**
     * InternalBrowser展示、关闭回调
     */
    public interface InternalBrowserListener {
        /**
         * Invoked when the internal browser has been presented to the user.
         *
         * @param adView
         */
        public void onInternalBrowserPresented(ZCAdView adView);

        /**
         * Invoked when the internal browser has been closed by the user or the SDK.
         *
         * @param adView
         */
        public void onInternalBrowserDismissed(ZCAdView adView);
    }


    /**
     * Interface allowing application developers to control logging.
     */
    public interface LogListener {
        /**
         * Invoked when the SDK logs events.  If applications override logging they can return true to
         * indicate the log event has been consumed and the SDK processing is not needed.
         * <p>
         * Will not be invoked if the adView instance's logLevel is set lower than the event.
         *
         * @param adView
         * @param event    String representing the event to be logged.
         * @param logLevel LogLevel of the event.
         * @return
         */
        public boolean onLogEvent(ZCAdView adView, String event, ZCAdView.LogLevel logLevel);
    }

    /**
     * 接口允许应用程序开发人员控制设备暴露于富媒体广告特性。默认情况下,SDK考虑硬件的可用性和操作系统级别的权限,以确定哪些功能应该报告为富媒体广告。
     *
     * Interface allowing application developers to control which device features are exposed to rich media
     * ads. By default the SDK considers hardware availability and OS level permissions to determine which
     * features should be reported as available to rich media ads. Using this interface, the application
     * can override this process and force features to be reported as available, or not.
     */
    public interface FeatureSupportHandler {
        /**
         * Should sending SMS messages be reported as a supported feature?
         *
         * @return Boolean true if this feature should be reported as a supported feature,
         * Boolean false if it should not, or NULL if the normal SDK hardware/permission check
         * should be performed.
         */
        public Boolean shouldSupportSMS(ZCAdView adView);

        /**
         * Should placing phone calls be reported as a supported feature?
         *
         * @return Boolean true if this feature should be reported as a supported feature,
         * Boolean false if it should not, or NULL if the normal SDK hardware/permission check
         * should be performed.
         */
        public Boolean shouldSupportPhone(ZCAdView adView);

        /**
         * Should vpaid supported feature?
         *
         * @return Boolean true if this feature should be reported as a supported feature,
         * Boolean false if it should not, or NULL if the normal SDK hardware/permission check
         * should be performed.
         */
        public Boolean shouldSupportVPAID(ZCAdView adView);

        /**
         * Should creating calendar entries by reported as a supported feature?
         *
         * @return Boolean true if this feature should be reported as a supported feature,
         * Boolean false if it should not, or NULL if the normal SDK hardware/permission check
         * should be performed.
         */
        public Boolean shouldSupportCalendar(ZCAdView adView);

        /**
         * Should storing pictures to the camera roll be reported as a supported feature?
         *
         * @return Boolean true if this feature should be reported as a supported feature,
         * Boolean false if it should not, or NULL if the normal SDK hardware/permission check
         * should be performed.
         */
        public Boolean shouldSupportStorePicture(ZCAdView adView);

        /**
         * Invoked when an ad intends to store a picture to the device camera role. Return boolean
         * true indicating the user has approved storing the picture, or false otherwise.
         * NOTE: the application developer is responsible for displaying user dialog, and associated
         * details such as running UI code on a UI thread if needed.
         *
         * @param sender The originating ad view where the event was triggered.
         * @param url    String URL of image that will be downloaded and stored, if approved.
         * @return True to allow picture storage, false otherwise.
         */
        public boolean shouldStorePicture(ZCAdView sender, String url);

        /**
         * Invoked when an ad intends to create an event in the users' calendar. Return boolean
         * true indicating the user has approved creating the event, or false otherwise.
         * NOTE: the application developer is responsible for displaying user dialog, and associated
         * details such as running UI code on a UI thread if needed.
         *
         * @param sender             The originating ad view where the event was triggered.
         * @param calendarProperties Complex string describing specifics of the calendar event.
         * @return True to allow picture storage, false otherwise.
         */
        public boolean shouldAddCalendarEntry(ZCAdView sender, String calendarProperties);
    }


    public interface ActivityListener {

        /**
         * Invoked when the ad receives a close button press that should be handled by
         * the application.
         * <p>
         * This only occurs for the close button enabled with showCloseButton() or in
         * the case of a interstitial rich media ad that closes itself.  It will not be
         * sent for rich media close buttons that collapse expanded or resized ads.
         *
         * @param adView
         */
        void onCloseButtonClick(ZCAdView adView);

        void countDownFinish(ZCAdView adView);
    }
}
