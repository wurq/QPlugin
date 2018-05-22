package com.arch.plugincore;

import android.app.Activity;

/**
 * Created by wurongqiu on 2018/5/16.
 */

public interface ActivityService {
    /**
     * 获取当前插件的上下文。
     */
    public PluginContext getCurrentPiContext();

    /**
     * 获取当前插件view。
     */
    public BasePage getCurrentPiView();

    /**
     * 获取当前插件view对应的id。
     */
    public int getCurrentPiViewId();

    /**
     * 获取当前插件activity。
     */
    public Activity getCurrentPiActivity();

    /**
     * 栈中是否有activity
     */
    public boolean hasPiActivity();

    /**
     * 获取栈中的activity。
     */
    public Activity getPiActivity(int piViewId);

//
//    /**
//     * 根据view id 获取其他插件对应的页面。
//     */
//    public BasePage getViewFromOtherForePi(int viewId, final Activity context);

}
