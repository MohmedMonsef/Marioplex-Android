package com.example.spotify.login;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.login.apiClasses.userProfile;
import com.example.spotify.pojo.playlist;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class user {
    static String name = null;
    static String id = null;
    static String email = null;
    static String dateOfBirth = null;
    static String gender = null;
    static String country = null;
    static String product = null;
    static String password = null;
    static String [] images = null;
    static playlist[] playlists = null;

    static String token = null;

    private user() {

    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        user.name = name;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        user.id = id;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        user.email = email;
    }

    public static String getDateOfBirth() {
        return dateOfBirth;
    }

    public static void setDateOfBirth(String dateOfBirth) {
        user.dateOfBirth = dateOfBirth;
    }

    public static String getGender() {
        return gender;
    }

    public static void setGender(String gender) {
        user.gender = gender;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        user.token = token;
    }

    public static String getCountry() {
        return country;
    }

    public static void setCountry(String country) {
        user.country = country;
    }

    public static String getProduct() {
        return product;
    }

    public static void setProduct(String product) {
        user.product = product;
    }

    public static String[] getImages() {
        return images;
    }

    public static void setImages(String[] images) {
        user.images = images;
    }

    public static playlist[] getPlaylists() {
        return playlists;
    }

    public static void setPlaylists(playlist[] playlists) {
        user.playlists = playlists;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        user.password = password;
    }

    static public void fetchUserData(){
        if(token == null)
            return;

        ApiSpotify apiSpotify = MainActivity.getApiSpotify();
        Retrofit retrofit;

        if(apiSpotify == null){
            retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.35:3000").addConverterFactory(GsonConverterFactory.create()).build();
            apiSpotify = retrofit.create(ApiSpotify.class);
        }

        apiSpotify.profile(token).enqueue(new Callback<ArrayList<userProfile>>() {
            @Override
            public void onResponse(Call<ArrayList<userProfile>> call, Response<ArrayList<userProfile>> response) {
                if(response.isSuccessful()){
                    user.setToken(token);
                    user.setName(response.body().get(0).getDisplayName());
                    user.setEmail(response.body().get(0).getEmail());
                    user.setDateOfBirth(response.body().get(0).getBirthDate());
                    user.setGender(response.body().get(0).getGender());
                    user.setCountry(response.body().get(0).getCountry());
                    user.setProduct(response.body().get(0).getProduct());
                    user.setImages(response.body().get(0).getImages());
                }
                else {
                    Log.v("usrftch",response.message().toString());
                    int x = 4;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<userProfile>> call, Throwable t) {
                //Toast.makeText(getContext(),"Failed to connect",Toast.LENGTH_SHORT).show();
            }
        });

        apiSpotify.myPlaylists(token).enqueue(new Callback<playlist[]>() {
            @Override
            public void onResponse(Call<playlist[]> call, Response<playlist[]> response) {
                user.setPlaylists(response.body());
            }

            @Override
            public void onFailure(Call<playlist[]> call, Throwable t) {
                int x=5;
            }
        });



    }
}
