/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.facade;

import com.grupoconamerica.backend.dto.SmsDTO;
import com.grupoconamerica.backend.exception.SmsException;
import java.util.concurrent.Future;
import javax.ejb.Local;

/**
 *
 * @author CarlosDaniel
 */
@Local
public interface SmsAsyncFacadeLocal {
    
    Future<?> sendMessage(SmsDTO smsDTO) throws SmsException;

    Future<?> queueMessage(SmsDTO smsDTO) throws SmsException;
    
    Future<?> sendMessages(SmsDTO[] smsDTOs) throws SmsException;

    Future<?> queueMessages(SmsDTO[] smsDTOs)throws SmsException;
        
}
