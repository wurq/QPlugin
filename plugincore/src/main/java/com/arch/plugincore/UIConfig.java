package com.arch.plugincore;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by wurongqiu on 2018/5/21.
 */

public class UIConfig {

    /** 系统资源属性定义命名空间 **/
    public static final String ANDROID_ATTRIBUTE_NAME_SPACE = "http://schemas.android.com/apk/res/android";

    /** 管家宿主资源属性定义命名空间 **/
    public static final String HOST_ATTRIBUTE_NAME_SPACE = "com.tencent.qqpimsecure";

    /** 管家资源包名 **/
    public static final String RES_PKG_NAME = "com.tencent.qqpimsecure";

    /** UI库内部定义的View附带的key **/
    public static final int UILIB_INTERNAL_KEY = Integer.MAX_VALUE;

    /** UI库内部定义的View附带的tag **/
    public static final String UILIB_INTERNAL_TAG = "uilib.internal.tag";

    /** 宿主环境 **/
    private static Context mUILibContext;

    /**
     * 屏幕宽度
     */
    public static int canvasWidth;

    /**
     * 屏幕高度
     */
    public static int canvasHeight;

//    /**
//     * UI环境桥接器
//     */
//    private static IUIEnvBridge mUIEnvBridge;

    /**
     * 当前是否处于测试模式。
     */
    private static boolean mIsDebuggable;

//    /**
//     * 初始化UI库
//     */
//    public static void initUILib(IUIEnvBridge uiEnvBridge, boolean isDebuggable) {
//        initUILib(uiEnvBridge);
//        mIsDebuggable = isDebuggable;
//    }
//
//    /**
//     * 初始化UI库
//     */
//    public static void initUILib(IUIEnvBridge uiEnvBridge) {
//        mUIEnvBridge = uiEnvBridge;
//        mUILibContext = mUIEnvBridge.getUILibContext();
//        initCanvas(mUILibContext);
//    }

    /**
     * 初始化屏幕宽高
     */
    private static void initCanvas(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        canvasWidth = wm.getDefaultDisplay().getWidth();
        canvasHeight = wm.getDefaultDisplay().getHeight();

        int width = canvasWidth;
        if (width > canvasHeight) {
            canvasWidth = canvasHeight;
            canvasHeight = width;
        }
    }

    /**
     * 当前是否处于测试模式。
     */
    public static boolean isDebuggable() {
        return mIsDebuggable;
    }

    /**
     * 获取UI库相关的Context
     */
    public static Context getUILibContext() {
        return mUILibContext;
    }

    /**
     * 获取外部资源
     */
    public static Resources getResources(Context context) {
        if (context instanceof IPluginUIEnv) {
            return ((IPluginUIEnv) context).getPluginUIResources();
        } else {
            return context.getResources();
        }
    }

    /**
     * 获取外部定义的字符串资源
     */
    public static String getString(Context context, int id) {
        if (context instanceof IPluginUIEnv) {
            return ((IPluginUIEnv) context).getPluginUIResources()
                    .getString(id);
        } else {
            return context.getResources().getString(id);
        }
    }

