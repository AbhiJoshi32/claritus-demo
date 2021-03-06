package com.example.claritus.claritus.di;


import com.example.claritus.claritus.auth.AuthActivity;
import com.example.claritus.claritus.main.MainActivity;
import com.example.claritus.claritus.splash.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {
    @ContributesAndroidInjector(modules = AuthFragmentBuilderModule.class)
    abstract AuthActivity contributeAuthActivity();
    @ContributesAndroidInjector()
    abstract SplashActivity contributeSplashActivity();
    @ContributesAndroidInjector(modules = MainFragmentBuilderModule.class)
    abstract MainActivity contributeMainActivity();
}
