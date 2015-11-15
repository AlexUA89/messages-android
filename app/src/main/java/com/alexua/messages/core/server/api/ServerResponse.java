package com.alexua.messages.core.server.api;

import com.alexua.messages.core.database.datas.DBStorable;

import java.util.HashMap;
import java.util.List;

/**
 * Created by AlexUA on 11/15/2015.
 */
public final class ServerResponse {

    private final int statusCode;

    private final boolean success;

    private final List<HashMap<String, String>> errors;

    private final HashMap<String, String> data;

    private final List<HashMap<String, String>> dataList;

    protected ServerResponse(int statusCode, boolean success, List<HashMap<String, String>> errors, HashMap<String, String> data, List<HashMap<String, String>> dataList) {
        this.statusCode = statusCode;
        this.success = success;
        this.errors = errors;
        this.data = data;
        this.dataList = dataList;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<HashMap<String, String>> getErrors() {
        return errors;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    public List<HashMap<String, String>> getDataList() {
        return dataList;
    }

    @Override
    public String toString() {
        return "statusCode: " + statusCode + " success:" + success + " errors: " + errors + " data: " + data + " dataList: " + dataList;
    }
}
