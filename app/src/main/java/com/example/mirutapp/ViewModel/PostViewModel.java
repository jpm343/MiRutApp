package com.example.mirutapp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mirutapp.Model.Post;
import com.example.mirutapp.Repository.PostRepository;

import java.util.List;

import javax.inject.Inject;


public class PostViewModel extends ViewModel {
    private LiveData<List<Post>> posts;
    private PostRepository postRepository;

    @Inject
    public PostViewModel(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void init() {
        System.out.println("debug");
        posts = postRepository.getAllPosts();
    }

    public LiveData<List<Post>> getPosts() {
        return this.posts;
    }
}
