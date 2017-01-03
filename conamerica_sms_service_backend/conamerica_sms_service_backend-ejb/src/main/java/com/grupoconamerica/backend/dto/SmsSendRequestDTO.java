/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.dto;

import com.grupoconamerica.backend.enums.SmsSendType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author CarlosDaniel
 */
public class SmsSendRequestDTO implements Serializable {
    
    private List<String> phoneNumbers;
    
    private String message;
    
    private String subject;
    
    private SmsSendType smsSendType;
    
    private Date sendAt;
    
    private Boolean secureBatch;

    public List<String> getPhoneNumbers() {
        if(this.phoneNumbers == null){
            this.phoneNumbers = new ArrayList<>();
        }
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
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

    public SmsSendType getSmsSendType() {
        return smsSendType;
    }

    public void setSmsSendType(SmsSendType smsSendType) {
        this.smsSendType = smsSendType;
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
    
}
