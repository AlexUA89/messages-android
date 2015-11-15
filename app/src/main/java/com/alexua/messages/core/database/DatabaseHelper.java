package com.alexua.messages.core.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alexua.messages.core.database.constants.CreateDatabase;

/**
 * Created with IntelliJ IDEA.
 * User: Oleksii Khom
 * Date: 29.08.13
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */
class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CreateDatabase.createDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
