package com.example.android.oyan.Callbacks;


/**
 * Created by Eldar on 10/21/2015.
 */
public interface LoginCallback {
    void onResult(Object result);

    void onBegin();

    void onError(Object error);
}
