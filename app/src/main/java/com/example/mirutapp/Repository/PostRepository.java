package com.example.mirutapp.Repository;

import androidx.lifecycle.LiveData;

import com.example.mirutapp.LocalDataBase.PostDao;
import com.example.mirutapp.Model.Post;
import com.example.mirutapp.Model.User;
import com.example.mirutapp.WebService.PostWebService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;

@Singleton
public class PostRepository {
    private final PostWebService webService;
    private final PostDao postDao;
    //private final Executor executor;

    @Inject
    public PostRepository(PostWebService webService, PostDao postDao) {
        this.webService = webService;
        this.postDao = postDao;
        //this.executor = executor;
    }

    public LiveData<List<Post>> getAllPosts() {
        //verify date of posts before fetching
        refreshPosts();
        return postDao.loadAll();
    }

    //not implemented yet (by now it just fetches the data)
    private void refreshPosts() {
        //here we should verify the date
        try {
            //it should be a list?
            Response<List<Post>> response = webService.getAllPosts().execute();
            postDao.save(response.body());
        } catch(IOException e) {
            e.printStackTrace();
        }
        /*
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //here we should verify the date
                try {
                    //it should be a list?
                    Response<List<Post>> response = webService.getAllPosts().execute();
                    postDao.save(response.body());
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        });
        */
    }


}
