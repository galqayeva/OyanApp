package com.example.android.oyan.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.android.oyan.Callbacks.LogoutCallback;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Controllers.LogoutController;
import com.example.android.oyan.R;


import java.util.Calendar;
import java.util.Locale;

public class Logout extends AppCompatActivity implements LogoutCallback {


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        Calendar localTime = Calendar.getInstance(Locale.getDefault());
        int hour = localTime.getTime().getHours();
        RelativeLayout rL = (RelativeLayout) this.findViewById(R.id.logoutLayout);
        if (hour > 6 && hour < 18) {
            rL.setBackgroundResource(R.drawable.bg1);
        } else if (hour > 18 && hour < 20) {
            rL.setBackgroundResource(R.drawable.bg2);
        } else {
            rL.setBackgroundResource(R.drawable.bg3);
        }
        String Token = getSharedPreferences(Constant.SHAREDPREFERENCES, MODE_PRIVATE).getString(Constant.TOKEN, null);
        RotateAnimation rotateAnimation = new RotateAnimation(0f, 350f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setDuration(700);
        ImageView logoImage = (ImageView) this.findViewById(R.id.loading);
        logoImage.startAnimation(rotateAnimation);

        SharedPreferences sharedPreferences = this.getSharedPreferences(Constant.SHAREDPREFERENCES, MODE_PRIVATE);
        String emailAddress = sharedPreferences.getString("LAST_EMAIL", "");
        getSharedPreferences(Constant.SHAREDPREFERENCES, MODE_PRIVATE).edit().clear().commit();
        getSharedPreferences(Constant.SHAREDPREFERENCES, MODE_PRIVATE).edit().putString("LAST_EMAIL", emailAddress).commit();
        LogoutController logoutController = new LogoutController();
        logoutController.logout(Token, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResultLogout(Object result) {

        Intent intent = new Intent(this, Entry.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Calendar localTime = Calendar.getInstance(Locale.getDefault());
        int hour = localTime.getTime().getHours();
        RelativeLayout rL = (RelativeLayout) this.findViewById(R.id.logoutLayout);
        if (hour > 6 && hour < 18) {
            rL.setBackgroundResource(R.drawable.bg1);
        } else if (hour > 18 && hour < 20) {
            rL.setBackgroundResource(R.drawable.bg2);
        } else {
            rL.setBackgroundResource(R.drawable.bg3);
        }

    }

    @Override
    public void onBeginLogout() {

    }

    @Override
    public void onErrorLogout(Object error) {

        Intent intent = new Intent(this, Entry.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
