/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.connection;

import com.grupoconamerica.backend.delegate.SmsDelegate;
import com.grupoconamerica.backend.dto.SmsDTO;
import com.grupoconamerica.backend.enums.SmsProcessedStatus;
import com.grupoconamerica.backend.enums.SmsType;
import com.grupoconamerica.backend.exception.SmsException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import org.smslib.AGateway;
import org.smslib.GatewayException;
import org.smslib.IInboundMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.Message;
import org.smslib.TimeoutException;

/**
 *
 * @author CarlosDaniel
 */
@Stateless
public class InboundNotification implements IInboundMessageNotification {

    @Override
    public void process(AGateway gateway, Message.MessageTypes msgType, InboundMessage msg) {
        if (msgType == Message.MessageTypes.INBOUND) {
            System.out.println("New Inbound message detected from Gateway: " + msg.getOriginator() + " " + msg.getText());
            SmsDTO smsDTO = new SmsDTO();
            smsDTO.setPhoneNumber(msg.getOriginator());
            smsDTO.setMessage(msg.getText());
            smsDTO.setGatewayId(msg.getGatewayId());
            smsDTO.setProcessedAt(msg.getDate());
            smsDTO.setSmsType(SmsType.INBOUND);
            smsDTO.setSmsProcessedStatus(SmsProcessedStatus.SUCCESS);
            try {
                new SmsDelegate().create(smsDTO);
            } catch (SmsException ex) {
                Logger.getLogger(SmsConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                gateway.deleteMessage(msg);
            } catch (GatewayException | TimeoutException | IOException | InterruptedException ex) {
                Logger.getLogger(SmsConnection.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
