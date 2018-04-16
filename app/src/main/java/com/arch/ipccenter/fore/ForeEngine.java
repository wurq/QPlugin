package com.arch.ipccenter.fore;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.arch.application.AppProfile;
import com.arch.commonconst.HostAction;
import com.arch.ipccenter.base.IIpcCallback;
import com.arch.ipccenter.base.IIpcConnection;
import com.arch.ipccenter.base.IpcCenter;

import static com.arch.ipccenter.fore.ForeEngineHandler.MSG_TRY_CONNECT_BACK_ENGINE;

/**
 * Created by wurongqiu on 2018/3/30.
 */

public class ForeEngine {
    final static String TAG = "ForeEngine";

    //---------------------------------------------------------------------------------
    IIpcConnection mBackEngine;

    /**
     * 后台进程AIDL通道是否已经连通。
     */
    public volatile boolean mIsBackConnected = false;

    boolean mIsStop = false;

    Context mContext;

    static ForeEngine mInstance = null;

    static ForeEngineHandler mForeMainHandler;

    static final int MAX_CONNECT_RETRY_COUNT = 10;

    volatile static int sConnectRetryCount;

//    public static boolean sIsExitSoftware = false;

//    static int sRebootViewId = -1;
//    static boolean sIsNeedReboot = false;

    public static volatile boolean sIsMeriReady = false;

    static boolean mNeedShowMainPage = false;

    // ------------------------------------------------------
    public static ForeEngine getInstance() {
        if(mInstance == null) {
            synchronized (ForeEngine.class) {
                if(mInstance == null) {
                    mInstance = new ForeEngine();
                }
            }
        }
        return mInstance;
    }

    ForeEngine() {
        mContext = AppProfile.getContext();
        mForeMainHandler = new ForeEngineHandler(this);
        sConnectRetryCount = 0;


        mForeMainHandler.post(new Runnable() {
            @Override
            public void run() {
                // 注册监听系统消息
            }
        });
    }

    /**
     * 后台进程的service connection。
     */
    ServiceConnection mBackEngineConn = new ServiceConnection() {
        /**
         * binService成功（后台到前台的通道此时已打通）后会被调用。 将前台回调注册到后台，打通后台到前台的通道。
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "back engine is connected");
            onBackServiceConnected(name, service);
        }

        /**
         * unbindService成功后会被调用。 移除先前注册的回调。
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "back engine is disconnected");
            onBackServiceDisconnected(name);
        }
    };



    private void onBackServiceConnected(ComponentName name, IBinder service) {
        if (service == null) {
            mIsBackConnected = false;
            mBackEngine = null;
            return;
        }

        mBackEngine = IIpcConnection.Stub.asInterface(service);
        if (mBackEngine != null) {
            try {
                mIsBackConnected = true;
                sConnectRetryCount = 0;

                mBackEngine.regCallback(mIpcCallback);
            } catch (RemoteException e) {
                Log.e(TAG, "ipc err @ onServiceConnected:" + e.getMessage(), e);
            }
        }
    }

    private void onBackServiceDisconnected(ComponentName name) {
    }


    /**
     * 后台service回调方法。
     */
    IIpcCallback mIpcCallback = new IIpcCallback.Stub() {
        @Override
        public int onIpcCallback(int ipcMsg, Bundle inBundle, Bundle outBundle) throws RemoteException {
            int err;
            try {
                err = handleIpcCallback(ipcMsg, inBundle, outBundle);
            } catch (Exception e) {
                err = -1;//MeriErrCode.MERI_ERR_UNKNOWN;
                throw new RuntimeException(e);
            }
            return err;
        }
    };

    int handleIpcCallback(int ipcMsg, Bundle inBundle, Bundle outBundle) {
        Log.i(TAG, "call back from background @ id :" + ipcMsg);
        int err = ForeIpcCenter.getInstance().onIpcCall(ipcMsg, inBundle, outBundle);
        return err;
    }

    /*
     *
     */
    public boolean bindBackEngine() {
        Log.i(TAG, "connect back engine");
        // 启动并连接后台
        Intent intent = new Intent();
        // 在部分手机上需要设置componentName ref：https://www.jianshu.com/p/97de61256e5b
        ComponentName componentName= new ComponentName( HostAction.APP_PACKAGE_NAME, HostAction.BACK_ENGINE);
        intent.setComponent(componentName);

        intent.setAction(HostAction.BACK_ENGINE);
        intent.putExtra(IpcCenter.IPC_MSG_ID, IpcCenter.IpcMsg.F2B_ASYNC_JUST_START);
        boolean ret = false;
        try {
            ret = mContext.bindService(intent, mBackEngineConn, Service.BIND_AUTO_CREATE);
        } catch (Exception e) {
            // do nothing
        }
        Log.d(TAG, "bind BackEngine Service return =  " + ret);
        return ret;
    }

    /*
     * 通过AIDL异步启动
     */
    public void connectBackEngineAsync() {
        Log.i(TAG, "connect back engine async");

        mIsStop = false;
        sConnectRetryCount = 0;

        if (!mIsBackConnected) {
            Message msg = Message.obtain();
            msg.what = MSG_TRY_CONNECT_BACK_ENGINE;
            mForeMainHandler.sendMessageAtFrontOfQueue(msg);
        }

        // 启动前台fore的service
        if (!ForeService.sIsServiceOn) {
            mForeMainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    Intent fsIntent = new Intent(ForeService.ACTION_NAME);
                    Intent fsIntent = new Intent(AppProfile.getContext(),ForeService.class);
                    AppProfile.getContext().startService(fsIntent);
                }
            }, 1000);
        }
    }

    public int ipcCallBackEngine(int ipcMsg, Bundle inBundle, Bundle inoutBundle) {
//        int err = MeriErrCode.MERI_ERR_NONE;
        int err = 0;
        try {
            if (mBackEngine != null && mIsBackConnected) {
                if (inBundle == null) {
                    inBundle = new Bundle();
                }
                if (inoutBundle == null) {
                    inoutBundle = new Bundle();
                }
                err = mBackEngine.ipcCall(ipcMsg, inBundle, inoutBundle);
            } else {
//                err = MeriErrCode.MERI_ERR_AIDL_ERR;
//                Log.e(TAG, "ipcCallBackEngine(), method(" + ipcMsg + "), - back engine is not connected");
            }
        } catch (RemoteException e) {
//            err = MeriErrCode.MERI_ERR_AIDL_ERR;
//            Log.e(TAG, "ipcCallBackEngine(), method(" + ipcMsg + "), - aidl err: " + e.getMessage(), e);
        } catch (SecurityException e) {
//            err = MeriErrCode.MERI_ERR_AIDL_ERR;
//            Log.e(TAG, "ipcCallBackEngine(), method(" + ipcMsg + "), - aidl err: " + e.getMessage(), e);
        }

        // 重连
//        if (err == MeriErrCode.MERI_ERR_AIDL_ERR) {
//            mAidlErrCount++;
//            if (mAidlErrCount == 5) {
//                mAidlErrCount = 0;
//                // connect后台，确保前后台通道连通
//                mForeMainHandler.sendEmptyMessageDelayed(MSG_TRY_CONNECT_BACK_ENGINE, 500);
//            }
//        }
        return err;
    }



}
