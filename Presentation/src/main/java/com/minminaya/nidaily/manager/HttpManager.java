package com.minminaya.nidaily.manager;


import com.minminaya.data.cache.CacheAtFileManage;
import com.minminaya.data.http.NetWorkForRestApi;
import com.minminaya.data.http.model.column.SectionItemModel;
import com.minminaya.data.http.model.column.SectionsModel;
import com.minminaya.data.http.model.content.ContentModel;
import com.minminaya.data.http.model.home.BeforeModel;
import com.minminaya.data.http.model.topic.ThemeItemModel;
import com.minminaya.data.http.model.topic.TopicItemModel;
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
    private static final HttpManager httpManager = new HttpManager();

    public static HttpManager getInstance() {
        return httpManager;
    }

    private int contentId;

    private String date;
    private int themeId;

    private String sectionDate;
    private int sectionId;

    /**
     * 连接网络获取相应数据
     */
    public void connectRestAPI(String date) {
        this.date = date;
        NetWorkForRestApi.getZhihuApi()
                .loadBeforeHomeInfo(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(beforeModelObserver);
    }

    /**
     * 获取相应id的详细内容
     */
    public void loadContentDataFromId(Integer contentId1) {

        this.contentId = contentId1;

        //加载数据
        NetWorkForRestApi.getZhihuApi()
                .loadContent(contentId1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(contentObserver);
    }

    /**
     * 获取相应Topic页Item
     */
    public void loadTopicItemModel() {


        //加载数据
        NetWorkForRestApi.getZhihuApi()
                .loadTopicItem()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(topicItemObserver);
    }

    /**
     * 根据指定的id获取相应Theme页Item
     *
     * @param id 主题日报的id
     */
    public void loadThemeItemModel(Integer id, String date) {
        this.date = date;
        this.themeId = id;
        //加载数据
        NetWorkForRestApi.getZhihuApi()
                .loadThemeItem(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(themeItemObserver);
    }

    /**
     * 根据指定的id获取相应Theme页Item
     *
     * @param id 主题日报的id
     */
    public void loadSectionItemModel(Integer id, String sectionDate) {
        this.sectionDate = sectionDate;
        this.sectionId = id;
        //加载数据
        NetWorkForRestApi.getZhihuApi()
                .loadSectionItemContentForId(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(sectionItemObserver);
    }

    /**
     * 获取相应Column页Item
     */
    public void loadColumnItemModel() {
        //加载数据
        NetWorkForRestApi.getZhihuApi()
                .loadSectionItem()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(columnItemObserver);
    }

    Observer<SectionsModel> columnItemObserver = new Observer<SectionsModel>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(SectionsModel value) {
            Logger.e("HttpManager", "SectionsModel：" + value.getData().get(0).getDescription());
            //缓存数据
            boolean isWrited = CacheAtFileManage.putObjectAtFile(value, C.CacheFileString.COLUMN_CACHE_ITEM);
            if (isWrited) {
                Logger.d("HttpManager", "HttpManager SectionsModel:缓存数据成功");
                //EvenBus 1事件发送
                EventBus.getDefault().post(C.EventBusString.COLUMN_CACHE_ITEM_DOWNLOAD_SUCCESSFUL);
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
    Observer<SectionItemModel> sectionItemObserver = new Observer<SectionItemModel>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(SectionItemModel value) {
            Logger.e("HttpManager", "SectionItemModel：" + value.getName());
            //缓存数据
            boolean isWrited = CacheAtFileManage.putObjectAtFile(value, C.CacheFileString.SECTION_CACHE_ITEM + sectionDate + sectionId);
            if (isWrited) {
                Logger.d("HttpManager", "HttpManager sectionItemModel:缓存数据成功");
                //EvenBus 1事件发送
                EventBus.getDefault().post(C.EventBusString.SECTION_CACHE_ITEM_DOWNLOAD_SUCCESSFUL);
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

    Observer<ThemeItemModel> themeItemObserver = new Observer<ThemeItemModel>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(ThemeItemModel value) {
            Logger.e("HttpManager", "ThemeItemModel：" + value.getDescription());
            //缓存数据
            boolean isWrited = CacheAtFileManage.putObjectAtFile(value, C.CacheFileString.THEME_CACHE_ITEM + date + themeId);
            if (isWrited) {
                Logger.d("HttpManager", "HttpManager themeItemObserver:缓存数据成功");
                //EvenBus 1事件发送
                EventBus.getDefault().post(C.EventBusString.THEME_CACHE_ITEM_DOWNLOAD_SUCCESSFUL);
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


    Observer<TopicItemModel> topicItemObserver = new Observer<TopicItemModel>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(TopicItemModel value) {
            Logger.e("HttpManager", "TopicItem：" + value.getOthers().get(0).getName());
            //缓存数据
            boolean isWrited = CacheAtFileManage.putObjectAtFile(value, C.CacheFileString.TOPIC_CACHE_ITEM);
            if (isWrited) {
                Logger.d("HttpManager", "HttpManager:缓存数据成功");
                //EvenBus 1事件发送
                EventBus.getDefault().post(C.EventBusString.TOPIC_CACHE_ITEM_DOWNLOAD_SUCCESSFUL);
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
            boolean isWrited = CacheAtFileManage.putObjectAtFile(value, C.CacheFileString.HOME_CACHE_FILE_NAME_DATE_IS + date);
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
