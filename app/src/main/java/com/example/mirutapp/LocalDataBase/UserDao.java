package com.example.mirutapp.LocalDataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mirutapp.Model.User;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {
    @Insert(onConflict = REPLACE)
    void save(User user);

    //use the following format to make queries to local database.
    @Query("SELECT * FROM user WHERE id = :userId")
    LiveData<User> load(int userId);
}
