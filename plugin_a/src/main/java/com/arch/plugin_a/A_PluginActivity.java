package com.arch.plugin_a;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.arch.pluginarc.BasePluginActivity;

/**
 * Created by wurongqiu on 2018/4/17.
 */

public class A_PluginActivity extends BasePluginActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mProxyActivity.setContentView(generateContentView(mProxyActivity));
        setContentView(R.layout.activity_plugin_a);
    }

    private View generateContentView(final Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        layout.setBackgroundColor(Color.parseColor("#F79AB5"));
        Button button = new Button(context);
        button.setText("button");
        layout.addView(button, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "you clicked button",
                        Toast.LENGTH_SHORT).show();
//                startActivityByProxy("com.ryg.dynamicloadclient.TestActivity");
            }
        });
        return layout;
    }
}
