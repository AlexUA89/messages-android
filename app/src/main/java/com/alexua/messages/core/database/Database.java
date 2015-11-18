package com.alexua.messages.core.database;

import android.database.Cursor;


import com.alexua.messages.core.database.datas.DBStorable;
import com.alexua.messages.core.database.listener.DBDeleteListener;
import com.alexua.messages.core.database.listener.DBInsertListener;
import com.alexua.messages.core.database.listener.DBUpdateListener;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Oleksii Khom
 * Date: 29.08.13
 * Time: 17:06
 * To change this template use File | Settings | File Templates.
 */
public abstract class Database {

    public abstract boolean open();

    public abstract void close();

    public abstract long insertNewDBStorable(DBStorable newDBStorable);

    public abstract List<Long> insertNewDBStorables(List<DBStorable> listDBStorable);

    public abstract int deleteDBStorableByType(int typeID, long rowID);

    public abstract int deleteDBStorable(DBStorable data);

    public abstract boolean updateDBStorable(DBStorable data);

    public abstract Cursor selectAllDBStorablesByType(int typeId);

    public abstract DBStorable selectDBStorableByTypeAndId(int typeId, long dbstorableId);

    public abstract DBStorable parseFromCursor(Cursor c, int typeId);

    public void addInsertListener(int typeID, DBInsertListener listener) {
    }

    public void deleteInsertListener(DBInsertListener listener) {
    }

    public void addDeleteListener(int typeID, DBDeleteListener listener) {
    }

    public void deleteDeleteListner(DBDeleteListener listener) {
    }

    public void addUpdateListener(int typeID, DBUpdateListener listener) {
    }

    public void deleteUpdateListner(DBUpdateListener listener) {
    }

}
