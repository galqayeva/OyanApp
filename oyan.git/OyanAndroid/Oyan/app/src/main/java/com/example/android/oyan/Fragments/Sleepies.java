package com.example.android.oyan.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.demievil.library.RefreshLayout;
import com.example.android.oyan.Activities.MainActivity;
import com.example.android.oyan.Adapters.SleepiesAdapter;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Controllers.SleepiesController;
import com.example.android.oyan.Domain.AlarmDomain;
import com.example.android.oyan.Domain.Objects;
import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.R;
import com.example.android.oyan.Domain.RequetTag;
import com.example.android.oyan.Domain.SingletonVolleyQueue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;


/**
 * Created by Eldar on 11/11/2015.
 */
public class Sleepies extends Fragment {
    public int pageNumber = 0;
    private RefreshLayout mRefreshLayout;
    private ListView mListView;
    private View footerLayout;
    private TextView textMore;
    private View rootView;
    public ImageView progressBar;
    private UserDomain userDomain;
    private SleepiesAdapter mAdapter;
    private ArrayList<Map<String, String>> mData = new ArrayList<>();
    private AlertDialog dialog;
    private int distance = 1000;
    private int time = 20;
    public String mSearchQuery = "";

    private Sleepies sl;

    public static boolean drawerSliding = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        sl = this;
    }

    public Sleepies(UserDomain userDomain) {

        super();
        this.userDomain = userDomain;
    }

    private void initAdapter() {

        try {
            (rootView.findViewById(R.id.nosleepies)).setVisibility(View.INVISIBLE);
            pageNumber = 0;
            mData.clear();
            mAdapter.notifyDataSetChanged();
            Calendar localTime = Calendar.getInstance(Locale.getDefault());

            if (localTime.getTimeZone().getID().equals("Asia/Baku")) {
                localTime = Calendar.getInstance(TimeZone.getTimeZone("Asia/Dubai"));
            }

            Calendar germanyTime = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
            germanyTime.setTimeInMillis(localTime.getTimeInMillis());
            int hour = germanyTime.get(Calendar.HOUR_OF_DAY);
            int minute = germanyTime.get(Calendar.MINUTE);

            UserDomain userDomain1 = new UserDomain();
            AlarmDomain alarmDomain1 = new AlarmDomain();

            userDomain1.setPageNumber(pageNumber);
            userDomain1.setToken(userDomain.getToken());
            userDomain1.setFullName(mSearchQuery);
            alarmDomain1.setTime(time);
            alarmDomain1.setCurrentTime(hour * 60 + minute);
            alarmDomain1.setWeekDay(germanyTime.get(Calendar.DAY_OF_WEEK));
            Objects objects1 = new Objects();
            objects1.setUserDomain(userDomain1);
            objects1.setAlarmDomain(alarmDomain1);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(rotateAnimation);
            SleepiesController sleepiesController = new SleepiesController();
            sleepiesController.getSleepies(objects1, this, getActivity());
        } catch (Exception e) {
        }
    }


    private void getNewTopData() {

        try {
            Calendar localTime = Calendar.getInstance(Locale.getDefault());
            Calendar myTime = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
            myTime.setTimeInMillis(localTime.getTimeInMillis());
            int hour = myTime.get(Calendar.HOUR_OF_DAY);
            int minute = myTime.get(Calendar.MINUTE);
            UserDomain userDomain1 = new UserDomain();
            AlarmDomain alarmDomain1 = new AlarmDomain();
            userDomain1.setDistance(distance);
            userDomain1.setGender("FEMALE");
            userDomain1.setPageNumber(pageNumber + (mData.size() % 5 != 0 ? 1 : 0));
            userDomain1.setFullName(mSearchQuery);
            userDomain1.setToken(userDomain.getToken());
            alarmDomain1.setTime(time);
            alarmDomain1.setCurrentTime(hour * 60 + minute);
            alarmDomain1.setWeekDay(myTime.get(Calendar.DAY_OF_WEEK));
            userDomain1.setLatitude(userDomain.getLatitude());
            userDomain1.setLongitude(userDomain.getLongitude());
            Objects objects1 = new Objects();
            objects1.setUserDomain(userDomain1);
            objects1.setAlarmDomain(alarmDomain1);
            SleepiesController sleepiesController = new SleepiesController();
            sleepiesController.refreshSleepies(objects1, this, getActivity());
        } catch (Exception e) {
        }
    }

    public void getNewBottomData() {

        try {


            Calendar localTime = Calendar.getInstance(Locale.getDefault());

            if (localTime.getTimeZone().getID().equals("Asia/Baku")) {
                localTime = Calendar.getInstance(TimeZone.getTimeZone("Asia/Dubai"));
            }

            Calendar germanyTime = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
            germanyTime.setTimeInMillis(localTime.getTimeInMillis());
            int hour = germanyTime.get(Calendar.HOUR_OF_DAY);
            int minute = germanyTime.get(Calendar.MINUTE);

            UserDomain userDomain1 = new UserDomain();
            AlarmDomain alarmDomain1 = new AlarmDomain();
            userDomain1.setPageNumber(pageNumber);
            userDomain1.setFullName(mSearchQuery);
            userDomain1.setToken(userDomain.getToken());
            alarmDomain1.setTime(time);
            alarmDomain1.setCurrentTime(hour * 60 + minute);
            alarmDomain1.setWeekDay(germanyTime.get(Calendar.DAY_OF_WEEK));
            Objects objects1 = new Objects();
            objects1.setUserDomain(userDomain1);
            objects1.setAlarmDomain(alarmDomain1);
            SleepiesController sleepiesController = new SleepiesController();
            sleepiesController.getSleepies(objects1, this, getActivity());
        } catch (Exception e) {
        }
    }

    FloatingActionButton fab;

    FloatingActionButton fab1;
    FloatingActionButton fab2;
    RotateAnimation rotateAnimation;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        try {
            rootView = inflater.inflate(R.layout.fragment_sleepies, container, false);
            mRefreshLayout = (RefreshLayout) rootView.findViewById(R.id.swipe_container);
            mListView = (ListView) rootView.findViewById(R.id.list);
            footerLayout = getActivity().getLayoutInflater().inflate(R.layout.listview_footer, null);
            textMore = (TextView) footerLayout.findViewById(R.id.text_more);
            progressBar = (ImageView) footerLayout.findViewById(R.id.load_progress_bar);
            rotateAnimation = new RotateAnimation(0f, 350f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setInterpolator(new LinearInterpolator());
            rotateAnimation.setRepeatCount(Animation.INFINITE);
            rotateAnimation.setDuration(700);


            textMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    simulateLoadingData();
                }
            });
            SleepiesAdapter.m = (MainActivity) getActivity();
            mListView.addFooterView(footerLayout);
            if (mData != null) mData.clear();
            mRefreshLayout.setChildView(mListView);
            mAdapter = new SleepiesAdapter(getActivity(), mData, R.layout.list_item,
                    new String[]{"img", "isFriend", "textId", "text", "textPlace", "textSipUsername", "textSipRegistrar", "userTime", "textViewStatus"},
                    new int[]{R.id.img, R.id.isFriend, R.id.textId, R.id.text, R.id.textPlace, R.id.textSipUsername, R.id.textSipRegistrar, R.id.userTime, R.id.textViewStatus});
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
            mListView.setAdapter(mAdapter);

            mRefreshLayout.setColorSchemeResources(R.color.google_blue);
            mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    simulateFetchingData();
                }
            });
            mRefreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
                @Override
                public void onLoad() {
                    simulateLoadingData();
                }
            });


            final MainActivity mainActivity = (MainActivity) this.getActivity();
            ImageView backImage = (ImageView) rootView.findViewById(R.id.back);
            backImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.drawer.openDrawer(Gravity.LEFT);
                }
            });
            final LinearLayout lin1 = (LinearLayout) rootView.findViewById(R.id.lin1);
            final LinearLayout lin2 = (LinearLayout) rootView.findViewById(R.id.lin2);

            final EditText searchText = (EditText) rootView.findViewById(R.id.searcheditText);
            EditText searchText1 = (EditText) rootView.findViewById(R.id.searcheditText);
            searchText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
            searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        mSearchQuery = searchText.getText().toString();

                        initAdapter();
                        return true;
                    }
                    return false;
                }
            });

            fab_open = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_open);
            fab_close = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_close);
            rotate_forward = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate_forward);
            rotate_backward = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate_backward);


            fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

            fab1 = (FloatingActionButton) rootView.findViewById(R.id.fab1);
            fab2 = (FloatingActionButton) rootView.findViewById(R.id.fab2);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    animateFAB();


                }
            });
            fab2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    adjust();
                }
            });

            fab1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (lin1.getVisibility() == View.GONE) {
                        lin2.setVisibility(View.GONE);
                        lin1.setVisibility(View.VISIBLE);
                        mSearchQuery = "";
                        searchText.setText("");
                        initAdapter();
                    } else {

                        lin1.setVisibility(View.GONE);
                        lin2.setVisibility(View.VISIBLE);
                    }
                }
            });

            ImageView cancel = (ImageView) (rootView.findViewById(R.id.backcancel));
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lin2.setVisibility(View.GONE);
                    lin1.setVisibility(View.VISIBLE);
                    mSearchQuery = "";
                    initAdapter();
                }
            });

            Calendar localTime = Calendar.getInstance(Locale.getDefault());
            int hour = localTime.getTime().getHours();
            LinearLayout rL = (LinearLayout) rootView.findViewById(R.id.sleepiesLayout);
            if (hour > 6 && hour < 18) {
                rL.setBackgroundResource(R.drawable.bg1);
                mainActivity.navigationView.setBackgroundResource(R.drawable.bg1);
            } else if (hour > 18 && hour < 20) {
                rL.setBackgroundResource(R.drawable.bg2);
                mainActivity.navigationView.setBackgroundResource(R.drawable.bg2);
            } else {
                rL.setBackgroundResource(R.drawable.bg3);
                mainActivity.navigationView.setBackgroundResource(R.drawable.bg3);
            }

            ImageView adjust = (ImageView) rootView.findViewById(R.id.adjust);
            adjust.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adjust();
                }
            });
            initAdapter();
        } catch (Exception e) {

        }
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

        SingletonVolleyQueue singletonVolleyQueue = SingletonVolleyQueue.getInstance(this.getActivity());
        singletonVolleyQueue.getRequestQueue().cancelAll(RequetTag.LIST_REQUESTS);
    }

    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    boolean isFabOpen = false;

    public void animateFAB() {

        if (isFabOpen) {
           // ((EditText) rootView.findViewById(R.id.searcheditText)).setText("");
            mSearchQuery = "";

            initAdapter();
            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;


        }
    }

    public void adjust() {

        try {
            AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity(), AlertDialog.THEME_HOLO_LIGHT);
            LayoutInflater inflater = this.getActivity().getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.distancedialog, null);
            b.setView(dialogView);
            dialog = b.create();

            dialog.getWindow().setGravity(Gravity.TOP);
            dialog.show();


            SeekBar seekBar1 = (SeekBar) dialog.findViewById(R.id.seekBar);
            ((TextView) dialog.findViewById(R.id.time)).setText(String.valueOf(time + " min"));
            seekBar1.setMax(20);
            seekBar1.setProgress(time);

            seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    ((TextView) dialog.findViewById(R.id.time)).setText(progress + " min");
                    time = progress;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {

                    pageNumber = 0;
                   // (rootView.findViewById(R.id.nosleepies)).setVisibility(View.INVISIBLE);
                    initAdapter();
                }
            });

        } catch (Exception e) {
        }
    }

    private void simulateFetchingData() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               // (rootView.findViewById(R.id.nosleepies)).setVisibility(View.INVISIBLE);
                initAdapter();//getNewTopData();
                mRefreshLayout.setRefreshing(false);
                mAdapter.notifyDataSetChanged();
                textMore.setVisibility(View.VISIBLE);
                progressBar.setAnimation(null);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }, 0);
    }

    private void simulateLoadingData() {

        textMore.setVisibility(View.GONE);

        progressBar.startAnimation(rotateAnimation);
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                (rootView.findViewById(R.id.nosleepies)).setVisibility(View.INVISIBLE);
                getNewBottomData();
                mRefreshLayout.setLoading(false);
                mAdapter.notifyDataSetChanged();
                textMore.setVisibility(View.VISIBLE);
                progressBar.setAnimation(null);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }, 500);
    }

    public void onResultGetSleepies(Object object) {

        try {
            Objects objects2 = (Objects) object;
            int sizeOb = objects2.getUserDomains().size();
            int size1 = mData.size();
            if (sizeOb == 0) {
                    (rootView.findViewById(R.id.nosleepies)).setVisibility(View.VISIBLE);
                return;
            }
            if (sizeOb > 4) pageNumber++;
            else {
                while (--size1 >= pageNumber * 5) {
                    mData.remove(size1);
                }
                mAdapter.notifyDataSetChanged();
            }
            try {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constant.SHAREDPREFERENCES, Context.MODE_PRIVATE);
                long myId = sharedPreferences.getLong(Constant.ID, 0);
                for (int i = 0; i < sizeOb; i++) {
                    if (objects2.getUserDomains().get(i).getId() == myId) continue;

                    Map<String, String> listItem = new HashMap<>();
                    if (objects2.getUserDomains().get(i).getIsFriend() != -1) {
                        listItem.put("img", objects2.getUserDomains().get(i).getPhotoName());
                        listItem.put("isFriend", "1");
                    } else {
                        listItem.put("isFriend", "-1");
                    }
                    listItem.put("textId", objects2.getUserDomains().get(i).getId() + "");
                    listItem.put("text", objects2.getUserDomains().get(i).getFullName());
                    listItem.put("textPlace", objects2.getUserDomains().get(i).getLocation());
                    listItem.put("textSipUsername", String.valueOf(objects2.getUserDomains().get(i).getSipUsername()));
                    listItem.put("textSipRegistrar", objects2.getUserDomains().get(i).getSipRegistrar());
                    listItem.put("textViewStatus", objects2.getUserDomains().get(i).getStatus());
                    listItem.put("textSipRegistrar", objects2.getUserDomains().get(i).getSipRegistrar());
                    long ft = objects2.getUserDomains().get(i).getFirstTime();
                    listItem.put("userTime", addTime(ft));
                    mData.add(listItem);
                }

            } catch (Exception ex) {

            }
            mAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    public void onErrorGetSleepies(Object object) {

        try {
            Toast.makeText(getActivity(), "Connection lost", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
        }
    }

    public void onResultRefreshSleepies(Object object) {

        try {
            mData.clear();
            mAdapter.notifyDataSetChanged();
            Objects objects2 = (Objects) object;
            int sizeOb = objects2.getUserDomains().size();



            if (sizeOb == 0) {
                (rootView.findViewById(R.id.nosleepies)).setVisibility(View.VISIBLE);
                return;
            }

            pageNumber = sizeOb / 5;
            sl.getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            if (sizeOb == 0) return;
            try {
                String tt = "";
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constant.SHAREDPREFERENCES, Context.MODE_PRIVATE);
                long myId = sharedPreferences.getLong(Constant.ID, 0);
                for (int i = 0; i < sizeOb; i++) {
                    if (objects2.getUserDomains().get(i).getId() == myId) continue;
                    Map<String, String> listItem = new HashMap<>();

                    if (objects2.getUserDomains().get(i).getIsFriend() == 1) {
                        listItem.put("img", objects2.getUserDomains().get(i).getPhotoName());
                        listItem.put("isFriend", "-1");
                    } else {
                        listItem.put("isFriend", "1");
                    }
                    listItem.put("textId", objects2.getUserDomains().get(i).getId() + "");
                    listItem.put("text", objects2.getUserDomains().get(i).getFullName());
                    listItem.put("textPlace", objects2.getUserDomains().get(i).getLocation());
                    listItem.put("textSipUsername", String.valueOf(objects2.getUserDomains().get(i).getSipUsername()));
                    listItem.put("textSipRegistrar", objects2.getUserDomains().get(i).getSipRegistrar());
                    listItem.put("textViewStatus", objects2.getUserDomains().get(i).getStatus());

                    long ft = objects2.getUserDomains().get(i).getFirstTime();
                    listItem.put("userTime", addTime(ft));
                    mData.add(listItem);
                }
            } catch (Exception ex) {
            }
            sl.getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            mAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    public void onErrorRefreshSleepies(Object object) {

        Toast.makeText(getActivity(), "Connection lost", Toast.LENGTH_SHORT).show();
    }


    public String addTime(long ft) {

        String minute1 = "";
        try {
            int hour = (int) (ft / 60);
            int minute = (int) (ft % 60);

            Calendar myTime = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
            myTime.set(Calendar.HOUR_OF_DAY, hour);
            myTime.set(Calendar.MINUTE, minute);

            Calendar localTime = Calendar.getInstance(Locale.getDefault());

            if (localTime.getTimeZone().getID().equals("Asia/Baku")) {
                localTime = Calendar.getInstance(TimeZone.getTimeZone("Asia/Dubai"));
            }

            localTime.setTimeInMillis(myTime.getTimeInMillis());
            hour = localTime.get(Calendar.HOUR_OF_DAY);
            minute = localTime.get(Calendar.MINUTE);

            String hour1 = hour < 10 ? "0" + hour : hour + "";
            minute1 = hour1 + " : " + (minute < 10 ? "0" + minute : minute + "");
        } catch (Exception e) {
        }
        return minute1;
    }


}