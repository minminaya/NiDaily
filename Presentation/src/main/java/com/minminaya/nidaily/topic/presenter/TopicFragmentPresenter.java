package com.minminaya.nidaily.topic.presenter;

import com.minminaya.data.http.model.home.BeforeModel;
import com.minminaya.data.http.model.topic.TopicItemModel;
import com.minminaya.library.util.DateUtils;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.C;
import com.minminaya.nidaily.home.fragment.HomeFragment;
import com.minminaya.nidaily.manager.ZhihuContentManager;
import com.minminaya.nidaily.mvp.presenter.base.BasePresenter;
import com.minminaya.nidaily.topic.fragment.TopicFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * TopicFragment的Presenter
 * Created by Niwa on 2017/10/10.
 */

public class TopicFragmentPresenter extends BasePresenter<TopicFragment> {

    private TopicItemModel topicItemModel;

    public TopicItemModel getTopicItemModel() {
        return topicItemModel;
    }

    public void setTopicItemModel(TopicItemModel topicItemModel) {
        this.topicItemModel = topicItemModel;
    }

    public void registerEventBus() {

        //注册EventBus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
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
                    getMvpView().notifyRecyvlerViewAdapter();
                } else {
                    Logger.e("ZhihuContentManager", "topicItemModel为空");
                }
                break;
        }
    }


    public void unReristerEventBus() {
        EventBus.getDefault().unregister(this);
    }
}
