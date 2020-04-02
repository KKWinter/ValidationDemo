package com.jumpraw.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.jumpraw.wrap.JRWrap;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();

        editText = findViewById(R.id.editText);

//        JRWrap.initialize(context, "10000");

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = editText.getText().toString().trim();

                openBySend(context, url);

            }
        });

        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                editText.setText("");

                JRWrap.initialize(context, "10000");
            }
        });
    }


    public void openBySend(Context context, String url) {
        Log.i("Deeplink", "openBySend: >>>" + url);
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            pendingIntent.send();
        } catch (Throwable e) {
            Log.i("Deeplink", "openBySend: error >>>");
            e.printStackTrace();
        }

    }
}
