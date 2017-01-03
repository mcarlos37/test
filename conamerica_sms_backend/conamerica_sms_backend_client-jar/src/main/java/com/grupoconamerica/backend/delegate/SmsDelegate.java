/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.delegate;

import com.grupoconamerica.backend.dto.SmsDTO;
import com.grupoconamerica.backend.enums.SmsType;
import com.grupoconamerica.backend.enums.SystemModule;
import com.grupoconamerica.backend.exception.SmsException;
import com.grupoconamerica.backend.exception.facade.SmsFacadeRemote;
import com.grupoconamerica.backend.locator.AbstractDelegate;
import java.util.Date;
import java.util.List;

/**
 *
 * @Carlos Daniel
 */
public class SmsDelegate extends AbstractDelegate<SmsFacadeRemote> implements SmsFacadeRemote {

    @Override
    public void sendMessage(SmsDTO smsDTO) throws  SmsException {
        this.getDelegate(JNDI_REMOTE_NAME, SystemModule.BACKEND_SMS).sendMessage(smsDTO);
    }

    @Override
    public void queueMessage(SmsDTO smsDTO) throws  SmsException {
        this.getDelegate(JNDI_REMOTE_NAME, SystemModule.BACKEND_SMS).queueMessage(smsDTO);
    }

    @Override
    public String sendMessages(SmsDTO[] smsDTOs, boolean secureBatch) throws SmsException {
        return this.getDelegate(JNDI_REMOTE_NAME, SystemModule.BACKEND_SMS).sendMessages(smsDTOs,secureBatch);
    }

    @Override
    public String queueMessages(SmsDTO[] smsDTOs, boolean secureBatch) throws SmsException {
        return this.getDelegate(JNDI_REMOTE_NAME, SystemModule.BACKEND_SMS).queueMessages(smsDTOs,secureBatch);
    }
    
    @Override
    public void create(SmsDTO smsDTO) throws SmsException {
        this.getDelegate(JNDI_REMOTE_NAME, SystemModule.BACKEND_SMS).create(smsDTO);
    }
    
    @Override
    public List<SmsDTO> findAll(SmsType smsType, Date initDate, Date endDate) throws SmsException {
        return this.getDelegate(JNDI_REMOTE_NAME, SystemModule.BACKEND_SMS).findAll(smsType, initDate, endDate);
    }
    
    @Override
    public List<SmsDTO> findRange(SmsType smsType, Date initDate, Date endDate, int[] range) throws SmsException {
        return this.getDelegate(JNDI_REMOTE_NAME, SystemModule.BACKEND_SMS).findRange(smsType, initDate, endDate, range);
    }
    
    @Override
    public Long countSendProcessed(String batchGeneratedId) throws SmsException {
        return this.getDelegate(JNDI_REMOTE_NAME, SystemModule.BACKEND_SMS).countSendProcessed(batchGeneratedId);
    }
    
    @Override
    public boolean isAlive() {
        return this.getDelegate(JNDI_REMOTE_NAME, SystemModule.BACKEND_SMS).isAlive();
    }    

}
