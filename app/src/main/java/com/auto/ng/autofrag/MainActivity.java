package com.auto.ng.autofrag;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import com.auto.ng.autofrag.AutoFareDBContract.BaseFareTemplate;

public class MainActivity extends AppCompatActivity {

    String minCharge, minCharge_KM, additionalFare, additionalFare_KM,
            nightCharge, waitingCharge, waitingCharge_Min;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        getFare();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.auto.ng.autofrag/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.auto.ng.autofrag/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
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
                txtviewMinCharge.setText(getResources().getString(R.string.Rs) + minCharge + " for " + minCharge_KM + "KM");
                TextView txtviewAddnCharge = (TextView) findViewById(R.id.txtviewAddnCharge);
                txtviewAddnCharge.setText(getResources().getString(R.string.Rs) + additionalFare + " for " + additionalFare_KM + "KM");
                TextView txtviewNightCharge = (TextView) findViewById(R.id.txtviewNightCharge);
                txtviewNightCharge.setText(nightCharge + "% Extra");
                TextView txtviewWatingCharge = (TextView) findViewById(R.id.txtviewWatingCharge);
                txtviewWatingCharge.setText(getResources().getString(R.string.Rs) + waitingCharge + " for " + waitingCharge_Min + "Mins");
            }
        } catch (Exception ex) {
            Log.e("AutoFare", ex.toString());
        }

    }
}


