package com.example.claritus.claritus.di;

import com.example.claritus.claritus.main.FirebaseSync.FirebaseSyncFragment;
import com.example.claritus.claritus.main.UserList.UserListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract FirebaseSyncFragment contributeSyncFragment();
    @ContributesAndroidInjector
    abstract UserListFragment contributeUserListFragment();
}
