/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupoconamerica.backend.connection;

import com.grupoconamerica.backend.util.PropertyFileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.smslib.GatewayException;
import org.smslib.SMSLibException;
import org.smslib.Service;
import org.smslib.modem.SerialModemGateway;

/**
 *
 * @author CarlosDaniel
 */
@Singleton
@Startup
public class SmsConnection implements SmsConnectionLocal {

    @PostConstruct
    public void init() {
        for (String[] gatewayMode : getGatewayModems()) {
            try {
                SerialModemGateway serialModemGateway = new SerialModemGateway(gatewayMode[0], gatewayMode[1], Integer.parseInt(gatewayMode[2]), gatewayMode[3], gatewayMode[4]);
                serialModemGateway.getATHandler().setStorageLocations("MESM");
                serialModemGateway.setInbound(Boolean.TRUE);
                serialModemGateway.setOutbound(Boolean.TRUE);
                Service.getInstance().addGateway(serialModemGateway);
            } catch (GatewayException ex) {
                Logger.getLogger(SmsConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Service.getInstance().setInboundMessageNotification(new InboundNotification());
        try {
            Service.getInstance().startService();
        } catch (SMSLibException | IOException | InterruptedException ex) {
            Logger.getLogger(SmsConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PreDestroy
    public void end() {
        try {
            Service.getInstance().stopService();
        } catch (SMSLibException | IOException | InterruptedException ex) {
            Logger.getLogger(SmsConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static List<String[]> getGatewayModems() {
        List<String[]> gatewayModems = new ArrayList<>();
        for(String gatewayModemProperty : PropertyFileReader.getPropertyFileValues(System.getProperty("com.sun.aas.instanceRoot") + "/config/gatewayModem.properties")){
            gatewayModems.add(gatewayModemProperty.substring(gatewayModemProperty.indexOf("=") + 1).split("_"));
        }
        return gatewayModems;
    }

}
