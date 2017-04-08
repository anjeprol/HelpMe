package com.pramont.helpme.Utils;

import android.content.SharedPreferences;

import com.pramont.helpme.Pojos.NotificationSettings.Contacts;
import com.pramont.helpme.Pojos.NotificationSettings.Preferences;


/**
 * Created by antoniopradoo on 3/30/17.
 */

public class UserPreferences extends Constants {
    private SharedPreferences mSharedPreferences;
    private Preferences userPreferences = new Preferences();

    public UserPreferences(SharedPreferences sharedPreferences) {
        this.mSharedPreferences = sharedPreferences;
    }

    private static final String TAG = "UserPreferences";

    /*
    * Method to get all the preferences at once
    * */
    public Preferences getPreferences() {
        String emails;
        String phones;

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

        emails = mSharedPreferences
                .getString(MAILS,
                        DEFAULT_VALUE);

        phones = mSharedPreferences.
                getString(PHONES,
                        DEFAULT_VALUE);

        userPreferences.setContacts(
                getContacts(
                        phones,
                        emails));

        return userPreferences;
    }

    /*
    * method to get the contact object
    * */
    private Contacts getContacts(String phones, String emails) {
        Contacts contacts = new Contacts();
        Utils utils = new Utils();

        contacts.setPhoneNumbers(utils.getPhones(phones));
        contacts.setMailTo(utils.getEmails(emails));

        return contacts;
    }

    /*
    * Method to set all the preferences on one hit
    * */
    public void setPreferences(Preferences userSettings) {
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
                            false).toString());
        }
        editor.putString(
                PHONES,
                concat(
                        userSettings.getContacts(),
                        userSettings.isEmailChecked(),
                        true).toString());
        editor.commit();
    }

    /*
    *  method to concatenate the numbers and the emails separated by comas
    * */
    private StringBuilder concat(Contacts contacts, boolean isEmailChecked, boolean isPhone) {

        StringBuilder stringBuilder = new StringBuilder();
        int size = contacts.getPhoneNumbers().size();

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
