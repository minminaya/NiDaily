package com.minminaya.nidaily.column.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.minminaya.data.http.model.column.SectionItemModel;
import com.minminaya.data.http.model.topic.ThemeItemModel;
import com.minminaya.library.util.DateUtils;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.C;
import com.minminaya.nidaily.R;
import com.minminaya.nidaily.base.BaseActivity;
import com.minminaya.nidaily.column.adapter.SectionItemRecyclerAdapter;
import com.minminaya.nidaily.manager.ZhihuContentManager;
import com.minminaya.nidaily.topic.adapter.ThemeRecyclerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SectionItemActivity extends BaseActivity {

    @BindView(R.id.recycler_view_theme_activity)
    XRecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private List<SectionItemModel> sectionItemModels = new ArrayList<>();
    private SectionItemModel sectionItemModel = null;
    private int id;
    private SectionItemRecyclerAdapter sectionItemRecyclerAdapter = null;
    private int dateIndex = 0;

    @Override
    public int getContentView() {
        return R.layout.activity_theme_item;
    }

    @Override
    public void initView(Bundle savedInstanceState) {



        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt(C.BundleKeyString.COLUMN_RECYCLER_VIEW_TO_SECTION_ACTIVITY);

        sectionItemRecyclerAdapter = new SectionItemRecyclerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(sectionItemRecyclerAdapter);

    }

    @Override
    public void setListeners() {
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
//                themeItemModel = (ThemeItemModel) ZhihuContentManager.getInstance().getThemeData(id, DateUtils.getBeforeDayTime(dateIndex));
                getEventBusEvent(C.EventBusString.THEME_CACHE_ITEM_DOWNLOAD_SUCCESSFUL);
            }

            @Override
            public void onLoadMore() {
                recyclerView.loadMoreComplete();
            }
        });
    }

    @Override
    public void bind() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        sectionItemModel = (SectionItemModel) ZhihuContentManager.getInstance().getSectionData(id, DateUtils.getBeforeDayTime(dateIndex));

        if (sectionItemModel != null) {
            notifyRecyvlerViewAdapter();
        }


    }

    @Override
    public void unBind() {
        EventBus.getDefault().unregister(this);
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
                    notifyRecyvlerViewAdapter();
                } else {
                    Logger.e("ZhihuContentManager", "sectionItemModel");
                }
                break;
        }
    }

    /**
     * 将BeforeModel设置到Adapter，并通知更新数据
     */
    private void notifyRecyvlerViewAdapter() {

        toolbar.setTitle(sectionItemModel.getName());
        setSupportActionBar(toolbar);

        sectionItemModels.clear();
        sectionItemModels.add(sectionItemModel);
        sectionItemRecyclerAdapter.setSectionItemModels(sectionItemModels);

        sectionItemRecyclerAdapter.notifyDataSetChanged();

        //停止刷新动画
        recyclerView.refreshComplete();
    }
}
