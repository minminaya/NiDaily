package com.minminaya.nidaily;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.minminaya.library.util.Logger;

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
        Utils.init(this);
        Logger.setDevelopMode(true);
    }
}
