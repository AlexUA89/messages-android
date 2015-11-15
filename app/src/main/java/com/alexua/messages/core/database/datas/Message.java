package com.alexua.messages.core.database.datas;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.text.TextUtils;

import com.alexua.messages.core.database.constants.DBConstants;
import com.alexua.messages.core.database.constants.Tables;

/**
 * Created by AlexUA on 11/15/2015.
 */
public class Message extends DBStorable {

    public static final int TypeID = 1;
    private static final String TAG = Message.class.getCanonicalName();
    private long rowId = DBConstants.DEFAULT_ROW_ID;
    private String message = "";
    private Double xcoord = Double.NaN;
    private Double ycoord = Double.NaN;
    private String toUserId = "";
    private String chantGroupId = "";
    private String userId = "";
    private String userName = "";
    private String time = "";


    public Message(long rowId, String message, Double xcoord, Double ycoord, String toUserId, String chantGroupId, String userId, String user_name, String time) {
        if (rowId < DBConstants.DEFAULT_ROW_ID) {
            throw new IllegalArgumentException("rowId can not be " + rowId);
        }
        this.rowId = rowId;
        if (TextUtils.isEmpty(message)) {
            throw new IllegalArgumentException("message can not be: " + message + ";");
        }
        this.message = message;

        if (xcoord == null) {
            throw new IllegalArgumentException("xcoord can not be null");
        }
        this.xcoord = xcoord;
        if (ycoord == null) {
            throw new IllegalArgumentException("ycoord can not be null");
        }
        this.ycoord = ycoord;
        if (TextUtils.isEmpty(toUserId) && TextUtils.isEmpty(chantGroupId)) {
            throw new IllegalArgumentException("chat group or toUserId should be defined");
        }
        this.toUserId = toUserId;
        this.chantGroupId = chantGroupId;

        if (toUserId == null || toUserId.isEmpty()) {
            throw new IllegalArgumentException("toUserId can not be: " + toUserId + ";");
        }
        this.toUserId = toUserId;
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
    }

    public Message(String message, Double xcoord, Double ycoord, String toUserId, String chantGroupId, String userId, String user_name, String time) {
        this(DBConstants.DEFAULT_ROW_ID, message, xcoord, ycoord, toUserId, chantGroupId, userId, user_name, time);
    }

    public Message(Message message) {
        this(message.getRowID(), message.getMessage(), message.getXcoord(), message.getYcoord(), message.getToUserId(), message.getChantGroupId(), message.getUserId(), message.getUserName(), message.getTime());
    }

    public Message(Parcel in) {
        this(in.readLong(), in.readString(), in.readDouble(), in.readDouble(), in.readString(), in.readString(), in.readString(), in.readString(), in.readString());
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
                c.getString(c.getColumnIndex(Tables.MESSAGES.TIME)));
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
        values.put(Tables.MESSAGES.ID, rowId);
        values.put(Tables.MESSAGES.MESSAGE, message);
        values.put(Tables.MESSAGES.XCOORD, xcoord);
        values.put(Tables.MESSAGES.YCOORD, ycoord);
        values.put(Tables.MESSAGES.TO_USER_ID, toUserId);
        values.put(Tables.MESSAGES.CHAT_GROUP_ID, chantGroupId);
        values.put(Tables.MESSAGES.USER_ID, userId);
        values.put(Tables.MESSAGES.USER_NAME, userName);
        values.put(Tables.MESSAGES.TIME, time);
        return values;
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
        dest.writeString(chantGroupId);
        dest.writeString(userId);
        dest.writeString(userName);
        dest.writeString(time);
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

    public String getChantGroupId() {
        return chantGroupId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getTime() {
        return time;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Message)) {
            return false;
        }
        Message o1 = (Message) o;
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
        if (!o1.getChantGroupId().equalsIgnoreCase(chantGroupId)) {
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
}
