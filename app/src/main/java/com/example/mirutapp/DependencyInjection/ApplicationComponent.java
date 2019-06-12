package com.example.mirutapp.DependencyInjection;

import android.app.Application;

import com.example.mirutapp.Fragment.IncidentListDialogFragment;
import com.example.mirutapp.Fragment.NewsFragment;
import com.example.mirutapp.Fragment.VehicleFragment;
import com.example.mirutapp.Services.VehicleCheckJobService;
import com.example.mirutapp.Services.VehicleCheckJobService_MembersInjector;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, RoomModule.class, WebServiceModule.class})
public interface ApplicationComponent {
    void inject(NewsFragment postFragment);
    void inject(VehicleFragment vehicleFragment);
    //void inject(IncidentListDialogFragment incidentListDialogFragment);
    void inject(VehicleCheckJobService vehicleCheckJobService);
    void inject(VehicleCheckJobService_MembersInjector vehicleCheckJobService_membersInjector);
    Application application();
}
