package com.auto.ng.autofrag;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.auto.ng.autofrag.AutoFareDBContract.BaseFareTemplate;
import com.auto.ng.autofrag.AutoFareDBContract.StateCityTemplate;


/**
 * Created by nikhilgeorge on 28-Dec-15.
 */
public class AutofareDBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database
    // version.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "autoFare.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES_BaseFare = "CREATE TABLE "
            + BaseFareTemplate.TABLE_NAME + " (" + BaseFareTemplate._ID
            + "AUTOINCREMENT INTEGER PRIMARY KEY" + COMMA_SEP +
            BaseFareTemplate.COLUMN_NAME_minCharge + TEXT_TYPE + COMMA_SEP +
            BaseFareTemplate.COLUMN_NAME_minCharge_KM + TEXT_TYPE + COMMA_SEP +
            BaseFareTemplate.COLUMN_NAME_additionalFare + TEXT_TYPE + COMMA_SEP +
            BaseFareTemplate.COLUMN_NAME_additionalFare_KM + TEXT_TYPE + COMMA_SEP +
            BaseFareTemplate.COLUMN_NAME_nightCharge + TEXT_TYPE + COMMA_SEP +
            BaseFareTemplate.COLUMN_NAME_waitingCharge + TEXT_TYPE + COMMA_SEP +
            BaseFareTemplate.COLUMN_NAME_waitingCharge_Min + TEXT_TYPE + COMMA_SEP +
            BaseFareTemplate.COLUMN_NAME_stateName + TEXT_TYPE +
            " )";

    private static final String SQL_CREATE_ENTRIES_StateCity = "CREATE TABLE "
            + StateCityTemplate.TABLE_NAME + " (" + StateCityTemplate._ID
            + "AUTOINCREMENT INTEGER PRIMARY KEY" + COMMA_SEP +
            StateCityTemplate.COLUMN_NAME_Place + TEXT_TYPE +
            " )";

    private static final String SQL_INSERT_ENTRIES_BaseFare_Kerala = "INSERT INTO "
            + BaseFareTemplate.TABLE_NAME + "("
            + BaseFareTemplate.COLUMN_NAME_minCharge + COMMA_SEP
            + BaseFareTemplate.COLUMN_NAME_minCharge_KM + COMMA_SEP
            + BaseFareTemplate.COLUMN_NAME_additionalFare + COMMA_SEP
            + BaseFareTemplate.COLUMN_NAME_additionalFare_KM + COMMA_SEP
            + BaseFareTemplate.COLUMN_NAME_nightCharge + COMMA_SEP
            + BaseFareTemplate.COLUMN_NAME_waitingCharge + COMMA_SEP
            + BaseFareTemplate.COLUMN_NAME_waitingCharge_Min + COMMA_SEP
            + BaseFareTemplate.COLUMN_NAME_stateName + ")"
            + " VALUES ('20','1.5','10','1','50','10','15','Kerala')";

    private static final String SQL_INSERT_ENTRIES_StateCity = "INSERT INTO "
            + StateCityTemplate.TABLE_NAME + "("
            + StateCityTemplate.COLUMN_NAME_Place + ")"
            + " VALUES ("; //The places will be concated from the Utilities.Places List

    private static final String SQL_DELETE_ENTRIES_BaseFare = "DROP TABLE IF EXISTS "
            + BaseFareTemplate.TABLE_NAME;
    private static final String SQL_DELETE_ENTRIES_StateCity = "DROP TABLE IF EXISTS "
            + StateCityTemplate.TABLE_NAME;

    public AutofareDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.w("AutoFare:", SQL_CREATE_ENTRIES_BaseFare);
            db.execSQL(SQL_CREATE_ENTRIES_BaseFare);
            Log.w("AutoFare:", SQL_CREATE_ENTRIES_StateCity);
            db.execSQL(SQL_CREATE_ENTRIES_StateCity);

            Log.w("AutoFare:", SQL_INSERT_ENTRIES_BaseFare_Kerala);
            db.execSQL(SQL_INSERT_ENTRIES_BaseFare_Kerala);

            for (String place : Utilities.places) {
                Log.w("AutoFare:", SQL_INSERT_ENTRIES_StateCity + "'" + place + "')");
                db.execSQL(SQL_INSERT_ENTRIES_StateCity + "'" + place + "')");
            }


        } catch (Exception ex) {
            Log.e("AutoFare", ex.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy
        // is to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES_BaseFare);
        db.execSQL(SQL_DELETE_ENTRIES_StateCity);
        onCreate(db);
    }


}
