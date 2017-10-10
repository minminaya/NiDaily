package com.minminaya.nidaily.column.presenter;

import com.minminaya.data.http.model.column.SectionsModel;
import com.minminaya.data.http.model.home.BeforeModel;
import com.minminaya.library.util.DateUtils;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.C;
import com.minminaya.nidaily.column.fragment.ColumnFragment;
import com.minminaya.nidaily.home.fragment.HomeFragment;
import com.minminaya.nidaily.manager.ZhihuContentManager;
import com.minminaya.nidaily.mvp.presenter.base.BasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * HomeFragment的Presenter
 * Created by Niwa on 2017/10/10.
 */

public class ColumnFragmentPresenter extends BasePresenter<ColumnFragment> {


    private SectionsModel sectionsModel;

    public SectionsModel getSectionsModel() {
        return sectionsModel;
    }

    public void setSectionsModel(SectionsModel sectionsModel) {
        this.sectionsModel = sectionsModel;
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
            case C.EventBusString.COLUMN_CACHE_ITEM_DOWNLOAD_SUCCESSFUL:

                sectionsModel = (SectionsModel) ZhihuContentManager.getInstance().getColumnData();
                if (sectionsModel != null) {
                    Logger.e("ZhihuContentManager", "getEventBusEvent：" + sectionsModel.getData().get(0).getDescription());
                    getMvpView().notifyRecyvlerViewAdapter();
                } else {
                    Logger.e("ZhihuContentManager", "sectionsModel为空");
                }
                break;
        }
    }


    public void unReristerEventBus() {
        EventBus.getDefault().unregister(this);
    }
}
