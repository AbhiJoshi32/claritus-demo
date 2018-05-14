package com.example.claritus.claritus.auth.registration;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import com.example.claritus.claritus.model.Resource;
import com.example.claritus.claritus.repository.UserRepository;
import com.example.claritus.claritus.utils.AbsentLiveData;
import javax.inject.Inject;

import timber.log.Timber;

public class RegistrationViewModel extends ViewModel {
    private MutableLiveData<RegistrationData> registrationLiveData = new MutableLiveData<>();
    public final ObservableField<String> email = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();
    public final ObservableField<String> phone = new ObservableField<>();
    public final ObservableField<String> fname = new ObservableField<>();
    public final ObservableField<String> lname = new ObservableField<>();

    public final ObservableField<String> emailError = new ObservableField<>();
    public final ObservableField<String> passwordError = new ObservableField<>();
    public final ObservableField<String> phoneError = new ObservableField<>();
    public final ObservableField<String> fnameError = new ObservableField<>();
    public final ObservableField<String> lnameError = new ObservableField<>();
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

    private boolean validateInputs() {
        boolean isValid = true;
        emailError.set(null);
        passwordError.set(null);
        fnameError.set(null);
        lnameError.set(null);
        phoneError.set(null);
        if (email.get() == null || email.get().isEmpty()) {
            emailError.set("Invalid Email");
            isValid = false;

        } else {
            emailError.set(null);
        }

        if (password.get() == null || password.get().length() < 4) {
            passwordError.set("Password too short");
            isValid = false;
        } else {
            passwordError.set(null);
        }

        if (fname.get() == null || fname.get().isEmpty()) {
            fnameError.set("Cannot be empty");
            isValid = false;
        } else {
            fnameError.set(null);
        }

        if (lname.get() == null) {
            lnameError.set("Password too short");
            isValid = false;
        } else {
            lnameError.set(null);
        }

        if (phone.get() == null || phone.get().length() < 4 || phone.get().isEmpty()) {
            phoneError.set("Password too short");
            isValid = false;
        } else {
            phoneError.set(null);
        }
        return isValid;
    }

    public void onRegisterBtnClicked() {
        if (validateInputs()) {
            RegistrationData registrationData = new RegistrationData(email.get(),password.get(),phone.get(),fname.get(),lname.get());
            registrationLiveData.setValue(registrationData);
        }
    }

//    public void setEmail(String email) {
//        Timber.d("Email set");
//        RegistrationData registrationData= new RegistrationData();
//        if (registrationLiveData.getValue() != null) {
//            registrationData = registrationLiveData.getValue();
//        }
//        registrationData.setEmail(email);
//        registrationLiveData.setValue(registrationData);
//    }
//
//    public void setPassword(String password) {
//        Timber.d("password set");
//        RegistrationData registrationData= new RegistrationData();
//        if (registrationLiveData.getValue() != null) {
//            registrationData = registrationLiveData.getValue();
//        }
//        registrationData.setPassword(password);
//        registrationLiveData.setValue(registrationData);
//    }
//
//    public void setFirstName(String firstName) {
//        RegistrationData registrationData= new RegistrationData();
//        if (registrationLiveData.getValue() != null) {
//            registrationData = registrationLiveData.getValue();
//        }
//        registrationData.setFirstName(firstName);
//        registrationLiveData.setValue(registrationData);
//    }
//
//    public void setLastName(String lastName) {
//        RegistrationData registrationData= new RegistrationData();
//        if (registrationLiveData.getValue() != null) {
//            registrationData = registrationLiveData.getValue();
//        }
//        registrationData.setLastName(lastName);
//        registrationLiveData.setValue(registrationData);
//    }
//
//    public void setPhone(String phone) {
//
//        RegistrationData registrationData= new RegistrationData();
//        if (registrationLiveData.getValue() != null) {
//            registrationData = registrationLiveData.getValue();
//        }
//        registrationData.setPhone(phone);
//        registrationLiveData.setValue(registrationData);
//    }

    public LiveData<Resource<String>> getToken() {
        return token;
    }
}
