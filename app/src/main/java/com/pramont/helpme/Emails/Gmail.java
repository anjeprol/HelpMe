package com.pramont.helpme.Emails;

import com.pramont.helpme.Pojos.NotificationSettings.UserSettings;
import com.pramont.helpme.Utils.Constants;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Gmail extends Constants{
    private static String TO ;

    public static void sendMail(final UserSettings userSettings) {
        Properties props = new Properties();
        StringBuilder stringBuilder = new StringBuilder();
        final String [] USER = userSettings.getMailFrom().split("@");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USER[0],userSettings.getPassword());
                    }
                });

        for(String string : userSettings.getMailsTo())
        {
            stringBuilder
                    .append(string)
                    .append(SEPARATOR);
        }

        TO = stringBuilder.toString();
        TO = TO.substring(0,TO.length()-1);

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userSettings.getMailFrom()));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse(TO)); // TO
            message.setSubject(userSettings.getSubject());
            message.setText(userSettings.getDefaultMessage()+JUMP_LINE+userSettings.getLocation());

            Transport.send(message);

            System.out.println("Sending email... [Done]");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


}
