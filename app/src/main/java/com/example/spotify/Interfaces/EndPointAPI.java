package com.example.spotify.Interfaces;

import com.example.spotify.SpotifyClasses.Track;
import com.example.spotify.SpotifyClasses.UserPlaylists;
import com.example.spotify.pojo.currentTrack;
import com.example.spotify.pojo.playlist;

import okhttp3.ResponseBody;
import retrofit2.Call;
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



}
