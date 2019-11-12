package com.hldemo.demo;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hldemo.R;
import com.pub18828.core.api.AdError;
import com.hldemo.demo.extra.AdmobAdManager;
import com.hldemo.demo.extra.MopubAdManager;
import com.pub18828.video.api.VideoAd;
import com.pub18828.video.api.VideoAdListener;
import com.pub18828.video.api.VideoAdResult;
import com.pub18828.video.api.VideoConfig;


public class VideoDemoActivity extends Activity {
    VideoAd videoAd;
    private RadioGroup mStyleGroup,mModeGroup;

    final String DESC = "1. Video Ads( Support Full Screen Video and Rewarded Video)<br>" +
            "2. Rewarded video supports  SDK callback and S2S callback. if your want to use the S2S callback, configure the callback URL in our website and set up the User-Id by SDK Api.<br>" +
            "3. Please \"<b><tt>Load</b></tt>\" Video Ads before \"<b><tt>SHOW</b></tt>\". It takes a few seconds for the video ad to load (depending on the customer's network status). We recommend loading a video ad after the SDK is initialized, and load the next video ad after the video has finished playing.<br>" +
            "4.When you need to play a video ad, you can check the status by \"<b><tt>CHECK STATUS</b></tt>\"";
    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
        }

        ;
    };

    int mType,model;
    VideoConfig mConfig;
    MopubAdManager mopubAdManager;
    AdmobAdManager admobAdManager;

    final int MP_TYPE = 0;
    final int MOPUB_TYPE = 1;
    final int ADMOB_TYPE = 2;

    int platformType = MP_TYPE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        TextView descTextView = findViewById(R.id.video_desc);
        descTextView.setText(Html.fromHtml(DESC));

        mStyleGroup = (RadioGroup) findViewById(R.id.video_style);
        mStyleGroup.check(R.id.auto_select_btn);
        mType = Configuration.ORIENTATION_UNDEFINED;

        mStyleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.landscape_select_btn:
                        mType = Configuration.ORIENTATION_LANDSCAPE;
                        videoAd.getConfig().setmOrientation(mType);
                        break;
                    case R.id.portrait_select_btn:
                        mType = Configuration.ORIENTATION_PORTRAIT;
                        videoAd.getConfig().setmOrientation(mType);
                        break;
                    case R.id.auto_select_btn:
                        mType = Configuration.ORIENTATION_UNDEFINED;
                        videoAd.getConfig().setmOrientation(mType);
                        break;
                }
            }
        });

        mModeGroup =  (RadioGroup) findViewById(R.id.video_mode);
        mModeGroup.check(R.id.full_select_btn);
        model = 0;

        mModeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.full_select_btn:
                        model = VideoConfig.FULL_VIDEO_MODE;
                        videoAd.getConfig().setShowMode(model);
                        break;
                    case R.id.dialog_select_btn:
                        model = VideoConfig.DIALOG_VIDEO_MODE;
                        videoAd.getConfig().setShowMode(model);
                        break;

                }
            }
        });

        mopubAdManager = new MopubAdManager();
        mopubAdManager.initMopubVideo(this);

        admobAdManager = new AdmobAdManager();
        admobAdManager.initAdmobVideo(this);

        videoAd = new VideoAd(this, DemoApplication.VIDEO);
//        videoAd.loadAd();
        VideoConfig config = new VideoConfig();
        config.setmOrientation(mType);
        config.setButtonBgColor(R.color.hartlion_videoad_test_red);
        config.setShowMode(model);
        videoAd.setConfig(config);


        videoAd.setUserId("zhou-test");

        videoAd.setListener(new VideoAdListener() {
            @Override
            public void onAdLoaded() {
                Toast.makeText(VideoDemoActivity.this, "Video is ready!!!! ", Toast.LENGTH_SHORT).show();
                Log.i("Video", "video load success, video is ready");
            }

            @Override
            public void onAdVideoStart() {
                Toast.makeText(VideoDemoActivity.this, "Video start playing!!!! ", Toast.LENGTH_SHORT).show();
                Log.i("Video", "video onAdVideoStart");
            }

            @Override
            public void onAdVideoComplete() {
                Toast.makeText(VideoDemoActivity.this, "Video has played complete!!!! ", Toast.LENGTH_SHORT).show();
                Log.i("Video", "video onAdVideoComplete");
            }

            @Override
            public void onAdClose(VideoAdResult result) {
                Toast.makeText(VideoDemoActivity.this, "Video Ad close!!!! ", Toast.LENGTH_SHORT).show();
                Log.i("Video", "video onAdClose");
            }

            @Override
            public void onAdError(AdError error) {
                Toast.makeText(VideoDemoActivity.this, "Video Ad error!!!! error msg:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Video", "video onAdError:" + error.getMessage());
            }

            @Override
            public void onAdClicked() {
                Toast.makeText(VideoDemoActivity.this, "Video Ad click!!!!", Toast.LENGTH_SHORT).show();
                Log.i("Video", "video onAdClicked");
            }

            @Override
            public void onADS2SCallback(final boolean result) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(VideoDemoActivity.this, "Video Ad S2SCallback result:" + result, Toast.LENGTH_SHORT).show();
                        Log.i("Video", "video onADS2SCallback");
                    }
                });
            }

            @Override
            public void onVideoResume() {
                Toast.makeText(VideoDemoActivity.this, "Video Ad resume!!!!", Toast.LENGTH_SHORT).show();
                Log.i("Video", "video onVideoResume");
            }

            @Override
            public void onVideoPause() {
                Toast.makeText(VideoDemoActivity.this, "Video Ad pause!!!!", Toast.LENGTH_SHORT).show();
                Log.i("Video", "video onVideoPause");
            }
        });

        View titleBarView = findViewById(R.id.video_title_bar);
        titleBarView.findViewById(R.id.back_icon).setVisibility(View.VISIBLE);
        titleBarView.findViewById(R.id.back_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ((TextView) titleBarView.findViewById(R.id.title_text)).setText("Video");


        findViewById(R.id.video_btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (platformType) {
                    case MOPUB_TYPE:
                        mopubAdManager.showVideoAd(VideoDemoActivity.this);
                        break;
                    case ADMOB_TYPE:
                        admobAdManager.showVideoAd(VideoDemoActivity.this);
                        break;
                    default:
                        if (videoAd.isVideoAdReady()) {
                            videoAd.playVideoAd();


                        } else {
                            Toast.makeText(VideoDemoActivity.this, "Video is not ready!!!! ", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }

            }
        });


        findViewById(R.id.video_button_fill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (platformType) {
                    case MOPUB_TYPE: /**mopub模式**/
                        mopubAdManager.loadVideoAd(mType);
                        break;
                    case ADMOB_TYPE: /**admob模式**/
                        admobAdManager.loadVideoAd(mType);
                        break;
                    default:
                        videoAd.loadAd();
                        break;
                }
            }
        });

        findViewById(R.id.video_button_is_ready).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isReady = false;
                switch (platformType) {
                    case MOPUB_TYPE: /**mopub模式**/
                        isReady = mopubAdManager.isVideoReady();
                        break;
                    case ADMOB_TYPE: /**admob模式**/
                        isReady = admobAdManager.isVideoReady();
                        break;
                    default:
                        isReady = videoAd.isVideoAdReady();
                        break;
                }

                Toast.makeText(VideoDemoActivity.this, "Video ready status :" + isReady, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
