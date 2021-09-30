package com.codershil.covid19tracker.controller;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleTon {

    private static VolleySingleTon volleySingleTon;
    private RequestQueue requestQueue;
    private Context context;

    private VolleySingleTon(Context context) {
        this.context = context;
        this.requestQueue = getRequestQueue();

    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized VolleySingleTon getInstance(Context context) {
        if (volleySingleTon == null) {
            volleySingleTon = new VolleySingleTon(context);
        }
        return volleySingleTon;
    }

    public <T> void addToRequestQue(Request<T> request) {
        requestQueue.add(request);
    }
}
