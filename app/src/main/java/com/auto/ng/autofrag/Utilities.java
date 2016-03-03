package com.auto.ng.autofrag;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

import static java.util.Arrays.asList;


/**
 * Created by nikhilgeorge on 29-Dec-15.
 */
public class Utilities {

    AutofareDBHelper dbHelper = null;
    public static List<String> places = asList("Andaman and Nicobar Islands",
            "Andhra Pradesh",
            "Arunachal Pradesh",
            "Assam",
            "Bihar",
            "Chandigarh",
            "Chhattisgarh",
            "Dadra and Nagar Haveli",
            "Daman and Diu",
            "Goa",
            "Gujarat",
            "Haryana",
            "Himachal Pradesh",
            "Jammu and Kashmir",
            "Jharkhand",
            "Karnataka",
            "Kerala",
            "Lakshadweep ",
            "Madhya Pradesh",
            "Maharashtra",
            "Manipur",
            "Meghalaya",
            "Mizoram",
            "Nagaland",
            "New Delhi",
            "Odisha(Orissa)",
            "Puducherry",
            "Punjab",
            "Rajasthan",
            "Sikkim",
            "Tamil Nadu",
            "Telangana",
            "Tripura",
            "Uttar Pradesh",
            "Uttarakhand",
            "West Bengal",
            "Bangalore",
            "Mumbai",
            "Kolkatta",
            "Chennai"
    );


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
