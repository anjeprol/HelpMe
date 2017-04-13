package com.pramont.helpme.Pojos.NotificationSettings;

import java.util.ArrayList;

/**
 * Created by antoniopradoo on 3/28/17.
 */

public class Contact {
    private ArrayList<Long> phoneNumbers;
    private ArrayList<String> mailTo;

    public ArrayList<Long> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(ArrayList<Long> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public ArrayList<String> getMailTo() {
        return mailTo;
    }

    public void setMailTo(ArrayList<String> mailTo) {
        this.mailTo = mailTo;
    }
}
