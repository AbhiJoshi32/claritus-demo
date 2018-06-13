package com.example.claritus.claritus.main.FirebaseSync;


import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.claritus.claritus.R;
import com.example.claritus.claritus.auth.AuthActivity;
import com.example.claritus.claritus.databinding.FragmentFirebaseSyncBinding;
import com.example.claritus.claritus.db.UserDao;
import com.example.claritus.claritus.di.Injectable;
import com.example.claritus.claritus.main.MainNavigationController;
import com.example.claritus.claritus.model.user.User;
import com.example.claritus.claritus.utils.AppExecutors;
import com.example.claritus.claritus.utils.AutoClearedValue;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import javax.inject.Inject;

import timber.log.Timber;

public class FirebaseSyncFragment extends Fragment implements Injectable{
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference userRef;
    private AutoClearedValue<FragmentFirebaseSyncBinding> binding;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    MainNavigationController navigationController;
    @Inject
    UserDao userDao;
    @Inject
    AppExecutors appExecutors;
    public FirebaseSyncFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference().child("User");
        FragmentFirebaseSyncBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_firebase_sync,
                container, false);
        binding = new AutoClearedValue<>(this,dataBinding);
        return dataBinding.getRoot();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.get().firebaseStatus.setText(R.string.firebase_config);
        String email = sharedPreferences.getString("email","");
        if (!email.equals("")) {
            firebaseAuth.fetchProvidersForEmail(email)
                    .addOnCompleteListener(task1 -> {
                        try {
                            if (task1.getResult().getProviders() == null) {
                                logout();
                            } else {
                                boolean check = task1.getResult().getProviders().isEmpty();
                                if (check) {
                                    User user = userDao.findByEmailSync(email);
                                    if (user != null) {
                                        firebaseAuth.createUserWithEmailAndPassword(email, "pass@123")
                                                .addOnCompleteListener(Objects.requireNonNull(getActivity()), task -> {
                                                    binding.get().progressBar2.setVisibility(View.GONE);
                                                    if (task.isSuccessful()) {
                                                        FirebaseUser fuser = task.getResult().getUser();
                                                        Timber.d(fuser.getUid());
                                                        user.setUid(fuser.getUid());
                                                        appExecutors.diskIO().execute(() -> userDao.insert(user));
                                                        sharedPreferences.edit().putBoolean("firebaseSync", true).apply();
                                                        database.getReference().child("Users").child(fuser.getUid()).setValue(user);
                                                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");
                                                        userRef.keepSynced(true);
                                                        DatabaseReference msgRef = FirebaseDatabase.getInstance().getReference("Messages").child(fuser.getUid());
                                                        msgRef.keepSynced(true);
                                                        navigationController.navigateToList();
                                                    } else {
                                                        binding.get().firebaseStatus.setText(R.string.firebase_sync_error);
                                                    }
                                                });
                                    } else {
                                        logout();
                                    }
                                } else {
                                    firebaseAuth.signInWithEmailAndPassword(email, "pass@123").addOnCompleteListener(Objects.requireNonNull(getActivity()), task -> {
                                        binding.get().progressBar2.setVisibility(View.GONE);
                                        if (task.isSuccessful()) {
                                            sharedPreferences.edit().putBoolean("firebaseSync", true).apply();

                                            navigationController.navigateToList();
                                        } else {
                                            Timber.d("Cant login with firebase");
                                        }
                                    });
                                }
                            }
                        } catch (Exception e) {e.printStackTrace();logout();}
                    });
        } else {
            logout();
        }
    }

    private void logout() {
        Timber.d("User not properly signedIn");
        sharedPreferences.edit().putBoolean("isLoggedIn",false).apply();
        startActivity(new Intent(getActivity(), AuthActivity.class));
    }
}
