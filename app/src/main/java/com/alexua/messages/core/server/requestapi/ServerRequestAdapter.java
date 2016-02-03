package com.alexua.messages.core.server.requestapi;

import com.alexua.messages.R;
import com.alexua.messages.core.ContextProvider;
import com.alexua.messages.core.database.datas.Message;
import com.alexua.messages.core.preferences.SharedPrefHelper;
import com.alexua.messages.core.server.requestapi.JsonRequest;
import com.alexua.messages.core.server.requestapi.ServerResponse;
import com.android.volley.Response;

import java.util.HashMap;

public class ServerRequestAdapter {

    private static final String serverUrl = ContextProvider.getAppContext().getResources().getString(R.string.server_url);

    public static void singinRequest(String email, String password, Response.Listener<ServerResponse> listener, Response.ErrorListener errorListener) {
        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        new JsonRequest(JsonRequest.POST, serverUrl + "/auth/signin", params, listener, errorListener).execute();
    }

    public static void sendMessage(Message message, Response.Listener<ServerResponse> listener, Response.ErrorListener errorListener) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", SharedPrefHelper.getToken(""));
        params.put("message", message.getMessage());
        if (message.getToUserId() != null) {
            params.put("toUserId", message.getToUserId());
        }
        if (message.getChatGroupId() != null) {
            params.put("chatGroupId", message.getChatGroupId());
        }
        if (message.getXcoord() != null && message.getYcoord() != null) {
            params.put("xCoord", message.getXcoord().toString());
            params.put("yCoord", message.getYcoord().toString());
        }
        new JsonRequest(JsonRequest.POST, serverUrl + "/messages/send", params, listener, errorListener).execute();
    }

    public static void getPrivateMessages(long time, Response.Listener<ServerResponse> listener, Response.ErrorListener errorListener) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", SharedPrefHelper.getToken(""));
        params.put("time", "" + time);
        new JsonRequest(JsonRequest.GET, serverUrl + "/messages/getPrivate", params, listener, errorListener).execute();
    }

}
