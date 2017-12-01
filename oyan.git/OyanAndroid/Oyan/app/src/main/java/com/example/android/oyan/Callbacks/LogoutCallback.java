package com.example.android.oyan.Callbacks;

/**
 * Created by Eldar on 11/19/2015.
 */
public interface LogoutCallback {

    void onResultLogout(Object result);

    void onBeginLogout();

    void onErrorLogout(Object error);
}