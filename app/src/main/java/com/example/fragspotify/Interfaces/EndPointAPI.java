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
    String token = "BQDi1YBom6loLJPordXPoL0Kxx2-2Hkq0fOyvrriA62NCcKs19l6IRXecP33Kw8BAQMYtr910dR0CYxJ_6KsVsCNBGlueXj7yyrAQueIpVXY-mLZFIZ46xTIFG5dB0f_PsAbc0BeYhpAmna1zmvYFANgi2-Yqydm-fSgALGEY_eOWpM3pASPgi1lMiab2s3tlODnquMwfff26o0R9pqhBt2yBBqJppBdmYxwNkQcllpkOXxAC8_jhD3j5fL85L8d6et9EJDjLHs1N284_OSXG6kf6Owcp0rFnw";
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

    @POST("users/playlists")
    @Headers("Authorization: Bearer "+token)
    Call<playlist> CreatePlaylist(@Field("name") String playlist_name); //check if fiels of Query



}
