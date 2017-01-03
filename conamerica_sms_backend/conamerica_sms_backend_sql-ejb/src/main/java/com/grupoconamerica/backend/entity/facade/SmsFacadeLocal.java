/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.entity.facade;

import com.grupoconamerica.backend.entity.Sms;
import com.grupoconamerica.backend.enums.SmsProcessedStatus;
import com.grupoconamerica.backend.enums.SmsType;
import com.grupoconamerica.backend.exception.JPAException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author CarlosDaniel
 */
@Local
public interface SmsFacadeLocal {

    void create(Sms sms) throws JPAException;

    void edit(Sms sms) throws JPAException;

    void remove(Sms sms) throws JPAException;

    Sms find(Object id) throws JPAException;

    List<Sms> findAll() throws JPAException;
    
    List<Sms> findAll(SmsType smsType, Date initDate, Date endDate) throws JPAException;
    
    List<Sms> findRange(SmsType smsType, Date initDate, Date endDate, int[] range) throws JPAException;

    List<Sms> findRange(int[] range) throws JPAException;

    int count() throws JPAException;
    
    Long countByBatchGeneratedIdAndSmsProcessedStatus(String batchGeneratedId, SmsProcessedStatus smsProcessedStatus) throws JPAException;
    
}
