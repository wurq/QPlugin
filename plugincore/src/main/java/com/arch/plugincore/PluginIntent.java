package com.arch.plugincore;

import android.content.Intent;

import com.arch.commonconst.CommonCst;

/**
 * Created by wurongqiu on 2018/5/10.
 */

public class PluginIntent extends Intent {

    public static final String LaunchModeKey 	= "lcmd"; //页面启动模式的key

    /**
     * 默认普通模式
     */
    public static final int MODE_ACTIVITY_STANDARD 			= 0x00;

    /**
     * singleTop模式
     */
    public static final int MODE_ACTIVITY_SINGLE_TOP 		= 0x01;

    /**
     * singleTask模式
     */
    public static final int MODE_ACTIVITY_SINGLE_TASK 		= 0x02;

    /**
     * singleInstance模式
     */
    public static final int MODE_ACTIVITY_SINGLE_INSTANCE 	= 0x03;

    /**
     * sms页面专用singleInstance模式
     */
    public static final int MODE_SMS_SINGLE_INSTANCE 	= 0x04;

    /**
     * 类通知栏跳转方式(使用已有实例)
     */
    public static final int NOTIFICATION_USE_HISTORY = 0x10;
    /**
     * 类通知栏跳转方式(使用新实例)
     */
    public static final int NOTIFICATION_BRAND_NEW	 = 0x11;

    public PluginIntent(int piViewId) {
        super();
        putExtra(CommonCst.ViewIdKey, piViewId);
    }

    public PluginIntent() {
        super();
    }

    public PluginIntent(Intent intent) {
        super(intent);
    }

    /**
     * 设置activity的启动模式。
     * @param mode 请参看{@link #MODE_ACTIVITY_STANDARD},
     * 				{@link #MODE_ACTIVITY_SINGLE_TOP},
     *				{@link #MODE_ACTIVITY_SINGLE_TASK},
     * 			   	{@link #MODE_ACTIVITY_SINGLE_INSTANCE},
     * 			        默认可不设置
     */
    public void setLaunchMode(int mode) {
        putExtra(LaunchModeKey, mode);
    }
}
