/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.entity;

import com.grupoconamerica.backend.dto.SmsMessageDTO;
import com.grupoconamerica.backend.enums.SmsMessageProcessedStatus;
import com.grupoconamerica.backend.enums.SmsMessageSendType;
import com.grupoconamerica.backend.enums.SmsMessageType;
import com.grupoconamerica.backend.exception.SmsException;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;

/**
 *
 * @author CarlosDaniel
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "SmsMessage.findAllBySmsMessageType", query = "SELECT s FROM SmsMessage s WHERE s.smsMessageType = :smsMessageType AND s.processedAt BETWEEN :initDate AND :endDate"),
    @NamedQuery(name = "SmsMessage.countByTicketAndSmsMessageProcessedStatus", query = "SELECT count(s.id) FROM SmsMessage s WHERE s.smsMessageProcessedStatus = :smsMessageProcessedStatus AND s.ticket = :ticket")
})
public class SmsMessage extends SmsMessageDTO {

    @Override
    public void setSubject(String subject) {
        super.setSubject(subject); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getSubject() {
        return super.getSubject(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSmsMessageType(SmsMessageType smsMessageType) {
        super.setSmsMessageType(smsMessageType); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Enumerated(EnumType.STRING)
    public SmsMessageType getSmsMessageType() {
        return super.getSmsMessageType(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSmsMessageProcessedStatus(SmsMessageProcessedStatus smsMessageProcessedStatus) {
        super.setSmsMessageProcessedStatus(smsMessageProcessedStatus); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Enumerated(EnumType.STRING)
    public SmsMessageProcessedStatus getSmsMessageProcessedStatus() {
        return super.getSmsMessageProcessedStatus(); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void setProcessedAt(Date processedAt) {
        super.setProcessedAt(processedAt); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getProcessedAt() {
        return super.getProcessedAt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCreatedAt(Date createdAt) {
        super.setCreatedAt(createdAt); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getCreatedAt() {
        return super.getCreatedAt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setGatewayId(String gatewayId) {
        super.setGatewayId(gatewayId); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getGatewayId() {
        return super.getGatewayId(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setMessage(String message) {
        super.setMessage(message); //To change body of generated methods, choose Tools | Templates.
    }

    @Column(nullable = false)
    @Override
    public String getMessage() {
        return super.getMessage(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        super.setPhoneNumber(phoneNumber); //To change body of generated methods, choose Tools | Templates.
    }

    @Column(nullable = false)
    @Override
    public String getPhoneNumber() {
        return super.getPhoneNumber(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setId(Long id) {
        super.setId(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return super.getId(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        super.setErrorMessage(errorMessage); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getErrorMessage() {
        return super.getErrorMessage(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSmsMessageSendType(SmsMessageSendType smsMessageSendType) {
        super.setSmsMessageSendType(smsMessageSendType); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Enumerated(EnumType.STRING)
    public SmsMessageSendType getSmsMessageSendType() {
        return super.getSmsMessageSendType(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTicket(String ticket) {
        super.setTicket(ticket); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTicket() {
        return super.getTicket(); //To change body of generated methods, choose Tools | Templates.
    }
    
    public SmsMessage() {
    }
    
    public SmsMessage(SmsMessageDTO smsMessageDTO) throws SmsException {
        if (smsMessageDTO == null) {
            throw new SmsException("smsMessageDTO is null");
        }
        this.id = smsMessageDTO.getId();
        if(this.id == null){
            this.createdAt = new Date();
        } else {
            this.createdAt = smsMessageDTO.getCreatedAt();
        }
        this.processedAt = smsMessageDTO.getProcessedAt();
        this.gatewayId = smsMessageDTO.getGatewayId();
        this.phoneNumber = smsMessageDTO.getPhoneNumber();
        this.message = smsMessageDTO.getMessage();
        this.smsMessageProcessedStatus = smsMessageDTO.getSmsMessageProcessedStatus();
        this.smsMessageType = smsMessageDTO.getSmsMessageType();
        this.subject = smsMessageDTO.getSubject();
        this.errorMessage = smsMessageDTO.getErrorMessage();
        this.smsMessageSendType = smsMessageDTO.getSmsMessageSendType();
        this.ticket = smsMessageDTO.getTicket();
    }
    
}
