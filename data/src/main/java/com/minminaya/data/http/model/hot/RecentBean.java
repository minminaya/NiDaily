package com.minminaya.data.http.model.hot;

import java.io.Serializable;

public class RecentBean implements Serializable {

    private static final long serialVersionUID = 153154564645645646L;

    /**
     * news_id : 9640959
     * url : http://news-at.zhihu.com/api/2/news/9640959
     * thumbnail : https://pic2.zhimg.com/v2-10298dfdd5de0e9c39413371396eea49.jpg
     * title : 假期旅行马上结束的你们，是不是打算回去就分手？
     */

    private int news_id;
    private String url;
    private String thumbnail;
    private String title;

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}