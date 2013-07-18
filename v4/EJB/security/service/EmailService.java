/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.security.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.AccessLocalException;
import javax.ejb.EJBAccessException;
import javax.ejb.Stateless;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author admin
 */
@Stateless
public class EmailService {

    @Resource(name = "mail/ontopad")
    private Session session;

    @RolesAllowed("administrator")
    public void send(String recipient, String subject, String body) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setText(body);
        message.setSubject(subject);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.saveChanges();
        Transport.send(message);
    }
}
