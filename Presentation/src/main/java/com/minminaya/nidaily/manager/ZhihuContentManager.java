package com.minminaya.nidaily.manager;

import com.minminaya.data.cache.CacheAtFileManage;
import com.minminaya.data.http.model.home.BeforeModel;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.App;
import com.minminaya.nidaily.C;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 知乎RestAPI数据的管理类
 * <p>策略如下，如果本地缓存有对应的数据，则获得本地数据，否则请求网络得到对应数据</p>
 * Created by Niwa on 2017/9/29.
 */

public class ZhihuContentManager {

    private static final ZhihuContentManager mZhihuContentManager = new ZhihuContentManager();

    public static ZhihuContentManager getInstance() {
        return mZhihuContentManager;
    }

    public Object getData(String date) {
        //首先读取缓存中的数据
        Object object = CacheAtFileManage.getObjectAtFile(C.CacheFileString.HOME_CACHE_FILE_NAME_DATE_IS + date);
        //如果不为空，则使用，为空，则重新访问网络下载到缓存
        if (object != null) {
            Logger.e("ZhihuContentManager", "object不为空");
            return object;
        } else {

            HttpManager.getInstance().connectRestAPI(date);
        }
        return null;
    }

    public Object getContentFromId(Integer contentId) {
        //首先读取缓存中的数据
        Object object = CacheAtFileManage.getObjectAtFile(C.CacheFileString.CONTENT_CACHE_AND_ID_IS + contentId);
        //如果不为空，则使用，为空，则重新访问网络下载到缓存
        if (object != null) {
            Logger.e("ZhihuContentManager getContentFromId", "object不为空");
            return object;
        } else {
            Logger.e("ZhihuContentManager getContentFromId", "object为空");
            HttpManager.getInstance().loadContentDataFromId(contentId);
            return null;
        }
    }

}
