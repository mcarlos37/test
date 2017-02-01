/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.dto;

import com.grupoconamerica.backend.enums.SmsMessageSendType;
import com.grupoconamerica.backend.util.DateAdapter;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author CarlosDaniel
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SmsSendMessageRequestDTO implements Serializable {
    
    private String[] phoneNumbers;
    
    private String message;
    
    private String subject;
    
    private SmsMessageSendType smsMessageSendType;
    
    @XmlElement(name = "sendAt", required = true) 
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date sendAt;
    
    private Boolean secureBatch;

    public String[] getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String[] phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public SmsMessageSendType getSmsMessageSendType() {
        return smsMessageSendType;
    }

    public void setSmsMessageSendType(SmsMessageSendType smsMessageSendType) {
        this.smsMessageSendType = smsMessageSendType;
    }

    public Date getSendAt() {
        return sendAt;
    }

    public void setSendAt(Date sendAt) {
        this.sendAt = sendAt;
    }

    public Boolean getSecureBatch() {
        return secureBatch;
    }

    public void setSecureBatch(Boolean secureBatch) {
        this.secureBatch = secureBatch;
    }
    
    public SmsSendMessageRequestDTO() {
    }

    public SmsSendMessageRequestDTO(String[] phoneNumbers, String message, String subject, SmsMessageSendType smsMessageSendType, Date sendAt, Boolean secureBatch) {
        this.phoneNumbers = phoneNumbers;
        this.message = message;
        this.subject = subject;
        this.smsMessageSendType = smsMessageSendType;
        this.sendAt = sendAt;
        this.secureBatch = secureBatch;
    }
    
}
