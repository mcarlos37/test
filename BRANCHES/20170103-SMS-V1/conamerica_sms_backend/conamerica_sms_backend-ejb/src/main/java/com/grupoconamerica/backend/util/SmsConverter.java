/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.util;

import com.grupoconamerica.backend.dto.SmsDTO;
import com.grupoconamerica.backend.entity.Sms;
import com.grupoconamerica.backend.exception.SmsException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CarlosDaniel
 */
public class SmsConverter {
    
    public static SmsDTO smsToSmsDTO(Sms sms) throws SmsException {
        if(sms == null){
            throw new SmsException("sms is null");
        }
        SmsDTO smsDTO = new SmsDTO();
        smsDTO.setId(sms.getId());
        smsDTO.setVersion(sms.getVersion());
        smsDTO.setCreatedAt(sms.getCreatedAt());
        smsDTO.setProcessedAt(sms.getProcessedAt());
        smsDTO.setGatewayId(sms.getGatewayId());
        smsDTO.setPhoneNumber(sms.getPhoneNumber());
        smsDTO.setMessage(sms.getMessage());
        smsDTO.setSubject(sms.getSubject());
        smsDTO.setSmsType(sms.getSmsType());
        smsDTO.setSmsSendType(sms.getSmsSendType());
        smsDTO.setSmsProcessedStatus(sms.getSmsProcessedStatus());
        smsDTO.setErrorMessage(sms.getErrorMessage());
        smsDTO.setBatchGeneratedId(sms.getBatchGeneratedId());
        return smsDTO;
    }
    
    public static List<SmsDTO> smssToSmsDTOs(List<Sms> smss) throws SmsException {
        if(smss == null){
            throw new SmsException("smss is null");
        }
        List<SmsDTO> smsDTOs = new ArrayList<>();
        for(Sms sms : smss){
            smsDTOs.add(smsToSmsDTO(sms));
        }
        return smsDTOs;
    }
        
}
