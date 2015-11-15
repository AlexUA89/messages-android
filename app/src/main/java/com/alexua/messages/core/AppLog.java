package com.alexua.messages.core;

import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Oleksii Khom
 * Date: 30.08.13
 * Time: 13:41
 * To change this template use File | Settings | File Templates.
 */
public final class AppLog {

    public static void E(String TAG, Throwable error) {
        System.out.println(TAG + "  :  " + error.getMessage());
        error.printStackTrace();
        Log.e(TAG, error.getMessage());
    }

    public static void D(String TAG, String message) {
        System.out.println(TAG + "  :  " + message);
        Log.d(TAG, message);
    }

}
