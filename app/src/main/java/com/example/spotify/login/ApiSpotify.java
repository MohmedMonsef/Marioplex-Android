package com.example.spotify.login;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiSpotify {
    @POST("Login")
    Call<Token> login(@Body LoginCredentials loginCredentials);

    @POST("sign_up")
    Call<ResponseBody> signUp(@Body SignUpData signUpData);
}