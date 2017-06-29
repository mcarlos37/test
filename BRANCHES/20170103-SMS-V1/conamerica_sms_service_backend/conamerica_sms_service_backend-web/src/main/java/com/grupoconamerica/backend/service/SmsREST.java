/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.service;

import com.grupoconamerica.backend.delegate.SmsMessageDelegate;
import com.grupoconamerica.backend.dto.SmsMessageDTO;
import com.grupoconamerica.backend.dto.SmsFindRangeRequestDTO;
import com.grupoconamerica.backend.dto.SmsFindRangeResponseDTO;
import com.grupoconamerica.backend.dto.SmsProcessStatusRequestDTO;
import com.grupoconamerica.backend.dto.SmsProcessStatusResponseDTO;
import com.grupoconamerica.backend.dto.SmsSendMessageResponseDTO;
import com.grupoconamerica.backend.dto.SmsSendMessageRequestDTO;
import com.grupoconamerica.backend.enums.SmsMessageSendType;
import com.grupoconamerica.backend.enums.SmsMessageType;
import com.grupoconamerica.backend.exception.SmsException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author CarlosDaniel
 */
@Stateless
@Path("/SmsREST")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class SmsREST {

    @POST
    @Path("/sendMessage")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SmsSendMessageResponseDTO sendMessage(SmsSendMessageRequestDTO smsSendMessageRequestDTO) throws SmsException {
        if (smsSendMessageRequestDTO == null) {
            throw new SmsException("smsSendMessageRequestDTO is null");
        }
        if (smsSendMessageRequestDTO.getPhoneNumbers() == null) {
            throw new SmsException("smsSendMessageRequestDTO.getPhoneNumbers() is null");
        }
        if (smsSendMessageRequestDTO.getPhoneNumbers().length == 0) {
            throw new SmsException("smsSendMessageRequestDTO.getPhoneNumbers() is empty");
        }
        if (smsSendMessageRequestDTO.getMessage() == null) {
            throw new SmsException("smsSendMessageRequestDTO.getMessage() is null");
        }
        if (smsSendMessageRequestDTO.getMessage().equals("")) {
            throw new SmsException("smsSendMessageRequestDTO.getMessage() is empty");
        }
        if (smsSendMessageRequestDTO.getSmsMessageSendType() == null) {
            smsSendMessageRequestDTO.setSmsMessageSendType(SmsMessageSendType.SYNC);
        }
        if (smsSendMessageRequestDTO.getSecureBatch() == null){
            smsSendMessageRequestDTO.setSecureBatch(Boolean.TRUE);
        }
        SmsMessageSendType smsSendType = smsSendMessageRequestDTO.getSmsMessageSendType();
        List<SmsMessageDTO> smsDTOs = new ArrayList<>();
        for (String phoneNumber : smsSendMessageRequestDTO.getPhoneNumbers()) {
            if(phoneNumber == null){
                continue;
            }
            if(phoneNumber.equals("")){
                continue;
            }
            SmsMessageDTO smsDTO = new SmsMessageDTO();
            smsDTO.setCreatedAt(new Date());
            smsDTO.setPhoneNumber(phoneNumber);
            smsDTO.setMessage(smsSendMessageRequestDTO.getMessage());
            smsDTO.setSubject(smsSendMessageRequestDTO.getSubject());
            smsDTO.setSmsMessageType(SmsMessageType.OUTBOUND);
            smsDTO.setSmsMessageSendType(smsSendMessageRequestDTO.getSmsMessageSendType());
            smsDTOs.add(smsDTO);
        }
        String ticket;
        if (smsSendType.equals(SmsMessageSendType.SYNC)) {
            ticket = new SmsMessageDelegate().sendMessages(smsDTOs.toArray(new SmsMessageDTO[smsDTOs.size()]), smsSendMessageRequestDTO.getSecureBatch());
        } else {
            ticket = new SmsMessageDelegate().queueMessages(smsDTOs.toArray(new SmsMessageDTO[smsDTOs.size()]), smsSendMessageRequestDTO.getSecureBatch());
        }
        return new SmsSendMessageResponseDTO(ticket);
    }
    
    @POST
    @Path("/findRange")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SmsFindRangeResponseDTO findRange(SmsFindRangeRequestDTO smsFindRangeRequestDTO) throws SmsException {
        if (smsFindRangeRequestDTO == null) {
            throw new SmsException("smsFindRangeRequestDTO is null");
        }
        if (smsFindRangeRequestDTO.getSmsMessageType() == null) {
            throw new SmsException("smsFindRangeRequestDTO.getSmsMessageType() is null");
        }
        if (smsFindRangeRequestDTO.getInitDate() == null) {
            throw new SmsException("smsFindRangeRequestDTO.getInitDate() is null");
        }
        System.out.println("*///////////////////////////////////////////////////");
        System.out.println("*///////////////////////////////////////////////////");
        System.out.println(smsFindRangeRequestDTO.getInitDate().toString());
        
        if (smsFindRangeRequestDTO.getEndDate() == null) {
            throw new SmsException("smsFindRangeRequestDTO.getFinalDate() is null");
        }
        if(smsFindRangeRequestDTO.getSmsMessageType().equals(SmsMessageType.INBOUND)){
            List<SmsMessageDTO> smsDTOs = new ArrayList<>();
            if(smsFindRangeRequestDTO.getQuantity() == null){
                smsDTOs.addAll(new SmsMessageDelegate().findAll(smsFindRangeRequestDTO.getSmsMessageType(), smsFindRangeRequestDTO.getInitDate(), smsFindRangeRequestDTO.getEndDate()));
            } else {
                int[] range = new int[2];
                range[0] = 0;
                range[1] = smsFindRangeRequestDTO.getQuantity();
                smsDTOs.addAll(new SmsMessageDelegate().findAllBySmsMessageType(smsFindRangeRequestDTO.getSmsMessageType(), smsFindRangeRequestDTO.getInitDate(), smsFindRangeRequestDTO.getEndDate(), range));
            }
            return new SmsFindRangeResponseDTO(smsDTOs, smsFindRangeRequestDTO.getSmsMessageType());
        } else if(smsFindRangeRequestDTO.getSmsMessageType().equals(SmsMessageType.OUTBOUND) ) {
            List<SmsMessageDTO> smsDTOs = new ArrayList<>();
            if(smsFindRangeRequestDTO.getQuantity() == null){
                smsDTOs.addAll(new SmsMessageDelegate().findAll(smsFindRangeRequestDTO.getSmsMessageType(), smsFindRangeRequestDTO.getInitDate(), smsFindRangeRequestDTO.getEndDate()));
            } else {
                int[] range = new int[2];
                range[0] = 0;
                range[1] = smsFindRangeRequestDTO.getQuantity();
                smsDTOs.addAll(new SmsMessageDelegate().findAllBySmsMessageType(smsFindRangeRequestDTO.getSmsMessageType(), smsFindRangeRequestDTO.getInitDate(), smsFindRangeRequestDTO.getEndDate(), range));
            }
            return new SmsFindRangeResponseDTO(smsDTOs, smsFindRangeRequestDTO.getSmsMessageType());
        }
        throw new SmsException("Not handled case");
    }
        
    @POST
    @Path("/getProcessStatus")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SmsProcessStatusResponseDTO getProcessStatus(SmsProcessStatusRequestDTO smsProcessStatusRequestDTO) throws SmsException {
        if (smsProcessStatusRequestDTO == null) {
            throw new SmsException("smsProcessStatusRequestDTO is null");
        }
        if (smsProcessStatusRequestDTO.getTicket() == null) {
            throw new SmsException("smsProcessStatusRequestDTO.getTicket() is null");
        }
        if (smsProcessStatusRequestDTO.getTicket().equals("")) {
            throw new SmsException("smsProcessStatusRequestDTO.getTicket() is empty");
        }
        Integer[] processStatus = new SmsMessageDelegate().getProcessStatus(smsProcessStatusRequestDTO.getTicket());
        return new SmsProcessStatusResponseDTO(processStatus[0], processStatus[1]);
    }
        
}
