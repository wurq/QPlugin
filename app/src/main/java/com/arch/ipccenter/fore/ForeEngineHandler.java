package com.arch.ipccenter.fore;

/**
 * Created by wurongqiu on 2017/6/18.
 */

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.arch.base.application.BaseApplication;
import com.arch.base.util.ProcessUtil;

import java.lang.ref.WeakReference;

import static com.arch.ipccenter.fore.ForeEngine.MAX_CONNECT_RETRY_COUNT;
import static com.arch.ipccenter.fore.ForeEngine.sConnectRetryCount;


public class ForeEngineHandler extends Handler {
    final static String TAG = "ForeEngineHandler";

    final static int MSG_TRY_CONNECT_BACK_ENGINE 	= 0x01;


    WeakReference<ForeEngine> mForeEngine;
    public ForeEngineHandler(ForeEngine foreEngine) {
        super(BaseApplication.getApplication().getMainLooper());
        mForeEngine = new WeakReference<ForeEngine>(foreEngine) ;
    }

    @Override
    public void handleMessage(Message msg) {
        ForeEngine foreEngine = mForeEngine.get();
        if (foreEngine != null) {
            switch (msg.what) {
                case MSG_TRY_CONNECT_BACK_ENGINE:{
                    removeMessages(msg.what);

                    if (!foreEngine.mIsBackConnected) {
                        boolean ret = foreEngine.bindBackEngine();

                        if (ret) {
                            // 延迟后检测是否连上了，如果没连上则重试
                            sendEmptyMessageDelayed(MSG_TRY_CONNECT_BACK_ENGINE, 1000);
                        } else {
                            sendEmptyMessageDelayed(MSG_TRY_CONNECT_BACK_ENGINE, 300);
                        }

                        // 当连接后台次数大于阈值时，重启
                        sConnectRetryCount++;
                        if (sConnectRetryCount >= MAX_CONNECT_RETRY_COUNT) {
                            sConnectRetryCount = 0;
                            Log.i(TAG, "connect back engine more than retry count");

                            ProcessUtil.killProcess(android.os.Process.myPid());

                        }
                    } else {
                        Log.d(TAG, "connect back engine, ignore");
                    }
                }
                    break;

                default:
                    break;
            }
        }
    }
};