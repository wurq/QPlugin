package com.arch.base.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arch.base.presenter.BaseFragmentPresenter;

/**
 * Created by wurongqiu on 2017/4/25.
 * <p>
 * 基础的MVPFragment
 */

public abstract class BaseFragment<T extends BaseFragmentPresenter> extends Fragment {

    public String mFragmentTitle = "";
    public boolean mIsCreated = false;
    protected T presenter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (presenter != null) {
            presenter.onAttach();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
        if (presenter != null) {
            presenter.onCreate();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        inflateRootView(inflater);
        if (presenter != null) {
            presenter.onCreateView();
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (presenter != null) {
            presenter.onActivityCreated(savedInstanceState);
        }
    }

//    /**
//     * Inflate a content view for the activity.
//     *
//     * @param resId ID for an XML layout resource as the content view
//     */
//    public void setRealContentView(@LayoutRes int resId) {
//        getActivity().getLayoutInflater().inflate(resId, contentView);
//    }

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
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.onDestroyView();
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
    public void onDetach() {
        super.onDetach();
        if (presenter != null) {
            presenter.onDetach();
        }
    }

    abstract protected void initPresenter();

  /*  @Override
    public void onSlidingFinish() {
        FragmentManager fm = getFragmentManager();
        if (!fm.popBackStackImmediate()) {
            getActivity().finish();
        }
        contentView.resetPosition();
    }*/

    protected void inflateRootView(LayoutInflater inflater) {
    }

}
