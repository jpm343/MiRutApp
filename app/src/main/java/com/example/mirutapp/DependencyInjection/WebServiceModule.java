package com.example.mirutapp.DependencyInjection;

import com.example.mirutapp.WebService.PostWebService;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class WebServiceModule {
    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://mirutapp.herokuapp.com/")
                .build();
    }

    @Provides
    @Singleton
    PostWebService providePostWebService(Retrofit retrofit) {
        return retrofit.create(PostWebService.class);
    }

    //how do i provide an executor
    @Provides
    @Singleton
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }
}
