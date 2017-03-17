package com.pramont.helpme.Pojos.ESettings;

/**
 * Created by antoniopradoo on 3/16/17.
 */

public class EmailSettings {
    private String mailTo;
    private String mailFrom;
    private String user;
    private String passwd;
    private String subject;
    private String bodyMesg;

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBodyMesg() {
        return bodyMesg;
    }

    public void setBodyMesg(String bodyMesg) {
        this.bodyMesg = bodyMesg;
    }
}
