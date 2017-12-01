package com.example.android.oyan.Domain;

import org.pjsip.pjsua2.AccountConfig;
import org.pjsip.pjsua2.app.MyAccount;
import org.pjsip.pjsua2.app.MyApp;
import org.pjsip.pjsua2.app.MyCall;

/**
 * Created by eldarh on 12/16/15.
 */
public class Sip {

    //    SIP *********************************************************** SIP

    public static MyApp app = null;
    public static MyCall currentCall = null;
    public static MyAccount account = null;
    public static AccountConfig accCfg = null;
    public static boolean networkConnected = false;
    public static boolean makeConn = false;
}
