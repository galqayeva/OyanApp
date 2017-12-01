package com.example.android.oyan.Listeners;

import android.content.Context;

import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.Fragments.MyProfile;
import com.example.android.oyan.Fragments.UserSettings;

/**
 * Created by Eldar on 11/12/2015.
 */
public interface MyProfileInterface {

    public void editProfile(Context context, final UserSettings myProfile, UserDomain userDomain);
    public void getUser(Context context, final MyProfile myProfile, UserDomain userDomain);
}
