/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.dto;

import com.grupoconamerica.backend.enums.SmsType;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author CarlosDaniel
 */
public class SmsRetrieveResponseDTO implements Serializable {
    
    private List<SmsDTO> smsDTOs;
    
    private SmsType smsType;
    
    public SmsRetrieveResponseDTO() {
    }

    public SmsRetrieveResponseDTO(List<SmsDTO> smsDTOs, SmsType smsType) {
        this.smsDTOs = smsDTOs;
        this.smsType = smsType;
    }

    public List<SmsDTO> getSmsDTOs() {
        return smsDTOs;
    }

    public void setSmsDTOs(List<SmsDTO> smsDTOs) {
        this.smsDTOs = smsDTOs;
    }

    public SmsType getSmsType() {
        return smsType;
    }

    public void setSmsType(SmsType smsType) {
        this.smsType = smsType;
    }
    
}
