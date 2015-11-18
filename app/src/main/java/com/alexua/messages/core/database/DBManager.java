package com.alexua.messages.core.database;


import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.widget.Toast;

import com.alexua.messages.core.ContextProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

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

    public static void saveDBonSDCard(){
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//"+ContextProvider.getAppContext().getPackageName()+"//databases//"+DatabaseHelper.DATABASE_NAME+"";
                String backupDBPath = "backupname.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    Toast.makeText(ContextProvider.getAppContext(), backupDB.toString(), Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {

        }
    }


}
