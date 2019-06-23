package com.example.mirutapp.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.mirutapp.MiRutAppApplication;
import com.example.mirutapp.Repository.RouteRepository;

import javax.inject.Inject;

public class RoutesAlarmReceiver extends BroadcastReceiver {
    public static final String TAG = "RoutesAlarmReceiver";
    private Context appContext;
    @Inject RouteRepository routeRepository;

    @Override
    public void onReceive(Context context, Intent intent) {
        ((MiRutAppApplication) context.getApplicationContext())
                .getApplicationComponent()
                .inject(this);
        appContext = context;
        Log.d(TAG, "routes alarm received");

        //get routeId to set alarms to a route
        //intent.getIntExtra("routeId", -1);
    }
}
