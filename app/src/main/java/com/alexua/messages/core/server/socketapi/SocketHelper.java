package com.alexua.messages.core.server.socketapi;

import com.alexua.messages.R;
import com.alexua.messages.core.AppLog;
import com.alexua.messages.core.ContextProvider;
import com.alexua.messages.core.preferences.SharedPrefHelper;
import com.alexua.messages.core.server.dto.Dto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.websocket.DeploymentException;
import javax.websocket.MessageHandler;

public class SocketHelper {

    private static final String TAG = SocketHelper.class.getCanonicalName();
    private static WebsocketClientEndpoint clientEndPoint;
    private static String socketServerUrl = ContextProvider.getAppContext().getResources().getString(R.string.socket_server_url);
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private static MessageHandler listener;

    public static boolean checkConnection() {
        if (SharedPrefHelper.getToken(null) == null) {
            return false;
        }
        if (clientEndPoint == null) {
            try {
                clientEndPoint = new WebsocketClientEndpoint(new URI(socketServerUrl + "/?token=" + SharedPrefHelper.getToken("")), socketListener);
            } catch (URISyntaxException | DeploymentException | IOException e) {
                AppLog.E(TAG, e);
                return false;
            }
        }
        return true;
    }

    public synchronized static void closeConnection() {
        if (clientEndPoint != null) {
            executorService.submit(() -> {
                clientEndPoint.close();
                clientEndPoint = null;
                AppLog.D(TAG, "Socket connection closed from android side");
            });
        } else {
            AppLog.D(TAG, "Socket connection already closed");
        }
    }

    public static void sendMessage(Dto message, MessageHandler msgH) {
        Long code = System.currentTimeMillis();
        message.setCode(code);
        listener = msgH;
        try {
            final String msg = ContextProvider.getObjectMapper().writeValueAsString(message);
            executorService.submit(() -> {
                if (checkConnection()) {
                    clientEndPoint.sendMessage(msg);
                    AppLog.D(TAG, "Message have sent: " + msg);
                } else {
                    AppLog.D(TAG, "Can not open connection");
                }

            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static WebsocketClientEndpoint.SocketListener socketListener = new WebsocketClientEndpoint.SocketListener() {
        @Override
        public void handleMessage(String message) {
            AppLog.D(TAG, "Received message");
            listener.receiveMessage(message);
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

    public static abstract class MessageHandler {
        public abstract void receiveMessage(String message);
    }


}
