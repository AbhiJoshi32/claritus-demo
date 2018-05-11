package com.example.claritus.claritus.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.claritus.claritus.auth.login.LoginViewModel;
import com.example.claritus.claritus.splash.SplashViewModel;
import com.example.claritus.claritus.viewmodel.ClaritusViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    abstract ViewModel bindSplashViewModel(SplashViewModel splashViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ClaritusViewModelFactory factory);
}
