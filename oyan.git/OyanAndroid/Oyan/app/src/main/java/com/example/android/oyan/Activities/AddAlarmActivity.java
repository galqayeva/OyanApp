package com.example.android.oyan.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.oyan.Callbacks.AddAlarmCallback;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Controllers.AlarmController;
import com.example.android.oyan.Domain.AlarmDomain;
import com.example.android.oyan.Domain.BaseDomain;
import com.example.android.oyan.Domain.Objects;
import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.Listeners.AddAlarmInterface;
import com.example.android.oyan.Message.Messages;
import com.example.android.oyan.R;
import com.example.android.oyan.Domain.RequetTag;
import com.example.android.oyan.Domain.SingletonVolleyQueue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class AddAlarmActivity extends AppCompatActivity implements AddAlarmCallback, AddAlarmInterface {

    private UserDomain userDomain;
    private Objects resultObjects;
    private AlarmDomain resultAlarmDomain;
    private UserDomain resultUserDomain;
    private TimePicker timePicker;
    private TextView textview1Add;
    private long[] weekDays;
    private int hour;
    private int minute;
    private Context context;
    private AddAlarmActivity addAlarmActivity = this;

    String tt = "";
    private ImageView loading;
    RotateAnimation rotateAnimation;
    LinearLayout lin1;

    public void setInvisible() {
        lin1.setVisibility(View.INVISIBLE);
        rotateAnimation = new RotateAnimation(0f, 350f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setDuration(700);
        loading.setVisibility(View.VISIBLE);
        loading.startAnimation(rotateAnimation);
    }

    public void setVisible() {
        lin1.setVisibility(View.VISIBLE);
        loading.setAnimation(null);
        loading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();

        SingletonVolleyQueue singletonVolleyQueue = SingletonVolleyQueue.getInstance(this);
        singletonVolleyQueue.getRequestQueue().cancelAll(RequetTag.LIST_REQUESTS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        context = this;
        userDomain = (UserDomain) getIntent().getSerializableExtra("resultDomain");
        timePicker = (TimePicker) this.findViewById(R.id.timePickerAdd);

        timePicker.setIs24HourView(true);
        Calendar cal = Calendar.getInstance();
        int h = cal.get(Calendar.HOUR_OF_DAY);
        int m = cal.get(Calendar.MINUTE);
        timePicker.setCurrentHour(h);
        timePicker.setCurrentMinute(m);
        loading = (ImageView) this.findViewById(R.id.loading);
        Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Trebuchet MS.ttf");
        ((TextView) this.findViewById(R.id.textView3)).setTypeface(font);
        ((TextView) this.findViewById(R.id.fragmentName)).setTypeface(font);
        ((Button) this.findViewById(R.id.buttonSave)).setTypeface(font);
        ((ImageView) this.findViewById(R.id.back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAlarmActivity.finish();
            }
        });

        Calendar localTime = Calendar.getInstance(Locale.getDefault());
        int hour = localTime.getTime().getHours();
        lin1 = (LinearLayout) this.findViewById(R.id.addalarmLinLayout);
        RelativeLayout rL = (RelativeLayout) this.findViewById(R.id.addalarmLayout);
        if (hour > 6 && hour < 18) {
            rL.setBackgroundResource(R.drawable.bg1);
        } else if (hour > 18 && hour < 20) {
            rL.setBackgroundResource(R.drawable.bg2);
        } else {
            rL.setBackgroundResource(R.drawable.bg3);
        }
    }

    ArrayList<Integer> aL = new ArrayList<>();
    boolean[] daysChecked = {false, false, false, false, false, false, false};

    @Override
    public void chooseDay(View view) {

    }

    public void markDayMo(View view) {
        Button dayButton = (Button) view;
        daysChecked[0] = !daysChecked[0];
        if (daysChecked[0]) {
            aL.add(2);
            dayButton.setBackgroundResource(R.drawable.days2);
            dayButton.setTextColor(Color.rgb(0, 0, 0));
        } else {
            dayButton.setBackgroundResource(R.drawable.days);
            dayButton.setTextColor(Color.WHITE);
            aL.remove(new Integer(2));
        }
    }

    public void markDayTu(View view) {

        Button dayButton = (Button) view;
        daysChecked[1] = !daysChecked[1];
        if (daysChecked[1]) {
            aL.add(3);
            dayButton.setBackgroundResource(R.drawable.days2);
            dayButton.setTextColor(Color.rgb(0, 0, 0));
        } else {
            dayButton.setBackgroundResource(R.drawable.days);
            dayButton.setTextColor(Color.WHITE);
            aL.remove(new Integer(3));
        }
    }

    public void markDayWe(View view) {

        Button dayButton = (Button) view;
        daysChecked[2] = !daysChecked[2];
        if (daysChecked[2]) {
            aL.add(4);
            dayButton.setTextColor(Color.rgb(0, 0, 0));
            dayButton.setBackgroundResource(R.drawable.days2);

        } else {
            dayButton.setBackgroundResource(R.drawable.days);
            aL.remove(new Integer(4));
            dayButton.setTextColor(Color.WHITE);
        }
    }

    public void markDayTh(View view) {

        Button dayButton = (Button) view;
        daysChecked[3] = !daysChecked[3];
        if (daysChecked[3]) {
            aL.add(5);
            dayButton.setBackgroundResource(R.drawable.days2);
            dayButton.setTextColor(Color.rgb(0, 0, 0));
        } else {
            dayButton.setBackgroundResource(R.drawable.days);
            dayButton.setTextColor(Color.WHITE);
            aL.remove(new Integer(5));
        }
    }

    public void markDayFr(View view) {

        Button dayButton = (Button) view;
        daysChecked[4] = !daysChecked[4];
        if (daysChecked[4]) {
            aL.add(6);
            dayButton.setBackgroundResource(R.drawable.days2);
            dayButton.setTextColor(Color.rgb(0, 0, 0));
        } else {
            dayButton.setBackgroundResource(R.drawable.days);
            dayButton.setTextColor(Color.WHITE);
            aL.remove(new Integer(6));
        }
    }

    public void markDaySa(View view) {

        Button dayButton = (Button) view;
        daysChecked[5] = !daysChecked[5];
        if (daysChecked[5]) {
            aL.add(7);
            dayButton.setBackgroundResource(R.drawable.days2);
            dayButton.setTextColor(Color.rgb(0, 0, 0));
        } else {
            dayButton.setBackgroundResource(R.drawable.days);
            dayButton.setTextColor(Color.WHITE);
            aL.remove(new Integer(7));
        }
    }

    public void markDaySu(View view) {

        Button dayButton = (Button) view;
        daysChecked[6] = !daysChecked[6];
        if (daysChecked[6]) {
            aL.add(1);
            dayButton.setBackgroundResource(R.drawable.days2);
            dayButton.setTextColor(Color.rgb(0, 0, 0));
        } else {
            dayButton.setBackgroundResource(R.drawable.days);
            dayButton.setTextColor(Color.WHITE);
            aL.remove(new Integer(1));
        }
    }


    @Override
    public void saveAlarm(View view) {

        weekDays = new long[aL.size()];
        for (int i = 0; i < aL.size(); i++) {
            weekDays[i] = (long) aL.get(i);
        }
        Arrays.sort(weekDays);

        hour = timePicker.getCurrentHour();
        minute = timePicker.getCurrentMinute();
        Calendar localTime = Calendar.getInstance(Locale.getDefault());

        if (localTime.getTimeZone().getID().equals("Asia/Baku")) {
            localTime = Calendar.getInstance(TimeZone.getTimeZone("Asia/Dubai"));
        }
        localTime.set(Calendar.HOUR_OF_DAY, hour);
        localTime.set(Calendar.MINUTE, minute);

        Calendar myTime = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        myTime.setTimeInMillis(localTime.getTimeInMillis());
        hour = myTime.get(Calendar.HOUR_OF_DAY);
        minute = myTime.get(Calendar.MINUTE);

        resultAlarmDomain = new AlarmDomain();
        resultAlarmDomain.setIsActive(1);
        resultAlarmDomain.setWeekDays(weekDays);
        resultAlarmDomain.setTime(hour * 60 + minute);
        resultUserDomain = new UserDomain();
        resultUserDomain.setToken(userDomain.getToken());

        resultUserDomain.setLocation(tt);
        resultObjects = new Objects();
        resultObjects.setAlarmDomain(resultAlarmDomain);
        resultObjects.setUserDomain(resultUserDomain);
        AlarmController alarmController = new AlarmController();
        alarmController.addAlarm(this, resultObjects);
    }

    @Override
    public void onBeginAddAlarm() {

        setInvisible();
    }

    @Override
    public void onErrorAddAlarm() {

        setVisible();

    }

    @Override
    public void onResultAddAlarm(Object object) {


        BaseDomain baseDomain = (BaseDomain) object;
        if (baseDomain.getMessageId() == Messages.SUCCESFULL) {

            SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SHAREDPREFERENCES, context.MODE_PRIVATE);
            sharedPreferences.edit().putLong("INSERTONE", 100).commit();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("resultDomain", (Serializable) userDomain);
            startActivity(intent);
        } else if (baseDomain.getMessageId() == Messages.AUTHORIZATION_ERROR) {
            Toast.makeText(this, "AUTHORIZATION_ERROR", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Logout.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Server error", Toast.LENGTH_LONG).show();
            setVisible();
        }
    }
}