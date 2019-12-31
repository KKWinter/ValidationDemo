//
//  AboutFragment.java
//
//  Copyright (c) 2014 Nexage. All rights reserved.
//

package com.jumpraw.mraiddemo;

import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jumpraw.mraid.MRAIDView;
import com.jumpraw.mraid.internal.MRAIDLog;

import androidx.fragment.app.Fragment;

public class AboutFragment extends Fragment {
    private static final String TAG = "AboutFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MRAIDLog.d(TAG, "onCreateView");

        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        String versionName = "";
        try {
            versionName = getActivity().getPackageManager().getPackageInfo(
                    getActivity().getPackageName(), 0).versionName;

        } catch (NameNotFoundException e) {
            MRAIDLog.e(TAG, e.getMessage());
        }

        // Set versions
        TextView version = (TextView) rootView.findViewById(R.id.versionNumber);
        version.setText("MRAID Library: " + MRAIDView.VERSION + " & Demo: "
                + versionName);

        return rootView;
    }

}
