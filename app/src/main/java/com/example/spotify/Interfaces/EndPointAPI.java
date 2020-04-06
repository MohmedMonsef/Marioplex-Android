package com.example.spotify.Interfaces;

import com.example.spotify.BackClasses.Backclasses.backcategory.Category;
import com.example.spotify.SpotifyClasses.NewRelease;
import com.example.spotify.SpotifyClasses.SearchClasses.search;
import com.example.spotify.SpotifyClasses.Track;
import com.example.spotify.SpotifyClasses.UserPlaylists;
import com.example.spotify.login.apiClasses.FacebookLoginData;
import com.example.spotify.login.apiClasses.LoginCredentials;
import com.example.spotify.login.apiClasses.LoginResponse;
import com.example.spotify.login.apiClasses.SignUpData;
import com.example.spotify.login.apiClasses.updateProfile;
import com.example.spotify.login.apiClasses.userProfile;
import com.example.spotify.login.user;
import com.example.spotify.pojo.currentTrack;
import com.example.spotify.pojo.playlist;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EndPointAPI {
    String token = "BQDbws7mVPJV0YSZRUNJz1XtQUZTGExfM9qptNtHuQuUti7qx0Lci75BZ6hP2cK0QFpBRoRcFNgU6LZwl6l2cxzSyLk3V7ZumuoSLxVzav1abEDJQYEh6Qkc0t1S02C2hezOWhMITWGUkPxnIiIcfm9RXtMverQZJM2bPWk-FDzwv12vLae0BpB9xc6YDS21iH3SGQf6TxTAkxiGk51vdFBHIezKklCVCimV7B3xQaOeM2-ocTB1pWSA_LtBXsV2LGsI5NOc5ZehD9BoD3lv9jBBeJVwuGbUUA";

    //////////////////////////////login requests//////////////////////////////////////////
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
    ////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////home requests//////////////////////////////////////////
    @GET("v1/browse/new-releases?country=SE&limit=10&offset=0")
    @Headers("Authorization: Bearer BQCuLAJp8vgez0xzrQwqBbbXSqSeZqdff9bJPSPOqEL7OK52W1lN_Ny3dSC38HxAnGr_X7_o5Fw435vJvXCTcNIBEkCanW7Yp-ylFrQWTsUUfw5n4Gz4VIbUFfoyXrmP836lmoXnv4mXB-8WRX5l-E11HXSjOAPP-QFjLXgvNbZ3sPvgiHu8ElQMU7TQfiqF")
    public Call<NewRelease> getNewRelease();


    ////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////media player requests//////////////////////////////////////

//    @GET("v1/tracks/3n3Ppam7vgaVa1iaRUc9Lp")
//    @Headers("Authorization: Bearer "+token)
//    Call<Track> getATrack();
//
//    @GET("v1/tracks")
//    @Headers("Authorization: Bearer "+token)
//    Call<Tracks> getTracks(@Query("ids") String tracksid);

    @GET("v1/me/playlists")
        //@Headers("Authorization: Bearer "+token)
    Call<UserPlaylists> getUserPlaylists();

    @PUT("me/like")
        //@Headers("Authorization: Bearer "+token)
    Call<ResponseBody> LikeATrack(@Query("id") String id);

    @DELETE("me/unlike")
        //@Headers("Authorization: Bearer "+token)
    Call<ResponseBody> unLikeATrack(@Query("id") String id);

    //////////////////////////////////our api requests//////////////////////////////////////////
    /////////you need to change the Base URL//////////////////
    @POST("me/player/next-playing")
    //@Headers("Authorization: Bearer "+token)
    Call<currentTrack> getNext(@Header("x-auth-token")String token1);
    //Call<currentTrack> getNext(@Header("Authorization")String token1);

    @POST("me/player/prev-playing")
        //@Headers("Authorization: Bearer "+token)
    Call<currentTrack> getPrevious(@Header("x-auth-token")String token1);

    @GET("me/player/currently-playing")
        //@Headers("Authorization: Bearer "+token)
    Call<currentTrack> getCurrentlyPlaying(@Header("x-auth-token")String token1);

    @GET("me/playlists")
        //@Headers("Authorization: Bearer "+token)
    Call<Track> getCurrentUserPlaylists(@Header("x-auth-token")String token1);


    @POST("playlists/{playlist_id}/tracks")
        //@Headers("Authorization: Bearer "+token)
    Call<playlist> AddTrackToAPlaylist(@Path("playlist_id") String playlistID , @Field("tracks") String track_id); //check if fiels or Query

    @POST("users/playlists")
        //@Headers("Authorization: Bearer "+token)
    Call<playlist> CreatePlaylist(@Field("name") String playlist_name); //check if fiels of Query


    // requests from classinterface
    @GET("search")
    @Headers("Authorization: Bearer "+token)
    public Call<search> getSearch(@Query("q") String name,
                                  @Query("type") String type,
                                  @Query("market") String market,
                                  @Query("limit") int limit,
                                  @Query("offset") int offset);

    @GET("browse/categories?country=SE&locale=sv_SE&limit=10&offset=5")
    @Headers("Authorization: Bearer "+token)
    public Call<Category> getCategories();



}