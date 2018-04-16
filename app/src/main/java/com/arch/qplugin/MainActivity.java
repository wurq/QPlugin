package com.arch.qplugin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.arch.ipccenter.base.IpcCenter;
import com.arch.ipccenter.fore.ForeIpcCenter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStartIpcActivity = (Button)findViewById(R.id.start_back_activity);
        btnStartIpcActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForeIpcCenter.getInstance().ipcCall(IpcCenter.IpcMsg.F2B_HANDLED_BY_BACK_EX,null,null);
            }
        });
    }
}
