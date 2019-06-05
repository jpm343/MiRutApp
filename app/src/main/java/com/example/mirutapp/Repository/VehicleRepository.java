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

    public void createVehicle(final Vehicle vehicle) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                vehicleDao.save(vehicle);
            }
        });
    }
}