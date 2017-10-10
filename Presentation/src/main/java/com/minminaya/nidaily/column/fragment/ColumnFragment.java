package com.minminaya.nidaily.column.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ProgressBar;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.minminaya.data.http.model.column.SectionsModel;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.App;
import com.minminaya.nidaily.C;
import com.minminaya.nidaily.R;
import com.minminaya.nidaily.base.BaseFragment;
import com.minminaya.nidaily.column.adapter.ColumnRecyclerViewAdapter;
import com.minminaya.nidaily.column.presenter.ColumnFragmentPresenter;
import com.minminaya.nidaily.manager.ZhihuContentManager;
import com.minminaya.nidaily.mvp.view.MvpView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 栏目页列表的fragment
 * Created by Niwa on 2017/10/7.
 */

public class ColumnFragment extends BaseFragment implements MvpView {

    @BindView(R.id.recycler_view_home_fragment)
    XRecyclerView recyclerView;

    private ColumnRecyclerViewAdapter columnRecyclerViewAdapter = null;
    private List<SectionsModel> sectionsModels = new ArrayList<>();
    private SectionsModel sectionsModel;
    private ColumnFragmentPresenter columnFragmentPresenter = new ColumnFragmentPresenter();

    private static ColumnFragment columnFragment = new ColumnFragment();

    public static ColumnFragment getInstance() {
        return columnFragment;
    }


    @Override
    protected void unBind() {
        Logger.e("ColumnFragment", "unBind");
        columnFragmentPresenter.unReristerEventBus();
        columnFragmentPresenter.detachView(this);
    }

    @Override
    public void iniView(View view) {
        Logger.e("ColumnFragment", "iniView");
        columnFragmentPresenter.registerEventBus();
    }

    @Override
    public void bind() {
        Logger.e("ColumnFragment", "bind");

        columnFragmentPresenter.attachView(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(App.getINSTANCE()));
        columnRecyclerViewAdapter = new ColumnRecyclerViewAdapter();
        recyclerView.setAdapter(columnRecyclerViewAdapter);

        //首次请求数据
        columnFragmentPresenter.getEventBusEvent(C.EventBusString.COLUMN_CACHE_ITEM_DOWNLOAD_SUCCESSFUL);

    }


    @Override
    public void setListeners() {
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                columnFragmentPresenter.getEventBusEvent(C.EventBusString.COLUMN_CACHE_ITEM_DOWNLOAD_SUCCESSFUL);
            }

            @Override
            public void onLoadMore() {
            }
        });
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public int setContView() {
        return R.layout.fragment_home;
    }

    @Override
    public void onFailed(Throwable throwable) {

    }


    /**
     * 将BeforeModel设置到Adapter，并通知更新数据
     */
    public void notifyRecyvlerViewAdapter() {
        sectionsModel = columnFragmentPresenter.getSectionsModel();

        sectionsModels.clear();
        sectionsModels.add(sectionsModel);
        columnRecyclerViewAdapter.setSectionsModels(sectionsModels);
        columnRecyclerViewAdapter.notifyDataSetChanged();
        recyclerView.refreshComplete();
    }

}
