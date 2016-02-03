package com.alexua.messages.core.server.dto;

import android.support.annotation.NonNull;

public class MessageDto extends Dto {

    @NonNull
    private String message;

    private String toUserId;

    private String chatGroupId;

    private Double xCoord;

    private Double yCoord;


    public MessageDto() {
    }

    public MessageDto(@NonNull String message, String toUserId, String chatGroupId, Double xCoord, Double yCoord) {
        this.message = message;
        this.toUserId = toUserId;
        this.chatGroupId = chatGroupId;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    public void setMessage(@NonNull String message) {
        this.message = message;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getChatGroupId() {
        return chatGroupId;
    }

    public void setChatGroupId(String chatGroupId) {
        this.chatGroupId = chatGroupId;
    }

    public Double getxCoord() {
        return xCoord;
    }

    public void setxCoord(Double xCoord) {
        this.xCoord = xCoord;
    }

    public Double getyCoord() {
        return yCoord;
    }

    public void setyCoord(Double yCoord) {
        this.yCoord = yCoord;
    }
}
