package com.example.spotify.login.apiClasses;

import com.google.gson.annotations.SerializedName;

public class userProfile {
    @SerializedName("_id")
    String id;
    @SerializedName("email")
    String email;
    @SerializedName("displayName")
    String displayName;
    @SerializedName("gender")
    String gender;
    @SerializedName("country")
    String country;
    @SerializedName("birthDate")
    String birthDate;
    @SerializedName("product")
    String product;
    @SerializedName("images")
    String [] images;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }
}
