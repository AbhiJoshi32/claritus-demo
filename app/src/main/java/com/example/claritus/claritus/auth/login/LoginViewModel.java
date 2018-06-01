package com.example.claritus.claritus.auth.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.claritus.claritus.model.Resource;
import com.example.claritus.claritus.model.Status;
import com.example.claritus.claritus.model.user.User;
import com.example.claritus.claritus.repository.UserRepository;
import com.example.claritus.claritus.utils.AbsentLiveData;

import javax.inject.Inject;

import timber.log.Timber;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginData> loginLiveData = new MutableLiveData<>();
    private LiveData<Resource<String>> token;
    private LiveData<Resource<User>> user;

    @Inject
    LoginViewModel(UserRepository userRepository) {
        token = Transformations.switchMap(loginLiveData,loginData->{
            if (loginData != null
                    && loginData.getEmail()!= null
                    && loginData.getPassword() != null)
                return userRepository.loginUser(loginData);
            else return AbsentLiveData.create();
        });
        user = Transformations.switchMap(token,tokenData->{
            if (tokenData!=null && tokenData.status== Status.SUCCESS) {
                return userRepository.loadUser();
            } else return AbsentLiveData.create();
        });
    }
    public void setEmail(String email) {
        Timber.d("Email set");
        LoginData loginData = new LoginData();
        if (loginLiveData.getValue() != null) {
            loginData = loginLiveData.getValue();
        }
        loginData.setEmail(email);
        loginLiveData.setValue(loginData);
    }

    public void setPassword(String password) {
        Timber.d("password set");
        LoginData loginData = new LoginData();
        if (loginLiveData.getValue() != null) {
            loginData = loginLiveData.getValue();
        }
        loginData.setPassword(password);
        loginLiveData.setValue(loginData);
    }

    public LiveData<Resource<String>> getToken() {
        return token;
    }

    public LiveData<Resource<User>> getUser() {
        return user;
    }
}
