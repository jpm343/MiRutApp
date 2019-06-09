package com.example.mirutapp.Repository;

import androidx.lifecycle.LiveData;

import com.example.mirutapp.LocalDataBase.VehicleDao;
import com.example.mirutapp.Model.Vehicle;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class VehicleRepository {
    private final VehicleDao vehicleDao;
    private final Executor executor;

    @Inject
    public VehicleRepository(VehicleDao vehicleDao, Executor executor) {
        this.vehicleDao = vehicleDao;
        this.executor = executor;
    }

    public LiveData<List<Vehicle>> getAllVehicles() {
        return vehicleDao.loadAll();
    }

    //this could freeze UI. be aware to use this method in background services only
    public List<Vehicle> loadAll() {
        return vehicleDao.getAllVehicles();
    }

    public void createVehicle(final Vehicle vehicle) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                vehicleDao.save(vehicle);
            }
        });
    }

    public void turnOffNotifications(final Vehicle vehicle) {
        vehicle.setNotificating(false);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                vehicleDao.save(vehicle);
            }
        });
    }
}
