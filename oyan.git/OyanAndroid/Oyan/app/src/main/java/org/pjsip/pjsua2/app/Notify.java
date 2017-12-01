package org.pjsip.pjsua2.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;

import com.example.android.oyan.Activities.Login;
import com.example.android.oyan.Activities.Registration;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Controllers.CallController;
import com.example.android.oyan.Controllers.WakeUpController;
import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.Services.SipService;
import com.example.android.oyan.Domain.Sip;

import org.pjsip.pjsua2.CallInfo;
import org.pjsip.pjsua2.CallOpParam;
import org.pjsip.pjsua2.PresenceStatus;
import org.pjsip.pjsua2.pjsip_inv_state;
import org.pjsip.pjsua2.pjsip_status_code;

/**
 * Created by eldarh on 12/12/15.
 */
class MSG_TYPE {

    public final static int INCOMING_CALL = 1;
    public final static int CALL_STATE = 2;
    public final static int REG_STATE = 3;
    public final static int BUDDY_STATE = 4;
    public final static int CALL_MEDIA_STATE = 5;
}

public class Notify implements Handler.Callback, MyAppObserver {

    Handler handler = new Handler(this);
    public SipService sipService;

    public Notify(SipService sipService) {

        this.sipService = sipService;
    }

    @Override
    public boolean handleMessage(Message m) {

        try {
            Log.v("THIS IS MESSAGE ", m.what + " ");
            if (m.what == 0) {

                Sip.app.deinit();
                Runtime.getRuntime().gc();
                android.os.Process.killProcess(android.os.Process.myPid());
            } else if (m.what == MSG_TYPE.CALL_STATE) {

                CallOpParam prm = new CallOpParam();
                prm.setStatusCode(pjsip_status_code.PJSIP_SC_RINGING);
                CallInfo ci = (CallInfo) m.obj;
        /* Forward the message to CallActivity */
                if (CallActivity.handler_ != null) {
                    Message m2 = Message.obtain(CallActivity.handler_, MSG_TYPE.CALL_STATE, ci);
                    m2.sendToTarget();
                }

            } else if (m.what == MSG_TYPE.CALL_MEDIA_STATE) {
         /* Forward the message to CallActivity */
                if (CallActivity.handler_ != null) {
                    Message m2 = Message.obtain(CallActivity.handler_, MSG_TYPE.CALL_MEDIA_STATE, null);
                    m2.sendToTarget();
                }
            } else if (m.what == MSG_TYPE.BUDDY_STATE) {

                MyBuddy buddy = (MyBuddy) m.obj;
                int idx = Sip.account.buddyList.indexOf(buddy);

		/* Return back Call activity */
                notifyCallState(Sip.currentCall);
            } else if (m.what == MSG_TYPE.REG_STATE) {

                String msg_str = (String) m.obj;
                Log.v("REGISTRATION MESSAGE ", msg_str);

            } else if (m.what == MSG_TYPE.INCOMING_CALL) {

                  final MyCall call = (MyCall) m.obj;
                CallOpParam prm = new CallOpParam();


                if (Sip.currentCall != null) {
                    call.delete();
                    return true;
                }
            /* Answer with ringing */
                prm.setStatusCode(pjsip_status_code.PJSIP_SC_RINGING);
                try {
                    call.answer(prm);
                } catch (Exception e) {
                }
                try {
                    UserDomain userDomain = new UserDomain();
                    userDomain.setSipUsername(
                            Long.valueOf(
                                    call.getInfo().getRemoteUri().substring(call.getInfo().getRemoteUri().indexOf(":")
                                            + 1, call.getInfo().getRemoteUri().indexOf("@"))));
                    CallController callController = new CallController();
                    callController.getUserBySipID(sipService, userDomain);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Sip.currentCall = call;


                try {
                    UserDomain userDomain2 = new UserDomain();
                    userDomain2.setToken(this.sipService.
                            getSharedPreferences(Constant.SHAREDPREFERENCES, 0).getString(Constant.TOKEN, null));
                    WakeUpController cc = new WakeUpController();
                    userDomain2.setId(CallActivity.callerID);
                    cc.updateIOutgoingCall(this.sipService, userDomain2);
                }catch(Exception e){}
            } else {
                return false;
            }
        } catch (Exception e) {
        }
        return true;
    }

    public static Ringtone mediaPlayer;

    public void notifyIncomingCall(MyCall call) {
        if (Sip.currentCall != null) {
            call.delete();
            return;
        }
        Message m = Message.obtain(handler, MSG_TYPE.INCOMING_CALL, call);
        m.sendToTarget();
    }

    public void notifyRegState(pjsip_status_code code, String reason, int expiration) {

        String msg_str = "";

//        if (expiration == 0)
//            msg_str += "Unregistration";
//        else
//            msg_str += "Registration";
        int res = code.swigValue() / 100;
//        if (res == 2) Sip.networkConnected = true;
//        else Sip.networkConnected = false;
        Sip.networkConnected = res == 2 ? true : false;

//        if (Login.fromLogin) {
//            Log.v("This is res " , "" + res);
//            if (res == 2) {
//                msg_str += " successful";
//
////                Sip.networkConnected = true;
////                Login.sipConPD.dismiss();
//                Login.fromLogin = false;
//                Login.toMainActivity(this.sipService.getApplicationContext());
//            } else {
////                Login.fromLogin = false;
////                Sip.networkConnected = false;
////                Login.alertSipFailReg(sipService.getApplicationContext());
//                msg_str += " failed: " + reason;
//            }
//        } else if (Registration.fromRegist) {
//
//            if (res == 2) {
//                msg_str += " successful";
//
//                Sip.networkConnected = true;
//                Registration.sipConPD.dismiss();
//                Registration.fromRegist = false;
//                Registration.toPhotoActivity(this.sipService.getApplicationContext());
//            } else {
//                Sip.networkConnected = false;
//                Registration.fromRegist = false;
//                Registration.alertSipFailReg(sipService.getApplicationContext());
//                msg_str += " failed: " + reason;
//            }
//
//        } else {
//            if (res == 2) Sip.networkConnected = true;
//            else Sip.networkConnected = false;
//        }

        Log.v("Your Regist status ", msg_str);
        Message m = Message.obtain(handler, MSG_TYPE.REG_STATE, msg_str);
        m.sendToTarget();
    }

    public void notifyCallState(MyCall call) {

        if (Sip.currentCall == null || call.getId() != Sip.currentCall.getId()) return;

        CallInfo ci;
        try {
            ci = call.getInfo();
        } catch (Exception e) {
            ci = null;
        }
        Message m = Message.obtain(handler, MSG_TYPE.CALL_STATE, ci);
        m.sendToTarget();
        if (ci != null && ci.getState() == pjsip_inv_state.PJSIP_INV_STATE_DISCONNECTED) {

            Sip.currentCall = null;
            m = null;
        }
    }

    public void notifyCallMediaState(MyCall call) {
        Message m = Message.obtain(handler, MSG_TYPE.CALL_MEDIA_STATE, null);
        m.sendToTarget();
    }

    public void notifyBuddyState(MyBuddy buddy) {

        Message m = Message.obtain(handler, MSG_TYPE.INCOMING_CALL, buddy);
        m.sendToTarget();
    }
}