package com.alexua.messages.core.database.constants;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created with IntelliJ IDEA.
 * User: Oleksii Khom
 * Date: 29.08.13
 * Time: 16:58
 * To change this template use File | Settings | File Templates.
 */
public final class CreateDatabase {

    public static void createDatabase(SQLiteDatabase db) {
        db.execSQL(CreateSQLScriptMessageProviders);
    }


    public static final String CreateSQLScriptMessageProviders = "CREATE TABLE IF NOT EXISTS " + Tables.MESSAGES.TABLE_NAME + " ( "
            + Tables.MESSAGES.ID + " INTEGER PRIMARY KEY NOT NULL, "
            + Tables.MESSAGES.MESSAGE + " TEXT NOT NULL, "
            + Tables.MESSAGES.XCOORD + " REAL, "
            + Tables.MESSAGES.YCOORD + " REAL, "
            + Tables.MESSAGES.TO_USER_ID + " TEXT, "
            + Tables.MESSAGES.CHAT_GROUP_ID + " TEXT, "
            + Tables.MESSAGES.USER_ID + " TEXT NOT NULL, "
            + Tables.MESSAGES.USER_NAME + " TEXT NOT NULL, "
            + Tables.MESSAGES.SERVER_ID + " TEXT NOT NULL, "
            + Tables.MESSAGES.TIME + " TEXT NOT NULL);";

}
