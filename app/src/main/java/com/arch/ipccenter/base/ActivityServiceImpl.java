package com.arch.ipccenter.base;

//import ActivityService;

import android.app.Activity;

import com.arch.plugincore.ActivityService;
import com.arch.plugincore.BasePage;
import com.arch.plugincore.PluginContext;

import java.util.LinkedHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by wurongqiu on 2018/5/16.
 */

public class ActivityServiceImpl implements ActivityService {

    static final String TAG = "ActivityServiceImpl";

    static ReentrantReadWriteLock mRWLock = new ReentrantReadWriteLock();

    /** 程序Activity栈 */
    LinkedHashMap<Long, Activity> mPiActivityStack = new LinkedHashMap<Long, Activity>();

    /** 当前运行的activity */
    Activity mCurrentPiActivity;

    /** 当前显示的页面 */
    BasePage mCurrentPiView;

    /** 当前显示的页面id */
    int mCurrentPiViewId;

    /** 当前运行的插件的PluginContext */
    PluginContext mCurrentPiContext;

    /** 当前处理的插件的class loader */
    static ClassLoader mPendingClassLoader = null;

    static ActivityServiceImpl mInstance = null;

    public static ActivityServiceImpl getInstance() {
        if (mInstance == null) {
            synchronized (ActivityServiceImpl.class) {
                if (mInstance == null) {
                    mInstance = new ActivityServiceImpl();
                }
            }
        }
        return mInstance;
    }

    ActivityServiceImpl() {
    }


    /**
     * 设置当前插件的上下文。
     */
    public void setCurrentPiContext(PluginContext iContext) {
        if (iContext == null) {
            mCurrentPiContext = null;
        }
        else {
            if (mCurrentPiContext == null) {
                mCurrentPiContext = new PluginContext();
            }
            mCurrentPiContext.mPiId = iContext.mPiId;
            mCurrentPiContext.mPiVer = iContext.mPiVer;
            mCurrentPiContext.setPluginPackage(iContext.getPluginPackage());
        }
    }

    /**
     * 设置当前插件的class loader。
     */
    public void setPendingClassLoader(ClassLoader classLoader) {
        mPendingClassLoader = classLoader;
    }

    /**
     * 获取当前插件的class loader。
     */
    public ClassLoader getPendingClassLoader() {
        return mPendingClassLoader;
    }

    /**
     * 设置当前插件view。
     */
    public void setCurrentPiView(int viewId, BasePage view) {
        mCurrentPiViewId = viewId;
        mCurrentPiView = view;
    }

    @Override
    public PluginContext getCurrentPiContext() {
        return mCurrentPiContext;
    }

    @Override
    public BasePage getCurrentPiView() {
        return null;
    }

    @Override
    public int getCurrentPiViewId() {
        return 0;
    }

    @Override
    public Activity getCurrentPiActivity() {
        return null;
    }

    @Override
    public boolean hasPiActivity() {
        return false;
    }

    @Override
    public Activity getPiActivity(int piViewId) {
        return null;
    }
}
