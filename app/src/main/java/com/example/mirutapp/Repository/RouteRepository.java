package com.example.mirutapp.Repository;

import android.util.Log;

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
                route.setId((int) routeDao.save(route));
                Log.d("routeRepo1", String.valueOf(route.getId()));
            }
        });
        Log.d("routeRepo2", String.valueOf(route.getId()));
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

    //CAREFUL WITH THIS METHOD. IT IS SUPPOSED TO BE USED IN A BACKGROUND THREAD (MAY FREEZE UI)
    public void createRouteInBackGround(Route route) {
        route.setId((int) routeDao.save(route));
    }
}
