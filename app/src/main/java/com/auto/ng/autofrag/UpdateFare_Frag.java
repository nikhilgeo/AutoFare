package com.auto.ng.autofrag;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class UpdateFare_Frag extends Fragment {

    View rootView;
    Button button = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_update_fare, container, false);
        /*** Auto Complete population code---removed for Boli release
         *
         * Layout code for AutoCompleteTextView
         *<AutoCompleteTextView
         *android:id="@+id/multi_auto_complete_textview"
         *android:layout_width="match_parent"
         *android:layout_height="wrap_content"
         *android:layout_gravity="center"
         *android:hint="Select City or State "/>
         *
         * Java code
         AutoCompleteTextView multiAutoCompleteTextView = (AutoCompleteTextView) rootView.findViewById(R.id.multi_auto_complete_textview);
         ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.select_dialog_item, Utilities.places.toArray(new String[0]));
         multiAutoCompleteTextView.setAdapter(adapter);
         multiAutoCompleteTextView.setThreshold(1);
         */
        getFare();
        button = (Button) rootView.findViewById(R.id.btn_calfare);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateFare();
            }
        });


        return rootView;
    }

    private void updateFare() {


        try {
            ContentValues cv = new ContentValues();
            Utilities utilities = new Utilities(getActivity());
            int updateStatus = -1;
            EditText edt_minChrgRate = (EditText) rootView.findViewById(R.id.edt_minChrgRate);
            EditText edt_minChrgDist = (EditText) rootView.findViewById(R.id.edt_minChrgDist);
            EditText edt_afterMinChrgRate = (EditText) rootView.findViewById(R.id.edt_afterMinChrgRate);
            EditText edt_afterMinChrgDist = (EditText) rootView.findViewById(R.id.edt_afterMinChrgDist);
            EditText edt_nightChrgRate = (EditText) rootView.findViewById(R.id.edt_nightChrgRate);
            EditText edt_waitingChrg = (EditText) rootView.findViewById(R.id.edt_waitingChrg);
            EditText edt_waitingChrgMin = (EditText) rootView.findViewById(R.id.edt_waitingChrgMin);
            //These Fields should be your String values of actual column names
            cv.put(AutoFareDBContract.BaseFareTemplate.COLUMN_NAME_minCharge, edt_minChrgRate.getText().toString());
            cv.put(AutoFareDBContract.BaseFareTemplate.COLUMN_NAME_minCharge_KM, edt_minChrgDist.getText().toString());
            cv.put(AutoFareDBContract.BaseFareTemplate.COLUMN_NAME_additionalFare, edt_afterMinChrgRate.getText().toString());
            cv.put(AutoFareDBContract.BaseFareTemplate.COLUMN_NAME_additionalFare_KM, edt_afterMinChrgDist.getText().toString());
            cv.put(AutoFareDBContract.BaseFareTemplate.COLUMN_NAME_waitingCharge, edt_waitingChrg.getText().toString());
            cv.put(AutoFareDBContract.BaseFareTemplate.COLUMN_NAME_waitingCharge_Min, edt_waitingChrgMin.getText().toString());
            cv.put(AutoFareDBContract.BaseFareTemplate.COLUMN_NAME_nightCharge, edt_nightChrgRate.getText().toString());
            updateStatus = utilities.updateDB(AutoFareDBContract.BaseFareTemplate.TABLE_NAME, cv, AutoFareDBContract.BaseFareTemplate.COLUMN_NAME_stateName + "=?", new String[]{"Kerala"});
            if (updateStatus != -1 && updateStatus != 0) {
                Toast toast = Toast.makeText(getActivity(), "Fare update successful", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(getActivity(), "Sorry, could not update fare", Toast.LENGTH_SHORT);
                toast.show();
            }

        } catch (Exception ex) {
            Toast toast = Toast.makeText(getActivity(), "Error updating fare", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    private void getFare() {
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

                EditText edt_minChrgRate = (EditText) rootView.findViewById(R.id.edt_minChrgRate);
                EditText edt_minChrgDist = (EditText) rootView.findViewById(R.id.edt_minChrgDist);
                EditText edt_afterMinChrgRate = (EditText) rootView.findViewById(R.id.edt_afterMinChrgRate);
                EditText edt_afterMinChrgDist = (EditText) rootView.findViewById(R.id.edt_afterMinChrgDist);
                EditText edt_nightChrgRate = (EditText) rootView.findViewById(R.id.edt_nightChrgRate);
                EditText edt_waitingChrg = (EditText) rootView.findViewById(R.id.edt_waitingChrg);
                EditText edt_waitingChrgMin = (EditText) rootView.findViewById(R.id.edt_waitingChrgMin);

                edt_minChrgRate.setText("" + minCharge);
                edt_minChrgDist.setText("" + minCharge_KM);
                edt_afterMinChrgRate.setText("" + additionalFare);
                edt_afterMinChrgDist.setText("" + additionalFare_KM);
                edt_nightChrgRate.setText("" + nightCharge);
                edt_waitingChrg.setText("" + waitingCharge);
                edt_waitingChrgMin.setText("" + waitingCharge_Min);
            }
        } catch (Exception ex) {
            Log.e("AutoFare", ex.toString());
        }
    }
}
