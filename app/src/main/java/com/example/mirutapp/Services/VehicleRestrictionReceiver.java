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
import com.example.mirutapp.Model.Vehicle;
import com.example.mirutapp.Model.VehicleRestriction;
import com.example.mirutapp.R;
import com.example.mirutapp.Repository.VehicleRepository;
import com.example.mirutapp.WebService.VehicleRestrictionWebService;
import com.example.mirutapp.util.RestrictionNumbersParser;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.mirutapp.MiRutAppApplication.CHANNEL_1_ID;

public class VehicleRestrictionReceiver extends BroadcastReceiver {
    public static final String TAG = "VehicleRestrictionReceiver";
    private Context appContext;
    public static List<Vehicle> vehicleList = new ArrayList<>();
    public static String fecha;
    @Inject VehicleRepository repository;

    @Override
    public void onReceive(Context context, Intent intent) {
        appContext = context;

        // dependency injection to access to vehicle repository
        ((MiRutAppApplication) appContext.getApplicationContext())
                .getApplicationComponent()
                .inject(this);

        // this method will be executed once a day. Here we should fetch vehicle restriction data from webservice
        Log.d(TAG, "ALARM RECEIVED");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://airesantiago.mma.gob.cl/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        VehicleRestrictionWebService webService = retrofit.create(VehicleRestrictionWebService.class);
        Call<VehicleRestriction> call = webService.getVehicleRestriction();
        call.enqueue(new Callback<VehicleRestriction>() {
            @Override
            public void onResponse(Call<VehicleRestriction> call, Response<VehicleRestriction> response) {
                VehicleRestriction vehicleRestriction = response.body();
                // initialize strings with restriction numbers
                String carCSVString = "";
                String carSSVString = "";
                String bikeString = "";
                String truckSSVString = "";
                String truckCSVString = "";

                //compare before replace
                if(vehicleRestriction != null) {
                    if(MiRutAppApplication.getRestriction() != vehicleRestriction) {
                        MiRutAppApplication.setRestriction(vehicleRestriction);
                    }
                    carCSVString = vehicleRestriction.getCsv();
                    carSSVString = vehicleRestriction.getSsv();
                    bikeString = vehicleRestriction.getBike();
                    truckSSVString = vehicleRestriction.getTruck_ssv();
                    truckCSVString = vehicleRestriction.getTruck_csv();
                    fecha = vehicleRestriction.getFecha();
                }

                //debug
                Log.d(TAG, vehicleRestriction.getCsv());
                Log.d(TAG, MiRutAppApplication.getRestriction().getCsv());

                List<Integer> carCSV = RestrictionNumbersParser.StringToIntList(carCSVString);
                List<Integer> carSSV = RestrictionNumbersParser.StringToIntList(carSSVString);
                List<Integer> bike = RestrictionNumbersParser.StringToIntList(bikeString);
                List<Integer> truckSSV = RestrictionNumbersParser.StringToIntList(truckSSVString);
                List<Integer> truckCSV = RestrictionNumbersParser.StringToIntList(truckCSVString);

                //notify each car type
                notifyRestrictionNumbers(carCSV, Vehicle.CarType.AUTO);
                notifyRestrictionNumbers(carSSV, Vehicle.CarType.AUTO);
                notifyRestrictionNumbers(bike, Vehicle.CarType.MOTO);
                notifyRestrictionNumbers(truckSSV, Vehicle.CarType.CAMION);
                notifyRestrictionNumbers(truckCSV, Vehicle.CarType.CAMION);
            }

            @Override
            public void onFailure(Call<VehicleRestriction> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void notifyRestrictionNumbers(List<Integer> numbers, Vehicle.CarType carType) {
        // verify here to avoid multiple check
        if(numbers == null)
            return;

        List<Vehicle> selectedVehicles = repository.selectByCarType(carType);
        if(selectedVehicles != null)
            for(Vehicle vehicle: selectedVehicles) {
                Log.d(TAG, vehicle.getAlias());
                Log.d(TAG, String.valueOf(vehicle.hasSelloVerde()));
                String patente = vehicle.getPatente();
                char lastDigit = patente.charAt(patente.length()-1);
                for(Integer number: numbers) {
                    if(number == Character.getNumericValue(lastDigit)) {
                        vehicleList.add(vehicle);
                        // here we should notify
                        sendNotification(vehicle);
                    }
                }
            }
    }

    private void sendNotification(Vehicle vehicle) {
        //intent with extra information in order to load routes section
        Intent activityIntent = new Intent(appContext, MainActivity.class);
        activityIntent.putExtra("comesFromNotification", "vehiclesFragment");
        PendingIntent contentIntent = PendingIntent.getActivity(appContext, vehicle.getId()+1, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String title = "Uno de tus vehículos tiene restricción para hoy!";
        String messageBody = "Vehículo: " + vehicle.getAlias() + ". Toca para revisar";

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(appContext);
        Notification notification = new NotificationCompat.Builder(appContext, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_car)
                .setColor(Color.GREEN)
                .setVibrate(new long[] {0, 500, 50, 400})
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .build();

        notificationManager.notify(vehicle.getId()+1, notification);
    }
}
