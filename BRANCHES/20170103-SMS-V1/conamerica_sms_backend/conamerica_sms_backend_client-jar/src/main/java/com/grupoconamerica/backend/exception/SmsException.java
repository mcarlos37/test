/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.exception;

import javax.xml.ws.WebFault;

/**
 *
 * @author CarlosDaniel
 */
@WebFault
public class SmsException extends Exception {

    public SmsException() {
    }

    public SmsException(String message) {
        super(message);
    }

    public SmsException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
