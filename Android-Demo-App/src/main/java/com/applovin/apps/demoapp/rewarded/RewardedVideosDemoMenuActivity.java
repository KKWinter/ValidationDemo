package com.applovin.apps.demoapp.rewarded;

import android.content.Intent;

import com.applovin.apps.demoapp.DemoMenuActivity;
import com.applovin.apps.demoapp.DemoMenuItem;
import com.applovin.apps.demoapp.rewarded.RewardedVideosActivity;
import com.applovin.apps.demoapp.rewarded.RewardedVideosZoneActivity;

public class RewardedVideosDemoMenuActivity
        extends DemoMenuActivity
{
    @Override
    protected DemoMenuItem[] getListViewContents()
    {
        DemoMenuItem[] result = {
                new DemoMenuItem( "Basic Integration", "Quick rewarded video integration", new Intent( this, RewardedVideosActivity.class ) ),
                new DemoMenuItem( "Zone Integration", "Create different user experiences of the same ad type", new Intent( this, RewardedVideosZoneActivity.class ) )
        };
        return result;
    }
}
