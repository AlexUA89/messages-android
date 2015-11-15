package com.alexua.messages.core.database.listener;

import com.alexua.messages.core.database.datas.DBStorable;

/**
 * Created with IntelliJ IDEA.
 * User: Oleksii Khom
 * Date: 01.09.13
 * Time: 20:53
 * To change this template use File | Settings | File Templates.
 */
public abstract class DBDeleteListener {

    public void deleted(int typeID, long getRowID) {
    }

    public void deleted(DBStorable data) {
    }

    public void deleted() {
    }

}
