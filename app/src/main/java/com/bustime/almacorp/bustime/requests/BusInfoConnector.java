package com.bustime.almacorp.bustime.requests;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class BusInfoConnector {

    private static BusInfoConnector bInstance;
    private static Context bContext;

    private RequestQueue bRequestQueue;

    private BusInfoConnector(Context context) {
        bContext = context;
        bRequestQueue = getRequestQueue();
    }

    public static synchronized BusInfoConnector getInstance(Context context) {
        if (bInstance == null) {
            bInstance = new BusInfoConnector(context);
        }
        return bInstance;
    }

    public RequestQueue getRequestQueue() {
        if (bRequestQueue == null) {
            bRequestQueue = Volley.newRequestQueue(bContext.getApplicationContext());
        }
        return bRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
