package com.arch.plugincore;

import android.app.Activity;

/**
 * Created by wurongqiu on 2018/5/5.
 */

public abstract class AbsPlugin implements IPi {

    static String TAG = "AbsPlugin";
    /**
     * 插件IContext。每个插件有属于自己的IContext，但IContext并非真正的Context类，
     * 只能通过IContext获取相关的资源、进行inflate等。
     */
    protected PluginContext mPiContext;

//    /**
//     * 与插件资源环境相关的ApplicationContext
//     */
//    PiApplicationContext mPiApplicationContext;

    public boolean mRecycle = true;

    /**
     * 插件id。
     */
    public int mPiId = -1;

    @Override
    public void onInstall(PluginContext piContext, boolean isVirgin) {
        mPiContext = piContext;
    }

    /**
     * 插件实例构造。 插件被加载时由框架调用，只调用一次。
     * 注：建议不要在插件接口内进行耗时操作。
     * <p>
     * <em>派生类必须“先”调用父类方法。</em>
     * </p>
     * @param piContext 插件上下文(并不是系统的Context)
     */
    @Override
    public void onCreate(PluginContext piContext) {
        mPiContext = piContext;
    }

//    /**
//     * 询问插件是否能够从内存中移除。
//     * @return 能否移除的状态
//     */
//    public PiUnloadState onPrepareUnload() {
//        Log.i(TAG, "plugin(" + mPiId + ") onPrepareUnload");
//        PiUnloadState state = new PiUnloadState(PiUnloadState.FLEXIBLE);
//        return state;
//    }

    /**
     * 插件移除时调用，用于清理内存。
     * <p>
     * <em>派生类必须“后”调用父类方法。</em>
     * </p>
     * @throws Exception
     */
//    @Override
//    public void onDestroy() throws Exception {
//        Log.i(TAG, "plugin(" + mPiId + ") onDestroy");
//        synchronized (mMapLock) {
//            if (mCallbackMap != null && mCallbackMap.size() > 0) {
//                Log.e(TAG, "PluginCallback doesn't total removed when destroy");
////			throw new Exception("PluginCallback doesn't total removed when destroy");
//            }
//            if (mCallbackMap != null) {
//                mCallbackMap.clear();
//                mCallbackMap = null;
//            }
//        }
//
//        if (mRecycle) {
//            mPiContext = null;
//            Log.d(TAG, "plugin(" + mPiId + ") onDestroy, recycle mPiContext");
//        } else {
//            Log.d(TAG, "plugin(" + mPiId + ") onDestroy, not recycle mPiContext");
//        }
//    }

    /**
     * 卸载插件时调用。 注：建议只在此回调中进行一些文件清理工作。
     */
    @Override
    public void onUninstall() {
//        Log.i(TAG, "plugin(" + mPiId + ") onUninstall");
    }
    /**
     * 根据view id 获取插件对应的页面。
     * 由插件重写实现。
     * @param viewId 页面id
     * @param activity 承载view的activity
     * @return 页面view
     */
    public BasePage getActivityView(int viewId, final Activity activity) {
        return null;
    }

    /**
     * 启动activity。
     * @param intent 启动页面所用的intent
     * @param outer 指明启动软件内部界面或外部界面。true:目的activity为软件外部activity; false:软件内部activity
     */
    public final void startActivity(PluginIntent intent, boolean outer) {
        startActivityForResult(intent, -1, outer);
    }

    /**
     * 启动activity，有返回值。
     * @param intent 启动页面所用的intent
     * @param requestCode If >= 0, this code will be returned in onActivityResult() when the activity exits.
     * @param outer 指明启动软件内部界面或外部界面。true:目的activity为软件外部activity; false:软件内部activity
     */
    public final void startActivityForResult(PluginIntent intent, int requestCode, boolean outer) {
//        ForePiManager.getPiManager().getPluginControler().startActivityForResult(intent, requestCode, outer);
    }
}
