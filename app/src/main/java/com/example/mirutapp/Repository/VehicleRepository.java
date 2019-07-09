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

    public void turnOnNotifications(final Vehicle vehicle) {
        vehicle.setNotificating(true);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                vehicleDao.save(vehicle);
            }
        });
    }

    public void deleteVehicle(final String patente){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                vehicleDao.deleteVehicleByPatente(patente);
            }
        });
    }

    public void updateVehicle(final String patenteOld, final String patenteNew, final String alias, final Vehicle.CarType type, final boolean selloVerde){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                vehicleDao.updateVehicleByPatente(patenteOld, patenteNew, alias, type.getCode(), selloVerde);
            }
        });
    }

    // warning, the following methods could freeze UI. be aware to use this method in background services only
    public List<Vehicle> loadAll() {
        return vehicleDao.getAllVehicles();
    }
    public List<Vehicle> selectByCarType(Vehicle.CarType type) { return vehicleDao.selectByCarType(type); }

    public void turnOffNotificationsById(int id) {
        Vehicle vehicle = vehicleDao.getVehicleById(id);
        if(vehicle != null) {
            vehicle.setNotificating(false);
            vehicleDao.save(vehicle);
        }
    }
}
