package com.arch.commonconst;

/**
 * Created by wurongqiu on 2018/5/16.
 */

public final class CommonCst {
    /**
     * function id偏移量，eg:
     * MainPageConst.BASE_FUNCTION_ID = PluginIdConst.MAIN_PAGE_PLUGIN << CommonConst.CONST_ID_OFFSET;
     */
    public static final int CONST_ID_OFFSET = 16;


    /**
     * 插件间通讯常量
     */
    public static final String ToClassKey 		= "pluginId";
    public static final String TodoKey 			= "todo";
    public static final String RetKey2 			= "ret2";
    public static final String ViewIdKey 		= "pivid"; //插件页面id的Key,用于界面跳转
    public static final String PhoneNum			= "phn";
    public static final String CpStateKey		= "cps";	//ContentProvider状态
    public static final String FlagKey 			= "flag";
    public static final String RawIntentKey		= "rawit";

    public static final String PRE_VIEW_TAG = "prevt";
    public static final int PRE_VIEW_GUIDE_VIEW = 1;
}
