package com.example.claritus.claritus.auth.login;

import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Inject
    LoginViewModel() {

    }
}
