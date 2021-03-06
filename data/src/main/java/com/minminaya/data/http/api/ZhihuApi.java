package com.minminaya.data.http.api;

import com.minminaya.data.http.model.column.SectionItemModel;
import com.minminaya.data.http.model.column.SectionsModel;
import com.minminaya.data.http.model.content.ContentModel;
import com.minminaya.data.http.model.home.BeforeModel;
import com.minminaya.data.http.model.home.LatestInfoModel;
import com.minminaya.data.http.model.hot.HotModel;
import com.minminaya.data.http.model.topic.ThemeItemModel;
import com.minminaya.data.http.model.topic.TopicItemModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 知乎api
 * Created by Niwa on 2017/9/28.
 */

public interface ZhihuApi {

    /**
     * 加载过往消息
     * <p>URL: http://news.at.zhihu.com/api/4/news/before/20131119</p>
     * <p>如果需要查询 11 月 18 日的消息，before 后的数字应为 20131119</p>
     * <p>知乎日报的生日为 2013 年 5 月 19 日，若 before 后数字小于 20130520 ，只会接收到空消息</p>
     * <p>输入的今日之后的日期仍然获得今日内容，但是格式不同于最新消息的 JSON 格式</p>
     *
     * @param date 日期
     */
    @GET("4/news/before/{path}")
    Observable<BeforeModel> loadBeforeHomeInfo(
            @Path("path") String date
    );

    @GET("4/news/{id}")
    Observable<ContentModel> loadContent(
            @Path("id") Integer id
    );

    /**
     * 获取topic页的item
     */
    @GET("4/themes")
    Observable<TopicItemModel> loadTopicItem();

    /**
     * 获取topic页的item
     */
    @GET("4/theme/{path}")
    Observable<ThemeItemModel> loadThemeItem(
            @Path("path") Integer id
    );

    /**
     * 获取column页的item
     */
    @GET("4/sections")
    Observable<SectionsModel> loadSectionItem();

    /**
     * 根据id获取Section页的item
     */
    @GET("4/section/{id}")
    Observable<SectionItemModel> loadSectionItemContentForId(
            @Path("id") Integer sectionId
    );

    /**
     * 获取Hot页的item
     */
    @GET("3/news/hot")
    Observable<HotModel> loadHotModel();

    /**
     * 获取letest页的最新数据，用来构造头部滚动view
     */
    @GET("4/news/latest")
    Observable<LatestInfoModel> loadLatestInfoModel();


}
