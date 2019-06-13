package com.example.mirutapp.Services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mirutapp.LocalDataBase.RevisionTecnica;
import com.example.mirutapp.MainActivity;
import com.example.mirutapp.MiRutAppApplication;
import com.example.mirutapp.Model.Vehicle;
import com.example.mirutapp.R;
import com.example.mirutapp.Repository.VehicleRepository;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import static com.example.mirutapp.MiRutAppApplication.CHANNEL_1_ID;

public class VehicleCheckAlarmReceiver extends BroadcastReceiver {
    public static final String TAG = "VehicleCheckAlarmReceiver";
    private List<Vehicle> vehicles;
    private Context appContext;
    @Inject VehicleRepository vehicleRepository;

    @Override
    public void onReceive(Context context, Intent intent) {
        ((MiRutAppApplication) context.getApplicationContext())
                .getApplicationComponent()
                .inject(this);

        appContext = context;
        vehicles = vehicleRepository.loadAll();
        if(vehicles != null){
            //get month and check its corresponding vehicle number
            Calendar c = Calendar.getInstance();
            int month = c.get(Calendar.MONTH);
            int vehicleNumber = RevisionTecnica.rules.get(month, -1);
            Log.d(TAG, String.valueOf(vehicleNumber));
            if(vehicleNumber != -1) {
                notifyVehiclesEndingIn(vehicleNumber);
            }
            else {
                //check for special cases (march and december)
                if(month == Calendar.MARCH)
                    notifyAllVehicles();
                if(month == Calendar.DECEMBER)
                    resetNotifications();

            }
        }
    }
    private void notifyVehiclesEndingIn(int digit) {
        List<Vehicle> vehicleList = vehicles;
        if(vehicleList == null)
            return;

        for(Vehicle vehicle: vehicleList){
            Log.d(TAG, String.valueOf(vehicle.isNotificating()));
            if(vehicle.isNotificating()) {
                //check for rules
                char lastDigit = vehicle.getPatente().charAt(5);
                if(digit == Character.getNumericValue(lastDigit))
                    sendNotification(vehicle);
            }
        }
    }

    private void notifyAllVehicles() {
        sendNotification(null);
    }

    private void resetNotifications() {
        //here we should modify the record in database
        if(vehicles != null) {
            for(Vehicle vehicle: vehicles) {
                vehicleRepository.turnOnNotifications(vehicle);
            }
        }
    }

    private void sendNotification(@Nullable Vehicle vehicle) {
        //if vehicle is null, notify all
        String title = "Recordatorio de renovación de Revisión Técnica";
        String messageBody;
        if(vehicle == null) {
            messageBody = "Recuerda que en marzo debes renovar las revisión de tus vehículos atrasados";
        } else {
            messageBody = "Para: " + vehicle.getAlias() + " (Patente: " + vehicle.getPatente() + ")";
        }

        //intent with extra information in order to load vehicles section
        Intent activityIntent = new Intent(appContext, MainActivity.class);
        activityIntent.putExtra("comesFromNotification", "vehiclesFragment");
        PendingIntent contentIntent = PendingIntent.getActivity(appContext, 0, activityIntent, 0);

        //intent to disable notifications for a vehicle
        Intent broadcastIntent = new Intent(appContext, DisableNotificationReceiver.class);
        broadcastIntent.putExtra("action", "disable");
        broadcastIntent.putExtra("vehicleId", vehicle.getId()); //this should never be null
        broadcastIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent actionIntent = PendingIntent.getBroadcast(appContext, 0, broadcastIntent, PendingIntent.FLAG_ONE_SHOT);

        //Intent to remember another day
        Intent broadcastIntent2 = new Intent(appContext, DisableNotificationReceiver.class);
        broadcastIntent2.putExtra("action", "remember");
        broadcastIntent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent actionIntent2 = PendingIntent.getBroadcast(appContext, 1, broadcastIntent2, PendingIntent.FLAG_ONE_SHOT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(appContext);
        Notification notification = new NotificationCompat.Builder(appContext, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_car)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .addAction(R.mipmap.ic_launcher, "Entendido", actionIntent)
                .addAction(R.mipmap.ic_launcher, "Recordarme mañana", actionIntent2)
                .build();

        //error here: there should be a different id for each notification in case of more than one vehicle being notified
        notificationManager.notify(1, notification);
    }
}
