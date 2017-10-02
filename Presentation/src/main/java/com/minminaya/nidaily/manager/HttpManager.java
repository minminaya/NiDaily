package com.minminaya.nidaily.manager;

import com.bumptech.glide.Glide;
import com.minminaya.data.cache.CacheAtFileManage;
import com.minminaya.data.http.NetWorkForRestApi;
import com.minminaya.data.http.model.content.ContentModel;
import com.minminaya.data.http.model.home.BeforeModel;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.C;
import com.minminaya.nidaily.content.activity.WebContentActivity;
import com.minminaya.nidaily.util.HtmlUtil;

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
    private static final HttpManager httpManager = new HttpManager();

    public static HttpManager getInstance() {
        return httpManager;
    }

    private int contentId;

    /**
     * 连接网络获取相应数据
     */
    public void connectRestAPI() {

        NetWorkForRestApi.getZhihuApi()
                .loadBeforeHomeInfo(20171002)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(beforeModelObserver);
    }


    public void loadContentDataFromId(Integer contentId1) {

        this.contentId = contentId1;

        //加载数据
        NetWorkForRestApi.getZhihuApi()
                .loadContent(contentId1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(contentObserver);
    }

    Observer<ContentModel> contentObserver = new Observer<ContentModel>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(ContentModel value) {


            //缓存数据
            boolean isWrited = CacheAtFileManage.putObjectAtFile(value, C.CacheFileString.CONTENT_CACHE_AND_ID_IS + contentId);
            if (isWrited) {
                Logger.d("HttpManager", "HttpManager:缓存数据成功");
                //EvenBus 1事件发送
                EventBus.getDefault().post(contentId);
            }

        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onComplete() {
            Logger.d("WebContentActivity", "加载成功");
        }
    };


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
