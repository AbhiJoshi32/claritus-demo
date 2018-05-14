/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.claritus.claritus.auth;

import android.support.v4.app.FragmentManager;

import com.example.claritus.claritus.R;
import com.example.claritus.claritus.auth.login.LoginFragment;
import com.example.claritus.claritus.auth.registration.RegistrationFragment;
import com.example.claritus.claritus.auth.verification.VerificationFragment;

import javax.inject.Inject;


public class AuthNavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;
    @Inject
    public AuthNavigationController(AuthActivity authActivity) {
        this.containerId = R.id.authContainer;
        this.fragmentManager = authActivity.getSupportFragmentManager();
    }

    public void navigateToLogin() {
        LoginFragment loginFragment = new LoginFragment();
        fragmentManager.beginTransaction()
                .replace(containerId, loginFragment)
                .commitAllowingStateLoss();
    }

    public void navigateToRegistration() {
        RegistrationFragment registrationFragment = new RegistrationFragment();
        fragmentManager.beginTransaction()
                .replace(containerId, registrationFragment)
                .commitAllowingStateLoss();
    }

    public void navigateToVerification() {
        VerificationFragment verificationFragment = new VerificationFragment();
        fragmentManager.beginTransaction()
                .replace(containerId, verificationFragment)
                .commitAllowingStateLoss();
    }

//    public void navigateToRepo(String owner, String name) {
//        RepoFragment fragment = RepoFragment.create(owner, name);
//        String tag = "repo" + "/" + owner + "/" + name;
//        fragmentManager.beginTransaction()
//                .replace(containerId, fragment, tag)
//                .addToBackStack(null)
//                .commitAllowingStateLoss();
//    }
}
