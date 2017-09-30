package com.minminaya.data.cache;

import com.blankj.utilcode.util.Utils;
import com.minminaya.library.util.DataUtils;


/** 缓存文件管理类
 * Created by Niwa on 2017/9/29.
 */

public class CacheAtFileManage {

    /**
     * 通过序列化文件保存对象到本地文件
     *
     * @param object    要保存的对象
     * @param cacheName 保存的文件名
     */
    public static boolean putObjectAtFile(Object object, String cacheName) {
        boolean isWrited = DataUtils.writeObject(object, Utils.getApp().getCacheDir(), cacheName);
        return isWrited;
    }

    /**
     *  得到缓存的对象文件
     *  @param cacheName 缓存文件名，目录默认在包下的cache目录
     *  @return object
     * */
    public static Object getObjectAtFile(String cacheName) {

        Object object = DataUtils.readObject(Utils.getApp().getCacheDir().getAbsolutePath(), cacheName);

        return object;
    }

}
