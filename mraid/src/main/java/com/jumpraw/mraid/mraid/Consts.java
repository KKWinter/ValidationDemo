package com.jumpraw.mraid.mraid;

public interface Consts {
    enum State {
        Loading,
        Default,
        Expanded,
        Resized,
        Hidden,
    }

    enum PlacementType {
        Inline,
        Interstitial,
    }

    enum Feature {
        SMS,
        Tel,
        Calendar,
        StorePicture,
        InlineVideo,
        VPAID
    }

    enum ForceOrientation {
        Portrait,
        Landscape,
        None,
    }

    enum CustomClosePosition {
        TopLeft,
        TopCenter,
        TopRight,
        Center,
        BottomLeft,
        BottomCenter,
        BottomRight,
    }

    public static final String StateLoading = "loading";
    public static final String StateDefault = "default";
    public static final String StateExpanded = "expanded";
    public static final String StateResized = "resized";
    public static final String StateHidden = "hidden";

    public static final String PlacementTypeInline = "inline";
    public static final String PlacementTypeInterstitial = "interstitial";

    public static final String FeatureSMS = "sms";
    public static final String FeatureTel = "tel";
    public static final String FeatureCalendar = "calendar";
    public static final String FeatureStorePicture = "storePicture";
    public static final String FeatureInlineVideo = "inlineVideo";
    public static final String FeatureVpaid = "vpaid";

    public static final String EventReady = "ready";

    public static final String True = "true";
    public static final String False = "false";

    public static final String Scheme = "mraid";
    public static final String CommandInit = "initialize";
    public static final String CommandClose = "close";
    public static final String CommandUnload = "unload";
    public static final String CommandOpen = "open";
    public static final String CommandUpdateCurrentPosition = "updateCurrentPosition";
    public static final String CommandExpand = "expand";
    public static final String CommandSetExpandProperties = "setExpandProperties";
    public static final String CommandResize = "resize";
    public static final String CommandSetResizeProperties = "setResizeProperties";
    public static final String CommandSetOrientationProperties = "setOrientationProperties";
    public static final String CommandPlayVideo = "playVideo";
    public static final String CommandCreateCalendarEvent = "createCalendarEvent";
    public static final String CommandStorePicture = "storePicture";
    public static final String CommandSetCloseCounter = "setCloseCounter";
    // VPAID
    public static final String VPAIDScheme = "vpaid";
    public static final String CommandVpaidInit = "initVpaid";
    public static final String CommandVpaidAdClickThru = "vpaidAdClickThruIdPlayerHandles";
    public static final String CommandVpaidAdError = "vpaidAdError";
    public static final String CommandVpaidAdImp = "vpaidAdImpression";
    public static final String CommandVpaidAdPaused = "vpaidAdPaused";
    public static final String CommandVpaidAdPlaying = "vpaidAdPlaying";
    public static final String CommandVpaidAdVideoStart = "vpaidAdVideoStart";
    public static final String CommandVpaidAdVideoFirstQuartile = "vpaidAdVideoFirstQuartile";
    public static final String CommandVpaidAdVideoMidpoint = "vpaidAdVideoMidpoint";
    public static final String CommandVpaidAdVideoThirdQuartile = "vpaidAdVideoThirdQuartile";
    public static final String CommandVpaidAdVideoComplete = "vpaidAdVideoComplete";


    public static final String CommandArgMessage = "message";


    public static final String CommandArgUrl = "url";
    public static final String CommandArgEvent = "event";
    public static final String CommandArgSeconds = "seconds";

    public static final String PropertiesWidth = "width";
    public static final String PropertiesHeight = "height";

    public static final String ExpandPropertiesUseCustomClose = "useCustomClose";

    public static final String ResizePropertiesCustomClosePosition = "customClosePosition";
    public static final String ResizePropertiesOffsetX = "offsetX";
    public static final String ResizePropertiesOffsetY = "offsetY";
    public static final String ResizePropertiesAllowOffscreen = "allowOffscreen";

    public static final String ResizePropertiesCCPositionTopLeft = "top-left";
    public static final String ResizePropertiesCCPositionTopCenter = "top-center";
    public static final String ResizePropertiesCCPositionTopRight = "top-right";
    public static final String ResizePropertiesCCPositionCenter = "center";
    public static final String ResizePropertiesCCPositionBottomLeft = "bottom-left";
    public static final String ResizePropertiesCCPositionBottomCenter = "bottom-center";
    public static final String ResizePropertiesCCPositionBottomRight = "bottom-right";

    public static final String OrientationPpropertiesAllowOrientationChange = "allowOrientationChange";
    public static final String OrientationPpropertiesForceOrientation = "forceOrientation";
    public static final String OrientationPropertiesForceOrientationNone = "none";
    public static final String OrientationPropertiesForceOrientationPortrait = "portrait";
    public static final String OrientationPropertiesForceOrientationLandscape = "landscape";
}
