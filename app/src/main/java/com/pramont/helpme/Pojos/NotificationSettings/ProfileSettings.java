package com.pramont.helpme.Pojos.NotificationSettings;

import java.util.ArrayList;

/**
 * Created by antoniopradoo on 3/16/17.
 */

public class ProfileSettings {

    private String mailFrom;
    private String fullName;
    private String password;
    private String subject;
    private String bodyMessage;
    private boolean isEmailChecked;
    private ArrayList<Integer> phoneNumbers;
    private ArrayList<String> mailTo;
    private int sensibility ;

    public int getSensibility() {
        return sensibility;
    }

    public void setSensibility(int sensibility) {
        this.sensibility = sensibility;
    }

    public ArrayList<Integer> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(ArrayList<Integer> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public ArrayList<String> getMailTo() {
        return mailTo;
    }

    public void setMailTo(ArrayList<String> mailTo) {
        this.mailTo = mailTo;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBodyMessage() {
        return bodyMessage;
    }

    public void setBodyMessage(String bodyMessage) {
        this.bodyMessage = bodyMessage;
    }
}
