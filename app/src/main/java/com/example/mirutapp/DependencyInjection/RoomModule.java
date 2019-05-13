package com.example.mirutapp.DependencyInjection;

import android.app.Application;

import androidx.annotation.MainThread;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.mirutapp.LocalDataBase.PostDao;
import com.example.mirutapp.LocalDataBase.PostDataBase;
import com.example.mirutapp.Model.Post;
import com.example.mirutapp.Repository.PostRepository;
import com.example.mirutapp.ViewModel.PostViewModelFactory;
import com.example.mirutapp.WebService.PostWebService;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class RoomModule {
    //declare the local databases
    private final PostDataBase postDatabase;

    public RoomModule(Application application) {
        this.postDatabase = Room.databaseBuilder(
                application,
                PostDataBase.class,
                "Post.db"
        ).build();
    }

    @Provides
    @Singleton
    PostDataBase providePostDatabase(Application application){
        return postDatabase;
    }

    @Provides
    @Singleton
    PostWebService providePostWebService(Retrofit retrofit){
        return retrofit.create(PostWebService.class);
    }

    @Provides
    @Singleton
    Executor provideExecutor(){
        return Executors.newCachedThreadPool();
    }

    @Provides
    @Singleton
    PostRepository providePostRepository(PostWebService webService, PostDao postDao, Executor executor) {
        return new PostRepository(webService, postDao, executor);
    }

    @Provides
    @Singleton
    PostDao providePostDao(PostDataBase postDataBase) {
        return postDataBase.postDao();
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory provideViewModelFactory(PostRepository postRepository) {
        return new PostViewModelFactory(postRepository);
    }
}
