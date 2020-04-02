package com.jumpraw.litepal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.litepal.LitePal;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                test();

            }
        });

    }


    private void test() {



        Song song1 = new Song();
        song1.setName("song1");
        song1.save();

        Song song2 = new Song();
        song2.setName("song2");
        song2.save();


        Album album = new Album();
        album.setName("测试1");
        album.getSongList().add(song1);
        album.save();

        Album album1 = new Album();
        album1.setName("测试2");
        album1.getSongList().add(song1);
        album1.getSongList().add(song2);
        album1.save();


        List<Album> albums = LitePal.findAll(Album.class, true);
        Log.i("test", "test: >>>" + albums.size());
        for (Album a : albums) {
            Log.i("test", "test: =====" + a.toString());
        }

    }
}
