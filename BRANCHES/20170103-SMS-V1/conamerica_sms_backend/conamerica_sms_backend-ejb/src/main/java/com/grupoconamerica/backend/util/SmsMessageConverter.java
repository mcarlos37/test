/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.util;

import com.grupoconamerica.backend.dto.SmsMessageDTO;
import com.grupoconamerica.backend.entity.SmsMessage;
import com.grupoconamerica.backend.exception.SmsException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CarlosDaniel
 */
public class SmsMessageConverter {
    
    public static SmsMessageDTO smsMessageToSmsMessageDTO(SmsMessage smsMessage) throws SmsException {
        if(smsMessage == null){
            throw new SmsException("smsMessage is null");
        }
        SmsMessageDTO smsMessageDTO = new SmsMessageDTO();
        smsMessageDTO.setId(smsMessage.getId());
        smsMessageDTO.setCreatedAt(smsMessage.getCreatedAt());
        smsMessageDTO.setProcessedAt(smsMessage.getProcessedAt());
        smsMessageDTO.setGatewayId(smsMessage.getGatewayId());
        smsMessageDTO.setPhoneNumber(smsMessage.getPhoneNumber());
        smsMessageDTO.setMessage(smsMessage.getMessage());
        smsMessageDTO.setSubject(smsMessage.getSubject());
        smsMessageDTO.setSmsMessageType(smsMessage.getSmsMessageType());
        smsMessageDTO.setSmsMessageSendType(smsMessage.getSmsMessageSendType());
        smsMessageDTO.setSmsMessageProcessedStatus(smsMessage.getSmsMessageProcessedStatus());
        smsMessageDTO.setErrorMessage(smsMessage.getErrorMessage());
        smsMessageDTO.setTicket(smsMessage.getTicket());
        return smsMessageDTO;
    }
    
    public static List<SmsMessageDTO> smsMessagesToSmsMessageDTOs(List<SmsMessage> smsMessages) throws SmsException {
        if(smsMessages == null){
            throw new SmsException("smsMessages is null");
        }
        List<SmsMessageDTO> smsMessageDTOs = new ArrayList<>();
        for(SmsMessage smsMessage : smsMessages){
            smsMessageDTOs.add(smsMessageToSmsMessageDTO(smsMessage));
        }
        return smsMessageDTOs;
    }
        
}
