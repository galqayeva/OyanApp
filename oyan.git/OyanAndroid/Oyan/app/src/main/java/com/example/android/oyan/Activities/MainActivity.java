package com.example.android.oyan.Activities;

import android.Manifest;
import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.oyan.Services.AlarmManagerService;
import com.example.android.oyan.BroadCastRecievers.AlarmReceiver;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Domain.Sip;
import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.Fragments.Alarms;
import com.example.android.oyan.Fragments.Friends;
import com.example.android.oyan.Fragments.MyProfile;
import com.example.android.oyan.Fragments.Sleepies;
import com.example.android.oyan.Fragments.UserSettings;
import com.example.android.oyan.R;
import com.example.android.oyan.Domain.RequetTag;
import com.example.android.oyan.Services.SipService;
import com.example.android.oyan.Domain.SingletonVolleyQueue;
import com.example.android.oyan.BroadCastRecievers.TimeChange;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public MainActivity mainActivity = this;
    private UserDomain userDomain;
    public NavigationView navigationView;
    public DrawerLayout drawer;


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            mainActivity = this;
            Sip.makeConn = true;
            setContentView(R.layout.activity_main);

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.PROCESS_OUTGOING_CALLS) == PackageManager.PERMISSION_DENIED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {

                Intent intent = new Intent(this, Entry.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                this.startActivity(intent);
                Log.e("error","error");
            }
            userDomain = new UserDomain();
            SharedPreferences sharedPreferences = this.getSharedPreferences(Constant.SHAREDPREFERENCES, MODE_PRIVATE);

            if (sharedPreferences.getLong(Constant.ID, -1) == -1 || sharedPreferences.getString(Constant.EMAIL, null) == null) {

                String emailAddress = sharedPreferences.getString("LAST_EMAIL", "");
                getSharedPreferences(Constant.SHAREDPREFERENCES, MODE_PRIVATE).edit().clear().commit();
                getSharedPreferences(Constant.SHAREDPREFERENCES, MODE_PRIVATE).edit().putString("LAST_EMAIL", emailAddress).commit();
                Intent intent = new Intent(this, Entry.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                this.startActivity(intent);
            }


            userDomain.setId(sharedPreferences.getLong(Constant.ID, 0));
            userDomain.setFullName(sharedPreferences.getString(Constant.FULLNAME, null));
            userDomain.setEmail(sharedPreferences.getString(Constant.EMAIL, null));
            userDomain.setGender(sharedPreferences.getString(Constant.GENDER, null));
            userDomain.setPhotoName(sharedPreferences.getString(Constant.PHOTO_NAME, null));
            userDomain.setToken(sharedPreferences.getString(Constant.TOKEN, null));
            userDomain.setSipUsername(sharedPreferences.getLong(Constant.SIPUSERNAME, -1));
            userDomain.setSipPassword(sharedPreferences.getString(Constant.SIPPASSWORD, null));
            userDomain.setSipRegistrar(sharedPreferences.getString(Constant.SIPREGISTRAR, null));
            userDomain.setStatus(sharedPreferences.getString(Constant.STATUS, null));

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            android.support.v7.app.ActionBarDrawerToggle toggle =
                    new android.support.v7.app.ActionBarDrawerToggle(this, drawer, toolbar,
                            R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();


            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((TextView) (navigationView.getHeaderView(0).findViewById(R.id.navprofilName))).setText(userDomain.getFullName());

                    Sleepies.drawerSliding = true;
                    Fragment fragment = new MyProfile(userDomain);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);

                }
            });

            drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {

                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    Glide.with(mainActivity).
                            load(Constant.SERVER_URL_PHOTO + userDomain.getPhotoName())
                            .placeholder(R.drawable.userph).
                            into(
                                    (ImageView) (mainActivity.navigationView.getHeaderView(0).findViewById(R.id.profilPhotoNav))
                            );
                }

                @Override
                public void onDrawerClosed(View drawerView) {

                }

                @Override
                public void onDrawerStateChanged(int newState) {

                }
            });


            navigationView.setCheckedItem(R.id.nav_alarm);
            Calendar localTime = Calendar.getInstance(Locale.getDefault());
            int hour = localTime.getTime().getHours();
            if (hour > 6 && hour < 18) {
                navigationView.setBackgroundResource(R.drawable.bg1);

            } else if (hour > 18 && hour < 20) {
                navigationView.setBackgroundResource(R.drawable.bg2);
            } else {
                navigationView.setBackgroundResource(R.drawable.bg3);
            }
            Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Trebuchet MS.ttf");
            TextView profilName = (TextView) (navigationView.getHeaderView(0).findViewById(R.id.navprofilName));
            profilName.setText(userDomain.getFullName());
            profilName.setTypeface(font);
            this.getSupportActionBar().hide();

            Fragment fragment = new Alarms(userDomain);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
        } catch (Exception e) {
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        SingletonVolleyQueue singletonVolleyQueue = SingletonVolleyQueue.getInstance(this);
        singletonVolleyQueue.getRequestQueue().cancelAll(RequetTag.LIST_REQUESTS);
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Calendar localTime = Calendar.getInstance(Locale.getDefault());
            int hour = localTime.getTime().getHours();
            if (hour > 6 && hour < 18) {
                navigationView.setBackgroundResource(R.drawable.bg1);

            } else if (hour > 18 && hour < 20) {
                navigationView.setBackgroundResource(R.drawable.bg2);
            } else {
                navigationView.setBackgroundResource(R.drawable.bg3);
            }
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        Sleepies.drawerSliding = true;
        Fragment fragment = null;

        if (menuItem.getItemId() == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Oyan");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Happy to see you in Oyan");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        } else if (menuItem.getItemId() == R.id.nav_alarm) {

            fragment = new Alarms(userDomain);

        } else if (menuItem.getItemId() == R.id.nav_friend) {

            fragment = new Friends(userDomain);

        } else if (menuItem.getItemId() == R.id.nav_sleepies) {

            fragment = new Sleepies(userDomain);
            Sleepies.drawerSliding = false;

        } else if (menuItem.getItemId() == R.id.nav_Setting) {

            fragment = new UserSettings(userDomain);
            Sleepies.drawerSliding = false;

        } else if (menuItem.getItemId() == R.id.nav_logout) {
            userDomain = null;
            Sip.makeConn = false;
            Intent intent2 = new Intent(this, SipService.class);
            stopService(intent2);
            if (Sip.app != null) Sip.app.accList.clear();
            if (Sip.account != null && Sip.account.isValid()) Sip.account.delete();
            Intent intent = new Intent(this, Logout.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            try {
                SharedPreferences sharedPreferences = this.getSharedPreferences(Constant.SHAREDPREFERENCES, this.MODE_PRIVATE);
                int preK = sharedPreferences.getInt("preK", -1);
                if (preK > 0) {
                    for (int i = 1; i < preK; i++) {
                        Intent intent1 = new Intent(this, AlarmReceiver.class);
                        AlarmManager alarmManager1 = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
                        alarmManager1.cancel(PendingIntent.getBroadcast(this, i, intent1, PendingIntent.FLAG_CANCEL_CURRENT));
                    }
                }
                Intent intent3 = new Intent(this, AlarmManagerService.class);
                stopService(intent3);


                getApplicationContext().unregisterReceiver(new AlarmReceiver());
            } catch (Exception e) {

            }
            try {
                getApplicationContext().unregisterReceiver(new TimeChange());
            } catch (Exception e) {
            }

        }

        if (fragment != null) {
            final Fragment fragment1 = fragment;
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment1).commit();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}