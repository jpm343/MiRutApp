package com.example.mirutapp.LocalDataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mirutapp.Model.User;

//creates local data base based on user model
@Database(entities = {User.class}, version = 1)
public abstract class UserDataBase extends RoomDatabase {
    public abstract UserDao userDao();
}
