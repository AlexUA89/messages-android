package com.alexua.messages.core.database.datas;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.text.TextUtils;

import com.alexua.messages.core.AppLog;
import com.alexua.messages.core.database.constants.DBConstants;
import com.alexua.messages.core.database.constants.Tables;

import java.util.HashMap;

public class Message extends DBStorable {

    public static final int TypeID = 1;
    private static final String TAG = Message.class.getCanonicalName();
    private long rowId = DBConstants.DEFAULT_ROW_ID;
    private String message = null;
    private Double xcoord = null;
    private Double ycoord = null;
    private String toUserId = null;
    private String chatGroupId = null;
    private String userId = null;
    private String userName = null;
    private String time = null;
    private String serverId = null;

    public Message(HashMap<String, String> data) {
        this(data.get("message"), stringToDouble(data.get("xCoord")), stringToDouble(data.get("yCoord")),
                data.get("toUserId"), data.get("chatGroupId"), data.get("userId"), data.get("userName"), data.get("time"), data.get("_id"));

    }

    public Message(long rowId, String message, Double xcoord, Double ycoord, String toUserId, String chantGroupId, String userId, String user_name, String time, String serverId) {
        if (rowId < DBConstants.DEFAULT_ROW_ID) {
            throw new IllegalArgumentException("rowId can not be " + rowId);
        }
        this.rowId = rowId;
        if (TextUtils.isEmpty(message)) {
            throw new IllegalArgumentException("message can not be: " + message + ";");
        }
        this.message = message;

        if (TextUtils.isEmpty(toUserId) && TextUtils.isEmpty(chantGroupId) && (xcoord == null || ycoord == null || xcoord.isNaN() || ycoord.isNaN())) {
            throw new IllegalArgumentException("chat group or toUserId or x/ycoords should be defined");
        }
        if (xcoord == null || xcoord.isNaN()) {
            this.xcoord = null;
        } else {
            this.xcoord = xcoord;
        }
        if (ycoord == null || ycoord.isNaN()) {
            this.ycoord = null;
        } else {
            this.ycoord = ycoord;
        }
        this.toUserId = toUserId;
        this.chatGroupId = chantGroupId;

        if (TextUtils.isEmpty(userId)) {
            throw new IllegalArgumentException("userId can not be: " + userId + ";");
        }
        this.userId = userId;
        if (TextUtils.isEmpty(user_name)) {
            throw new IllegalArgumentException("userName can not be: " + user_name + ";");
        }
        this.userName = user_name;
        if (TextUtils.isEmpty(time)) {
            throw new IllegalArgumentException("time can not be: " + time + ";");
        }
        this.time = time;
        this.serverId = serverId;
    }

    public Message(String message, Double xcoord, Double ycoord, String toUserId, String chantGroupId, String userId, String user_name, String time, String serverId) {
        this(DBConstants.DEFAULT_ROW_ID, message, xcoord, ycoord, toUserId, chantGroupId, userId, user_name, time, serverId);
    }

    public Message(Message message) {
        this(message.getRowID(), message.getMessage(), message.getXcoord(), message.getYcoord(), message.getToUserId(), message.getChatGroupId(), message.getUserId(), message.getUserName(), message.getTime(), message.getServerId());
    }

    public Message(Parcel in) {
        this(in.readLong(), in.readString(), in.readDouble(), in.readDouble(), in.readString(), in.readString(), in.readString(), in.readString(), in.readString(), in.readString());
    }

    public Message(Cursor c) {
        this(c.getLong(c.getColumnIndex(Tables.MESSAGES.ID)),
                c.getString(c.getColumnIndex(Tables.MESSAGES.MESSAGE)),
                c.getDouble(c.getColumnIndex(Tables.MESSAGES.XCOORD)),
                c.getDouble(c.getColumnIndex(Tables.MESSAGES.YCOORD)),
                c.getString(c.getColumnIndex(Tables.MESSAGES.TO_USER_ID)),
                c.getString(c.getColumnIndex(Tables.MESSAGES.CHAT_GROUP_ID)),
                c.getString(c.getColumnIndex(Tables.MESSAGES.USER_ID)),
                c.getString(c.getColumnIndex(Tables.MESSAGES.USER_NAME)),
                c.getString(c.getColumnIndex(Tables.MESSAGES.TIME)),
                c.getString(c.getColumnIndex(Tables.MESSAGES.SERVER_ID)));
    }

    @Override
    public long getRowID() {
        return this.rowId;
    }

    @Override
    public int getTypeID() {
        return TypeID;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(Tables.MESSAGES.MESSAGE, message);
        values.put(Tables.MESSAGES.XCOORD, xcoord);
        values.put(Tables.MESSAGES.YCOORD, ycoord);
        values.put(Tables.MESSAGES.TO_USER_ID, toUserId);
        values.put(Tables.MESSAGES.CHAT_GROUP_ID, chatGroupId);
        values.put(Tables.MESSAGES.USER_ID, userId);
        values.put(Tables.MESSAGES.USER_NAME, userName);
        values.put(Tables.MESSAGES.TIME, time);
        values.put(Tables.MESSAGES.SERVER_ID, serverId);
        return values;
    }

    @Override
    public String toJsonString() {
        StringBuilder builder = new StringBuilder("{");
        builder.append(" message: " + message);
        if (xcoord != null && ycoord != null) {
            builder.append(", xCoord: "+xcoord);
            builder.append(", yCoord: "+ycoord);
        }
        if(toUserId!=null) {
            builder.append(", toUserId: "+toUserId);
        }
        if(chatGroupId !=null) {
            builder.append(", chatGroupId: "+ chatGroupId);
        }
        builder.append(", userId: "+ userId);
        builder.append(", userName: "+ userName+"}");
        return builder.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Message(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(rowId);
        dest.writeString(message);
        dest.writeDouble(xcoord);
        dest.writeDouble(ycoord);
        dest.writeString(toUserId);
        dest.writeString(chatGroupId);
        dest.writeString(userId);
        dest.writeString(userName);
        dest.writeString(time);
        dest.writeString(serverId);
    }

    public static final Creator CREATOR = new Creator() {
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public String getMessage() {
        return message;
    }

    public Double getXcoord() {
        return xcoord;
    }

    public Double getYcoord() {
        return ycoord;
    }

    public String getToUserId() {
        return toUserId;
    }

    public String getChatGroupId() {
        return chatGroupId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getServerId() {
        return serverId;
    }

    public String getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Message)) {
            return false;
        }
        Message o1 = (Message) o;
        if (serverId != null && o1.getServerId() != null && !serverId.equals(o1.getServerId())) {
            return false;
        }
        if (!o1.getMessage().equalsIgnoreCase(message)) {
            return false;
        }
        if (o1.getXcoord() != xcoord) {
            return false;
        }
        if (o1.getYcoord() != ycoord) {
            return false;
        }
        if (!o1.getToUserId().equalsIgnoreCase(toUserId)) {
            return false;
        }
        if (!o1.getChatGroupId().equalsIgnoreCase(chatGroupId)) {
            return false;
        }
        if (!o1.getUserId().equalsIgnoreCase(userId)) {
            return false;
        }
        if (!o1.getUserName().equalsIgnoreCase(userName)) {
            return false;
        }
        return o1.getTime().equalsIgnoreCase(time);
    }

    private static Double stringToDouble(String coord) {
        Double res = null;
        if (!TextUtils.isEmpty(coord)) {
            try {
                res = Double.parseDouble(coord);
            } catch (NumberFormatException e) {
                AppLog.E(TAG, e);
            }

        }
        return res;
    }
}
