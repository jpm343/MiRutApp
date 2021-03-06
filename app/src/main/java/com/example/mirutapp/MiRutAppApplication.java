package com.example.mirutapp;

import android.app.AlarmManager;
import android.app.Application;

import android.app.PendingIntent;
import android.app.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.mirutapp.DependencyInjection.ApplicationComponent;
import com.example.mirutapp.DependencyInjection.ApplicationModule;
import com.example.mirutapp.DependencyInjection.DaggerApplicationComponent;
import com.example.mirutapp.DependencyInjection.RoomModule;
import com.example.mirutapp.DependencyInjection.WebServiceModule;
import com.example.mirutapp.Model.VehicleRestriction;
import com.example.mirutapp.Services.VehicleCheckAlarmReceiver;
import com.example.mirutapp.Services.VehicleRestrictionReceiver;

import java.util.Calendar;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasServiceInjector;

public class MiRutAppApplication extends Application implements HasServiceInjector {
    @Inject
    DispatchingAndroidInjector<Service> serviceInjector;
    public static final String TAG = "MiRutAppApplication";
    public static final String CHANNEL_1_ID = "channel1";
    private ApplicationComponent applicationComponent;
    private static Context appContext;

    //This field is updated everyday by VehicleRestrictionReceiver. verify if it is not null before accessing.
    public static VehicleRestriction restriction;

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return serviceInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .webServiceModule(new WebServiceModule())
                .roomModule(new RoomModule(this))
                .build();
        createNotificationsChannels();
        setVehicleCheckAlarm();
        setVehicleRestrictionAlarm();
    }

    private void setVehicleCheckAlarm() {
        //set alarm time
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 0);

        //check we aren't setting it in the past which would trigger it to fire instantly
        Calendar now = Calendar.getInstance();
        if(calendar.before(now))
            calendar.add(Calendar.DAY_OF_MONTH, 1);

        //set intent to receive notification
        Intent intent = new Intent(getApplicationContext(), VehicleCheckAlarmReceiver.class);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void setVehicleRestrictionAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 0);

        Calendar now = Calendar.getInstance();
        if(calendar.before(now))
            calendar.add(Calendar.DAY_OF_MONTH, 1);

        Intent intent = new Intent(getApplicationContext(), VehicleRestrictionReceiver.class);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, now.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
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

    public static Context getAppContext() {
        return appContext;
    }
    public static VehicleRestriction getRestriction() { return restriction; }
    public static void setRestriction(VehicleRestriction newRestriction) { restriction = newRestriction; }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
