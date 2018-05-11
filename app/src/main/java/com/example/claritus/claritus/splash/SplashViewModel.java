package com.example.claritus.claritus.splash;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.claritus.claritus.model.AndroidAuth;
import com.example.claritus.claritus.model.Resource;
import com.example.claritus.claritus.repository.MiscRepository;
import com.example.claritus.claritus.utils.AbsentLiveData;
import com.example.claritus.claritus.utils.Objects;

import javax.inject.Inject;

import timber.log.Timber;

public class SplashViewModel extends ViewModel {
    private LiveData<Resource<String>> authTokenLiveData;
    private final MutableLiveData<AndroidAuth> androidAuthMutableLiveData = new MutableLiveData<>();

    @Inject
    public SplashViewModel(MiscRepository miscRepository) {
        authTokenLiveData = Transformations.switchMap(androidAuthMutableLiveData,
                androidAuth -> {
                    Timber.d("Data changed in andAuth");
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

    public void setToken(String token) {
        AndroidAuth androidAuth = new AndroidAuth();
        if (androidAuthMutableLiveData.getValue() != null)
            androidAuth = androidAuthMutableLiveData.getValue();
            if (Objects.equals(androidAuth.getToken(),token))
                return;
            androidAuth.setToken(token);
        this.androidAuthMutableLiveData.setValue(androidAuth);
    }

    public void setAndroidIdLiveData(String androidId) {
        AndroidAuth androidAuth = new AndroidAuth();
        if (androidAuthMutableLiveData.getValue() != null)
            androidAuth = androidAuthMutableLiveData.getValue();
        if (Objects.equals(androidAuth.getId(),androidId))
            return;
        androidAuth.setId(androidId);
        this.androidAuthMutableLiveData.setValue(androidAuth);
    }

    public LiveData<Resource<String>> getAuthTokenLiveData() {
        return authTokenLiveData;
    }
}