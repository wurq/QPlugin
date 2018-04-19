package com.arch.pluginarc;

import android.content.Intent;
import android.os.Parcelable;

import com.arch.util.Configs;

import java.io.Serializable;

/**
 * Created by wurongqiu on 2018/4/16.
 */

public class PluginIntent extends Intent {



    private String mPluginPackage;
    private String mPluginClass;

    public PluginIntent() {
        super();
    }

    public PluginIntent(String pluginPackage) {
        super();
        this.mPluginPackage = pluginPackage;
    }

    public PluginIntent(String pluginPackage, String pluginClass) {
        super();
        this.mPluginPackage = pluginPackage;
        this.mPluginClass = pluginClass;
    }

    public PluginIntent(String pluginPackage, Class<?> clazz) {
        super();
        this.mPluginPackage = pluginPackage;
        this.mPluginClass = clazz.getName();
    }

    public String getPluginPackage() {
        return mPluginPackage;
    }

    public void setPluginPackage(String pluginPackage) {
        this.mPluginPackage = pluginPackage;
    }

    public String getPluginClass() {
        return mPluginClass;
    }

    public void setPluginClass(String pluginClass) {
        this.mPluginClass = pluginClass;
    }

    public void setPluginClass(Class<?> clazz) {
        this.mPluginClass = clazz.getName();
    }

    @Override
    public Intent putExtra(String name, Parcelable value) {
        setupExtraClassLoader(value);
        return super.putExtra(name, value);
    }

    @Override
    public Intent putExtra(String name, Serializable value) {
        setupExtraClassLoader(value);
        return super.putExtra(name, value);
    }

    private void setupExtraClassLoader(Object value) {
        ClassLoader pluginLoader = value.getClass().getClassLoader();
        Configs.sPluginClassloader = pluginLoader;
        setExtrasClassLoader(pluginLoader);
    }

}

