package com.minminaya.nidaily.home.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.minminaya.data.http.model.home.BeforeModel;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.App;
import com.minminaya.nidaily.C;
import com.minminaya.nidaily.R;
import com.minminaya.nidaily.base.BaseFragment;
import com.minminaya.nidaily.home.adapter.HomeRecyclerViewAdapter;
import com.minminaya.nidaily.home.presenter.HomeFragmentPresenter;
import com.minminaya.nidaily.manager.ZhihuContentManager;
import com.minminaya.nidaily.mvp.view.MvpView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * 主页的Fragment
 */
public class HomeFragment extends BaseFragment implements MvpView {
    private static final HomeFragment homeFragment = new HomeFragment();

    @BindView(R.id.recycler_view_home_fragment)
    XRecyclerView recyclerView;
    HomeRecyclerViewAdapter mHomeRecyclerViewAdapter;

    BeforeModel beforeModel = null;

    private HomeFragmentPresenter homeFragmentPresenter = new HomeFragmentPresenter();


    public static HomeFragment getInstance() {
        return homeFragment;
    }


    @Override
    protected void unBind() {
        Logger.e("HomeFragment", "unBind");
        homeFragmentPresenter.detachView(this);
    }

    @Override
    public void iniView(View view) {
        Logger.e("HomeFragment", "iniView");

        //注册EventBus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    @Override
    public void bind() {
        Logger.e("HomeFragment", "bind");

        homeFragmentPresenter.attachView(this);
        homeFragmentPresenter.textMvp();//测试mvp架构是否正常使用

        recyclerView.setLayoutManager(new LinearLayoutManager(App.getINSTANCE()));
        mHomeRecyclerViewAdapter = new HomeRecyclerViewAdapter();
        recyclerView.setAdapter(mHomeRecyclerViewAdapter);

        Object object = ZhihuContentManager.getInstance().getData();

        if (object != null) {
            beforeModel = (BeforeModel) object;
            notifyRecyvlerViewAdapter();
        }
    }


    @Override
    public void setListeners() {

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
    public void getEventBusEvent(Integer index) {
        switch (index) {
            case C.EventBusString.FROM_HTTPMANAGER_TO_ZHIHU_CONTENT_MANAGER:
                Logger.e("ZhihuContentManager", "getEventBusEvent");
                beforeModel = (BeforeModel) ZhihuContentManager.getInstance().getData();
                Logger.e("ZhihuContentManager", "getEventBusEvent：" + beforeModel.getDate());
                notifyRecyvlerViewAdapter();
                break;
        }
    }

    /**
     * 将BeforeModel设置到Adapter，并通知更新数据
     */
    private void notifyRecyvlerViewAdapter() {
        mHomeRecyclerViewAdapter.setBeforeModel(beforeModel);
        mHomeRecyclerViewAdapter.notifyDataSetChanged();
    }


}
