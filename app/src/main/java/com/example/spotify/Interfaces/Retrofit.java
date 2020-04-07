package com.example.spotify.Interfaces;

import com.example.spotify.login.ApiSpotify;
import com.example.spotify.media.TrackInfo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {
    private static Retrofit instance;
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();
    private retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
            .baseUrl("http://192.168.1.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();
    private EndPointAPI endPointAPI = retrofit.create(EndPointAPI.class);
    //private ApiSpotify apiSpotify = retrofit.create(ApiSpotify .class);
    //private classinterface apiService = retrofit.create(classinterface.class);


    public static Retrofit getInstance() {
        if(instance == null)
            instance = new Retrofit();
        return instance;
    }

    public retrofit2.Retrofit getRetrofit(){
        return retrofit;
    }
    public EndPointAPI getEndPointAPI(){
        return endPointAPI;
    }
    /*public  ApiSpotify getApiSpotify(){
        return apiSpotify;
    }*/
    /*public EndPointAPI getEndPointAPI(){
        return apiService;
    }

     */

}
