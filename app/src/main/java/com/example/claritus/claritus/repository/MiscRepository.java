package com.example.claritus.claritus.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.claritus.claritus.api.ClaritusService;
import com.example.claritus.claritus.model.AuthorizeResponse;
import com.example.claritus.claritus.model.Resource;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

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

    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    @Inject
    MiscRepository(ClaritusService claritusService,SharedPreferences sharedPreferences, Gson gson) {
        this.claritusService = claritusService;
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
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
        Call<String> call= claritusService.getAuthorize(deviceId,"Core123","300001",deviceToken);
        call.enqueue(new Callback<String>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                Timber.d("Got a response");
                if (response.isSuccessful()) {
                    Timber.d(response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        if (jsonObject.optString("code").equals("200")) {
                            AuthorizeResponse authorizeResponse = gson.fromJson(response.body(),AuthorizeResponse.class);
                            tokenMutableLiveData.setValue(Resource.success(authorizeResponse.getApiCurrentToken()));
                            sharedPreferences.edit().putString("deviceToken",authorizeResponse.getAuthorizeData().getAPICURRENTTOKEN()).apply();
                            sharedPreferences.edit().putString("deviceId",deviceId).apply();
                        } else {
                            tokenMutableLiveData.setValue(Resource.error(jsonObject.optString("message"),null));
                        }
                    } catch (JSONException e) {
                        tokenMutableLiveData.setValue(Resource.error("unable to parse data",null));
                        e.printStackTrace();
                    }

                } else {
                    String message = null;
                    if (response.errorBody() != null) {
                        try {
                            message = response.errorBody().string();
                        } catch (IOException e) {
                            Timber.e(e, "error while parsing response");
                        }
                    }
                    if (message == null || message.trim().length() == 0) {
                        message = response.message();
                    }
                    tokenMutableLiveData.setValue(Resource.error(message,null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t){
                Timber.d("Failed response");
                tokenMutableLiveData.setValue(Resource.error(t.getMessage(),null));
                //Handle on Failure here
            }
        });
        return tokenMutableLiveData;
    }
}