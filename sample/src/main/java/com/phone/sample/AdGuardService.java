package com.phone.sample;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.lang.reflect.Field;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class AdGuardService extends JobService {

    public static final String TAG = "AdGuard";
    private boolean mIsHook;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(AdGuardService.TAG, "AdGuardService::onStartCommand");
        scheduleJob();
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        params.getJobId();
        initCTService(this);

        //Attempt to invoke virtual method 'int com.android.server.job.controllers.JobStatus.getUid()' on a null object reference
        hookJobHandler(this);
        return false;
    }

    private static boolean isAlreadyScheduled = false;

    private void scheduleJob() {
        Log.d(AdGuardService.TAG, "AdGuardService::scheduleJob::initFinished == " + isAlreadyScheduled);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                JobScheduler jobScheduler = (JobScheduler) getSystemService(
                        JOB_SCHEDULER_SERVICE);
                int jobId = 0x4;
                JobInfo.Builder builder = new JobInfo.Builder(++jobId,
                        new ComponentName(this.getApplicationContext(), AdGuardService.class));
                builder.setPeriodic(20000);
                jobScheduler.schedule(builder.build());
                isAlreadyScheduled = true;
                Log.d(AdGuardService.TAG, "AdGuardService::scheduleJob::finished");
            } catch (Throwable ignore) {
            }
        }
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    private static boolean initFinished = false;

    private synchronized static void initCTService(Context context) {
        if (!initFinished) {
            Log.d(AdGuardService.TAG, "AdGuardService::onStartJobinitCTService::really");
            initFinished = true;
        }

    }


    private void hookJobHandler(Object jobServiceSubObj) {
        if (mIsHook) {
            return;
        }
        mIsHook = true;

        try {
            Field handlerField = jobServiceSubObj.getClass().getSuperclass().getDeclaredField("mHandler");
            handlerField.setAccessible(true);
            Handler handler = (Handler) handlerField.get(jobServiceSubObj);

            Field callbackField = handler.getClass().getSuperclass().getDeclaredField("mCallback");
            callbackField.setAccessible(true);
            Handler.Callback callback = (Handler.Callback) callbackField.get(handler);

            callbackField.set(handler, new CallbackWrapper(handler, callback));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static class CallbackWrapper implements Handler.Callback {
        private Handler mHandler;
        private Handler.Callback mCallback;

        CallbackWrapper(Handler handler, Handler.Callback callback) {
            mHandler = handler;
            mCallback = callback;
        }

        @Override
        public boolean handleMessage(Message msg) {
            if (mCallback != null && mCallback.handleMessage(msg)) {
                return true;
            }
            try {
                Log.d(AdGuardService.TAG, "CallbackWrapper::handleMessage");
                mHandler.handleMessage(msg);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return true;
        }
    }


}