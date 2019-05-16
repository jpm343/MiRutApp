package com.example.mirutapp.Repository;

import androidx.lifecycle.LiveData;

import com.example.mirutapp.LocalDataBase.PostDao;
import com.example.mirutapp.Model.Post;
import com.example.mirutapp.WebService.PostWebService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;

@Singleton
public class PostRepository {
    private final PostWebService webService;
    private final PostDao postDao;
    private final Executor executor;

    @Inject
    public PostRepository(PostWebService webService, PostDao postDao, Executor executor) {
        this.webService = webService;
        this.postDao = postDao;
        this.executor = executor;
    }

    public LiveData<List<Post>> getAllPosts() {
        //verify date of posts before fetching
        refreshPosts();
        return postDao.loadAll();
    }

    //not implemented yet (by now it just fetches the data)
    private void refreshPosts() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //here we should verify the date
                try {
                    Response<List<Post>> response = webService.getAllPosts().execute();
                    //save each post on local database
                    if(response.isSuccessful() && response.body()!= null){
                        response.body().forEach(new Consumer<Post>() {
                            @Override
                            public void accept(Post post) {
                                postDao.save(post);
                            }
                        });
                    }
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
