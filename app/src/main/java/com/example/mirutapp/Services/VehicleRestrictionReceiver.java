package com.example.mirutapp.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.mirutapp.MiRutAppApplication;
import com.example.mirutapp.Model.VehicleRestriction;
import com.example.mirutapp.WebService.VehicleRestrictionWebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VehicleRestrictionReceiver extends BroadcastReceiver {
    public static final String TAG = "VehicleRestrictionReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        //this method will be executed once a day. Here we should fetch vehicle restriction data from webservice
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
                //compare before replace
                if(vehicleRestriction != null)
                    if(MiRutAppApplication.getRestriction() != vehicleRestriction)
                        MiRutAppApplication.setRestriction(vehicleRestriction);

                //debug
                Log.d(TAG, vehicleRestriction.getCsv());
                Log.d(TAG, MiRutAppApplication.getRestriction().getCsv());
            }

            @Override
            public void onFailure(Call<VehicleRestriction> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
