package com.alexua.messages.core.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.alexua.messages.core.AppLog;
import com.alexua.messages.ui.base.AlexApplication;
import com.alexua.messages.core.preferences.SharedPrefHelper;

/**
 * Created with IntelliJ IDEA.
 * User: Алексей
 * Date: 24.10.13
 * Time: 10:59
 * To change this template use File | Settings | File Templates.
 */
public class GeolocationService extends Service {

    public static final String TAG = GeolocationService.class.getCanonicalName();

    public static void startService() {
        if (!SharedPrefHelper.getServiceState()) {
            Intent i = new Intent(AlexApplication.getAppContext(), GeolocationService.class);
            AlexApplication.getAppContext().startService(i);
        }
    }

    public void onCreate() {
        super.onCreate();
        AppLog.D(TAG, "GeolocationService onCreate");
        SharedPrefHelper.setServiceState(true);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        AppLog.D(TAG, "GeolocationService onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        AppLog.D(TAG, "GeolocationService onDestroy");
        SharedPrefHelper.setServiceState(false);
        super.onDestroy();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        AppLog.D(TAG, "GeolocationService onStartCommand");

        GeoTimer timer = new GeoTimer();
        timer.sendEmptyMessageDelayed(0, 600 * 1000);

        return Service.START_STICKY;

    }

    @Override
    public boolean onUnbind(Intent intent) {
        AppLog.D(TAG, "GeolocationService onUnbind");
        return super.onUnbind(intent);
    }

    public static class GeoTimer extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {

            //TODO send my geolocation

            sendEmptyMessageDelayed(0, 600 * 1000);
        }
    }


}
