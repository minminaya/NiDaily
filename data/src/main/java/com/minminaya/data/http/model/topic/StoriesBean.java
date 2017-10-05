package com.minminaya.data.http.model.topic;

import java.io.Serializable;
import java.util.List;

public  class StoriesBean implements Serializable{
    private static final long serialVersionUID = 68423347778999112L;
        /**
         * type : 0
         * id : 7483384
         * title : 更多日常心理学，都在读读日报里
         * images : ["http://pic1.zhimg.com/56d1d1202077c7b5b0e48e3b7d3ebb60_t.jpg"]
         */

        private int type;
        private int id;
        private String title;
        private List<String> images;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }