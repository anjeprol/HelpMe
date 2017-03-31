package com.pramont.helpme.Utils;

import android.content.SharedPreferences;
import android.nfc.Tag;
import android.util.Log;

import com.pramont.helpme.Pojos.NotificationSettings.Preferences;

/**
 * Created by antoniopradoo on 3/30/17.
 */

public class UserPreferences extends Constants {
    private SharedPreferences mSharedPreferences;
    private Preferences userPreferences;

    public UserPreferences(SharedPreferences sharedPreferences) {
        this.mSharedPreferences = sharedPreferences;
    }

    private static final String TAG = "UserPreferences";

    public Preferences getPreferences() {
        userPreferences.setBodyMessage(
                mSharedPreferences.getString(
                        BODY_MESSAGE,
                        DEFAULT_VALUE));

        userPreferences.setEmailChecked(
                mSharedPreferences.getBoolean(
                        CHECKED_EMAIL,
                        false));

        userPreferences.setSensibility(
                Integer.valueOf(
                        mSharedPreferences.getString(
                                SENSIBILITY,
                                DEFAULT_VALUE)));

        userPreferences.setPassword(
                mSharedPreferences.getString(
                        PASSWORD,
                        DEFAULT_VALUE));

        userPreferences.setMailFrom(
                mSharedPreferences.getString(
                        USER_EMAIL,
                        DEFAULT_VALUE));
        //TODO add the phones and emails from contacts, here is required the method to add one string with all the phones and emails depends  of status

        return userPreferences;
    }

    public void setPreferences(String key, String string) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key,string);
        editor.commit();
    }

    public void setPreferences(String key, boolean status) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key,status);
        editor.commit();
    }
}
