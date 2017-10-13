package com.minminaya.nidaily.home.fragment;


import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.minminaya.data.http.model.home.BeforeModel;
import com.minminaya.data.http.model.home.LatestInfoModel;
import com.minminaya.data.http.model.home.StoriesBean;
import com.minminaya.data.http.model.home.TopStoriesBean;
import com.minminaya.library.util.DateUtils;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.App;
import com.minminaya.nidaily.C;
import com.minminaya.nidaily.R;
import com.minminaya.nidaily.base.BaseFragment;
import com.minminaya.nidaily.home.adapter.HomeRecyclerViewAdapter;
import com.minminaya.nidaily.home.adapter.HomeScrollViewAdapter;
import com.minminaya.nidaily.home.presenter.HomeFragmentPresenter;
import com.minminaya.nidaily.mvp.view.MvpView;
import com.tmall.ultraviewpager.UltraViewPager;


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

    private HomeRecyclerViewAdapter mHomeRecyclerViewAdapter;
    private HomeScrollViewAdapter mHomeScrollViewAdapter = null;
    private BeforeModel beforeModel = null;
    private LatestInfoModel mLatestInfoModel = null;
    private List<TopStoriesBean> topStoriesBeanList = new ArrayList<>();
    /**
     * 时间的前后标志，用于刷新数据，默认为最新一天
     */
    private int dateIndex = 1;
    private List<StoriesBean> storiesBeanList = new ArrayList<>();
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
        //加载headview数据
        homeFragmentPresenter.loadLatestInfo();
    }


    @Override
    public void setListeners() {
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                Log.d("HomeFragment onRefresh", "onRefresh");
                ToastUtils.setBgColor(Color.parseColor("#009688"));
                ToastUtils.setMsgColor(Color.WHITE);
                ToastUtils.showShort("当前数据无更新");
                recyclerView.refreshComplete();
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

        //数据添加到list的尾部
        storiesBeanList.addAll(beforeModel.getStories());

        mHomeRecyclerViewAdapter.setStoriesBeanList(storiesBeanList);
        mHomeRecyclerViewAdapter.notifyDataSetChanged();

        ToastUtils.setBgColor(Color.parseColor("#009688"));
        ToastUtils.setMsgColor(Color.WHITE);
        ToastUtils.showShort(DateUtils.convertDate(beforeModel.getDate()) + "数据加载成功");

        recyclerView.refreshComplete();
    }

    /**
     * 创建头部headview并通知headview的adapter更新数据
     */
    public void notifyHeadViewAdapter() {
        Logger.e("HomeFragment--notifyHeadViewAdapter", "notifyHeadViewAdapter");
        mLatestInfoModel = homeFragmentPresenter.getLatestInfoModel();
        topStoriesBeanList.clear();
        topStoriesBeanList.addAll(mLatestInfoModel.getTop_stories());

        //初始化头部View
        View headview = LayoutInflater.from(this.getContext()).inflate(R.layout.view_home_head, null);
        UltraViewPager ultraViewPager = headview.findViewById(R.id.ultra_viewpager);

        ultraViewPager.setInfiniteLoop(true);
        ultraViewPager.setAutoScroll(3000);

        mHomeScrollViewAdapter = new HomeScrollViewAdapter();
        mHomeScrollViewAdapter.setTopStoriesBean(topStoriesBeanList);
        ultraViewPager.setAdapter(mHomeScrollViewAdapter);

        recyclerView.addHeaderView(headview);
    }
}
