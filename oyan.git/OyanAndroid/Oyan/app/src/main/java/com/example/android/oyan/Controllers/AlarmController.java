package com.example.android.oyan.Controllers;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.android.oyan.Callbacks.AddAlarmCallback;
import com.example.android.oyan.Callbacks.AlarmCallback;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Domain.BaseDomain;
import com.example.android.oyan.Domain.Objects;
import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.Fragments.Alarms;
import com.example.android.oyan.Domain.RequetTag;
import com.example.android.oyan.Domain.SingletonVolleyQueue;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Eldar on 10/28/2015.
 */
public class AlarmController {

    public void getAlarms(final Context context, final UserDomain userDomain, final Alarms alarms) {
        ((AlarmCallback) alarms).onBeginGetAlarms();
        JSONObject jsonObject = null;
        Gson gson = new Gson();
        try {
            jsonObject = new JSONObject(gson.toJson(userDomain));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, Constant.SERVER_URL + Constant.GETALARMS, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Gson gson = new Gson();
                        Objects objects = gson.fromJson(jsonObject.toString(), Objects.class);
                        alarms.onResultGetAlarms(objects);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        alarms.onErrorGetAlarms();
                    }
                });
        int socketTimeout = 4000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 1, 1);
        jsonObjectRequest.setTag(RequetTag.LIST_REQUESTS);
        jsonObjectRequest.setRetryPolicy(policy);
        SingletonVolleyQueue singletonVolleyQueue = SingletonVolleyQueue.getInstance(context);
        singletonVolleyQueue.getRequestQueue().add(jsonObjectRequest);
    }

    public void deleteAlarm(final Context context, final Objects objects, final Alarms alarmsObject) {

        ((AlarmCallback) alarmsObject).onBeginDeleteAlarm();
        JSONObject jsonObject = null;
        Gson gson = new Gson();
        try {
            jsonObject = new JSONObject(gson.toJson(objects));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, Constant.SERVER_URL + Constant.DELETEALARM, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Gson gson = new Gson();

                        BaseDomain resultDomain = gson.fromJson(jsonObject.toString(), BaseDomain.class);
                        alarmsObject.onResultDeleteAlarm(resultDomain);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.v("Errror is ", volleyError.getMessage());
                        alarmsObject.onErrorDeleteAlarm();
                    }
                });
        int socketTimeout = 4000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 1, 1);
        jsonObjectRequest.setTag(RequetTag.LIST_REQUESTS);
        jsonObjectRequest.setRetryPolicy(policy);
        SingletonVolleyQueue singletonVolleyQueue = SingletonVolleyQueue.getInstance(context);
        singletonVolleyQueue.getRequestQueue().add(jsonObjectRequest);
    }

    public void addAlarm(final Context context, final Objects objects) {

        ((AddAlarmCallback) context).onBeginAddAlarm();
        JSONObject jsonObject = null;
        Gson gson = new Gson();
        try {
            jsonObject = new JSONObject(gson.toJson(objects));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, Constant.SERVER_URL + Constant.ADDALARM, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Gson gson = new Gson();

                        BaseDomain resultDomain = gson.fromJson(jsonObject.toString(), BaseDomain.class);
                        ((AddAlarmCallback) context).onResultAddAlarm(resultDomain);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        ((AddAlarmCallback) context).onErrorAddAlarm();
                    }
                });
        int socketTimeout = 4000;
        jsonObjectRequest.setTag(RequetTag.LIST_REQUESTS);
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 1, 1);
        jsonObjectRequest.setRetryPolicy(policy);
        SingletonVolleyQueue singletonVolleyQueue = SingletonVolleyQueue.getInstance(context);
        singletonVolleyQueue.getRequestQueue().add(jsonObjectRequest);
    }

}