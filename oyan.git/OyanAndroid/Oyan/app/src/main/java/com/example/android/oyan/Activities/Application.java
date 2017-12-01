package com.example.android.oyan.Activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.LruCache;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.oyan.Callbacks.ApplicationCallback;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Controllers.ApplicationController;
import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.Message.Messages;
import com.example.android.oyan.R;

import java.io.Serializable;
import java.util.UUID;

public class Application extends Activity {

    private ProgressBar progressBar = null;
    public static int cacheSize;
    public static int memClass;
    public static String photoNameKey = UUID.randomUUID() + ".jpeg";
    public static LruCache<String, Bitmap> mMemoryCache;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);



    }


}