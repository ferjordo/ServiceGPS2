package com.example.estudiante.gps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by nathali on 18/08/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private String TAG;

    private static final int DATABASE_VERSION = 2;
    private String POINT_TABLE_NAME;

    DatabaseHelper(Context context, String name, String tag,String point) {
        super(context,name, null, DATABASE_VERSION);
        TAG = tag;
        POINT_TABLE_NAME = point;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.i(TAG, "Creating table " + POINT_TABLE_NAME);
            db.execSQL("CREATE TABLE " + POINT_TABLE_NAME + " ("
                    + GPSData.GPSPoint._ID + " INTEGER PRIMARY KEY,"
                    + GPSData.GPSPoint.LATITUDE + " VARCHAR,"
                    + GPSData.GPSPoint.LONGITUDE + " VARCHAR,"
                    + GPSData.GPSPoint.TIME + " VARCHAR"
                    + ");");
        } catch (SQLiteException e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + POINT_TABLE_NAME);
        onCreate(db);
    }
}

