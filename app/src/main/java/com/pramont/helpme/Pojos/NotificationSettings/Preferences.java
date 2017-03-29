package com.pramont.helpme.Pojos.NotificationSettings;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by antoniopradoo on 3/16/17.
 */

public class Preferences {

    private String mailFrom;
    private String fullName;
    private String password;
    private String bodyMessage;
    private boolean isEmailChecked;
    private int sensibility;
    private HashMap<Integer, Contacts> contactsHashMap;

    public HashMap<Integer, Contacts> getContactsHashMap() {
        return contactsHashMap;
    }

    public void setContactsHashMap(HashMap<Integer, Contacts> contactsHashMap) {
        this.contactsHashMap = contactsHashMap;
    }

    public int getSensibility() {
        return sensibility;
    }

    public void setSensibility(int sensibility) {
        this.sensibility = sensibility;
    }

    public boolean isEmailChecked() {
        return isEmailChecked;
    }

    public void setEmailChecked(boolean emailChecked) {
        isEmailChecked = emailChecked;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBodyMessage() {
        return bodyMessage;
    }

    public void setBodyMessage(String bodyMessage) {
        this.bodyMessage = bodyMessage;
    }
}
