package com.example.mirutapp.DependencyInjection;

import android.app.Application;

import com.example.mirutapp.Fragment.NewsFragment;
import com.example.mirutapp.Fragment.VehicleFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, RoomModule.class, WebServiceModule.class})
public interface ApplicationComponent {
    void inject(NewsFragment postFragment);
    void inject(VehicleFragment vehicleFragment);
    Application application();
}
