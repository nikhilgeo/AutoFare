package com.auto.ng.autofrag;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.auto.ng.autofrag.AutoFareDBContract.BaseFareTemplate;

public class MainActivity extends AppCompatActivity {

    String minCharge, minCharge_KM, additionalFare, additionalFare_KM,
            nightCharge, waitingCharge, waitingCharge_Min;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFare();
    }


    private void getFare() {
        try {
            Log.w("AutoFare", "In getFare");
            Utilities utilities = new Utilities(getApplicationContext());
            String[] projection = {BaseFareTemplate.COLUMN_NAME_minCharge,
                    BaseFareTemplate.COLUMN_NAME_minCharge_KM,
                    BaseFareTemplate.COLUMN_NAME_additionalFare,
                    BaseFareTemplate.COLUMN_NAME_additionalFare_KM,
                    BaseFareTemplate.COLUMN_NAME_waitingCharge,
                    BaseFareTemplate.COLUMN_NAME_waitingCharge_Min,
                    BaseFareTemplate.COLUMN_NAME_nightCharge,
                    BaseFareTemplate.COLUMN_NAME_stateName};
            String whereCondtion = null;


            Cursor cursor = utilities.readDB(BaseFareTemplate.TABLE_NAME, projection, whereCondtion);

            if (cursor.moveToFirst()) {
                minCharge = cursor.getString(cursor.getColumnIndexOrThrow(BaseFareTemplate.COLUMN_NAME_minCharge));
                minCharge_KM = cursor.getString(cursor.getColumnIndexOrThrow(BaseFareTemplate.COLUMN_NAME_minCharge_KM));
                additionalFare = cursor.getString(cursor.getColumnIndexOrThrow(BaseFareTemplate.COLUMN_NAME_additionalFare));
                additionalFare_KM = cursor.getString(cursor.getColumnIndexOrThrow(BaseFareTemplate.COLUMN_NAME_additionalFare_KM));
                nightCharge = cursor.getString(cursor.getColumnIndexOrThrow(BaseFareTemplate.COLUMN_NAME_nightCharge));
                waitingCharge = cursor.getString(cursor.getColumnIndexOrThrow(BaseFareTemplate.COLUMN_NAME_waitingCharge));
                waitingCharge_Min = cursor.getString(cursor.getColumnIndexOrThrow(BaseFareTemplate.COLUMN_NAME_waitingCharge_Min));

                TextView txtviewMinCharge = (TextView) findViewById(R.id.txtviewMinCharge);
                txtviewMinCharge.setText(getResources().getString(R.string.Rs) + minCharge + "/" + minCharge_KM + "KM");
                TextView txtviewAddnCharge = (TextView) findViewById(R.id.txtviewAddnCharge);
                txtviewAddnCharge.setText(getResources().getString(R.string.Rs) + additionalFare + "/" + additionalFare_KM + "KM");
                TextView txtviewNightCharge = (TextView) findViewById(R.id.txtviewNightCharge);
                txtviewNightCharge.setText(nightCharge + "% Extra");
                TextView txtviewWatingCharge = (TextView) findViewById(R.id.txtviewWatingCharge);
                txtviewWatingCharge.setText(getResources().getString(R.string.Rs) + waitingCharge + "/" + waitingCharge_Min + "Mins");
            }
        } catch (Exception ex) {
            Log.e("AutoFare", ex.toString());
        }

    }
}


