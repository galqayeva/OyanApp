package com.example.android.oyan.Controllers;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Domain.Objects;
import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.Fragments.Friends;
import com.example.android.oyan.Domain.RequetTag;
import com.example.android.oyan.Domain.SingletonVolleyQueue;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by e on 2/15/16.
 */
public class FriendsController {

    public void getFriends(Objects objects, final Friends friends, final Context context) {

        JSONObject jsonObject = null;
        Gson gson = new Gson();
        try {

            jsonObject = new JSONObject(gson.toJson(objects));
        } catch (JSONException e) {
            e.printStackTrace();
        }
         JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, Constant.SERVER_URL + Constant.GETFRIENDS, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject1) {

                Objects objects = null;
                JSONObject jsonObject = null;
                Gson gson = new Gson();
                try {

                    objects = gson.fromJson(jsonObject1.toString(), Objects.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                (friends).onResultGetFriends(objects);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                (friends).onErrorGetFriends(error);

            }
        });
        int socketTimeout = 4000;
        req.setTag(RequetTag.LIST_REQUESTS);
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 1, 1);
        req.setRetryPolicy(policy);
        SingletonVolleyQueue singletonVolleyQueue  = SingletonVolleyQueue.getInstance(context);
        singletonVolleyQueue.getRequestQueue().add(req);



    }


    public void deleteFriend(final Context context,final Friends friends, final Objects obj) {
        JSONObject jsonObject = null;
        Gson gson = new Gson();
        try {
            jsonObject = new JSONObject(gson.toJson(obj));
        } catch (JSONException e) {
            e.printStackTrace();
        }

         JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, Constant.SERVER_URL + Constant.DELETEFRIEND, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        Gson gson = new Gson();
                        UserDomain userDomain1 = gson.fromJson(jsonObject.toString(), UserDomain.class);
                        friends.onResultDeleteFriend(userDomain1);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        friends.onErrorDeleteFriend(volleyError);
                    }
                });
        int socketTimeout = 5000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 1, 1);
        jsonObjectRequest.setRetryPolicy(policy);
        jsonObjectRequest.setTag(RequetTag.LIST_REQUESTS);
        SingletonVolleyQueue singletonVolleyQueue  = SingletonVolleyQueue.getInstance(context);
        singletonVolleyQueue.getRequestQueue().add(jsonObjectRequest);
    }

}
