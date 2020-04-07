package com.example.spotify.login.apiClasses;

import com.google.gson.annotations.SerializedName;

public class FacebookLoginData {
    @SerializedName("email")
    String email;
    @SerializedName("name")
    String name;
    @SerializedName("gender")
    String gender;
    @SerializedName("photos")
    String photos;
    @SerializedName("birthday")
    String birthday;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
