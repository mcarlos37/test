/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.facade;

import com.grupoconamerica.backend.dto.SmsMessageDTO;
import com.grupoconamerica.backend.entity.SmsMessage;
import com.grupoconamerica.backend.enums.SmsMessageProcessedStatus;
import com.grupoconamerica.backend.enums.SmsMessageSendType;
import com.grupoconamerica.backend.enums.SmsMessageType;
import com.grupoconamerica.backend.exception.JPAException;
import com.grupoconamerica.backend.exception.SmsException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.AccessTimeout;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Lock;
import static javax.ejb.LockType.READ;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import org.smslib.AGateway;
import org.smslib.GatewayException;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.TimeoutException;

/**
 *
 * @author CarlosDaniel
 */
@Singleton
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class SmsAsyncFacade implements SmsAsyncFacadeLocal {

    @Inject
    private com.grupoconamerica.backend.entity.facade.SmsMessageFacadeLocal smsMessageFacadeLocal;

    @Asynchronous
    @Lock(READ)
    @AccessTimeout(-1)
    @Override
    public Future<?> sendMessage(SmsMessageDTO smsDTO) throws SmsException {
        boolean success = true;
        OutboundMessage outboundMessage = null;
        String errorMessage = "";
        try {
            if (!evaluateCellphoneNumber(smsDTO.getPhoneNumber())) {
                errorMessage = "PhoneNumber is not a cellphoneNumber";
                throw new SmsException(errorMessage);
            }
            System.out.println("formatPhoneNumber : " + formatPhoneNumber(smsDTO.getPhoneNumber()));
            System.out.println("smsDTO.getMessage() : " + smsDTO.getMessage());
            outboundMessage = new OutboundMessage(formatPhoneNumber(smsDTO.getPhoneNumber()), smsDTO.getMessage());
            for (AGateway aGateway : (List<AGateway>) Service.getInstance().getGateways()) {
                System.out.println("====================================================");
                System.out.println("PUERTO : " + aGateway.getGatewayId());
            }
            Service.getInstance().sendMessage(outboundMessage, ((List<AGateway>) Service.getInstance().getGateways()).get(new Random().nextInt(((List<AGateway>) Service.getInstance().getGateways()).size())).getGatewayId());
        } catch (SmsException | TimeoutException | GatewayException | IOException | InterruptedException ex) {
            Logger.getLogger(SmsAsyncFacade.class.getName()).log(Level.SEVERE, null, ex);
            success = false;
            if (errorMessage.equals("") && outboundMessage != null) {
                errorMessage = outboundMessage.getErrorMessage();
            } else if (errorMessage.equals("") && outboundMessage == null) {
                errorMessage = ex.getMessage();
            }
        } finally {
                  System.out.println("outboundMessage : " + outboundMessage);
                   System.out.println("outboundMessage : " + outboundMessage);
                    System.out.println("outboundMessage : " + outboundMessage);
                     System.out.println("outboundMessage : " + outboundMessage);
            if (outboundMessage != null) {
                smsDTO.setGatewayId(outboundMessage.getGatewayId());
            }
            smsDTO.setSmsMessageType(SmsMessageType.OUTBOUND);
            smsDTO.setProcessedAt(new Date());
            smsDTO.setSmsMessageSendType(SmsMessageSendType.SYNC);
            if (success) {
                smsDTO.setSmsMessageProcessedStatus(SmsMessageProcessedStatus.SUCCESS);
            } else {
                smsDTO.setSmsMessageProcessedStatus(SmsMessageProcessedStatus.FAIL);
                smsDTO.setErrorMessage(errorMessage);
            }
            try {
                this.smsMessageFacadeLocal.create(new SmsMessage(smsDTO));
            } catch (JPAException ex) {
                Logger.getLogger(SmsAsyncFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new AsyncResult<>(true);
    }

    @Asynchronous
    @Lock(READ)
    @AccessTimeout(-1)
    @Override
    public Future<?> queueMessage(SmsMessageDTO smsDTO) throws SmsException {
        boolean success = true;
        String errorMessage = "";
        OutboundMessage outboundMessage = null;
        try {
            if (!evaluateCellphoneNumber(smsDTO.getPhoneNumber())) {
                errorMessage = "PhoneNumber is not a cellphoneNumber";
                throw new SmsException(errorMessage);
            }
            outboundMessage = new OutboundMessage(formatPhoneNumber(smsDTO.getPhoneNumber()), smsDTO.getMessage());
            Service.getInstance().queueMessage(outboundMessage, ((List<AGateway>) Service.getInstance().getGateways()).get(new Random().nextInt(((List<AGateway>) Service.getInstance().getGateways()).size())).getGatewayId());
        } catch (SmsException ex) {
            Logger.getLogger(SmsAsyncFacade.class.getName()).log(Level.SEVERE, null, ex);
            success = false;
            if (errorMessage.equals("") && outboundMessage != null) {
                errorMessage = outboundMessage.getErrorMessage();
            } else if (errorMessage.equals("") && outboundMessage == null) {
                errorMessage = ex.getMessage();
            }
        } finally {
            if (outboundMessage != null) {
                smsDTO.setGatewayId(outboundMessage.getGatewayId());
            }
            smsDTO.setSmsMessageType(SmsMessageType.OUTBOUND);
            smsDTO.setProcessedAt(new Date());
            smsDTO.setSmsMessageSendType(SmsMessageSendType.ASYNC);
            if (success) {
                smsDTO.setSmsMessageProcessedStatus(SmsMessageProcessedStatus.SUCCESS);
            } else {
                smsDTO.setSmsMessageProcessedStatus(SmsMessageProcessedStatus.FAIL);
                smsDTO.setErrorMessage(errorMessage);
            }
            try {
                this.smsMessageFacadeLocal.create(new SmsMessage(smsDTO));
            } catch (JPAException ex) {
                Logger.getLogger(SmsAsyncFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new AsyncResult<>(true);
    }

    @Asynchronous
    @Lock(READ)
    @AccessTimeout(-1)
    @Override
    public Future<?> sendMessages(SmsMessageDTO[] smsDTOs) throws SmsException {
        List<OutboundMessage> outboundMessages = new ArrayList<>();
        for (SmsMessageDTO smsDTO : smsDTOs) {
            OutboundMessage outboundMessage = new OutboundMessage(formatPhoneNumber(smsDTO.getPhoneNumber()), smsDTO.getMessage());
            outboundMessages.add(outboundMessage);
        }
        try {
            Service.getInstance().sendMessages(outboundMessages, ((List<AGateway>) Service.getInstance().getGateways()).get(new Random().nextInt(((List<AGateway>) Service.getInstance().getGateways()).size())).getGatewayId());
        } catch (TimeoutException | GatewayException | IOException | InterruptedException ex) {
            Logger.getLogger(SmsAsyncFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new AsyncResult<>(true);
    }

    @Asynchronous
    @Lock(READ)
    @AccessTimeout(-1)
    @Override
    public Future<?> queueMessages(SmsMessageDTO[] smsDTOs) throws SmsException {
        List<OutboundMessage> outboundMessages = new ArrayList<>();
        for (SmsMessageDTO smsDTO : smsDTOs) {
            OutboundMessage outboundMessage = new OutboundMessage(formatPhoneNumber(smsDTO.getPhoneNumber()), smsDTO.getMessage());
            outboundMessages.add(outboundMessage);
        }
        Service.getInstance().queueMessages(outboundMessages, ((List<AGateway>) Service.getInstance().getGateways()).get(new Random().nextInt(((List<AGateway>) Service.getInstance().getGateways()).size())).getGatewayId());
        return new AsyncResult<>(true);
    }

    private static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber.startsWith("0")) {
            phoneNumber = "+58" + phoneNumber.substring(1);
        }
        if (phoneNumber.startsWith("58")) {
            phoneNumber = "+" + phoneNumber.substring(0);
        }
        return phoneNumber;
    }

    private static boolean evaluateCellphoneNumber(String phoneNumber) {
        if (phoneNumber.contains("0414") || phoneNumber.contains("414")) {
            return true;
        }
        if (phoneNumber.contains("0424") || phoneNumber.contains("424")) {
            return true;
        }
        if (phoneNumber.contains("0416") || phoneNumber.contains("416")) {
            return true;
        }
        if (phoneNumber.contains("0426") || phoneNumber.contains("426")) {
            return true;
        }
        return phoneNumber.contains("0412") || phoneNumber.contains("412");
    }

}
