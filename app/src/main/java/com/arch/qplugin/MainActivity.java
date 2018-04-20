package com.arch.qplugin;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.arch.pluginarc.PluginIntent;
import com.arch.pluginarc.PluginManager;
import com.arch.util.PluginUtils;
import com.arch.utils.HostUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String TAG = "host-MainActivity";

    public static final String FROM = "extra.from";
    public static final int FROM_INTERNAL = 0;
    public static final int FROM_EXTERNAL = 1;

    private ArrayList<PluginItem> mPluginItems = new ArrayList<PluginItem>();
    private PluginAdapter mPluginAdapter;

    private ListView mListView;
    private TextView mNoPluginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initApk();
        initView();
        initData();
//        initApkAsync();
    }


    private void initApkAsync()  {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                initApk();
                initData();
            }
        });
        //开启线程
        thread.start();
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
//            String ret = Environment.getExternalStorageDirectory() + "/0" +path;
            // Log.d(TAG,"getResources().getAssets() ret = "+ret);
//            return ret;
            // FileUploadIntentService.startFileUpload(target,path);

        } catch (IOException e) {
            Log.d(TAG,"getResources().getAssets() IOException");
            e.printStackTrace();
        }
    }

    private void initView() {
        mPluginAdapter = new PluginAdapter();
        mListView = (ListView) findViewById(R.id.plugin_list);
        mNoPluginTextView = (TextView)findViewById(R.id.no_plugin);
    }

    private void initData() {
        String pluginFolder = Environment.getExternalStorageDirectory() + "/0";
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
            PluginManager.getInstance(this).loadApk(item.pluginPath);
        }

        mListView.setAdapter(mPluginAdapter);
        mListView.setOnItemClickListener(this);
        mPluginAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                PluginUtils.showDialog(this, getString(R.string.action_about), getString(R.string.introducation));
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class PluginAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public PluginAdapter() {
            mInflater = MainActivity.this.getLayoutInflater();
        }

        @Override
        public int getCount() {
            return mPluginItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mPluginItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.plugin_item, parent, false);
                holder = new ViewHolder();
                holder.appIcon = (ImageView) convertView.findViewById(R.id.app_icon);
                holder.appName = (TextView) convertView.findViewById(R.id.app_name);
                holder.apkName = (TextView) convertView.findViewById(R.id.apk_name);
                holder.packageName = (TextView) convertView.findViewById(R.id.package_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            PluginItem item = mPluginItems.get(position);
            PackageInfo packageInfo = item.packageInfo;
            holder.appIcon.setImageDrawable(PluginUtils.getAppIcon(MainActivity.this, item.pluginPath));
            holder.appName.setText(PluginUtils.getAppLabel(MainActivity.this, item.pluginPath));
            holder.apkName.setText(item.pluginPath.substring(item.pluginPath.lastIndexOf(File.separatorChar) + 1));
            holder.packageName.setText(packageInfo.applicationInfo.packageName);
            return convertView;
        }
    }

    private static class ViewHolder {
        public ImageView appIcon;
        public TextView appName;
        public TextView apkName;
        public TextView packageName;
    }

    public static class PluginItem {
        public PackageInfo packageInfo;
        public String pluginPath;

        public PluginItem() {
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//        try {
//            Class clazz = Class.forName("com.arch.qplugin.MainActivity");
//
//            Log.d(TAG,clazz.getSimpleName());
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

//        Intent intent = new Intent(this, ProxyActivity.class);
//        startActivity(intent);

        PluginItem item = mPluginItems.get(position);
        PluginManager pluginManager = PluginManager.getInstance(this);
        pluginManager.startPluginActivity(this,
                new PluginIntent(item.packageInfo.packageName));

    }


}
