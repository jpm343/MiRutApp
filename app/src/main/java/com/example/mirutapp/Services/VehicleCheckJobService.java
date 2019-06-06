package com.example.mirutapp.Services;

import android.app.job.JobParameters;
import android.app.job.JobService;

import androidx.lifecycle.LiveData;

import com.example.mirutapp.Model.Vehicle;
import com.example.mirutapp.Repository.VehicleRepository;

import java.util.List;

public class VehicleCheckJobService extends JobService {
    private LiveData<List<Vehicle>> vehicles;
    private VehicleRepository vehicleRepository;

    @Override
    public boolean onStartJob(JobParameters params) {
        vehicles = vehicleRepository.getAllVehicles();
        List<Vehicle> vehicleList = vehicles.getValue();
        if(vehicleList != null){
            for(Vehicle vehicle: vehicleList){
                if(vehicle.isNotificating()) {
                    //check for rules
                    char lastDigit = vehicle.getPatente().charAt(5);
                    switch(lastDigit) {
                        case '1':
                            break;
                        case '2':
                            break;
                        case '3':
                            break;
                        case '4':
                            break;
                        case '5':
                            break;
                        case '6':
                            break;
                        case '7':
                            break;
                        case '8':
                            break;
                        case '9':
                            break;
                        default:
                            break;
                    }

                }
            }
        }

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
