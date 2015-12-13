package com.alexua.messages.ui.base;

import android.app.Application;
import android.content.Context;

import com.alexua.messages.utils.LogUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Oleksii Khom
 * Date: 30.08.13
 * Time: 13:04
 */
public class AlexApplication extends Application {

    private static AlexApplication instance;

    @Override
    public void onCreate() {
        LogUtils.log("application onCreate");
        super.onCreate();
        instance = this;
    }

    public static AlexApplication getInstance() {
        return instance;
    }

    synchronized public static Context getAppContext() {
        return getInstance().getApplicationContext();
    }
}
