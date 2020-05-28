package com.example.spotify.Interfaces;

import com.example.spotify.BackClasses.Backclasses.backcategory.Category;
import com.example.spotify.login.apiClasses.FacebookLoginData;
import com.example.spotify.login.apiClasses.LoginCredentials;
import com.example.spotify.login.apiClasses.LoginResponse;
import com.example.spotify.login.apiClasses.SignUpData;
import com.example.spotify.login.apiClasses.forgotPasswordEmail;
import com.example.spotify.login.apiClasses.updateProfile;
import com.example.spotify.login.apiClasses.userProfile;
import com.example.spotify.login.user;

import java.util.ArrayList;
import com.example.spotify.pojo.BasicPlaylist;
import com.example.spotify.pojo.PlaylistTracks;
import com.example.spotify.pojo.addTrackToPlaylistBody;
import com.example.spotify.pojo.currentTrack;
import com.example.spotify.pojo.playlist;
import com.example.spotify.pojo.createPlaylistBody;

import java.util.List;

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
    @POST("api/Login")
    Call<LoginResponse> login(@Body LoginCredentials loginCredentials);

    @POST("api/sign_up")
    Call<LoginResponse> signUp(@Body SignUpData signUpData);

    @GET("api/me")
    Call<ArrayList<userProfile>> profile(@Header("x-auth-token") String token);

    @PUT("api/me/update")
    Call<ResponseBody> updateProfile(@Header("x-auth-token") String token,@Body updateProfile data);

    @GET("api/me/playlists")
    Call<playlist[]> myPlaylists(@Header("x-auth-token") String token);

    @POST("api/auth/facebookAndroid")
    Call<LoginResponse> facebookLogin(@Body FacebookLoginData facebookLoginData);

    @POST("api/login/forgetpassword")
    Call<ResponseBody> forgetPassword(@Body forgotPasswordEmail email);


    ////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////home requests//////////////////////////////////////////



    ////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////media player requests//////////////////////////////////////

//    @GET("v1/tracks/3n3Ppam7vgaVa1iaRUc9Lp")
//    @Headers("Authorization: Bearer "+token)
//    Call<Track> getATrack();
//
//    @GET("v1/tracks")
//    @Headers("Authorization: Bearer "+token)
//    Call<Tracks> getTracks(@Query("ids") String tracksid);



//    @PUT("me/like")
//    //@Headers("Authorization: Bearer "+token)
//    Call<ResponseBody> LikeATrack(@Query("id") String id);

//    @DELETE("me/unlike")
//    //@Headers("Authorization: Bearer "+token)
//    Call<ResponseBody> unLikeATrack(@Query("id") String id);

    //////////////////////////////////our api requests//////////////////////////////////////////
    /////////you need to change the Base URL//////////////////
    @POST("api/me/player/next-playing")
    //@Headers("Authorization: Bearer "+token)
    Call<currentTrack> getNext(@Header("x-auth-token")String token1);
    //Call<currentTrack> getNext(@Header("Authorization")String token1);

    @POST("api/me/player/prev-playing")
    //@Headers("Authorization: Bearer "+token)
    Call<currentTrack> getPrevious(@Header("x-auth-token")String token1);

    @GET("api/me/player/currently-playing")
    //@Headers("Authorization: Bearer "+token)
    Call<currentTrack> getCurrentlyPlaying(@Header("x-auth-token")String token1);

//    @GET("users/{user_id}/playlists")
//    //@Headers("Authorization: Bearer "+token)
//    Call<List<BasicPlaylist>> getCurrentUserPlaylists(@Path("user_id") String userID ,
//                                                      @Header("x-auth-token")String token1);

@GET("api/me/playlists")
    //@Headers("Authorization: Bearer "+token)
    Call<List<BasicPlaylist>> getCurrentUserPlaylists(@Header("x-auth-token")String token1);


    @POST("api/playlists/{playlist_id}/tracks")
    //@Headers("Authorization: Bearer "+token)
    Call<Object> AddTrackToAPlaylist(@Path("playlist_id") String playlistID
                                      ,@Body addTrackToPlaylistBody t
                                      ,@Header("x-auth-token")String token1);

    @POST("api/users/playlists")
    //@Headers("Authorization: Bearer "+token)
    Call<playlist> CreatePlaylist(@Body createPlaylistBody c
                                 ,@Header("x-auth-token")String token1); //check if fiels of Query


    @PUT("api/me/player/shuffle")
    Call<Void> toggleShuffle(@Query("state") Boolean state , @Header("x-auth-token")String token1);

    @POST("api/createQueue/{playlist_id}/{trackid}")
    Call<Void> CreateQueue(@Path("playlist_id") String playlist_id ,
                             @Path("trackid") String track_id ,
                             @Query("isPlaylist") Boolean isPlaylist ,
                             @Header("x-auth-token")String token1);


    @GET("api/playlists/{playlist_id}/tracks")
    Call<List<PlaylistTracks>> getPlaylistTracks(@Path("playlist_id") String playlist_id,
                                                @Header("x-auth-token")String token1);

    @PUT("api/me/like/{track_id}")
    Call<Void> LikeTrack(@Path("track_id") String track_id ,
                         @Header("x-auth-token")String token1);

    @DELETE("api/me/unlike/{track_id}")
    Call<Void> UNLikeTrack(@Path("track_id") String track_id ,
                           @Header("x-auth-token")String token1);


    @DELETE("api/playlists/{playlist_id}/followers")
    Call<Void> UNLikePlaylist(@Path("playlist_id") String playlistID ,
                              @Header("x-auth-token")String token1);

    @PUT("api/playlists/{playlist_id}/followers")
    Call<Void> LikePlaylist(@Path("playlist_id") String playlistID ,
                            @Header("x-auth-token")String token1);



    @GET("api/browse/categories?country=SE&locale=sv_SE&limit=10&offset=5")
    @Headers("Authorization: Bearer "+token)
    public Call<Category> getCategories();


}
