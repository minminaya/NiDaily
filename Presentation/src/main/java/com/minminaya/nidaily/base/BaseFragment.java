package com.minminaya.nidaily.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Niwa on 2017/09/27
 */

public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;


    //是否加载了rootView
    protected boolean mPrepared;
    //Fragment是否显示给了用户
    protected boolean isVisibleToUser;

    private BaseActivity baseActivity;

    public BaseActivity getBaseActivity() {
        return baseActivity;
    }


    /**
     * 当Fragment被添加到Activity时调用，只调用一次
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        baseActivity = (BaseActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(setContView(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        iniView(rootView);
        mPrepared = true;
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        tryLoad();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bind();
        tryLoad();
        setListeners();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPrepared = false;
        unBind();
        //取消ButterKnife的绑定
        unbinder.unbind();
    }

    protected abstract void unBind();

    /**
     * ---次序--1--
     */
    public abstract void iniView(View view);

    /**
     * ---次序--2--
     */
    public abstract void bind();

    /**
     * ---次序--3.0--
     */
    public void tryLoad() {
        if (isVisibleToUser && mPrepared) {
            //如果已经显示给用户和加载了rootView，那么执行懒加载
            lazyLoad();
        }
    }

    /**
     * ---次序--4--
     */
    public abstract void setListeners();

    /**
     * -----次序3---
     *
     * 如果已经显示给用户和加载了rootView，那么执行懒加载
     */
    public abstract void lazyLoad();

    public abstract int setContView();
}


