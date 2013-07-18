/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.*;
import ontopad.domain.Account;
import ontopad.manager.AccountManager;
import ontopad.producer.LoginRequest;

/**
 *
 * @author Amann
 */
@MessageDriven(mappedName = "jms/LoginQueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class LoginService implements MessageListener {

    @Resource
    private SessionContext context;
    @Inject
    private AccountManager accountManager;
    @Inject
    @LoginRequest
    private QueueConnection loginConnection;
    @Inject
    @LoginRequest
    private QueueSession loginSession;

    @Override
    public void onMessage(Message message) {
        String username = null;
        String password;
        try {
            username = message.getStringProperty("username");
            password = message.getStringProperty("password");
        } catch (JMSException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        Account account = accountManager.findById(username);
        if (account != null) {
            try {
                TextMessage requestMessage = (TextMessage) message;
                Destination replyDestination = message.getJMSReplyTo();
                MessageProducer replyProducer = loginSession.createProducer(replyDestination); 
                TextMessage replyMessage = loginSession.createTextMessage();
                replyMessage.setText("You win");
                replyMessage.setJMSCorrelationID(requestMessage.getJMSMessageID());
                replyProducer.send(replyMessage);
            } catch (JMSException ex) {
                Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
        }
        try {
            loginConnection.close();
        } catch (JMSException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
