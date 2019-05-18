package com.example.mirutapp.DependencyInjection;

import android.app.Application;

import com.example.mirutapp.Fragment.PostFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, RoomModule.class, WebServiceModule.class})
public interface ApplicationComponent {
    void inject(PostFragment postFragment);
    Application application();
}
