package com.hldemo.demo.mopub;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class HLViewBinder {

        public final static class Builder {
            private final int layoutId;
            private int titleId;
            private int textId;
            private int callToActionId;
            private int mainImageId;
            private int iconImageId;
            private int privacyInformationIconImageId;
            @NonNull
            private Map<String, Integer> extras = Collections.emptyMap();

            public Builder(final int layoutId) {
                this.layoutId = layoutId;
                this.extras = new HashMap<String, Integer>();
            }

            @NonNull
            public final HLViewBinder.Builder titleId(final int titleId) {
                this.titleId = titleId;
                return this;
            }

            @NonNull
            public final HLViewBinder.Builder textId(final int textId) {
                this.textId = textId;
                return this;
            }

            @NonNull
            public final HLViewBinder.Builder callToActionId(final int callToActionId) {
                this.callToActionId = callToActionId;
                return this;
            }

            @NonNull
            public final HLViewBinder.Builder mainImageId(final int mediaLayoutId) {
                this.mainImageId = mediaLayoutId;
                return this;
            }

            @NonNull
            public final HLViewBinder.Builder iconImageId(final int iconImageId) {
                this.iconImageId = iconImageId;
                return this;
            }

            @NonNull
            public final HLViewBinder.Builder privacyInformationIconImageId(final int privacyInformationIconImageId) {
                this.privacyInformationIconImageId = privacyInformationIconImageId;
                return this;
            }

            @NonNull
            public final HLViewBinder.Builder addExtras(final Map<String, Integer> resourceIds) {
                this.extras = new HashMap<String, Integer>(resourceIds);
                return this;
            }

            @NonNull
            public final HLViewBinder.Builder addExtra(final String key, final int resourceId) {
                this.extras.put(key, resourceId);
                return this;
            }

            @NonNull
            public final HLViewBinder build() {
                return new HLViewBinder(this);
            }
        }

        final int layoutId;
        final int titleId;
        final int textId;
        final int callToActionId;
        final int mainImageId;
        final int iconImageId;
        final int privacyInformationIconImageId;
        @NonNull
        final Map<String, Integer> extras;

        private HLViewBinder(@NonNull final HLViewBinder.Builder builder) {
            this.layoutId = builder.layoutId;
            this.titleId = builder.titleId;
            this.textId = builder.textId;
            this.callToActionId = builder.callToActionId;
            this.mainImageId = builder.mainImageId;
            this.iconImageId = builder.iconImageId;
            this.privacyInformationIconImageId = builder.privacyInformationIconImageId;
            this.extras = builder.extras;
        }
    }


