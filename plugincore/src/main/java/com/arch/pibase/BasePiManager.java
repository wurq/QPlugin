package com.arch.pibase;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;

import com.arch.plugincore.IPi;
import com.arch.plugincore.PluginContext;
import com.arch.plugincore.PluginPackage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wurongqiu on 2018/5/10.
 */

public class BasePiManager {
    protected static String TAG = "BasePiManager";
    /** 宿主的上下文 */
    protected Context mHostContext;

    /** 插件class loader的 parent class loader */
    protected ClassLoader mDexClassLoaderParent;

    /** 插件控制器，所有插件共用一个。
     * 通过插件控制器，完成插件调用宿主进行startActivity、startService等操作。
     */
    BasePiController mPiController;

    /** 插件实体表。插件ID与Entity的对应 */
    protected Map<Integer, PluginContext> mPiEntityMap;

    protected Handler mHandler = null;

    /** 插件动态库存放目录 */
    protected String mPiLibDir;

//    /** 插件dex输出目录 */
//    protected String mPiDexDir;
//
//    protected HandlerThread mInstallLooper = null;
//    protected Handler mInstallHandler = null;

    protected static BasePiManager sInstance;

    protected SparseArray<PluginPackage> mPluginPackageMap;

    public static BasePiManager getPiManager() {
        return sInstance;
    }
    public void onCreate(Context context) {
        Log.i(TAG, "onCreate");

        mHostContext = context;
        mDexClassLoaderParent = IPi.class.getClassLoader();
        mPiEntityMap = new HashMap<Integer, PluginContext>();
//        mHostCallbackMap = new HashMap<Integer, PluginCallback>();
//        mPluginCallbackMap = new HashMap<Integer, List<PluginCallback>>();
        mPluginPackageMap = new SparseArray<PluginPackage>(2);
//        PluginPackage hostPkg = new PluginPackage(mHostContext, mDexClassLoaderParent);
//        mPluginPackageMap.append(PluginPackage.HOST_PKG_ID, hostPkg);
//        //mPiConfigResolver = new ConfigResolver();
//        mPiConfigDao = PiConfigDao.getInstance();
//        mPiControler = new BasePiController();
//
//        mPiLibDir = mHostContext.getDir(MeriConfigDao.CON_PI_LIB_DIR,
//                Context.MODE_PRIVATE).getAbsolutePath();
//        mPiDexDir = mHostContext.getDir(MeriConfigDao.CON_PI_DEX_DIR,
//                Context.MODE_PRIVATE).getAbsolutePath();
    }

}
