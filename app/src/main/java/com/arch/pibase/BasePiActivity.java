package com.arch.pibase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.arch.commonconst.CommonCst;
import com.arch.ipccenter.base.ActivityServiceImpl;
import com.arch.plugincore.AbsPlugin;
import com.arch.plugincore.BasePage;
import com.arch.plugincore.IPluginUIEnv;
import com.arch.plugincore.PluginContext;
import com.arch.plugincore.PluginIntent;

/**
 * Created by wurongqiu on 2018/5/18.
 */

public abstract class BasePiActivity extends AppCompatActivity implements IPluginUIEnv {
    static final String TAG = "BasePiActivity";


    /**页面主体视图，用于接收Activity的生命周期回调方法**/
    private BasePage mView;

    private boolean mTouchIntercepted;

    protected static BasePiManager sPiManager;

    protected static ActivityServiceImpl mActivityManager = null;

    /** 当前的插件 */
    protected PluginContext mPiContext = null;

    /** 当前的view */
    protected BasePage mPiView = null;

    /** 当前view的id */
    protected int mPiViewId = -1;

    /** 是否作为插件activity使用 */
    protected boolean mIsUseAsPiActivity = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 强制设置为竖屏

        if (!mIsUseAsPiActivity) {
            super.onCreate(savedInstanceState);
            return;
        }

//        BombStrategy.onUICreate();

//        if (mActivityManager == null) {
//            mActivityManager = (ActivityServiceImpl)
//                    ServiceCenter.getHostUseService(ServiceCenter.ACTIVITY_SERVICE);
//        }

        // 因为intent可能来自不同的插件，所以要设置其bundle的class loader
        Intent intent = getIntent();
        if (intent != null) {
            ClassLoader classLoader = mActivityManager.getPendingClassLoader();
            if (classLoader != null) {
                intent.setExtrasClassLoader(classLoader);
            }
            mPiViewId = intent.getIntExtra(CommonCst.ViewIdKey, -1);

            // 实现singleTop
            int launchMode = intent.getIntExtra(PluginIntent.LaunchModeKey, 0);
            if (launchMode == PluginIntent.MODE_ACTIVITY_SINGLE_TOP) {
                Log.i(TAG, "cur view id: " + mActivityManager.getCurrentPiViewId() + ", new view id: " + mPiViewId);
                if (mPiViewId == mActivityManager.getCurrentPiViewId()) {
                    mPiViewId = 0;
                    super.onCreate(savedInstanceState);
                    this.finish();
                    return;
                }
            }

//            mActivityManager.addPiActivity(mPiViewId, this);
//            mActivityManager.setCurrentPiActivity(this);
//
//            if (sPiManager == null) {
//                sPiManager = ForePiManager.getPiManager();
//            }
        }
        super.onCreate(savedInstanceState);

    }


    private  void createView(Bundle savedInstanceState) {
        mView = createView();
        if (mView == null) {
            return;
        }
        if(isFinishing()){
            return;
        }
        mView.frameInit();
        mView.onCreate(savedInstanceState);

        if(isFinishing()){
            return;
        }
//        mView.mTemplate.initByActivity(this);
//        setContentView(mView.mTemplate.getPageView(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

    }




    /**
     * 获取具体的实现页面
     *
     * @return
     */

    public BasePage createView() {
        if (!mIsUseAsPiActivity) {
            return null;
        }
        Log.d(TAG, "QuickLoadActivity createView 1");

        // 从高位还原出插件id
        int piId = (mPiViewId >>> CommonCst.CONST_ID_OFFSET);
        if (mPiViewId < 0 || piId <= 0 || piId == 65535) {
            String msg = "pi[" + piId + "] not found - view[" + (mPiViewId & 0x0000ffff) + "]";
            Log.e(TAG, msg);
//            QDebugToast.ShowShortToast(BasePiApplication.getAppContext(), msg);
            return null;
        }

//        // 通过id找到插件
//        Log.d("LongBoard", "QuickLoadActivity getPiEntity start");
//        mPiContext = sPiManager.getPiEntity(piId);
//        Log.d("LongBoard", "QuickLoadActivity getPiEntity end");

//        mPiContext =
        if (mPiContext != null) {
            mActivityManager.setCurrentPiContext(mPiContext);

            AbsPlugin pi = (AbsPlugin) mPiContext.getPiInstance();
            if (pi != null) {
                Log.d("LongBoard", "QuickLoadActivity getActivityView start");
                mPiView = pi.getActivityView(mPiViewId, this);
                Log.d("LongBoard", "QuickLoadActivity getActivityView");
            }
            if (mPiView == null) {
                Log.e(TAG, "pi[" + piId + "]'s view[" + (mPiViewId & 0x0000ffff) + "] not found");
//                QDebugToast.ShowShortToast(BasePiApplication.getAppContext(),
//                        "pi[" + piId + "]'s view[" + (mPiViewId & 0x0000ffff) + "] not found");
            } else {
                Log.i(TAG, "createPiView: " + mPiView.getClass().getName());
            }
        } else {
            String msg = "pi[" + piId + "] not found - view[" + (mPiViewId & 0x0000ffff) + "]";
            Log.e(TAG, msg);
//            QDebugToast.ShowShortToast(BasePiApplication.getAppContext(), msg);
        }

        return mPiView;
    }

    @Override
    protected void onStop() {
        if (!mIsUseAsPiActivity) {
            super.onStop();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (!mIsUseAsPiActivity) {
            super.onStop();
        }
        super.onDestroy();
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

}
