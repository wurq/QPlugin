package com.arch.plugin_a;

import android.os.Bundle;

import com.arch.pluginarc.BasePluginActivity;

/**
 * Created by wurongqiu on 2018/4/17.
 */

public class A_PluginActivity extends BasePluginActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_a);
    }
}
