package com.example.spotify.login.apiClasses;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class nameUpdate {
    @SerializedName("user")
    User user;

    public User getUser() {
        return user;
    }

    public nameUpdate(String userName, String password) {
        user = new User(userName,password);
    }

    class User{
        @SerializedName("displayName")
        String diaplayName;
        @SerializedName("password")
        String password;

        public User(String diaplayName, String password) {
            this.diaplayName = diaplayName;
            this.password = password;
        }

        public String getDiaplayName() {
            return diaplayName;
        }

        public String getPassword() {
            return password;
        }
    }
}
