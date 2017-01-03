/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grupoconamerica.backend.jms;

import java.io.Serializable;
import javax.ejb.Local;

/**
 *
 * @author CarlosDaniel
 */
@Local
public interface JMSSmsClientSessionBeanLocal {
        
    void sendJMSMessageToSendMessageQueue(Serializable messageData, long deliveryDelay, int priority);
       
    void sendJMSMessageToQueueMessageQueue(Serializable messageData, long deliveryDelay, int priority);
            
}
