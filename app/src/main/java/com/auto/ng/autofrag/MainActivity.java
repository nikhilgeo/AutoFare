package com.auto.ng.autofrag;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.auto.ng.autofrag.AutoFareDBContract.BaseFareTemplate;


public class MainActivity extends AppCompatActivity {

    float minCharge, minCharge_KM, additionalFare, additionalFare_KM,
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
                minCharge = Float.parseFloat(cursor.getString(cursor.getColumnIndexOrThrow(BaseFareTemplate.COLUMN_NAME_minCharge)));
                minCharge_KM = Float.parseFloat(cursor.getString(cursor.getColumnIndexOrThrow(BaseFareTemplate.COLUMN_NAME_minCharge_KM)));
                additionalFare = Float.parseFloat(cursor.getString(cursor.getColumnIndexOrThrow(BaseFareTemplate.COLUMN_NAME_additionalFare)));
                additionalFare_KM = Float.parseFloat(cursor.getString(cursor.getColumnIndexOrThrow(BaseFareTemplate.COLUMN_NAME_additionalFare_KM)));
                nightCharge = Float.parseFloat(cursor.getString(cursor.getColumnIndexOrThrow(BaseFareTemplate.COLUMN_NAME_nightCharge)));
                waitingCharge = Float.parseFloat(cursor.getString(cursor.getColumnIndexOrThrow(BaseFareTemplate.COLUMN_NAME_waitingCharge)));
                waitingCharge_Min = Float.parseFloat(cursor.getString(cursor.getColumnIndexOrThrow(BaseFareTemplate.COLUMN_NAME_waitingCharge_Min)));

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

    public void calcFare(View view) {
        try {
            EditText etxt_travel_dist = (EditText) findViewById(R.id.travel_distance);
            EditText etxt_waiting_time = (EditText) findViewById(R.id.waiting_time);
            CheckBox chk_apply_night_charge = (CheckBox) findViewById(R.id.apply_night_charge);

            TextView txtview_running_charge = (TextView) findViewById(R.id.running_charge);
            TextView txtviewe_waiting__charge = (TextView) findViewById(R.id.waiting__charge);
            TextView txtview_night_charge = (TextView) findViewById(R.id.night_charge);
            TextView txtview_total_charge = (TextView) findViewById(R.id.total_charge);


            Boolean apply_night_charge = chk_apply_night_charge.isChecked();

            float fare = minCharge;


            if (etxt_travel_dist.getText().toString().trim().length() != 0) {
                float travel_dist = Float.parseFloat(etxt_travel_dist.getText().toString());
                if (travel_dist > minCharge_KM) {
                    float addn_dist = travel_dist - minCharge_KM;
                    fare = fare + (addn_dist * additionalFare);
                }
                txtview_running_charge.setText(getResources().getString(R.string.Rs) + Float.toString(fare));
            }
            if (etxt_waiting_time.getText().toString().trim().length() != 0) {
                float waiting_time = Float.parseFloat(etxt_waiting_time.getText().toString());
                float waiting_charge = waiting_time * (waitingCharge / waitingCharge_Min);
                fare = fare + waiting_charge;
                txtviewe_waiting__charge.setText(getResources().getString(R.string.Rs) + Math.round(waiting_charge));
            }
            if (apply_night_charge) {
                float night_charge = (nightCharge / 100) * fare;
                fare = fare + night_charge;
                txtview_night_charge.setText(getResources().getString(R.string.Rs) + Math.round(night_charge));
            }

            txtview_total_charge.setText(getResources().getString(R.string.Rs) + String.valueOf(Math.round(fare)));

        } catch (Exception ex) {
            Log.e("AutoFare", ex.toString());
        }
    }
}


