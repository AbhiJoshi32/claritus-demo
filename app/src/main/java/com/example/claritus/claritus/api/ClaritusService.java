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

package com.example.claritus.claritus.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ClaritusService {
    @GET("parentapi/authorize/index")
    Call<String> getAuthorize(@Header("DeviceId") String devceId,
                                                          @Header("ApiKey") String apiKey,
                                                          @Header("DeviceType") String devceType,
                                                          @Header("DeviceToken") String devceToken);
//    @GET("users/{login}")
//    LiveData<ApiResponse<User>> getUser(@Path("login") String login);
    @FormUrlEncoded
    @POST("user/Api/v1/user/login")
    Call<String> loginUser(@Header("AuthorizedToken") String authToken,
                           @Header("DeviceId") String deviceId,
                           @Field("username") String email,
                           @Field("password") String password);
    @FormUrlEncoded
    @POST("user/Api/v1/user/create")
    Call<String> registerUser(@Header("AuthorizedToken") String authToken,
                              @Header("DeviceId") String deviceId,
                              @Field("password") String password,
                              @Field("email") String email,
                              @Field("last_name") String lastName,
                              @Field("first_name") String firstName,
                              @Field("phone") String phone,
                              @Field("user_type") String userType,
                              @Field("role") String role);

    @GET("user/Api/v1/user/view")
    Call<String> getUser(@Header("AuthorizedToken") String authToken,
                         @Header("DeviceId") String deviceId);
}
