package com.minminaya.data.http.model.home;

import java.io.Serializable;
import java.util.List;

/** 过往消息的model,home页的接口model
 * Created by Niwa on 2017/9/28.
 */

public class BeforeModel implements Serializable{

    private static final long serialVersionUID = 1564654564645645646L;

    private String date;
    private List<StoriesBean> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

}
