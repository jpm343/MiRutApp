package com.example.mirutapp.DependencyInjection;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.mirutapp.LocalDataBase.AppDataBase;
import com.example.mirutapp.LocalDataBase.PostDao;
import com.example.mirutapp.LocalDataBase.RouteDao;
import com.example.mirutapp.LocalDataBase.VehicleDao;
import com.example.mirutapp.Repository.PostRepository;
import com.example.mirutapp.Repository.RouteRepository;
import com.example.mirutapp.Repository.VehicleRepository;
import com.example.mirutapp.ViewModel.PostViewModelFactory;
import com.example.mirutapp.ViewModel.RouteViewModelFactory;
import com.example.mirutapp.ViewModel.VehicleViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {
    //declare the local databases
    private final AppDataBase appDatabase;

    public RoomModule(Application application) {
        this.appDatabase = Room.databaseBuilder(
                application,
                AppDataBase.class,
                "Post.db"
        ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    AppDataBase provideAppDatabase(Application application){
        return appDatabase;
    }

    @Provides
    @Singleton
    PostDao providePostDao(AppDataBase appDataBase) {
        return appDataBase.postDao();
    }

    @Provides
    @Singleton
    VehicleDao provideVehicleDao(AppDataBase appDataBase) { return appDataBase.vehicleDao(); }

    @Provides
    @Singleton
    RouteDao provideRouteDao(AppDataBase appDataBase) {return appDataBase.routeDao(); }

    @Provides
    @Singleton
    PostViewModelFactory provideViewModelFactory(PostRepository postRepository) {
        return new PostViewModelFactory(postRepository);
    }

    @Provides
    @Singleton
    VehicleViewModelFactory provideVehicleViewModelFactory(VehicleRepository vehicleRepository) {
        return new VehicleViewModelFactory(vehicleRepository);
    }

    @Provides
    @Singleton
    RouteViewModelFactory provideRouteViewModelFactory(RouteRepository routeRepository) {
        return new RouteViewModelFactory(routeRepository);
    }
}
