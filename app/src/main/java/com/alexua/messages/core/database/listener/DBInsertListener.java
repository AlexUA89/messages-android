package com.alexua.messages.core.database.listener;


import com.alexua.messages.core.database.datas.DBStorable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Oleksii Khom
 * Date: 01.09.13
 * Time: 20:37
 * To change this template use File | Settings | File Templates.
 */
public abstract class DBInsertListener {

    public long inserting(DBStorable dbStorable) {
        return 0;
    }

    public List<Long> insertingList(List<DBStorable> list) {
        return null;
    }

}
