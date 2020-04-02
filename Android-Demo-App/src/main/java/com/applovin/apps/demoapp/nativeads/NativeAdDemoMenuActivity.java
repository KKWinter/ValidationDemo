package com.applovin.apps.demoapp.nativeads;

import android.content.Intent;

import com.applovin.apps.demoapp.DemoMenuActivity;
import com.applovin.apps.demoapp.DemoMenuItem;

public class NativeAdDemoMenuActivity
        extends DemoMenuActivity
{
    @Override
    protected DemoMenuItem[] getListViewContents()
    {
        DemoMenuItem[] result = {
                new DemoMenuItem( "Single ad", "Programmatically load an ad using our open-source carousel view", new Intent( this, NativeAdCarouselUIActivity.class ) ),
                new DemoMenuItem( "Multiple ads", "Simple native ads in a RecyclerView", new Intent( this, NativeAdRecyclerViewActivity.class ) )
        };
        return result;
    }
}
