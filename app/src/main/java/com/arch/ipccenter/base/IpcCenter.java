package com.arch.ipccenter.base;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wurongqiu on 2018/3/30.
 */

public abstract class IpcCenter {
    private static final String TAG = "IpcCenter";
    /**
     * 前后台通信消息
     */
    public static final class IpcMsg {
        //----前台到后台, 由fore发送到back端的消息汇总-----------
        public static final int F2B_IPC_MSG_BEGIN							= 0x0000;
        public static final int F2B_HANDLED_BY_BACK_EX             		 	= F2B_IPC_MSG_BEGIN + 0x01; //由后台处理request ex
        public static final int F2B_TO_LIVE_PLAYER                          = F2B_IPC_MSG_BEGIN + 0x02; //进入直播间
//        public static final int F2B_STOP_BACK_ENGINE					 	= F2B_IPC_MSG_BEGIN + 0x02;	//停止后台进程
//        public static final int F2B_NOTIFY_BACK_START_LOAD 				 	= F2B_IPC_MSG_BEGIN + 0x03; //开始加载后台插件
//        public static final int F2B_NOTIFY_BACK_ENGINE_GC					= F2B_IPC_MSG_BEGIN + 0x04;	//通知后台gc

        //----后台到前台--------
        public static final int B2F_IPC_MSG_BEGIN							= 0x1000;
        public static final int B2F_INIT_FOR_FIRST_RUN	                	= B2F_IPC_MSG_BEGIN + 0x01;
        public static final int B2F_PLAY_DONE_RUN		                    = B2F_IPC_MSG_BEGIN + 0x02;
        public static final int B2F_LIVE_OVER		                        = B2F_IPC_MSG_BEGIN + 0x03;

        //----前台到后台（异步，通过startService完成）--------
        public static final int F2B_ASYNC_IPC_MSG_BEGIN				= 0x2000;
        public static final int F2B_ASYNC_JUST_START 				= F2B_ASYNC_IPC_MSG_BEGIN + 0x01;
        public static final int F2B_ASYNC_CRASH_REBOOT 				= F2B_ASYNC_IPC_MSG_BEGIN + 0x03;
//        public static final int F2B_ASYNC_CHECK_KILLED_REBOOT 		= F2B_ASYNC_IPC_MSG_BEGIN + 0x04;

        public static final int F2B_ASYNC_FORE_ENGINE_READY 		= F2B_ASYNC_IPC_MSG_BEGIN + 0x12;
//        public static final int F2B_ASYNC_SUBSCRIBE_PI_MSG			= F2B_ASYNC_IPC_MSG_BEGIN + 0x13;	//前台向后台注册插件广播

        //----后台到前台（异步，通过startService完成）--------
        public static final int B2F_ASYNC_IPC_MSG_BEGIN				= 0x3000;
        public static final int B2F_ASYNC_INIT_FOR_FIRST_RUN		= B2F_ASYNC_IPC_MSG_BEGIN + 0x01;
        //        public static final int B2F_ASYNC_PLUGIN_FILE_CHANGE 		= B2F_ASYNC_IPC_MSG_BEGIN + 0x02;
        public static final int B2F_ASYNC_CONTINUE_EXIT				= B2F_ASYNC_IPC_MSG_BEGIN + 0x09;
    }


    public static final String IPC_MSG_ID = "msg_id_530";

    public interface IIpcReceiver {
        /**
         * ipc调用回调，同步方法
         * @param ipcMsg ipc消息id
         * @param inBundle 输入数据
         * @param outBundle 输出数据，当ipcMsg为异步消息时，outBundle为null
         * @return 错误码
         */
        public int onIpcCall(final int ipcMsg, Bundle inBundle, Bundle outBundle);
    }

    public interface IIpcReceiverEx extends IIpcReceiver {
        public ArrayList<Integer> getRegisterMsgIds();
    }

    HashMap<Integer, IIpcReceiver> mIpcExtraReceiverMap = new HashMap<Integer, IIpcReceiver>();

    /**
     * 注册IPC消息监听器
     * @param ipcMsg IPC消息id，请在 IpcMsg.java中定义
     * @param receiver 监听器
     */
    public void regIpcReceiver(int ipcMsg, IIpcReceiver receiver) {
        mIpcExtraReceiverMap.put(ipcMsg, receiver);
    }

    public void regIpcReceiverEx(IIpcReceiverEx receiver) {
        if (receiver != null) {
            ArrayList<Integer> msgIds = receiver.getRegisterMsgIds();
            if (msgIds != null && msgIds.size() > 0) {
                for (int msgId : msgIds) {
                    mIpcExtraReceiverMap.put(msgId, receiver);
                }
            }
        }
    }

    /**
     * 注销IPC消息监听器
     * @param receiver 监听器
     */
    public void unregIpcReceiver(IIpcReceiver receiver) {
        mIpcExtraReceiverMap.remove(receiver);
    }

    /**
     * 发起同步AIDL IPC调用。
     * @param ipcMsg ipc消息id
     * @param inBundle 输入数据
     * @param outBundle 输出数据
     * @return 错误码
     */
    public abstract int ipcCall(int ipcMsg, Bundle inBundle, Bundle outBundle);

    public int onIpcCall(int ipcMsg, Bundle inBundle, Bundle outBundle) {
        Log.d(TAG,"Ipc Center onIpcCall: ipcMsg = " + ipcMsg);
        int err;
        IIpcReceiver receiver = mIpcExtraReceiverMap.get(ipcMsg);
        Log.d(TAG,"Ipc Center onIpcCall: receiver = " );
        if (receiver == null) {
            err = -4;//MeriErrCode.MERI_ERR_INVALID_REQUEST;
        } else {
            err = receiver.onIpcCall(ipcMsg, inBundle, outBundle);
        }
        return err;
    }

    /**
     * 发起异步IPC通信，通过startService完成。
     * @param ipcMsg ipc消息id
     * @param inBundle 输入数据
     * @return 错误码
     */
    public abstract int asyncIpcCall(int ipcMsg, Bundle inBundle);
}
