package com.arch.pihelloworld;

import android.app.Activity;
import android.os.Bundle;

import com.arch.plugincore.AbsPlugin;
import com.arch.plugincore.BasePage;
import com.arch.plugincore.PluginCallback;
import com.arch.plugincore.PluginContext;

public class PiHelloWorld extends AbsPlugin {

    private static final String TAG = "PiHelloWorld";

    private static PiHelloWorld sInstance;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
//    }

    @Override
    public void onInstall(PluginContext piContext, boolean isVirgin) {
        sInstance = this;
    }

    @Override
    public void onCreate(PluginContext piContext) {
        super.onCreate(piContext);

        sInstance = this;
    }

    @Override
    public void onDestroy() throws Exception {

    }


    /**
     * 根据view id 获取插件对应的页面
     *
     * @param viewId
     *            页面id
     * @param activity
     *            承载view的activity
     * @return 页面
     */
    @Override
    public BasePage getActivityView(int viewId, Activity activity) {
        switch (viewId) {
            // 插件框架
            case HelloWorldConst.ViewId.HELLO_WORLD_MAIN_VIEW: { // 插件主页面
                return new MainExampleView(activity);
            }

            default: {
                return null;
            }
        }


    }

    @Override
    public void onUninstall() {

    }

    @Override
    public int handleOuterRequest(int srcPiId, Bundle inBundle, Bundle outBundle) {
        return 0;
    }

    @Override
    public int handleOuterCallback(int srcPiId, int callbackAction, PluginCallback callback) {
        return 0;
    }

    @Override
    public int handleCallback(int callbackAction, PluginCallback callback) {
        return 0;
    }
}