    /**
     * 获取外部定义的颜色资源
     */
    public static int getColor(Context context, int id) {
        if (context instanceof IPluginUIEnv) {
            return ((IPluginUIEnv) context).getPluginUIResources().getColor(id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    /**
     * 获取外部定义的Drawable资源
     */
    public static Drawable getDrawable(Context context, int id) {
        return context.getResources().getDrawable(id);

//		if (context instanceof IPluginUIEnv) {
//			return ((IPluginUIEnv) context).getPluginUIResources().getDrawable(
//					id);
//		} else {
//			return context.getResources().getDrawable(id);
//		}
    }

    /**
     * inflate外部定义的资源
     */
    public static View inflate(Context context, int resource, ViewGroup root,
                               boolean attachToRoot) {
        if (context instanceof IPluginUIEnv) {
            return ((IPluginUIEnv) context).inflatePluginUIView(context,
                    resource, root, attachToRoot);
        } else {
            return LayoutInflater.from(context).inflate(resource, root,
                    attachToRoot);
        }
    }

    /**
     * inflate外部定义的资源
     */
    public static View inflate(Context context, int resource, ViewGroup root) {
        return inflate(context, resource, root, root != null);
    }

    /**
     * 获取UI库资源
     */
    public static Resources getUILibResources(Context context) {
        return mUILibContext.getResources();
    }

    /**
     * 获取UI库定义的字符串资源
     */
    public static String getUILibString(Context context, int id) {
        return mUILibContext.getString(id);
    }

    /**
     * 获取UI库定义的颜色资源
     */
    public static int getUILibColor(Context context, int id) {
        return mUILibContext.getResources().getColor(id);
    }

    /**
     * 获取UI库定义的Drawable资源
     */
    public static Drawable getUILibDrawable(Context context, int id) {
        return mUILibContext.getResources().getDrawable(id);
    }

//	/**
//	 * 释放UI库图片资源
//	 */
//	public static void destroyUILibDrawable(){
//		if (mUILibContext instanceof IDrawableHolderOwner){
//			((IDrawableHolderOwner)mUILibContext).destroyHolder();
////			System.gc();
//		}
//	}

    /**
     * 利用UILib定义的资源setTextAppearance
     */
    public static void setTextAppearanceByUILib(Context context,
                                                TextView textView, int resid) {
        textView.setTextAppearance(mUILibContext, resid);
    }

    /**
     * inflate UI库定义的资源
     */
    public static View inflateUILibResource(int resource, ViewGroup root,
                                            boolean attachToRoot) {
        View v = LayoutInflater.from(mUILibContext).inflate(resource, root,
                attachToRoot);
//        initUILibInternalTag(v);
        return v;
    }

    /**
     * inflate UI库定义的资源
     */
    public static View inflateUILibResource(int resource, ViewGroup root) {
        return inflateUILibResource(resource, root, root != null);
    }

    /**
     * 判断是否为UI库资源
     */
    public static boolean isUILibView(Object view) {
        if (view == null) {
            return false;
        }

        boolean inUILib = view.getClass().getPackage().getName()
                .startsWith("uilib.");
        return inUILib;
    }

    /**
     * 指定使用宿主定义的资源来设置背景
     *
     * @param view
     * @param backgroundResourceId
     */
    public static void setBackgroundUseUILibRes(View view,
                                                int backgroundResourceId) {
        view.setBackgroundDrawable(getUILibDrawable(view.getContext(),
                backgroundResourceId));
    }

//    /**
//     * 运行线程任务
//     */
//    public static void runThreadTask(Runnable runnable, String taskName) {
//        mUIEnvBridge.runThreadTask(runnable, taskName);
//    }
//
//    /**
//     * 通知页面创建完成
//     */
//    public static void notifyPageCreate(String pageClassName, long elapsedTime) {
//        mUIEnvBridge.notifyPageCreate(pageClassName, elapsedTime);
//    }

//    /**
//     * 默认的UI桥接器实现
//     *
//     */
//    public static class DefaultUIEnvBridge implements IUIEnvBridge{
//
//        private Context mUILibContext;
//
//        public DefaultUIEnvBridge(Context uilibContext) {
//            mUILibContext = uilibContext;
//        }
//
//        @Override
//        public Context getUILibContext() {
//            return mUILibContext;
//        }
//
//        @Override
//        public void runThreadTask(Runnable runnable, String taskName) {
//            Thread thread = new Thread(runnable);
//            thread.start();
//        }
//
//        @Override
//        public void notifyPageCreate(String pageClassName, long elapsedTime) {
//        }
//
//    }
//
//    /**
//     * 初始化宿主View及其子View的tag标志
//     * 只供UI库内部使用,递归实现
//     * @hide
//     */
//    public static void initUILibInternalTag(View v){
//        if (v == null){
//            return;
//        }
//        v.setTag(UILIB_INTERNAL_KEY, UILIB_INTERNAL_TAG);
//        if (v instanceof ViewGroup){
//            ViewGroup parent = (ViewGroup) v;
//            View child;
//            int count = parent.getChildCount();
//            for(int index = 0; index < count; index++){
//                child = parent.getChildAt(index);
//                if (child != null){
//                    initUILibInternalTag(child);
//                }
//            }
//        }
//    }
}
