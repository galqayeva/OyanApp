package com.example.android.oyan.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.oyan.Listeners.EntryInterface;
import com.example.android.oyan.R;

import java.util.Calendar;
import java.util.Locale;

public class Entry extends AppCompatActivity implements EntryInterface {

    int t = 1;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

//
//        for (int i = 0; i < grantResults.length; i++) {
//
//            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
//
//                t = 0;
//            }
//        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_entry);
        Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Trebuchet MS.ttf");
        ((Button) this.findViewById(R.id.signUp)).setTypeface(font);
        ((Button) this.findViewById(R.id.signIn)).setTypeface(font);
        ((TextView) this.findViewById(R.id.welcome)).setTypeface(font);
        Calendar localTime = Calendar.getInstance(Locale.getDefault());
        int hour = localTime.getTime().getHours();
        RelativeLayout rL = (RelativeLayout) this.findViewById(R.id.layoutEntry);
        if (hour > 6 && hour < 18) {
            rL.setBackgroundResource(R.drawable.bg1);
        } else if (hour > 18 && hour < 20) {
            rL.setBackgroundResource(R.drawable.bg2);
        } else {
            rL.setBackgroundResource(R.drawable.bg3);
        }
        check();


}

    @Override
    protected void onRestart() {
        super.onRestart();
        Calendar localTime = Calendar.getInstance(Locale.getDefault());
        int hour = localTime.getTime().getHours();
        RelativeLayout rL = (RelativeLayout) this.findViewById(R.id.layoutEntry);
        if (hour > 6 && hour < 18) {
            rL.setBackgroundResource(R.drawable.bg1);
        } else if (hour > 18 && hour < 20) {
            rL.setBackgroundResource(R.drawable.bg2);
        } else {
            rL.setBackgroundResource(R.drawable.bg3);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void login(View view) {
        if (check()) {
            Intent intent = new Intent(this, Login.class);
            this.startActivity(intent);
        } else {
            AlertDialog a = new AlertDialog.Builder(this).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            }).setMessage("Permissions required to continue").show();
        }
    }

    @Override
    public void registration(View view) {

        if (check()) {
            Intent intent = new Intent(this, Registration.class);
            this.startActivity(intent);
        } else {
            AlertDialog a = new AlertDialog.Builder(this).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            }).setMessage("Permissions required to continue").show();
        }
    }


    public boolean check() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.PROCESS_OUTGOING_CALLS) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.PROCESS_OUTGOING_CALLS,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
            }, 1);
            return false;
        } else {
            return true;
        }
    }
}
