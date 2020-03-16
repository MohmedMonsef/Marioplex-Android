package com.example.spotify;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiSpotify {
    @POST("Login")
    Call<ResponseBody> login(@Body Credentials credentials);
}