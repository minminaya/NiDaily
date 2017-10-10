package com.minminaya.nidaily.column.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.minminaya.data.http.model.column.SectionItemModel;
import com.minminaya.nidaily.C;
import com.minminaya.nidaily.R;
import com.minminaya.nidaily.base.BaseActivity;
import com.minminaya.nidaily.column.adapter.SectionItemRecyclerAdapter;
import com.minminaya.nidaily.column.presenter.SectionItemActivityPresenter;
import com.minminaya.nidaily.mvp.view.MvpView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * SectionItemActivity
 * For Niwa at 2017/10/10
 */
public class SectionItemActivity extends BaseActivity implements MvpView {

    @BindView(R.id.recycler_view_theme_activity)
    XRecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private List<SectionItemModel> sectionItemModels = new ArrayList<>();
    private SectionItemModel sectionItemModel = null;
    private int id;
    private SectionItemRecyclerAdapter sectionItemRecyclerAdapter = null;
    private SectionItemActivityPresenter sectionItemActivityPresenter = new SectionItemActivityPresenter();

    @Override
    public int getContentView() {
        return R.layout.activity_theme_item;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        sectionItemActivityPresenter.registerEventBus();
        sectionItemActivityPresenter.attachView(this);

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
                sectionItemActivityPresenter.getEventBusEvent(C.EventBusString.SECTION_CACHE_ITEM_DOWNLOAD_SUCCESSFUL);
            }

            @Override
            public void onLoadMore() {
                recyclerView.loadMoreComplete();
            }
        });
    }

    @Override
    public void bind() {
        sectionItemActivityPresenter.setId(id);
        sectionItemActivityPresenter.getEventBusEvent(C.EventBusString.SECTION_CACHE_ITEM_DOWNLOAD_SUCCESSFUL);


    }

    @Override
    public void unBind() {
        sectionItemActivityPresenter.unReristerEventBus();
        sectionItemActivityPresenter.detachView(this);
    }


    /**
     * 将BeforeModel设置到Adapter，并通知更新数据
     */
    public void notifyRecyvlerViewAdapter() {
        //获取sectionItemModel数据
        sectionItemModel = sectionItemActivityPresenter.getSectionItemModel();

        toolbar.setTitle(sectionItemModel.getName());
        setSupportActionBar(toolbar);

        sectionItemModels.clear();
        sectionItemModels.add(sectionItemModel);

        sectionItemRecyclerAdapter.setSectionItemModels(sectionItemModels);
        sectionItemRecyclerAdapter.notifyDataSetChanged();

        //停止刷新动画
        recyclerView.refreshComplete();
    }

    @Override
    public void onFailed(Throwable throwable) {

    }
}
