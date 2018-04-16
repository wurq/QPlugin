package com.arch.base.presenter;

/**
 * Created by wurongqiu on 2017/4/25.
 */

public abstract class BasePresenter <T> {
    protected T target;

    public BasePresenter(T target) {
        this.target = target;
    }

    public void onCreate() {
    }

    public void onStart() {
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onStop() {
    }

    public void onDestroy() {
    }


}
