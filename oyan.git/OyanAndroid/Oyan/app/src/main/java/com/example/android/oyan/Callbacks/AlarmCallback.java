package com.example.android.oyan.Callbacks;

/**
 * Created by Eldar on 10/28/2015.
 */
public interface AlarmCallback {

    public void onBeginGetAlarms();

    public void onResultGetAlarms(Object object);

    public void onErrorGetAlarms();

    public void onBeginDeleteAlarm();

    public void onErrorDeleteAlarm();

    public void onResultDeleteAlarm(Object object);
}
