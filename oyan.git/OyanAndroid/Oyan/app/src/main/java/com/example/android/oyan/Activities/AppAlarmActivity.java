package com.example.android.oyan.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.R;

import java.util.Calendar;
import java.util.Locale;

public class AppAlarmActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_alarm);

        final AppAlarmActivity appAlarmActivity = this;

        mediaPlayer = MediaPlayer.create(this.getApplicationContext(), R.raw.alarmclock);
        mediaPlayer.start();
        LinearLayout wakeUp = (LinearLayout) this.findViewById(R.id.wakeUp);

        wakeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                Intent intent = new Intent(appAlarmActivity.getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                appAlarmActivity.startActivity(intent);
            }
        });

        Calendar localTime = Calendar.getInstance(Locale.getDefault());
        int hour = localTime.getTime().getHours();
        RelativeLayout rL = (RelativeLayout) this.findViewById(R.id.relativeLayoutAppAlarm);
        if (hour > 6 && hour < 18) {
            rL.setBackgroundResource(R.drawable.bg1);
        } else if (hour > 18 && hour < 20) {
            rL.setBackgroundResource(R.drawable.bg2);
        } else {
            rL.setBackgroundResource(R.drawable.bg3);
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constant.SHAREDPREFERENCES, MODE_PRIVATE);
        sharedPreferences.edit().putLong("CALL_ACCEPTED", -1).commit();
    }
}