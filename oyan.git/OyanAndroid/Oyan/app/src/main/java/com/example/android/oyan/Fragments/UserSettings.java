package com.example.android.oyan.Fragments;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.android.oyan.Activities.MainActivity;
import com.example.android.oyan.Callbacks.MyProfileCallback;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Controllers.MyProfileController;
import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.Message.Messages;
import com.example.android.oyan.R;
import com.example.android.oyan.Domain.RequetTag;
import com.example.android.oyan.Domain.SingletonVolleyQueue;

import java.util.Calendar;
import java.util.Locale;

import static android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;


public class UserSettings extends Fragment implements MyProfileCallback {

    public View rootView;
    public EditText fullname;
    public EditText status;
    public UserDomain userDomain;
    private ProgressDialog progressDialog;

    public UserSettings(UserDomain userDomain) {
        this.userDomain = userDomain;
    }

    private ImageView loading;
    private SwipeMenuListView slistView;
    RotateAnimation rotateAnimation;
    LinearLayout lin1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_user_settings, container, false);
        final MainActivity mainActivity = (MainActivity) this.getActivity();
        loading = (ImageView) rootView.findViewById(R.id.loading);
        ImageView backImage = (ImageView) rootView.findViewById(R.id.back);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.drawer.openDrawer(Gravity.LEFT);
            }
        });
        ImageView saveImage = (ImageView) rootView.findViewById(R.id.saveData);
        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();

            }
        });
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Trebuchet MS.ttf");
        ((TextView) rootView.findViewById(R.id.fragmentName)).setTypeface(font);

        fullname = ((EditText) rootView.findViewById(R.id.editTextfullname));
        status = ((EditText) rootView.findViewById(R.id.editTextStatus));
        fullname.setText(userDomain.getFullName());
        status.setText(userDomain.getStatus());

        Calendar localTime = Calendar.getInstance(Locale.getDefault());
        int hour = localTime.getTime().getHours();
        if (hour > 6 && hour < 18) {
            mainActivity.navigationView.setBackgroundResource(R.drawable.bg1);
        } else if (hour > 18 && hour < 20) {
            mainActivity.navigationView.setBackgroundResource(R.drawable.bg2);
        } else {
            mainActivity.navigationView.setBackgroundResource(R.drawable.bg3);
        }
        return rootView;
    }

    public void saveProfile() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
                Constant.SHAREDPREFERENCES, getActivity().MODE_PRIVATE);
        UserDomain resultDomain1 = new UserDomain();
        resultDomain1.setToken(sharedPreferences.getString(Constant.TOKEN, "0"));
        resultDomain1.setFullName(fullname.getText().toString());
        resultDomain1.setStatus(status.getText().toString());

        MyProfileController myProfileController = new MyProfileController();
        myProfileController.editProfile(getActivity(), this, resultDomain1);
    }

    @Override
    public void onBeginEditProfile() {

        progressDialog = new ProgressDialog(this.getActivity(), ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
        progressDialog.setMessage("Updating...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();

        SingletonVolleyQueue singletonVolleyQueue = SingletonVolleyQueue.getInstance(this.getActivity());
        singletonVolleyQueue.getRequestQueue().cancelAll(RequetTag.LIST_REQUESTS);
    }

    @Override
    public void onErrorEditProfile(Object error) {
        if (progressDialog != null) progressDialog.cancel();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), THEME_DEVICE_DEFAULT_LIGHT);
        builder.setMessage("Network connection error");
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onResultEditProfile(Object result) {
        if (progressDialog != null) progressDialog.cancel();
        if (!(result instanceof UserDomain)) return;


        UserDomain resultDomain = (UserDomain) result;

        if (resultDomain.getMessageId() == Messages.SUCCESFULL) {

            userDomain.setFullName(resultDomain.getFullName());
            userDomain.setPhotoName(resultDomain.getPhotoName());
            SharedPreferences sharedPreferences =
                    this.getActivity().getSharedPreferences(Constant.SHAREDPREFERENCES, getActivity().MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(Constant.FULLNAME);
            editor.remove(Constant.STATUS);
            editor.commit();
            editor.putString(Constant.FULLNAME, resultDomain.getFullName());
            editor.putString(Constant.STATUS, resultDomain.getStatus());
            editor.commit();

            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            getActivity().startActivity(intent);
        } else if (resultDomain.getMessageId() == Messages.DATA_ACCESS_LAYER_ISSUE || resultDomain.getMessageId() == Messages.DATA_VALIDATION_ERROR) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), THEME_DEVICE_DEFAULT_LIGHT);
            builder.setMessage("Server error");
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return;
        }
    }
}