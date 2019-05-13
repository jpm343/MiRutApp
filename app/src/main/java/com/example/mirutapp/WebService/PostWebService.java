package com.example.mirutapp.WebService;

import com.example.mirutapp.Model.Post;

import java.util.List;

import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostWebService {
    @GET("https://mirutapp.herokuapp.com/posts.json/")
    Call<List<Post>> getAllPosts();
}
