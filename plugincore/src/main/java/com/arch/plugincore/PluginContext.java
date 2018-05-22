package com.arch.plugincore;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

/**
 * Created by wurongqiu on 2018/5/4.
 */

public class PluginContext implements IContext{
    static final String TAG = "PluginContext";

    /**
     * 插件包，一个插件包可以对应多个插件
     */
    PluginPackage mPluginPkg;

    /** 插件id。  */
    public int mPiId = -1;

    /** 插件版本  */
    public int mPiVer;

    /** 插件jar包的包名(对应manifest) */
    public String mPackageName;

    /** 插件实例  */
    AbsPlugin mPiInstance;

    //有些插件直接引用了public的变量，暂时放这里。。。
    public Context mAppContext;


    public PluginPackage getPluginPackage(){
        return mPluginPkg;
    }

    public void setPluginPackage(PluginPackage pluginPkg){
        mPluginPkg = pluginPkg;
        mAppContext = pluginPkg.getAppContext();
    }


    /**
     * 构造函数。
     */
    public PluginContext() {
    }

    public Resources.Theme getTheme() {
        return mPluginPkg.getTheme();
    }

    public PluginContext(PluginPackage pluginPkg) {
        mPluginPkg = pluginPkg;
        mAppContext = pluginPkg.getAppContext();
    }

    @Override
    public AssetManager getAssertManager() {
        return mPluginPkg.getAssertManager();
    }

    @Override
    public IContext.ILayoutInflater getLayoutInflater() {
        return mPluginPkg.getLayoutInflater();
    }

    @Override
    public Resources getResources() {
        return mPluginPkg.getResources();
    }

    @Override
    public ClassLoader getClassLoader() {
        return mPluginPkg.getClassLoader();
    }

//    /**
//     * 设置插件的实例
//     * @param piInstance
//     */
//    public void setPiInstance(AbsPi piInstance) {
//        mPiInstance = piInstance;
//        Log.i(TAG, "set pi(" + mPiId + ")'s instance");
//    }

    /**
     * 释放实例。
     */
    public void releaseInstance() {
//        mPiInstance = null;
        Log.i(TAG, "releaseInstance() " + "release pi[" + mPiId + "]'s instance");
    }

    /**
     * 获取插件实例。
     * @return
     */
    public AbsPlugin getPiInstance() {
//        Log.i(TAG, "get pi(" + mPiId + ")'s instance: " + (mPiInstance == null ? "null" : mPiInstance.getClass().getName()));
        return mPiInstance;
    }

//    /**
//     * 获取公共服务
//     * @param serviceName 服务名称
//     * @return
//     */
//    public Object getMeriService(int serviceName) {
//        return ServiceCenter.getPiUseService(serviceName, mPiId);
//    }
}
