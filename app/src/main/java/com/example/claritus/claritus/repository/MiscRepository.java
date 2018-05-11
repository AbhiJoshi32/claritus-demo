package com.example.claritus.claritus.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.content.SharedPreferences;

import com.example.claritus.claritus.api.ClaritusService;
import com.example.claritus.claritus.db.UserDao;
import com.example.claritus.claritus.model.AuthorizeResponse;
import com.example.claritus.claritus.model.Resource;
import com.example.claritus.claritus.utils.AppExecutors;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

@Singleton
public class MiscRepository {
    private final ClaritusService claritusService;
    private final AppExecutors appExecutors;
    private final SharedPreferences sharedPreferences;

    @Inject
    MiscRepository(AppExecutors appExecutors, ClaritusService claritusService,SharedPreferences sharedPreferences) {
        this.claritusService = claritusService;
        this.appExecutors = appExecutors;
        this.sharedPreferences = sharedPreferences;
//        sharedPreferences.edit().putString("deviceToken","asdsd").apply();
    }

    public LiveData<Resource<String>> getAuthorizationToken(String deviceId, String deviceToken) {
        String token = sharedPreferences.getString("deviceToken","");
        MutableLiveData<Resource<String>> tokenLiveData = new MutableLiveData<>();
        if (token.equals("")) {
            return fetchTokenFromApi(deviceId,deviceToken);
        } else {
            tokenLiveData.setValue(Resource.success(token));
        }
        return tokenLiveData;
    }

    private LiveData<Resource<String>> fetchTokenFromApi(String deviceId, String deviceToken) {
        MutableLiveData<Resource<String>> tokenMutableLiveData = new MutableLiveData<>();
        Call<AuthorizeResponse> call= claritusService.getAuthorize(deviceId,"Core123","300001",deviceToken);
        call.enqueue(new Callback<AuthorizeResponse>() {
            @Override
            public void onResponse(Call<AuthorizeResponse> call, Response<AuthorizeResponse> response) {
                Timber.d("Got a response");
                if (response.isSuccessful()) {
                    Timber.d(response.body().toString());
                    AuthorizeResponse authorizeResponse = response.body();
                    tokenMutableLiveData.setValue(Resource.success(authorizeResponse.getApiCurrentToken()));
                    sharedPreferences.edit().putString("deviceToken",authorizeResponse.getData().getAPICURRENTTOKEN()).apply();
                } else {
                    String message = null;
                    if (response.errorBody() != null) {
                        try {
                            message = response.errorBody().string();
                        } catch (IOException ignored) {
                            Timber.e(ignored, "error while parsing response");
                        }
                    }
                    if (message == null || message.trim().length() == 0) {
                        message = response.message();
                    }
                    tokenMutableLiveData.setValue(Resource.error(message,null));
                }

            }
            @Override
            public void onFailure(Call<AuthorizeResponse> call, Throwable t){
                Timber.d("Failed response");
                tokenMutableLiveData.setValue(Resource.error(t.getMessage(),null));
                //Handle on Failure here
            }
        });
        return tokenMutableLiveData;
    }
}
