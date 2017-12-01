package com.example.android.oyan.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.oyan.Callbacks.RegistrationCallback;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Controllers.RegistrationController;
import com.example.android.oyan.Domain.CountryDomain;
import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.Listeners.RegistrationInterface;
import com.example.android.oyan.Message.Messages;
import com.example.android.oyan.R;
import com.example.android.oyan.Services.SipService;
import com.victor.loading.rotate.RotateLoading;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Registration extends AppCompatActivity implements RegistrationInterface, RegistrationCallback {

    private EditText emailAddress = null;
    private EditText password = null;
    private EditText fullName = null;
    private RadioGroup genderChoice = null;
    private RelativeLayout relativeLayR;

    private ImageView loading;
    RotateAnimation rotateAnimation;
    RelativeLayout rel1;

    public void setInvisible() {
        rel1.setVisibility(View.INVISIBLE);
        rotateAnimation = new RotateAnimation(0f, 350f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setDuration(700);
        loading.setVisibility(View.VISIBLE);
        loading.startAnimation(rotateAnimation);
    }

    public void setVisible() {
        rel1.setVisibility(View.VISIBLE);
        loading.setAnimation(null);
        loading.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Trebuchet MS.ttf");
        ((EditText) this.findViewById(R.id.emailAddress)).setTypeface(font);
        ((EditText) this.findViewById(R.id.fullName)).setTypeface(font);
        ((RadioButton) this.findViewById(R.id.radioButtonFemale)).setTypeface(font);
        ((RadioButton) this.findViewById(R.id.radioButtonMale)).setTypeface(font);

        ((EditText) this.findViewById(R.id.password)).setTypeface(font);
        ((TextView) this.findViewById(R.id.textView)).setTypeface(font);
        ((Button) this.findViewById(R.id.joinNow)).setTypeface(font);
        ((Button) this.findViewById(R.id.joinNowFace)).setTypeface(font);
        loading = (ImageView) this.findViewById(R.id.loading);
        Calendar localTime = Calendar.getInstance(Locale.getDefault());
        int hour = localTime.getTime().getHours();
        RelativeLayout rL = (RelativeLayout) this.findViewById(R.id.registrationLayout);
        rel1 = (RelativeLayout) this.findViewById(R.id.relativeLayR);
        if (hour > 6 && hour < 18) {
            rL.setBackgroundResource(R.drawable.bg1);
        } else if (hour > 18 && hour < 20) {
            rL.setBackgroundResource(R.drawable.bg2);
        } else {
            rL.setBackgroundResource(R.drawable.bg3);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Calendar localTime = Calendar.getInstance(Locale.getDefault());
        int hour = localTime.getTime().getHours();
        RelativeLayout rL = (RelativeLayout) this.findViewById(R.id.registrationLayout);
        if (hour > 6 && hour < 18) {
            rL.setBackgroundResource(R.drawable.bg1);
        } else if (hour > 18 && hour < 20) {
            rL.setBackgroundResource(R.drawable.bg2);
        } else {
            rL.setBackgroundResource(R.drawable.bg3);
        }

    }


    @Override
    public void getCountries() {

//        ArrayAdapter<CharSequence> arrayAdapter =
//                ArrayAdapter.createFromResource(this, R.array.countries, R.layout.countryspinnerlayout);
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);


    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void registration(View view) {


        emailAddress = (EditText) this.findViewById(R.id.emailAddress);
        fullName = (EditText) this.findViewById(R.id.fullName);
        password = (EditText) this.findViewById(R.id.password);
        genderChoice = (RadioGroup) this.findViewById(R.id.genderChoice);
        //    spinnerCountryNames = (Spinner) this.findViewById(R.id.spinnerCountryNames);
        String emailAdressText = emailAddress.getText().toString().trim();
        String fullNameText = fullName.getText().toString().trim();
        String passwordText = password.getText().toString().trim();
        //   String countryName = spinnerCountryNames.getSelectedItem().toString();
        String genderChoiceText = null;
        if (emailAdressText == null
                || emailAdressText.isEmpty()
                || fullNameText == null
                || fullNameText.isEmpty()
                || passwordText == null
                || passwordText.isEmpty()) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setMessage("Make sure you add a valid data");
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

        RadioButton radioButton = (RadioButton) this.findViewById(genderChoice.getCheckedRadioButtonId());
        genderChoiceText = radioButton.getText().toString();
        UserDomain userDomain = new UserDomain();
        userDomain.setEmail(emailAdressText);
        userDomain.setFullName(fullNameText);
        userDomain.setPassword(passwordText);
        userDomain.setGender(genderChoiceText);
        // userDomain.setCountryName(countryName);
//        if (userDomain.getGender().equals("Female")) {
//            userDomain.setPhotoName("woman.jpeg");
//        } else {
//            userDomain.setPhotoName("man.jpeg");
//        }
        RegistrationController registrationController = new RegistrationController();
        registrationController.registration(this, userDomain);
    }

    @Override
    public void onBeginRegistration() {

        setInvisible();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onResultRegistration(Object object) {

        if (!(object instanceof UserDomain)) return;
        UserDomain resultDomain = (UserDomain) object;
        if (resultDomain == null || resultDomain.getMessageId() == Messages.DATA_ACCESS_LAYER_ISSUE) {

            setVisible();

            AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
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
        } else if (resultDomain.getMessageId() == Messages.REGISTRATION_ERROR) {

            setVisible();

            AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setMessage("Email address already exist");
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return;
        } else if (resultDomain.getMessageId() == Messages.DATA_VALIDATION_ERROR) {

            setVisible();

            AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setMessage("Validation error");
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return;
        } else if (resultDomain.getMessageId() == Messages.SUCCESFULL) {

            SharedPreferences sharedPreferences = this.getSharedPreferences(Constant.SHAREDPREFERENCES, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constant.TOKEN, resultDomain.getToken());
            editor.putLong(Constant.SIPUSERNAME, resultDomain.getSipUsername());
            editor.putString(Constant.SIPPASSWORD, resultDomain.getSipPassword());
            editor.putString(Constant.SIPREGISTRAR, resultDomain.getSipRegistrar());
            editor.putLong(Constant.ID, resultDomain.getId());
            editor.putString(Constant.FULLNAME, resultDomain.getFullName());
            editor.putString(Constant.EMAIL, resultDomain.getEmail());
            editor.putString(Constant.GENDER, resultDomain.getGender());
            editor.putString("LAST_EMAIL", emailAddress.getText().toString());
            editor.commit();

            editor.putString(Constant.PHOTO_NAME, resultDomain.getPhotoName());
            editor.commit();
            Intent intent2 = new Intent(this, SipService.class);
            this.startService(intent2);
            toPhotoActivity(this);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onErrorRegistration(Object object) {

        setVisible();

        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
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

    public static void toPhotoActivity(Context context) {

        Intent intent = new Intent(context, Photo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}