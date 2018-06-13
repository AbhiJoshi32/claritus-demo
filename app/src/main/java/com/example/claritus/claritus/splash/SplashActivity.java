package com.example.claritus.claritus.splash;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.claritus.claritus.R;
import com.example.claritus.claritus.auth.AuthActivity;
import com.example.claritus.claritus.main.MainActivity;

import com.example.claritus.claritus.model.Status;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import timber.log.Timber;

public class SplashActivity extends AppCompatActivity{
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AndroidInjection.inject(this);
        splashViewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel.class);
        splashViewModel.getAuthTokenLiveData().observe(this, stringResource -> {
            Timber.d("Observed Something");
            if (stringResource != null) {
                Timber.d(stringResource.data);
                if (stringResource.status==Status.SUCCESS) {
                    if (splashViewModel.isLoggedIn()) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        this.overridePendingTransition(0, 0);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        this.overridePendingTransition(0, 0);
                        finish();
                    }
                }
            }
            else Timber.d("null");
        });
        splashViewModel.setToken("sdasd");
        splashViewModel.setAndroidIdLiveData("asdsa");
    }
}
