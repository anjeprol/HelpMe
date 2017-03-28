package com.pramont.helpme.Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.pramont.helpme.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private Switch mSwitchGmail;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        mSwitchGmail = (Switch) rootView.findViewById(R.id.sw_email);
        mSwitchGmail.setOnCheckedChangeListener(this);

        return rootView;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        Context context = getContext();
        if (isChecked)
        {
           AlertDialog alertDialog = new AlertDialog.Builder(context)
                    .setTitle(R.string.msg_email_notification)
                    .setMessage(R.string.alert_message_gmail_notification)
                    .setNegativeButton(context.getString(R.string.alert_opt_not), new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialogInterface, int i) {
                            //No actions for now
                            mSwitchGmail.setChecked(false);
                        }
                    })
                    .setPositiveButton(context.getString(R.string.alert_opt_yes), new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialogInterface, int i) {
                        //TODO enable to show the section for gmail account
                        }
                    })
                    .show();
            alertDialog.setCanceledOnTouchOutside(false);
        }
        else
        {

        }
    }
}
