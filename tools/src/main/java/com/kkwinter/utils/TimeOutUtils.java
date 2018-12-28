package com.kkwinter.utils;

import android.os.Handler;
import android.os.Looper;

public class TimeOutUtils {

    private long mTime;
    private Handler timeOutHandler;
    private Runnable timeOutRunnable;
    private TimeOutListener mTimeOutListener;

    public TimeOutUtils(long mTime) {
        this.mTime = mTime;
        timeOutHandler = new Handler(Looper.getMainLooper());
    }

    public TimeOutUtils(TimeOutListener listener, long mTime) {
        this(mTime);
        setTimeOutListener(listener);
    }

    /**
     * 设置超时监听
     *
     * @param listener 监听回调
     * @return 结果
     */
    public TimeOutUtils setTimeOutListener(TimeOutListener listener) {
        mTimeOutListener = listener;
        timeOutRunnable = new Runnable() {
            @Override
            public void run() {
                finish(true);
            }
        };
        return this;
    }

    public void schedule() {
        timeOutHandler.postDelayed(timeOutRunnable, mTime);
    }

    public void cancel() {
        finish(false);
    }

    private synchronized void finish(boolean isTimeout) {
        timeOutHandler.removeCallbacks(timeOutRunnable);
        if (null == mTimeOutListener) return;
        if (isTimeout) {
            mTimeOutListener.onTimeout();
        } else {
            mTimeOutListener.onCancel();
        }
        mTimeOutListener = null;
    }

    public interface TimeOutListener {
        void onTimeout();

        void onCancel();
    }
}

