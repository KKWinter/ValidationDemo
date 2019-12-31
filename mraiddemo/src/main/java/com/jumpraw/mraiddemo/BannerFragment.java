package com.jumpraw.mraiddemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.ListFragment;

public class BannerFragment extends ListFragment {

    private final static String TAG = "BannerFragment";
    private final static String PREFIX = "banner";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(),
                android.R.layout.simple_list_item_1,
                MainActivity.getTestFiles(getContext(), PREFIX));
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String name = (String) getListAdapter().getItem(position);
        String filename = PREFIX + "." + name + ".html";
        Log.d(TAG, name);
        String content = MainActivity.getAssets(getContext(), filename);

        if (content != null) {
            Intent intent = new Intent(getActivity(), BannerActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("content", content);
            startActivity(intent);
        }
    }


}
