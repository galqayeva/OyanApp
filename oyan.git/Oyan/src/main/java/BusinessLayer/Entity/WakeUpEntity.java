/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLayer.Entity;

import SeriveLayer.Domain.WakeUpDomain;

/**
 *
 * @author e
 */
public class WakeUpEntity {
     public int getAcceptCallAmount() {
        return acceptCallAmount;
    }

    public void setAcceptCallAmount(int acceptCallAmount) {
        this.acceptCallAmount = acceptCallAmount;
    }

    public int getIncomingCallAmount() {
        return incomingCallAmount;
    }

    public void setIncomingCallAmount(int incomingCallAmount) {
        this.incomingCallAmount = incomingCallAmount;
    }

    public int getOutgoingCallAmount() {
        return outgoingCallAmount;
    }

    public void setOutgoingCallAmount(int outgoingCallAmount) {
        this.outgoingCallAmount = outgoingCallAmount;
    }
    
    public int acceptCallAmount;
    
    public int incomingCallAmount;
    
    public int outgoingCallAmount;
    
    private WakeUpEntity wakeupEntity;

    public WakeUpEntity getWakeupEntity() {
        return wakeupEntity;
    }

    public void setWakeupEntity(WakeUpEntity wakeupEntity) {
        this.wakeupEntity = wakeupEntity;
    }

     
    
}
