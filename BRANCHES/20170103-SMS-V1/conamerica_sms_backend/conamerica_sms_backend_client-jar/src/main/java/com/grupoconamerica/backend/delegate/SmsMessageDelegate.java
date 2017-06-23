/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.delegate;

import com.grupoconamerica.backend.dto.SmsMessageDTO;
import com.grupoconamerica.backend.enums.SmsMessageType;
import com.grupoconamerica.backend.enums.SystemModule;
import com.grupoconamerica.backend.exception.SmsException;
import com.grupoconamerica.backend.exception.facade.SmsMessageFacadeRemote;
import com.grupoconamerica.backend.locator.AbstractDelegate;
import java.util.Date;
import java.util.List;

/**
 *
 * @Carlos Daniel
 */
public class SmsMessageDelegate extends AbstractDelegate<SmsMessageFacadeRemote> implements SmsMessageFacadeRemote {

    @Override
    public String sendMessages(SmsMessageDTO[] smsDTOs, boolean secureBatch) throws SmsException {
        return this.getDelegate(JNDI_REMOTE_NAME, SystemModule.BACKEND_SMS).sendMessages(smsDTOs,secureBatch);
    }

    @Override
    public String queueMessages(SmsMessageDTO[] smsDTOs, boolean secureBatch) throws SmsException {
        return this.getDelegate(JNDI_REMOTE_NAME, SystemModule.BACKEND_SMS).queueMessages(smsDTOs,secureBatch);
    }
    
    @Override
    public void create(SmsMessageDTO smsDTO) throws SmsException {
        this.getDelegate(JNDI_REMOTE_NAME, SystemModule.BACKEND_SMS).create(smsDTO);
    }
    
    @Override
    public List<SmsMessageDTO> findAll(SmsMessageType smsType, Date initDate, Date endDate) throws SmsException {
        return this.getDelegate(JNDI_REMOTE_NAME, SystemModule.BACKEND_SMS).findAll(smsType, initDate, endDate);
    }
    
    @Override
    public List<SmsMessageDTO> findAllBySmsMessageType(SmsMessageType smsType, Date initDate, Date endDate, int[] range) throws SmsException {
        return this.getDelegate(JNDI_REMOTE_NAME, SystemModule.BACKEND_SMS).findAllBySmsMessageType(smsType, initDate, endDate, range);
    }
    
    @Override
    public Integer[] getProcessStatus(String ticket) throws SmsException {
        return this.getDelegate(JNDI_REMOTE_NAME, SystemModule.BACKEND_SMS).getProcessStatus(ticket);
    }
        
    @Override
    public boolean isAlive() {
        return this.getDelegate(JNDI_REMOTE_NAME, SystemModule.BACKEND_SMS).isAlive();
    }    

}
