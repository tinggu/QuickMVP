package com.cyou.quick;

import android.app.Application;

import com.squareup.leakcanary.RefWatcher;

/**
 * Created by dongyanjing on 2015/10/27.
 */
public abstract class QuickApplication extends Application {

    private static QuickApplication sInstance;

    @Override
    public void onCreate() {
        sInstance = this;
        super.onCreate();
    }

    public abstract RefWatcher getRefWatcher();

    public static QuickApplication getInstance() {
        return sInstance;
    }

}
