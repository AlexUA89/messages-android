package com.alexua.messages.core;

import android.app.Application;
import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created with IntelliJ IDEA.
 * User: Oleksii Khom
 * Date: 30.08.13
 * Time: 13:04
 * To change this template use File | Settings | File Templates.
 */
public class ContextProvider extends Application {

    private static ContextProvider instance;

    private static ObjectMapper objectMapper;

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

    synchronized public static ObjectMapper getObjectMapper(){
        if(objectMapper == null){
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }
}
