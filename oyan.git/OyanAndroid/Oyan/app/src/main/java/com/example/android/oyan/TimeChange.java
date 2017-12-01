package com.example.android.oyan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.android.oyan.Services.SipService;

public class TimeChange extends BroadcastReceiver {
    public TimeChange() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {


        Intent intent2 = new Intent(context, AlarmManagerService.class);
        context.startService(intent2);
    }
}
