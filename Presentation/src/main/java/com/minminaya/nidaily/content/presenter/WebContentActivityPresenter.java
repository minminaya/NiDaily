package com.minminaya.nidaily.content.presenter;

import com.minminaya.data.http.model.content.ContentModel;
import com.minminaya.data.http.model.topic.ThemeItemModel;
import com.minminaya.library.util.DateUtils;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.C;
import com.minminaya.nidaily.content.activity.WebContentActivity;
import com.minminaya.nidaily.manager.HttpManager;
import com.minminaya.nidaily.manager.ZhihuContentManager;
import com.minminaya.nidaily.mvp.presenter.base.BasePresenter;
import com.minminaya.nidaily.topic.activity.ThemeItemActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * WebContentActivity的Presenter
 * Created by Niwa on 2017/10/10.
 */

public class WebContentActivityPresenter extends BasePresenter<WebContentActivity> {


    private ContentModel contentModel;

    public ContentModel getContentModel() {
        return contentModel;
    }

    public void setContentModel(ContentModel contentModel) {
        this.contentModel = contentModel;
    }


    /**
     * 接收来自HttpManager端EventBus的通知，然后重新读取本地数据，通知RecyclerView更新数据
     */
    @Subscribe(threadMode = ThreadMode.POSTING, priority = 56)
    public void getEventBusEvent(Integer id) {

        Logger.e("WebContentActivity", "getEventBusEvent");
        contentModel = (ContentModel) ZhihuContentManager.getInstance().getContentFromId(id);
        if (contentModel != null) {
            Logger.e("WebContentActivity", "getEventBusEvent：" + contentModel.getId());
            getMvpView().setViewData(contentModel);
        }
        if (HttpManager.getInstance().isHttpManagerSend) {
            //如果当前唤醒getEventBusEvent方法的是来自HttpManager的EventBus事件，则拦截消息取消它接着往下传
            EventBus.getDefault().cancelEventDelivery(id);
            Logger.e("getEventBusEvent cancelEventDelivery", "取消消息事件往下传递成功");
            HttpManager.getInstance().isHttpManagerSend = false;
        }
    }


    public void registerEventBus() {

        //注册EventBus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public void unReristerEventBus() {
        EventBus.getDefault().unregister(this);
    }
}
