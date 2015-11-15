package com.alexua.messages.core.database;


import android.database.sqlite.SQLiteDatabase;

import com.alexua.messages.core.ContextProvider;

/**
 * Created with IntelliJ IDEA.
 * User: Oleksii Khom
 * Date: 30.08.13
 * Time: 13:00
 * To change this template use File | Settings | File Templates.
 */
public class DBManager {

    private static DBManager inctance;
    private static Database db;

    private DBManager() {
    }


    synchronized public static DBManager getInctance() {
        if (inctance == null) {
            inctance = new DBManager();
        }
        return inctance;
    }

    synchronized public static Database getDatabase() {
        if (db == null) {
            db = new DBLAdapter(new DBAdapter(new DatabaseHelper(ContextProvider.getAppContext())));
        }
        return db;
    }


}
