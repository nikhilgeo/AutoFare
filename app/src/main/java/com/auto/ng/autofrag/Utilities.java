package com.auto.ng.autofrag;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by nikhilgeorge on 29-Dec-15.
 */
public class Utilities {

    AutofareDBHelper dbHelper = null;

    public Utilities(Context applicationContext) {
        dbHelper = new AutofareDBHelper(applicationContext);
    }

    public Cursor readDB(String TableName, String[] projection, String whereClause) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TableName, // The table to query
                    projection, // The columns to return
                    null, // The columns for the WHERE clause
                    null,// The sort order e.g: new String[] { wifiSSID }
                    null, null, null);
            return cursor;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e("AutoFare", ex.toString());
            db.close();
            return null;
        }

    }
}
