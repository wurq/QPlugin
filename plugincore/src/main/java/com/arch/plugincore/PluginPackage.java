package com.arch.plugincore;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by wurongqiu on 2018/5/10.
 */

public class PluginPackage implements IContext{
    static final String TAG = "PluginContext";

    /**
     * 宿主插件包ID,其它插件包ID以外层插件ID作为插件包ID
     */
    public static final int HOST_PKG_ID = 0;

//    PiResourceChanger mPluginResourceChanger;
    ILayoutInflater mPluginLayoutInflater;
    AssetManager mPluginAssetManager;
    ClassLoader mPluginClassLoader;
    Resources mPluginResources;
    Resources.Theme mPluginTheme;
    Context mAppContext;
    public boolean isReunion;

//    /**
//     * 用于LayoutInflater inflate宿主view和插件自定义view的Factory。
//     * 注：因为LayoutInflater的setFactory只能设置一次， inflate from
//     * service的时候使用的是宿主的service，service的factory只能设一个，
//     * 要对应到多个插件，所以使用静态的factory，然后通过ClassLoader的改变指向不用的插件。
//     */
//    public static Factory sPluginInflaterFactory;

    /**
     * 用于sPluginInflaterFactory调用对应的插件的class loader
     */
    static ClassLoader sPluginInflaterClassLoader;

    /**
     * 插件是否reunion
     */
    static boolean sIsPluginReunion;


    @Override
    public Resources getResources() {
        return mPluginResources;
    }

    @Override
    public AssetManager getAssertManager() {
        return mPluginAssetManager;
    }

    @Override
    public ILayoutInflater getLayoutInflater() {
        return mPluginLayoutInflater;
    }

    @Override
    public ClassLoader getClassLoader() {
        return mPluginClassLoader;
    }

    public PluginPackage(Context context, final ClassLoader classLoader) {
        try {
//            if (sPluginInflaterFactory == null) {
//                sPluginInflaterFactory = new PluginInflaterFactory();
//            }

            isReunion = true;

            mAppContext = context;
            mPluginClassLoader = classLoader;
            mPluginAssetManager = mAppContext.getAssets();
//            mPluginResourceChanger = new PiResourceChanger();
            mPluginResources = mAppContext.getResources();
            mPluginTheme = mPluginResources.newTheme();
//            mPluginTheme.applyStyle(PiResourceChanger.mThemeResource, true);

//            mPluginLayoutInflater = new PluginLayoutInflater(
//                    BasePiApplication.getCurrentProcessRuntype());
        } catch (Exception e) {
            Log.e(TAG, "PluginContext construct err: " + e.getMessage(), e);

            // 测试模式下将异常抛出
//            BasePiApplication.throwDebugException(e.getMessage(), e);
        }
    }

    public PluginPackage(String pluginpath, Context context,
                         final ClassLoader classLoader) {
        try {
//            if (sPluginInflaterFactory == null) {
//                sPluginInflaterFactory = new PluginInflaterFactory();
//            }
            isReunion = false;
            mAppContext = context;
            mPluginClassLoader = classLoader;
            mPluginAssetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = AssetManager.class.getDeclaredMethod(
                    "addAssetPath", String.class);
            addAssetPathMethod.setAccessible(true);
            addAssetPathMethod.invoke(mPluginAssetManager, pluginpath);

//            mPluginResourceChanger = new PiResourceChanger();
//            mPluginResources = new PluginResources(mPluginAssetManager,
//                    mAppContext.getResources().getDisplayMetrics(), mAppContext
//                    .getResources().getConfiguration(), mAppContext);
            mPluginTheme = mPluginResources.newTheme();
//            mPluginTheme.applyStyle(PiResourceChanger.mThemeResource, true);

//            mPluginLayoutInflater = new PluginLayoutInflater(
//                    BasePiApplication.getCurrentProcessRuntype());
        } catch (Exception e) {
            Log.e(TAG, "PluginContext construct err: " + e.getMessage());

            // 测试模式下将异常抛出
//            BasePiApplication.throwDebugException(e.getMessage(), e);
        }
    }

}
