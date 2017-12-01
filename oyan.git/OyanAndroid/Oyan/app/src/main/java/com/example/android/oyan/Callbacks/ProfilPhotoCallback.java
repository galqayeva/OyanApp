package com.example.android.oyan.Callbacks;

/**
 * Created by Eldar on 11/13/2015.
 */
public interface ProfilPhotoCallback {

    public void onBeginPhotoUpload();

    public void onResultPhotoUpload(Object object);

    public void onErrorPhotoUpload(Object object);
}
