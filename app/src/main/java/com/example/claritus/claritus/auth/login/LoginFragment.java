package com.example.claritus.claritus.auth.login;


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
import com.example.claritus.claritus.auth.AuthNavigationController;
import com.example.claritus.claritus.databinding.FragmentLoginBinding;
import com.example.claritus.claritus.di.Injectable;
import com.example.claritus.claritus.main.MainActivity;
import com.example.claritus.claritus.model.Status;
import com.example.claritus.claritus.utils.AutoClearedValue;

import javax.inject.Inject;

import timber.log.Timber;

public class LoginFragment extends Fragment implements Injectable {
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    AuthNavigationController authNavigationController;
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
        loginViewModel.getToken().observe(this,token->{
            Timber.d("Got a res");
            if (token != null) {
                if (token.status == Status.SUCCESS)
                    Timber.d(token.data);
                else {
                    Timber.d(token.message);
                    if (token.message.equals("not verified")) {
                        authNavigationController.navigateToVerification();
                    }
                }
            }
        });

        loginViewModel.getUser().observe(this,userResource -> {
            Timber.d("Got a res for user");
            if (userResource != null) {
                if (userResource.status == Status.SUCCESS)
                    startActivity(new Intent(getContext(), MainActivity.class));
                else {
                    Timber.d(userResource.message);
                }
            }
        });
        binding.get().button.setOnClickListener(view -> {
            String email = binding.get().emailEdit.getText().toString();
            String password = binding.get().passEdit.getText().toString();
            loginViewModel.setEmail(email);
            loginViewModel.setPassword(password);
        });
        binding.get().registerText.setOnClickListener(view -> {
            authNavigationController.navigateToRegistration();
        });
    }
}
