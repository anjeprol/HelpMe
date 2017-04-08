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
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.pramont.helpme.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment implements CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {
    private Switch mSwitchGmail;
    private LinearLayout mGmail_section_ll;
    private SeekBar mSensibility_SB;
    private TextView mProgress_tv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        mSwitchGmail = (Switch) rootView.findViewById(R.id.sw_email);
        mGmail_section_ll = (LinearLayout) rootView.findViewById(R.id.gmail_login_ll);
        mSensibility_SB = (SeekBar) rootView.findViewById(R.id.level_sensibility_sb);
        mProgress_tv = (TextView) rootView.findViewById(R.id.level_sensibility_percent);

        mProgress_tv.setText(mProgress_tv.getText().toString() + " " + getString(R.string.symb_percentage));

        mGmail_section_ll.setVisibility(View.GONE);
        mSwitchGmail.setOnCheckedChangeListener(this);
        mSensibility_SB.setOnSeekBarChangeListener(this);

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
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //No actions for now
                            mSwitchGmail.setChecked(false);

                        }
                    })
                    .setPositiveButton(context.getString(R.string.alert_opt_yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //TODO enable to show the section for gmail account
                            mGmail_section_ll.setVisibility(View.VISIBLE);
                        }
                    })
                    .show();
            alertDialog.setCanceledOnTouchOutside(false);
        }
        else
        {
            mGmail_section_ll.setVisibility(View.GONE);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Integer min = getResources().getInteger(R.integer.min_required_sensibility_int);
        StringBuilder minReqStringBuilder = new StringBuilder();
        StringBuilder progressStringBuilder = new StringBuilder();
        String symbol = getString(R.string.symb_percentage);

        minReqStringBuilder
                .append(min)
                .append(" ")
                .append(symbol);

        if (progress < min)
        {
            progress = min;
            mProgress_tv.setText(minReqStringBuilder.toString());
            mSensibility_SB.setProgress(progress);
        }
        progressStringBuilder
                .append(progress)
                .append(" ")
                .append(symbol);

        mProgress_tv.setText(progressStringBuilder.toString());
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
