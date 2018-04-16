package com.arch.base.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.arch.base.presenter.BasePresenter;

/**
 * Created by wurongqiu on 2017/4/25.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    //butternife unbinder
    protected T presenter;
    /**
     * 用于监听滑动的view
     */
    protected FrameLayout contentView;
    /**
     * 用于监听inflater view
     */
    protected LayoutInflater layoutInflater;
    //    protected View touchView;
//    protected ViewGroup rootView;
//    protected SystemBarTintManager mTintManager;
    //加载进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatusBarManager();
        layoutInflater = LayoutInflater.from(this);
        initPresenter();
        if (presenter != null) {
            presenter.onCreate();
        }
//        setNavigationBarColor(this);
    }

    //初始化沉浸栏
    private void initStatusBarManager() {
        int color = onInitStatusBarColor();
        if (color != 0) {
//            StatusBarHelper.setStatusBarColor(this, color);
        } else {
//            int resId = onInitStatusBarResource();
//            if (resId != 0) {
//                StatusBarHelper.setStatusBarResource(this, resId);
//            }
        }
    }

//    @ColorRes
//    protected int onInitStatusBarResource() {
//        return R.color.colorPrimary;
//    }

    @ColorInt
    protected int onInitStatusBarColor() {
        return 0;
    }

    /**
     * Inflate a content view for the activity.
     *
     * @param resId ID for an XML layout resource as the content view
     */
    public void setRealContentView(@LayoutRes int resId) {
        getLayoutInflater().inflate(resId, contentView);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @TargetApi(19)
    public void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    protected void disableScreenCapture() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (presenter != null) {
            presenter.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.onStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            //监控/拦截菜单键
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    abstract protected void initPresenter();

    /**
     * 显示TOAST  id
     */
    public void showToast(final int resId) {
        if (resId > 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    ToastUtil.makeShortToast(resId);
                }
            });
        }
    }

    /**
     * 显示TOAST  string
     */
    public void showToast(final String resStr) {
        if (!TextUtils.isEmpty(resStr)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    new ToastUtil().makeShortToast(resStr);
                }
            });
        }
    }



    //解决web  字体错乱问题
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}
