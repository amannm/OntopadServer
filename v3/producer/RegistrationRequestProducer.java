/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.producer;

import javax.annotation.Resource;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.jms.*;

/**
 *
 * @author Amann
 */
public class RegistrationRequestProducer {

    @Produces
    @RegistrationRequest
    @Resource(name = "jms/RegistrationConnectionFactory")
    private QueueConnectionFactory registrationConnectionFactory;
    @Produces
    @RegistrationRequest
    @Resource(name = "jms/RegistrationQueue")
    private Queue registrationQueue;
    
    @Produces @RegistrationRequest
    public QueueConnection createOrderConnection() throws JMSException {
        return registrationConnectionFactory.createQueueConnection();
    }

    @Produces @RegistrationRequest
    public QueueSession createOrderSession(@RegistrationRequest QueueConnection conn) throws JMSException {
        return conn.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);
    }

    public void closeOrderSession(@Disposes @RegistrationRequest QueueConnection conn) throws JMSException {
        conn.close();
    }

    public void closeOrderSession(@Disposes @RegistrationRequest QueueSession session) throws JMSException {
        session.close();
    }
}
