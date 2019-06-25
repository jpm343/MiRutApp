package com.example.mirutapp.Services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.mirutapp.MiRutAppApplication;
import com.example.mirutapp.Model.Route;
import com.example.mirutapp.Repository.RouteRepository;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

public class RoutesAlarmSetterJobService extends JobService {
    public static final String TAG = "RoutesAlarmSetterJobService";
    private boolean jobCanelled = false;
    @Inject
    RouteRepository repository;
    @Override
    public boolean onStartJob(JobParameters params) {
        // Dependency injection in order to get the routes repository
        ((MiRutAppApplication) getApplicationContext())
                .getApplicationComponent()
                .inject(this);

        //first check if we are doing a reset from boot receiver (when rebooting the device)
        String resetFromReboot = params.getExtras().getString("ResetAlarms");
        if(resetFromReboot != null && resetFromReboot.equals("true")) {
            //do job in background
            this.resetAlarmsInBackground();

            //finally return to avoid doing unnecessary job
            return true;
        }

        Log.d(TAG, "Job started");
        this.doBackGroundWork(params);
        return true;
    }

    private void doBackGroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //do work here
                String json = params.getExtras().getString("Route");
                Gson gson = new Gson();
                Route route = gson.fromJson(json, Route.class);

                //save route
                repository.createRouteInBackGround(route);

                //set alarms
                setRouteAlarm(route);

                Log.d(TAG, "Job finished");
                jobFinished(params, false);
            }
        }).start();
    }

    private void resetAlarmsInBackground() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //check all routes from repository and set their alarms
                List<Route> routes = repository.getAllRoutesInBackGround();
                for(Route route: routes) {
                    Log.d(TAG, "Setting alarm for: "+route.getRouteName());
                    setRouteAlarm(route);
                }
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion");
        jobCanelled = true;
        return false;
    }

    private void setRouteAlarm(Route route){
        Context context = getApplicationContext();
        Intent intent = new Intent(context, RoutesAlarmReceiver.class);

        //put id to check repository in receiver class
        intent.putExtra("RouteId", route.getId());

        //debug
        Log.d(TAG+" routeId", String.valueOf(route.getId()));

        // Here we should verify if the alarm is created or not
        //cancelAlarmIfExists(context, route.getId(), intent);

        scheduleAlarm(route, context, intent);
    }

    private void scheduleAlarm(Route route, Context context, Intent intent) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, route.getAlarmHour());
        calendar.set(Calendar.MINUTE, route.getAlarmMinute());

        // Check we aren't setting it in the past which would trigger it to fire instantly
        Calendar now = Calendar.getInstance();
        if(calendar.before(now))
            calendar.add(Calendar.DAY_OF_MONTH, 1);

        // PendingIntent to be triggered
        PendingIntent yourIntent =
                PendingIntent.getBroadcast(context, route.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // alarm is set to be triggered daily at given time. we should verify the day at broadcast receiver
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, yourIntent);
        Log.d("routeviewmodel", "alarmScheduled");
    }

    private void cancelAlarmIfExists(Context context, int routeId, Intent intent){
        try {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, routeId, intent,0);
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
