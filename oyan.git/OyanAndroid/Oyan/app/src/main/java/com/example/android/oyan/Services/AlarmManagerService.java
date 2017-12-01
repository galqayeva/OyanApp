package com.example.android.oyan.Services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.oyan.BroadCastRecievers.AlarmReceiver;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Domain.AlarmDomain;

import java.util.Calendar;
import java.util.List;

public class AlarmManagerService extends Service {

    public static List<AlarmDomain> alarmDomainList;
    public static int alarmCount = 0;
    public static Calendar lastCallTime ;

    public AlarmManagerService() {
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        if (alarmDomainList == null) return;

        Log.v("Service ", "start again");
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constant.SHAREDPREFERENCES, this.MODE_PRIVATE);

        int k = 1;
        int preK = sharedPreferences.getInt("preK", -1);
        Log.v("preK is -> ", preK + "");
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent1 = new Intent(this, AlarmReceiver.class);
        if (preK > 0) {
            for (int i = 1; i < preK; i++) {

                PendingIntent p = PendingIntent.getBroadcast(this, i, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                p.cancel();
                alarmManager.cancel(p);
            }
        }

        Calendar calSet = Calendar.getInstance();

        for (AlarmDomain alarmDomain : alarmDomainList) {

            if (alarmDomain.getIsActive() == 1) {
                for (long day : alarmDomain.getWeekDays()) {

                    calSet.set(Calendar.DAY_OF_WEEK, (int) day);
                    calSet.set(Calendar.HOUR_OF_DAY, (int) alarmDomain.getTime() / 60);
                    calSet.set(Calendar.MINUTE, (int) (alarmDomain.getTime() % 60));
                    calSet.set(Calendar.SECOND, (int) (0));

                    Intent intentAlarm = new Intent(this, AlarmReceiver.class);

                    intentAlarm.putExtra("TIME", String.valueOf(alarmDomain.getTime()));
                    intentAlarm.putExtra("WEEKDAY", String.valueOf(day));

                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                            calSet.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7,
                            PendingIntent.getBroadcast(this, k++, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
                }
            }
        }

        sharedPreferences.edit().putInt("preK", k).commit();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
