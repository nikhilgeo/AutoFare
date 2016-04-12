package com.auto.ng.autofrag;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class RateCard_Frag extends Fragment {

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_rate_card, container, false);
        getFare(rootView);
        gen_rate_card(rootView);
        return rootView;
    }

    private void getFare(View rootView) {
        float minCharge, minCharge_KM, additionalFare, additionalFare_KM,
                nightCharge, waitingCharge, waitingCharge_Min;
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


                TextView txtviewMinCharge = (TextView) this.rootView.findViewById(R.id.txtviewMinCharge);
                txtviewMinCharge.setText(getResources().getString(R.string.Rs) + minCharge + "/" + minCharge_KM + "KM");
                TextView txtviewAddnCharge = (TextView) this.rootView.findViewById(R.id.txtviewAddnCharge);
                txtviewAddnCharge.setText(getResources().getString(R.string.Rs) + additionalFare + "/" + additionalFare_KM + "KM");
                TextView txtviewNightCharge = (TextView) this.rootView.findViewById(R.id.txtviewNightCharge);
                txtviewNightCharge.setText(nightCharge + "% Extra");
                TextView txtviewWatingCharge = (TextView) this.rootView.findViewById(R.id.txtviewWatingCharge);
                txtviewWatingCharge.setText(getResources().getString(R.string.Rs) + waitingCharge + "/" + waitingCharge_Min + "Mins");
            }
        } catch (Exception ex) {
            Log.e("AutoFare", ex.toString());
        }

    }


    private void gen_rate_card(View rootView) {
        try {

            double minCharge = 0, minCharge_KM = 0, additionalFare = 0, nightCharge = 0;
            TableLayout tbl = (TableLayout) rootView.findViewById(R.id.tbl_main);

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
                nightCharge = Float.parseFloat(cursor.getString(cursor.getColumnIndexOrThrow(AutoFareDBContract.BaseFareTemplate.COLUMN_NAME_nightCharge)));

            }
            cursor.close();

            for (int i = 3; i < 51; i++) {
                double km = i * 0.5;
                double fare = minCharge;
                if (km > minCharge_KM) {
                    double addn_dist = km - minCharge_KM;
                    fare = fare + (addn_dist * additionalFare);
                }

                String color = "#E8EAF6";
                if (i % 2 == 0) {
                    color = "#C5CAE9";
                }

                TableRow row = new TableRow(getActivity());
                TableRow.LayoutParams lp = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);

                TextView txtKM = new TextView(getActivity());
                txtKM.setText(Double.toString(km) + " KM");
                txtKM.setTextColor(Color.BLACK);
                txtKM.setGravity(Gravity.CENTER_HORIZONTAL);
                txtKM.setBackgroundColor(Color.parseColor(color));


                TextView txtStdFare = new TextView(getActivity());
                txtStdFare.setText(getResources().getString(R.string.Rs) + Long.toString(Math.round(fare)));
                txtStdFare.setTextColor(Color.BLACK);
                txtStdFare.setGravity(Gravity.CENTER_HORIZONTAL);
                txtStdFare.setBackgroundColor(Color.parseColor(color));

                double night_charge = (nightCharge / 100) * fare;
                fare = fare + night_charge;

                TextView txtNightCharge = new TextView(getActivity());
                txtNightCharge.setText(getResources().getString(R.string.Rs) + Long.toString(Math.round(fare)));
                txtNightCharge.setTextColor(Color.BLACK);
                txtNightCharge.setGravity(Gravity.CENTER_HORIZONTAL);
                txtNightCharge.setBackgroundColor(Color.parseColor(color));

                row.addView(txtKM);
                row.addView(txtStdFare);
                row.addView(txtNightCharge);
                tbl.addView(row);
            }


        } catch (Exception ex) {
            //Log.e("AutoFare", ex.toString());
        }
    }
}
