package com.auto.ng.autofrag;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
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
        GridLayout grd_ratesumry = (GridLayout) rootView.findViewById(R.id.grd_ratesumry);
        grd_ratesumry.setVisibility(GridLayout.GONE);

        Button button = (Button) rootView.findViewById(R.id.btn_calfare);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcFare();
            }
        });
        getFare();
        return rootView;
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

            }
        } catch (Exception ex) {
            Log.e("AutoFare", ex.toString());
        }

    }

    public void calcFare() {
        try {


            GridLayout grd_ratesumry = (GridLayout) rootView.findViewById(R.id.grd_ratesumry);
            grd_ratesumry.setVisibility(GridLayout.VISIBLE);
            EditText etxt_travel_dist = (EditText) rootView.findViewById(R.id.travel_distance);
            EditText etxt_waiting_time = (EditText) rootView.findViewById(R.id.waiting_time);
            CheckBox chk_apply_night_charge = (CheckBox) rootView.findViewById(R.id.apply_night_charge);
            Boolean apply_night_charge = chk_apply_night_charge.isChecked();
            TextView calc_travel_distance = (TextView) rootView.findViewById(R.id.calc_travel_distance);
            TextView calc_waiting_time = (TextView) rootView.findViewById(R.id.calc_waiting_time);
            TextView calc_apply_night_charge = (TextView) rootView.findViewById(R.id.calc_apply_night_charge);
            TextView calc_txt_totchrg = (TextView) rootView.findViewById(R.id.calc_txt_totchrg);
            calc_travel_distance.setText(getResources().getString(R.string.Rs) + "0");
            calc_waiting_time.setText(getResources().getString(R.string.Rs) + "0");
            calc_apply_night_charge.setText(getResources().getString(R.string.Rs) + "0");
            calc_txt_totchrg.setText(getResources().getString(R.string.Rs) + "0");

            float fare = minCharge;


            if (etxt_travel_dist.getText().toString().trim().length() != 0) {
                float travel_dist = Float.parseFloat(etxt_travel_dist.getText().toString());
                if (travel_dist > minCharge_KM) {
                    float addn_dist = travel_dist - minCharge_KM;
                    fare = fare + (addn_dist * additionalFare);
                }
                calc_travel_distance.setText(getResources().getString(R.string.Rs) + Float.toString(fare));
            }
            if (etxt_waiting_time.getText().toString().trim().length() != 0) {
                float waiting_time = Float.parseFloat(etxt_waiting_time.getText().toString());
                float waiting_charge = waiting_time * (waitingCharge / waitingCharge_Min);
                fare = fare + waiting_charge;
                calc_waiting_time.setText(getResources().getString(R.string.Rs) + Math.round(waiting_charge));
            }
            if (apply_night_charge) {
                float night_charge = (nightCharge / 100) * fare;
                fare = fare + night_charge;
                calc_apply_night_charge.setText(getResources().getString(R.string.Rs) + Math.round(night_charge));
            }

            calc_txt_totchrg.setText("Total charge:" + getResources().getString(R.string.Rs) + String.valueOf(Math.round(fare)));

        } catch (Exception ex) {
            Log.e("AutoFare", ex.toString());
        }
    }
}
