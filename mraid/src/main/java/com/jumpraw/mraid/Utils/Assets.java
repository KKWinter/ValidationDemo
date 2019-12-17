package com.jumpraw.mraid.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

public class Assets {


    /*
     * To add a new assets, convert the asset (js or jpg or png) to a base64 string, use an online tool to remove the
     * new line characters, then add the string here.
     */
    public static final String ib_apdate_regular = "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";
    public static final String ib_arrow_left_regular = "iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAAAXNSR0IArs4c6QAAA8lJREFUWAntVm9IU1EUv29vc5uay6GWgoUlSbmKyKyU1KK0TARHiBGUGvRHioQCpehDEKkFqVFGlIlBIPXBD0GQmRJWWlqYiiBRH0oMI0trOrf3Z53zcPNtb3+em+WXDvz27rv33Pv7vXvOPXeUzWYjC2mKhSRH7v8CCOaAH3lAwe4FHziSv6Lva8ejnuG27XJDaeezP/0JAZKHrt+0blPJqeNPdVpdzsSP3yq5Alz9lK4dPt4FcsOGxOSqmsv3IsOWxnAcSywWqz8fIlDNRQCShBWfLMw9VFhYExG2JJxlbETBBXaM5QpAcv3+wwXGo8eO12qVIRqG4QlNYf7wPjbN+7AcAQJ5flH+vlOlpdUqEqxhrPjVNkLB1/MBFjJfAnA88kLthdM703eeoIlGzbKc45Mo3q8T5JiPDW8CcCzmbNW5M3uzck6y04QwzCy5sArsPgu7QCkUSY+7Hwpdnn7U6iBep1/EwHi72Iey1wCKwgR3mEBedqm8zJhrLGEssOFuQk2DlyaEEJWGEOfpjnUcDYWSIio1IfHhSRIih9NMA9xIbH1zff3quMQ08yTnlhx9OWEHoJ6bKJ8CgkBkaLgTt0DnGgIkX3mz6dbt+NiElKkpxiO5MBsjgpsqw3i4drSctFyIBQjk1+/X3YmOWrZ19Ns4HDEZK8t0CbUpiVanlXiLBehgdN3wl7EItVLvz/0gWVzcYaPVZDFDi7uEtmRPKsvPd/T2vh+xMgyZT7AsS3hemsliARMgqQ/QfaOionVwcHAEJ80b4M5wJ0AcAjhs5KOwL5DgdRWV1qy8vM27svesnekL6MFxnE8BSGAXYYW26UlzMz7JjqxMtyJoGs52EE26u968/jT04Tv6erLF+rDP6ZnJrzIM2U4uXgsReG4BpO025m1LyUhfQ9NYemZNo1WRyKgQ0t7Sfq7h2t23syNuWz+g9yMUPnw6zJMAdECyGEASIDXBYNhYUFyUKhaxSKchy+P0xGT6mXdw79FW8PNmWDWsIMCpnouT0HUyCx0jgE7A06GBga6muw0vGYZhMZ4IG2S1AlaIiNLDTUFMPmCGcSdyePf5rxhFjAJ6AG0govNBY+OLaTC7iECvY287AJyC4eHFuL0DtA71Dzy/eflKi9lsNvFwQ9kvM8HTjx85AnBZuwhMtJZf4+Ndt69WP7NMW8cwDIGYXAHIgUxYrPoBbSCis/ZiVYPFyr6Ed7/N2ynwtKjwzxgGVwGiAZ8BHwCYZD7NNWT+CEASFIFXWzAAi9UkQJLh0CcxjwIknv+oYy458Fck/QETrugHu1/pxAAAAABJRU5ErkJggg==";
    public static final String ib_arrow_right_regular = "iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAAAXNSR0IArs4c6QAAA5pJREFUWAntV11IFFEUPnfnZ2d39s81H0qyIoTwQQoioiKrx6B6ip6MKEgIougpfA8SkoIiItLoxYikQEtQsTQwKYuSMkEtxN8wkdbV/XFmdjrHWp3dcZedccGXDhz2zj1n7/fdc849d4bpug7rKY71BCfs/wSAasBiHXAYOV9DW/3x/unupp7h1oCVNCbxkr92UiAi4NaOlu4yWfAdKwwGuqySMBK2Q4AiUDQyPF6mRDgQwFUeXAMJOwRoA3wsGg8sRhjEFxzAJ6TywmBBX+9oxy7j7nIZ2yWAdQMORUnAYgQgPu8ApoolBV7/a6skbBOg3WlaApRFHWILALEwAy3O+QMenyUSayOQ0EBDVVUNYhEdomEkFeP8Pren88PYq7O5pIA3OFFxiS29TysMc6bhzPSs9Lr1XZmicBtUTVux45AekQtIMufzu311SAJ2bz5Sv+JkHrFkD2CMBdG8/evY+/dmt5UZujqUGIYcw66pK/PJEcOYCk4AlweJeBjMRcPnjCSSeEl/YwSKcLIiNJNI2lb9JQKJhA4YedAzuKpITFOxJjQGskeu+zzZdWjnporTqy1oJOBCh+LIgrKan7W5f6lQkISqOsDtdVYiCViNhJHAEshsKGQNLIs3Y1hUcxz4AgL4g0Llp4lO8k6JhIlAKIylnEdheDznIzwomhvcPk5IX9pEYFHJQwoMKFjcwCs6jIyMd1WdPH8pWxEu/U2lCsqTMAcDl4uHUHim5/LpixdwWVN+TRHIFwEHhydAEuFL38eHt6/duoHg31Hj6XszEoiiceLZ44aX6U6pzzrDzucq3rJt4979+3ak2v4+cQjuliUYGuy/h+C1ODuGagInb1MjwjlqSNlEQmPpnoMHjp44depwuiPHO0CWhfj46I+6murrNWifRF3Oa7YaoPx8RaWWnE1kMvI8F9Wo7xpEEDkE5+NtzS+qm580NaIpBdzgujw0poBWozTkItiMma4Z7gIC93qFeGd7+xUEf46L/EJd3nmmRY0EMvlknE8SEEUeW64QetPx6mrjo0ba+SxqhkadutyaCFA+nRIPHq/4827NraqhgaG3VsCJim0C2F8SosiwzUpT92vvnEFwukXnUHPaOYGT2H0hUSWX87ck8/0Pbt6t/NY30INrURFbAicCxmNIz7kI3ZqlqCWoU6iDqPOoOX1kph9DOwTomNJRpO8DfCVdOjk5gaOv6SNomQAZ10Ps1kDeuP4Brz1n7KNndXgAAAAASUVORK5CYII=";
    public static final String ib_bg_down = "iVBORw0KGgoAAAANSUhEUgAAAUAAAAApCAMAAABzwgC+AAADAFBMVEWurq6rq6t8fHysrKytra2Xl5eUlJSRkZGPj4+SkpKMjIyLi4uJiYmIiIiGhoaFhYWEhISDg4OBgYF/f39+fn59fX2Ojo4yOESoqKipqamqqqoAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAICxOXAAAAlUlEQVRoBe3QtxHCAADAQNnknMFm/0EZQhV3Uq/mmUoJoO7mibmUQICKb575lhJgKCUQoOIbBhalBFiWEqASSCCBBBJIIIEEEkgggQT+TmBVSoB1KQF2pQTYlBJgW0qAdykB9qUEOJQS4FhKgFMpAc6lBLiUEuBaSoBbKQHupQR4lBLgWUqAVykBxlICASq+ceRTSuAHY4z30Mx20KkAAAAASUVORK5CYII=";
    public static final String ib_close_regular = "iVBORw0KGgoAAAANSUhEUgAAACIAAAAiCAYAAAA6RwvCAAAAAXNSR0IArs4c6QAABU5JREFUWAntlmtMXFUQgGcfd3fvvqAUyi4V2kgiVV7hIbDFDYHEklQTU400NVZjUo2/jAkoiqmm1lSIVlBsbelS0tJoXdOCojUFgwWMCGk0pZSmRGt9tdhEXov73r3OXLh6dzkLxD/tD04Y7rnnzJnz7cycOVchCALcDk15O0AQwypIdCRWPRLtEaBTI5coBQW+G7Y+Unn3pYnB0ZGJgeNR86xXLQ5av7nS7RyfHL4w+OPZeLmSfC95PwKCJmSNIEzZ+ZkVved7fr1685Lwy/SIsAwMQdzT1tnW/9PEmHBt6qIwPjkUASPfXN6PlSMEYczKyyxqeO/tE9aEtFTf3yrwe1Rg1pl3jtxgeoYg0lucRxwluTa73436uIYDPidhTXxftGdQN6IporwACmyoMQ/R/Fb7huSN1nAIh/BPqRaA04WA04dgzutqz7Han1iwJkIcdrY4Sgvus4WDqIzOVaoEUOvCon4APCNTUzNlJelbpiMIFl5YIAacy+zq/7wzbd2GeQhpJdpXEQwfBo0hDHO+ufZcq/1pnE4/9HGLY3OezRZagJCWEAzHC6gfgqDCN5KRUJwrzcmfrNCYUWGT41D7zwF/GEKh0H8SDIHfFwbvnABeF4Bebdh5ZqjDSRDFOUU2mguhjnwN2fC5wxhWgLGxq0flm8v7LI+sRYXNKFsqt20r2/Pq7mz5ArGPnlFzKNr55A76FBAM4ExErs+vokCTXueZzqa91XvfwFT4a5E9HFAzBmdw7AIKf7ajQ/TY7lfqFsGgo8Dvn18thJEhBgSH2ePsOHWwoa6+EbXJNrOxQIKoeR3lO1qBMPSAl2tfXAQjTsT4p8CfwGkVcOqzToJoQDWySbaZjQVCikyYF2qqVwRDEBpOAZ9+0bUiCNowFkhMmF3P7FoSRqlUgE6vgu4vv14xxHIgcpgf8CU5Mzf7gamZWRqP2TQaFR5VHsorSywNdUuHQ26EdWrk89SnYpXRcKSxNd6cVBjwY5Yu0TgEWZvIQ3KKEfRGTXtBarlU9MRV0QVUMrUciFgxm443O3htnM3jCYIQZhwPyRo+FRganldD4joDJFkMYDBqI2D+D4gI0Xis2aHljDa3O7AshMRDMHo9B0nJBrCkmIA36aoL7yh/h+ZjgcRKVixXkFa7r+41lVJnc7k8EF7GExKE+MTouVxBCAsh9BAmlwL2n/+td7owtSJmZWWB0FhK9Z6ampT1G6vmZr1MCJVKCZSY577qHfb7gry9ojziNFEmuWakSidAssXUijDEyYRhgdCHTP76tDufnJ11472BxqIaQWAiwvjly8e6Tn4yiNM5pGIrs0fA4LUDwakghMgzWOotaiWFhwkilnAyIms89i0tjR/0ez2+oHiJ0UW2IFTLDQhx/fdrBw+8+e7rqNuD0ocVeGCwb+CipCc9/b4AkFdnZ9ye/p6h7bJ9IrqsU0MeKUC5PyMrq+Thxx8rVSqVoufIE3HxPNz88w95sSKDKSglKGV4UdoLbcX/eobCF7dG7xvqH3juo9aTTkxW5vcIKzRUsejSU10ZHYXTJz6Eh3bsKOU4ldpo1kZDSHfHorspr+jebI1GDaY4ve/bc33PO9ucp9Fm7GpIx0kuqEyNQpaIsgWlflNOdnf94SZv7b6XDuB7GgrrB9AYzVWhvP/g9keH9x9t9lY9VfUsvpMtMQ3ke8n7rNDgGrHRwgQU+qK6C+UGyvcoS92iBENhykexooyjkHcnUcSsp81ZbSkQ0icYM4oJBb+xgOIrhQO7zEYwlGeU9PgdJ4ZDhMB+zIK2CISUb0UT43YrNo7ecxUk2iP/AASMggGF4ClIAAAAAElFTkSuQmCC";
    public static final String ib_window_regular = "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";

