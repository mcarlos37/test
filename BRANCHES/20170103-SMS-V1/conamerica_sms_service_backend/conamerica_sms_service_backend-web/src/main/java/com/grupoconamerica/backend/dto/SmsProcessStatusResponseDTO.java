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
public class SmsProcessStatusResponseDTO implements Serializable {
    
    private int processedQuantity;
    
    private int totalQuantity;

    public int getProcessedQuantity() {
        return processedQuantity;
    }

    public void setProcessedQuantity(int processedQuantity) {
        this.processedQuantity = processedQuantity;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public SmsProcessStatusResponseDTO() {
    }

    public SmsProcessStatusResponseDTO(int processedQuantity, int totalQuantity) {
        this.processedQuantity = processedQuantity;
        this.totalQuantity = totalQuantity;
    }
    
}
