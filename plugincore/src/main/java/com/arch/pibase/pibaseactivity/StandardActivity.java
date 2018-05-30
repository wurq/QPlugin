package com.arch.pibase.pibaseactivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.arch.pibase.BasePiActivity;
import com.arch.plugincore.BasePage;

public class StandardActivity extends BasePiActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        //因为intent可能来自不同的插件，所以要设置其bundle的class loader
        Intent intent = getIntent();
        ClassLoader classLoader = mActivityManager.getPendingClassLoader();
        if (classLoader != null) {
            intent.setExtrasClassLoader(classLoader);
        }

        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_stantard);
    }

    @Override
    public BasePage createView() {
        return null;
    }

    @Override
    public Resources getPluginUIResources() {
        return null;
    }

    @Override
    public View inflatePluginUIView(Context context, int resource, ViewGroup root, boolean attachToRoot) {
        return null;
    }


    @Override
    protected void onStop() {
        if (!mIsUseAsPiActivity) {
            super.onStop();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (!mIsUseAsPiActivity) {
            super.onStop();
        }
        super.onDestroy();
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

}
