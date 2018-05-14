package com.example.claritus.claritus.di;

import com.example.claritus.claritus.auth.login.LoginFragment;
import com.example.claritus.claritus.auth.registration.RegistrationFragment;
import com.example.claritus.claritus.auth.verification.VerificationFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AuthFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract LoginFragment contributeLoginFragment();
    @ContributesAndroidInjector
    abstract RegistrationFragment contributeRegisterFragment();
    @ContributesAndroidInjector
    abstract VerificationFragment contributeVerificationFragment();
}