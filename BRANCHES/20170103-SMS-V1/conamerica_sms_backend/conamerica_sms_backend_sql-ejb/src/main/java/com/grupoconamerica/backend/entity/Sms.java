/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.entity;

import com.grupoconamerica.backend.dto.SmsDTO;
import com.grupoconamerica.backend.enums.SmsProcessedStatus;
import com.grupoconamerica.backend.enums.SmsSendType;
import com.grupoconamerica.backend.enums.SmsType;
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
import javax.persistence.Version;

/**
 *
 * @author CarlosDaniel
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Sms.findAllByType", query = "SELECT s FROM Sms s WHERE s.smsType = :smsType AND s.processedAt BETWEEN :initDate AND :endDate"),
    @NamedQuery(name = "Sms.countByBatchGeneratedIdAndSmsProcessedStatus", query = "SELECT count(s.id) FROM Sms s WHERE s.smsProcessedStatus = :smsProcessedStatus AND s.batchGeneratedId = :batchGeneratedId")
})
public class Sms extends SmsDTO {

    @Override
    public void setSubject(String subject) {
        super.setSubject(subject); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getSubject() {
        return super.getSubject(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSmsType(SmsType smsType) {
        super.setSmsType(smsType); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Enumerated(EnumType.STRING)
    public SmsType getSmsType() {
        return super.getSmsType(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSmsProcessedStatus(SmsProcessedStatus smsProcessedStatus) {
        super.setSmsProcessedStatus(smsProcessedStatus); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Enumerated(EnumType.STRING)
    public SmsProcessedStatus getSmsProcessedStatus() {
        return super.getSmsProcessedStatus(); //To change body of generated methods, choose Tools | Templates.
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
    public void setVersion(Integer version) {
        super.setVersion(version); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Version
    public Integer getVersion() {
        return super.getVersion(); //To change body of generated methods, choose Tools | Templates.
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
    public void setSmsSendType(SmsSendType smsSendType) {
        super.setSmsSendType(smsSendType); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Enumerated(EnumType.STRING)
    public SmsSendType getSmsSendType() {
        return super.getSmsSendType(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setBatchGeneratedId(String batchGeneratedId) {
        super.setBatchGeneratedId(batchGeneratedId); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getBatchGeneratedId() {
        return super.getBatchGeneratedId(); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Sms() {
    }
    
    public Sms(SmsDTO smsDTO) throws SmsException {
        if (smsDTO == null) {
            throw new SmsException("smsDTO is null");
        }
        this.id = smsDTO.getId();
        this.version = smsDTO.getVersion();
        if(this.id == null){
            this.createdAt = new Date();
        } else {
            this.createdAt = smsDTO.getCreatedAt();
        }
        this.processedAt = smsDTO.getProcessedAt();
        this.gatewayId = smsDTO.getGatewayId();
        this.phoneNumber = smsDTO.getPhoneNumber();
        this.message = smsDTO.getMessage();
        this.smsProcessedStatus = smsDTO.getSmsProcessedStatus();
        this.smsType = smsDTO.getSmsType();
        this.subject = smsDTO.getSubject();
        this.errorMessage = smsDTO.getErrorMessage();
        this.smsSendType = smsDTO.getSmsSendType();
        this.batchGeneratedId = smsDTO.getBatchGeneratedId();
    }
    
}
