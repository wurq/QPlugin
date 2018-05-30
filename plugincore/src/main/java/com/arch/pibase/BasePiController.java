package com.arch.pibase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.arch.base.ActivityServiceImpl;
import com.arch.pibase.pibaseactivity.StandardActivity;
import com.arch.plugincore.PluginContext;
import com.arch.plugincore.PluginIntent;

import java.util.ArrayList;
import java.util.List;

//import com.arch.ipccenter.base.ActivityServiceImpl;
//import com.arch.plugin.ProxyActivity;
//import com.arch.plugin.StandardActivity;


/**
 * Created by wurongqiu on 2018/5/10.
 *
 * 插件启动类，启动插件中的四大组件，暂时只实现activity 模式，其余待加入 by wu
 */

public class BasePiController {
    protected static String TAG = "BasePiController";
    private static BasePiController _instance;

    ActivityServiceImpl mActivityManager;

    Context mContext;

    private BasePiController () {
        Log.i(TAG,"BasePiController private Constructor ...");
        mContext = BasePiApplication.getAppContext();
    }

    public static BasePiController getInstance ( ) {
        if(_instance == null) {
            synchronized (BasePiController.class) {
                if(_instance == null) {
                    _instance = new BasePiController();
                }
            }
        }
        return _instance;
    }

    public final void startActivityForResult(PluginIntent pluginIntent)  {
        Class<?> standardCls = null;
        standardCls = StandardActivity.class;

        pluginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        pluginIntent.setClass(mContext, standardCls);

        BasePiApplication.getAppContext().startActivity(pluginIntent);

    }


    public final void startActivityForResult(PluginIntent pluginIntent, int requestCode) {
        Class<?> taskCls = null;
        List<? super Number> list = new ArrayList<Number>();
        taskCls = StandardActivity.class;
        if(null != taskCls)
        {
            pluginIntent.setClass(BasePiApplication.getAppContext(), taskCls);
            pluginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | pluginIntent.getFlags());
            try {
                BasePiApplication.getAppContext().startActivity(pluginIntent);
            } catch (Exception e) {
                Log.e(TAG, "startActivity @ 2, err: " + e.getMessage());
            }
            Log.i(TAG, "startActivity @ 2： " + taskCls);
            return;
        }
    }


    public final void startActivityForResult(Intent intent, int requestCode, boolean outer) {

//        ensureActivityService();
        // 跳到软件外部
        if (outer) {
            try {
//                Activity activity = mActivityManager.getCurrentPiActivity();
//
//                if (activity == null) {
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.getFlags());
//                    mContext.startActivity(intent);
//                } else if ((activity instanceof BaseActivity) && ((BaseActivity)activity).isRunning()) {
//                    activity.startActivityForResult(intent, requestCode);
//                    Log.i(TAG, "startActivityForResult @ 1.1： " + "outer activity");
//                } else if (!(activity instanceof BaseActivity)) {
//                    activity.startActivityForResult(intent, requestCode);
//                    Log.i(TAG, "startActivityForResult @ 1.2： " + "outer activity");
//                } else {
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.getFlags());
//                    mContext.startActivity(intent);
//                    Log.i(TAG, "startActivity @ 1.3： " + "outer activity");
//                }
//
//                // 触发一下检查栈顶
//                ForeIpcCenter.getInstance().asyncIpcCall(IpcMsg.F2B_ASYNC_START_OUTER_APP, null);

            } catch (Exception e) {
                Log.e(TAG, "startActivityForResult, err: " + e.getMessage(), e);
            }
        }
        // 软件内部跳转
        else {
//            int viewId = intent.getIntExtra(CommonCst.ViewIdKey, -1);
//            //转到临时任务进程的Activity
//            Class<?> taskCls = null;
//            if (viewId == MainPageConst.ViewId.UI_WEB_VIEW) {
//                taskCls = WebViewActivity.class;
//            }else {
//                int useTaskProc = intent.getIntExtra(CommonConst.GALLERY_TASK, 0);
//                if(useTaskProc == 1){
//                    taskCls = ImageGalleryActivity.class;
//                }
//                int interceptSms = intent.getIntExtra(CommonConst.INTERCEPT_SMS, 0);
//                if (interceptSms == 1) {
//                    taskCls = InterceptWebViewActivity.class;
//                }
            }

            Class<?> taskCls = null;
            taskCls = StandardActivity.class;
            if(null != taskCls)
            {
                intent.setClass(BasePiApplication.getAppContext(), taskCls);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.getFlags());
                try {
                    BasePiApplication.getAppContext().startActivity(intent);
                } catch (Exception e) {
                    Log.e(TAG, "startActivity @ 2, err: " + e.getMessage());
                }
                Log.i(TAG, "startActivity @ 2： " + taskCls);
                return;
            }
            Class<?> standardCls = null;
            //短信插件视图特殊处理逻辑，转移到新的堆栈
