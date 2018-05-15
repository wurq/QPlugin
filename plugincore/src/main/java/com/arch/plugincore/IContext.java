package com.arch.plugincore;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wurongqiu on 2018/5/10.
 */

public interface IContext {
    public interface ILayoutInflater {

        /**解释XML布局文件
         * @param resource 布局文件ID
         * @param root 指定生成视图的父视图(未使用)
         * @param attachToRoot (未使用)
         * @return View
         */
        View inflate(Context context, int resource, ViewGroup root, boolean attachToRoot);

        /**解释XML布局文件
         * @param resource 布局文件ID
         * @param root 指定生成视图的父视图
         * @return View 生成视图
         */
        View inflate(Context context, int resource, ViewGroup root);
    }

    /**
     * 获取资源读取器
     * @return 资源读取器
     */
    Resources getResources();

    /**
     * 获取自定义格式读取器
     * @return 自定义格式读取器
     */
    AssetManager getAssertManager();

    /**
     * 获取布局文件解释器
     * @return 布局文件解释器
     */
    ILayoutInflater getLayoutInflater();

    /**
     * 获取class loader
     * @return
     */
    ClassLoader getClassLoader();
}
