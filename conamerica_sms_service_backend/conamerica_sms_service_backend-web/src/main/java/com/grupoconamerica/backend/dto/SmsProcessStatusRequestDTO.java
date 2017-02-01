/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author CarlosDaniel
 */
@XmlRootElement
public class SmsProcessStatusRequestDTO implements Serializable {

    private String ticket;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
    
    public SmsProcessStatusRequestDTO() {
    }

    public SmsProcessStatusRequestDTO(String ticket) {
        this.ticket = ticket;
    }
    
}
