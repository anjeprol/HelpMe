package com.pramont.helpme.Utils;

import java.util.ArrayList;

/**
 * Created by antoniopradoo on 3/30/17.
 */

public class Test {
    public static void main(String [] args) {
        Utils utils = new Utils();
        String testString = "antoniopradoo@gmail.com,anjeprol_prado@hotmail.com,anjeprolprado@gmail.com,prolananjeprol@gmail.com,otrocorreo@hotmail.com";

        ArrayList<String> stringArrayList = utils.splitString(testString);
    }
}
