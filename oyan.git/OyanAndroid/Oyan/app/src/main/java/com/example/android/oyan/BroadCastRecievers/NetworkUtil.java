package com.example.android.oyan.BroadCastRecievers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.android.oyan.Domain.Sip;

/**
 * Created by eldarh on 12/25/15.
 */

public class NetworkUtil {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;


    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static int getConnectivityStatusString(Context context) {

        int conn = NetworkUtil.getConnectivityStatus(context);
        if (conn == 0) {

            try {
                if (Sip.app != null && Sip.account != null) {
                    Sip.app.delAcc(Sip.account);
                    Sip.app.accList.clear();
                    Sip.account.cfg.delete();
                    Sip.account.delete();
                }
            } catch (Exception e) {
            }
        }
        return conn == NetworkUtil.TYPE_NOT_CONNECTED ? 0 : 1;
    }
}

