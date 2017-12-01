package com.example.android.oyan.Controllers;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.android.oyan.Callbacks.ProfilPhotoCallback;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.Domain.RequetTag;
import com.example.android.oyan.Domain.SingletonVolleyQueue;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Eldar on 11/13/2015.
 */
public class ProfilPhotoController {

    public void photoUpload(final Context context, final UserDomain userDomain) {

        ((ProfilPhotoCallback) context).onBeginPhotoUpload();
        JSONObject jsonObject = null;
        try {
            Gson gson = new Gson();
            jsonObject = new JSONObject(gson.toJson(userDomain));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, Constant.SERVER_URL + Constant.PHOTOUPLOAD, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {

                                Gson gson = new Gson();

                                UserDomain resultDomain = gson.fromJson(jsonObject.toString(), UserDomain.class);
                                Log.v("Birinci", "Now" + " --  " + resultDomain.getPhotoName());
                                ((ProfilPhotoCallback) context).onResultPhotoUpload(resultDomain);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.v("Ikinci", "Now");
                        ((ProfilPhotoCallback) context).onErrorPhotoUpload(volleyError);
                    }
                });
        int socketTimeout = 15000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 1, 1);
        jsonObjectRequest.setRetryPolicy(policy);
        jsonObjectRequest.setTag(RequetTag.LIST_REQUESTS);
        SingletonVolleyQueue singletonVolleyQueue  = SingletonVolleyQueue.getInstance(context);
        singletonVolleyQueue.getRequestQueue().add(jsonObjectRequest);
    }
}
