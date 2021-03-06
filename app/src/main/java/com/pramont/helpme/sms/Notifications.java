package com.pramont.helpme.sms;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.pramont.helpme.Pojos.NotificationSettings.UserSettings;
import com.pramont.helpme.R;

/**
 * Created by antoniopradoo on 3/3/18.
 */

public class Notifications {

    private FragmentActivity mFragmentActivity;

    public Notifications(FragmentActivity fragmentActivity) {
        this.mFragmentActivity = fragmentActivity;
    }

    public void sendSMS(UserSettings mProfile) {
        StringBuilder message = new StringBuilder();

        if(mProfile.getBodyMessage().trim().isEmpty())
            message.append(mProfile.getDefaultMessage());
        else
            message.append(mProfile.getBodyMessage());
        message.append(" ");

        if (mProfile.getLocation() != null)
            message.append(mProfile.getLocation());


        for (String receiver : mProfile.getPhoneNumbers())
        {
            try
            {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(receiver, null, message.toString(), null, null);
                Log.d("SMS", "Enviando a :" + receiver + " Mensaje: " + mProfile.getDefaultMessage());
            }
            catch (android.content.ActivityNotFoundException ex)
            {
                Toast.makeText(mFragmentActivity, R.string.sms_error_message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
