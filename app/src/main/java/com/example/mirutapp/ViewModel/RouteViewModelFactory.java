package com.example.mirutapp.ViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mirutapp.Repository.RouteRepository;

import javax.inject.Inject;

public class RouteViewModelFactory implements ViewModelProvider.Factory {
    private final RouteRepository repository;

    @Inject
    public RouteViewModelFactory(RouteRepository routeRepository) {
        this.repository = routeRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if(modelClass.isAssignableFrom(RouteViewModel.class)) {
            return (T) new RouteViewModel(repository);
        }
        throw new IllegalArgumentException("Wrong ViewModel class");
    }
}
