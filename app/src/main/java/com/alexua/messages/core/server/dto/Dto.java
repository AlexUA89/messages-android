package com.alexua.messages.core.server.dto;

import javax.validation.constraints.NotNull;

public class Dto {

    @NotNull
    protected long code;

    @NotNull
    protected String token;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
