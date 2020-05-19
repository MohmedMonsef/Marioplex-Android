package com.example.spotify.Interfaces;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {
    private static Retrofit instance;
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(200, TimeUnit.SECONDS)
            .writeTimeout(200, TimeUnit.SECONDS)
            .readTimeout(200, TimeUnit.SECONDS)
            .build();
    //http://52.205.254.29/
    private retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
            //.baseUrl("http://52.205.254.29/")
            .baseUrl("http://192.168.1.3:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();
    private EndPointAPI endPointAPI = retrofit.create(EndPointAPI.class);
    private String Baseurl = "http://192.168.1.3:3000/";
    //private ApiSpotify apiSpotify = retrofit.create(ApiSpotify .class);
    //private classinterface apiService = retrofit.create(classinterface.class);


    public static Retrofit getInstance() {
        if(instance == null)
            instance = new Retrofit();
        return instance;
    }

    public String getBaseurl() {
        return Baseurl;
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
