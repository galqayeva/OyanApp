package com.example.android.oyan.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.oyan.Activities.Logout;
import com.example.android.oyan.Activities.MainActivity;
import com.example.android.oyan.Activities.ProfilPhotoActivity;
import com.example.android.oyan.Callbacks.UserSettingsCallback;
import com.example.android.oyan.Callbacks.WakeUpCallback;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Controllers.MyProfileController;
import com.example.android.oyan.Controllers.WakeUpController;
import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.Domain.WakeUpDomain;
import com.example.android.oyan.Message.Messages;
import com.example.android.oyan.R;
import com.example.android.oyan.Domain.RequetTag;
import com.example.android.oyan.Domain.SingletonVolleyQueue;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Eldar on 11/12/2015.
 */
public class MyProfile extends Fragment implements UserSettingsCallback, WakeUpCallback {

    public View rootView;
    public TextView fullName;
    public TextView status;

    public Spinner spinner;
    public UserDomain userDomain;
    public ImageView profilPhoto;

    public MyProfile(UserDomain userDomain) {

        this.userDomain = userDomain;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPause() {
        super.onPause();

        SingletonVolleyQueue singletonVolleyQueue = SingletonVolleyQueue.getInstance(this.getActivity());
        singletonVolleyQueue.getRequestQueue().cancelAll(RequetTag.LIST_REQUESTS);
    }

    MainActivity mainActivity2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_myprofile, container, false);
        MyProfileController myProfileController = new MyProfileController();
        myProfileController.getUser(getActivity(), this, userDomain);
        WakeUpController controller = new WakeUpController();
        controller.getCallData(getActivity(), this, userDomain);

        fullName = (TextView) rootView.findViewById(R.id.fragmentName);
        status = (TextView) rootView.findViewById(R.id.status);

        final MainActivity mainActivity = (MainActivity) this.getActivity();
        mainActivity2 = mainActivity;
        fullName.setText(userDomain.getFullName());
        profilPhoto = (ImageView) rootView.findViewById(R.id.profilPhoto);

        ImageView backImage = (ImageView) rootView.findViewById(R.id.back);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.drawer.openDrawer(Gravity.LEFT);
            }
        });

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Trebuchet MS.ttf");

        status.setTypeface(font);
        status.setText(userDomain.getStatus());
        ((TextView) rootView.findViewById(R.id.fragmentName)).setTypeface(font);
        profilPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getActivity(), ProfilPhotoActivity.class);
                intent.putExtra("resultDomain", userDomain);
                startActivity(intent);
            }
        });
        int t = R.drawable.userph;

        Bitmap myBitmap = null;
            Picasso.with(this.getActivity()).load(Constant.SERVER_URL_PHOTO + userDomain.getPhotoName())
                     .placeholder(t).into(profilPhoto);


        Calendar localTime = Calendar.getInstance(Locale.getDefault());
        int hour = localTime.getTime().getHours();
        LinearLayout rL = (LinearLayout) rootView.findViewById(R.id.myProfileLayout);
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

        return rootView;
    }

    @Override
    public void onGetUserResult(Object result) {

        if (!(result instanceof UserDomain)) {

            return;
        }
        UserDomain resultDomain = (UserDomain) result;
        if (((UserDomain) result).getMessageId() == Messages.SUCCESFULL) {
            userDomain.setFullName(resultDomain.getFullName());
            userDomain.setPhotoName(resultDomain.getPhotoName());
            userDomain.setStatus(resultDomain.getStatus());
            Glide.with(this.getActivity()).
                    load(Constant.SERVER_URL_PHOTO + userDomain.getPhotoName())
                    .placeholder(R.drawable.userph).
                    into(
                            (ImageView) (mainActivity2.navigationView.getHeaderView(0).findViewById(R.id.profilPhotoNav))
                    );


            SharedPreferences sharedPreferences =
                    this.getActivity().getSharedPreferences(Constant.SHAREDPREFERENCES, getActivity().MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(Constant.FULLNAME);
            editor.remove(Constant.STATUS);
            editor.remove(Constant.PHOTO_NAME);
            editor.commit();
            editor.putString(Constant.FULLNAME, resultDomain.getFullName());
            editor.putString(Constant.STATUS, resultDomain.getStatus());
            editor.putString(Constant.PHOTO_NAME, resultDomain.getPhotoName());
            editor.commit();
        } else if (((UserDomain) result).getMessageId() == Messages.AUTHORIZATION_ERROR) {
            Toast.makeText(this.getActivity(), "AUTHORIZATION_ERROR", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this.getActivity(), Logout.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this.getActivity(), "Server error", Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onGetCallDataResult(Object object) {
        if ((object instanceof WakeUpDomain)) {

            WakeUpDomain resultDomain = (WakeUpDomain) object;
            ((TextView) rootView.findViewById(R.id.textq1)).setText(String.valueOf(resultDomain.getIncomingCallAmount()));
            ((TextView) rootView.findViewById(R.id.textq2)).setText(String.valueOf(resultDomain.getAcceptCallAmount()));
            ((TextView) rootView.findViewById(R.id.textq3)).setText(String.valueOf(resultDomain.getOutgoingCallAmount()));
        }
    }


}