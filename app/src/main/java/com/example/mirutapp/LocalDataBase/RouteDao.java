package com.example.mirutapp.LocalDataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mirutapp.Model.Route;

import java.util.List;
import java.util.Set;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RouteDao {
    @Insert(onConflict = REPLACE)
    long save(Route route);

    @Query("SELECT * FROM route")
    LiveData<List<Route>> loadAll();

    @Query("SELECT * FROM route")
    List<Route> getAllRoutes();

    @Query("SELECT * FROM route WHERE id = :routeId")
    Route getRouteById(int routeId);

    @Query("DELETE FROM route WHERE id = :routeId")
    void deleteRouteById(int routeId);

    @Query("UPDATE route SET routeName = :routeName, days = :days, alarmHour = :alarmHour, alarmMinute = :alarmMinute WHERE id = :routeId")
    void updateRouteById(int routeId, String routeName, Set<Integer> days, int alarmHour, int alarmMinute);
}
