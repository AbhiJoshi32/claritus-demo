package com.example.claritus.claritus.auth.registration;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.claritus.claritus.auth.login.LoginData;
import com.example.claritus.claritus.model.Resource;
import com.example.claritus.claritus.repository.UserRepository;
import com.example.claritus.claritus.utils.AbsentLiveData;

import javax.inject.Inject;

import timber.log.Timber;

public class RegistrationViewModel extends ViewModel {
    private MutableLiveData<RegistrationData> registrationLiveData = new MutableLiveData<>();
    private LiveData<Resource<String>> token;

    @Inject
    RegistrationViewModel(UserRepository userRepository) {
        token = Transformations.switchMap(registrationLiveData, registrationData->{
            if (registrationData != null
                    && registrationData.getEmail()!=null
                    && registrationData.getPassword() != null
                    && registrationData.getPhone() != null
                    && registrationData.getFirstName() != null
                    && registrationData.getLastName() != null)
                return userRepository.registerUser(registrationData);
            else return AbsentLiveData.create();
        });
    }
    public void setEmail(String email) {
        Timber.d("Email set");
        RegistrationData registrationData= new RegistrationData();
        if (registrationLiveData.getValue() != null) {
            registrationData = registrationLiveData.getValue();
        }
        registrationData.setEmail(email);
        registrationLiveData.setValue(registrationData);
    }

    public void setPassword(String password) {
        Timber.d("password set");
        RegistrationData registrationData= new RegistrationData();
        if (registrationLiveData.getValue() != null) {
            registrationData = registrationLiveData.getValue();
        }
        registrationData.setPassword(password);
        registrationLiveData.setValue(registrationData);
    }

    public void setFirstName(String firstName) {
        RegistrationData registrationData= new RegistrationData();
        if (registrationLiveData.getValue() != null) {
            registrationData = registrationLiveData.getValue();
        }
        registrationData.setFirstName(firstName);
        registrationLiveData.setValue(registrationData);
    }

    public void setLastName(String lastName) {
        RegistrationData registrationData= new RegistrationData();
        if (registrationLiveData.getValue() != null) {
            registrationData = registrationLiveData.getValue();
        }
        registrationData.setLastName(lastName);
        registrationLiveData.setValue(registrationData);
    }

    public void setPhone(String phone) {

        RegistrationData registrationData= new RegistrationData();
        if (registrationLiveData.getValue() != null) {
            registrationData = registrationLiveData.getValue();
        }
        registrationData.setPhone(phone);
        registrationLiveData.setValue(registrationData);
    }

    public LiveData<Resource<String>> getToken() {
        return token;
    }
}
