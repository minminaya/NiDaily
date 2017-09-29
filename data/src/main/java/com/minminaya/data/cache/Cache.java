package com.minminaya.data.cache;

import com.blankj.utilcode.util.CacheUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储CacheUtils的工具类
 * Created by Niwa on 2017/9/29.
 */

public class Cache {
    public static Map<String, CacheUtils> map = new HashMap<>();

    /**
     * 将CacheUtils对象存进Map中
     *
     * @param cacheFile 将文件夹名称作为key
     */
    public static void putCacheUtilsAtHashMap(String cacheFile) {
        //新建CacheUtils对象
        CacheUtils cacheUtils = CacheUtils.getInstance(cacheFile);
        //存入Map中
        map.put(cacheFile, cacheUtils);
    }

    /**
     * 从map中得到CacheUtils对象
     *
     * @param cacheFile key
     */
    public static CacheUtils getCacheUtilsAtHashMap(String cacheFile) {
        return map.get(cacheFile);
    }
}
