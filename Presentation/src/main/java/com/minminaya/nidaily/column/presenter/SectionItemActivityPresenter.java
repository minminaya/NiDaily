package com.minminaya.nidaily.column.presenter;

import com.minminaya.data.http.model.column.SectionItemModel;
import com.minminaya.data.http.model.topic.ThemeItemModel;
import com.minminaya.library.util.DateUtils;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.C;
import com.minminaya.nidaily.column.activity.SectionItemActivity;
import com.minminaya.nidaily.manager.ZhihuContentManager;
import com.minminaya.nidaily.mvp.presenter.base.BasePresenter;
import com.minminaya.nidaily.topic.activity.ThemeItemActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/** SectionItemActivity的Presenter
 * Created by Niwa on 2017/10/10.
 */

public class SectionItemActivityPresenter extends BasePresenter<SectionItemActivity> {

    private SectionItemModel sectionItemModel = null;
    private int id;
    private int dateIndex = 0;

    public SectionItemModel getSectionItemModel() {
        return sectionItemModel;
    }

    public void setSectionItemModel(SectionItemModel sectionItemModel) {
        this.sectionItemModel = sectionItemModel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDateIndex() {
        return dateIndex;
    }

    public void setDateIndex(int dateIndex) {
        this.dateIndex = dateIndex;
    }

    /**
     * 接收来自HttpManager端EventBus的通知，然后重新读取本地数据，通知RecyclerView更新数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 3)
    public void getEventBusEvent(Integer eventIndex) {
        switch (eventIndex) {
            case C.EventBusString.SECTION_CACHE_ITEM_DOWNLOAD_SUCCESSFUL:

                sectionItemModel = (SectionItemModel) ZhihuContentManager.getInstance().getSectionData(id, DateUtils.getBeforeDayTime(dateIndex));
                if (sectionItemModel != null) {
                    Logger.e("ZhihuContentManager", "getEventBusEvent：" + sectionItemModel.getName());
                    getMvpView().notifyRecyvlerViewAdapter();
                } else {
                    Logger.e("ZhihuContentManager", "sectionItemModel");
                }
                break;
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
