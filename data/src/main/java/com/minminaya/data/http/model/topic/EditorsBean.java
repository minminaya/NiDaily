package com.minminaya.data.http.model.topic;

import java.io.Serializable;

public class EditorsBean implements Serializable {
    private static final long serialVersionUID = 222111115645816L;
    /**
     * url : http://www.zhihu.com/people/moheng-esther
     * bio : 树上的女爵
     * id : 79
     * avatar : http://pic1.zhimg.com/0a6456810_m.jpg
     * name : 刘柯
     */

    private String url;
    private String bio;
    private int id;
    private String avatar;
    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
