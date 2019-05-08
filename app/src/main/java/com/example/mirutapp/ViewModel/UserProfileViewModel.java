package com.example.mirutapp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mirutapp.Model.User;
import com.example.mirutapp.Repository.UserRepository;

import javax.inject.Inject;

public class UserProfileViewModel extends ViewModel {
    private LiveData<User> user;
    private UserRepository userRepository;

    //we use dagger 2 to provide user repository with dependency injection. similar to @Autowired
    @Inject
    public UserProfileViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void init(int userId) {
        if(this.user != null) {
            //if the user already exists and it doesn't change
            return;
        }
        user = userRepository.getUser(userId);
    }

    public LiveData<User> getUser() {
        return this.user;
    }
}
