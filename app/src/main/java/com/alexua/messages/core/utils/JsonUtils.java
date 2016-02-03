package com.alexua.messages.core.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class JsonUtils {

    public static final String TAG = JsonUtils.class.getCanonicalName();

    public static HashMap<String, String> getMapFromObject(JSONObject jObj) throws JSONException {
        if(jObj == null) return null;
        HashMap<String, String> result = new HashMap<>();
        Iterator<String> iter = jObj.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            result.put(key, jObj.getString(key));
        }
        return result;
    }


    public static List<HashMap<String, String>> getListOfObjects(JSONArray jArray) throws JSONException {
        if(jArray == null) return null;
        List<HashMap<String, String>> result = new ArrayList<>();
        for (int i = 0; i < jArray.length(); i++) {
            JSONObject actor = jArray.getJSONObject(i);
            result.add(getMapFromObject(actor));
        }
        return result;
    }

    public static JSONObject mapToObject(HashMap<String, String> map){
        return new JSONObject(map);
    }

}
