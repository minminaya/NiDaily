package com.minminaya.nidaily.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Niwa on 2017/9/27
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        unbinder = ButterKnife.bind(this);
        initView(savedInstanceState);
        setListeners();
        bind();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBind();
        unbinder.unbind();
    }

    /**
     * --次序1--
     * 返回layout的id
     */
    public abstract int getContentView();

    /**
     * --次序2--
     * 初始化view
     */
    public abstract void initView(Bundle savedInstanceState);

    /**
     * 设置监听器，在initView()之后
     * --次序3--
     */
    public abstract void setListeners();

    /**
     * --次序4--
     * 绑定
     */
    public abstract void bind();

    /**
     * 结尾
     */
    public abstract void unBind();
}
