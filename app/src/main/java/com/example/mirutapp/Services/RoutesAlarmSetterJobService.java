package com.example.mirutapp.Services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.mirutapp.MiRutAppApplication;
import com.example.mirutapp.Model.Route;
import com.example.mirutapp.Repository.RouteRepository;
import com.google.gson.Gson;

import java.util.Calendar;

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

                repository.createRouteInBackGround(route);

                Log.d(TAG, route.getRouteName());
                Log.d(TAG, String.valueOf(route.getId()));

                Log.d(TAG, "Job finished");
                jobFinished(params, false);
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

        //debug
        Log.d("routeviewmodel", String.valueOf(route.getUrl().hashCode()));

        // Here we should verify if the alarm is created or not
        cancelAlarmIfExists(context, route.getId(), intent);

        for(int day: route.getDays()){
            // calendar enum starts at 1, so add 1 to day of week
            scheduleAlarm(day+1, route, context, intent);
        }
    }

    private void scheduleAlarm(int dayOfWeek, Route route, Context context, Intent intent) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        calendar.set(Calendar.HOUR_OF_DAY, route.getAlarmHour());
        calendar.set(Calendar.MINUTE, route.getAlarmMinute());

        // Check we aren't setting it in the past which would trigger it to fire instantly
        if(calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }

        // PendingIntent to be triggered
        PendingIntent yourIntent =
                PendingIntent.getBroadcast(context, route.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, yourIntent);
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
