/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.service;

import com.grupoconamerica.backend.delegate.SmsDelegate;
import com.grupoconamerica.backend.dto.SmsDTO;
import com.grupoconamerica.backend.dto.SmsRetrieveRequestDTO;
import com.grupoconamerica.backend.dto.SmsRetrieveResponseDTO;
import com.grupoconamerica.backend.dto.SmsSendRequestDTO;
import com.grupoconamerica.backend.dto.SmsSendResponseDTO;
import com.grupoconamerica.backend.enums.SmsProcessedStatus;
import com.grupoconamerica.backend.enums.SmsSendType;
import com.grupoconamerica.backend.enums.SmsType;
import com.grupoconamerica.backend.exception.SmsException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;

/**
 *
 * @author CarlosDaniel
 */
@WebService(serviceName = "SmsWS")
@Stateless()
public class SmsWS {

    @WebMethod(operationName = "send")
    public SmsSendResponseDTO send(@WebParam(name = "smsSendRequestDTO") SmsSendRequestDTO smsSendRequestDTO) throws SmsException {
        if (smsSendRequestDTO == null) {
            throw new SmsException("smsSendRequestDTO is null");
        }
        if (smsSendRequestDTO.getPhoneNumbers() == null) {
            throw new SmsException("smsSendRequestDTO.getPhoneNumbers() is null");
        }
        if (smsSendRequestDTO.getPhoneNumbers().isEmpty()) {
            throw new SmsException("smsSendRequestDTO.getPhoneNumbers() is empty");
        }
        if (smsSendRequestDTO.getMessage() == null) {
            throw new SmsException("smsSendRequestDTO.getMessage() is null");
        }
        if (smsSendRequestDTO.getMessage().equals("")) {
            throw new SmsException("smsSendRequestDTO.getMessage() is empty");
        }
        if (smsSendRequestDTO.getSmsSendType() == null) {
            smsSendRequestDTO.setSmsSendType(SmsSendType.SYNC);
        }
        if (smsSendRequestDTO.getSecureBatch() == null){
            smsSendRequestDTO.setSecureBatch(Boolean.TRUE);
        }
        SmsSendType smsSendType = smsSendRequestDTO.getSmsSendType();
        List<SmsDTO> smsDTOs = new ArrayList<>();
        for (String phoneNumber : smsSendRequestDTO.getPhoneNumbers()) {
            if(phoneNumber == null){
                continue;
            }
            if(phoneNumber.equals("")){
                continue;
            }
            SmsDTO smsDTO = new SmsDTO();
            smsDTO.setCreatedAt(new Date());
            smsDTO.setPhoneNumber(phoneNumber);
            smsDTO.setMessage(smsSendRequestDTO.getMessage());
            smsDTO.setSubject(smsSendRequestDTO.getSubject());
            smsDTO.setSmsType(SmsType.OUTBOUND);
            smsDTO.setSmsSendType(smsSendRequestDTO.getSmsSendType());
            smsDTOs.add(smsDTO);
        }
        String batchGeneratedId;
        if (smsSendType.equals(SmsSendType.SYNC)) {
            batchGeneratedId = new SmsDelegate().sendMessages(smsDTOs.toArray(new SmsDTO[smsDTOs.size()]), smsSendRequestDTO.getSecureBatch());
        } else {
            batchGeneratedId = new SmsDelegate().queueMessages(smsDTOs.toArray(new SmsDTO[smsDTOs.size()]), smsSendRequestDTO.getSecureBatch());
        }
        return new SmsSendResponseDTO(batchGeneratedId, smsDTOs.size(), SmsProcessedStatus.PROCESSING, SmsType.OUTBOUND, smsSendType);
    }

    @WebMethod(operationName = "findRange")
    public SmsRetrieveResponseDTO findRange(@WebParam(name = "smsRetrieveRequestDTO") SmsRetrieveRequestDTO smsRetrieveRequestDTO) throws SmsException {
        if (smsRetrieveRequestDTO == null) {
            throw new SmsException("smsRetrieveRequestDTO is null");
        }
        if (smsRetrieveRequestDTO.getSmsType() == null) {
            throw new SmsException("smsRetrieveRequestDTO.getSmsType() is null");
        }
        if (smsRetrieveRequestDTO.getInitDate() == null) {
            throw new SmsException("smsRetrieveRequestDTO.getInitDate() is null");
        }
        if (smsRetrieveRequestDTO.getEndDate() == null) {
            throw new SmsException("smsRetrieveRequestDTO.getFinalDate() is null");
        }
        if(smsRetrieveRequestDTO.getSmsType().equals(SmsType.INBOUND) && smsRetrieveRequestDTO.getQuantity() == null){
            List<SmsDTO> smsDTOs = new ArrayList<>();
            if(smsRetrieveRequestDTO.getQuantity() == null){
                smsDTOs.addAll(new SmsDelegate().findAll(smsRetrieveRequestDTO.getSmsType(), smsRetrieveRequestDTO.getInitDate(), smsRetrieveRequestDTO.getEndDate()));
            } else {
                int[] range = new int[2];
                range[0] = 0;
                range[1] = smsRetrieveRequestDTO.getQuantity();
                smsDTOs.addAll(new SmsDelegate().findRange(smsRetrieveRequestDTO.getSmsType(), smsRetrieveRequestDTO.getInitDate(), smsRetrieveRequestDTO.getEndDate(), range));
            }
            return new SmsRetrieveResponseDTO(smsDTOs, smsRetrieveRequestDTO.getSmsType());
        } else if(smsRetrieveRequestDTO.getSmsType().equals(SmsType.OUTBOUND) ) {
            List<SmsDTO> smsDTOs = new ArrayList<>();
            if(smsRetrieveRequestDTO.getQuantity() == null){
                smsDTOs.addAll(new SmsDelegate().findAll(smsRetrieveRequestDTO.getSmsType(), smsRetrieveRequestDTO.getInitDate(), smsRetrieveRequestDTO.getEndDate()));
            } else {
                int[] range = new int[2];
                range[0] = 0;
                range[1] = smsRetrieveRequestDTO.getQuantity();
                smsDTOs.addAll(new SmsDelegate().findRange(smsRetrieveRequestDTO.getSmsType(), smsRetrieveRequestDTO.getInitDate(), smsRetrieveRequestDTO.getEndDate(), range));
            }
            return new SmsRetrieveResponseDTO(smsDTOs, smsRetrieveRequestDTO.getSmsType());
        }
        throw new SmsException("Not handled case");
    }
        
    @WebMethod(operationName = "countSendProcessed")
    public Long countSendProcessed(@WebParam(name = "batchGeneratedId") String batchGeneratedId) throws SmsException {
        return new SmsDelegate().countSendProcessed(batchGeneratedId);
    }
    
    @WebMethod(operationName = "isAlive")
    public boolean isAlive() throws SmsException {
        return new SmsDelegate().isAlive();
    }

}
