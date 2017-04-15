package com.pramont.helpme.Utils;

import android.content.SharedPreferences;

import com.pramont.helpme.Pojos.NotificationSettings.UserSettings;

import java.util.ArrayList;


/**
 * Created by antoniopradoo on 3/30/17.
 */

public class Preferences extends Constants {
    private SharedPreferences mSharedPreferences;
    private UserSettings mUserSettings = new UserSettings();

    public Preferences(SharedPreferences sharedPreferences) {
        this.mSharedPreferences = sharedPreferences;
    }

    private static final String TAG = "Preferences";

    /*
    * Method to get all the preferences at once
    * */
    public UserSettings getPreferences() {
        String emails;
        String phones;
        String sens;

        mUserSettings.setBodyMessage(
                mSharedPreferences.getString(BODY_MESSAGE, DEFAULT_VALUE));

        mUserSettings.setEmailChecked(
                mSharedPreferences.getBoolean(CHECKED_EMAIL, false));

        sens = mSharedPreferences.getString(SENSIBILITY, DEFAULT_VALUE);
        if (!sens.trim().isEmpty())
        {
            mUserSettings.setSensibility(Integer.valueOf(sens));
        }

        mUserSettings.setPassword(
                mSharedPreferences.getString(PASSWORD, DEFAULT_VALUE));

        mUserSettings.setMailFrom(
                mSharedPreferences.getString(USER_EMAIL, DEFAULT_VALUE));

        emails = mSharedPreferences.getString(MAILS, DEFAULT_VALUE);

        phones = mSharedPreferences.getString(PHONES, DEFAULT_VALUE);

        mUserSettings.setMailsTo(new Utils().splitString(emails));

        mUserSettings.setPhoneNumbers(new Utils().splitString(phones));

        return mUserSettings;
    }

    /*
    * Method to set all the preferences on one hit
    * */
    public void setPreferences(UserSettings userSettings) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        editor.putString(BODY_MESSAGE, userSettings.getBodyMessage());

        editor.putBoolean(CHECKED_EMAIL, userSettings.isEmailChecked());

        editor.putString(SENSIBILITY,
                String.valueOf(
                        userSettings.getSensibility()));

        editor.putString(PASSWORD, userSettings.getPassword());

        editor.putString(USER_EMAIL, userSettings.getMailFrom());

        if (userSettings.isEmailChecked())
        {
            editor.putString(MAILS, concat(userSettings, true).toString());
        }
        editor.putString(PHONES, concat(userSettings, false).toString());

        editor.commit();
    }

    /*
    *  method to concatenate the numbers and the emails separated by comas
    * */
    private StringBuilder concat(UserSettings userSettings, boolean isEmail) {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> stringArrayList;
        if (isEmail)
        {
            stringArrayList = userSettings.getMailsTo();
        }
        else
        {
            stringArrayList = userSettings.getPhoneNumbers();
        }
        for (String string : stringArrayList)
        {
            stringBuilder.append(string);
            stringBuilder.append(SEPARATOR);
        }
        return stringBuilder;
    }
}
