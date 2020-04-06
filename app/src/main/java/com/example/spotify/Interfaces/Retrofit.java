package com.example.spotify.Interfaces;

import com.example.spotify.media.TrackInfo;

import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {
    private static Retrofit instance;
    private retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
            .baseUrl("http://192.168.1.35:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private EndPointAPI endPointAPI = retrofit.create(EndPointAPI.class);
    //private ApiSpotify apiSpotify = retrofit.create(ApiSpotify .class);
    private classinterface apiService = retrofit.create(classinterface.class);


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
    public classinterface getclassinterface(){
        return apiService;
    }

}
