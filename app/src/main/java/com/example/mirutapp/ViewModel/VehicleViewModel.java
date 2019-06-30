package com.example.mirutapp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mirutapp.Model.Vehicle;
import com.example.mirutapp.Repository.VehicleRepository;

import java.util.List;

import javax.inject.Inject;

public class VehicleViewModel extends ViewModel {
    public enum Status {OK, ERROR, ERROR_MOTO}
    private LiveData<List<Vehicle>> vehicles;
    private VehicleRepository vehicleRepository;

    @Inject
    public VehicleViewModel (VehicleRepository vehicleRepository) { this.vehicleRepository = vehicleRepository; }

    public void init() {
        vehicles = vehicleRepository.getAllVehicles();
    }

    public LiveData<List<Vehicle>> getVehicles() { return this.vehicles; }

    //method to store a new vehicle
    public Status saveVehicle(String patente, String alias, Vehicle.CarType type, boolean selloVerde) {
        if((type==Vehicle.CarType.AUTO || type==Vehicle.CarType.CAMION)&&(patente.length()!=6 || !Character.isDigit(patente.charAt(5)))) {
            return Status.ERROR;
        } else if((type == Vehicle.CarType.MOTO) && (patente.length() != 5 || !Character.isDigit(patente.charAt(4)))) {
            return Status.ERROR_MOTO;
        }

        Vehicle vehicle = new Vehicle(patente, alias, type, selloVerde);
        vehicleRepository.createVehicle(vehicle);
        //vehicleRepository.turnOffNotifications(vehicle);
        return Status.OK;
    }

    public Status deleteVehicle(String patente){
        vehicleRepository.deleteVehicle(patente);
        return Status.OK;
    }

    public Status updateVehicle(String patenteOld, String patenteNew, String alias){
        vehicleRepository.updateVehicle(patenteOld,patenteNew,alias);
        return Status.OK;
    }
}
