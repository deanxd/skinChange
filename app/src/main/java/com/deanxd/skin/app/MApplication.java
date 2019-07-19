package com.deanxd.skin.app;

import android.app.Application;

import com.deanxd.skin.lib.SkinManager;

public class MApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(getApplicationContext());
    }
}
