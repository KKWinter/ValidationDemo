package com.applovin.apps.demoapp.interstitials;

import android.content.Intent;

import com.applovin.apps.demoapp.DemoMenuActivity;
import com.applovin.apps.demoapp.DemoMenuItem;

public class InterstitialDemoMenuActivity
        extends DemoMenuActivity
{
    @Override
    protected DemoMenuItem[] getListViewContents()
    {
        DemoMenuItem[] result = {
                new DemoMenuItem( "Basic Integration", "Quick interstitial ads integration", new Intent( this, InterstitialBasicIntegrationActivity.class ) ),
                new DemoMenuItem( "Manually loading ad", "Use this for greater control over the ad load process", new Intent( this, InterstitialManualLoadingActivity.class ) ),
                new DemoMenuItem( "Zone Integration", "Create different user experiences of the same ad type", new Intent( this, InterstitialZoneActivity.class ) )
        };
        return result;
    }
}
