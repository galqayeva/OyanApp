package com.example.android.oyan.Controllers;

import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.android.oyan.Callbacks.CallCallback;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Domain.Objects;
import com.example.android.oyan.Domain.Sip;
import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.Message.Messages;
import com.example.android.oyan.Domain.RequetTag;
import com.example.android.oyan.Domain.SingletonVolleyQueue;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.pjsip.pjsua2.app.CallActivity;
import org.pjsip.pjsua2.app.Notify;

/**
 * Created by eldarh on 12/21/15.
 */
public class CallController {

    public void getUserBySipID(final Context context, final UserDomain userDomain) {
        JSONObject jsonObject = null;
        Gson gson = new Gson();
        try {
            jsonObject = new JSONObject(gson.toJson(userDomain));
        } catch (JSONException e) {
            e.printStackTrace();
        }

         JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, Constant.SERVER_URL + Constant.GETUSERBYSIPID, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Gson gson = new Gson();
                        UserDomain userDomain1 = gson.fromJson(jsonObject.toString(), UserDomain.class);
                        if (userDomain1.getMessageId() == Messages.SUCCESFULL)
                            CallActivity.callerName = userDomain1.getFullName();
                        CallActivity.photoName = userDomain1.getPhotoName();
                        CallActivity.callerID = userDomain1.getId();
                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                        Notify.mediaPlayer =  RingtoneManager.getRingtone(context, notification);
                        Notify.mediaPlayer.play();
                        Intent intent1 = new Intent(context, CallActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent1);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (Sip.currentCall != null) {
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                            Notify.mediaPlayer =  RingtoneManager.getRingtone(context, notification);
                            Notify.mediaPlayer.play();
                            CallActivity.callerName = "Unknown user";
                            Intent intent1 = new Intent(context, CallActivity.class);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent1);
                        }
                    }
                });
        int socketTimeout = 2000;
        jsonObjectRequest.setTag(RequetTag.LIST_REQUESTS);
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 1, 1);
        jsonObjectRequest.setRetryPolicy(policy);
        SingletonVolleyQueue singletonVolleyQueue  = SingletonVolleyQueue.getInstance(context);
        singletonVolleyQueue.getRequestQueue().add(jsonObjectRequest);
    }



    public void makeFavorite(final Context context, final Objects obj) {
        JSONObject jsonObject = null;
        Gson gson = new Gson();
        try {
            jsonObject = new JSONObject(gson.toJson(obj));
        } catch (JSONException e) {
            e.printStackTrace();
        }

         JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, Constant.SERVER_URL + Constant.MAKEFRIEND, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        Gson gson = new Gson();
                        UserDomain userDomain1 = gson.fromJson(jsonObject.toString(), UserDomain.class);
                        ((CallCallback)context).onResultFriend(userDomain1);
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
        SingletonVolleyQueue singletonVolleyQueue  = SingletonVolleyQueue.getInstance(context);
        singletonVolleyQueue.getRequestQueue().add(jsonObjectRequest);
    }

}
