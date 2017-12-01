package com.example.android.oyan;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.android.oyan.BroadCastRecievers.AlarmReceiver;

/**
 * Created by e on 8/29/16.
 */
public class SingletonBroadcastReceiver {

    private static SingletonBroadcastReceiver mInstance;

    public Intent intentAlarm;
    public AlarmManager alarmManager;
    Context ctx;

    private SingletonBroadcastReceiver(Context context) {

        ctx = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        intentAlarm = new Intent(context, AlarmReceiver.class);
    }

    public static synchronized SingletonBroadcastReceiver getInstance(Context context) {

        if (mInstance == null) {
            mInstance = new SingletonBroadcastReceiver(context);
        }
        return mInstance;
    }

    public AlarmManager getAlarmManager() {
        if (alarmManager == null) {
            alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        }
        return alarmManager;
    }

    public Intent getIntent() {
        if (intentAlarm == null) {
            intentAlarm = new Intent(ctx, AlarmReceiver.class);
        }
        return intentAlarm;
    }
}
