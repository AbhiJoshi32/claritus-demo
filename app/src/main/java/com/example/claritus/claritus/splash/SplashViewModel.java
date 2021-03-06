package com.example.claritus.claritus.splash;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.claritus.claritus.model.Resource;
import com.example.claritus.claritus.repository.MiscRepository;
import com.example.claritus.claritus.repository.UserRepository;
import com.example.claritus.claritus.utils.AbsentLiveData;
import com.example.claritus.claritus.utils.Objects;

import javax.inject.Inject;

import timber.log.Timber;

@SuppressWarnings("SameParameterValue")
public class SplashViewModel extends ViewModel {
    private final UserRepository userRepository;
    private LiveData<Resource<String>> authTokenLiveData;
    private final MutableLiveData<AndroidAuth> androidAuthMutableLiveData = new MutableLiveData<>();

    @Inject
    SplashViewModel(MiscRepository miscRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        authTokenLiveData = Transformations.switchMap(androidAuthMutableLiveData,
                androidAuth -> {
                    Timber.d("AuthorizeData changed in andAuth");
                    if (androidAuthMutableLiveData.getValue() != null
                                && androidAuthMutableLiveData.getValue().getId() != null
                            && androidAuthMutableLiveData.getValue().getToken() != null) {
                        Timber.d("calling misc Repo");
                        return miscRepository.getAuthorizationToken(androidAuth.getId(),
                                androidAuth.getToken());
                    }
                    else return AbsentLiveData.create();
                });
    }

    void setToken(String token) {
        AndroidAuth androidAuth = new AndroidAuth();
        if (androidAuthMutableLiveData.getValue() != null)
            androidAuth = androidAuthMutableLiveData.getValue();
            if (Objects.equals(androidAuth.getToken(),token))
                return;
            androidAuth.setToken(token);
        this.androidAuthMutableLiveData.setValue(androidAuth);
    }

    void setAndroidIdLiveData(String androidId) {
        AndroidAuth androidAuth = new AndroidAuth();
        if (androidAuthMutableLiveData.getValue() != null)
            androidAuth = androidAuthMutableLiveData.getValue();
        if (Objects.equals(androidAuth.getId(),androidId))
            return;
        androidAuth.setId(androidId);
        this.androidAuthMutableLiveData.setValue(androidAuth);
    }

    LiveData<Resource<String>> getAuthTokenLiveData() {
        return authTokenLiveData;
    }

    boolean isLoggedIn() {
        return userRepository.isLoggedIn();
    }
}