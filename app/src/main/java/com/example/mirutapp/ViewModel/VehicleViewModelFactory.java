package com.example.mirutapp.ViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mirutapp.Repository.VehicleRepository;

import javax.inject.Inject;

public class VehicleViewModelFactory implements ViewModelProvider.Factory {
    private final VehicleRepository repository;

    @Inject
    public VehicleViewModelFactory(VehicleRepository vehicleRepository) { this.repository = vehicleRepository; }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(VehicleViewModel.class)) {
            return (T) new VehicleViewModel(repository);
        }
        throw new IllegalArgumentException("Wrong ViewModel class");
    }
}
