package com.minminaya.data.http.model.home;

import java.io.Serializable;

public class TopStoriesBean implements Serializable {

    private static final long serialVersionUID = 156465456464998819L;

    /**
     * image : https://pic4.zhimg.com/v2-0fabfc1f25534008794a44735ce445d3.jpg
     * type : 0
     * id : 9649478
     * ga_prefix : 101111
     * title : 鹿晗的粉丝帝国：我们只听、只信、只看鹿晗
     */

    private String image;
    private int type;
    private int id;
    private String ga_prefix;
    private String title;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
