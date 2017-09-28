package com.minminaya.data;

import android.app.Application;

/**
 * data模块的Application，用于提供Application的Context
 * Created by Niwa on 2017/9/28.
 */

public class DataModuleApplication extends Application {

    private static DataModuleApplication INSTANCE;

    public static DataModuleApplication getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }
}
