package com.minminaya.nidaily.home.presenter;

import com.minminaya.data.cache.CacheAtFileManage;
import com.minminaya.data.http.NetWorkForRestApi;
import com.minminaya.data.http.model.home.BeforeModel;
import com.minminaya.data.http.model.home.LatestInfoModel;
import com.minminaya.data.http.model.hot.HotModel;
import com.minminaya.library.util.DateUtils;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.C;
import com.minminaya.nidaily.home.fragment.HomeFragment;
import com.minminaya.nidaily.manager.ZhihuContentManager;
import com.minminaya.nidaily.mvp.presenter.base.BasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * HomeFragment的Presenter
 * Created by Niwa on 2017/9/27.
 */

public class HomeFragmentPresenter extends BasePresenter<HomeFragment> {


    private BeforeModel beforeModel = null;
    private LatestInfoModel latestInfoModel = null;

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

    public LatestInfoModel getLatestInfoModel() {
        return latestInfoModel;
    }

    public void setLatestInfoModel(LatestInfoModel latestInfoModel) {
        this.latestInfoModel = latestInfoModel;
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
            case C.EventBusString.HOME_LATEST_CACHE_ITEM_DOWNLOAD_SUCCESSFUL:
                //头部滚动view事件
                Logger.e("ZhihuContentManager", "getEventBusEvent");

                latestInfoModel = (LatestInfoModel) ZhihuContentManager.getInstance().getLatestData(DateUtils.getBeforeDayTime(0));

                if (latestInfoModel != null) {
                    Logger.e("HomeFragmentPresenter --latestInfoModelObserver", "value" + latestInfoModel.getTop_stories().get(0).getTitle());
                    getMvpView().notifyHeadViewAdapter();
                } else {
                    Logger.e("ZhihuContentManager", "latestInfoModel");
                }
                break;
        }
    }

    public void loadLatestInfo() {
        getEventBusEvent(C.EventBusString.HOME_LATEST_CACHE_ITEM_DOWNLOAD_SUCCESSFUL);
    }


    public void unReristerEventBus() {
        EventBus.getDefault().unregister(this);
    }
}
