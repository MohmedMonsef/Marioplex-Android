package com.example.spotify.login;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.annotations.SerializedName;


public class user {
    static String name = null;
    static String id = null;
    static String email = null;
    static String dateOfBirth = null;
    static String gender = null;
    static String country = null;
    static String product = null;
    static String [] images = null;

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

    public static String getCountry() {
        return country;
    }

    public static void setCountry(String country) {
        user.country = country;
    }

    public static String getProduct() {
        return product;
    }

    public static void setProduct(String product) {
        user.product = product;
    }

    public static String[] getImages() {
        return images;
    }

    public static void setImages(String[] images) {
        user.images = images;
    }
}
