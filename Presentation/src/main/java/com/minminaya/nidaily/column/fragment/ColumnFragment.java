package com.minminaya.nidaily.column.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.minminaya.data.http.model.column.SectionsModel;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.App;
import com.minminaya.nidaily.C;
import com.minminaya.nidaily.R;
import com.minminaya.nidaily.base.BaseFragment;
import com.minminaya.nidaily.column.adapter.ColumnRecyclerViewAdapter;
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

    ColumnRecyclerViewAdapter columnRecyclerViewAdapter = null;

    List<SectionsModel> sectionsModels = new ArrayList<>();
    SectionsModel sectionsModel;

    private static ColumnFragment columnFragment = new ColumnFragment();

    public static ColumnFragment getInstance() {
        return columnFragment;
    }


    @Override
    protected void unBind() {
        Logger.e("ColumnFragment", "unBind");
    }

    @Override
    public void iniView(View view) {
        Logger.e("ColumnFragment", "iniView");

        //注册EventBus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    @Override
    public void bind() {
        Logger.e("ColumnFragment", "bind");


        recyclerView.setLayoutManager(new LinearLayoutManager(App.getINSTANCE()));
        columnRecyclerViewAdapter = new ColumnRecyclerViewAdapter();
        recyclerView.setAdapter(columnRecyclerViewAdapter);

        sectionsModel = (SectionsModel) ZhihuContentManager.getInstance().getColumnData();
        if (sectionsModel != null) {
            notifyRecyvlerViewAdapter();
        }

    }


    @Override
    public void setListeners() {
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getEventBusEvent(C.EventBusString.COLUMN_CACHE_ITEM_DOWNLOAD_SUCCESSFUL);
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
     * 接收来自HttpManager端EventBus的通知，然后重新读取本地数据，通知RecyclerView更新数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1)
    public void getEventBusEvent(Integer eventIndex) {
        switch (eventIndex) {
            case C.EventBusString.COLUMN_CACHE_ITEM_DOWNLOAD_SUCCESSFUL:

                sectionsModel = (SectionsModel) ZhihuContentManager.getInstance().getTopicData();
                if (sectionsModel != null) {
                    Logger.e("ZhihuContentManager", "getEventBusEvent：" + sectionsModel.getData().get(0).getDescription());
                    notifyRecyvlerViewAdapter();
                } else {
                    Logger.e("ZhihuContentManager", "sectionsModel为空");
                }
                break;
        }
    }

    /**
     * 将BeforeModel设置到Adapter，并通知更新数据
     */
    private void notifyRecyvlerViewAdapter() {
        sectionsModels.clear();
        sectionsModels.add(sectionsModel);
        columnRecyclerViewAdapter.setSectionsModels(sectionsModels);
        columnRecyclerViewAdapter.notifyDataSetChanged();
        recyclerView.refreshComplete();
    }

}
