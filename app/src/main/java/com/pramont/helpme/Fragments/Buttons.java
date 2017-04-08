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

    private ImageButton mImgButton;
    private TextView    mMessage_tv;
    private boolean isFirst = true;
    private ProgressDialog progressDialog ;


    public Buttons() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_buttons, container, false);


        mImgButton  = (ImageButton) rootView.findViewById(R.id.service_img_Btn);
        mMessage_tv = (TextView) rootView.findViewById(R.id.service_message_tv);

        mImgButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.service_img_Btn:
                if (isFirst)
                {
                    progressDialog = ProgressDialog.show(getActivity(),
                        "Activating",
                        "Please wait...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    isFirst = false;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {

                            mImgButton.setImageResource(R.drawable.ic_green_button);
                            mMessage_tv.setText(getString(R.string.active));
                            progressDialog.dismiss();

                        }
                    }, 1000);   //5 seconds
                    /*
                    try {
                        //To send the email after checking permissions
                        onPermissionChecked();
                        Log.d(Constants.TAG_EMAIL,getString(R.string.email_sending));
                    } catch (Exception e) {
                        Log.e(Constants.TAG_EMAIL,getString(R.string.error)+e.getMessage(), e);
                    } */

                }else {
                    mImgButton.setImageResource(R.drawable.ic_red_button);
                    mMessage_tv.setText(getString(R.string.disabled));
                    isFirst=true;
                }
                break;
        }
    }

    /*
    * Methond to send the email
    * */
    public void onPermissionChecked()
    {
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
