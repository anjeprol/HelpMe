package com.pramont.helpme.Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.pramont.helpme.Pojos.NotificationSettings.UserSettings;
import com.pramont.helpme.R;
import com.pramont.helpme.Utils.Constants;
import com.pramont.helpme.Utils.Preferences;
import com.pramont.helpme.Utils.Utils;


/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment implements CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    private Switch mSwitchGmail;
    private LinearLayout mGmail_section_ll;
    private SeekBar mSensibility_SB;
    private TextView mProgress_tv;
    private EditText mMessage_et;
    private EditText mEmailUsr_et;
    private EditText mPassword_et;
    private boolean mIsSharedPreference = false;
    private boolean mIsVisible = false;
    private UserSettings mProfile;
    private ImageView mVisivility_iv;


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
        mVisivility_iv = (ImageView) rootView.findViewById(R.id.img_visivility);

        mProgress_tv.setText(mProgress_tv.getText().toString() + Constants.DEFAULT_VALUE + getString(R.string.symb_percentage));

        mSwitchGmail.setOnCheckedChangeListener(this);
        mSensibility_SB.setOnSeekBarChangeListener(this);
        mVisivility_iv.setOnClickListener(this);
        loadData();
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.img_visivility:
                if (!mIsVisible)
                {
                    mPassword_et.setTransformationMethod(null);
                    mVisivility_iv.setImageResource(R.drawable.ic_visibility_black_24dp);
                    mIsVisible = true;
                } else {
                    mPassword_et.setTransformationMethod(new PasswordTransformationMethod());
                    mVisivility_iv.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    mIsVisible = false;
                }
                break;
        }
    }

    //To Load the data from sharePreferences that comes from the bundle
    private void loadData() {
        int progress;
        mProfile = new Utils()
                .getUserData
                        (new Preferences(
                                getActivity().
                                        getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)));

        mIsSharedPreference = true;
        mMessage_et.setText(mProfile.getBodyMessage().trim());
        progress = mProfile.getSensibility();
        progress = progress * 10;
        mSensibility_SB.setProgress(progress);
        if (mProfile.isEmailChecked())
        {
            mGmail_section_ll.setVisibility(View.VISIBLE);
            mSwitchGmail.setChecked(mProfile.isEmailChecked());
            mPassword_et.setText(mProfile.getPassword().trim());
            mEmailUsr_et.setText(mProfile.getMailFrom().trim());
        }
        else
        {
            mGmail_section_ll.setVisibility(View.GONE);
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
        intent.putExtra(Constants.CHECKED_EMAIL, isChecked);
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
        Preferences preferences = new Preferences(
                getContext()
                        .getSharedPreferences(Constants.PREFERENCES, getContext().MODE_PRIVATE));
        //Saving the whole structure from user preferences, at this point al the values should be
        //into the structure for settings fragment
        preferences.setPreferences(mProfile);
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
