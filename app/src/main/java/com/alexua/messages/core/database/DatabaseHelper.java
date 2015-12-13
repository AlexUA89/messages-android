package com.alexua.messages.core.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alexua.messages.ui.base.AlexApplication;
import com.alexua.messages.core.database.constants.CreateDatabase;

/**
 * Created with IntelliJ IDEA.
 * User: Oleksii Khom
 * Date: 29.08.13
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */
class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "database.db";
    public static final int DATABASE_VERSION = 1;
    public static String getDBPath() {
        if(android.os.Build.VERSION.SDK_INT >= 17) {
            return  AlexApplication.getAppContext().getApplicationInfo().dataDir + "/databases/"+DATABASE_NAME;
        } else {
            return "/data/data/" + AlexApplication.getAppContext().getPackageName() + "/databases/"+DATABASE_NAME;
        }
    }

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
