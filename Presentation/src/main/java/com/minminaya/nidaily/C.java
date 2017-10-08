package com.minminaya.nidaily;


/**
 * 模仿R包，存放所有的静态常量
 * Created by Niwa on 2017/9/27.
 */

public class C {




    public static class CacheFileString {
        public static final String HOME_CACHE_FILE_NAME_DATE_IS = "home_cache_file_name_date_is_";
        public static final String HOT_CACHE_FILE_NAME_DATE_IS = "hot_cache_file_name_date_is_";
        public static final String CONTENT_CACHE_AND_ID_IS = "content_cache_and_id_is_";
        public static final String TOPIC_CACHE_ITEM = "topic_cache_item";
        public static final String THEME_CACHE_ITEM = "theme_cache_item_is_";
        public static final String SECTION_CACHE_ITEM = "section_cache_item_is_";
        public static final String COLUMN_CACHE_ITEM = "column_cache_item";

    }

    public static class ActivityLoadString {
        public static final String LOAD_CONTENT_ACTIVITY = "load_content_activity";
    }

    public static class EventBusString {
        public static final int FROM_HTTPMANAGER_TO_ZHIHU_CONTENT_MANAGER = 0x68479631;
        public static final int TOPIC_CACHE_ITEM_DOWNLOAD_SUCCESSFUL = 0x664fff;
        public static final int THEME_CACHE_ITEM_DOWNLOAD_SUCCESSFUL = 0x66ee4fff;
        public static final int SECTION_CACHE_ITEM_DOWNLOAD_SUCCESSFUL = 0x66aa4fff;
        public static final int COLUMN_CACHE_ITEM_DOWNLOAD_SUCCESSFUL = 0x66fe4fff;
        public static final int HOT_CACHE_ITEM_DOWNLOAD_SUCCESSFUL = 0x66fe7fff;
    }

    public static class BundleKeyString {
        public static final String TOPIC_RECYCLER_VIEW_TO_THEME_ACTIVITY = "from_topic_recycler_view_to_theme_activity";
        public static final String COLUMN_RECYCLER_VIEW_TO_SECTION_ACTIVITY = "from_column_recycler_view_to_section_activity";
    }


}
