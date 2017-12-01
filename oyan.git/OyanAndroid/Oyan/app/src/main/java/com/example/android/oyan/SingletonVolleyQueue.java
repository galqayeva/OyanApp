package com.example.android.oyan;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class SingletonVolleyQueue {


    private static SingletonVolleyQueue mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;


    private SingletonVolleyQueue(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized SingletonVolleyQueue getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SingletonVolleyQueue(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
