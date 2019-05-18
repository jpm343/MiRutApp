package com.example.mirutapp.ViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mirutapp.Model.Post;
import com.example.mirutapp.Repository.PostRepository;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class PostViewModelFactory implements ViewModelProvider.Factory {
    private final PostRepository repository;

    @Inject
    public PostViewModelFactory(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PostViewModel.class)) {
            return (T) new PostViewModel(repository);
        }
        throw new IllegalArgumentException("Wrong ViewModel class");
    }
}
