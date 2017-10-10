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
import com.minminaya.nidaily.C;
import com.minminaya.nidaily.R;
import com.minminaya.nidaily.base.BaseActivity;
import com.minminaya.nidaily.mvp.view.MvpView;
import com.minminaya.nidaily.topic.adapter.ThemeRecyclerAdapter;
import com.minminaya.nidaily.topic.presenter.ThemeItemActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ThemeItemActivity extends BaseActivity implements MvpView {


    @BindView(R.id.recycler_view_theme_activity)
    XRecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private View view;

    private List<ThemeItemModel> themeItemModels = new ArrayList<>();
    private ThemeItemModel themeItemModel = null;
    private int id;
    private ThemeRecyclerAdapter themeRecyclerAdapter = null;

    private ThemeItemActivityPresenter themeItemActivityPresenter = new ThemeItemActivityPresenter();

    @Override
    public int getContentView() {
        return R.layout.activity_theme_item;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        themeItemActivityPresenter.registerEventBus();
        themeItemActivityPresenter.attachView(this);

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
                themeItemActivityPresenter.setId(id);
                themeItemActivityPresenter.getEventBusEvent(C.EventBusString.THEME_CACHE_ITEM_DOWNLOAD_SUCCESSFUL);
            }

            @Override
            public void onLoadMore() {
                recyclerView.loadMoreComplete();
            }
        });
    }

    @Override
    public void bind() {

        themeItemActivityPresenter.setId(id);
        themeItemActivityPresenter.getEventBusEvent(C.EventBusString.THEME_CACHE_ITEM_DOWNLOAD_SUCCESSFUL);
    }

    @Override
    public void unBind() {
        themeItemActivityPresenter.unReristerEventBus();
        themeItemActivityPresenter.detachView(this);
    }


    /**
     * 将BeforeModel设置到Adapter，并通知更新数据
     */
    public void notifyRecyvlerViewAdapter() {

        themeItemModel = themeItemActivityPresenter.getThemeItemModel();

        toolbar.setTitle(themeItemModel.getName());
        setSupportActionBar(toolbar);

        themeItemModels.clear();
        themeItemModels.add(themeItemModel);
        themeRecyclerAdapter.setTopicItemModels(null);
        themeRecyclerAdapter.setTopicItemModels(themeItemModels);

        //初始化head view
        initHeadView();

        themeRecyclerAdapter.notifyDataSetChanged();

        //停止刷新动画
        recyclerView.refreshComplete();
    }

    @Override
    public void onFailed(Throwable throwable) {

    }

    /**
     * 初始化头部view及数据
     */
    private void initHeadView() {
        if (view == null) {
            //设置头部内容
            view = LayoutInflater.from(this).inflate(R.layout.view_head, null);
            ImageView imageView = view.findViewById(R.id.img_head_view);
            Glide.with(this).load(themeItemModel.getImage()).into(imageView);
            TextView textView = view.findViewById(R.id.tv_description_head_view);
            textView.setText(themeItemModel.getDescription());
            recyclerView.addHeaderView(view);
        }
    }
}
