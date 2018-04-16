package com.arch.base.util.config;


import com.arch.qplugin.BuildConfig;

/**
 * Created by wurongqiu on 17/4/11.
 */

public class ServEnv {

    private static final String TAG = "ServEnv";
    private static final String sBaseUrlDev = "https://ww"; //开发服务器环境 https

    private static final String sBaseUrlTest = "https://www"; //测试服务器 https
    private static final String sBaseUrlTestHttp = "http://www"; //测试服务器 http

    private static final String sBaseUrlPre = "http://www"; //预发布服务器 http
    private static final String sBaseUrlOnline = "https://www"; //线上服务器环境 https

    public static String getAppBaseUrl() {
        if (BuildConfig.FLAVOR.equals("")) {
            return sBaseUrlOnline;
        } else if (BuildConfig.FLAVOR.equals("Pre")) {
            return sBaseUrlPre;
        } else if (BuildConfig.FLAVOR.equals("Dev")) {
            return sBaseUrlDev;
        } else {
            return sBaseUrlOnline;
        }
    }

    private static String sChannel;

    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    public static boolean isNotOnLine() {
        return false;
//        return !BuildConfig.FLAVOR.equals("_360.."); ///
    }

    /**
     * 判断给测试打的release包当前是否是test渠道
     * 判断是否是测试环境，用于切换环境功能是否屏蔽判断
     */
//    public static boolean isCanUseChangeEnv() {
//        return BuildConfig.CAN_CHANGE_SERVER;
//    }
}
