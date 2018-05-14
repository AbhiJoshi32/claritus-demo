package com.example.claritus.claritus.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.claritus.claritus.api.ClaritusService;
import com.example.claritus.claritus.auth.login.LoginData;
import com.example.claritus.claritus.auth.registration.RegistrationData;
import com.example.claritus.claritus.db.UserDao;
import com.example.claritus.claritus.model.LoginResponse;
import com.example.claritus.claritus.model.RegisterResponse;
import com.example.claritus.claritus.model.Resource;
import com.example.claritus.claritus.model.user.User;
import com.example.claritus.claritus.model.user.UserResponse;
import com.example.claritus.claritus.utils.AppExecutors;
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
public class UserRepository {
    private final UserDao userDao;
    private final ClaritusService claritusService;
    private final AppExecutors appExecutors;
    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    @Inject
    UserRepository(AppExecutors appExecutors, UserDao userDao, ClaritusService claritusService, SharedPreferences sharedPreferences, Gson gson) {
        this.userDao = userDao;
        this.claritusService = claritusService;
        this.appExecutors = appExecutors;
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
    }

    public LiveData<Resource<String>> loginUser(LoginData loginData) {
        MutableLiveData<Resource<String>> loginLiveData = new MutableLiveData<>();
        String deviceToken = sharedPreferences.getString("deviceToken","");
        String deviceId = sharedPreferences.getString("deviceId","");
        Call<String> call= claritusService.loginUser(deviceToken,deviceId,loginData.getEmail(),loginData.getPassword());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                Timber.d(response.body());
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        if (jsonObject.optString("code").equals("200")) {
                            LoginResponse loginResponse = gson.fromJson(response.body(), LoginResponse.class);
                            loginLiveData.setValue(Resource.success(loginResponse.getMessage()));
                            sharedPreferences.edit().putBoolean("isLoggedIn",true).apply();
                            sharedPreferences.edit().putString("deviceToken",loginResponse.getApiCurrentToken()).apply();
                            sharedPreferences.edit().putString("email",loginData.getEmail()).apply();
                        } else if(jsonObject.optString("code").equals("100")) {
                            loginLiveData.setValue(Resource.error("not verified",null));
                        } else {
                            loginLiveData.setValue(Resource.error(jsonObject.optString("message"),null));
                        }
                    } catch (JSONException e) {
                        loginLiveData.setValue(Resource.error("unable to parse data", null));
                        e.printStackTrace();
                    }
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
                    loginLiveData.setValue(Resource.error(message,null));
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t){
                loginLiveData.setValue(Resource.error(t.getMessage(),null));
                //Handle on Failure here
            }
        });
        return loginLiveData;
    }

    public LiveData<Resource<String>> registerUser(RegistrationData registrationData) {
        MutableLiveData<Resource<String>> registerLiveData = new MutableLiveData<>();
        String deviceToken = sharedPreferences.getString("deviceToken","");
        String deviceId = sharedPreferences.getString("deviceId","");
        Call<String> call= claritusService.registerUser(deviceToken,
                deviceId,
                registrationData.getPassword(),
                registrationData.getEmail(),
                registrationData.getLastName(),
                registrationData.getFirstName(),
                registrationData.getPhone(),
                "150002",
                "100002");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                Timber.d(response.body());
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        sharedPreferences.edit().putString("deviceToken",jsonObject.optString("api_current_token")).apply();
                        if (jsonObject.optString("code").equals("200")) {
                            RegisterResponse registerResponse = gson.fromJson(response.body(), RegisterResponse.class);
                            registerLiveData.setValue(Resource.success(registerResponse.getMessage()));
                            sharedPreferences.edit()    .putBoolean("isLoggedIn",true).apply();
                            sharedPreferences.edit().putString("email",registrationData.getEmail()).apply();
                        } else {
                            registerLiveData.setValue(Resource.error(jsonObject.optString("message"),null));
                        }
                    } catch (JSONException e) {
                        registerLiveData.setValue(Resource.error("unable to parse data", null));
                        e.printStackTrace();
                    }
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
                    registerLiveData.setValue(Resource.error(message,null));
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t){
                registerLiveData.setValue(Resource.error(t.getMessage(),null));
                //Handle on Failure here
            }
        });
        return registerLiveData;
    }

    public LiveData<Resource<User>> loadUser() {
        MutableLiveData<Resource<User>> userLiveData = new MutableLiveData<>();
        String deviceToken = sharedPreferences.getString("deviceToken","");
        String deviceId = sharedPreferences.getString("deviceId","");
        Call<String> call= claritusService.getUser(deviceToken,
                deviceId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                Timber.d(response.body());
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        sharedPreferences.edit().putString("deviceToken",jsonObject.optString("api_current_token")).apply();
                        if (jsonObject.optString("code").equals("200")) {
                            UserResponse userResponse = gson.fromJson(response.body(), UserResponse.class);
                            User user = userResponse.getData();
                            user.setUid("1");
                            userDao.insert(user);
                            userLiveData.setValue(Resource.success(user));
                        } else {
                            userLiveData.setValue(Resource.error(jsonObject.optString("message"),null));
                        }
                    } catch (JSONException e) {
                        userLiveData.setValue(Resource.error("unable to parse data", null));
                        e.printStackTrace();
                    }
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
                    userLiveData.setValue(Resource.error(message,null));
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t){
                userLiveData.setValue(Resource.error(t.getMessage(),null));
                //Handle on Failure here
            }
        });
        return userLiveData;
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean("isLoggedIn",false);
    }

//    public LiveData<Resource<User>> loadUser() {
//        return new NetworkBoundResource<User,User>(appExecutors) {
//            @Override
//            protected void saveCallResult(@NonNull User item) {
//                userDao.insert(item);
//            }
//
//            @Override
//            protected boolean shouldFetch(@Nullable User data) {
//                return data == null;
//            }
//
//            @NonNull
//            @Override
//            protected LiveData<User> loadFromDb() {
//                String email = sharedPreferences.getString("email","");
//                return userDao.findByUsername(email);
//            }
//
//            @NonNull
//            @Override
//            protected LiveData<UserResponse<User>> createCall() {
//                return claritusService.getUser();
//            }
//        }.asLiveData();
//    }
}
