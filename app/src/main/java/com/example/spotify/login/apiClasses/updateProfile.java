package com.example.spotify.login.apiClasses;

import com.google.gson.annotations.SerializedName;

public class updateProfile {
    @SerializedName("Email")
    String Email;
    @SerializedName("Password")
    String Password;
    @SerializedName("Country")
    String Country;
    @SerializedName("Display_Name")
    String Display_Name;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getDisplay_Name() {
        return Display_Name;
    }

    public void setDisplay_Name(String display_Name) {
        Display_Name = display_Name;
    }
}
