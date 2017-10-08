package com.minminaya.data.http.model.hot;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Niwa on 2017/10/8.
 */

public class HotModel implements Serializable{
    private static final long serialVersionUID = 234654564645645646L;


    private List<RecentBean> recent;

    public List<RecentBean> getRecent() {
        return recent;
    }

    public void setRecent(List<RecentBean> recent) {
        this.recent = recent;
    }


}
