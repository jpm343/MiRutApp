package com.example.mirutapp.LocalDataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mirutapp.Model.Post;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface PostDao {
    @Insert(onConflict = REPLACE)
    void save(Post post);

    @Query("SELECT * FROM post")
    LiveData<List<Post>> loadAll();
}
