package com.example.claritus.claritus.auth.login;


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
import com.example.claritus.claritus.auth.NavigationController;
import com.example.claritus.claritus.databinding.FragmentLoginBinding;
import com.example.claritus.claritus.di.Injectable;
import com.example.claritus.claritus.utils.AutoClearedValue;

import javax.inject.Inject;

public class LoginFragment extends Fragment implements Injectable {
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    NavigationController navigationController;
    private AutoClearedValue<FragmentLoginBinding> binding;

    LoginViewModel loginViewModel;

    public LoginFragment() {
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
        loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);
        binding.get().button.setOnClickListener(view -> {
            String email = binding.get().emailEdit.getText().toString();
            String password = binding.get().passEdit.getText().toString();
            loginViewModel.setEmail(email);
            loginViewModel.setPassword(password);
        });

    }
}
