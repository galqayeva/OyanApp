package com.example.android.oyan.Controllers;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.android.oyan.Callbacks.PhotoCallback;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.Domain.RequetTag;
import com.example.android.oyan.Domain.SingletonVolleyQueue;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Eldar on 10/22/2015.
 */
public class PhotoController {

    public void photoUpload(final Context context, final UserDomain userDomain) {

        ((PhotoCallback) context).onBeginPhotoUpload();
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
                                ((PhotoCallback) context).onResultPhotoUpload(resultDomain);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        ((PhotoCallback) context).onErrorPhotoUpload(volleyError);
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
