package com.pramont.helpme.Utils;

import java.util.ArrayList;

/**
 * Created by antoniopradoo on 3/30/17.
 */

public class Test {
    public static void main(String [] args) {
        Utils utils = new Utils();
        String testString = "1,2,3,4";

        ArrayList<Integer> stringArrayList = utils.getPhones(testString);
    }
}
