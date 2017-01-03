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
import java.util.Date;

/**
 *
 * @author CarlosDaniel
 */
public class SmsDTO implements Serializable {

    protected Long id;

    protected Integer version;

    protected String phoneNumber;

    protected String message;

    protected String subject;

    protected String gatewayId;

    protected Date createdAt;

    protected Date processedAt;

    protected SmsProcessedStatus smsProcessedStatus;

    protected SmsType smsType;

    protected String errorMessage;

    protected SmsSendType smsSendType;

    protected String batchGeneratedId;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(Date processedAt) {
        this.processedAt = processedAt;
    }

    public SmsProcessedStatus getSmsProcessedStatus() {
        return smsProcessedStatus;
    }

    public void setSmsProcessedStatus(SmsProcessedStatus smsProcessedStatus) {
        this.smsProcessedStatus = smsProcessedStatus;
    }

    public SmsType getSmsType() {
        return smsType;
    }

    public void setSmsType(SmsType smsType) {
        this.smsType = smsType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public SmsSendType getSmsSendType() {
        return smsSendType;
    }

    public void setSmsSendType(SmsSendType smsSendType) {
        this.smsSendType = smsSendType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getBatchGeneratedId() {
        return batchGeneratedId;
    }

    public void setBatchGeneratedId(String batchGeneratedId) {
        this.batchGeneratedId = batchGeneratedId;
    }

    @Override
    public String toString() {
        return "SmsDTO{"
                + "id=" + this.id
                + "version=" + this.version
                + "phoneNumber=" + this.phoneNumber
                + "message=" + this.message
                + "subject=" + this.subject
                + "gatewayId=" + this.gatewayId
                + "createdAt=" + this.createdAt
                + "processedAt=" + this.processedAt
                + "smsProcessedStatus=" + this.smsProcessedStatus
                + "smsType=" + this.smsType
                + "errorMessage=" + this.errorMessage
                + "smsSendType=" + this.smsSendType
                + "batchGeneratedId=" + this.batchGeneratedId
                + '}';
    }

}
