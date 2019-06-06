package com.example.mirutapp;

import android.app.Application;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.net.Uri;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;


import com.example.mirutapp.DependencyInjection.ApplicationComponent;
import com.example.mirutapp.DependencyInjection.ApplicationModule;
import com.example.mirutapp.DependencyInjection.DaggerApplicationComponent;
import com.example.mirutapp.DependencyInjection.RoomModule;
import com.example.mirutapp.DependencyInjection.WebServiceModule;
import com.example.mirutapp.Services.VehicleCheckJobService;
import com.google.firebase.messaging.FirebaseMessaging;

public class MiRutAppApplication extends Application {
    public static final String TAG = "MiRutAppApplication";
    public static final String CHANNEL_1_ID = "channel1";
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .webServiceModule(new WebServiceModule())
                .roomModule(new RoomModule(this))
                .build();

        createNotificationsChannels();
        scheduleVehicleCheckJob();
    }

    private void scheduleVehicleCheckJob() {
        ComponentName componentName = new ComponentName(this, VehicleCheckJobService.class);
        JobInfo info = new JobInfo.Builder(1, componentName)
                .setPersisted(true)
                .setPeriodic(24 * 60 * 60 * 1000)
                .build();
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if(resultCode == JobScheduler.RESULT_SUCCESS)
            Log.d(TAG, "job scheduled");
        else
            Log.d(TAG, "job not scheduled");
    }

    private void cancelScheduleVehicleCheckJob() {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(1);
        Log.d(TAG, "job cancelled");
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    private void createNotificationsChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel1.setDescription("channel to notify road events");


            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }
}
