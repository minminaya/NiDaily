package com.minminaya.nidaily.hot.presenter;

import com.minminaya.data.http.model.hot.HotModel;
import com.minminaya.library.util.DateUtils;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.C;
import com.minminaya.nidaily.hot.fragment.HotFragment;
import com.minminaya.nidaily.manager.ZhihuContentManager;
import com.minminaya.nidaily.mvp.presenter.base.BasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * HotFragment的Presenter
 * Created by Niwa on 2017/10/10.
 */

public class HotFragmentPresenter extends BasePresenter<HotFragment> {


    private HotModel hotModel = null;

    /**
     * 时间的前后标志，用于刷新数据，默认为最新一天
     */
    private int dateIndex = 1;

    public HotModel getHotModel() {
        return hotModel;
    }

    public void setHotModel(HotModel hotModel) {
        this.hotModel = hotModel;
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
            case C.EventBusString.HOT_CACHE_ITEM_DOWNLOAD_SUCCESSFUL:
                Logger.e("ZhihuContentManager", "getEventBusEvent");

                hotModel = (HotModel) ZhihuContentManager.getInstance().getHotData(DateUtils.getBeforeDayTime(dateIndex));
                if (hotModel != null) {
                    Logger.e("ZhihuContentManager", "getEventBusEvent：" + hotModel.getRecent().get(0).getTitle());
                    getMvpView().notifyRecyvlerViewAdapter();

                } else {
                    Logger.e("ZhihuContentManager", "hotModel为空");
                }
                break;
        }
    }

    public void unReristerEventBus() {
        EventBus.getDefault().unregister(this);
    }
}
