package com.arch.pibase;

import android.content.Context;

import com.arch.application.AppProfile;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wurongqiu on 2018/5/10.
 */

public class BasePiApplication {

    static final String TAG = "BasePluginApplication";

    /** Debug模式下是安装所有插件 */
    public static final boolean INSTALL_ALL_PI_IN_DEBUG_MODE = true;

    /** 当前所在的进程 */
    static int mCurrentProcessRuntype = -1;

    /** 分配进程内全局唯一的ID,用于回调,通知栏等等 **/
    static AtomicInteger mAllocatedId = new AtomicInteger(0);

    static int mDebugMode = -1;


    /**
     * 获取程序上下文
     */
    public static Context getAppContext() {
        return AppProfile.getContext();
    }

}
