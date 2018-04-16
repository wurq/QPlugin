package com.arch.ipccenter.fore;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.arch.application.CrashHandle;

public class ForeService extends Service {
    static final String TAG = "ForeService";

    public static final String ACTION_NAME = "com.arch.ipccenter.fore.ForeService";

    static final int HANDLE_ASYNC_IPC_CALL		= 0x01;
    static final int TRY_TO_CLOSE_FORE_SERVICE 	= 0x02;
    static final int HANDLE_BACK_PLUGIN_CALL 	= 0x03;
    static final int MSG_NOT_ENOUGH_STORAGE 	= 0x09;
    static final int MSG_WAIT_FOR_CONNECTED		= 0x0A;
    public static boolean sIsServiceOn = false;
    static ForeService sInstance;

    @Override
    public void onCreate() {
        long startTime = 0;

        super.onCreate();
        sInstance = this;
        sIsServiceOn = true;
        Log.d(TAG, "ForeService onCreate");


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d(TAG,"onStartCommand entering...");

        Message msg = Message.obtain();
        msg.what = HANDLE_ASYNC_IPC_CALL;
        msg.obj = intent;
        mHandler.sendMessage(msg);

        CrashHandle.getInstance().init(this);

        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLE_ASYNC_IPC_CALL: {
                    Intent intent = (Intent) msg.obj;
                    handleAsyncCall(intent);
                }
                break;
                default:
                    break;
            }
        }
            
    };

    private void handleAsyncCall(Intent intent) {
    }
}
