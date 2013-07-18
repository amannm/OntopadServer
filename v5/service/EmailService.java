/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.service;

import java.io.Serializable;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author admin
 */
@ApplicationScoped
public class EmailService implements Serializable {

    @Resource(name = "mail/ontopad")
    private Session emailSession;

    public void send(String recipient, String subject, String body) throws MessagingException {
        Message message = new MimeMessage(emailSession);
        message.setFrom(new InternetAddress("ontopad@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(subject);
        message.setText(body);
        message.saveChanges();
        Transport.send(message);
    }

    public void read() throws NoSuchProviderException, MessagingException {
        Store store = emailSession.getStore();
        store.connect();
        Folder inbox = store.getFolder("inbox");
        inbox.open(Folder.READ_ONLY);
        Message[] messages = inbox.getMessages();
        for (Message m : messages) {
        }
        inbox.close(false);
        store.close();
    }
}
