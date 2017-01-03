/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.dto;

import com.grupoconamerica.backend.enums.SmsProcessedStatus;
import com.grupoconamerica.backend.enums.SmsSendType;
import com.grupoconamerica.backend.enums.SmsType;
import java.io.Serializable;

/**
 *
 * @author CarlosDaniel
 */
public class SmsSendResponseDTO implements Serializable {
    
    private String batchGeneratedId;
    
    private Integer batchSize;
    
    private SmsProcessedStatus smsProcessedStatus;
    
    private SmsSendType smsSendType;
    
    private SmsType smsType;

    public String getBatchGeneratedId() {
        return batchGeneratedId;
    }

    public void setBatchGeneratedId(String batchGeneratedId) {
        this.batchGeneratedId = batchGeneratedId;
    }

    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }

    public SmsProcessedStatus getSmsProcessedStatus() {
        return smsProcessedStatus;
    }

    public void setSmsProcessedStatus(SmsProcessedStatus smsProcessedStatus) {
        this.smsProcessedStatus = smsProcessedStatus;
    }

    public SmsSendResponseDTO() {
    }

    public SmsSendResponseDTO(String batchGeneratedId, Integer batchSize, SmsProcessedStatus smsProcessedStatus, SmsType smsType, SmsSendType smsSendType) {
        this.batchGeneratedId = batchGeneratedId;
        this.batchSize = batchSize;
        this.smsProcessedStatus = smsProcessedStatus;
        this.smsType = smsType;
        this.smsSendType = smsSendType;
    }
    
}
