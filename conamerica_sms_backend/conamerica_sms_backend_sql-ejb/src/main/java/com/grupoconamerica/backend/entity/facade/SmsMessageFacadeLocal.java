/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.entity.facade;

import com.grupoconamerica.backend.entity.SmsMessage;
import com.grupoconamerica.backend.enums.SmsMessageProcessedStatus;
import com.grupoconamerica.backend.enums.SmsMessageType;
import com.grupoconamerica.backend.exception.JPAException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author CarlosDaniel
 */
@Local
public interface SmsMessageFacadeLocal {

    void create(SmsMessage smsMessage) throws JPAException;

    void edit(SmsMessage smsMessage) throws JPAException;

    void remove(SmsMessage smsMessage) throws JPAException;

    SmsMessage find(Object id) throws JPAException;

    List<SmsMessage> findAll() throws JPAException;
    
    List<SmsMessage> findAll(SmsMessageType smsMessageType, Date initDate, Date endDate) throws JPAException;
    
    List<SmsMessage> findAllBySmsMessageType(SmsMessageType smsType, Date initDate, Date endDate, int[] range) throws JPAException;

    List<SmsMessage> findRange(int[] range) throws JPAException;
    
    void updateProcessStatus(List<SmsMessage> smsMessages) throws JPAException;

    int count() throws JPAException;
    
    Integer countByTicketAndSmsMessageProcessedStatus(String ticket, SmsMessageProcessedStatus smsMessageProcessedStatus) throws JPAException;
    
}
