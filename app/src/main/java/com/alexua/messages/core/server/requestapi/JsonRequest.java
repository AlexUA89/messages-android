package com.alexua.messages.core.server.requestapi;

import com.alexua.messages.core.AppLog;
import com.alexua.messages.core.ContextProvider;
import com.alexua.messages.core.server.dto.Dto;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.Map;

final class JsonRequest<ReqT extends Dto, ResT extends Dto> extends Request<ResT> {

    public static final String TAG = JsonRequest.class.getCanonicalName();
    public static int GET = Method.GET;
    public static int POST = Method.POST;
    private static RequestQueue queue;
    private Class<ResT> klass;

    private Map<String, String> params = null;
    private Response.Listener<ResT> mListener = null;
    private ReqT body;

    private synchronized static RequestQueue getQueue() {
        if (queue == null) {
            queue = Volley.newRequestQueue(ContextProvider.getAppContext());
        }
        return queue;
    }

    public JsonRequest(int method, String url, Map<String, String> params, ReqT body, Class<ResT> klass, Response.Listener<ResT> listener, Response.ErrorListener errorListener) {
        super(method, packParametersToUrl(method, url, params), errorListener);
        this.params = params;
        this.mListener = listener;
        this.body = body;
        this.klass = klass;
    }

    @Override
    protected Response<ResT> parseNetworkResponse(NetworkResponse response) {
        int statusCode = response.statusCode;
        ResT resData = null;
        try {
            resData = ContextProvider.getObjectMapper().readValue(new String(response.data), klass);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.error(new VolleyError(new String(e.getMessage())));
        }
        resData.setStatusCode(statusCode);
        return Response.success(resData, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void onFinish() {
        super.onFinish();
        mListener = null;
    }

    @Override
    protected void deliverResponse(ResT response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }

    public void execute() {
        getQueue().add(this);
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            return ContextProvider.getObjectMapper().writeValueAsBytes(body);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            AppLog.E(TAG, e);
        }
        return new byte[0];
    }

    private static String packParametersToUrl(int method, String url, Map<String, String> params) {
        if (Method.GET == method) {
            String urlParams = "";
            for(String key : params.keySet()){
                if(urlParams.isEmpty()) {
                    urlParams += "?";
                } else {
                    urlParams += "&";
                }
                urlParams += key+"="+params.get(key);
            }
            return url + urlParams;
        } else {
            return url;
        }
    }
}
