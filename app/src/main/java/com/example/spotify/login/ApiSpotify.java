package com.example.spotify.login;

import com.example.spotify.login.apiClasses.LoginCredentials;
import com.example.spotify.login.apiClasses.LoginResponse;
import com.example.spotify.login.apiClasses.SignUpData;
import com.example.spotify.login.apiClasses.FacebookLoginData;
import com.example.spotify.login.apiClasses.updateProfile;
import com.example.spotify.login.apiClasses.userProfile;
import com.example.spotify.pojo.playlist;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiSpotify {
    @POST("Login")
    Call<LoginResponse> login(@Body LoginCredentials loginCredentials);

    @POST("sign_up")
    Call<ResponseBody> signUp(@Body SignUpData signUpData);

    @GET("me")
    Call<ArrayList<userProfile>> profile(@Header("x-auth-token") String token);

    @PUT("me/update")
    Call<ResponseBody> updateProfile(@Header("x-auth-token") String token,@Body updateProfile data);

    @GET("me/playlists")
    Call<playlist[]> myPlaylists(@Header("x-auth-token") String token);

    @POST("auth/facebookAndroid")
    Call<LoginResponse> facebookLogin(@Body FacebookLoginData facebookLoginData);

}              