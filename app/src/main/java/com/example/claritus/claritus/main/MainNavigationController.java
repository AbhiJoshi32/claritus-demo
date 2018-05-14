package com.example.claritus.claritus.main;

import android.support.v4.app.FragmentManager;

import com.example.claritus.claritus.R;
import com.example.claritus.claritus.main.FirebaseSync.FirebaseSyncFragment;

import javax.inject.Inject;

public class MainNavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;
    @Inject
    public MainNavigationController(MainActivity mainActivity) {
        this.containerId = R.id.mainContainer;
        this.fragmentManager = mainActivity.getSupportFragmentManager();
    }

    public void navigateToSync() {
        FirebaseSyncFragment firebaseSyncFragment = new FirebaseSyncFragment();
        fragmentManager.beginTransaction()
                .replace(containerId, firebaseSyncFragment)
                .commitAllowingStateLoss();
    }

    public void navigateToList() {
    }
}
