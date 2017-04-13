package com.pramont.helpme.Utils;

import android.content.SharedPreferences;

import com.pramont.helpme.Pojos.NotificationSettings.Contact;
import com.pramont.helpme.Pojos.NotificationSettings.UserSettings;


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
                mSharedPreferences.getString(
                        BODY_MESSAGE,
                        DEFAULT_VALUE));

        mUserSettings.setEmailChecked(
                mSharedPreferences.getBoolean(
                        CHECKED_EMAIL,
                        false));

        sens = mSharedPreferences.getString(SENSIBILITY, DEFAULT_VALUE);
        if (!sens.trim().isEmpty())
        {
            mUserSettings.setSensibility(
                    Integer.valueOf(sens));
        }


        mUserSettings.setPassword(
                mSharedPreferences.getString(
                        PASSWORD,
                        DEFAULT_VALUE));

        mUserSettings.setMailFrom(
                mSharedPreferences.getString(
                        USER_EMAIL,
                        DEFAULT_VALUE));

        emails = mSharedPreferences
                .getString(MAILS,
                        DEFAULT_VALUE);

        phones = mSharedPreferences.
                getString(PHONES,
                        DEFAULT_VALUE);

        mUserSettings.setContacts(
                getContacts(
                        phones,
                        emails));

        return mUserSettings;
    }

    /*
    * method to get the contact object
    * */
    private Contact getContacts(String phones, String emails) {
        Contact contacts = new Contact();
        Utils utils = new Utils();

        contacts.setPhoneNumbers(utils.getPhones(phones));
        contacts.setMailTo(utils.getEmails(emails));

        return contacts;
    }

    /*
    * Method to set all the preferences on one hit
    * */
    public void setPreferences(UserSettings userSettings) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        editor.putString(
                BODY_MESSAGE,
                userSettings.getBodyMessage());
        editor.putBoolean(
                CHECKED_EMAIL,
                userSettings.isEmailChecked());
        editor.putString(
                SENSIBILITY,
                String.valueOf(
                        userSettings.getSensibility()));
        editor.putString(
                PASSWORD,
                userSettings.getPassword());
        editor.putString(
                USER_EMAIL,
                userSettings.getMailFrom());
        if (userSettings.isEmailChecked())
        {
            editor.putString(
                    MAILS,
                    concat(
                            userSettings.getContacts(),
                            userSettings.isEmailChecked(),
                            false)
                            .toString());
        }
        editor.putString(
                PHONES,
                concat(
                        userSettings.getContacts(),
                        userSettings.isEmailChecked(),
                        true)
                        .toString());
        editor.commit();
    }

    /*
    *  method to concatenate the numbers and the emails separated by comas
    * */
    private StringBuilder concat(Contact contacts, boolean isEmailChecked, boolean isPhone) {

        StringBuilder stringBuilder = new StringBuilder();
        int size = 0;
        if (contacts != null)
        {
            size = contacts.getPhoneNumbers().size();
        }

        for (int index = 0; index < size; index++)
        {
            if (index + 1 != size)
            {
                if (isEmailChecked && !isPhone)
                {
                    stringBuilder.append(contacts.getMailTo().get(index));
                    stringBuilder.append(SEPARATOR);
                }
                else
                {
                    stringBuilder.append(contacts.getPhoneNumbers().get(index));
                    stringBuilder.append(SEPARATOR);
                }
            }
            else
            {
                if (isPhone)
                {
                    stringBuilder.append(contacts.getPhoneNumbers().get(index));
                }
                else
                {
                    stringBuilder.append(contacts.getMailTo().get(index));
                }
            }
        }
        return stringBuilder;
    }
}
