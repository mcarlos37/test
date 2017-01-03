/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.jms;

import com.grupoconamerica.backend.dto.SmsDTO;
import com.grupoconamerica.backend.exception.SmsException;
import com.grupoconamerica.backend.facade.SmsAsyncFacadeLocal;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 *
 * @author CarlosDaniel
 */
@MessageDriven(mappedName = "jms/sms_send_message_queue",
        activationConfig = {
            @ActivationConfigProperty(propertyName = "destinationType",
                    propertyValue = "javax.jms.Queue")
        })
public class SmsSendMessageMessageBean implements MessageListener {
    
    @Inject
    private SmsAsyncFacadeLocal smsAsyncFacadeLocal;

    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            Object object = (Object) objectMessage.getObject();
            if(object instanceof SmsDTO){
                this.smsAsyncFacadeLocal.sendMessage((SmsDTO) object).get(30, TimeUnit.SECONDS);
            } else {
                this.smsAsyncFacadeLocal.sendMessages((SmsDTO[]) object).get();
            }
        } catch (JMSException | SmsException | InterruptedException | ExecutionException | TimeoutException ex) {
            Logger.getLogger(SmsSendMessageMessageBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
