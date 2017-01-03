/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.jms;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;

/**
 *
 * @author CarlosDaniel
 */
@Stateless
public class JMSSmsClientSessionBean implements JMSSmsClientSessionBeanLocal {

    @Resource(mappedName = "jms/sms_send_message_queue")
    private Queue queueSMSSendMessage;
    @Resource(mappedName = "jms/sms_queue_message_queue")
    private Queue queueSMSQueueMessage;
    
    @Resource(mappedName = "jms/sms_connection_factory")
    private QueueConnectionFactory connectionFactorySMS;
    
    @Override
    public void sendJMSMessageToSendMessageQueue(Serializable messageData, long deliveryDelay, int priority) {
        try (Connection connection = connectionFactorySMS.createConnection();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                MessageProducer messageProducer = session.createProducer(queueSMSSendMessage)) {
            ObjectMessage message = session.createObjectMessage();
            message.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
            message.setObject(messageData);
            messageProducer.setDeliveryDelay(deliveryDelay);
            messageProducer.setPriority(priority);
            messageProducer.send(message);
        } catch (JMSException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sendJMSMessageToQueueMessageQueue(Serializable messageData, long deliveryDelay, int priority) {
        try (Connection connection = connectionFactorySMS.createConnection();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                MessageProducer messageProducer = session.createProducer(queueSMSQueueMessage)) {
            ObjectMessage message = session.createObjectMessage();
            message.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
            message.setObject(messageData);
            messageProducer.setDeliveryDelay(deliveryDelay);
            messageProducer.setPriority(priority);
            messageProducer.send(message);
        } catch (JMSException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
