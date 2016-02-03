package com.alexua.messages.core.server.requestapi;

import com.alexua.messages.core.AppLog;
import com.alexua.messages.core.ContextProvider;
import com.alexua.messages.core.utils.JsonUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class JsonRequest extends Request<ServerResponse>{

    public static final String TAG = JsonRequest.class.getCanonicalName();
    public static int GET = Method.GET;
    public static int POST = Method.POST;
    private static RequestQueue queue;

    private Map<String, String> params = null;
    private Response.Listener<ServerResponse> mListener = null;

    private synchronized static RequestQueue getQueue() {
        if (queue == null) {
            queue = Volley.newRequestQueue(ContextProvider.getAppContext());
        }
        return queue;
    }

    public JsonRequest(int method, String url, Map<String, String> params, Response.Listener<ServerResponse> listener, Response.ErrorListener errorListener) {
        super(method, packParametersToUrl(method, url, params), errorListener);
        this.params = params;
        this.mListener = listener;
    }

    @Override
    protected Response<ServerResponse> parseNetworkResponse(NetworkResponse response) {
        int statusCode = response.statusCode;
        JSONObject jObject;
        boolean success;
        List<HashMap<String,String>> errors = null;
        HashMap<String, String> data = null;
        List<HashMap<String, String>> dataList = null;
        try {
            jObject = new JSONObject(new String(response.data));
            success = jObject.getBoolean("success");
            errors = JsonUtils.getListOfObjects(jObject.optJSONArray("errors"));
            Object jData = jObject.opt("data");
            if (jData instanceof JSONObject) {
               data = JsonUtils.getMapFromObject((JSONObject)jData);
            } else if (jData instanceof JSONArray){
                dataList = JsonUtils.getListOfObjects((JSONArray)jData);
            }
        } catch (JSONException e) {
            AppLog.E(TAG, e);
            return Response.error(new VolleyError(new String(response.data)));
        }
        return Response.success(new ServerResponse(statusCode, success, errors, data, dataList), HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void onFinish() {
        super.onFinish();
        mListener = null;
    }

    @Override
    protected void deliverResponse(ServerResponse response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }

    public void execute() {
        getQueue().add(this);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return this.params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
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
