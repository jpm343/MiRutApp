package com.example.mirutapp.DependencyInjection;

import android.app.Application;

import com.example.mirutapp.Adapter.MyRouteRecyclerViewAdapter;
import com.example.mirutapp.Adapter.PatentesRecyclerViewAdapter;
import com.example.mirutapp.Fragment.HazardFragment;
import com.example.mirutapp.Fragment.MapsFragment;
import com.example.mirutapp.Fragment.NewsFragment;
import com.example.mirutapp.Fragment.RouteFragment;
import com.example.mirutapp.Fragment.VehicleFragment;
import com.example.mirutapp.Services.DisableNotificationReceiver;
import com.example.mirutapp.Services.RoutesAlarmReceiver;
import com.example.mirutapp.Services.RoutesAlarmSetterJobService;
import com.example.mirutapp.Services.VehicleCheckAlarmReceiver;
import com.example.mirutapp.Services.VehicleRestrictionReceiver;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, RoomModule.class, WebServiceModule.class})
public interface ApplicationComponent {
    void inject(NewsFragment postFragment);
    void inject(VehicleFragment vehicleFragment);
    void inject(MapsFragment mapsFragment);
    void inject(HazardFragment hazardFragment);
    void inject(RouteFragment routeFragment);
    void inject(VehicleCheckAlarmReceiver vehicleCheckAlarmReceiver);
    void inject(DisableNotificationReceiver disableNotificationReceiver);
    void inject(RoutesAlarmReceiver routesAlarmReceiver);
    void inject(PatentesRecyclerViewAdapter patentesRecyclerViewAdapter);
    void inject(MyRouteRecyclerViewAdapter myRouteRecyclerViewAdapter);
    void inject(RoutesAlarmSetterJobService routesAlarmSetterJobService);
    void inject(VehicleRestrictionReceiver vehicleRestrictionReceiver);
    Application application();


}
