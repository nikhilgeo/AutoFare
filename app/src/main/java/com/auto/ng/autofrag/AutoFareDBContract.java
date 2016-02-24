package com.auto.ng.autofrag;

import android.provider.BaseColumns;

/**
 * Created by nikhilgeorge on 28-Dec-15.
 */
public class AutoFareDBContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public AutoFareDBContract() {
    }

    /* Inner class that defines the table contents */
    public static abstract class BaseFareTemplate implements BaseColumns {
        public static final String TABLE_NAME = "BaseFare";
        public static final String COLUMN_NAME_minCharge = "minCharge";
        public static final String COLUMN_NAME_minCharge_KM = "minCharge_KM";
        public static final String COLUMN_NAME_additionalFare = "additionalFare";
        public static final String COLUMN_NAME_additionalFare_KM = "additionalFare_KM";
        public static final String COLUMN_NAME_nightCharge = "nightCharge";
        public static final String COLUMN_NAME_waitingCharge = "waitingCharge";
        public static final String COLUMN_NAME_waitingCharge_Min = "waitingCharge_Min";
        public static final String COLUMN_NAME_stateName = "state_city_name";

    }

    public static abstract class StateCityTemplate implements BaseColumns {
        public static final String TABLE_NAME = "StateCity";
        public static final String COLUMN_NAME_Place = "state_city_name";


    }
}
