package com.example.mirutapp.Services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mirutapp.MainActivity;
import com.example.mirutapp.MiRutAppApplication;
import com.example.mirutapp.Model.Route;
import com.example.mirutapp.R;
import com.example.mirutapp.Repository.RouteRepository;

import java.util.Calendar;

import javax.inject.Inject;

import static com.example.mirutapp.MiRutAppApplication.CHANNEL_1_ID;

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

        //get id from intent extra and use it to retrieve route from repo
        int routeId = intent.getIntExtra("RouteId", -1);
        Route route = routeRepository.getRouteById(routeId);

        //notify if route is notifying and the current day match with any of the specified on route
        Calendar c = Calendar.getInstance();
        int currentDay = c.get(Calendar.DAY_OF_WEEK);
        Log.d(TAG, String.valueOf(currentDay));
        if(route.getDays().contains(currentDay-1))
            this.sendNotification(route);
    }

    private void sendNotification(Route route) {
        //intent with extra information in order to load routes section
        Intent activityIntent = new Intent(appContext, MainActivity.class);
        activityIntent.putExtra("comesFromNotification", "routeFragment");
        PendingIntent contentIntent = PendingIntent.getActivity(appContext, route.getId()+1, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String title = "Revisa el estado de tu Ruta antes de salir!";
        String messageBody = "Ruta: " + route.getRouteName() + ". Toca para revisar";

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(appContext);
        Notification notification = new NotificationCompat.Builder(appContext, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_car)
                .setColor(Color.BLUE)
                .setVibrate(new long[] {0, 500, 100, 500})
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .build();

        notificationManager.notify(route.getId(), notification);
    }
}
