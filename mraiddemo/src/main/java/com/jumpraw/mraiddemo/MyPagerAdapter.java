package com.jumpraw.mraiddemo;

import com.jumpraw.mraiddemo.AboutFragment;
import com.jumpraw.mraiddemo.BannerFragment;
import com.jumpraw.mraiddemo.InterstitialFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {

	public MyPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
		case 0:
			return new BannerFragment();
		case 1:
			return new InterstitialFragment();
		case 2:
			return new AboutFragment();
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
