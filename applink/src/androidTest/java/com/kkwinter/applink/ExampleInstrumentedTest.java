package com.kkwinter.applink;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final String TAG = "ExampleInstrumentedTest";


    private static String request = "ORp7Ou9vq/Yrgr1loFqm8cP1FvQbZdl2FKBgRvjDllyxXuFQHX4LDxRGD9YrKqPNW1jf5zYe8V8kcWB4j2fNH32ErzOQrFfLofbb3erHrlMtN4kUd9guTdZg0HohiIe0iSZaAgEL4WqGh4sp0rkk7cBo/RxoWFlfk7TR6bChK9gCcDEv6MgSKp76aE9wMLSnO0qOUzw3FYHZnkQc01mWADeArQaDpS1K/3TGs3+H1msEIzrO7IH+1hTTZnjuLuMIQt2e+VZZyMJMcD5lxFNzmC1cHDP078qORqvRVzamVnbYkhOrdFta4ox7+hEKklS7ABaGTCvgBN5EF/MCEEoo6symmECcey4NGsjcwIxPgB1qdFJL4wAPHGJN6un9zu1wQzkXCxChNvBGalo2YtBgkVpcJPUSVXt6vPagFp+RW8p2v5rN9fub1cLGH1/5zuW3xI8PolXL46N2SuR2+mgMpqOcBKW+1kue+VRgQOcmpB1MkpVzaqj2pZIoYn251RceNP0jFWctwGCSkFsQm1D9UUW89mwqWp8H+E58VMKlpuBzNpBP2oP8/t3ubA7cwcNVPiggGFyFDJJmICF7JCynuHYPWskPJs4oLa/GrkZS7x/9j2kin33pmxAL1IqnXyTDfJOXSXXFCySr5gCZpQZtQW3695EfJIDXaZVfBgtWBdRCxTCKuU3+Bz9cCJ3m/G2w/2UqVvAUtrRiQBpNC1kB8j4RIbnooM/bh59N7yuPEc41ev+sDb7r1kuwMt/tLiur";

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();



        Log.i(TAG, "onClick: >>>" + In.a("DpmNU4uLIZTFl1zvpPaPsUrxVw=="));
        Log.i(TAG, "onClick: >>>" + In.a("DpmNU4uLIZTFl1z7pOI="));


        Log.i(TAG, "useAppContext: " + In.a(request));
    }
}
