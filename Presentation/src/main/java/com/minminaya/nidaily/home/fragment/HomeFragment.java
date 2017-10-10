package com.minminaya.nidaily.home.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.minminaya.data.http.model.home.BeforeModel;
import com.minminaya.data.http.model.home.StoriesBean;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.App;
import com.minminaya.nidaily.C;
import com.minminaya.nidaily.R;
import com.minminaya.nidaily.base.BaseFragment;
import com.minminaya.nidaily.home.adapter.HomeRecyclerViewAdapter;
import com.minminaya.nidaily.home.presenter.HomeFragmentPresenter;
import com.minminaya.nidaily.mvp.view.MvpView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 主页的Fragment
 */
public class HomeFragment extends BaseFragment implements MvpView {
    private static final HomeFragment homeFragment = new HomeFragment();

    @BindView(R.id.recycler_view_home_fragment)
    XRecyclerView recyclerView;
    HomeRecyclerViewAdapter mHomeRecyclerViewAdapter;

    BeforeModel beforeModel = null;

    /**
     * 时间的前后标志，用于刷新数据，默认为最新一天
     */
    private int dateIndex = 1;
    private boolean isRefresh = false;
    List<StoriesBean> storiesBeanList = new ArrayList<>();
    private HomeFragmentPresenter homeFragmentPresenter = new HomeFragmentPresenter();


    public static HomeFragment getInstance() {
        return homeFragment;
    }


    @Override
    protected void unBind() {
        Logger.e("HomeFragment", "unBind");
        homeFragmentPresenter.unReristerEventBus();
        homeFragmentPresenter.detachView(this);
    }

    @Override
    public void iniView(View view) {
        Logger.e("HomeFragment", "iniView");
        homeFragmentPresenter.registerEventBus();

    }

    @Override
    public void bind() {
        Logger.e("HomeFragment", "bind");

        homeFragmentPresenter.attachView(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(App.getINSTANCE()));
        mHomeRecyclerViewAdapter = new HomeRecyclerViewAdapter();
        recyclerView.setAdapter(mHomeRecyclerViewAdapter);

        //首次加载数据
        homeFragmentPresenter.getEventBusEvent(C.EventBusString.FROM_HTTPMANAGER_TO_ZHIHU_CONTENT_MANAGER);

    }


    @Override
    public void setListeners() {
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                Log.d("HomeFragment onRefresh", "onRefresh");
                dateIndex = 1;
                //标记当前状态为刷新状态
                isRefresh = true;
                homeFragmentPresenter.setDateIndex(dateIndex);
                homeFragmentPresenter.getEventBusEvent(C.EventBusString.FROM_HTTPMANAGER_TO_ZHIHU_CONTENT_MANAGER);
            }

            @Override
            public void onLoadMore() {
                Logger.e("HomeFragment::onLoadMore", "onLoadMore");
                dateIndex--;
                homeFragmentPresenter.setDateIndex(dateIndex);
                homeFragmentPresenter.getEventBusEvent(C.EventBusString.FROM_HTTPMANAGER_TO_ZHIHU_CONTENT_MANAGER);
                Logger.e("HomeFragment::onLoadMore", "dateIndex:" + dateIndex);
            }
        });
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


    /**
     * 将BeforeModel设置到Adapter，并通知更新数据
     */
    public void notifyRecyvlerViewAdapter() {
        beforeModel = homeFragmentPresenter.getBeforeModel();
        if (isRefresh) {
            //如果本次刷新数据来自下拉，那么把数据添加到头部
            storiesBeanList.addAll(0, beforeModel.getStories());
            isRefresh = false;
        } else {
            storiesBeanList.addAll(beforeModel.getStories());
        }


        mHomeRecyclerViewAdapter.setStoriesBeanList(storiesBeanList);
        mHomeRecyclerViewAdapter.notifyDataSetChanged();
        recyclerView.refreshComplete();
    }

}
