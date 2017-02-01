/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.dto;

import com.grupoconamerica.backend.enums.SmsMessageType;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author CarlosDaniel
 */
@XmlRootElement
public class SmsFindRangeResponseDTO implements Serializable {
    
    private List<SmsMessageDTO> smsMessageDTOs;
    
    private SmsMessageType smsMessageType;
    
    public SmsFindRangeResponseDTO() {
    }

    public SmsFindRangeResponseDTO(List<SmsMessageDTO> smsMessageDTOs, SmsMessageType smsMessageType) {
        this.smsMessageDTOs = smsMessageDTOs;
        this.smsMessageType = smsMessageType;
    }

    public List<SmsMessageDTO> getSmsMessageDTOs() {
        return smsMessageDTOs;
    }

    public void setSmsMessageDTOs(List<SmsMessageDTO> smsMessageDTOs) {
        this.smsMessageDTOs = smsMessageDTOs;
    }

    public SmsMessageType getSmsMessageType() {
        return smsMessageType;
    }

    public void setSmsMessageType(SmsMessageType smsMessageType) {
        this.smsMessageType = smsMessageType;
    }
    
}
