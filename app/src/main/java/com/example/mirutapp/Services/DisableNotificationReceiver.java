package com.example.mirutapp.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

import com.example.mirutapp.MiRutAppApplication;
import com.example.mirutapp.Repository.VehicleRepository;

import javax.inject.Inject;

public class DisableNotificationReceiver extends BroadcastReceiver {
    public static final String TAG = "DisableNotificationReceiver";
    @Inject VehicleRepository vehicleRepository;
    @Override
    public void onReceive(Context context, Intent intent) {
        ((MiRutAppApplication) context.getApplicationContext())
                .getApplicationComponent()
                .inject(this);

        String action = intent.getStringExtra("action");
        //String action = intent.getAction();
        Log.d(TAG, action);
        if(action.equals("disable")) {
            int vehicleId = intent.getIntExtra("vehicleId", -1);
            vehicleRepository.turnOffNotificationsById(vehicleId);
            Toast.makeText(context, "Notificaciones desactivadas para el vehículo indicado", Toast.LENGTH_LONG).show();
        }

        else if(action.equals("remember")) {
            Toast.makeText(context, "Se volverá a recordar", Toast.LENGTH_LONG).show();
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(1);
        System.out.println("NOTIFICATION");
    }
}
