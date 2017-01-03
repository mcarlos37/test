/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.facade;

import com.grupoconamerica.backend.dto.SmsDTO;
import com.grupoconamerica.backend.entity.Sms;
import com.grupoconamerica.backend.enums.SmsProcessedStatus;
import com.grupoconamerica.backend.enums.SmsType;
import com.grupoconamerica.backend.exception.JPAException;
import com.grupoconamerica.backend.exception.SmsException;
import com.grupoconamerica.backend.exception.facade.SmsFacadeRemote;
import com.grupoconamerica.backend.jms.JMSSmsClientSessionBeanLocal;
import com.grupoconamerica.backend.util.SmsConverter;
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
@Stateless(name = "SmsFacade", mappedName = SmsFacadeRemote.JNDI_REMOTE_NAME)
@Remote(SmsFacadeRemote.class)
@Local(SmsFacadeLocal.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class SmsFacade implements SmsFacadeRemote, SmsFacadeLocal {

    @Inject
    private JMSSmsClientSessionBeanLocal jmsSmsClientSessionBeanLocal;
    
    @Inject
    private com.grupoconamerica.backend.entity.facade.SmsFacadeLocal smsFacadeLocal;
    
    @Override
    public void sendMessage(SmsDTO smsDTO) throws SmsException {
        if (smsDTO == null) {
            throw new SmsException("smsDTO is null");
        }
        if (smsDTO.getPhoneNumber() == null) {
            throw new SmsException("smsDTO.getPhoneNumber() is null");
        }
        if (smsDTO.getPhoneNumber().equals("")) {
            throw new SmsException("smsDTO.getPhoneNumber() is empty");
        }
        if (smsDTO.getMessage() == null) {
            throw new SmsException("smsDTO.getMessage() is null");
        }
        if (smsDTO.getMessage().equals("")) {
            throw new SmsException("smsDTO.getMessage() is empty");
        }
        this.jmsSmsClientSessionBeanLocal.sendJMSMessageToSendMessageQueue(smsDTO, 0, 9);
    }

    @Override
    public void queueMessage(SmsDTO smsDTO) throws SmsException {
        if (smsDTO == null) {
            throw new SmsException("smsDTO is null");
        }
        if (smsDTO.getPhoneNumber() == null) {
            throw new SmsException("smsDTO.getPhoneNumber() is null");
        }
        if (smsDTO.getPhoneNumber().equals("")) {
            throw new SmsException("smsDTO.getPhoneNumber() is empty");
        }
        if (smsDTO.getMessage() == null) {
            throw new SmsException("smsDTO.getMessage() is null");
        }
        if (smsDTO.getMessage().equals("")) {
            throw new SmsException("smsDTO.getMessage() is empty");
        }
        this.jmsSmsClientSessionBeanLocal.sendJMSMessageToQueueMessageQueue(smsDTO, 0, 9);
    }

    @Override
    public String sendMessages(SmsDTO[] smsDTOs, boolean secureBatch) throws SmsException {
        if (smsDTOs == null) {
            throw new SmsException("smsDTOs is null");
        }
        if (smsDTOs.length == 0) {
            throw new SmsException("smsDTOs is empty");
        }
        StringBuilder batchGeneratedId = new StringBuilder();
        batchGeneratedId.append(UUID.randomUUID().toString());
        batchGeneratedId.append("--");
        batchGeneratedId.append(smsDTOs.length);
        for(SmsDTO smsDTO : smsDTOs){
            smsDTO.setBatchGeneratedId(batchGeneratedId.toString());
        }
        if (secureBatch) {
            for (SmsDTO smsDTO : smsDTOs) {
                this.sendMessage(smsDTO);
            }
        } else {
            this.jmsSmsClientSessionBeanLocal.sendJMSMessageToSendMessageQueue(smsDTOs, 0, 9);
        }
        return batchGeneratedId.toString();
    }

    @Override
    public String queueMessages(SmsDTO[] smsDTOs, boolean secureBatch) throws SmsException {
        if (smsDTOs == null) {
            throw new SmsException("smsDTOs is null");
        }
        if (smsDTOs.length == 0) {
            throw new SmsException("smsDTOs is empty");
        }
        StringBuilder batchGeneratedId = new StringBuilder();
        batchGeneratedId.append(UUID.randomUUID().toString());
        batchGeneratedId.append("--");
        batchGeneratedId.append(smsDTOs.length);
        for(SmsDTO smsDTO : smsDTOs){
            smsDTO.setBatchGeneratedId(batchGeneratedId.toString());
        }
        if (secureBatch) {
            for (SmsDTO smsDTO : smsDTOs) {
                this.queueMessage(smsDTO);
            }
        } else {
            this.jmsSmsClientSessionBeanLocal.sendJMSMessageToQueueMessageQueue(smsDTOs, 0, 9);
        }
        return batchGeneratedId.toString();
    }
    
    @Override
    public void create(SmsDTO smsDTO) throws SmsException {
        if(smsDTO == null){
            throw new SmsException("smsDTO is null");
        }
        try {
            this.smsFacadeLocal.create(new Sms(smsDTO));
        } catch (JPAException ex) {
            Logger.getLogger(SmsFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new SmsException(ex.getMessage(),ex);
        }
    }
    
    @Override
    public List<SmsDTO> findAll(SmsType smsType, Date initDate, Date endDate) throws SmsException {
        try {
            return SmsConverter.smssToSmsDTOs(this.smsFacadeLocal.findAll(smsType, initDate, endDate));
        } catch (JPAException ex) {
            Logger.getLogger(SmsFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new SmsException(ex.getMessage(), ex);
        }
    }
    
    @Override
    public List<SmsDTO> findRange(SmsType smsType, Date initDate, Date endDate, int[] range) throws SmsException {
        try {
            return SmsConverter.smssToSmsDTOs(this.smsFacadeLocal.findRange(smsType, initDate, endDate, range));
        } catch (JPAException ex) {
            Logger.getLogger(SmsFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new SmsException(ex.getMessage(), ex);
        }
    }

    @Override
    public Long countSendProcessed(String batchGeneratedId) throws SmsException {
        try {
            Long count = this.smsFacadeLocal.countByBatchGeneratedIdAndSmsProcessedStatus(batchGeneratedId, SmsProcessedStatus.SUCCESS);
            count = count + this.smsFacadeLocal.countByBatchGeneratedIdAndSmsProcessedStatus(batchGeneratedId, SmsProcessedStatus.FAIL);
            return count;
        } catch (JPAException ex) {
            Logger.getLogger(SmsFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new SmsException(ex.getMessage(), ex);
        }
    }

    @Override
    public boolean isAlive() {
        return true;
    }

}
