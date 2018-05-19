package com.phyte.sanraphindustries.viso.car.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.phyte.sanraphindustries.viso.car.db.table.ConsumerTable;

/**
 *Application Name: CarAssistant
 * Package name: com.classic.car.app
 *
 * File Description: Car Assistant
 * Creator: Continue to write classics
 * Creation time：16/5/29, afternoon 02:07
 */
public class DbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME    = "CarAssistant.db";
    private static final int    DB_VERSION = 2;

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL(ConsumerTable.create());
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.beginTransaction();
            switch (newVersion) {
                case 2:
                    update2(db);
                    break;
                default:
                    break;
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        db.setVersion(newVersion);
    }

    private void update2(SQLiteDatabase db) {
        db.execSQL(ConsumerTable.updateToVersion2());
    }
}
