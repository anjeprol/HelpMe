package com.pramont.helpme.Pojos.NotificationSettings;

import java.util.ArrayList;


public class UserSettings {

    private String mailFrom;
    private String password;
    private String bodyMessage;
    private boolean isEmailChecked;
    private int sensibility;
    private ArrayList<String> phoneNumbers;
    private ArrayList<String> mailsTo;

    public ArrayList<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(ArrayList<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public ArrayList<String> getMailsTo() {
        return mailsTo;
    }

    public void setMailsTo(ArrayList<String> mailsTo) {
        this.mailsTo = mailsTo;
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
