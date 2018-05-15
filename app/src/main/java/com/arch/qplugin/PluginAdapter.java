package com.arch.qplugin;

//import RecyclerView.Adapte;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arch.application.AppProfile;
import com.arch.utils.PluginUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by wurongqiu on 2018/5/11.
 */

public class PluginAdapter extends RecyclerView.Adapter {

    PluginViewHolder mPluginViewHolder;

    protected final Context mContext;
    protected final LayoutInflater mLayoutInflater;

    private ArrayList<PluginItem> mPluginItems = new ArrayList<PluginItem>();

    public PluginAdapter(Context context, ArrayList<PluginItem> mPluginItems) {
//        super();
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mPluginItems = mPluginItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


                 return new PluginViewHolder(LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.plugin_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PluginViewHolder pluginViewHolder = (PluginViewHolder) holder;

        PluginItem item = mPluginItems.get(position);
        PackageInfo packageInfo = item.packageInfo;
        pluginViewHolder.appIcon.setImageDrawable(PluginUtils.getAppIcon(AppProfile.getContext(), item.pluginPath));
        pluginViewHolder.appName.setText(PluginUtils.getAppLabel(AppProfile.getContext(), item.pluginPath));
        pluginViewHolder.apkName.setText(item.pluginPath.substring(item.pluginPath.lastIndexOf(File.separatorChar) + 1));
        pluginViewHolder.packageName.setText(packageInfo.applicationInfo.packageName);
    }

    @Override
    public int getItemCount() {
        return mPluginItems == null ? 0 : mPluginItems.size();
    }

    class PluginViewHolder extends RecyclerView.ViewHolder {

        public ImageView appIcon;
        public TextView appName;
        public TextView apkName;
        public TextView packageName;

        public PluginViewHolder(View itemView) {
            super(itemView);
            appIcon = (ImageView) itemView.findViewById(R.id.app_icon);
            appName = (TextView) itemView.findViewById(R.id.app_name);
            apkName = (TextView) itemView.findViewById(R.id.apk_name);
            packageName = (TextView) itemView.findViewById(R.id.package_name);
        }
    }

    public static class PluginItem {
        public PackageInfo packageInfo;
        public String pluginPath;

        public PluginItem() {
        }
    }
}
