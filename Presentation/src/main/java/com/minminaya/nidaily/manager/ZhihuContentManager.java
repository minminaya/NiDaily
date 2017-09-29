package com.minminaya.nidaily.manager;

import com.minminaya.data.cache.CacheAtFileManage;
import com.minminaya.data.http.model.home.BeforeModel;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.C;

/** 知乎RestAPI数据的管理类
 * <p>策略如下，如果本地缓存有对应的数据，则获得本地数据，否则请求网络得到对应数据</p>
 * Created by Niwa on 2017/9/29.
 */

public class ZhihuContentManager {

    public static void getData() {
        //首先读取缓存中的数据
        Object object = CacheAtFileManage.getObjectAtFile(C.CacheFileString.homeCacheFileName);
        //如果不为空，则使用，为空，则重新访问网络下载到缓存
        if (object != null) {
            BeforeModel beforeModel = (BeforeModel) object;
            Logger.e("ZhuhuContentManager", "ZhuhuContentManager里：" + beforeModel.getDate());
            // TODO: 2017/9/29 对数据的处理
        }else{
            HttpManager httpManager = new HttpManager();
            httpManager.connectRestAPI();
            // TODO: 2017/9/29 对数据的处理
            getData();
        }
    }
}
