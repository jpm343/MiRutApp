package com.example.mirutapp.LocalDataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mirutapp.Model.Vehicle;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface VehicleDao {
    @Insert(onConflict = REPLACE)
    void save(Vehicle vehicle);

    @Query("SELECT * FROM vehicle")
    LiveData<List<Vehicle>> loadAll();

    @Query("SELECT * FROM vehicle")
    List<Vehicle> getAllVehicles();
}
