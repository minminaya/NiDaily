package com.minminaya.data.http.model.column;

import java.io.Serializable;
import java.util.List;

/** 栏目列表
 * Created by Niwa on 2017/10/7.
 */

public class SectionsModel implements Serializable{
    private static final long serialVersionUID = 9654564941399411L;

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }


}
