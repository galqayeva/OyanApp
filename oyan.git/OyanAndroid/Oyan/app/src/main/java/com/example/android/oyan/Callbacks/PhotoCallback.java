package com.example.android.oyan.Callbacks;

/**
 * Created by Eldar on 10/22/2015.
 */
public interface PhotoCallback {

    public void onBeginPhotoUpload();

    public void onResultPhotoUpload(Object object);

    public void onErrorPhotoUpload(Object object);
}
