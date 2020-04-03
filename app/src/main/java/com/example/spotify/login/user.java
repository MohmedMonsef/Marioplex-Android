package com.example.spotify.login;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.annotations.SerializedName;


public class user {
    @SerializedName("displayName")
    static String name = null;
    @SerializedName("_id")
    static String id = null;
    @SerializedName("email")
    static String email = null;
    @SerializedName("birthDate")
    static String dateOfBirth = null;
    @SerializedName("gender")
    static String gender = null;

    static String token = null;

    private user() {

    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        user.name = name;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        user.id = id;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        user.email = email;
    }

    public static String getDateOfBirth() {
        return dateOfBirth;
    }

    public static void setDateOfBirth(String dateOfBirth) {
        user.dateOfBirth = dateOfBirth;
    }

    public static String getGender() {
        return gender;
    }

    public static void setGender(String gender) {
        user.gender = gender;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        user.token = token;
    }
}
