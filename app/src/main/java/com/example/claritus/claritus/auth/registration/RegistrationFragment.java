package com.example.claritus.claritus.auth.registration;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.claritus.claritus.R;
import com.example.claritus.claritus.auth.NavigationController;
import com.example.claritus.claritus.databinding.FragmentLoginBinding;
import com.example.claritus.claritus.di.Injectable;
import com.example.claritus.claritus.main.MainActivity;
import com.example.claritus.claritus.model.Status;
import com.example.claritus.claritus.utils.AutoClearedValue;

import javax.inject.Inject;

import timber.log.Timber;

public class RegistrationFragment extends Fragment implements Injectable {
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    NavigationController navigationController;
    private AutoClearedValue<FragmentLoginBinding> binding;

    RegistrationViewModel registrationViewModel;

    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentLoginBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login,
                container, false);
        binding = new AutoClearedValue<>(this,dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registrationViewModel = ViewModelProviders.of(this, viewModelFactory).get(RegistrationViewModel.class);
        registrationViewModel.getToken().observe(this, token->{
            Timber.d("Got a res");
            if (token != null) {
                if (token.status == Status.SUCCESS)
                    startActivity(new Intent(getContext(), MainActivity.class));
                else Timber.d(token.message);
            }
        });
        binding.get().button.setOnClickListener(view -> {
            String email = binding.get().emailEdit.getText().toString();
            String password = binding.get().passEdit.getText().toString();
            registrationViewModel.setEmail(email);
            registrationViewModel.setPassword(password);
        });
    }
}
