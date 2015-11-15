package com.alexua.messages.core;

import android.app.Application;
import android.content.Context;

/**
 * Created with IntelliJ IDEA.
 * User: Oleksii Khom
 * Date: 30.08.13
 * Time: 13:04
 * To change this template use File | Settings | File Templates.
 */
public class ContextProvider extends Application {

    private static ContextProvider instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    synchronized public static ContextProvider getInstance() {
        return instance;
    }

    synchronized public static Context getAppContext() {
        return getInstance().getApplicationContext();
    }
}
