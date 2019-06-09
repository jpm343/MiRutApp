package com.example.mirutapp.Services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
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

public class VehicleCheckJobService extends JobService {
    public static final String TAG = "VehicleCheckingService";
    private List<Vehicle> vehicles;
    @Inject VehicleRepository vehicleRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        ((MiRutAppApplication) getApplication())
                .getApplicationComponent()
                .inject(this);
    }

    @Override
    public boolean onStartJob(JobParameters params) {
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
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    private void notifyVehiclesEndingIn(int digit) {
        List<Vehicle> vehicleList = vehicles;
        if(vehicleList == null)
            return;

        for(Vehicle vehicle: vehicleList){
            if(vehicle.isNotificating()) {
                //check for rules
                char lastDigit = vehicle.getPatente().charAt(5);
                String message = lastDigit + "=" + digit;
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
        Intent activityIntent = new Intent(this, MainActivity.class);
        activityIntent.putExtra("comesFromNotification", "vehiclesFragment");
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_car)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .build();

        notificationManager.notify(1, notification);
    }
}
