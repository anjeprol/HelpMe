package com.pramont.helpme.Fragments;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pramont.helpme.Emails.Gmail;
import com.pramont.helpme.Pojos.NotificationSettings.UserSettings;
import com.pramont.helpme.R;
import com.pramont.helpme.Utils.Constants;
import com.pramont.helpme.Utils.Preferences;
import com.pramont.helpme.Utils.Utils;
import com.pramont.helpme.sms.Notifications;

/**
 * A simple {@link Fragment} subclass.
 */
public class Buttons extends Fragment implements View.OnClickListener {

    private ImageButton mService_ImgButton;
    private ImageButton mAlert_ImgButton;
    private TextView mService_Message_tv;
    private TextView mAlert_Message_tv;
    private boolean isServiceFirst = true;
    private boolean isAlertFirst = true;
    private ProgressDialog progressDialog;
    private UserSettings mProfile;
    private Notifications mNotifications;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mNotifications = new Notifications(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_buttons, container, false);
        checkPermissions();

        mService_ImgButton = (ImageButton) rootView.findViewById(R.id.service_img_Btn);
        mAlert_ImgButton = (ImageButton) rootView.findViewById(R.id.alert_img_Btn);
        mService_Message_tv = (TextView) rootView.findViewById(R.id.service_message_tv);
        mAlert_Message_tv = (TextView) rootView.findViewById(R.id.alert_message_tv);

        mService_ImgButton.setOnClickListener(this);
        mAlert_ImgButton.setOnClickListener(this);
        loadData();
        return rootView;
    }

    private void loadData() {
        mProfile = new Utils()
                .getUserData(
                        new Preferences(getActivity()
                                .getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.service_img_Btn:
                setService();
                break;
            case R.id.alert_img_Btn:
                setAlert();
                break;
        }
    }

    /**
     * Method to create the alert service in order to notify all the contacts
     */
    private void setService() {
        if (isServiceFirst)
        {
            startProgressDialog();
            isServiceFirst = false;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {

                    mService_ImgButton.setImageResource(R.drawable.ic_green_button);
                    mService_Message_tv.setText(getString(R.string.active));
                    progressDialog.dismiss();

                }
            }, 1000);   //1 seconds

            try
            {
                //To send the email after checking permissions
                //activateAlerts();
                Log.d(Constants.TAG_EMAIL, getString(R.string.email_sending));
            }
            catch (Exception e)
            {
                Log.e(Constants.TAG_EMAIL, getString(R.string.error) + e.getMessage(), e);
            }

        }
        else
        {
            mService_ImgButton.setImageResource(R.drawable.ic_red_button);
            mService_Message_tv.setText(getString(R.string.disabled));
            isServiceFirst = true;
        }

    }

    /*
    * method to activate the service
    * */
    private void setAlert() {
        if (isAlertFirst)
        {
            isAlertFirst = false;
            try
            {
                //To send the email after checking permissions
                // activateAlerts();
                //    if (activateAlerts())
                //    {
                activateAlerts();
                startProgressDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        mAlert_ImgButton.setImageResource(R.drawable.ic_green_button);
                        mAlert_Message_tv.setText(getString(R.string.active));
                        progressDialog.dismiss();
                        Log.d(Constants.TAG_EMAIL, getString(R.string.email_sending));
                    }
                }, 1000);   //1 seconds

                //       }

            }
            catch (Exception e)
            {
                Log.e(Constants.TAG_EMAIL, getString(R.string.error) + e.getMessage(), e);
            }

        }
        else
        {
            mAlert_ImgButton.setImageResource(R.drawable.ic_red_button);
            mAlert_Message_tv.setText(getString(R.string.disabled));
            isAlertFirst = true;
        }
    }

    private void startProgressDialog() {
        progressDialog = ProgressDialog.show(getActivity(),
                getString(R.string.dialog_activating),
                getString(R.string.dialog_wait));
        progressDialog.setCanceledOnTouchOutside(false);
    }

    /*
    * Method to send the email
    * */
    public void activateAlerts() {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        boolean isPhone = false;
        loadData();

        mProfile.setDefaultMessage(getString(R.string.default_msg_text));
        mProfile.setSubject(getString(R.string.gmail_subject));
        //TODO add the location here and activate
        mProfile.setLocation(getString(R.string.url_gmaps));
       /* if (mProfile.isEmailChecked())
        { //To send the emails
            if (mProfile.getPhoneNumbers() != null && !mProfile.getPhoneNumbers().isEmpty())
            {
                if (!mProfile.getPhoneNumbers().get(0).trim().isEmpty())
                {
                    if (SDK_INT > Build.VERSION_CODES.FROYO)
                    {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                .permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        //Sending mail
                        Gmail.sendMail(mProfile);
                        //return true;
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "There is not contacts configured to notify", Toast.LENGTH_LONG).show();
                }
            }
        } */

        //To send the SMS
        mNotifications.sendSMS(mProfile);

        // return false;
    }

    private void checkPermissions() {
        //Checking the version of the current SDK
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            //Asking for permissions before send the message
            int hasWriteContactsPermission = getActivity().checkSelfPermission(Manifest.permission.SEND_SMS);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, Constants.REQUEST_CODE_ASK_PERMISSIONS);
            }
            //TODO add the permissions from Location
        }

    }

}
