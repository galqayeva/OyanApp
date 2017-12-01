package com.example.android.oyan.BroadCastRecievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.android.oyan.Activities.AppAlarmActivity;
import com.example.android.oyan.Services.AlarmManagerService;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String tt = intent.getStringExtra("TIME");
        String rr = intent.getStringExtra("WEEKDAY");
        if (tt == null || rr == null) {
            Log.v("There is null", "");
            return;
        }
        long TIME = Long.valueOf(tt);
        long WEEKDAY = Long.valueOf(rr);

        // SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SHAREDPREFERENCES, context.MODE_PRIVATE);
        // String CALLEDYET = sharedPreferences.getString("CALLEDYET", "NO");

        Calendar curTime = Calendar.getInstance();

        //     long ALARMTIME = sharedPreferences.getLong("ALARMTIME", -1);

        Log.v("THIS ->  " + TIME + "  --  " + WEEKDAY, "TIME ->  " + (curTime.get(Calendar.HOUR_OF_DAY) * 60 + curTime.get(Calendar.MINUTE))

                + "  --  " + curTime.get(Calendar.DAY_OF_WEEK));

        if (curTime.get(Calendar.DAY_OF_WEEK) == WEEKDAY && TIME == curTime.get(Calendar.HOUR_OF_DAY) * 60 + curTime.get(Calendar.MINUTE)) {

            //   sharedPreferences.edit().putLong("ALARMTIME", TIME).commit();
            //      Log.v("YOUR ALARM ", "TIME IS ON !!!  -> CALL_ACCEPTED  = " + CALLEDYET + " - " + ALARMTIME);

            if (AlarmManagerService.lastCallTime != null && TIME - (AlarmManagerService.lastCallTime.getTime().getHours() * 60 +
                    AlarmManagerService.lastCallTime.getTime().getMinutes()) <= 20) {
                AlarmManagerService.lastCallTime = null;
            }
//            if (CALLEDYET == "YES") {
//
//                sharedPreferences.edit().putString("CALLEDYET", "NO").commit();
//            }
            else {

                Intent intent2 = new Intent(context, AppAlarmActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
            }
        }
    }
}