package com.emailapi.services;


import com.emailapi.constants.Env;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {

    //method for sending email (only text)
    public boolean sendEmail(String subject, String message, String receiver) {

        boolean flag = false;

        //sender email and authentication username & password
        String sender = Env.getSender();
        String username = Env.getUsername();
        String password = Env.getPassword();

        //getting properties object
        Properties properties = System.getProperties();

        //setting mail server properties
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);

        //getting message session object
        Session session = Session.getInstance(properties, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        //compose message
        Message msg = new MimeMessage(session);

        try {

            //setting message sending properties
            msg.setFrom(new InternetAddress(sender));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            msg.setSubject(subject);
            msg.setText(message);

            //send message
            Transport.send(msg);

            System.out.println("Email sent successfully...!");
            flag = true;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return flag;
    }
}
