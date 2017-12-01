package com.example.android.oyan.Activities;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.android.oyan.Constants.Constant;

/**
 * Created by e on 9/1/16.
 */
public class ApplicationStart extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constant.SHAREDPREFERENCES, this.MODE_PRIVATE);
        sharedPreferences.edit().putLong("INSERTONE", 100).commit();
    }
}
