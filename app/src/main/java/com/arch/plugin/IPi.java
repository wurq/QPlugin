package com.arch.plugin;

import android.os.Bundle;

/**
 * Created by wurongqiu on 2018/5/4.
 */

public interface IPi {
    /**
     * 插件安装回调，每次在插件安装后第一次运行有此回调。
     * 注：宿主首次运行时会触发插件安装
     * @param piContext 插件上下文(并不是系统的Context)
     * @param isVirgin 表明插件是否第一次安装，安装过一次之后就不再是v了
     */
    public void onInstall(PluginContext piContext, boolean isVirgin);

    /**
     * 插件实例构造。
     * 插件被加载时由框架调用，只调用一次。
     * 注：建议不要在插件接口内进行耗时操作。
     * @param piContext 插件上下文(并不是系统的Context)
     */
    public void onCreate(PluginContext piContext);

//    /**
//     * 询问插件是否能够从内存中移除。
//     * @return 能否移除的状态
//     */
//    public PiUnloadState onPrepareUnload();

    /**
     * 插件移除时调用，用于清理内存。
     * @throws Exception 如有不清楚,请联系weechen
     */
    public void onDestroy() throws Exception;

    /**
     * 卸载插件时调用。
     * 注：建议只在此回调中进行一些文件清理工作。
     */
    public void onUninstall();

    /**
     * 同步处理外部插件提出的请求。
     * @param srcPiId 提出请求的插件的id
     * @param inBundle 待处理的参数
     * @param outBundle 处理结果
     * @return 错误码
     */
    public int handleOuterRequest(int srcPiId, Bundle inBundle, Bundle outBundle);

    /**
     * callBack 回调函数
     */
    public int handleOuterCallback(int srcPiId, int callbackAction, PluginCallback callback);

    public int handleCallback(int callbackAction, PluginCallback callback);

}
