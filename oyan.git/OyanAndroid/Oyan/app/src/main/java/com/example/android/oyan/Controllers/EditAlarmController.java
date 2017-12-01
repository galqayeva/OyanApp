package com.example.android.oyan.Controllers;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.android.oyan.Adapters.AlarmListAdapter;
import com.example.android.oyan.Callbacks.EditAlarmCallback;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Domain.AlarmDomain;
import com.example.android.oyan.Domain.BaseDomain;
import com.example.android.oyan.Domain.Objects;
import com.example.android.oyan.Domain.RequetTag;
import com.example.android.oyan.Domain.SingletonVolleyQueue;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Eldar on 10/29/2015.
 */
public class EditAlarmController {

    public void editAlarms(final Context context, final Objects objects) {

        ((EditAlarmCallback) context).onBeginEditAlarm();
        JSONObject jsonObject = null;
        Gson gson = new Gson();
        try {
            jsonObject = new JSONObject(gson.toJson(objects));
        } catch (JSONException e) {
            e.printStackTrace();
        }
         JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, Constant.SERVER_URL + Constant.UPDATEALARM, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Gson gson = new Gson();

                        BaseDomain resultDomain = gson.fromJson(jsonObject.toString(), BaseDomain.class);
                        ((EditAlarmCallback) context).onResultEditAlarm(resultDomain);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        ((EditAlarmCallback) context).onErrorEditAlarm();
                    }
                });
        int socketTimeout = 5000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 1, 1);
        jsonObjectRequest.setRetryPolicy(policy);
        jsonObjectRequest.setTag(RequetTag.LIST_REQUESTS);
        SingletonVolleyQueue singletonVolleyQueue  = SingletonVolleyQueue.getInstance(context);
        singletonVolleyQueue.getRequestQueue().add(jsonObjectRequest);
    }

    public void isActive(final Context context, final AlarmDomain alarmDomain, final AlarmListAdapter alarmListAdapter) {

        JSONObject jsonObject = null;
        Gson gson = new Gson();
        try {
            jsonObject = new JSONObject(gson.toJson(alarmDomain));
        } catch (JSONException e) {
            e.printStackTrace();
        }
         JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, Constant.SERVER_URL + Constant.ISACTIVEALARM, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Gson gson = new Gson();

                        BaseDomain resultDomain = gson.fromJson(jsonObject.toString(), BaseDomain.class);
                        alarmListAdapter.onResultIsActive(resultDomain);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

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
