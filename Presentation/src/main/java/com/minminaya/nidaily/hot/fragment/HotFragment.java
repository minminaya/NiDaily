package com.minminaya.nidaily.hot.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.minminaya.data.http.model.hot.HotModel;
import com.minminaya.data.http.model.hot.RecentBean;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.App;
import com.minminaya.nidaily.C;
import com.minminaya.nidaily.R;
import com.minminaya.nidaily.base.BaseFragment;
import com.minminaya.nidaily.hot.adapter.HotRecyclerViewAdapter;
import com.minminaya.nidaily.hot.presenter.HotFragmentPresenter;
import com.minminaya.nidaily.mvp.view.MvpView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Hot页的Fragment
 */
public class HotFragment extends BaseFragment implements MvpView {
    private static final HotFragment hotFragment = new HotFragment();

    @BindView(R.id.recycler_view_home_fragment)
    XRecyclerView recyclerView;
    HotRecyclerViewAdapter mHotRecyclerViewAdapter;

    HotModel hotModel = null;

    /**
     * 时间的前后标志，用于刷新数据，默认为最新一天
     */
    private int dateIndex = 1;
    private boolean isRefresh = false;
    List<RecentBean> recentBeen = new ArrayList<>();
    private HotFragmentPresenter hotFragmentPresenter = new HotFragmentPresenter();


    public static HotFragment getInstance() {
        return hotFragment;
    }


    @Override
    protected void unBind() {
        Logger.e("HotFragment", "unBind");
        hotFragmentPresenter.unReristerEventBus();
        hotFragmentPresenter.detachView(this);
    }

    @Override
    public void iniView(View view) {
        Logger.e("HotFragment", "iniView");

        hotFragmentPresenter.registerEventBus();
    }

    @Override
    public void bind() {
        Logger.e("HotFragment", "bind");

        hotFragmentPresenter.attachView(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(App.getINSTANCE()));
        mHotRecyclerViewAdapter = new HotRecyclerViewAdapter();
        recyclerView.setAdapter(mHotRecyclerViewAdapter);

        hotFragmentPresenter.getEventBusEvent(C.EventBusString.HOT_CACHE_ITEM_DOWNLOAD_SUCCESSFUL);


    }


    @Override
    public void setListeners() {
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                Log.d("HotFragment onRefresh", "onRefresh");

                dateIndex = 1;
                //标记当前状态为刷新状态
                isRefresh = true;

                hotFragmentPresenter.setDateIndex(dateIndex);
                hotFragmentPresenter.getEventBusEvent(C.EventBusString.HOT_CACHE_ITEM_DOWNLOAD_SUCCESSFUL);
            }

            @Override
            public void onLoadMore() {
                Logger.e("HotFragment::onLoadMore", "onLoadMore");
                recyclerView.loadMoreComplete();
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
        hotModel = hotFragmentPresenter.getHotModel();

        if (isRefresh) {
            //如果本次刷新数据来自下拉，那么把数据添加到头部
            recentBeen.addAll(0, hotModel.getRecent());
            isRefresh = false;
        } else {
            recentBeen.addAll(hotModel.getRecent());
        }

        mHotRecyclerViewAdapter.setRecentBeanList(recentBeen);
        mHotRecyclerViewAdapter.notifyDataSetChanged();
        recyclerView.refreshComplete();
    }

}
