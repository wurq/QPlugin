package com.arch.ipccenter.back;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.arch.application.CrashHandle;
import com.arch.ipccenter.base.IIpcCallback;
import com.arch.ipccenter.base.IIpcConnection;
import com.arch.ipccenter.base.IpcCenter;

import java.util.ArrayList;

/**
 * Created by wurongqiu on 2018/3/30.
 */

public class BackEngine extends Service {
    final static String TAG = "BackEngine";

    static BackEngine sInstance;

    /** 服务是否已经启动 */
    static boolean sIsEngineReady = false;

    // -----进程间AIDL相关------------------------------------------------------------------------------
    /**
     * 进程间的回调。
     */
    RemoteCallbackList<IIpcCallback> mIpcCallbacks = new RemoteCallbackList<IIpcCallback>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        CrashHandle.getInstance().init(this);
        return stub;
    }

    protected final IIpcConnection.Stub stub = new IIpcConnection.Stub() {
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            try {
                return super.onTransact(code, data, reply, flags);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public int ipcCall(int ipcMsg, Bundle inBundle, Bundle inoutBundle) throws RemoteException {
            int err;
            Log.d(TAG,"ipcCall ipcMsg = "+ ipcMsg );
            try {
                err = handleIpcCall(ipcMsg, inBundle, inoutBundle);
            } catch (Exception e) {
                err = -4;
//                err = MeriErrCode.MERI_ERR_UNKNOWN;
                throw new RuntimeException(e);
            }
            Log.d(TAG,"ipcCall err = "+ err );
            return err;
        }

        @Override
        public void regCallback(IIpcCallback callback) throws RemoteException {
            if (callback != null) {
                mIpcCallbacks.register(callback);
                Log.d(TAG, "Added callback with id_hash=" + System.identityHashCode(callback));
            }
        }

        @Override
        public void unregCallback(IIpcCallback callback) throws RemoteException {
            if (callback != null) {
                Log.d(TAG, "unregistering callback with id_hash=" + System.identityHashCode(callback));
                mIpcCallbacks.unregister(callback);
            }
            Log.d(TAG, "unregisterCallback");
        }
    };

    /**
     * 处理IPC同步请求
     */
    int handleIpcCall(int ipcMsg, Bundle inBundle, Bundle outBundle) {
        // 变态,远程传递的Bundle,其classloader是未设置的
        inBundle.setClassLoader(this.getClass().getClassLoader());

        return BackIpcCenter.getInstance().onIpcCall(ipcMsg, inBundle, outBundle);
    }


    /**
     * 通过AIDL回调前台的方法。
     */
    public synchronized int callBackRemoteMethod(int callbackId, Bundle inBundle, Bundle outBundle) {
        int err = -1;
//        Log.i("PMTaskProxy", "BackEngine callBackRemoteMethod sIsExitSoftware:" + sIsExitSoftware + " callbackId:" + callbackId);
//        if (sIsExitSoftware) {
//            return err;
//        }

        if (inBundle == null) {
            inBundle = new Bundle();
        }
        if (outBundle == null) {
            outBundle = new Bundle();
        }

        try {
            Log.i("PMTaskProxy", "BackEngine callBackRemoteMethod before beginBroadcast:" + " callbackId:" + callbackId);
            int count = mIpcCallbacks.beginBroadcast();
            Log.d(TAG, "BackEngine callBackRemoteMethod(): count=" + count);
//            if (count == 0) {
//                err = MeriErrCode.MERI_ERR_FORE_NOT_START;
//            }

            for (int i = count-1; i >= 0; --i) {
                try {
                    IIpcCallback callBack = mIpcCallbacks.getBroadcastItem(i);
                    if (callBack == null) {
                        Log.w(TAG, "BackEngine invoke fore engine method(" + callbackId + "), - aidl err: null callback!");
                        continue;
                    }
                    Log.d(TAG, "BackEngine onRemoteCallback callback id_hash=" + System.identityHashCode(callBack) + " callbackId:" + callbackId);
                    err = callBack.onIpcCallback(callbackId, inBundle, outBundle);
                    Log.d(TAG, "BackEngine after onIpcCallback callbackId:" + callbackId);
                } catch (DeadObjectException e) {
                    Log.w(TAG, "BackEngine invoke fore engine method(" + callbackId + "), - the call back is dead");
                } catch (RemoteException e) {
                    // 将异常吃掉不让aidl err导致crash
                    err = -4;//MeriErrCode.MERI_ERR_AIDL_ERR;
                    Log.w(TAG, "invoke fore engine method(" + callbackId + "), - aidl err");
                }
            }
        } catch (Exception e) {
            err = -5;//MeriErrCode.MERI_ERR_UNKNOWN;
            Log.w(TAG, "BackEngine invoke fore engine method(" + callbackId + "), - aidl err:" + e.getMessage());
        } finally {
            if (mIpcCallbacks != null) {
                mIpcCallbacks.finishBroadcast();
                Log.w(TAG, "BackEngine callBackRemoteMethod after finishBroadcast" + " callbackId:" + callbackId);
            }
            Log.w(TAG, "BackEngine callBackRemoteMethod finnaly" + " callbackId:" + callbackId);
        }

        return err;
    }
    // -----进程间AIDL相关----end-----------------------------------------------------------------------

    /**
     * 构造函数，由系统调用
     */
    public BackEngine() {
        super();
        sInstance = this;
    }

    public static BackEngine getBackEngine() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        long startTime = 0;
        super.onCreate();
        Log.d(TAG,"onCreate entering...");
//        // 提高进程优先级
//        if (SDKUtil.getSDKVersion() >= SDKUtil.OS_2_0
//                && SDKUtil.getSDKVersion() < SDKUtil.OS_4_3_0) {
//            IServiceForegroundSetter setter = new ServiceForegroundSetterV2(this);
//            setter.setEnable(true);
//        }

//        initAsyncCall();
        // 监听ipc消息
        BackIpcCenter.getInstance().regIpcReceiverEx(mIpcReceiver);
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, BackEngine.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(intent);
    }


    IpcCenter.IIpcReceiverEx mIpcReceiver = new IpcCenter.IIpcReceiverEx() {
        @Override
        public ArrayList<Integer> getRegisterMsgIds() {
            Log.d(TAG,"IpcCenter.IIpcReceiverEx getRegisterMsgIds entering...");
            ArrayList<Integer> msgList = new ArrayList<Integer>();
            msgList.add(IpcCenter.IpcMsg.F2B_HANDLED_BY_BACK_EX);
            msgList.add(IpcCenter.IpcMsg.F2B_TO_LIVE_PLAYER);
//            msgList.add(IpcCenter.IpcMsg.F2B_HANDLED_BY_BACKGROUND_HOST);
            return msgList;
//            return null;
        }

        @Override
        public int onIpcCall(int ipcMsg, Bundle inBundle, Bundle outBundle) {
            switch (ipcMsg) {
                case IpcCenter.IpcMsg.F2B_HANDLED_BY_BACK_EX:
                {
                    Log.d(TAG,"F2B_HANDLED_BY_BACK_EX entering...");
                }
                break;
                default:
                    break;
            }
            return 0;
        }
    };
}
