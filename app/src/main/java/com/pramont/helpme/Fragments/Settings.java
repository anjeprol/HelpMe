package com.pramont.helpme.Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.pramont.helpme.Activities.MainActivity;
import com.pramont.helpme.Pojos.NotificationSettings.Preferences;
import com.pramont.helpme.R;
import com.pramont.helpme.Utils.Constants;
import com.pramont.helpme.Utils.UserPreferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment implements CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {
    private Switch mSwitchGmail;
    private LinearLayout mGmail_section_ll;
    private SeekBar mSensibility_SB;
    private TextView mProgress_tv;
    private EditText mMessage_et;
    private EditText mEmailUsr_et;
    private EditText mPassword_et;
    private boolean mIsSharedPreference = false;
    private Preferences mProfile = new Preferences();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        mSwitchGmail = (Switch) rootView.findViewById(R.id.sw_email);
        mGmail_section_ll = (LinearLayout) rootView.findViewById(R.id.gmail_login_ll);
        mSensibility_SB = (SeekBar) rootView.findViewById(R.id.level_sensibility_sb);
        mProgress_tv = (TextView) rootView.findViewById(R.id.level_sensibility_percent);
        mMessage_et = (EditText) rootView.findViewById(R.id.et_body_msg);
        mEmailUsr_et = (EditText) rootView.findViewById(R.id.et_email_usr);
        mPassword_et = (EditText) rootView.findViewById(R.id.et_password_email);

        mProgress_tv.setText(mProgress_tv.getText().toString() + Constants.DEFAULT_VALUE + getString(R.string.symb_percentage));


        mSwitchGmail.setOnCheckedChangeListener(this);
        mSensibility_SB.setOnSeekBarChangeListener(this);

        loadData();
        return rootView;
    }

    //To Load the data from sharePreferences that comes from the bundle
    private void loadData() {
        Bundle bundle;
        int progress;
        if (getArguments() != null)
        {
            mIsSharedPreference = true;
            bundle = getArguments();
            mMessage_et.setText(bundle.getString(Constants.BODY_MESSAGE).trim());
            progress = bundle.getInt(Constants.SENSIBILITY);
            progress = progress * 10;
            mSensibility_SB.setProgress(progress);
            if (bundle.getBoolean(Constants.CHECKED_EMAIL))
            {
                mGmail_section_ll.setVisibility(View.VISIBLE);
                mSwitchGmail.setChecked(bundle.getBoolean(Constants.CHECKED_EMAIL));
                mPassword_et.setText(bundle.getString(Constants.PASSWORD).trim());
                mEmailUsr_et.setText(bundle.getString(Constants.USER_EMAIL).trim());
            }
            else
            {
                mGmail_section_ll.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, final boolean isChecked) {
        //to send the status from emails enabled
        Intent intent = new Intent(Constants.BROADCAST);


        if (isChecked)
        {
            if (mIsSharedPreference)
            {
                mIsSharedPreference = false;
                mGmail_section_ll.setVisibility(View.VISIBLE);

            }
            else
            {
                showAlert(getContext());
            }
        }
        else
        {
            mGmail_section_ll.setVisibility(View.GONE);
        }
        intent.putExtra(Constants.CHECKED_EMAIL,isChecked);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
        //Saving the status (email switch) for sharedPreferences
        mProfile.setEmailChecked(isChecked);
    }

    //Method to show or hide the email section
    public void showAlert(Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(R.string.msg_email_notification)
                .setMessage(R.string.alert_message_gmail_notification)
                .setNegativeButton(context.getString(R.string.alert_opt_not), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mSwitchGmail.setChecked(false);

                    }
                })
                .setPositiveButton(context.getString(R.string.alert_opt_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mGmail_section_ll.setVisibility(View.VISIBLE);
                    }
                })
                .show();
        alertDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onStop() {
        super.onStop();
        String message = mMessage_et.getText().toString();
        String password = mPassword_et.getText().toString();
        String email = mEmailUsr_et.getText().toString();

        //Saving the body message in case of exist
        if (!message.trim().isEmpty())
        {
            mProfile.setBodyMessage(message);
        }
        if (mProfile.isEmailChecked())
        {
            if (!email.trim().isEmpty())
            {
                mProfile.setMailFrom(email);
            }
            if (!password.trim().isEmpty())
            {
                mProfile.setPassword(password);
            }
        }

        //Setting the sharedPreference in order to send the user information for sharedPreferences
        UserPreferences userPreferences = new UserPreferences(
                getContext()
                        .getSharedPreferences(Constants.PREFERENCES, getContext().MODE_PRIVATE));
        //Saving the whole structure from user preferences, at this point al the values should be
        //into the structure for settings fragment
        userPreferences.setPreferences(mProfile);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Integer min = getResources().getInteger(R.integer.min_required_sensibility_int);
        int sens = 0;
        String symbol = getString(R.string.symb_percentage);
        StringBuilder minReqStringBuilder = new StringBuilder();
        StringBuilder progressStringBuilder = new StringBuilder();


        minReqStringBuilder
                .append(min)
                .append(Constants.DEFAULT_VALUE)
                .append(symbol);

        if (progress < min)
        {
            progress = min;
            mProgress_tv.setText(minReqStringBuilder.toString());
            mSensibility_SB.setProgress(progress);
        }
        progressStringBuilder
                .append(progress)
                .append(Constants.DEFAULT_VALUE)
                .append(symbol);

        mProgress_tv.setText(progressStringBuilder.toString());
        //Saving the sensibility to sharePreferences
        sens = progress / 10;
        mProfile.setSensibility(sens);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
