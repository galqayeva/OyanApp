package org.pjsip.pjsua2.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Activity;
import android.widget.Toast;

import com.example.android.oyan.Activities.Logout;
import com.example.android.oyan.Activities.MainActivity;
import com.example.android.oyan.Controllers.WakeUpController;
import com.example.android.oyan.Services.AlarmManagerService;
import com.example.android.oyan.Callbacks.CallCallback;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Controllers.CallController;
import com.example.android.oyan.Domain.Objects;
import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.Message.Messages;
import com.example.android.oyan.R;
import com.example.android.oyan.Domain.Sip;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.pjsip.pjsua2.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class CallActivity extends Activity implements Handler.Callback, SensorEventListener, CallCallback {


    class MSG_TYPE {

        public final static int INCOMING_CALL = 1;
        public final static int CALL_STATE = 2;
        public final static int REG_STATE = 3;
        public final static int BUDDY_STATE = 4;
        public final static int CALL_MEDIA_STATE = 5;
    }

    public static boolean isAccepted = false;
    public static String photoName = "";
    public static String photoUrl = "";
    public static long callerID;
    public String Uname = "";
    public static Handler handler_;
    private final Handler handler = new Handler(this);
    private static CallInfo lastCallInfo;
    private TextView tvPeer;
    public static String callerName = "";
    SensorManager sensorManager;
    android.hardware.Sensor mProximity;
    private PowerManager powerManager;
    private PowerManager.WakeLock wakeLock;
    public static boolean isFriend = true;

    public static CallActivity c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        c = this;
        Calendar localTime = Calendar.getInstance(Locale.getDefault());
        int hour = localTime.getTime().getHours();
        RelativeLayout rL = (RelativeLayout) this.findViewById(R.id.callLayout);
        if (hour > 6 && hour < 18) {
            rL.setBackgroundResource(R.drawable.bg1);
        } else if (hour > 18 && hour < 20) {
            rL.setBackgroundResource(R.drawable.bg2);
        } else {
            rL.setBackgroundResource(R.drawable.bg3);
        }

        powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(0x00000020, getLocalClassName());
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = sensorManager.getDefaultSensor(android.hardware.Sensor.TYPE_PROXIMITY);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        tvPeer = (TextView) findViewById(R.id.textViewPeer);
        try {

            String x = getIntent().getStringExtra("isFriend");
            if (x == null) x = "1";
            if (Long.valueOf(x) == -1) {

                isFriend = false;
            } else {

                isFriend = true;
            }
            photoUrl = getIntent().getStringExtra("img");
            Uname = getIntent().getStringExtra("text");
        } catch (Exception e) {
        }

        ImageView buttonShowPreview = (ImageView) findViewById(R.id.buttonShowPreview);
        if (Sip.currentCall == null || Sip.currentCall.vidWin == null) {
// this has changed
            buttonShowPreview.setVisibility(View.INVISIBLE);
        }
        handler_ = handler;

        if (Sip.currentCall != null) {

            try {
                lastCallInfo = Sip.currentCall.getInfo();
                updateCallState(lastCallInfo);
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            if (lastCallInfo != null)
                updateCallState(lastCallInfo);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = -1;
        if (wakeLock.isHeld())
            wakeLock.release();
        getWindow().setAttributes(params);

        callIsOn = false;
        if (Notify.mediaPlayer != null && Notify.mediaPlayer.isPlaying())
            Notify.mediaPlayer.stop();
        CallOpParam c = new CallOpParam();
        c.setStatusCode(pjsip_status_code.PJSIP_SC_GONE);
        if (Sip.currentCall != null && Sip.currentCall.isActive())
            try {
                Sip.currentCall.hangup(c);
            } catch (Exception e) {
                e.printStackTrace();
            }
        sensorManager.unregisterListener(this);
    }

    public void acceptCall(View view) {

        if (Notify.mediaPlayer != null && Notify.mediaPlayer.isPlaying()) Notify.mediaPlayer.stop();
        findViewById(R.id.buttonShowPreview).setVisibility(View.GONE);
        AlarmManagerService.lastCallTime = Calendar.getInstance();
        callIsOn = true;
        CallOpParam prm = new CallOpParam();
        prm.setStatusCode(pjsip_status_code.PJSIP_SC_ACCEPTED);
        try {
            SendInstantMessageParam p = new SendInstantMessageParam();
            p.setContent("hello are you hearing me now ?");
            Sip.currentCall.sendInstantMessage(p);
            Sip.currentCall.answer(prm);


        } catch (Exception e) {
            System.out.println(e);
        }
        view.setVisibility(View.GONE);

        try {
            isAccepted = true;
            send();
            UserDomain userDomain1 = new UserDomain();
            SharedPreferences sharedPreferences = getSharedPreferences(Constant.SHAREDPREFERENCES, 0);
            userDomain1.setId(sharedPreferences.getLong(Constant.ID, 0));
            String token = sharedPreferences.getString(Constant.TOKEN, null);
            userDomain1.setToken(token);
            WakeUpController cc = new WakeUpController();
            cc.updateAcceptCall(this, userDomain1);
            UserDomain userDomain2 = new UserDomain();
            userDomain2.setToken(token);
            userDomain2.setId(callerID);
            cc.updateIncomingCall(this, userDomain2);
        } catch (Exception e2) {
        }
    }

    public void hangupCall(View view) {


        if (Sip.currentCall != null) {

            callIsOn = false;
            CallOpParam prm = new CallOpParam();
            if (Notify.mediaPlayer != null && Notify.mediaPlayer.isPlaying())
                Notify.mediaPlayer.stop();
            prm.setStatusCode(pjsip_status_code.PJSIP_SC_BUSY_EVERYWHERE);
            try {

                Sip.currentCall.hangup(prm);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        finish();
    }

    @Override
    public boolean handleMessage(Message m) {

        if (m.what == MSG_TYPE.CALL_STATE) {

            lastCallInfo = (CallInfo) m.obj;
            updateCallState(lastCallInfo);
        } else return false;

        return true;
    }

    String call_state;
    ImageView userPhoto = null;

    private void updateCallState(CallInfo ci) {


        TextView tvState = (TextView) findViewById(R.id.textViewCallState);
        TextView tvs = tvState;
        ImageView buttonHangup = (ImageView) findViewById(R.id.buttonHangup);
        ImageView buttonAccept = (ImageView) findViewById(R.id.buttonAccept);
        this.userPhoto = (ImageView) findViewById(R.id.userPhoto);
        if (ci.getRole() == pjsip_role_e.PJSIP_ROLE_UAC) {
            buttonAccept.setVisibility(View.GONE);
        }

        if (ci.getState().swigValue() < pjsip_inv_state.PJSIP_INV_STATE_CONFIRMED.swigValue()) {

            this.tvPeer.setText(callerName);
            //if (photoName != null) {
                Picasso.with(this).load(Constant.SERVER_URL_PHOTO + photoName).
                        placeholder(R.drawable.userph).into(this.userPhoto);

            if (ci.getRole() == pjsip_role_e.PJSIP_ROLE_UAS) {
                this.call_state = "Incoming Call";
            } else {
                if (photoUrl != null) {
                    Picasso.with(getApplicationContext()).load(photoUrl).placeholder(R.drawable.userph).into(this.userPhoto);
                }
                findViewById(R.id.buttonShowPreview).setVisibility(View.GONE);
                this.tvPeer.setText(Uname);
                this.call_state = "Calling";
                buttonHangup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            callIsOn = false;
                            CallOpParam param = new CallOpParam();
                            param.setStatusCode(pjsip_status_code.PJSIP_SC_GONE);
                            Sip.currentCall.hangup(param);
                            handler_ = null;
                        } catch (Exception e) {

                        }
                        finish();
                    }
                });
            }
        }  else if (ci.getState().swigValue() >= pjsip_inv_state.PJSIP_INV_STATE_CONFIRMED.swigValue()) {

            buttonAccept.setVisibility(View.GONE);

            final Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            if (ci.getState() == pjsip_inv_state.PJSIP_INV_STATE_CONFIRMED) {
                callIsOn = true;
                buttonHangup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            callIsOn = false;
                            CallOpParam param = new CallOpParam();
                            param.setStatusCode(pjsip_status_code.PJSIP_SC_GONE);
                            Sip.currentCall.hangup(param);
                            handler_ = null;
                        } catch (Exception e) {
                        }
                        finish();
                    }
                });
                call_state = "Call continues";
            } else if (ci.getState() == pjsip_inv_state.PJSIP_INV_STATE_DISCONNECTED) {
                if (Notify.mediaPlayer != null && Notify.mediaPlayer.isPlaying()) {
                    Notify.mediaPlayer.stop();
                }
                callIsOn = false;
                call_state = "Call finished";
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);
                buttonHangup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            }
        }
        tvState.setText(call_state);
    }

    public void showPreview(View view) {

        try {
            handler_ = null;
            finish();
            if (Sip.currentCall != null) {
                CallOpParam prm = new CallOpParam();
                prm.setStatusCode(pjsip_status_code.PJSIP_SC_DECLINE);
                ;
                try {
                    Sip.currentCall.hangup(prm);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.values[0] == 0) {
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.screenBrightness = 0;
            if (!wakeLock.isHeld() && callIsOn == true) wakeLock.acquire();
            getWindow().setAttributes(params);
        } else {

            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.screenBrightness = -1;
            if (wakeLock.isHeld())
                wakeLock.release();
            getWindow().setAttributes(params);
        }
    }

    @Override
    public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {

    }

    public void send() {

        UserDomain userDomain1 = new UserDomain();
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.SHAREDPREFERENCES, 0);
        userDomain1.setId(sharedPreferences.getLong(Constant.ID, 0));
        userDomain1.setToken(sharedPreferences.getString(Constant.TOKEN, null));
        UserDomain userDomain2 = new UserDomain();
        userDomain2.setId(callerID);
        Objects objects = new Objects();
        ArrayList<UserDomain> l = new ArrayList();
        l.add(userDomain1);
        l.add(userDomain2);
        objects.setUserDomains(l);
        new CallController().makeFavorite(this, objects);
    }

    @Override
    public void onResultFriend(UserDomain userDomain) {

        if (userDomain.getMessageId() == Messages.SUCCESFULL) {
            if (userDomain.getPhotoName() != null)
                Picasso.with(this).load(Constant.SERVER_URL_PHOTO + userDomain.getPhotoName())
                        .memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).placeholder(R.drawable.man).into(userPhoto);
        } else if (userDomain.getMessageId() == Messages.AUTHORIZATION_ERROR) {
            Toast.makeText(this, "AUTHORIZATION_ERROR", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Logout.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Server error", Toast.LENGTH_LONG).show();

        }

    }

    public static boolean callIsOn = false;


}