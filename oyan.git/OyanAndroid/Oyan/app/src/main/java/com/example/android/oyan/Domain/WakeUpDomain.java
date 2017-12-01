package com.example.android.oyan.Domain;

/**
 * Created by e on 4/5/17.
 */
public class WakeUpDomain {
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
}
