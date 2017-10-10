package com.minminaya.nidaily.home.presenter;

import com.minminaya.data.http.model.home.BeforeModel;
import com.minminaya.data.http.model.home.StoriesBean;
import com.minminaya.library.util.DateUtils;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.C;
import com.minminaya.nidaily.OuterActivity;
import com.minminaya.nidaily.home.fragment.HomeFragment;
import com.minminaya.nidaily.manager.ZhihuContentManager;
import com.minminaya.nidaily.mvp.presenter.base.BasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * HomeFragment的Presenter
 * Created by Niwa on 2017/9/27.
 */

public class HomeFragmentPresenter extends BasePresenter<HomeFragment> {


    BeforeModel beforeModel = null;

    /**
     * 时间的前后标志，用于刷新数据，默认为最新一天
     */
    private int dateIndex = 1;

    public BeforeModel getBeforeModel() {
        return beforeModel;
    }

    public void setBeforeModel(BeforeModel beforeModel) {
        this.beforeModel = beforeModel;
    }

    public int getDateIndex() {
        return dateIndex;
    }

    public void setDateIndex(int dateIndex) {
        this.dateIndex = dateIndex;
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
            case C.EventBusString.FROM_HTTPMANAGER_TO_ZHIHU_CONTENT_MANAGER:
                Logger.e("ZhihuContentManager", "getEventBusEvent");

                beforeModel = (BeforeModel) ZhihuContentManager.getInstance().getData(DateUtils.getBeforeDayTime(dateIndex));
                if (beforeModel != null) {
                    Logger.e("ZhihuContentManager", "getEventBusEvent：" + beforeModel.getDate());
                    getMvpView().notifyRecyvlerViewAdapter();
                } else {
                    Logger.e("ZhihuContentManager", "befoModel为空");
                }
                break;
        }
    }



    public void unReristerEventBus() {
        EventBus.getDefault().unregister(this);
    }
}
