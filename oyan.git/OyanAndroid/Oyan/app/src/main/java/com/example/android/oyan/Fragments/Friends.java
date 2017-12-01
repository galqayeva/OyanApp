package com.example.android.oyan.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.example.android.oyan.Activities.EditAlarmActivity;
import com.example.android.oyan.Activities.Logout;
import com.example.android.oyan.Activities.MainActivity;
import com.example.android.oyan.Activities.Wakeup;
import com.example.android.oyan.Adapters.FriendsAdapter;
import com.example.android.oyan.Callbacks.FriendsCallback;
import com.example.android.oyan.Controllers.FriendsController;
import com.example.android.oyan.Domain.AlarmDomain;
import com.example.android.oyan.Domain.Objects;
import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.Message.Messages;
import com.example.android.oyan.R;
import com.example.android.oyan.Domain.RequetTag;
import com.example.android.oyan.Domain.SingletonVolleyQueue;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by e on 2/15/16.
 */
public class Friends extends Fragment implements FriendsCallback {

    private View rootView;
    private UserDomain userDomain;
    private FriendsAdapter mAdapter;
    private ImageView noConnection;
    public ArrayList<UserDomain> userDomains;
    private ImageView loading;
    private SwipeMenuListView slistView;
    RotateAnimation rotateAnimation;
    TextView nofriend;

    public void setInvisible() {
        slistView.setVisibility(View.INVISIBLE);
        noConnection.setVisibility(View.INVISIBLE);
        rotateAnimation = new RotateAnimation(0f, 350f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setDuration(700);
        loading.setVisibility(View.VISIBLE);
        loading.startAnimation(rotateAnimation);
        nofriend.setVisibility(View.INVISIBLE);

    }

    public void setVisible() {

        slistView.setVisibility(View.VISIBLE);
        loading.setAnimation(null);
        loading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public Friends(UserDomain userDomain) {
        super();
        this.userDomain = userDomain;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        try {
            inflater.inflate(R.menu.menu_friends, menu);
            super.onCreateOptionsMenu(menu, inflater);
        } catch (Exception e) {
        }
    }

    private void initAdapter() {

        try {
            Calendar localTime = Calendar.getInstance(Locale.getDefault());
            Calendar myTime = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
            myTime.setTimeInMillis(localTime.getTimeInMillis());
            int hour = myTime.get(Calendar.HOUR_OF_DAY);
            int minute = myTime.get(Calendar.MINUTE);
            UserDomain userDomain1 = new UserDomain();
            AlarmDomain alarmDomain1 = new AlarmDomain();
            userDomain1.setToken(userDomain.getToken());
            alarmDomain1.setCurrentTime(hour * 60 + minute);
            alarmDomain1.setWeekDay(myTime.get(Calendar.DAY_OF_WEEK));
            Objects objects1 = new Objects();
            objects1.setUserDomain(userDomain1);
            objects1.setAlarmDomain(alarmDomain1);
            setInvisible();
            FriendsController friendsController = new FriendsController();
            friendsController.getFriends(objects1, this, getActivity());

        } catch (Exception e) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {
            rootView = inflater.inflate(R.layout.fragment_friends_list, container, false);
            nofriend = (TextView) (rootView.findViewById(R.id.nofriend));
            noConnection = (ImageView) rootView.findViewById(R.id.noConnection);
            slistView = (SwipeMenuListView) rootView.findViewById(R.id.listViewFav1);
            userDomains = new ArrayList<>();
            mAdapter = new FriendsAdapter(getActivity(), userDomains);
            slistView.setAdapter(mAdapter);
            loading = (ImageView) rootView.findViewById(R.id.loading);
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
                    SwipeMenuItem item1 = new SwipeMenuItem(
                            getActivity().getApplicationContext());

                    SwipeMenuItem item2 = new SwipeMenuItem(
                            getActivity().getApplicationContext());

                    item2.setWidth(dp2px(90));
                    item2.setIcon(R.drawable.ic_action_trash);
                    menu.addMenuItem(item2);
                }

                private int dp2px(int dp) {
                    return (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, dp, getActivity().getResources().getDisplayMetrics());
                }

            };
            slistView.setMenuCreator(creator);
            slistView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                    switch (index) {
                        case 0:

                            deleteFriend(position);
                            break;
                    }
                    return false;
                }
            });
            slistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    itemclick(position);

                }
            });
            final MainActivity mainActivity = (MainActivity) this.getActivity();
            ImageView adjust = (ImageView) rootView.findViewById(R.id.adjust);
            adjust.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    initAdapter();
                }
            });

            ImageView backImage = (ImageView) rootView.findViewById(R.id.back);
            backImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.drawer.openDrawer(Gravity.LEFT);
                }
            });
            Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Trebuchet MS.ttf");
            ((TextView) rootView.findViewById(R.id.fragmentName)).setTypeface(font);

            initAdapter();

            Calendar localTime = Calendar.getInstance(Locale.getDefault());
            int hour = localTime.getTime().getHours();
            LinearLayout rL = (LinearLayout) rootView.findViewById(R.id.friendLayout);
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

        } catch (Exception e) {
        }

        return rootView;
    }

    public void itemclick(int position) {
        UserDomain userDomain5 = userDomains.get(position);
        Intent intent = new Intent(this.getActivity(), Wakeup.class);

        intent.putExtra("wakeupDomain", (Serializable) userDomain5);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();

        SingletonVolleyQueue singletonVolleyQueue = SingletonVolleyQueue.getInstance(this.getActivity());
        singletonVolleyQueue.getRequestQueue().cancelAll(RequetTag.LIST_REQUESTS);
    }

    public void onResultGetFriends(Object object) {

        userDomains.clear();
        if (object == null || ((Objects) object).getUserDomains() == null
                || ((Objects) object).getUserDomains().size() == 0)
            nofriend.setVisibility(View.VISIBLE);
        else
            userDomains.addAll(((Objects) object).getUserDomains());
        setVisible();
        mAdapter.notifyDataSetChanged();
    }

    public void onErrorGetFriends(Object object) {

        try {
            setVisible();
            noConnection.setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }
    }

    int position1;

    public void deleteFriend(int position) {

        try {
            UserDomain userDomain4 = new UserDomain();

            long Id = Long.valueOf(userDomains.get(position).getId());
            userDomain4.setId(Id);

            position1 = position;
            UserDomain userDomain3 = new UserDomain();
            userDomain3.setId(userDomain.getId());
            userDomain3.setToken(userDomain.getToken());
            Objects ob = new Objects();
            ArrayList<UserDomain> u = new ArrayList<>();
            u.add(userDomain3);
            u.add(userDomain4);
            ob.setUserDomains(u);
            FriendsController friendsController = new FriendsController();
            friendsController.deleteFriend(this.getActivity(), this, ob);
        } catch (Exception e) {
        }
    }

    @Override
    public void onResultDeleteFriend(Object object) {

        if (!(object instanceof UserDomain)) return;
        UserDomain resultDomain = (UserDomain) object;

        if (resultDomain.getMessageId() == Messages.SUCCESFULL) {

            userDomains.remove(position1);

            mAdapter.notifyDataSetChanged();
        } else if (resultDomain.getMessageId() == Messages.AUTHORIZATION_ERROR) {
            Toast.makeText(this.getActivity(), "AUTHORIZATION_ERROR", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this.getActivity(), Logout.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this.getActivity(), "Server error", Toast.LENGTH_LONG).show();
        }

        if(userDomains.size() == 0 ){
            nofriend.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onErrorDeleteFriend(Object object) {

    }

}