/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.service;

import com.grupoconamerica.backend.exception.SmsException;
import javax.ejb.Stateless;
import javax.jws.WebParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author CarlosDaniel
 */
@Stateless
@Path("sms")
public class SmsREST {

    @GET
    @Path("sendMessage/{phoneNumber}/{message}")
    @Produces("text/plain")
    public void sendMessage(@WebParam(name = "phoneNumber") String phoneNumber, @WebParam(name = "message") String message) throws SmsException {
    }

}
