package com.example.android.oyan.Callbacks;

/**
 * Created by e on 2/15/16.
 */
public interface FriendsCallback {

    void onResultGetFriends(Object object);

    void onErrorGetFriends(Object object);

    void onResultDeleteFriend(Object object);

    void onErrorDeleteFriend(Object object);
}
