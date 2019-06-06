package com.example.mirutapp.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.mirutapp.Model.Vehicle;
import com.example.mirutapp.Repository.VehicleRepository;

import java.util.List;

public class NotificationsService extends Service {
    private LiveData<List<Vehicle>> vehicles;
    private VehicleRepository repository;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
