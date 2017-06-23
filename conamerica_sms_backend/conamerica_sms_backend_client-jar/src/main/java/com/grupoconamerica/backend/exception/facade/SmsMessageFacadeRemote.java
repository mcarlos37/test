/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.exception.facade;

import com.grupoconamerica.backend.dto.SmsMessageDTO;
import com.grupoconamerica.backend.enums.SmsMessageType;
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
public interface SmsMessageFacadeRemote extends ServiceVerifier {
    
    public final String JNDI_REMOTE_NAME = "ejb/smsMessageFacadeRemote";
        
    public String sendMessages(SmsMessageDTO[] smsDTOs, boolean secureBatch) throws SmsException;
    
    public String queueMessages(SmsMessageDTO[] smsDTOs, boolean secureBatch) throws SmsException;
    
    public void create(SmsMessageDTO smsDTO) throws SmsException;
    
    public List<SmsMessageDTO> findAll(SmsMessageType smsType, Date initDate, Date endDate) throws SmsException;
    
    public List<SmsMessageDTO> findAllBySmsMessageType(SmsMessageType smsType, Date initDate, Date endDate, int[] range) throws SmsException;
        
    public Integer[] getProcessStatus(String ticket) throws SmsException;
    
}
