package com.example.fragspotify.Interfaces;

import com.example.fragspotify.SpotifyClasses.Track;
import com.example.fragspotify.SpotifyClasses.Tracks;
import com.example.fragspotify.SpotifyClasses.UserPlaylists;
import com.example.fragspotify.pojo.playlist;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EndPointAPI {
    String token = "BQAoFTN3bIfLC0YWowrIsQC1A-WXQwBtwtvzzUfCUYDhbuUURVo0o6aGaCyZ5BMrLjnzhubbEh3UVE0P4hi3YsKcCevYjLn1A3M8uZRgrFcMEgQxKuSjzVQMt__gKVMC8GF2PX7tPGnuQl_kkMSm9dKGa7VY_lMFeZMHPvskv4dicJjmicFAgwwTB9SW55EZhm-Ofn6tjdFEF2l5-jAjDYbzQwfoVR8SSnT8TxgAVDOUhcLQmU3j1kK1nrU5esIt-nh7yx8wMiM_wS7_tZVfbVIFRyl5yeHOYQ";

    @GET("v1/tracks/3n3Ppam7vgaVa1iaRUc9Lp")
    @Headers("Authorization: Bearer "+token)
    Call<Track> getATrack();

    @GET("v1/tracks")
    @Headers("Authorization: Bearer "+token)
    Call<Tracks> getTracks(@Query("ids") String tracksid);

    @GET("v1/me/playlists")
    @Headers("Authorization: Bearer "+token)
    Call<UserPlaylists> getUserPlaylists();

    @PUT("me/like")
    @Headers("Authorization: Bearer "+token)
    Call<ResponseBody> LikeATrack(@Query("id") String id);

    @DELETE("me/unlike")
    @Headers("Authorization: Bearer "+token)
    Call<ResponseBody> unLikeATrack(@Query("id") String id);

    //our api requests
    @POST("playlists/{playlist_id}/tracks")
    @Headers("Authorization: Bearer "+token)
    Call<playlist> AddTrackToAPlaylist(@Path("playlist_id") String playlistID , @Field("tracks") String track_id); //check if fiels of Query



}
