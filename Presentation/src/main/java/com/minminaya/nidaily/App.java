package com.minminaya.nidaily;

import android.app.Application;

import com.minminaya.nidaily.util.Logger;

/**
 * Created by Niwa on 2017/9/27.
 */

public class App extends Application {
    private static App INSTANCE;

    public static App getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        Logger.setDevelopMode(true);
    }
}
