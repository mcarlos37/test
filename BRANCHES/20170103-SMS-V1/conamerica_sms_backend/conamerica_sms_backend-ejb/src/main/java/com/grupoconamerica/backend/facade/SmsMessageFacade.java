/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.facade;

import com.grupoconamerica.backend.dto.SmsMessageDTO;
import com.grupoconamerica.backend.entity.SmsMessage;
import com.grupoconamerica.backend.enums.SmsMessageProcessedStatus;
import com.grupoconamerica.backend.enums.SmsMessageType;
import com.grupoconamerica.backend.exception.JPAException;
import com.grupoconamerica.backend.exception.SmsException;
import com.grupoconamerica.backend.exception.facade.SmsMessageFacadeRemote;
import com.grupoconamerica.backend.jms.JMSSmsClientSessionBeanLocal;
import com.grupoconamerica.backend.util.SmsMessageConverter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

/**
 *
 * @author CarlosDaniel
 */
@Stateless(name = "SmsMessageFacade", mappedName = SmsMessageFacadeRemote.JNDI_REMOTE_NAME)
@Remote(SmsMessageFacadeRemote.class)
@Local(SmsMessageFacadeLocal.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class SmsMessageFacade implements SmsMessageFacadeRemote, SmsMessageFacadeLocal {

    @Inject
    private JMSSmsClientSessionBeanLocal jmsSmsClientSessionBeanLocal;
    
    @Inject
    private com.grupoconamerica.backend.entity.facade.SmsMessageFacadeLocal smsFacadeLocal;

    @Override
    public String sendMessages(SmsMessageDTO[] smsMessageDTOs, boolean secureBatch) throws SmsException {
        if (smsMessageDTOs == null) {
            throw new SmsException("smsMessageDTOs is null");
        }
        if (smsMessageDTOs.length == 0) {
            throw new SmsException("smsMessageDTOs is empty");
        }
        String ticket = UUID.randomUUID().toString();
        for(SmsMessageDTO smsMessageDTO : smsMessageDTOs){
            smsMessageDTO.setTicket(ticket);
        }
        if (secureBatch) {
            for (SmsMessageDTO smsMessageDTO : smsMessageDTOs) {
                this.sendMessage(smsMessageDTO);
            }
        } else {
            this.jmsSmsClientSessionBeanLocal.sendJMSMessageToSendMessageQueue(smsMessageDTOs, 0, 9);
        }
        return ticket;
    }

    @Override
    public String queueMessages(SmsMessageDTO[] smsDTOs, boolean secureBatch) throws SmsException {
        if (smsDTOs == null) {
            throw new SmsException("smsDTOs is null");
        }
        if (smsDTOs.length == 0) {
            throw new SmsException("smsDTOs is empty");
        }
        String ticket = UUID.randomUUID().toString();
        for(SmsMessageDTO smsDTO : smsDTOs){
            smsDTO.setTicket(ticket);
        }
        if (secureBatch) {
            for (SmsMessageDTO smsDTO : smsDTOs) {
                this.queueMessage(smsDTO);
            }
        } else {
            this.jmsSmsClientSessionBeanLocal.sendJMSMessageToQueueMessageQueue(smsDTOs, 0, 9);
        }
        return ticket;
    }
    
    @Override
    public void create(SmsMessageDTO smsDTO) throws SmsException {
        if(smsDTO == null){
            throw new SmsException("smsDTO is null");
        }
        try {
            this.smsFacadeLocal.create(new SmsMessage(smsDTO));
        } catch (JPAException ex) {
            Logger.getLogger(SmsMessageFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new SmsException(ex.getMessage(),ex);
        }
    }
    
    @Override
    public List<SmsMessageDTO> findAll(SmsMessageType smsType, Date initDate, Date endDate) throws SmsException {
        try {
            return SmsMessageConverter.smsMessagesToSmsMessageDTOs(this.smsFacadeLocal.findAll(smsType, initDate, endDate));
        } catch (JPAException ex) {
            Logger.getLogger(SmsMessageFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new SmsException(ex.getMessage(), ex);
        }
    }
    
    @Override
    public List<SmsMessageDTO> findAllBySmsMessageType(SmsMessageType smsType, Date initDate, Date endDate, int[] range) throws SmsException {
        try {
            List<SmsMessage> smsMessage = new ArrayList();
            smsMessage = this.smsFacadeLocal.findAllBySmsMessageType(smsType, initDate, endDate, range);
            this.smsFacadeLocal.updateProcessStatus(smsMessage);
            return SmsMessageConverter.smsMessagesToSmsMessageDTOs(smsMessage);
        } catch (JPAException ex) {
            Logger.getLogger(SmsMessageFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new SmsException(ex.getMessage(), ex);
        }
    }
    
    @Override
    public Integer[] getProcessStatus(String ticket) throws SmsException {
        if (ticket == null) {
            throw new SmsException("ticket is null");
        }
        if (ticket.equals("")) {
            throw new SmsException("ticket is empty");
        }
        Integer totalQuantity = 0;
        Integer notProcessedQuantity = 0;
        try {
            totalQuantity = totalQuantity + this.smsFacadeLocal.countByTicketAndSmsMessageProcessedStatus(ticket, SmsMessageProcessedStatus.SUCCESS);
            totalQuantity = totalQuantity + this.smsFacadeLocal.countByTicketAndSmsMessageProcessedStatus(ticket, SmsMessageProcessedStatus.FAIL);
        } catch (JPAException ex) {
            throw new SmsException(ex.getMessage());
        }
        try {
            notProcessedQuantity = notProcessedQuantity + this.smsFacadeLocal.countByTicketAndSmsMessageProcessedStatus(ticket, SmsMessageProcessedStatus.PROCESSING);
            notProcessedQuantity = notProcessedQuantity + this.smsFacadeLocal.countByTicketAndSmsMessageProcessedStatus(ticket, null);
        } catch (JPAException ex) {
            throw new SmsException(ex.getMessage());
        }
        if(totalQuantity == 0){
            throw new SmsException("There is no process to this ticket");
        }
        Integer processedQuantity = totalQuantity - notProcessedQuantity;
        Integer[] response = new Integer[2]; //[0] processed quantity , [1] total quantity
        response[0] = processedQuantity;
        response[1] = totalQuantity;
        return response;
    }
    
    @Override
    public boolean isAlive() {
        return true;
    }
    
    private void sendMessage(SmsMessageDTO smsMessageDTO) throws SmsException {
        if (smsMessageDTO == null) {
            throw new SmsException("smsMessageDTO is null");
        }
        if (smsMessageDTO.getPhoneNumber() == null) {
            throw new SmsException("smsMessageDTO.getPhoneNumber() is null");
        }
        if (smsMessageDTO.getPhoneNumber().equals("")) {
            throw new SmsException("smsMessageDTO.getPhoneNumber() is empty");
        }
        if (smsMessageDTO.getMessage() == null) {
            throw new SmsException("smsMessageDTO.getMessage() is null");
        }
        if (smsMessageDTO.getMessage().equals("")) {
            throw new SmsException("smsMessageDTO.getMessage() is empty");
        }
        this.jmsSmsClientSessionBeanLocal.sendJMSMessageToSendMessageQueue(smsMessageDTO, 0, 9);
    }
    
    private void queueMessage(SmsMessageDTO smsMessageDTO) throws SmsException {
        if (smsMessageDTO == null) {
            throw new SmsException("smsMessageDTO is null");
        }
        if (smsMessageDTO.getPhoneNumber() == null) {
            throw new SmsException("smsMessageDTO.getPhoneNumber() is null");
        }
        if (smsMessageDTO.getPhoneNumber().equals("")) {
            throw new SmsException("smsMessageDTO.getPhoneNumber() is empty");
        }
        if (smsMessageDTO.getMessage() == null) {
            throw new SmsException("smsMessageDTO.getMessage() is null");
        }
        if (smsMessageDTO.getMessage().equals("")) {
            throw new SmsException("smsMessageDTO.getMessage() is empty");
        }
        this.jmsSmsClientSessionBeanLocal.sendJMSMessageToQueueMessageQueue(smsMessageDTO, 0, 9);
    }

}
