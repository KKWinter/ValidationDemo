package com.jumpraw.circule;

import android.view.View;

import java.util.Map;

public abstract class OnViewRefreshStatus<VALUE, VIEW extends View> {
    private static final String TAG = OnViewRefreshStatus.class.getSimpleName();
    private long key;

    public OnViewRefreshStatus(long key) {
        this.key = key;
    }

    public long getKey() {
        return key;
    }

    public void update(final VIEW view, Map<Long, VALUE> map) {
        final VALUE value = map.get(key);

        if (value == null) {
            return;
        }
        OnViewRefreshStatus(view, value);

    }


    public abstract void OnViewRefreshStatus(VIEW view, VALUE value);

}


