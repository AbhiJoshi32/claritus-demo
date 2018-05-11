package com.example.claritus.claritus.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.claritus.claritus.api.ApiResponse;
import com.example.claritus.claritus.api.ClaritusService;
import com.example.claritus.claritus.db.UserDao;
import com.example.claritus.claritus.model.LoginResponse;
import com.example.claritus.claritus.model.Resource;
import com.example.claritus.claritus.model.User;
import com.example.claritus.claritus.utils.AppExecutors;
import com.example.claritus.claritus.utils.NetworkBoundResource;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

@Singleton
public class UserRepository {
    private final UserDao userDao;
    private final ClaritusService claritusService;
    private final AppExecutors appExecutors;
    private final SharedPreferences sharedPreferences;

    @Inject
    UserRepository(AppExecutors appExecutors, UserDao userDao, ClaritusService claritusService,SharedPreferences sharedPreferences) {
        this.userDao = userDao;
        this.claritusService = claritusService;
        this.appExecutors = appExecutors;
        this.sharedPreferences = sharedPreferences;
    }

    public LiveData<Resource<User>> loginUser(String email,String password) {
        MutableLiveData<Resource<User>> loginLiveData = new MutableLiveData<>();
        Call<LoginResponse> call= claritusService.loginUser(email,password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    User user = new User(response.body());
                    //TODO login
                    loginLiveData.setValue(Resource.success(user));
                    sharedPreferences.edit().putBoolean("isLoggedIn",true).apply();
                    sharedPreferences.edit().putString("email",email).apply();
                } else {
                    String message = null;
                    if (response.errorBody() != null) {
                        try {
                            message = response.errorBody().string();
                        } catch (IOException ignored) {
                            Timber.e(ignored, "error while parsing response");
                        }
                    }
                    if (message == null || message.trim().length() == 0) {
                        message = response.message();
                    }
                    loginLiveData.setValue(Resource.error(message,null));
                }

            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t){
                loginLiveData.setValue(Resource.error(t.getMessage(),null));
                //Handle on Failure here
            }
        });
        return loginLiveData;
    }

    public LiveData<Resource<User>> loadUser(String username) {
        return new NetworkBoundResource<User,User>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull User item) {
                userDao.insert(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable User data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<User> loadFromDb() {
                return userDao.findByUsername(username);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return claritusService.getUser(username);
            }
        }.asLiveData();
    }
}
