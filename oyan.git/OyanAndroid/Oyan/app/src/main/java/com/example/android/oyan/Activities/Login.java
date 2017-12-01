package com.example.android.oyan.Activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Controllers.LoginController;
import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.Callbacks.LoginCallback;
import com.example.android.oyan.Listeners.LoginInterface;
import com.example.android.oyan.Message.Messages;
import com.example.android.oyan.R;
import com.example.android.oyan.Services.SipService;
import com.example.android.oyan.BroadCastRecievers.TimeChange;

import java.util.Calendar;
import java.util.Locale;

import static android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;

public class Login extends AppCompatActivity implements LoginCallback, LoginInterface {

    private EditText emailAddress;
    private EditText password;

    public ImageView logoImage;
    public TextView logoText;
    public Button loginButton;

    RotateAnimation rotateAnimation;

    public void setInvisible() {
        emailAddress.setVisibility(View.INVISIBLE);
        password.setVisibility(View.INVISIBLE);
        loginButton.setVisibility(View.INVISIBLE);
        logoText.setVisibility(View.INVISIBLE);

        rotateAnimation = new RotateAnimation(0f, 350f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setDuration(700);
        logoImage.startAnimation(rotateAnimation);
    }

    public void setVisible() {
        emailAddress.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.VISIBLE);
        logoText.setVisibility(View.VISIBLE);
        logoImage.setAnimation(null);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Trebuchet MS.ttf");
        emailAddress = (EditText) this.findViewById(R.id.emailAddress);
        password = (EditText) this.findViewById(R.id.password);
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constant.SHAREDPREFERENCES, MODE_PRIVATE);
        emailAddress.setText(sharedPreferences.getString("LAST_EMAIL", ""));
        password.setTypeface(font);
        emailAddress.setTypeface(font);
        logoImage = (ImageView) this.findViewById(R.id.logo);
        logoText = ((TextView) this.findViewById(R.id.textView8));
        logoText.setTypeface(font);
        loginButton = ((Button) this.findViewById(R.id.loginButton));
        loginButton.setTypeface(font);

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    login(v);
                    return true;
                }
                return false;
            }
        });
        Calendar localTime = Calendar.getInstance(Locale.getDefault());
        int hour = localTime.getTime().getHours();
        RelativeLayout rL = (RelativeLayout) this.findViewById(R.id.LoginLayout);
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
        RelativeLayout rL = (RelativeLayout) this.findViewById(R.id.relativeLayoutLogin);
        if (hour > 6 && hour < 18) {
            rL.setBackgroundResource(R.drawable.bg1);
        } else if (hour > 18 && hour < 20) {
            rL.setBackgroundResource(R.drawable.bg2);
        } else {
            rL.setBackgroundResource(R.drawable.bg3);
        }

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void login(View view) {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


        SharedPreferences sharedPreferences = this.getSharedPreferences(Constant.SHAREDPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("LAST_EMAIL", emailAddress.getText().toString());
        editor.commit();

        String emailAddressText = emailAddress.getText().toString().trim();
        String passwordText = password.getText().toString().trim();
        if (emailAddressText == null
                || emailAddressText.isEmpty()
                || passwordText == null
                || passwordText.isEmpty()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this, THEME_DEVICE_DEFAULT_LIGHT);
            builder.setMessage("Email Address or Password is not a valid");
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
        UserDomain userDomain = new UserDomain();
        userDomain.setEmail(emailAddressText);
        userDomain.setPassword(passwordText);

        LoginController loginController = new LoginController();
        loginController.login(userDomain, this);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onResult(Object result) {
        try {

            if (!(result instanceof UserDomain)) {

                setVisible();
                return;
            }

            UserDomain resultDomain = (UserDomain) result;
            if (resultDomain == null || resultDomain.getMessageId() == Messages.DATA_ACCESS_LAYER_ISSUE) {

                setVisible();
                AlertDialog.Builder builder = new AlertDialog.Builder(this, THEME_DEVICE_DEFAULT_LIGHT);
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
            } else if (resultDomain.getMessageId() == Messages.DATA_VALIDATION_ERROR) {

                setVisible();
                AlertDialog.Builder builder = new AlertDialog.Builder(this, THEME_DEVICE_DEFAULT_LIGHT);
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
            } else if (resultDomain.getMessageId() == Messages.USER_NOT_EXSIST) {
                setVisible();
                AlertDialog.Builder builder = new AlertDialog.Builder(this, THEME_DEVICE_DEFAULT_LIGHT);
                builder.setMessage("Email or password is not correct.");
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
                editor.putString(Constant.PHOTO_NAME, resultDomain.getPhotoName());
                editor.putString(Constant.STATUS, resultDomain.getStatus());
                sharedPreferences.edit().putLong("INSERTONE", 100).commit();
                editor.commit();


                Log.e("login","login");
                Intent intent2 = new Intent(this, SipService.class);
                this.startService(intent2);

                IntentFilter intentFilter = new IntentFilter("android.intent.action.TIME_SET");
                intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
                getApplicationContext().registerReceiver(new TimeChange(), intentFilter);


                toMainActivity(this);
            }
        } catch (Exception e) {
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onError(Object error) {
        setVisible();
        AlertDialog.Builder builder = new AlertDialog.Builder(this, THEME_DEVICE_DEFAULT_LIGHT);
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

    @Override
    public void onBegin() {
        setInvisible();
    }

    @Override
    public void forgetPassword(View view) {
    }

    public static void toMainActivity(Context context) {

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
