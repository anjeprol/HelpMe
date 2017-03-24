package com.pramont.helpme.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.pramont.helpme.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Contacts extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
        LinearLayout container_contacts_lly = (LinearLayout) rootView.findViewById(R.id.container_contacts);
        LinearLayout hidden_buttons_lly     = (LinearLayout) rootView.findViewById(R.id.hiddenButtons);
        View buttonsViews = getLayoutInflater(savedInstanceState).inflate(R.layout.hidden_buttons,container_contacts_lly,false);
        container_contacts_lly.addView(buttonsViews);
        return rootView;
    }

}
