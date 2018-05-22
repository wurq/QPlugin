package com.arch.plugincore;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wurongqiu on 2018/5/21.
 */

public interface IPluginUIEnv {
    /**
     * 获取插件资源
     */
    public Resources getPluginUIResources();

    /**
     * infalte插件View
     */
    public View inflatePluginUIView(Context context, int resource, ViewGroup root, boolean attachToRoot);

}
