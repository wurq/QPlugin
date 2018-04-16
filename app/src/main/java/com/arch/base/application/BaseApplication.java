package com.arch.base.application;

import android.app.Application;

/**
 * Created by wurongqiu on 2018/3/30.
 */

public abstract class BaseApplication extends Application {

    private static BaseApplication sContext;

    public static BaseApplication getApplication(){
        return sContext;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }
}
