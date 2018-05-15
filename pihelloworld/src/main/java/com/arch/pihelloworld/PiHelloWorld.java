package com.arch.pihelloworld;

import android.os.Bundle;

import com.arch.plugincore.AbsPlugin;
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
