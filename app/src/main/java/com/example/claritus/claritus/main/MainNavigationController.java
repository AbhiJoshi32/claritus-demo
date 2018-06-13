package com.example.claritus.claritus.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.example.claritus.claritus.R;
import com.example.claritus.claritus.main.Chat.ChatFragment;
import com.example.claritus.claritus.main.FirebaseSync.FirebaseSyncFragment;
import com.example.claritus.claritus.main.Profile.ProfileFragment;
import com.example.claritus.claritus.main.UserList.UserListFragment;

import javax.inject.Inject;

public class MainNavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;
    @Inject
    public MainNavigationController(MainActivity mainActivity) {
        this.containerId = R.id.mainContainer;
        this.fragmentManager = mainActivity.getSupportFragmentManager();
    }

    void navigateToSync() {
        FirebaseSyncFragment firebaseSyncFragment = new FirebaseSyncFragment();
        fragmentManager.beginTransaction()
                .add(containerId, firebaseSyncFragment)
                .commitAllowingStateLoss();
    }

    public void navigateToList() {
        UserListFragment userListFragment = new UserListFragment();
        fragmentManager.beginTransaction()
                .replace(containerId,userListFragment)
                .commitAllowingStateLoss();
    }

    public void navigateToChat(String uid, String email) {
        ChatFragment chatFragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString("uid", uid);
        args.putString("email",email);
        chatFragment.setArguments(args);
        fragmentManager.beginTransaction()
                .replace(containerId,chatFragment)
                .addToBackStack("chatFragment")
                .commitAllowingStateLoss();
    }

    void navigateToProfile() {
        ProfileFragment profileFragment = new ProfileFragment();
        fragmentManager.beginTransaction()
                .replace(containerId,profileFragment)
                .addToBackStack("chatFragment")
                .commitAllowingStateLoss();
    }
}