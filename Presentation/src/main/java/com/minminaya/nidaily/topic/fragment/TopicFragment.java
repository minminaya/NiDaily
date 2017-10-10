package com.minminaya.nidaily.topic.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.minminaya.data.http.model.topic.TopicItemModel;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.App;
import com.minminaya.nidaily.C;
import com.minminaya.nidaily.R;
import com.minminaya.nidaily.base.BaseFragment;
import com.minminaya.nidaily.mvp.view.MvpView;
import com.minminaya.nidaily.topic.adapter.TopicRecyclerViewAdapter;
import com.minminaya.nidaily.topic.presenter.TopicFragmentPresenter;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Topic页的Fragment
 */
public class TopicFragment extends BaseFragment implements MvpView {
    private static final TopicFragment topicFragment = new TopicFragment();

    @BindView(R.id.recycler_view_home_fragment)
    XRecyclerView recyclerView;

    private TopicRecyclerViewAdapter topicRecyclerViewAdapter = null;

    private List<TopicItemModel> topicItemModels = new ArrayList<>();
    private TopicItemModel topicItemModel;

    private TopicFragmentPresenter topicFragmentPresenter = new TopicFragmentPresenter();

    public static TopicFragment getInstance() {
        return topicFragment;
    }


    @Override
    protected void unBind() {
        Logger.e("TopicFragment", "unBind");

        topicFragmentPresenter.unReristerEventBus();
        topicFragmentPresenter.detachView(this);

    }

    @Override
    public void iniView(View view) {
        Logger.e("TopicFragment", "iniView");

        topicFragmentPresenter.registerEventBus();
    }

    @Override
    public void bind() {
        Logger.e("TopicFragment", "bind");

        topicFragmentPresenter.attachView(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(App.getINSTANCE()));
        topicRecyclerViewAdapter = new TopicRecyclerViewAdapter();
        recyclerView.setAdapter(topicRecyclerViewAdapter);

        topicFragmentPresenter.getEventBusEvent(C.EventBusString.TOPIC_CACHE_ITEM_DOWNLOAD_SUCCESSFUL);


    }


    @Override
    public void setListeners() {
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                topicFragmentPresenter.getEventBusEvent(C.EventBusString.TOPIC_CACHE_ITEM_DOWNLOAD_SUCCESSFUL);
            }

            @Override
            public void onLoadMore() {
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

        topicItemModel = topicFragmentPresenter.getTopicItemModel();

        topicItemModels.clear();
        topicItemModels.add(topicItemModel);
        topicRecyclerViewAdapter.setTopicItemModels(topicItemModels);
        topicRecyclerViewAdapter.notifyDataSetChanged();
        recyclerView.refreshComplete();
    }


}
