package com.example.mirutapp.DependencyInjection;

import android.app.Application;

import com.example.mirutapp.MiRutAppApplication;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final MiRutAppApplication application;
    public ApplicationModule(MiRutAppApplication appApplication) {
        this.application = appApplication;
    }

    @Provides
    MiRutAppApplication provideMiRutAppApplication(){
        return application;
    }

    @Provides
    Application provideApplication(){
        return application;
    }
    
}
