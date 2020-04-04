package com.example.spotify.login;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiSpotify {
    @POST("Login")
    Call<LoginResponse> login(@Body LoginCredentials loginCredentials);

    @POST("sign_up")
    Call<ResponseBody> signUp(@Body SignUpData signUpData);

    @GET("me")
    Call<ArrayList<userProfile>> profile(@Header("x-auth-token") String token);
}