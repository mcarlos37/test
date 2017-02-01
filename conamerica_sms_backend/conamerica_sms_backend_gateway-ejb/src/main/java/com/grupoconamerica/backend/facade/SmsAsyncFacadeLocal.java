/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.facade;

import com.grupoconamerica.backend.dto.SmsMessageDTO;
import com.grupoconamerica.backend.exception.SmsException;
import java.util.concurrent.Future;
import javax.ejb.Local;

/**
 *
 * @author CarlosDaniel
 */
@Local
public interface SmsAsyncFacadeLocal {
    
    Future<?> sendMessage(SmsMessageDTO smsMessageDTO) throws SmsException;

    Future<?> queueMessage(SmsMessageDTO smsMessageDTO) throws SmsException;
    
    Future<?> sendMessages(SmsMessageDTO[] smsMessageDTOs) throws SmsException;

    Future<?> queueMessages(SmsMessageDTO[] smsMessageDTOs)throws SmsException;
        
}
