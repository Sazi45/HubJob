// DBHelper.java
package com.example.hubjob;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "CVStorage.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "UserCVs";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_CV = "cv_blob";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_EMAIL + " TEXT PRIMARY KEY, " +
                COLUMN_CV + " BLOB)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean saveCV(String email, byte[] cvBytes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_CV, cvBytes);

        long result = db.replace(TABLE_NAME, null, values); // replace if exists
        return result != -1;
    }
}
