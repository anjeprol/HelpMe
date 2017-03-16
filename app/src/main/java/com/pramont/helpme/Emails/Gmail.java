package com.pramont.helpme.Emails;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Gmail {
    private static String TO   = "anjeproL_prado@hotmail.com";
    private static String FROM = "antoniopradoo@gmail.com";
    private static String USER = "antoniopradoo";
    private static String PASSWD = "lireovas4563";
    private static String LOCATION = "https://maps.google.com/?q=20.468998,-99.881996";

    public static void sendMail() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USER,PASSWD);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(TO)); // TO
            message.setSubject("Panic Button Message");
            message.setText("Estoy en problemas, necesito tu ayuda" +
                    "\n\n Esta es mi ultima ubicación "+LOCATION);

            Transport.send(message);

            System.out.println("Sending email... [Done]");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


}
