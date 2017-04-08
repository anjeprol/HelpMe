package com.pramont.helpme.Utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by antoniopradoo on 3/30/17.
 */

public class Utils {

    /**
     * @Description method to split the emails that are concatenated separated by comas and return
     *              the ArrayList
     * @Param emils String
     */
    public ArrayList<String> getEmails(String emails) {
        ArrayList<String> singleEmails = new ArrayList<String>(Arrays.asList(emails.split(",")));

        for (String value : singleEmails)
        {
            System.out.println(value);
        }
        return singleEmails;
    }

    /**
     * @Description method to split the phones that are concatenated separated by comas and return
     *              the ArrayList converted in integers
     * @Param phones String
     */
    public ArrayList<Integer> getPhones(String phones) {
        ArrayList<String> stringArrayList = getEmails(phones);
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        for (String stringValue : stringArrayList)
        {
            try
            {
                //Convert String to Integer, and store it into integer array list.
                integerArrayList.add(Integer.parseInt(stringValue));
            }
            catch (NumberFormatException nfe)
            {
                //System.out.println("Could not parse " + nfe);
                Log.d("NumberFormat", "Parsing failed! " + stringValue + " can not be an integer");
            }
        }
        return integerArrayList;
    }
}
