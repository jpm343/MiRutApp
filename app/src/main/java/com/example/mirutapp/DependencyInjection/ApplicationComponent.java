package com.example.mirutapp.DependencyInjection;

import android.app.Application;

import com.example.mirutapp.Fragment.MapsFragment;
import com.example.mirutapp.Fragment.NewsFragment;
import com.example.mirutapp.Fragment.VehicleFragment;
import com.example.mirutapp.Services.DisableNotificationReceiver;
import com.example.mirutapp.Services.VehicleCheckAlarmReceiver;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, RoomModule.class, WebServiceModule.class})
public interface ApplicationComponent {
    void inject(NewsFragment postFragment);
    void inject(VehicleFragment vehicleFragment);
    void inject(MapsFragment mapsFragment);
    void inject(VehicleCheckAlarmReceiver vehicleCheckAlarmReceiver);
    void inject(DisableNotificationReceiver disableNotificationReceiver);
    Application application();
}
