package com.alexua.messages.core.preferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.*;
import android.preference.PreferenceManager;

import com.alexua.messages.core.AppLog;
import com.alexua.messages.core.ContextProvider;

/**
 * Created with IntelliJ IDEA.
 * User: Алексей
 * Date: 24.10.13
 * Time: 11:15
 * To change this template use File | Settings | File Templates.
 */
public class SharedPrefHelper {

    public static final String TAG = SharedPrefHelper.class.getCanonicalName();

    // -------------------------------------------------------BASIC FUNCTIONS------------------------------------------------------------
    private static SharedPreferences getSHP() {
        AppLog.D(TAG, "getSHP()");
        return PreferenceManager.getDefaultSharedPreferences(ContextProvider.getAppContext());
    }

    private static Editor getEditor() {
        AppLog.D(TAG, "getEditor()");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ContextProvider.getAppContext());
        return sharedPreferences.edit();
    }

    // -------------------------------------------------------PUBLIC METHODS------------------------------------------------------------
    public static boolean getServiceState() {
        return getSHP().getBoolean(SPConstants.SERVISE_STATE, false);
    }

    public static void setServiceState(boolean state) {
        getEditor().putBoolean(SPConstants.SERVISE_STATE, state).commit();
    }


    // -------------------------------------------------------USER METHODS------------------------------------------------------------

    public static void setUserName(String name) {
        getEditor().putString(SPConstants.NAME, name).commit();
    }

    public static String getUserName() {
        return getSHP().getString(SPConstants.NAME, "");
    }

    public static void setEmail(String email) {
        getEditor().putString(SPConstants.EMAIL, email).commit();
    }

    public static String getEmail() {
        return getSHP().getString(SPConstants.EMAIL, "");
    }

    public static void setToken(String token) {
        getEditor().putString(SPConstants.SESSION_TOKEN, token).commit();
    }

    public static String getToken() {
        return getSHP().getString(SPConstants.SESSION_TOKEN, "");
    }


}
