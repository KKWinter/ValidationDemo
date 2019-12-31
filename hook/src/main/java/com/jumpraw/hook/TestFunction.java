package com.jumpraw.hook;

import android.util.Log;

public class TestFunction {

    private Pen pen;

    public TestFunction() {
        pen = new Logger();
    }

    public void start(String url) {
        pen.print(url);
    }

    class Logger implements Pen {

        @Override
        public void print(String url) {
            Log.i("test", "print: " + url);
        }
    }

    interface Pen {
        void print(String url);
    }
}
