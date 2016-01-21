package com.auto.ng.autofrag;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


public class CalculateFare_Frag extends Fragment {
    float minCharge, minCharge_KM, additionalFare, additionalFare_KM,
            nightCharge, waitingCharge, waitingCharge_Min;
    private EditText etxttravel_distance, etxtwaiting_time;
    private TextView txtrunning_charge, txtwaiting__charge;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_calculate_fare, container, false);

        etxttravel_distance = (EditText) rootView.findViewById(R.id.travel_distance);
        etxtwaiting_time = (EditText) rootView.findViewById(R.id.waiting_time);
        txtrunning_charge = (TextView) rootView.findViewById(R.id.running_charge);
        txtwaiting__charge = (TextView) rootView.findViewById(R.id.waiting__charge);

        etxttravel_distance.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtrunning_charge.setText("0");
            }
        });

        etxtwaiting_time.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtwaiting__charge.setText("0");
            }
        });


        getFare();
        return inflater.inflate(R.layout.fragment_calculate_fare, container, false);
    }


    private void getFare() {
        try {
            Log.w("AutoFare", "In getFare");
            Utilities utilities = new Utilities(getActivity());
            String[] projection = {AutoFareDBContract.BaseFareTemplate.COLUMN_NAME_minCharge,
                    AutoFareDBContract.BaseFareTemplate.COLUMN_NAME_minCharge_KM,
                    AutoFareDBContract.BaseFareTemplate.COLUMN_NAME_additionalFare,
                    AutoFareDBContract.BaseFareTemplate.COLUMN_NAME_additionalFare_KM,
                    AutoFareDBContract.BaseFareTemplate.COLUMN_NAME_waitingCharge,
                    AutoFareDBContract.BaseFareTemplate.COLUMN_NAME_waitingCharge_Min,
                    AutoFareDBContract.BaseFareTemplate.COLUMN_NAME_nightCharge,
                    AutoFareDBContract.BaseFareTemplate.COLUMN_NAME_stateName};
            String whereCondtion = null;


            Cursor cursor = utilities.readDB(AutoFareDBContract.BaseFareTemplate.TABLE_NAME, projection, whereCondtion);

            if (cursor.moveToFirst()) {
                minCharge = Float.parseFloat(cursor.getString(cursor.getColumnIndexOrThrow(AutoFareDBContract.BaseFareTemplate.COLUMN_NAME_minCharge)));
                minCharge_KM = Float.parseFloat(cursor.getString(cursor.getColumnIndexOrThrow(AutoFareDBContract.BaseFareTemplate.COLUMN_NAME_minCharge_KM)));
                additionalFare = Float.parseFloat(cursor.getString(cursor.getColumnIndexOrThrow(AutoFareDBContract.BaseFareTemplate.COLUMN_NAME_additionalFare)));
                additionalFare_KM = Float.parseFloat(cursor.getString(cursor.getColumnIndexOrThrow(AutoFareDBContract.BaseFareTemplate.COLUMN_NAME_additionalFare_KM)));
                nightCharge = Float.parseFloat(cursor.getString(cursor.getColumnIndexOrThrow(AutoFareDBContract.BaseFareTemplate.COLUMN_NAME_nightCharge)));
                waitingCharge = Float.parseFloat(cursor.getString(cursor.getColumnIndexOrThrow(AutoFareDBContract.BaseFareTemplate.COLUMN_NAME_waitingCharge)));
                waitingCharge_Min = Float.parseFloat(cursor.getString(cursor.getColumnIndexOrThrow(AutoFareDBContract.BaseFareTemplate.COLUMN_NAME_waitingCharge_Min)));

                TextView txtviewMinCharge = (TextView) rootView.findViewById(R.id.txtviewMinCharge);
                txtviewMinCharge.setText(getResources().getString(R.string.Rs) + minCharge + "/" + minCharge_KM + "KM");
                TextView txtviewAddnCharge = (TextView) rootView.findViewById(R.id.txtviewAddnCharge);
                txtviewAddnCharge.setText(getResources().getString(R.string.Rs) + additionalFare + "/" + additionalFare_KM + "KM");
                TextView txtviewNightCharge = (TextView) rootView.findViewById(R.id.txtviewNightCharge);
                txtviewNightCharge.setText(nightCharge + "% Extra");
                TextView txtviewWatingCharge = (TextView) rootView.findViewById(R.id.txtviewWatingCharge);
                txtviewWatingCharge.setText(getResources().getString(R.string.Rs) + waitingCharge + "/" + waitingCharge_Min + "Mins");
            }
        } catch (Exception ex) {
            Log.e("AutoFare", ex.toString());
        }

    }

    public void calcFare(View view) {
        try {
            EditText etxt_travel_dist = (EditText) rootView.findViewById(R.id.travel_distance);
            EditText etxt_waiting_time = (EditText) rootView.findViewById(R.id.waiting_time);
            CheckBox chk_apply_night_charge = (CheckBox) rootView.findViewById(R.id.apply_night_charge);
            TextView txtview_running_charge = (TextView) rootView.findViewById(R.id.running_charge);
            TextView txtviewe_waiting__charge = (TextView) rootView.findViewById(R.id.waiting__charge);
            TextView txtview_night_charge = (TextView) rootView.findViewById(R.id.night_charge);
            TextView txtview_total_charge = (TextView) rootView.findViewById(R.id.total_charge);
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
