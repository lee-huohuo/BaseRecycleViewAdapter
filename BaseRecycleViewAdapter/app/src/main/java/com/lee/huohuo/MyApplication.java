package com.lee.huohuo;

import android.app.Application;

import org.xutils.x;

/**
 * Created by lee on 16-12-13.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
    }
}
