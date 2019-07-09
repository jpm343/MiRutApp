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

    @Query("SELECT * FROM vehicle WHERE id = :vehicleId")
    Vehicle getVehicleById(int vehicleId);

    @Query("DELETE FROM vehicle WHERE patente = :patente")
    void deleteVehicleByPatente(String patente);

    @Query("UPDATE vehicle SET patente = :patenteNew, alias = :alias, type = :type, selloVerde = :selloVerde  WHERE patente = :patenteOld")
    void updateVehicleByPatente(String patenteOld, String patenteNew, String alias, Integer type, boolean selloVerde);

    @Query("SELECT * FROM vehicle WHERE type = :carType")
    List<Vehicle> selectByCarType(Vehicle.CarType carType);
}
