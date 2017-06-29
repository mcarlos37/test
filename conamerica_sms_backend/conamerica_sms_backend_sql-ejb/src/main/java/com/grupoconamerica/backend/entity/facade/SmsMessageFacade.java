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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

/**
 *
 * @author CarlosDaniel
 */
@Stateless
public class SmsMessageFacade extends AbstractFacade<SmsMessage> implements SmsMessageFacadeLocal {

    @PersistenceUnit(unitName = "conamerica_sms_PU")
    private EntityManagerFactory emf;

    @Override
    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public SmsMessageFacade() {
        super(SmsMessage.class);
    }

    @Override
    public List<SmsMessage> findAll(SmsMessageType smsMessageType, Date initDate, Date endDate) throws JPAException {
        EntityManager em = this.getEntityManager();
        try {
            TypedQuery<SmsMessage> query = em.createNamedQuery("SmsMessage.findAllBySmsMessageType", SmsMessage.class);
            query.setParameter("smsMessageType", smsMessageType);
            query.setParameter("initDate", initDate);
            query.setParameter("endDate", endDate);
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "RESULT LIST SIZE: " + query.getResultList().size());
            return query.getResultList();
        } catch (PersistenceException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            throw new JPAException(ex.getMessage(), ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<SmsMessage> findAllBySmsMessageType(SmsMessageType smsMessageType, Date initDate, Date endDate, int[] range) throws JPAException {
        EntityManager em = this.getEntityManager();
        try {
            TypedQuery<SmsMessage> query = em.createNamedQuery("SmsMessage.findAllBySmsMessageType", SmsMessage.class);
            query.setParameter("smsMessageType", smsMessageType);
            System.out.println("*******************************************************************");
            System.out.println("*******************************************************************");
            System.out.println(initDate.toString());
            query.setParameter("initDate", initDate);
            query.setParameter("endDate", endDate);
            query.setMaxResults(range[1] - range[0] + 1);
            query.setFirstResult(range[0]);
            return query.getResultList();
        } catch (PersistenceException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            throw new JPAException(ex.getMessage(), ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Integer countByTicketAndSmsMessageProcessedStatus(String ticket, SmsMessageProcessedStatus smsMessageProcessedStatus) throws JPAException {
        EntityManager em = this.getEntityManager();
        try {
            TypedQuery<Long> query = em.createNamedQuery("SmsMessage.countByTicketAndSmsMessageProcessedStatus", Long.class);
            query.setParameter("ticket", ticket);
            query.setParameter("smsMessageProcessedStatus", smsMessageProcessedStatus);
            return (int) (long) query.getSingleResult();
        } catch (PersistenceException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            throw new JPAException(ex.getMessage(), ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
