package com.arch.plugin;


import android.os.Bundle;
import android.os.Handler.Callback;

/**
 * Created by wurongqiu on 2018/5/4.
 */

public abstract class PluginCallback implements Callback {
    protected int mCallbackId;		//回调类ID，在一个进程内作为回调对象的唯一标识
    protected int mType;			//回调类附带数据，类似于Message.what
    protected Bundle mBundle;		//回调类附带数据，类似于Message.data

//    public PluginCallback() {
//        mCallbackId = BasePiApplication.getAllocatedId();
//        mType = 0;
//    }
//
//    public PluginCallback(int type) {
//        mCallbackId = BasePiApplication.getAllocatedId();
//        mType = type;
//    }

    public int getId() {
        return mCallbackId;
    }

    public void setType(int type) {
        mType = type;
    }

    public int getType() {
        return mType;
    }

    public void setBundle(Bundle bundle) {
        mBundle = bundle;
    }

    public Bundle getBundle() {
        return mBundle;
    }
}
