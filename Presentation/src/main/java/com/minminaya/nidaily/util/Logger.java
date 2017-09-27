package com.minminaya.nidaily.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * Description: 通用的Log管理
 * 开发阶段LOGLEVEL = 6
 * 发布阶段LOGLEVEL = -1
 */

public class Logger {

    private static int LOGLEVEL = 6;
    private static int VERBOSE = 1;
    private static int DEBUG = 2;
    private static int INFO = 3;
    private static int WARN = 4;
    private static int ERROR = 5;

    /**
     *  设置当前log开关
     *  @param flag true为开发阶段打开log，false为发布阶段关闭log
     * */
    public static void setDevelopMode(boolean flag) {
        if(flag) {
            LOGLEVEL = 6;
        } else {
            LOGLEVEL = -1;
        }
    }

    public static void v(String tag, String msg) {
        if(LOGLEVEL > VERBOSE && !TextUtils.isEmpty(msg)) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if(LOGLEVEL > DEBUG && !TextUtils.isEmpty(msg)) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if(LOGLEVEL > INFO && !TextUtils.isEmpty(msg)) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if(LOGLEVEL > WARN && !TextUtils.isEmpty(msg)) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if(LOGLEVEL > ERROR && !TextUtils.isEmpty(msg)) {
            Log.e(tag, msg);
        }
    }

}
