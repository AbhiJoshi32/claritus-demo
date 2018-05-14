package com.example.claritus.claritus.auth.registration;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.claritus.claritus.R;
import com.example.claritus.claritus.auth.AuthNavigationController;
import com.example.claritus.claritus.di.Injectable;

import com.example.claritus.claritus.databinding.FragmentRegisterBinding;
import com.example.claritus.claritus.model.Status;
import com.example.claritus.claritus.utils.AutoClearedValue;

import javax.inject.Inject;

import timber.log.Timber;

public class RegistrationFragment extends Fragment implements Injectable {
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    AuthNavigationController authNavigationController;
    private AutoClearedValue<FragmentRegisterBinding> binding;

    RegistrationViewModel registrationViewModel;


    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentRegisterBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register,
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
                    authNavigationController.navigateToVerification();
                else Timber.d(token.message);
            }
        });
        binding.get().setViewModel(registrationViewModel);
        binding.get().registerButton.setOnClickListener(view -> {
            registrationViewModel.onRegisterBtnClicked();
        });
    }
}
