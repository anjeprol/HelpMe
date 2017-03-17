package com.pramont.helpme.Fragments;


import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pramont.helpme.Emails.Gmail;
import com.pramont.helpme.R;
import com.pramont.helpme.Utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class Buttons extends Fragment implements View.OnClickListener {

    private ImageButton mImgButton;
    private TextView    mMessage_tv;
    private boolean isFirst = true;

    public Buttons() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_buttons, container, false);

        mImgButton  = (ImageButton) rootView.findViewById(R.id.imgBtn);
        mMessage_tv = (TextView) rootView.findViewById(R.id.tv_message);

        mImgButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.imgBtn:
                if (isFirst)
                {
                    mImgButton.setImageResource(R.drawable.ic_green_button);
                    mMessage_tv.setText(getString(R.string.active));
                    isFirst = false;
                    try {
                        //To send the email after checking permissions
                        onPermissionChecked();
                        Log.d(Constants.TAG_EMAIL,getString(R.string.email_sending));
                    } catch (Exception e) {
                        Log.e(Constants.TAG_EMAIL,getString(R.string.error)+e.getMessage(), e);
                    }
                }else {
                    mImgButton.setImageResource(R.drawable.ic_red_button);
                    mMessage_tv.setText(getString(R.string.press_it));
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
