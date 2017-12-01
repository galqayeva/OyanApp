package com.example.android.oyan.Controllers;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.Callbacks.LoginCallback;
import com.example.android.oyan.Domain.RequetTag;
import com.example.android.oyan.Domain.SingletonVolleyQueue;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Eldar on 10/19/2015.
 */
public class LoginController {

    public Context context = null;
    private UserDomain resultDomain = null;

    public void login(UserDomain userDomain, final Context context) {

        ((LoginCallback) context).onBegin();
        resultDomain = new UserDomain();
        this.context = context;
        JSONObject jsonObject = null;
        try {
            Gson gson = new Gson();
            jsonObject = new JSONObject(gson.toJson(userDomain));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SingletonVolleyQueue singletonVolleyQueue  = SingletonVolleyQueue.getInstance(context);

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, Constant.SERVER_URL + Constant.LOGIN, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {

                                Gson gson = new Gson();
                                resultDomain = gson.fromJson(jsonObject.toString(), UserDomain.class);
                                ((LoginCallback) context).onResult(resultDomain);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        ((LoginCallback) context).onError(volleyError);
                    }
                });
        int socketTimeout = 5000;
        jsonObjectRequest.setTag(RequetTag.LIST_REQUESTS);
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 1, 1);
        jsonObjectRequest.setRetryPolicy(policy);
        singletonVolleyQueue.getRequestQueue().add(jsonObjectRequest);

    }
}
