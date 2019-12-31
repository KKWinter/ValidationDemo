package com.jumpraw.mraiddemo.mraid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.jumpraw.mraiddemo.R;
import com.jumpraw.mraiddemo.Utils.ContextHolder;
import com.jumpraw.mraiddemo.Utils.PageAdVO;
import com.jumpraw.mraiddemo.Utils.Utils;
import com.jumpraw.mraiddemo.gp.GpsHelper;

public class MraidActivity extends AppCompatActivity {

    private RelativeLayout container;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mraid);
        context = this.getApplicationContext();
        ContextHolder.init(context);
        GpsHelper.startLoadGaid();

        container = findViewById(R.id.rl_container);

        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PageAdVO.PageAd pageAd = new PageAdVO.PageAd();
                pageAd.manifest = "http://static.cloudmobi.net/pagead/manifest.appcache";
//                pageAd.html_tag = "<link rel=\"stylesheet\" type=\"text/css\" href=\"http://static.cloudmobi.net/pagead/css/app.css\">\n" +
//                        "\n" +
//                        "<script src=\"http://mraid.js\"></script>\n" +
//                        "\n" +
//                        "<script>var ctAdData = {\"title\":\"Arcade Center\",\"description\":\"Here are 5000+ free games to activate your brain!\",\"icon\":\"https://gwpublic-yeahtargeter-com.oss-ap-southeast-1.aliyuncs.com/gw-ofs/1576046353771195982.png\",\"rate\":0,\"review\":0,\"img\":\"https://gwpublic-yeahtargeter-com.oss-ap-southeast-1.aliyuncs.com/gw-ofs/1576046357151423956.gif\",\"img_w\":1020,\"img_h\":600,\"button\":\"立刻查看\",\"format\":\"banner_300x250\",\"tpl_type\":\"i_190x100\",\"ad_w\":300,\"ad_h\":250,\"click_url\":\"https://www.funtop.online/?utm_source=sdk\\u0026utm_medium=67102232\\u0026utm_campaign=25\\u0026utm_term=mobile\",\"final_url\":\"\",\"landing_type\":1,\"choices_link\":\"http://www.suibyuming.com/privacy-policy.html\",\"clk_tks\":[\"http://logger.suibyuming.com/android/v1/click?msv=1\\u0026country=CN\\u0026pn=com.zcoup.adsdk.example%2816%29\\u0026user_id=5daa881ceaa3643f\\u0026ran=720-980-782\\u0026platform=Android\\u0026gaid=b2ddafe1-d118-456a-a892-5ed30537c9ba\\u0026dt=phone\\u0026nt=1\\u0026aid=5daa881ceaa3643f\\u0026ip=221.218.247.50\\u0026gp=1\\u0026dmf=LGE\\u0026dml=Nexus+5X\\u0026dpd=bullhead\\u0026so=1\\u0026ds=2.625\\u0026ua=Mozilla%2F5.0+%28Linux%3B+Android+6.0.1%3B+Nexus+5X+Build%2FMMB29K%3B+wv%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Version%2F4.0+Chrome%2F78.0.3904.108+Mobile+Safari%2F537.36\\u0026srnc=0\\u0026lang=en\\u0026slot=67102232\\u0026adtype=16\\u0026offer=25\\u0026imp=210082762567102232\\u0026channel=huk\\u0026server_id=\\u0026method=\\u0026sv=a-4.0.5\\u0026doimp=1\\u0026city=beijing\\u0026region=beijing\\u0026pkg=\\u0026image_id=\"],\"imp_tks\":[\"http://logger.suibyuming.com/android/v1/impression?msv=1\\u0026country=CN\\u0026pn=com.zcoup.adsdk.example%2816%29\\u0026user_id=5daa881ceaa3643f\\u0026ran=720-980-782\\u0026platform=Android\\u0026gaid=b2ddafe1-d118-456a-a892-5ed30537c9ba\\u0026dt=phone\\u0026nt=1\\u0026aid=5daa881ceaa3643f\\u0026ip=221.218.247.50\\u0026gp=1\\u0026dmf=LGE\\u0026dml=Nexus+5X\\u0026dpd=bullhead\\u0026so=1\\u0026ds=2.625\\u0026ua=Mozilla%2F5.0+%28Linux%3B+Android+6.0.1%3B+Nexus+5X+Build%2FMMB29K%3B+wv%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Version%2F4.0+Chrome%2F78.0.3904.108+Mobile+Safari%2F537.36\\u0026srnc=0\\u0026lang=en\\u0026slot=67102232\\u0026adtype=16\\u0026offer=25\\u0026imp=210082762567102232\\u0026channel=huk\\u0026server_id=\\u0026method=\\u0026sv=a-4.0.5\\u0026doimp=1\\u0026city=beijing\\u0026region=beijing\\u0026pkg=\\u0026image_id=\"],\"control\":{\"is_mute\":1,\"is_skip\":0}} </script>\n" +
//                        "\n" +
//                        "<script src=\"http://static.cloudmobi.net/pagead/js/app.js\"></script>";

                pageAd.html_tag = Utils.getAssets(context, "demo.html");
                PageAdVO adsVO = new PageAdVO(pageAd);


                ZCAdView adView = new ZCAdView(context, false);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                adView.setZone("10000");
                adView.renderRichMedia(adsVO);
                container.addView(adView, layoutParams);
            }
        });

    }


}
