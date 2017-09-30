package com.minminaya.nidaily.manager;

import com.minminaya.data.cache.CacheAtFileManage;
import com.minminaya.data.http.NetWorkForRestApi;
import com.minminaya.data.http.model.home.BeforeModel;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.C;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * <p>网络请求管理类</p>
 * <p>1.负责访问数据，并缓存到本地缓存</p>
 * <p>2.</p>
 * Created by Niwa on 2017/9/29.
 */

public class HttpManager {


    /**
     * 连接网络获取相应数据
     */
    public void connectRestAPI() {

        NetWorkForRestApi.getZhihuApi()
                .loadBeforeHomeInfo(20170202)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(beforeModelObserver);
    }

    Observer<BeforeModel> beforeModelObserver = new Observer<BeforeModel>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(BeforeModel value) {
            //缓存数据
            boolean isWrited = CacheAtFileManage.putObjectAtFile(value, C.CacheFileString.homeCacheFileName);
            if (isWrited) {
                Logger.d("HttpManager", "HttpManager:缓存数据成功");
                //EvenBus 1事件发送
                EventBus.getDefault().post(C.EventBusString.FROM_HTTPMANAGER_TO_ZHIHU_CONTENT_MANAGER);
            }
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onComplete() {

        }
    };

}
