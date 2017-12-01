package com.example.android.oyan.Callbacks;

/**
 * Created by Eldar on 10/21/2015.
 */
public interface ApplicationCallback {

    public void onResult(Object object);

    public void onError(Object object);

    public void onFinal();
}
