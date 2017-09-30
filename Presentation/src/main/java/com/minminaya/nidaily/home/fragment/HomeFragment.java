package com.minminaya.nidaily.home.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.App;
import com.minminaya.nidaily.R;
import com.minminaya.nidaily.base.BaseFragment;
import com.minminaya.nidaily.home.adapter.HomeRecyclerViewAdapter;
import com.minminaya.nidaily.home.presenter.HomeFragmentPresenter;
import com.minminaya.nidaily.mvp.view.MvpView;

import butterknife.BindView;

/**
 * 主页的Fragment
 */
public class HomeFragment extends BaseFragment implements MvpView{
    private static final HomeFragment homeFragment = new HomeFragment();

    @BindView(R.id.recycler_view_home_fragment)
    XRecyclerView recyclerView;


    private HomeFragmentPresenter homeFragmentPresenter = new HomeFragmentPresenter();


    public static HomeFragment getInstance() {
        return homeFragment;
    }


    @Override
    protected void unBind() {
        Logger.e("HomeFragment", "unBind");
        homeFragmentPresenter.detachView(this);
    }

    @Override
    public void iniView(View view) {
        Logger.e("HomeFragment", "iniView");
    }

    @Override
    public void bind() {
        Logger.e("HomeFragment", "bind");

        homeFragmentPresenter.attachView(this);
        homeFragmentPresenter.textMvp();//测试mvp架构是否正常使用

        recyclerView.setLayoutManager(new LinearLayoutManager(App.getINSTANCE()));
        HomeRecyclerViewAdapter mHomeRecyclerViewAdapter = new HomeRecyclerViewAdapter();
        recyclerView.setAdapter(mHomeRecyclerViewAdapter);
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public int setContView() {
        return R.layout.fragment_home;
    }

    @Override
    public void onFailed(Throwable throwable) {

    }
}
