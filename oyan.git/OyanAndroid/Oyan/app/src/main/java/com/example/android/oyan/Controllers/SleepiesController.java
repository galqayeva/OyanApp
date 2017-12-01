package com.example.android.oyan.Controllers;

import android.content.Context;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Domain.Objects;
import com.example.android.oyan.Fragments.Sleepies;
import com.example.android.oyan.Domain.RequetTag;
import com.example.android.oyan.Domain.SingletonVolleyQueue;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Eldar on 11/8/2015.
 */
public class SleepiesController {


    public void getSleepies(Objects objects, final Sleepies sleepies, final Context context) {
        JSONObject jsonObject = null;
        Gson gson = new Gson();
        try {

            jsonObject = new JSONObject(gson.toJson(objects));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, Constant.SERVER_URL + Constant.GETSLEEPIES, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject1) {

                Objects objects = null;
                JSONObject jsonObject = null;
                Gson gson = new Gson();
                try {
                   if( sleepies.progressBar != null ){
                       sleepies.progressBar.setVisibility(View.INVISIBLE);
                       sleepies.progressBar.setAnimation(null);
                   }
                    objects = gson.fromJson(jsonObject1.toString(), Objects.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                (sleepies).onResultGetSleepies(objects);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                sleepies.progressBar.setVisibility(View.INVISIBLE);
                sleepies.progressBar.setAnimation(null);
                (sleepies).onErrorGetSleepies(error);

            }
        });
        int socketTimeout = 5000;
        req.setTag(RequetTag.LIST_REQUESTS);
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 1, 1);
        req.setRetryPolicy(policy);
        SingletonVolleyQueue singletonVolleyQueue  = SingletonVolleyQueue.getInstance(context);
        singletonVolleyQueue.getRequestQueue().add(req);
    }

    public void refreshSleepies(Objects objects, final Sleepies sleepies, final Context context) {
        JSONObject jsonObject = null;
        Gson gson = new Gson();
        try {

            jsonObject = new JSONObject(gson.toJson(objects));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, Constant.SERVER_URL + Constant.REFRESHSLEEPIES,
                jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject1) {

                Objects objects = null;
                JSONObject jsonObject = null;
                Gson gson = new Gson();
                try {
                    sleepies.progressBar.setVisibility(View.INVISIBLE);
                    sleepies.progressBar.setAnimation(null);
                    objects = gson.fromJson(jsonObject1.toString(), Objects.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                (sleepies).onResultRefreshSleepies(objects);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                sleepies.progressBar.setVisibility(View.INVISIBLE);
                sleepies.progressBar.setAnimation(null);
                (sleepies).onErrorRefreshSleepies(error);

            }
        });
        int socketTimeout = 5000;
        req.setTag(RequetTag.LIST_REQUESTS);
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 1, 1);
        req.setRetryPolicy(policy);
        SingletonVolleyQueue singletonVolleyQueue  = SingletonVolleyQueue.getInstance(context);
        singletonVolleyQueue.getRequestQueue().add(req);
    }

}