package com.example.android.oyan.Controllers;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.android.oyan.Callbacks.ApplicationCallback;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.Domain.RequetTag;
import com.example.android.oyan.Domain.SingletonVolleyQueue;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Eldar on 10/21/2015.
 */
public class ApplicationController {


    public void startApp(final Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SHAREDPREFERENCES, context.MODE_PRIVATE);
        UserDomain userDomain = new UserDomain();
        userDomain.setToken(sharedPreferences.getString(Constant.TOKEN, null));

        if (userDomain.getToken() != null) {

            JSONObject jsonObject = null;
            try {
                Gson gson = new Gson();
                jsonObject = new JSONObject(gson.toJson(userDomain));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest =
                    new JsonObjectRequest(Request.Method.POST, Constant.SERVER_URL + Constant.GETUSERBYTOKEN, jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {

                                    UserDomain resultDomain = new UserDomain();
                                    Gson gson = new Gson();
                                    resultDomain = gson.fromJson(jsonObject.toString(), UserDomain.class);
                                    ((ApplicationCallback) context).onResult(resultDomain);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            ((ApplicationCallback) context).onError(volleyError);
                        }
                    });
            int socketTimeout = 8000;
            jsonObjectRequest.setTag(RequetTag.LIST_REQUESTS);
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 1, 1);
            jsonObjectRequest.setRetryPolicy(policy);
            SingletonVolleyQueue singletonVolleyQueue  = SingletonVolleyQueue.getInstance(context);
            singletonVolleyQueue.getRequestQueue().add(jsonObjectRequest);
        } else {

            ((ApplicationCallback) context).onFinal();
        }

    }
}
