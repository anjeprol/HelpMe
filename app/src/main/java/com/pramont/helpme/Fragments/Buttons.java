package com.pramont.helpme.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pramont.helpme.Emails.Gmail;
import com.pramont.helpme.R;

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


    public Buttons() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_buttons, container, false);


        mService_ImgButton = (ImageButton) rootView.findViewById(R.id.service_img_Btn);
        mAlert_ImgButton = (ImageButton) rootView.findViewById(R.id.alert_img_Btn);
        mService_Message_tv = (TextView) rootView.findViewById(R.id.service_message_tv);
        mAlert_Message_tv = (TextView) rootView.findViewById(R.id.alert_message_tv);

        mService_ImgButton.setOnClickListener(this);
        mAlert_ImgButton.setOnClickListener(this);

        return rootView;
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

    /*
* method to activate the alerts
* */
    private void setAlert() {
        if (isAlertFirst)
        {
            startProgressDialog();
            isAlertFirst = false;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {

                    mAlert_ImgButton.setImageResource(R.drawable.ic_green_button);
                    mAlert_Message_tv.setText(getString(R.string.active));
                    progressDialog.dismiss();

                }
            }, 1000);   //1 seconds
                    /*
                    try {
                        //To send the email after checking permissions
                        onPermissionChecked();
                        Log.d(Constants.TAG_EMAIL,getString(R.string.email_sending));
                    } catch (Exception e) {
                        Log.e(Constants.TAG_EMAIL,getString(R.string.error)+e.getMessage(), e);
                    } */

        }
        else
        {
            mAlert_ImgButton.setImageResource(R.drawable.ic_red_button);
            mAlert_Message_tv.setText(getString(R.string.disabled));
            isAlertFirst = true;
        }

    }

    /*
    * method to activate the service
    * */
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
                    /*
                    try {
                        //To send the email after checking permissions
                        onPermissionChecked();
                        Log.d(Constants.TAG_EMAIL,getString(R.string.email_sending));
                    } catch (Exception e) {
                        Log.e(Constants.TAG_EMAIL,getString(R.string.error)+e.getMessage(), e);
                    } */

        }
        else
        {
            mService_ImgButton.setImageResource(R.drawable.ic_red_button);
            mService_Message_tv.setText(getString(R.string.disabled));
            isServiceFirst = true;
        }
    }

    private void startProgressDialog() {
        progressDialog = ProgressDialog.show(getActivity(),
                "Activating",
                "Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    /*
    * Methond to send the email
    * */
    public void onPermissionChecked() {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //Sending mail
            Gmail.sendMail();
        }
    }

}
