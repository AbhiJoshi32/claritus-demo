package com.example.claritus.claritus.main.Profile;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.claritus.claritus.R;
import com.example.claritus.claritus.auth.AuthActivity;
import com.example.claritus.claritus.databinding.ProfileFragmentBinding;
import com.example.claritus.claritus.di.Injectable;
import com.example.claritus.claritus.model.user.User;
import com.example.claritus.claritus.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import javax.inject.Inject;

public class ProfileFragment extends Fragment implements Injectable {
    private ProfileFragmentBinding dataBinding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    @Inject
    UserRepository userRepository;
    private User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         dataBinding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment,
                container, false);
         return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (auth.getCurrentUser() == null) {
            logout();
        }
        else {
            userRepository.getUser(auth.getCurrentUser().getEmail()).observe(Objects.requireNonNull(getActivity()), this::updateUI);
            dataBinding.logout.setOnClickListener(view -> logout());
        }
    }

    private void logout() {
        userRepository.logout();
        auth.signOut();
        startActivity(new Intent(getActivity(), AuthActivity.class));
    }

    private void updateUI(User user1) {
        user = user1;
        dataBinding.name.setText(user1.getFirstName());
        dataBinding.phone.setText(user1.getPhone());
        dataBinding.address.setText(user1.getAddress());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.profile_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save) {
            user.setUid(Objects.requireNonNull(auth.getCurrentUser()).getUid());
            user.setFirstName(dataBinding.name.getText().toString());
            user.setAddress(dataBinding.address.getText().toString());
            user.setPhone(dataBinding.phone.getText().toString());
            userRepository.saveUser(user);
            database.getReference().child("Users").child(auth.getCurrentUser().getUid()).setValue(user);
        }
        return true;
    }
}