//            int currentViewId = mActivityManager.getCurrentPiViewId();
//            boolean isMmsPi = ((viewId >>> CommonCst.CONST_ID_OFFSET) == PluginIdConst.PiMms);
//            if (isMmsPi) {
//                standardCls = MmsStandardActivity.class;
//            }else{
//                standardCls = StandardActivity.class;
//            }
//            if (currentViewId > 0) {
//                //已经打开过界面才需要处理
//                boolean isCurMmsView = false;
//                isCurMmsView = ((currentViewId >>> CommonCst.CONST_ID_OFFSET) == PluginIdConst.PiMms);
//                if (isMmsPi != isCurMmsView) {
//                    //处于不同的栈
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                }
//            }

            // 处理单例模式
            int launchMode = intent.getIntExtra(PluginIntent.LaunchModeKey, PluginIntent.MODE_ACTIVITY_STANDARD);

            switch (launchMode) {
                case PluginIntent.MODE_ACTIVITY_SINGLE_INSTANCE: {
                    if (mActivityManager.getCurrentPiActivity() != null) {
                        PluginContext piContext = mActivityManager.getCurrentPiContext();
                        if (piContext != null) {
                            ClassLoader cl = piContext.getClassLoader();
                            if (cl != null) {
                                mActivityManager.setPendingClassLoader(cl);
                            }
                        }
                    }

//                    // 因为宿主只有一个SingleInstance的activity，
//                    // 如果已经启动并装有其他的view，则先把它干掉
//                    mActivityManager.closeSingleIntanceActivityIfExists();
//
//                    intent.setClass(BasePiApplication.getAppContext(), SingleInstanceActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    try {
//                        BasePiApplication.getAppContext().startActivity(intent);
//                    } catch (Exception e) {
//                        Log.e(TAG, "startActivity @ 3, err: " + e.getMessage());
//                    }
//                    Log.i(TAG, "startActivity @ 3： " + "SingleInstanceActivity");
//                    return;
                }

             /*   case PluginIntent.MODE_SMS_SINGLE_INSTANCE: {
                    if (mActivityManager.getCurrentPiActivity() != null) {
                        PluginContext piContext = mActivityManager.getCurrentPiContext();
                        if (piContext != null) {
                            ClassLoader cl = piContext.getClassLoader();
                            if (cl != null) {
                                mActivityManager.setPendingClassLoader(cl);
                            }
                        }
                    }
                    intent.setClass(BasePiApplication.getAppContext(), SingleSmsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    try {
                        BasePiApplication.getAppContext().startActivity(intent);
                    } catch (Exception e) {
                        Log.e(TAG, "startActivity @ 4, err: " + e.getMessage());
                    }
                    Log.i(TAG, "startActivity @ 4： " + "SingleSmsActivity");
                    return;
                }

                case PluginIntent.MODE_ACTIVITY_SINGLE_TASK: {
                    boolean isExist = (mActivityManager.getPiActivity(viewId) != null);
                    if (isExist) {
                        int curViewId = mActivityManager.getCurrentPiViewId();
                        if (curViewId != viewId) {
                            mActivityManager.closeTopPiActivity(viewId);
                        }
                        BasePiActivity activity = (BasePiActivity) mActivityManager.getPiActivity(viewId);
                        if (activity != null) {
                            activity.onNewIntent(intent);

                            // 当前页面和目的页面是同一个页面，需要强行刷新一下
                            boolean needRefresh = false;
                            if (curViewId == viewId) {
                                needRefresh = true;
                            }

                            // 当前页面处于不可见的状态，需要强行刷新一下
                            if (!needRefresh) {
                                BasePage view = mActivityManager.getCurrentPiView();
                                if (view == null || !view.isResume()) {
                                    needRefresh = true;
                                }
                            }

                            if (needRefresh) {
                                // 刷新触发调用onResume
                                ForeEngine.getInstance().bringActivityToFront(isMmsPi);
                            }
                            return;
                        }
                    }
                }
                break;

                case PluginIntent.MODE_ACTIVITY_SINGLE_TOP: {
                    // 当前页面和目的页面是同一个页面
                    int curViewId = mActivityManager.getCurrentPiViewId();
                    if (curViewId == viewId) {
                        BasePiActivity activity = (BasePiActivity) mActivityManager.getPiActivity(viewId);
                        if (activity != null) {
                            activity.onNewIntent(intent);

                            // 当前页面和目的页面是同一个页面，需要强行刷新一下
                            boolean needRefresh = false;
                            if (curViewId == viewId) {
                                needRefresh = true;
                            }

                            // 当前页面处于不可见的状态，需要强行刷新一下
                            if (!needRefresh) {
                                BasePage view = mActivityManager.getCurrentPiView();
                                if (view == null || !view.isResume()) {
                                    needRefresh = true;
                                }
                            }

                            if (needRefresh) {
                                // 刷新触发调用onResume
                                ForeEngine.getInstance().bringActivityToFront(isMmsPi);
                            }
                            return;
                        }
                    }
                }
                break;

//                case PluginIntent.NOTIFICATION_USE_HISTORY:
//                case PluginIntent.NOTIFICATION_BRAND_NEW: {
//                    Context context = BasePiApplication.getAppContext();
//                    if (context != null) {
//                        intent.setClassName(BasePiApplication.getAppContext(), QuickLoadActivity.class.getName());
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//                        intent.putExtra(NtService.IS_FROM_NOTIFICATION, true);
//                        if (launchMode == PluginIntent.NOTIFICATION_USE_HISTORY) {
//                            intent.putExtra(NtServiceBackImpl.KEY_JUMP_TYPE, NtJumpType.TYPE_USE_HISTORY);
//                        } else {
//                            intent.putExtra(NtServiceBackImpl.KEY_JUMP_TYPE, NtJumpType.TYPE_BRAND_NEW);
//                        }
//                        try {
//                            context.startActivity(intent);
//                        } catch (Exception e) {
//                            Log.e(TAG, "startActivity @ 5, err: " + e.getMessage(), e);
//                        }
//                        Log.i(TAG, "startActivity @ 5： " + "QuickLoadActivity");
//                    }
//                    return;
//                }
*/
                // case PluginIntent.MODE_ACTIVITY_STANDARD:
                default: {
                }
                break;
            }

            // 处理 intent 的 flags
            // TODO：目前并不支持所有的flags
            // 已支持：
            // Intent.FLAG_ACTIVITY_CLEAR_TOP
            //
            if ((intent.getFlags() & Intent.FLAG_ACTIVITY_CLEAR_TOP) != 0) {
                // 清空栈中已有的Activity 实例，并且此Activity实例之上的其他Activity实例统统出栈，然后生成此Activity新的实例。

                intent.setFlags(intent.getFlags() - Intent.FLAG_ACTIVITY_CLEAR_TOP); // 清除标志位

                int viewId = 0;///////////////////
                Activity piActivity = mActivityManager.getPiActivity(viewId);
                boolean isExists = (piActivity != null);

                if (isExists) {
                    // 关闭后重建
//                    mActivityManager.closeTopPiActivity(viewId);
//                    mActivityManager.closePiActivity(viewId);

                    mActivityManager.setPendingClassLoader(mActivityManager.getCurrentPiContext().getClassLoader());
                    Activity activity = mActivityManager.getCurrentPiActivity();
                    if (activity != null) {
                        intent.setClass(activity, standardCls);
                        try {
                            activity.startActivityForResult(intent, requestCode);
                        } catch (Exception e) {
                            Log.i(TAG, "startActivityForResult @ 6, err: " + e.getMessage());
                        }
                        Log.i(TAG, "startActivityForResult @ 6： " + standardCls);
                        return;
                    }
                }
            }

      /*      // 处理其他情况的 插件间跳转
            final PluginContext curPiContext = mActivityManager.getCurrentPiContext();
            final Activity curActivity = mActivityManager.getCurrentPiActivity();
            if (curPiContext != null && curActivity != null) {
                // 正常的插件间跳转

                mActivityManager.setPendingClassLoader(curPiContext.getClassLoader());

                intent.setClass(curActivity, standardCls);
                try {
                    curActivity.startActivityForResult(intent, requestCode);
                } catch (Exception e) {
                    Log.e(TAG, "startActivityForResult @ 7, err: " + e.getMessage());
                }
                Log.i(TAG, "startActivityForResult @ 7： " + standardCls);

                boolean needRefresh = false;

                // 当前页面处于不可见的状态，需要强行刷新一下
                if (!needRefresh) {
                    BasePage view = mActivityManager.getCurrentPiView();
                    if (view == null || !view.isResume()) {
                        needRefresh = true;

                        // FIXME 5.1暂时先对病毒弹框做处理
                        boolean specialFlag = intent.getBooleanExtra("virusflag", false);
                        try {
                            if (specialFlag) {
                                Activity ac = mActivityManager.getCurrentDesttopActivity();
                                if (ac != null) {
                                    DeskTopActivity dta = (DeskTopActivity) ac;
                                    if (dta.isResume()) {
                                        needRefresh = false;
                                    }
                                }
                            }
                        } catch (Exception e) {
                            // do nothing
                        }
                    }
                }

                if (needRefresh) {
                    // 刷新触发调用onResume
                    ForeEngine.getInstance().bringActivityToFront(isMmsPi);
                }
            }
            else {
                //当前没有activity，应不属于插件间跳转，为避免错误使用导致失效，转用context跳转
                Context context = BasePiApplication.getAppContext();
                if (context != null) {
                    intent.setClass(BasePiApplication.getAppContext(),
                            isMmsPi ? MmsQuickLoadActivity.class
                                    : QuickLoadActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    try {
                        context.startActivity(intent);
                    } catch (Exception e) { // crash fix： 81305816 by maxxx
                        Log.e(TAG, "startActivity @ 8, err" + e.getMessage());
                    }
                    Log.i(TAG, "startActivity @ 8： " + "QuickLoadActivity");
                }
            }
        }*/
    }

//    private void ensureActivityService() {
//        if (mActivityManager == null) {
//            mActivityManager = (ActivityServiceImpl)
//                    ServiceCenter.getHostUseService(ServiceCenter.ACTIVITY_SERVICE);
//        }
//    }
}
