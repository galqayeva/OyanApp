package com.example.android.oyan.BroadCastRecievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.example.android.oyan.Domain.Sip;
import com.example.android.oyan.Services.SipService;

/**
 * Created by eldarh on 12/25/15.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {


        if (Sip.makeConn == true) {
            int r = NetworkUtil.getConnectivityStatusString(context);
            try {
//                Toast.makeText(context, r + " ", Toast.LENGTH_SHORT).show();
                final Intent i1 = new Intent(context, SipService.class);
                if (r == 1) {
                    context.startService(i1);
                } else {
                    context.stopService(i1);
                    Sip.networkConnected = false;
                }
            } catch (Exception e) {
            }
        }
    }
}