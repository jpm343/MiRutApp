package com.example.mirutapp.Repository;

import androidx.lifecycle.LiveData;

import com.example.mirutapp.LocalDataBase.RouteDao;
import com.example.mirutapp.Model.Route;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RouteRepository {
    private final RouteDao routeDao;
    private final Executor executor;

    @Inject
    public RouteRepository(RouteDao routeDao, Executor executor) {
        this.routeDao = routeDao;
        this.executor = executor;
    }

    public LiveData<List<Route>> getAllRoutes() { return routeDao.loadAll(); }

    public void createRoute(final Route route) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                routeDao.save(route);
            }
        });
    }

    public void turnOnNotifications(final Route route) {
        route.setNotificating(true);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                routeDao.save(route);
            }
        });
    }

    public void turnOffNotification(final Route route) {
        route.setNotificating(false);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                routeDao.save(route);
            }
        });
    }

}
