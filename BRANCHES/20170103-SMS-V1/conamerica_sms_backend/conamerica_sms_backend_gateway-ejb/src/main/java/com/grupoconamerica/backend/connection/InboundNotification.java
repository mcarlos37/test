/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.connection;

import com.grupoconamerica.backend.delegate.SmsMessageDelegate;
import com.grupoconamerica.backend.dto.SmsMessageDTO;
import com.grupoconamerica.backend.enums.SmsMessageProcessedStatus;
import com.grupoconamerica.backend.enums.SmsMessageType;
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
            SmsMessageDTO smsDTO = new SmsMessageDTO();
            smsDTO.setPhoneNumber(msg.getOriginator());
            smsDTO.setMessage(msg.getText());
            smsDTO.setGatewayId(msg.getGatewayId());
            smsDTO.setProcessedAt(msg.getDate());
            smsDTO.setSmsMessageType(SmsMessageType.INBOUND);
            smsDTO.setSmsMessageProcessedStatus(SmsMessageProcessedStatus.PROCESSING);
            try {
                new SmsMessageDelegate().create(smsDTO);
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
