package com.arch.qplugin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arch.pibase.BasePiController;
import com.arch.plugincore.PluginIntent;
import com.arch.qplugin.PluginAdapter.PluginItem;
import com.arch.utils.HostUtils;
import com.arch.utils.PluginUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "host-MainActivity";

    private PluginAdapter mPluginAdapter;
    private ArrayList<PluginItem> mPluginItems = new ArrayList<PluginItem>();

    private RecyclerView mRecyclerView;
    private TextView mNoPluginTextView;

    private static class ViewHolder {
        public ImageView appIcon;
        public TextView appName;
        public TextView apkName;
        public TextView packageName;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initApk();
        initData();

//        Button btnStartIpcActivity = (Button)findViewById(R.id.start_back_activity);
//        btnStartIpcActivity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ForeIpcCenter.getInstance().ipcCall(IpcCenter.IpcMsg.F2B_HANDLED_BY_BACK_EX,null,null);
//            }
//        });
    }



    private void initApk()  {
        try {
            String[] targetTestString = this.getAssets().list("");
            for(int i = 0; i < targetTestString.length;i++) {
                String path = targetTestString[i];
                Log.d(TAG, "getResources().getAssets()" + path);
                if (path.endsWith("apk")) {
                    HostUtils.copy(path, this);
                }
            }

        } catch (IOException e) {
            Log.d(TAG,"getResources().getAssets() IOException");
            e.printStackTrace();
        }
    }

//    private void initView() {
////        mPluginAdapter = new PluginAdapter();
//        mRecyclerView = (RecyclerView) findViewById(R.id.plugin_list);
//        mNoPluginTextView = (TextView)findViewById(R.id.no_plugin);
//    }

    private void initData() {
        String pluginFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/0";
        Log.d(TAG,"pluginFolder = " + pluginFolder);
        File file = new File(pluginFolder);
        File[] plugins = file.listFiles();
        if (plugins == null || plugins.length == 0) {
            mNoPluginTextView.setVisibility(View.VISIBLE);
            return;
        }

        for (File plugin : plugins) {
            PluginItem item = new PluginItem();
            item.pluginPath = plugin.getAbsolutePath();
            item.packageInfo = PluginUtils.getPackageInfo(this, item.pluginPath);
            mPluginItems.add(item);
//            PluginManager.getInstance(this).loadApk(item.pluginPath);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.plugin_list);
        mNoPluginTextView = (TextView)findViewById(R.id.no_plugin);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mPluginAdapter = new PluginAdapter(MainActivity.this,mPluginItems);
        mRecyclerView.setAdapter(mPluginAdapter);
        mPluginAdapter.setOnItemClickListener(new PluginAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view , int position){
                Log.d(TAG,"msg ++++++++ view: " + view.toString() + "pn : " + position);
                PluginIntent pluginIntent = new PluginIntent();
                pluginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pluginIntent.setLaunchMode(PluginIntent.MODE_ACTIVITY_SINGLE_TASK);

                BasePiController.getInstance().startActivityForResult(pluginIntent,0);
//                BasePiManager.getPiManager()
            }
        });


        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
//        mRecyclerView.set
//        mListView.setAdapter(mPluginAdapter);
//        mRecyclerView.setOnItemClickListener(this);
//        mPluginAdapter.notifyDataSetChanged();
    }

}
