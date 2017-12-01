package com.example.android.oyan.Controllers;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.android.oyan.Callbacks.MyProfileCallback;
import com.example.android.oyan.Callbacks.UserSettingsCallback;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.Fragments.MyProfile;
import com.example.android.oyan.Fragments.UserSettings;
import com.example.android.oyan.Listeners.MyProfileInterface;
import com.example.android.oyan.Domain.RequetTag;
import com.example.android.oyan.Domain.SingletonVolleyQueue;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Eldar on 11/12/2015.
 */
public class MyProfileController implements MyProfileInterface {


    @Override
    public void editProfile(Context context, final UserSettings myProfile, UserDomain userDomain) {

        myProfile.onBeginEditProfile();
        JSONObject jsonObject = null;
        try {
            Gson gson = new Gson();
            jsonObject = new JSONObject(gson.toJson(userDomain));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, Constant.SERVER_URL + Constant.UPDATEUSER, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {

                                Gson gson = new Gson();
                                UserDomain resultDomain = gson.fromJson(jsonObject.toString(), UserDomain.class);
                                ((MyProfileCallback) myProfile).onResultEditProfile(resultDomain);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        ((MyProfileCallback) myProfile).onErrorEditProfile(volleyError);
                    }
                });
        int socketTimeout = 5000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 1, 1);
        jsonObjectRequest.setRetryPolicy(policy);
        jsonObjectRequest.setTag(RequetTag.LIST_REQUESTS);
        SingletonVolleyQueue singletonVolleyQueue = SingletonVolleyQueue.getInstance(context);
        singletonVolleyQueue.getRequestQueue().add(jsonObjectRequest);

    }


    public void getUser(Context context, final MyProfile myProfile, UserDomain userDomain) {


        JSONObject jsonObject = null;
        try {
            Gson gson = new Gson();
            jsonObject = new JSONObject(gson.toJson(userDomain));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SingletonVolleyQueue singletonVolleyQueue = SingletonVolleyQueue.getInstance(context);

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, Constant.SERVER_URL + Constant.GETUSER, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {

                                Gson gson = new Gson();
                                UserDomain resultDomain = gson.fromJson(jsonObject.toString(), UserDomain.class);
                                ((UserSettingsCallback) myProfile).onGetUserResult(resultDomain);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {


                    }
                });
        int socketTimeout = 5000;
        jsonObjectRequest.setTag(RequetTag.LIST_REQUESTS);
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 1, 1);
        jsonObjectRequest.setRetryPolicy(policy);
        singletonVolleyQueue.getRequestQueue().add(jsonObjectRequest);

    }
}
