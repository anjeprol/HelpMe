package com.pramont.helpme.utils;

/**
 * Created by antoniopradoo on 3/4/18.
 */

public class UtilsTest {
    private long millis = 0;

    public UtilsTest(final long millis) {
        this.millis = millis;
    }

    public void waitForIt() {
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
