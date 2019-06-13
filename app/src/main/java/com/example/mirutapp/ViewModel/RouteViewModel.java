package com.example.mirutapp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mirutapp.Model.Route;
import com.example.mirutapp.Repository.RouteRepository;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

public class RouteViewModel extends ViewModel {
    public enum Status {OK, HOUR_ERROR, DAYS_ERROR}
    private LiveData<List<Route>> routes;
    private RouteRepository routeRepository;

    @Inject
    public RouteViewModel(RouteRepository routeRepository) { this.routeRepository = routeRepository; }

    public void init() { routes = routeRepository.getAllRoutes(); }

    public LiveData<List<Route>> getRoutes() { return this.routes; }

    public Status saveRoute(String url, String routeName, int alarmHour, int alarmMinute, Set<Integer> days) {
        if(alarmHour < 0 || alarmHour > 23)
            return Status.HOUR_ERROR;
        if(alarmMinute < 0 || alarmMinute > 59)
            return Status.HOUR_ERROR;
        if(days.isEmpty() || days.size() > 7)
            return Status.DAYS_ERROR;

        Route route = new Route(url, routeName, alarmHour, alarmMinute, days);
        routeRepository.createRoute(route);
        return Status.OK;
    }
}
