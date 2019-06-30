package com.example.mirutapp.LocalDataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mirutapp.Model.Post;
import com.example.mirutapp.Model.Route;
import com.example.mirutapp.Model.Vehicle;

@Database(entities = {Post.class, Vehicle.class, Route.class}, version = 6)
public abstract class AppDataBase extends RoomDatabase {
    public abstract PostDao postDao();
    public abstract VehicleDao vehicleDao();
    public abstract RouteDao routeDao();
}
