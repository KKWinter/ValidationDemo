package com.applovin.apps.demoapp.banners;

import android.content.Intent;

import com.applovin.apps.demoapp.DemoMenuActivity;
import com.applovin.apps.demoapp.DemoMenuItem;

public class BannerDemoMenuActivity
        extends DemoMenuActivity
{
    @Override
    protected DemoMenuItem[] getListViewContents()
    {
        DemoMenuItem[] result = {
                new DemoMenuItem( "Programmatic", "Programmatically creating an instance of it", new Intent( this, BannerProgrammaticActivity.class ) ),
                new DemoMenuItem( "Layout Editor", "Create a banner from the layout editor", new Intent( this, BannerLayoutEditorActivity.class ) ),
                new DemoMenuItem( "Zone Integration", "Create different user experiences of the same ad type", new Intent( this, BannerZoneActivity.class ) ),
        };
        return result;
    }
}
