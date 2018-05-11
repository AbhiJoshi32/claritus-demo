package com.example.claritus.claritus.di;

import com.example.claritus.claritus.auth.login.LoginFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AuthFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract LoginFragment contributeRepoFragment();
}