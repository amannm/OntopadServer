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
public class LoginRequestProducer {

    @Resource(name = "jms/LoginConnectionFactory")
    private QueueConnectionFactory loginConnectionFactory;
    @Resource(name = "jms/LoginQueue")
    private Queue loginQueue;

    @Produces
    @LoginRequest
    public QueueConnection createLoginRequestConnection() throws JMSException {
        return loginConnectionFactory.createQueueConnection();
    }

    @Produces
    @LoginRequest
    public Queue getLoginRequestQueue() throws JMSException {
        return loginQueue;
    }

    @Produces
    @LoginRequest
    public QueueSession createLoginRequestSession(@LoginRequest QueueConnection conn) throws JMSException {
        return conn.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);
    }

    public void closeLoginRequestSession(@Disposes @LoginRequest QueueConnection conn) throws JMSException {
        conn.close();
    }

    public void closeLoginRequestSession(@Disposes @LoginRequest QueueSession session) throws JMSException {
        session.close();
    }
}
