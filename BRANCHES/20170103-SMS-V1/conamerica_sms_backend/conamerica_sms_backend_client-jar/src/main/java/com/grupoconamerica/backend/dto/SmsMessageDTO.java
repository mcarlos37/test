/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.dto;

import com.grupoconamerica.backend.enums.SmsMessageProcessedStatus;
import com.grupoconamerica.backend.enums.SmsMessageSendType;
import com.grupoconamerica.backend.enums.SmsMessageType;
import com.grupoconamerica.backend.util.DateAdapterWithMilliseconds;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author CarlosDaniel
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SmsMessageDTO implements Serializable {

    @XmlTransient
    protected Long id;

    protected String phoneNumber;

    protected String message;

    protected String subject;

    @XmlTransient
    protected String gatewayId;

    @XmlElement(name = "createdAt", required = true)
    @XmlJavaTypeAdapter(DateAdapterWithMilliseconds.class)
    protected Date createdAt;

    @XmlElement(name = "processedAt", required = true)
    @XmlJavaTypeAdapter(DateAdapterWithMilliseconds.class)
    protected Date processedAt;

    protected SmsMessageProcessedStatus smsMessageProcessedStatus;

    protected SmsMessageType smsMessageType;

    protected String errorMessage;

    protected SmsMessageSendType smsMessageSendType;

    @XmlTransient
    protected String ticket;

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

    public SmsMessageProcessedStatus getSmsMessageProcessedStatus() {
        return smsMessageProcessedStatus;
    }

    public void setSmsMessageProcessedStatus(SmsMessageProcessedStatus smsMessageProcessedStatus) {
        this.smsMessageProcessedStatus = smsMessageProcessedStatus;
    }

    public SmsMessageType getSmsMessageType() {
        return smsMessageType;
    }

    public void setSmsMessageType(SmsMessageType smsMessageType) {
        this.smsMessageType = smsMessageType;
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

    public SmsMessageSendType getSmsMessageSendType() {
        return smsMessageSendType;
    }

    public void setSmsMessageSendType(SmsMessageSendType smsMessageSendType) {
        this.smsMessageSendType = smsMessageSendType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "SmsMessageDTO{"
                + "id=" + this.id
                + "phoneNumber=" + this.phoneNumber
                + "message=" + this.message
                + "subject=" + this.subject
                + "gatewayId=" + this.gatewayId
                + "createdAt=" + this.createdAt
                + "processedAt=" + this.processedAt
                + "smsProcessedStatus=" + this.smsMessageProcessedStatus
                + "smsType=" + this.smsMessageType
                + "errorMessage=" + this.errorMessage
                + "smsSendType=" + this.smsMessageSendType
                + "ticket=" + this.ticket
                + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SmsMessageDTO other = (SmsMessageDTO) obj;
        System.out.print("=============================================================================");
        System.out.print("=============================================================================");
        System.out.print("=============================================================================");

        System.out.print("NUMBER:" + this.phoneNumber + "  OTHER:   " + other.phoneNumber);
        System.out.print("MESSAGE:" + this.message + "  OTHER:   " + other.message);

        return Objects.equals(this.phoneNumber, other.phoneNumber) && Objects.equals(this.message, other.message);
    }

}
