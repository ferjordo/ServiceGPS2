package com.example.estudiante.gps;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
/**
 * Created by nathali on 18/08/16.
 */
public class GPSDataContentProvider extends ContentProvider {

    private static final String TAG = "GPSDataContentProvider";

    private static final String DATABASE_NAME = "gpsdata.db";
    private static final String POINT_TABLE_NAME = "gpspoints";


    public static final String AUTHORITY = "com.example.GPSDataContentProvider";//Aquí

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/gpspoint");

    private DatabaseHelper mOpenHelper;

    public DatabaseHelper getmOpenHelper() {
        return mOpenHelper;
    }

    public void setmOpenHelper(DatabaseHelper mOpenHelper) {
        this.mOpenHelper = mOpenHelper;
    }

    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper(getContext(),DATABASE_NAME,TAG,POINT_TABLE_NAME);
        return true;
    }

    @Override
    public int delete(Uri arg0, String arg1, String[] arg2) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        Log.i(TAG, "getting type for " + uri.toString());
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.e(TAG, "inserting value " + values.toString());

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long rowId = db.insert(POINT_TABLE_NAME, "", values);
        if (rowId > 0) {
            Uri noteUri = ContentUris.withAppendedId(GPSDataContentProvider.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

    public Cursor consultar(){
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor res =  db.rawQuery( "Select LATITUDE,LONGITUDE,TIME from gpspoints", null );
        return res;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO Auto-generated method stub
        return 0;
    }
}

