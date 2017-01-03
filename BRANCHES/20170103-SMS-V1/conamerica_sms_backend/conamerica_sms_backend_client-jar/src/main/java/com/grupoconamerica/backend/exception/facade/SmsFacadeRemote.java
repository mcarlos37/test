/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.exception.facade;

import com.grupoconamerica.backend.dto.SmsDTO;
import com.grupoconamerica.backend.enums.SmsType;
import com.grupoconamerica.backend.exception.SmsException;
import com.grupoconamerica.backend.locator.ServiceVerifier;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author CarlosDaniel
 */
@Remote
public interface SmsFacadeRemote extends ServiceVerifier {
    
    public final String JNDI_REMOTE_NAME = "ejb/smsFacadeRemote";
    
    public void sendMessage(SmsDTO smsDTO) throws SmsException;
    
    public void queueMessage(SmsDTO smsDTO) throws SmsException;
    
    public String sendMessages(SmsDTO[] smsDTOs, boolean secureBatch) throws SmsException;
    
    public String queueMessages(SmsDTO[] smsDTOs, boolean secureBatch) throws SmsException;
    
    public void create(SmsDTO smsDTO) throws SmsException;
    
    public List<SmsDTO> findAll(SmsType smsType, Date initDate, Date endDate) throws SmsException;
    
    public List<SmsDTO> findRange(SmsType smsType, Date initDate, Date endDate, int[] range) throws SmsException;
        
    public Long countSendProcessed(String batchGeneratedId) throws SmsException;
    
}