    public static final String close_button_normal = "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";
    public static final String close_button_pressed = "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";
    public static final String close_button_default = "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";

    public static Drawable getDrawableFromBase64(Resources resources, String encodedString) {
        byte[] decodedString = Base64.decode(encodedString, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return new BitmapDrawable(resources, bitmap);
    }

    private static final String mraidJs = "/* eslint-disable */\n" +
            "function getPlatform() {\n" +
            "    var ua = navigator.userAgent\n" +
            "    if (ua.indexOf('Android') > -1 || ua.indexOf('Adr') > -1) {\n" +
            "        return 'Android'\n" +
            "    } else if (ua.match(/\\(i[^;]+;( U;)? CPU.+Mac OS X/)) {\n" +
            "        return 'iOS'\n" +
            "    } else {\n" +
            "        return 'web'\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "var platform = getPlatform()\n" +
            "if (platform !== 'web') {\n" +
            "    console = {};\n" +
            "    console.debug = console.log;\n" +
            "    console.info = console.log;\n" +
            "    console.warn = console.log;\n" +
            "    console.error = console.log;\n" +
            "    console.log = function (log) {\n" +
            "        MASTMRAIDWebView.nativeInvoke(\"console://localhost?\" + log);\n" +
            "    };\n" +
            "}\n" +
            "\n" +
            "window.mraid_init = function () {\n" +
            "    console.log(\"mraid_init\");\n" +
            "\n" +
            "    var mraid = window.mraid = {};\n" +
            "\n" +
            "    // MAST SDK\n" +
            "    mraid.returnResult = function (call) {\n" +
            "        return call().toString();\n" +
            "    };\n" +
            "\n" +
            "    // MAST SDK\n" +
            "    mraid.returnInfo = function (call) {\n" +
            "        var info = '';\n" +
            "\n" +
            "        var result = call();\n" +
            "        for (property in result) {\n" +
            "            if (info) {\n" +
            "                info += '&';\n" +
            "            }\n" +
            "\n" +
            "            info += encodeURIComponent(property) + '=' + encodeURIComponent(result[property]);\n" +
            "        }\n" +
            "\n" +
            "        return info;\n" +
            "    };\n" +
            "\n" +
            "    // MAST SDK\n" +
            "    mraid.nativeInvoke = function (call) {\n" +
            "        if (platform !== 'web') {\n" +
            "            MASTMRAIDWebView.nativeInvoke(call);\n" +
            "        }\n" +
            "    };\n" +
            "    // MAST SDK\n" +
            "    mraid.vpaidInvoke = function (call) {\n" +
            "        if (platform !== 'web') {\n" +
            "            MASTMRAIDWebView.vpaidInvoke(call);\n" +
            "        }\n" +
            "    };\n" +
            "    /////////\n" +
            "    //\n" +
            "    // events\n" +
            "    //\n" +
            "\n" +
            "    var EVENTS = mraid.EVENTS = {\n" +
            "        READY: \"ready\",\n" +
            "        ERROR: \"error\",\n" +
            "        STATE_CHANGE: \"stateChange\",\n" +
            "        VIEWABLE_CHANGE: \"viewableChange\",\n" +
            "        SIZE_CHANGE: \"sizeChange\",\n" +
            "        EXPOSURE_CHANGE: \"exposureChange\",\n" +
            "        AUDIO_VOLUME_CHANGE: \"audioVolumeChange\"\n" +
            "    };\n" +
            "\n" +
            "    var listeners = {};\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.addEventListener = function (event, listener) {\n" +
            "        console.log(\"addEventListener\");\n" +
            "\n" +
            "        var handlers = listeners[event];\n" +
            "\n" +
            "        // Create the listeners for the event if not already created\n" +
            "        if (!handlers) {\n" +
            "            listeners[event] = [];\n" +
            "            handlers = listeners[event];\n" +
            "        }\n" +
            "\n" +
            "        // Don't add the same listener twice\n" +
            "        for (var i = 0; i < handlers.length; ++i) {\n" +
            "            if (listener === handlers[i]) {\n" +
            "                return;\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "        // Add the new listener\n" +
            "        handlers.push(listener);\n" +
            "    };\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.removeEventListener = function (event, listener) {\n" +
            "        console.log(\"removeEventListener\");\n" +
            "\n" +
            "        var handlers = listeners[event];\n" +
            "        if (handlers) {\n" +
            "            if (listener) {\n" +
            "                delete handlers[listener];\n" +
            "            } else {\n" +
            "                listeners[event] = null;\n" +
            "            }\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "    // MAST SDK\n" +
            "    mraid.fireChangeEvent = function (event, newValue) {\n" +
            "        console.log(\"fireChangeEvent handler:\" + event + \" with:\" + newValue);\n" +
            "\n" +
            "        var handlers = listeners[event];\n" +
            "        if (handlers) {\n" +
            "            for (var i = 0; i < handlers.length; ++i) {\n" +
            "                console.log(\"fireChangeEvent invoked handler\");\n" +
            "\n" +
            "                handlers[i](newValue);\n" +
            "            }\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "    // MAST SDK\n" +
            "    mraid.fireErrorEvent = function (message, action) {\n" +
            "        console.log(\"fireErrorEvent handler:\" + message + \" action:\" + action);\n" +
            "\n" +
            "        var handlers = listeners[EVENTS.ERROR];\n" +
            "        if (handlers) {\n" +
            "            for (var i = 0; i < handlers.length; ++i) {\n" +
            "                handlers[i](message, action);\n" +
            "            }\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "    // MAST SDK\n" +
            "    mraid.fireEvent = function (event) {\n" +
            "        console.log(\"fireEvent handler:\" + event);\n" +
            "\n" +
            "        var handlers = listeners[event];\n" +
            "        if (handlers) {\n" +
            "            for (var i = 0; i < handlers.length; ++i) {\n" +
            "                handlers[i]();\n" +
            "            }\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    /////////\n" +
            "    //\n" +
            "    // version\n" +
            "    //\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.getVersion = function () {\n" +
            "        console.log(\"getVersion\");\n" +
            "\n" +
            "        return (\"3.0\");\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    //////////\n" +
            "    //\n" +
            "    // supports\n" +
            "    //\n" +
            "\n" +
            "    var FEATURES = mraid.FEATURES = {\n" +
            "        SMS: \"sms\",\n" +
            "        TEL: \"tel\",\n" +
            "        CALENDAR: \"calendar\",\n" +
            "        STORE_PICTURE: \"storePicture\",\n" +
            "        INLINE_VIDEO: \"inlineVideo\",\n" +
            "        VPAID: \"vpaid\"\n" +
            "    };\n" +
            "\n" +
            "    var supportedFeatures = {};\n" +
            "\n" +
            "    // MAST SDK\n" +
            "    mraid.setSupports = function (feature, supported) {\n" +
            "        supportedFeatures[feature] = supported;\n" +
            "    };\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.supports = function (feature) {\n" +
            "        console.log(\"supports\");\n" +
            "\n" +
            "        return supportedFeatures[feature];\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    /////////\n" +
            "    //\n" +
            "    // state\n" +
            "    //\n" +
            "\n" +
            "    var STATES = mraid.STATES = {\n" +
            "        LOADING: \"loading\",\n" +
            "        DEFAULT: \"default\",\n" +
            "        EXPANDED: \"expanded\",\n" +
            "        RESIZED: \"resized\",\n" +
            "        HIDDEN: \"hidden\"\n" +
            "    };\n" +
            "\n" +
            "    var state = STATES.LOADING;\n" +
            "\n" +
            "    // MAST SDK\n" +
            "    mraid.setState = function (newState) {\n" +
            "        var diff = state != newState;\n" +
            "\n" +
            "        state = newState;\n" +
            "\n" +
            "        if (diff) {\n" +
            "            mraid.fireChangeEvent(EVENTS.STATE_CHANGE, state);\n" +
            "        } else if (state === STATES.RESIZED) {\n" +
            "            // spec says resized -> resized fires an event\n" +
            "            mraid.fireChangeEvent(EVENTS.STATE_CHANGE, state);\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.getState = function () {\n" +
            "        console.log(\"getState\");\n" +
            "\n" +
            "        return state;\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    /////////\n" +
            "    //\n" +
            "    // placementType\n" +
            "    //\n" +
            "\n" +
            "    var PLACEMENT_TYPES = mraid.PLACEMENT_TYPES = {\n" +
            "        INLINE: \"inline\",\n" +
            "        INTERSTITIAL: \"interstitial\"\n" +
            "    };\n" +
            "\n" +
            "    var placementType = PLACEMENT_TYPES.INLINE;\n" +
            "\n" +
            "    // MAST SDK\n" +
            "    mraid.setPlacementType = function (newPlacementType) {\n" +
            "        placementType = newPlacementType;\n" +
            "    };\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.getPlacementType = function () {\n" +
            "        console.log(\"getPlacementType\");\n" +
            "\n" +
            "        return placementType;\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    //////////\n" +
            "    //\n" +
            "    // isViewable\n" +
            "    //\n" +
            "\n" +
            "    var isViewable = false;\n" +
            "\n" +
            "    // MAST SDK\n" +
            "    mraid.setViewable = function (viewable) {\n" +
            "        var diff = isViewable != viewable;\n" +
            "\n" +
            "        isViewable = viewable;\n" +
            "\n" +
            "        if (diff) {\n" +
            "            mraid.fireChangeEvent(EVENTS.VIEWABLE_CHANGE, isViewable);\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.isViewable = function () {\n" +
            "        console.log(\"isViewable\");\n" +
            "\n" +
            "        return isViewable;\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    //////////\n" +
            "    //\n" +
            "    // open\n" +
            "    //\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.open = function (url) {\n" +
            "        console.log(\"open\");\n" +
            "\n" +
            "        var invoke = \"mraid://open?url=\" + encodeURIComponent(url);\n" +
            "        mraid.nativeInvoke(invoke);\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    //////////\n" +
            "    //\n" +
            "    // close\n" +
            "    //\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.close = function () {\n" +
            "        console.log(\"close\");\n" +
            "\n" +
            "        var invoke = \"mraid://close\";\n" +
            "        mraid.nativeInvoke(invoke);\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    //////////\n" +
            "    //\n" +
            "    // video\n" +
            "    //\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.playVideo = function (url) {\n" +
            "        console.log(\"playVideo\");\n" +
            "\n" +
            "        var invoke = \"mraid://playVideo?url=\" + encodeURIComponent(url);\n" +
            "        mraid.nativeInvoke(invoke);\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    //////////\n" +
            "    //\n" +
            "    // expand\n" +
            "    //\n" +
            "\n" +
            "    var expandProperties = {\n" +
            "        width: 0,\n" +
            "        height: 0,\n" +
            "        useCustomClose: false,\n" +
            "        isModal: true\n" +
            "    };\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.setExpandProperties = function (properties) {\n" +
            "        console.log(\"setExpandProperties\");\n" +
            "\n" +
            "        var writableFields = [\"width\", \"height\", \"useCustomClose\"];\n" +
            "\n" +
            "        for (wf in writableFields) {\n" +
            "            var field = writableFields[wf];\n" +
            "            if (properties[field] !== undefined) {\n" +
            "                expandProperties[field] = properties[field];\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "        var invoke = \"mraid://setExpandProperties?\" + mraid.returnInfo(mraid.getExpandProperties);\n" +
            "        mraid.nativeInvoke(invoke);\n" +
            "    };\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.getExpandProperties = function () {\n" +
            "        console.log(\"getExpandProperties\");\n" +
            "\n" +
            "        return expandProperties;\n" +
            "    };\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.useCustomClose = function (useCustomClose) {\n" +
            "        console.log(\"useCustomClose\");\n" +
            "\n" +
            "        var property = {\n" +
            "            useCustomClose: useCustomClose\n" +
            "        };\n" +
            "\n" +
            "        mraid.setExpandProperties(property);\n" +
            "    };\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.expand = function (url) {\n" +
            "        console.log(\"expand\");\n" +
            "\n" +
            "        var invoke = \"mraid://expand\";\n" +
            "\n" +
            "        if (url) {\n" +
            "            invoke += \"?url=\" + encodeURIComponent(url);\n" +
            "        }\n" +
            "\n" +
            "        mraid.nativeInvoke(invoke);\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    //////////\n" +
            "    //\n" +
            "    // resize\n" +
            "    //\n" +
            "\n" +
            "    var RESIZE_PROPERTIES_CUSTOM_CLOSE_POSITION = mraid.RESIZE_PROPERTIES_CUSTOM_CLOSE_POSITION = {\n" +
            "        TOP_LEFT: \"top-left\",\n" +
            "        TOP_RIGHT: \"top-right\",\n" +
            "        CENTER: \"center\",\n" +
            "        BOTTOM_LEFT: \"bottom-left\",\n" +
            "        BOTTOM_RIGHT: \"bottom-right\"\n" +
            "    };\n" +
            "\n" +
            "    var resizeProperties = {\n" +
            "        width: 0,\n" +
            "        height: 0,\n" +
            "        customClosePosition: RESIZE_PROPERTIES_CUSTOM_CLOSE_POSITION.TOP_RIGHT,\n" +
            "        offsetX: 0,\n" +
            "        offsetY: 0,\n" +
            "        allowOffscreen: false\n" +
            "    };\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.setResizeProperties = function (properties) {\n" +
            "        console.log(\"setResizeProperties\");\n" +
            "\n" +
            "        var writableFields = [\"width\", \"height\", \"customClosePosition\", \"offsetX\", \"offsetY\", \"allowOffscreen\"];\n" +
            "\n" +
            "        for (wf in writableFields) {\n" +
            "            var field = writableFields[wf];\n" +
            "            if (properties[field] !== undefined) {\n" +
            "                resizeProperties[field] = properties[field];\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "        var invoke = \"mraid://setResizeProperties?\" + mraid.returnInfo(mraid.getResizeProperties);\n" +
            "        mraid.nativeInvoke(invoke);\n" +
            "    };\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.getResizeProperties = function () {\n" +
            "        console.log(\"getResizeProperties\");\n" +
            "\n" +
            "        return resizeProperties;\n" +
            "    };\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.resize = function () {\n" +
            "        console.log(\"resize\");\n" +
            "\n" +
            "        var invoke = \"mraid://resize\";\n" +
            "        mraid.nativeInvoke(invoke);\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    //////////\n" +
            "    //\n" +
            "    // orientation\n" +
            "    //\n" +
            "\n" +
            "    var ORIENTATION_PROPERTIES_FORCE_ORIENTATION = mraid.ORIENTATION_PROPERTIES_FORCE_ORIENTATION = {\n" +
            "        PORTRAIT: \"portrait\",\n" +
            "        LANDSCAPE: \"landscape\",\n" +
            "        NONE: \"none\"\n" +
            "    };\n" +
            "\n" +
            "    var orientationProperties = {\n" +
            "        allowOrientationChange: true,\n" +
            "        forceOrientation: ORIENTATION_PROPERTIES_FORCE_ORIENTATION.NONE\n" +
            "    };\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.setOrientationProperties = function (properties) {\n" +
            "        console.log(\"setOrientationProperties\");\n" +
            "\n" +
            "        var writableFields = [\"allowOrientationChange\", \"forceOrientation\"];\n" +
            "\n" +
            "        for (wf in writableFields) {\n" +
            "            var field = writableFields[wf];\n" +
            "            if (properties[field] !== undefined) {\n" +
            "                orientationProperties[field] = properties[field];\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "        var invoke = \"mraid://setOrientationProperties?\" + mraid.returnInfo(mraid.getOrientationProperties);\n" +
            "        mraid.nativeInvoke(invoke);\n" +
            "    };\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.getOrientationProperties = function () {\n" +
            "        console.log(\"getOrientationProperties\");\n" +
            "\n" +
            "        return orientationProperties;\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    //////////\n" +
            "    //\n" +
            "    // position and size\n" +
            "    //\n" +
            "\n" +
            "    var currentPosition = {\n" +
            "        x: 0,\n" +
            "        y: 0,\n" +
            "        width: 0,\n" +
            "        height: 0\n" +
            "    };\n" +
            "\n" +
            "    var maxSize = {\n" +
            "        width: 0,\n" +
            "        height: 0\n" +
            "    };\n" +
            "\n" +
            "    var defaultPosition = {\n" +
            "        x: 0,\n" +
            "        y: 0,\n" +
            "        width: 0,\n" +
            "        height: 0\n" +
            "    };\n" +
            "\n" +
            "    var screenSize = {\n" +
            "        width: 0,\n" +
            "        height: 0\n" +
            "    };\n" +
            "\n" +
            "    // MAST SDK\n" +
            "    mraid.setCurrentPosition = function (position) {\n" +
            "        var previousSize = mraid.getSize();\n" +
            "\n" +
            "        currentPosition = position;\n" +
            "\n" +
            "        var currentSize = mraid.getSize();\n" +
            "\n" +
            "        // Only send the size changed event if the size in the position\n" +
            "        // was different from the previous position\n" +
            "        if ((previousSize.width === currentSize.width) && (previousSize.height === currentSize.height)) {\n" +
            "            return;\n" +
            "        }\n" +
            "\n" +
            "        var handlers = listeners[EVENTS.SIZE_CHANGE];\n" +
            "        if (handlers) {\n" +
            "            var width = currentPosition.width;\n" +
            "            var height = currentPosition.height;\n" +
            "\n" +
            "            for (var i = 0; i < handlers.length; ++i) {\n" +
            "                handlers[i](width, height);\n" +
            "            }\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.getCurrentPosition = function () {\n" +
            "        console.log(\"getCurrentPosition\");\n" +
            "\n" +
            "        var invoke = \"mraid://updateCurrentPosition\";\n" +
            "        mraid.nativeInvoke(invoke);\n" +
            "\n" +
            "        return currentPosition;\n" +
            "    };\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.getSize = function () {\n" +
            "        console.log(\"getSize\");\n" +
            "\n" +
            "        var size = {\n" +
            "            width: currentPosition.width,\n" +
            "            height: currentPosition.height\n" +
            "        };\n" +
            "\n" +
            "        return size;\n" +
            "    };\n" +
            "\n" +
            "    // MAST SDK\n" +
            "    mraid.setMaxSize = function (size) {\n" +
            "        maxSize = size;\n" +
            "    };\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.getMaxSize = function () {\n" +
            "        console.log(\"getMaxSize\");\n" +
            "\n" +
            "        return maxSize;\n" +
            "    };\n" +
            "\n" +
            "    // MAST SDK\n" +
            "    mraid.setDefaultPosition = function (position) {\n" +
            "        defaultPosition = position;\n" +
            "    };\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.getDefaultPosition = function () {\n" +
            "        console.log(\"getDefaultPosition\");\n" +
            "\n" +
            "        return defaultPosition;\n" +
            "    };\n" +
            "\n" +
            "    // MAST SDK\n" +
            "    mraid.setScreenSize = function (size) {\n" +
            "        screenSize = size;\n" +
            "    };\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.getScreenSize = function () {\n" +
            "        console.log(\"getScreenSize\");\n" +
            "\n" +
            "        return screenSize;\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    //////////\n" +
            "    //\n" +
            "    // picture\n" +
            "    //\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.storePicture = function (url) {\n" +
            "        console.log(\"storePicture\");\n" +
            "\n" +
            "        var invoke = \"mraid://storePicture?url=\" + encodeURIComponent(url);\n" +
            "        mraid.nativeInvoke(invoke);\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    //////////\n" +
            "    //\n" +
            "    // calendar\n" +
            "    //\n" +
            "\n" +
            "    // MRAID\n" +
            "    mraid.createCalendarEvent = function (parameters) {\n" +
            "        console.log(\"createCalendarEvent\");\n" +
            "\n" +
            "        var invoke = \"mraid://createCalendarEvent?event=\" + encodeURIComponent(JSON.stringify(parameters));\n" +
            "        mraid.nativeInvoke(invoke);\n" +
            "    };\n" +
            "\n" +
            "    //////////\n" +
            "    //\n" +
            "    // VPAID\n" +
            "    //\n" +
            "    mraid._vpaidObjcet = {};\n" +
            "\n" +
            "    mraid.startAd = function () {\n" +
            "        this._vpaidObjcet.startAd();\n" +
            "    };\n" +
            "\n" +
            "    mraid.subscribe = function (eventName) {\n" +
            "        var mraidCallbacks = {\n" +
            "            'AdClickThru': this.onAdClickThru,\n" +
            "            'AdError': this.onAdError,\n" +
            "            'AdImpression': this.onAdImpression,\n" +
            "            'AdPaused': this.onAdPaused,\n" +
            "            'AdPlaying': this.onAdPlaying,\n" +
            "            'AdVideoStart': this.onAdVideoStart,\n" +
            "            'AdVideoFirstQuartile': this.onAdVideoFirstQuartile,\n" +
            "            'AdVideoMidpoint': this.onAdVideoMidpoint,\n" +
            "            'AdVideoThirdQuartile': this.onAdVideoThirdQuartile,\n" +
            "            'AdVideoComplete': this.onAdVideoComplete,\n" +
            "        };\n" +
            "        if (eventName in mraidCallbacks) {\n" +
            "            this._vpaidObjcet.subscribe(mraidCallbacks[eventName], eventName, this);\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "    mraid.unsubscribe = function (eventName) {\n" +
            "        if (eventName in mraidCallbacks) {\n" +
            "            this._vpaidObjcet.unsubscribe(mraidCallbacks[eventName], eventName, this);\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "    mraid.getAdDuration = function () {\n" +
            "        return this._vpaidObject.getAdDuration();\n" +
            "    };\n" +
            "\n" +
            "    mraid.getAdRemainingTime = function () {\n" +
            "        return this._vpaidObject.getAdRemainingTime();\n" +
            "    };\n" +
            "\n" +
            "    //mraid VPAID\n" +
            "    mraid.initVpaid = function (vpaidObject) {\n" +
            "        this._vpaidObjcet = vpaidObject;\n" +
            "        //通知sdk vpaid 初始化完成\n" +
            "        var invoke = \"vpaid://initVpaid\";\n" +
            "        mraid.vpaidInvoke(invoke);\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.onAdClickThru = function (url, id, playerHandles) {\n" +
            "        var adjustedUrl = url;\n" +
            "        if (adjustedUrl === undefined)\n" +
            "            adjustedUrl = \"\";\n" +
            "        var invoke = \"vpaid://vpaidAdClickThruIdPlayerHandles?url=\" + encodeURIComponent(adjustedUrl)\n" +
            "            + \"&id=\" + encodeURIComponent(id) + \"&playerHandles=\" + encodeURIComponent(playerHandles);\n" +
            "        mraid.vpaidInvoke(invoke);\n" +
            "        // CTMRAIDVpaidClient.vpaidAdClickThruIdPlayerHandles(adjustedUrl, id, playerHandles);\n" +
            "    };\n" +
            "\n" +
            "    mraid.onAdError = function (message) {\n" +
            "        var invoke = \"vpaid://vpaidAdError?message=\" + encodeURIComponent(message);\n" +
            "        mraid.vpaidInvoke(invoke);\n" +
            "        // CTMRAIDVpaidClient.vpaidAdError(message);\n" +
            "    };\n" +
            "\n" +
            "    mraid.onAdImpression = function () {\n" +
            "        var invoke = \"vpaid://vpaidAdImpression\";\n" +
            "        mraid.vpaidInvoke(invoke);\n" +
            "        // CTMRAIDVpaidClient.vpaidAdImpression();\n" +
            "    };\n" +
            "\n" +
            "    mraid.onAdPaused = function () {\n" +
            "        var invoke = \"vpaid://vpaidAdPaused\";\n" +
            "        mraid.vpaidInvoke(invoke);\n" +
            "        // CTMRAIDVpaidClient.vpaidAdPaused();\n" +
            "    };\n" +
            "\n" +
            "    mraid.onAdPlaying = function () {\n" +
            "        var invoke = \"vpaid://vpaidAdPlaying\";\n" +
            "        mraid.vpaidInvoke(invoke);\n" +
            "        // CTMRAIDVpaidClient.vpaidAdPlaying();\n" +
            "    };\n" +
            "\n" +
            "    mraid.onAdVideoStart = function () {\n" +
            "        var invoke = \"vpaid://vpaidAdVideoStart\";\n" +
            "        mraid.vpaidInvoke(invoke);\n" +
            "        // CTMRAIDVpaidClient.vpaidAdVideoStart();\n" +
            "    };\n" +
            "\n" +
            "    mraid.onAdVideoFirstQuartile = function () {\n" +
            "        var invoke = \"vpaid://vpaidAdVideoFirstQuartile\";\n" +
            "        mraid.vpaidInvoke(invoke);\n" +
            "        // CTMRAIDVpaidClient.vpaidAdVideoFirstQuartile();\n" +
            "    };\n" +
            "\n" +
            "    mraid.onAdVideoMidpoint = function () {\n" +
            "        var invoke = \"vpaid://vpaidAdVideoMidpoint\";\n" +
            "        mraid.vpaidInvoke(invoke);\n" +
            "        // CTMRAIDVpaidClient.vpaidAdVideoMidpoint();\n" +
            "    };\n" +
            "\n" +
            "    mraid.onAdVideoThirdQuartile = function () {\n" +
            "        var invoke = \"vpaid://vpaidAdVideoThirdQuartile\";\n" +
            "        mraid.vpaidInvoke(invoke);\n" +
            "        // CTMRAIDVpaidClient.vpaidAdVideoThirdQuartile();\n" +
            "    };\n" +
            "\n" +
            "    mraid.onAdVideoComplete = function () {\n" +
            "        var invoke = \"vpaid://vpaidAdVideoComplete\";\n" +
            "        mraid.vpaidInvoke(invoke);\n" +
            "        // CTMRAIDVpaidClient.vpaidAdVideoComplete();\n" +
            "    };\n" +
            "    //////////\n" +
            "    //\n" +
            "    // MRAID_ENV\n" +
            "    //\n" +
            "    var mraidEnv = {\n" +
            "        version: \"\",\n" +
            "        sdk: \"\",\n" +
            "        sdkVersion: \"\",\n" +
            "        appId: \"\",\n" +
            "        ifa: \"\",\n" +
            "        limitAdTracking: false,\n" +
            "        coppa: false\n" +
            "    };\n" +
            "	 window.mraidEnv = mraidEnv;\n" +
            "    mraid.setEnv = function (properties) {\n" +
            "        var writableFields = [\"version\", \"sdk\", \"sdkVersion\", \"appId\", \"ifa\", \"limitAdTracking\", \"coppa\"];\n" +
            "\n" +
            "        for (wf in writableFields) {\n" +
            "            var field = writableFields[wf];\n" +
            "            if (properties[field] !== undefined) {\n" +
            "                mraidEnv[field] = properties[field];\n" +
            "            }\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "    // Exposure Change Event\n" +
            "    var currentExposure = {\n" +
            "        exposedPercentage: 0,\n" +
            "        viewport: {\n" +
            "            width: 0,\n" +
            "            height: 0\n" +
            "        },\n" +
            "        visibleRectangle: {\n" +
            "            x: 0,\n" +
            "            y: 0,\n" +
            "            width: 0,\n" +
            "            height: 0,\n" +
            "            occlusionRectangle: {\n" +
            "                x: 0,\n" +
            "                y: 0,\n" +
            "                width: 0,\n" +
            "                height: 0\n" +
            "            }\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "    mraid.setExposureChange = function (exposure) {\n" +
            "        var handlers = listeners[EVENTS.EXPOSURE_CHANGE];\n" +
            "        currentExposure = exposure;\n" +
            "        if (handlers) {\n" +
            "            var exposedPercentage = currentExposure.exposedPercentage;\n" +
            "            var visibleRectangle = currentExposure.visibleRectangle;\n" +
            "            var occlusionRectangle = currentExposure.visibleRectangle.occlusionRectangle;\n" +
            "\n" +
            "            for (var i = 0; i < handlers.length; ++i) {\n" +
            "                handlers[i](exposedPercentage, visibleRectangle, occlusionRectangle);\n" +
            "            }\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "    //Audio Volume Change Event\n" +
            "    mraid.setAudioVolumeChange = function (audioVolume) {\n" +
            "        var handlers = listeners[EVENTS.AUDIO_VOLUME_CHANGE];\n" +
            "        if (handlers) {\n" +
            "            for (var i = 0; i < handlers.length; ++i) {\n" +
            "                handlers[i](audioVolume);\n" +
            "            }\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "    //App Orientation\n" +
            "    var APP_ORIENTATION_PROPERTIES = mraid.APP_ORIENTATION_PROPERTIES = {\n" +
            "        PORTRAIT: \"portrait\",\n" +
            "        LANDSCAPE: \"landscape\"\n" +
            "    };\n" +
            "\n" +
            "    var currentOrientation = {\n" +
            "        orientation: APP_ORIENTATION_PROPERTIES.PORTRAIT,\n" +
            "        locked: false\n" +
            "    };\n" +
            "\n" +
            "    mraid.setCurrentAppOrientation = function (properties) {\n" +
            "        var writableFields = [\"orientation\", \"locked\"];\n" +
            "        for (wf in writableFields) {\n" +
            "            var field = writableFields[wf];\n" +
            "            if (properties[field] !== undefined) {\n" +
            "                currentOrientation[field] = properties[field];\n" +
            "            }\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "    mraid.getCurrentAppOrientation = function () {\n" +
            "        return currentOrientation;\n" +
            "    };\n" +
            "\n" +
            "    //location\n" +
            "    var currentLocation = {\n" +
            "        lat: 0,\n" +
            "        lon: 0,\n" +
            "        type: 0,\n" +
            "        accuracy: \"\",\n" +
            "        lastfix: 0,\n" +
            "        ipservice: \"\"\n" +
            "    };\n" +
            "\n" +
            "    mraid.setLocation = function (properties) {\n" +
            "        var writableFields = [\"lat\", \"lon\", \"type\", \"accuracy\", \"lastfix\", \"ipservice\"];\n" +
            "        for (wf in writableFields) {\n" +
            "            var field = writableFields[wf];\n" +
            "            if (properties[field] !== undefined) {\n" +
            "                currentLocation[field] = properties[field];\n" +
            "            }\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "    mraid.getLocation = function () {\n" +
            "        if (currentLocation.lat !== 0 && currentLocation.lon !== 0)\n" +
            "            return currentLocation;\n" +
            "        else\n" +
            "            return -1;\n" +
            "    };\n" +
            "    mraid.setCloseCounter = function (parameters) {\n" +
            "	     console.log(\"setCloseCounter\");\n" +
            "	     var invoke = \"mraid://setCloseCounter?seconds=\" + encodeURIComponent(JSON.stringify(parameters));\n" +
            "	     mraid.nativeInvoke(invoke);\n" +
            "    };\n" +
            "\n" +
            "    //unload\n" +
            "    mraid.unload = function () {\n" +
            "        mraid.nativeInvoke(\"mraid://unload\");\n" +
            "    };\n" +
            "\n" +
            "    mraid.nativeInvoke(\"mraid://initialize\");\n" +
            "};\n" +
            "\n" +
            "if (!window.mraid) {\n" +
            "    window.mraid_init();\n" +
            "}".replaceAll("(?m)^\\s+", "")
                    .replaceAll("(?m)^//.*(?=\\n)", "");

    public static String getMraidJs(Context context) {
        return mraidJs;
    }
}
