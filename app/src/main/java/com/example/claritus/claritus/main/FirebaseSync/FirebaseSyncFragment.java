package com.example.claritus.claritus.main.FirebaseSync;


import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.claritus.claritus.R;
import com.example.claritus.claritus.auth.AuthActivity;
import com.example.claritus.claritus.databinding.FragmentFirebaseSyncBinding;
import com.example.claritus.claritus.db.UserDao;
import com.example.claritus.claritus.main.MainNavigationController;
import com.example.claritus.claritus.utils.AutoClearedValue;

import javax.inject.Inject;

import timber.log.Timber;

public class FirebaseSyncFragment extends Fragment {
//    FirebaseAuth firebaseAuth;
//    FirebaseDatabase database;

    private AutoClearedValue<FragmentFirebaseSyncBinding> binding;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    MainNavigationController navigationController;
    @Inject
    UserDao userDao;
    public FirebaseSyncFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        firebaseAuth = FirebaseAuth.getInstance();
//        database = FirebaseDatabase.getInstance();
        // Inflate the layout for this fragment
        FragmentFirebaseSyncBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login,
                container, false);
        binding = new AutoClearedValue<>(this,dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.get().firebaseStatus.setText("Configuring with firebase");
        String email = sharedPreferences.getString("email","");

//        if (!email.equals("")) {
//            userDao.findByEmail(email).observe(this, user -> {
//                if (user != null) {
//                    firebaseAuth.createUserWithEmailAndPassword(email, "pass@123")
//                            .addOnCompleteListener(getActivity(), task -> {
//                                binding.get().progressBar2.setVisibility(View.GONE);
//                                if (task.isSuccessful()) {
//                                    database.getReference().child("Users").push().setValue(user);
//
//                                    navigationController.navigateToList();
//                                } else {
//                                    binding.get().firebaseStatus.setText("Some error occured");
//                                }
//                                // ...
//                            });
//                } else {
//                    logout();
//                }
//            });
//        } else {
//            logout();
//        }
    }

    private void logout() {
        Timber.d("User not properly signedIn");
        sharedPreferences.edit().putBoolean("isLoggedIn",false).apply();
        startActivity(new Intent(getActivity(), AuthActivity.class));

    }
}
