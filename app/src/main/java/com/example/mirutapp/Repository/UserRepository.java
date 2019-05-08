package com.example.mirutapp.Repository;

import androidx.lifecycle.LiveData;

import com.example.mirutapp.LocalDataBase.UserDao;
import com.example.mirutapp.Model.User;
import com.example.mirutapp.WebService.UserWebService;

import java.io.IOException;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;

//informs dagger that this class should be constructed only once
@Singleton
public class UserRepository {
    private final UserWebService webService;
    private final UserDao userDao;
    private final Executor executor;

    @Inject
    public UserRepository(UserWebService webService, UserDao userDao, Executor executor) {
        this.webService = webService;
        this.userDao = userDao;
        this.executor = executor;
    }

    public LiveData<User> getUser(int userId) {
        refreshUser(userId);
        //returns a live data object directly from local data base
        return userDao.load(userId);
    }

    private void refreshUser(final int userId) {
        //runs in a background thread
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //we should check if user data was fetched recently
                //to do that we must implement some method to check on webservice class.
                /*Example
                 * boolean userExists = userDao.hasUser(FRESH_TIMEOUT);
                 * if (!userExists) {
                 * //refreshes the data
                 * Response<User> response = webService.getUser(userId).execute();
                 * //check for errors here.
                 *   //updates the data base
                 *  userDao.save(response.body());
                 */
                //by now we just execute the fetch
                try {
                    //this request of webservice should be handled in case of failure
                    Response<User> response = webService.getUser(userId).execute();

                    userDao.save(response.body());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
