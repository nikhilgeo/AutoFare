package com.auto.ng.autofrag;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;


public class UpdateFare_Frag extends Fragment {

    View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_update_fare, container, false);
        AutoCompleteTextView multiAutoCompleteTextView = (AutoCompleteTextView) rootView.findViewById(R.id.multi_auto_complete_textview);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.select_dialog_item, Utilities.places.toArray(new String[0]));
        multiAutoCompleteTextView.setAdapter(adapter);
        multiAutoCompleteTextView.setThreshold(1);
        return rootView;
    }

}