package com.jumpraw.circule;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PollingStateMachine implements INetCallback {

    private static volatile PollingStateMachine instance = null;
    private ScheduledExecutorService pool;
    public static final int TYPE_MATCH = 1;

    private Map matchMap = new HashMap<>();
    private List<WeakReference<View>> list = new ArrayList<>();
    private Handler handler;

    // private constructor suppresses
    private PollingStateMachine() {
        defineHandler();
        pool = Executors.newSingleThreadScheduledExecutor();
        pool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                doTasks();
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    private void doTasks() {

        // TODO: 2019-10-22
        Log.i("test", "doTasks: >>>>>>");
//        ThreadPoolUtils.execute(new PollRunnable(this));
    }

    public static PollingStateMachine getInstance() {
        // if already inited, no need to get lock everytime
        if (instance == null) {
            synchronized (PollingStateMachine.class) {
                if (instance == null) {
                    instance = new PollingStateMachine();
                }
            }
        }
        return instance;
    }
    public <VIEW extends View> void subscibeMatch(VIEW view, OnViewRefreshStatus onViewRefreshStatus) {
        subscibe(TYPE_MATCH,view,onViewRefreshStatus);
    }

    private <VIEW extends View> void subscibe(int type, VIEW view, OnViewRefreshStatus onViewRefreshStatus) {
        view.setTag(onViewRefreshStatus);
        if (type == TYPE_MATCH) {
            onViewRefreshStatus.update(view, matchMap);
        }
        for (WeakReference<View> viewSoftReference : list) {
            View textView = viewSoftReference.get();
            if (textView == view) {
                return;
            }
        }
        WeakReference<View> viewSoftReference = new WeakReference<View>(view);
        list.add(viewSoftReference);
    }

    public void updateView(final int type) {
        Iterator<WeakReference<View>> iterator = list.iterator();
        while (iterator.hasNext()) {
            WeakReference<View> next = iterator.next();
            final View view = next.get();
            if (view == null) {
                iterator.remove();
                continue;
            }
            Object tag = view.getTag();
            if (tag == null || !(tag instanceof OnViewRefreshStatus)) {
                continue;
            }
            final OnViewRefreshStatus onViewRefreshStatus = (OnViewRefreshStatus) tag;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (type == TYPE_MATCH) {
                        onViewRefreshStatus.update(view, matchMap);
                    }
                }
            });

        }
    }

    public void clear() {
        pool.shutdown();
        instance = null;
    }

    private Handler defineHandler() {
        if (handler == null && Looper.myLooper() == Looper.getMainLooper()) {
            handler = new Handler();
        }
        return handler;
    }


    @Override
    public void onNetCallback(int type, Map msg) {
        if (type == TYPE_MATCH) {
            matchMap=msg;
        }
        updateView(type);
    }
}


