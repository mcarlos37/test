/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.grupoconamerica.backend.enums.SmsMessageType;
import com.grupoconamerica.backend.util.DateAdapterWithMilliseconds;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author CarlosDaniel
 */
@XmlRootElement
public class SmsFindRangeRequestDTO implements Serializable {

    private SmsMessageType smsMessageType;

    private Date initDate;

    private Date endDate;

    private Integer quantity;

    public SmsMessageType getSmsMessageType() {
        return smsMessageType;
    }

    public void setSmsMessageType(SmsMessageType smsMessageType) {
        this.smsMessageType = smsMessageType;
    }

    @JsonProperty("initDate")
    @XmlJavaTypeAdapter(DateAdapterWithMilliseconds.class)
    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    @JsonProperty("endDate")
    @XmlJavaTypeAdapter(DateAdapterWithMilliseconds.class)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public SmsFindRangeRequestDTO() {
    }

    public SmsFindRangeRequestDTO(SmsMessageType smsMessageType, Date initDate, Date endDate, Integer quantity) {
        this.smsMessageType = smsMessageType;
        this.initDate = initDate;
        this.endDate = endDate;
        this.quantity = quantity;
    }

}
