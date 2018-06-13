
package com.example.claritus.claritus.model.user;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @SerializedName("uid")
    private String uid;
    @SerializedName("address")
    private String Address;
    @SerializedName("city")
    private String City;
    @SerializedName("country")
    private String Country;
    @SerializedName("email")
    private String Email;
    @SerializedName("first_name")
    private String FirstName;
    @SerializedName("gender")
    private String Gender;
    @SerializedName("image_path")
    private String ImagePath;
    @SerializedName("info1")
    private String Info1;
    @SerializedName("info2")
    private String Info2;
    @SerializedName("info3")
    private String Info3;
    @SerializedName("info4")
    private String Info4;
    @SerializedName("info5")
    private String Info5;
    @SerializedName("info6")
    private String Info6;
    @SerializedName("last_name")
    private String LastName;
    @SerializedName("marital_status")
    private String MaritalStatus;
    @SerializedName("phone")
    private String Phone;
    @SerializedName("state")
    private String State;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getInfo1() {
        return Info1;
    }

    public void setInfo1(String info1) {
        Info1 = info1;
    }

    public String getInfo2() {
        return Info2;
    }

    public void setInfo2(String info2) {
        Info2 = info2;
    }

    public String getInfo3() {
        return Info3;
    }

    public void setInfo3(String info3) {
        Info3 = info3;
    }

    public String getInfo4() {
        return Info4;
    }

    public void setInfo4(String info4) {
        Info4 = info4;
    }

    public String getInfo5() {
        return Info5;
    }

    public void setInfo5(String info5) {
        Info5 = info5;
    }

    public String getInfo6() {
        return Info6;
    }

    public void setInfo6(String info6) {
        Info6 = info6;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getMaritalStatus() {
        return MaritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        MaritalStatus = maritalStatus;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

}
