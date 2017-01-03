/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.facade;

import com.grupoconamerica.backend.dto.SmsDTO;
import com.grupoconamerica.backend.entity.Sms;
import com.grupoconamerica.backend.enums.SmsProcessedStatus;
import com.grupoconamerica.backend.enums.SmsSendType;
import com.grupoconamerica.backend.enums.SmsType;
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
    private com.grupoconamerica.backend.entity.facade.SmsFacadeLocal smsFacadeLocal;

    @Asynchronous
    @Lock(READ)
    @AccessTimeout(-1)
    @Override
    public Future<?> sendMessage(SmsDTO smsDTO) throws SmsException {
        boolean success = true;
        OutboundMessage outboundMessage = null;
        String errorMessage = "";
        try {
            if (!evaluateCellphoneNumber(smsDTO.getPhoneNumber())) {
                errorMessage = "PhoneNumber is not a cellphoneNumber";
                throw new SmsException(errorMessage);
            }
            outboundMessage = new OutboundMessage(formatPhoneNumber(smsDTO.getPhoneNumber()), smsDTO.getMessage());
            Service.getInstance().sendMessage(outboundMessage, ((List<AGateway>) Service.getInstance().getGateways()).get(new Random().nextInt(((List<AGateway>) Service.getInstance().getGateways()).size())).getGatewayId());
        } catch (SmsException | TimeoutException | GatewayException | IOException | InterruptedException ex) {
            Logger.getLogger(SmsAsyncFacade.class.getName()).log(Level.SEVERE, null, ex);
            success = false;
            if(errorMessage.equals("") && outboundMessage != null){
                errorMessage = outboundMessage.getErrorMessage();
            } else if(errorMessage.equals("") && outboundMessage == null){
                errorMessage = ex.getMessage();
            }
        } finally {
            if(outboundMessage != null){
                smsDTO.setGatewayId(outboundMessage.getGatewayId());
            }
            smsDTO.setSmsType(SmsType.OUTBOUND);
            smsDTO.setProcessedAt(new Date());
            smsDTO.setSmsSendType(SmsSendType.SYNC);
            if (success) {
                smsDTO.setSmsProcessedStatus(SmsProcessedStatus.SUCCESS);
            } else {
                smsDTO.setSmsProcessedStatus(SmsProcessedStatus.FAIL);
                smsDTO.setErrorMessage(errorMessage);
            }
            try {
                this.smsFacadeLocal.create(new Sms(smsDTO));
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
    public Future<?> queueMessage(SmsDTO smsDTO) throws SmsException {
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
            if(errorMessage.equals("") && outboundMessage != null){
                errorMessage = outboundMessage.getErrorMessage();
            } else if(errorMessage.equals("") && outboundMessage == null){
                errorMessage = ex.getMessage();
            }
        } finally {
            if(outboundMessage != null){
                smsDTO.setGatewayId(outboundMessage.getGatewayId());
            }
            smsDTO.setSmsType(SmsType.OUTBOUND);
            smsDTO.setProcessedAt(new Date());
            smsDTO.setSmsSendType(SmsSendType.ASYNC);
            if (success) {
                smsDTO.setSmsProcessedStatus(SmsProcessedStatus.SUCCESS);
            } else {
                smsDTO.setSmsProcessedStatus(SmsProcessedStatus.FAIL);
                smsDTO.setErrorMessage(errorMessage);
            }
            try {
                this.smsFacadeLocal.create(new Sms(smsDTO));
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
    public Future<?> sendMessages(SmsDTO[] smsDTOs) throws SmsException {
        List<OutboundMessage> outboundMessages = new ArrayList<>();
        for (SmsDTO smsDTO : smsDTOs) {
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
    public Future<?> queueMessages(SmsDTO[] smsDTOs) throws SmsException {
        List<OutboundMessage> outboundMessages = new ArrayList<>();
        for (SmsDTO smsDTO : smsDTOs) {
            OutboundMessage outboundMessage = new OutboundMessage(formatPhoneNumber(smsDTO.getPhoneNumber()), smsDTO.getMessage());
            outboundMessages.add(outboundMessage);
        }
        Service.getInstance().queueMessages(outboundMessages, ((List<AGateway>) Service.getInstance().getGateways()).get(new Random().nextInt(((List<AGateway>) Service.getInstance().getGateways()).size())).getGatewayId());
        return new AsyncResult<>(true);
    }

    private static String formatPhoneNumber(String phoneNumber) {
        if(phoneNumber.startsWith("0")){
            phoneNumber = "+58" + phoneNumber.substring(1);
        } 
        if(phoneNumber.startsWith("58")){
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
