package com.pramont.helpme.Utils;

import android.util.Log;

import com.pramont.helpme.Pojos.NotificationSettings.UserSettings;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by antoniopradoo on 3/30/17.
 */

public class Utils {

    /**
     * @Description method to split the emails and phones that are concatenated separated by comas
     * and return the ArrayList
     *
     * @Param emails String
     */
    public ArrayList<String> splitString(String string) {
        ArrayList<String> stringArrayList = new ArrayList<>(Arrays.asList(string.split(",")));

        for (String value : stringArrayList)
        {
            System.out.println(value);
        }
        return stringArrayList;
    }

    /**
     * @Description Method to read the sharedPreferences
     *
     * @Param preferences Preferences
     */
    public UserSettings getUserData(Preferences preferences){
        UserSettings userSettings = preferences.getPreferences();
        return userSettings;
    }
}
