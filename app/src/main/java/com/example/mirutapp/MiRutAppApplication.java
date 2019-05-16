package com.example.mirutapp;

import android.app.Application;

import com.example.mirutapp.DependencyInjection.ApplicationComponent;
import com.example.mirutapp.DependencyInjection.ApplicationModule;
import com.example.mirutapp.DependencyInjection.DaggerApplicationComponent;
import com.example.mirutapp.DependencyInjection.RoomModule;
import com.example.mirutapp.DependencyInjection.WebServiceModule;

public class MiRutAppApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .webServiceModule(new WebServiceModule())
                .roomModule(new RoomModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
