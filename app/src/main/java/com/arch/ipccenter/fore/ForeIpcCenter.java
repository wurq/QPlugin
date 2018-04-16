package com.arch.ipccenter.fore;

import android.os.Bundle;
import android.util.Log;

import com.arch.ipccenter.base.IpcCenter;

/**
 * Created by wurongqiu on 2018/3/30.
 */

public class ForeIpcCenter extends IpcCenter{
    static ForeIpcCenter sInstance = null;

    public int mF2BSyncIpcSendCount = 0;
    public int mB2FSyncIpcReceiveCount = 0;
    public int mLastForePid = 0;

    public static ForeIpcCenter getInstance() {
        if (sInstance == null) {
            synchronized (IpcCenter.class) {
                if (sInstance == null) {
                    sInstance = new ForeIpcCenter();
                }
            }
        }
        return sInstance;
    }


    @Override
    public int ipcCall(int ipcMsg, Bundle inBundle, Bundle outBundle) {
        int err;
        if (ForeEngine.getInstance() != null) {
            err = ForeEngine.getInstance().ipcCallBackEngine(ipcMsg, inBundle, outBundle);
            if (mLastForePid > 0 && ForeEngine.getInstance().mIsBackConnected) { // 只算丢包率，不算没连接上的情况
                if (ipcMsg > 0 && ipcMsg < IpcMsg.B2F_IPC_MSG_BEGIN) {
                    mF2BSyncIpcSendCount++;
                    Log.i("F_IPC_COUNT", "F2B SyncIpc SendCount: " + mF2BSyncIpcSendCount);
                    Log.d("F_IPC_COUNT", "F2B SyncIpc IpcMsg(send): " + ipcMsg + ", ret: " + err);
                }
            }
        } else {
//            err = MeriErrCode.MERI_ERR_NOT_FOUND;
            err = -1;
        }
        return err;
//        return 0;
    }

    @Override
    public int asyncIpcCall(int ipcMsg, Bundle inBundle) {
        return 0;
    }
}
