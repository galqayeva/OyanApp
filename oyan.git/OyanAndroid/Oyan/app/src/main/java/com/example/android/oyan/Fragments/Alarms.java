package com.example.android.oyan.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.bumptech.glide.Glide;
import com.example.android.oyan.Activities.AddAlarmActivity;
import com.example.android.oyan.Activities.EditAlarmActivity;
import com.example.android.oyan.Activities.Logout;
import com.example.android.oyan.Activities.MainActivity;
import com.example.android.oyan.Adapters.AlarmListAdapter;
import com.example.android.oyan.Services.AlarmManagerService;
import com.example.android.oyan.Callbacks.AlarmCallback;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Controllers.AlarmController;
import com.example.android.oyan.Domain.AlarmDomain;
import com.example.android.oyan.Domain.Objects;
import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.Listeners.AlarmInterface;
import com.example.android.oyan.Message.Messages;
import com.example.android.oyan.R;
import com.example.android.oyan.Domain.RequetTag;
import com.example.android.oyan.Domain.SingletonVolleyQueue;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Alarms extends Fragment implements AlarmInterface, AlarmCallback {

    private UserDomain userDomain;
    private View rootView;
    private Objects objects;
    private Context context;
    private Alarms alarmsObject = this;
    private ImageView loading;
    private ImageView noConnection;
    public long position;
    public AlarmListAdapter alarmListAdapter;
    private SwipeMenuListView slistView;
    RotateAnimation rotateAnimation;

    public Alarms(UserDomain userDomain) {
        this.userDomain = userDomain;
    }

    public void setInvisible() {

        rotateAnimation = new RotateAnimation(0f, 350f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setDuration(700);
        loading.setVisibility(View.VISIBLE);
        loading.startAnimation(rotateAnimation);
    }

    public void setVisible() {

        loading.setAnimation(null);
        loading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        try {
            super.onActivityCreated(savedInstanceState);
            context = this.getActivity();
        } catch (Exception e) {
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        SingletonVolleyQueue singletonVolleyQueue = SingletonVolleyQueue.getInstance(this.getActivity());
        singletonVolleyQueue.getRequestQueue().cancelAll(RequetTag.LIST_REQUESTS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {
            rootView = inflater.inflate(R.layout.fragment_alarm_list, container, false);


            noConnection = (ImageView) rootView.findViewById(R.id.noConnection);
            loading = (ImageView) rootView.findViewById(R.id.loading);
            getAlarms();

            slistView = (SwipeMenuListView) rootView.findViewById(R.id.slistView);
            SwipeMenuCreator creator = new SwipeMenuCreator() {
                @Override
                public void create(SwipeMenu menu) {
                    switch (0) {
                        case 0:
                            createMenu1(menu);
                            break;
                    }
                }

                private void createMenu1(SwipeMenu menu) {


                    SwipeMenuItem item2 = new SwipeMenuItem(
                            getActivity().getApplicationContext());

                    item2.setWidth(dp2px(90));
                    item2.setIcon(R.drawable.ic_action_trash);
                    menu.addMenuItem(item2);
                }

                private int dp2px(int dp) {
                    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
                }

            };
            slistView.setMenuCreator(creator);
            slistView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                    switch (index) {
                        case 0:
                            onDeleteClick(position);
                            break;
                    }
                    return false;
                }
            });
            slistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onAboutClick(position);
                }
            });

        } catch (Exception e) {
        }
        final MainActivity mainActivity = (MainActivity) this.getActivity();
        ImageView addAlarm = (ImageView) rootView.findViewById(R.id.addAlarm);
        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, AddAlarmActivity.class);
                intent.putExtra("resultDomain", (Serializable) userDomain);
                startActivity(intent);

            }
        });


        Glide.with(getActivity()).
                load(Constant.SERVER_URL_PHOTO + this.userDomain.getPhotoName())
                .placeholder(R.drawable.userph).
                into((ImageView) mainActivity.navigationView.getHeaderView(0)
                        .findViewById(R.id.profilPhotoNav));

        ImageView backImage = (ImageView) rootView.findViewById(R.id.back);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.drawer.openDrawer(Gravity.LEFT);
            }
        });
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Trebuchet MS.ttf");
        ((TextView) rootView.findViewById(R.id.fragmentName)).setTypeface(font);

        Calendar localTime = Calendar.getInstance(Locale.getDefault());
        int hour = localTime.getTime().getHours();
        LinearLayout rL = (LinearLayout) rootView.findViewById(R.id.alarmLayout);
        if (hour > 6 && hour < 18) {
            mainActivity.navigationView.setBackgroundResource(R.drawable.bg1);
            rL.setBackgroundResource(R.drawable.bg1);
        } else if (hour > 18 && hour < 20) {
            rL.setBackgroundResource(R.drawable.bg2);
            mainActivity.navigationView.setBackgroundResource(R.drawable.bg2);
        } else {
            rL.setBackgroundResource(R.drawable.bg3);
            mainActivity.navigationView.setBackgroundResource(R.drawable.bg3);
        }

        return rootView;
    }

    @Override
    public void getAlarms() {
        try {
            AlarmController alarmController = new AlarmController();
            alarmController.getAlarms(this.getActivity(), userDomain, this);
        } catch (Exception e) {
        }
    }

    @Override
    public void onBeginDeleteAlarm() {

    }

    @Override
    public void onBeginGetAlarms() {
        setInvisible();
    }

    @Override
    public void onResultGetAlarms(final Object object) {

        try {

            setVisible();
            objects = (Objects) object;
            if (objects == null || objects.getAlarmDomains() == null || objects.getAlarmDomains().size() == 0) {

                (rootView.findViewById(R.id.noalarm)).setVisibility(View.VISIBLE);
                return;
            }
            boolean[] firstChecked = new boolean[objects.getAlarmDomains().size()];

            Calendar myTime = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
            Calendar localTime = Calendar.getInstance(Locale.getDefault());

            if (localTime.getTimeZone().getID().equals("Asia/Baku")) {
                localTime = Calendar.getInstance(TimeZone.getTimeZone("Asia/Dubai"));
            }
            for (AlarmDomain a : objects.getAlarmDomains()) {

                myTime.set(Calendar.HOUR_OF_DAY, (int) a.getTime() / 60);
                myTime.set(Calendar.MINUTE, (int) a.getTime() % 60);

                localTime.setTimeInMillis(myTime.getTimeInMillis());
                a.setTime(localTime.get(Calendar.HOUR_OF_DAY) * 60 + localTime.get(Calendar.MINUTE));
            }

            alarmListAdapter = new AlarmListAdapter(this.getActivity(), objects.getAlarmDomains(), userDomain, firstChecked);
            slistView.setAdapter(alarmListAdapter);

            SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SHAREDPREFERENCES, context.MODE_PRIVATE);

            if (sharedPreferences.getLong("INSERTONE", -1) == 100) {
                sharedPreferences.edit().putLong("INSERTONE", -1).commit();

                Thread t = new Thread() {
                    @Override
                    public void run() {
                        setAlarmManager(objects.getAlarmDomains());
                    }
                };
                t.start();
            }


        } catch (Exception e) {
        }
    }

    @Override
    public void onErrorGetAlarms() {

        try {
            setVisible();
            noConnection.setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }
    }

    public void onAboutClick(int position) {

        try {
            Intent intent = new Intent(this.getActivity(), EditAlarmActivity.class);
            intent.putExtra("alarmDomain", (Serializable) objects.getAlarmDomains().get(position));
            intent.putExtra("resultDomain", (Serializable) userDomain);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    public boolean onDeleteClick(int position) {

        this.position = position;
        final Objects objects1 = new Objects();
        AlarmDomain a = new AlarmDomain();
        a.setId(objects.getAlarmDomains().get(position).getId());
        objects1.setAlarmDomain(a);
        objects1.setUserDomain(userDomain);
        objects.getAlarmDomains().remove(position);
        if (objects.getAlarmDomains().size() == 0) {

            (rootView.findViewById(R.id.noalarm)).setVisibility(View.VISIBLE);
        }


        Thread t = new Thread() {
            @Override
            public void run() {
                setAlarmManager(objects.getAlarmDomains());
            }
        };
        t.start();
        alarmListAdapter.notifyDataSetChanged();
        AlarmController alarmController = new AlarmController();
        alarmController.deleteAlarm(context, objects1, alarmsObject);
        return true;
    }

    @Override
    public void onResultDeleteAlarm(Object object) {


        if (!(object instanceof AlarmDomain)) {

            return;
        } else if (((AlarmDomain) object).getMessageId() == Messages.AUTHORIZATION_ERROR) {
            Toast.makeText(this.getActivity(), "AUTHORIZATION_ERROR", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this.getActivity(), Logout.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this.getActivity(), "Server error", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onErrorDeleteAlarm() {

        Toast.makeText(this.getActivity(), "Network connection error", Toast.LENGTH_LONG).show();
    }

    public void setAlarmManager(List<AlarmDomain> alarmDomainList) {
        AlarmManagerService.alarmDomainList = alarmDomainList;
        Intent intent = new Intent(context, AlarmManagerService.class);
        context.startService(intent);
    }


}