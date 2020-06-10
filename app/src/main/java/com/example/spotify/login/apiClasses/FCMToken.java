package com.example.spotify.login.apiClasses;

public class FCMToken {
    String fcmToken;

    public FCMToken(String token) {
        this.fcmToken = token;
    }

    public String getToken() {
        return fcmToken;
    }

    public void setToken(String token) {
        this.fcmToken = token;
    }
}
