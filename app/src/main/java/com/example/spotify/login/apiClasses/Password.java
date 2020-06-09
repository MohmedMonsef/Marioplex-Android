package com.example.spotify.login.apiClasses;

import com.google.gson.annotations.SerializedName;

public class Password {
    @SerializedName("password")
    String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
