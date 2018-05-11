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

package com.example.claritus.claritus.model;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(primaryKeys = "username")
public class User {
    @SerializedName("username")
    @NonNull
    private  String username;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("phoneNo")
    private String phoneNo;

    public User(@NonNull String username, String imageUrl, String name, String email, String phoneNo) {
        this.username = username;
        this.imageUrl = imageUrl;
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public User(LoginResponse loginResponse) {
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }
}