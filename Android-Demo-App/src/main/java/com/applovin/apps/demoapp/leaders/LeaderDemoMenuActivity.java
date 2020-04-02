package com.applovin.apps.demoapp.leaders;

import android.content.Intent;

import com.applovin.apps.demoapp.DemoMenuActivity;
import com.applovin.apps.demoapp.DemoMenuItem;

public class LeaderDemoMenuActivity
        extends DemoMenuActivity
{
    @Override
    protected DemoMenuItem[] getListViewContents()
    {
        DemoMenuItem[] result = {
                new DemoMenuItem( "Programmatic", "Programmatically create an instance of it", new Intent( this, LeaderProgrammaticActivity.class ) ),
                new DemoMenuItem( "Layout Editor", "Create a Leader from the layout editor", new Intent( this, LeaderLayoutEditorActivity.class ) ),
        };
        return result;
    }
}
