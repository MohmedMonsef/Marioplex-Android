package com.example.spotify.login.apiClasses;

import android.icu.util.Calendar;

import com.google.gson.annotations.SerializedName;

public class SignUpData {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("country")
    private String country;
    @SerializedName("email")
    private String email;
    @SerializedName("gender")
    private String gender;
    @SerializedName("birthday")
    private android.icu.util.Calendar birthday;

    public SignUpData(String username, String password, String country, String email, String gender, android.icu.util.Calendar birthday) {
        this.username = username;
        this.password = password;
        this.country = country;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public android.icu.util.Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }
}
