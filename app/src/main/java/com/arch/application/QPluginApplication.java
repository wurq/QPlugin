package com.arch.application;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.arch.base.application.BaseApplication;
import com.arch.base.util.ProcessUtil;
import com.arch.commonconst.AppProcess;
import com.arch.commonconst.RunType;
import com.arch.ipccenter.fore.ForeEngine;
import com.arch.pibase.BasePiApplication;

/**
 * Created by wurongqiu on 2018/3/30.
 */

public class QPluginApplication extends BaseApplication {

    static final String TAG = "IPCApplication";

    /** 后台是否准备好了 */
    public static boolean sIsBackEngineReady = false;

    static Context mContext;
    static Handler sMainThreadHandler;

    public static Handler getMainHandler() {
        return sMainThreadHandler;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Context context = getApplicationContext();
        AppProfile.setContext(context);

        BasePiApplication.setAppContext(context);
        AppProfile.setStartTime(System.currentTimeMillis() /*+ DataConst.RuntimeStatus.CURRENT_TIME*/);
        sMainThreadHandler = new Handler();

        // 以下所有的初始化，请严格区分进程（前台、后台、重启进程）进行
        int runType = ProcessUtil.getCurrentProcessRunType();
        Log.i(TAG,"current process is,  runType = " + runType);

        if(runType == RunType.RUN_IN_FOREGROUND)  {
            sIsBackEngineReady = ProcessUtil.isProcessAlive(AppProcess.BACKGROUND_PROCESS);
        }

        Log.d(TAG,"application onCreate runType = "+ runType + ", sIsBackEngineReady = " + sIsBackEngineReady);
        if(runType == RunType.RUN_IN_FOREGROUND)  {
//            if(sIsBackEngineReady == true) {
                ForeEngine.getInstance().connectBackEngineAsync();
//            }
        }
        // 后台
        else if (runType == RunType.RUN_IN_BACKGROUND) {

        }
//        ForeService.tryStartForeService();
    }


}
