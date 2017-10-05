package com.minminaya.nidaily.topic.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.minminaya.data.http.model.topic.ThemeItemModel;
import com.minminaya.library.util.DateUtils;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.C;
import com.minminaya.nidaily.R;
import com.minminaya.nidaily.base.BaseActivity;
import com.minminaya.nidaily.manager.ZhihuContentManager;
import com.minminaya.nidaily.topic.adapter.ThemeRecyclerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ThemeItemActivity extends BaseActivity {


    @BindView(R.id.recycler_view_theme_activity)
    XRecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    View view;

    private List<ThemeItemModel> themeItemModels = new ArrayList<>();
    private ThemeItemModel themeItemModel = null;
    private int id;
    private ThemeRecyclerAdapter themeRecyclerAdapter = null;
    private int dateIndex = 0;

    @Override
    public int getContentView() {
        return R.layout.activity_theme_item;
    }

    @Override
    public void initView(Bundle savedInstanceState) {



        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt(C.BundleKeyString.TOPIC_RECYCLER_VIEW_TO_THEME_ACTIVITY);

        themeRecyclerAdapter = new ThemeRecyclerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(themeRecyclerAdapter);

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
        themeItemModel = (ThemeItemModel) ZhihuContentManager.getInstance().getThemeData(id, DateUtils.getBeforeDayTime(dateIndex));

        if (themeItemModel != null) {
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
            case C.EventBusString.THEME_CACHE_ITEM_DOWNLOAD_SUCCESSFUL:

                themeItemModel = (ThemeItemModel) ZhihuContentManager.getInstance().getThemeData(id, DateUtils.getBeforeDayTime(dateIndex));
                if (themeItemModel != null) {
                    Logger.e("ZhihuContentManager", "getEventBusEvent：" + themeItemModel.getDescription());
                    notifyRecyvlerViewAdapter();
                } else {
                    Logger.e("ZhihuContentManager", "themeItemModel");
                }
                break;
        }
    }

    /**
     * 将BeforeModel设置到Adapter，并通知更新数据
     */
    private void notifyRecyvlerViewAdapter() {

        toolbar.setTitle(themeItemModel.getName());
        setSupportActionBar(toolbar);

        themeItemModels.clear();
        themeItemModels.add(themeItemModel);
        themeRecyclerAdapter.setTopicItemModels(themeItemModels);

        if(view == null){
            //设置头部内容
            view = LayoutInflater.from(this).inflate(R.layout.view_head, null);
            ImageView imageView = view.findViewById(R.id.img_head_view);
            Glide.with(this).load(themeItemModel.getImage()).into(imageView);
            TextView textView = view.findViewById(R.id.tv_description_head_view);
            textView.setText(themeItemModel.getDescription());
            recyclerView.addHeaderView(view);
        }

        themeRecyclerAdapter.notifyDataSetChanged();

        //停止刷新动画
        recyclerView.refreshComplete();
    }
}
