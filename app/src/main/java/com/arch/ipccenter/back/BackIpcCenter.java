package com.arch.ipccenter.back;

import android.os.Bundle;
import android.util.Log;

import com.arch.ipccenter.base.IpcCenter;

/**
 * Created by wurongqiu on 2018/3/30.
 */

public class BackIpcCenter extends IpcCenter{
    private static final String TAG = "BackIpcCenter";

    static BackIpcCenter sInstance = null;

    public static BackIpcCenter getInstance() {
        if (sInstance == null) {
            synchronized (IpcCenter.class) {
                if (sInstance == null) {
                    sInstance = new BackIpcCenter();
                }
            }
        }
        return sInstance;
    }

    BackIpcCenter() {

    }

    @Override
    public int ipcCall(int ipcMsg, Bundle inBundle, Bundle outBundle) {
        int err = -1;
        if (BackEngine.getBackEngine() != null) {
            Log.i("PMTaskProxy", "BackIpcCenter before ipcCall callBackRemoteMethod ipcMsg:" + ipcMsg);
            err = BackEngine.getBackEngine().callBackRemoteMethod(ipcMsg, inBundle, outBundle);
//            Log.i("PMTaskProxy", "BackIpcCenter after ipcCall callBackRemoteMethod ipcMsg:" + ipcMsg);
//
//            if (mLastForePid > 0) {
//                if (ipcMsg > 0 && ipcMsg < IpcMsg.F2B_ASYNC_IPC_MSG_BEGIN) {
//                    if (err != MeriErrCode.MERI_ERR_FORE_NOT_START && !(BackEngine.getBackEngine().sIsExitSoftware)) {
//                        mB2FSyncIpcSendCount++;
//                        Log.i("B_IPC_COUNT", "[BACK] B2F SyncIpc SendCount: " + mB2FSyncIpcSendCount);
//                    }
//                    Log.d("B_IPC_COUNT", "B2F SyncIpc IpcMsg(send): " + ipcMsg + ", err: " + err);
//                }
//            }
//        } else {
//            Log.i("PMTaskProxy", "ipcCall error");
//            err = MeriErrCode.MERI_ERR_INVALID_REQUEST;
        }
        return err;
    }

    @Override
    public int asyncIpcCall(int ipcMsg, Bundle inBundle) {
        return 0;
    }
}
