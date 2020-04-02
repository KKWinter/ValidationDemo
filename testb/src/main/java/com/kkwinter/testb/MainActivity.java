package com.kkwinter.testb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Activity otherActivity;

    public void setActivity(Activity paramActivity) {
        this.otherActivity = paramActivity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        boolean b = false;

        if (savedInstanceState != null) {
            b = savedInstanceState.getBoolean("KEY_START_FROM_OTHER_ACTIVITY", false);

        }

        if (b) {
            this.otherActivity.setContentView(getTextView(otherActivity, "未安装的B应用"));
        } else {
            super.onCreate(savedInstanceState);
            setContentView(getTextView(this, "安装的B应用"));
        }

    }


    private TextView getTextView(Context context, String content) {

        TextView textView = new TextView(context);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setGravity(Gravity.CENTER);
        textView.setText(content);
        textView.setTextSize(30);
        textView.setTextColor(Color.parseColor("#000000"));

        return textView;
    }
}
