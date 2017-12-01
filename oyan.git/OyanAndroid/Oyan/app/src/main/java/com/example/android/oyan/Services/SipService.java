package com.example.android.oyan.Services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.android.oyan.Activities.MainActivity;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Domain.Sip;

import org.pjsip.pjsua2.AccountConfig;
import org.pjsip.pjsua2.AuthCredInfo;
import org.pjsip.pjsua2.AuthCredInfoVector;
import org.pjsip.pjsua2.app.MyApp;
import org.pjsip.pjsua2.app.Notify;


public class SipService extends Service {

    public SipService() {

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        try {
            SharedPreferences sharedPreferences = this.getSharedPreferences(Constant.SHAREDPREFERENCES, MODE_PRIVATE);
            String registrar = "sip:10.50.3.176"; //sharedPreferences.getString(Constant.SIPREGISTRAR, "");
            String username = String.valueOf(sharedPreferences.getLong(Constant.SIPUSERNAME, 0));
            String password = sharedPreferences.getString(Constant.SIPPASSWORD, "");
            String acc_id = "sip:" + username + "@" + registrar.substring(4);

//        Log.v(registrar + "  - " + username ," - " + password + " -  " + acc_id);
//         acc_id = "sip:1004@10.50.3.176";
//         registrar = "sip:10.50.3.176";
//         username  = "1004";
//         password  = "freebsd";

            Notify notify = new Notify(this);
            if (Sip.app == null) {
                Sip.app = new MyApp();
                Sip.app.init(notify, getFilesDir().getAbsolutePath());
            }
            if (Sip.app.accList.size() == 0) {

                Sip.accCfg = new AccountConfig();
                Sip.accCfg.setIdUri(acc_id);
                Sip.accCfg.getNatConfig().setIceEnabled(true);
                Sip.accCfg.getVideoConfig().setAutoTransmitOutgoing(true);
                Sip.accCfg.getVideoConfig().setAutoShowIncoming(true);
                Sip.account = Sip.app.addAcc(Sip.accCfg);
            } else {

                Sip.account = Sip.app.accList.get(0);
                Sip.accCfg = Sip.account.cfg;
            }
            Sip.accCfg.setIdUri(acc_id);
            Sip.accCfg.getRegConfig().setRegistrarUri(registrar);
            AuthCredInfoVector creds = Sip.accCfg.getSipConfig().getAuthCreds();
            creds.clear();
            creds.add(new AuthCredInfo("Digest", "*", username, 0, password));
            Sip.accCfg.getNatConfig().setIceEnabled(true);
            try {
                Sip.account.modify(Sip.accCfg);
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}