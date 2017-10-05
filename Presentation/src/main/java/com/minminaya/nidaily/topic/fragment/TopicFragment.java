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
import com.minminaya.nidaily.manager.ZhihuContentManager;
import com.minminaya.nidaily.mvp.view.MvpView;
import com.minminaya.nidaily.topic.adapter.TopicRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    TopicRecyclerViewAdapter topicRecyclerViewAdapter = null;

    List<TopicItemModel> topicItemModels = new ArrayList<>();
    TopicItemModel topicItemModel;

    public static TopicFragment getInstance() {
        return topicFragment;
    }


    @Override
    protected void unBind() {
        Logger.e("TopicFragment", "unBind");
    }

    @Override
    public void iniView(View view) {
        Logger.e("TopicFragment", "iniView");

        //注册EventBus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    @Override
    public void bind() {
        Logger.e("TopicFragment", "bind");


        recyclerView.setLayoutManager(new LinearLayoutManager(App.getINSTANCE()));
        topicRecyclerViewAdapter = new TopicRecyclerViewAdapter();
        recyclerView.setAdapter(topicRecyclerViewAdapter);
        topicRecyclerViewAdapter.setFragmentManager(getActivity().getSupportFragmentManager());

        topicItemModel = (TopicItemModel) ZhihuContentManager.getInstance().getTopicData();
        if (topicItemModel != null) {
            notifyRecyvlerViewAdapter();
        }


    }


    @Override
    public void setListeners() {
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getEventBusEvent(C.EventBusString.TOPIC_CACHE_ITEM_DOWNLOAD_SUCCESSFUL);
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
     * 接收来自HttpManager端EventBus的通知，然后重新读取本地数据，通知RecyclerView更新数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1)
    public void getEventBusEvent(Integer eventIndex) {
        switch (eventIndex) {
            case C.EventBusString.TOPIC_CACHE_ITEM_DOWNLOAD_SUCCESSFUL:

                topicItemModel = (TopicItemModel) ZhihuContentManager.getInstance().getTopicData();
                if (topicItemModel != null) {
                    Logger.e("ZhihuContentManager", "getEventBusEvent：" + topicItemModel.getOthers().size());
                    notifyRecyvlerViewAdapter();
                } else {
                    Logger.e("ZhihuContentManager", "befoModel为空");
                }
                break;
        }
    }

    /**
     * 将BeforeModel设置到Adapter，并通知更新数据
     */
    private void notifyRecyvlerViewAdapter() {
        topicItemModels.clear();
        topicItemModels.add(topicItemModel);
        topicRecyclerViewAdapter.setTopicItemModels(topicItemModels);
        topicRecyclerViewAdapter.notifyDataSetChanged();
        recyclerView.refreshComplete();
    }


}
