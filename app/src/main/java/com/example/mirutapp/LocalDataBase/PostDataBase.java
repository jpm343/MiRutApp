package com.example.mirutapp.LocalDataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mirutapp.Model.Post;

@Database(entities = {Post.class}, version = 1)
public abstract class PostDataBase extends RoomDatabase {
    public abstract PostDao postDao();
}
