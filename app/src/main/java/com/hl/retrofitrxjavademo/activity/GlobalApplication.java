package com.hl.retrofitrxjavademo.activity;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by HL on 2017/9/14/0014.
 */

public class GlobalApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
