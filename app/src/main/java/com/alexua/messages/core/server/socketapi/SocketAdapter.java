package com.alexua.messages.core.server.socketapi;

import com.alexua.messages.core.AppLog;
import com.alexua.messages.core.preferences.SharedPrefHelper;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by AlexUA on 11/19/2015.
 */
public class SocketAdapter {

    private static final String TAG = SocketAdapter.class.getCanonicalName();
    private static WebsocketClientEndpoint clientEndPoint;

    private static boolean createConnection() {
        if (SharedPrefHelper.getToken(null) == null) {
            return false;
        }
        try {
            clientEndPoint = new WebsocketClientEndpoint(new URI("ws://31.220.107.60:3000/?token=" + SharedPrefHelper.getToken("")));
            clientEndPoint.addSocketListener(socketListener);
        } catch (URISyntaxException e) {
            AppLog.E(TAG, e);
            return false;
        }
        return true;
    }

    public synchronized static WebsocketClientEndpoint getConnection() {
        if (clientEndPoint != null || createConnection()) {
            return clientEndPoint;
        }
        return null;
    }

    public synchronized static void closeConnection() {
        if (clientEndPoint != null) {
            clientEndPoint.close();
            clientEndPoint = null;
            AppLog.D(TAG, "Socket connection closed from android side");
        } else {
            AppLog.D(TAG, "Socket connection already closed");
        }

    }

    public static void sendMessage(String message) {
        if (clientEndPoint != null) {
            clientEndPoint.sendMessage(message);
            AppLog.D(TAG, "Message have sent: " + message);
        }
        throw new IllegalAccessError("Socket closed");
    }

    private static WebsocketClientEndpoint.SocketListener socketListener = new WebsocketClientEndpoint.SocketListener() {
        @Override
        public void handleMessage(String message) {


        }

        @Override
        public void handleOnOpen() {
            AppLog.D(TAG, "Socket connection openned");
        }

        @Override
        public void handleOnClose(String message) {
            AppLog.D(TAG, "Socket connection closed");
            clientEndPoint.close();
            clientEndPoint = null;
        }
    };


}
