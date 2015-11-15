package com.alexua.messages.core.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.alexua.messages.core.AppLog;
import com.alexua.messages.core.preferences.SharedPrefHelper;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 9/4/13
 * Time: 7:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class OnBootReceiver extends BroadcastReceiver {

    public static final String TAG = OnBootReceiver.class.getCanonicalName();
    private static final String BOOT = "android.intent.action.BOOT_COMPLETED";
    private static final String BOOT1 = "android.intent.action.QUICKBOOT_POWERON";
    private static final String SHUT = "android.intent.action.ACTION_SHUTDOWN";
    private static final String SHUT1 = "android.intent.action.QUICKBOOT_POWEROFF";

    @Override
    public void onReceive(Context context, Intent intent) {
        AppLog.D(TAG, "INTENT");
        if (intent != null && intent.getAction() != null &&
                ((BOOT.compareToIgnoreCase(intent.getAction()) == 0) || (BOOT1.compareToIgnoreCase(intent.getAction()) == 0))) {

            AppLog.D(TAG, intent.getAction());
            GeolocationService.startService();

        } else if (intent != null && intent.getAction() != null &&
                ((SHUT.compareToIgnoreCase(intent.getAction()) == 0) || (SHUT1.compareToIgnoreCase(intent.getAction()) == 0))) {
            AppLog.D(TAG, intent.getAction());

            SharedPrefHelper.setServiceState(false);

        }

    }
}
