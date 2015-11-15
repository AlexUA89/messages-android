package com.alexua.messages.core.server;

import com.alexua.messages.core.server.api.JsonRequest;
import com.alexua.messages.core.server.api.ServerResponse;
import com.android.volley.Response;

import java.util.HashMap;

/**
 * Created by AlexUA on 11/15/2015.
 */
public class ServerAdapter {

    private static final String serverUrl = "http://31.220.107.60:3000";

    public static void singinReques(String email, String password, Response.Listener<ServerResponse> listener, Response.ErrorListener errorListener) {
        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        new JsonRequest(JsonRequest.POST, serverUrl + "/auth/signin", params, listener,errorListener).execute();
    }


}
