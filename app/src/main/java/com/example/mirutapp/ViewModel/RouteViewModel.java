package com.example.mirutapp.ViewModel;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mirutapp.MiRutAppApplication;
import com.example.mirutapp.Model.Route;
import com.example.mirutapp.Repository.RouteRepository;
import com.example.mirutapp.Services.RoutesAlarmSetterJobService;
import com.google.gson.Gson;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

public class RouteViewModel extends ViewModel {
    public enum Status {OK, HOUR_ERROR, DAYS_ERROR, DATABASE_ERROR}
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

        //create route
        Route route = new Route(url, routeName, alarmHour, alarmMinute, days);

        //set alarm before return. This should be done in a background job
        Context context = MiRutAppApplication.getAppContext();

        //pass the route object using json
        Gson gson = new Gson();
        String json = gson.toJson(route);
        PersistableBundle bundle = new PersistableBundle();
        bundle.putString("Route", json);

        //start background job
        ComponentName componentName = new ComponentName(context, RoutesAlarmSetterJobService.class);
        JobInfo info = new JobInfo.Builder(1, componentName)
                .setOverrideDeadline(0)
                .setExtras(bundle)
                .setPersisted(true)
                .build();

        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if(resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d("routeViewModel", "Job scheduled");
            return Status.OK;
        } else {
            return Status.DATABASE_ERROR;
        }
    }
}
