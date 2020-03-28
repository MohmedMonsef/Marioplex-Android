package com.example.fragspotify.Interfaces;

import com.example.fragspotify.SpotifyClasses.Track;
import com.example.fragspotify.SpotifyClasses.Tracks;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface EndPointAPI {
    String token = "BQDRwB_SGS3G2kzqGrEXOZK-N9LuA9qhx0O4sGoAjOVhFbCSqmOxPmMsJQI806ot4V6PjqVHim9gzyhZy4d24Yl8QkpY-9sUNV3D4rj-uLMAqi3Lmh_6iaJ8F-U_P1qindN2hBH1Gwn1Xe7TPqk5wTgTM5FZgJhXoNK9J6UsqycaX6osDnZ4DFV6LOWFgSlk";

    @GET("v1/tracks/3n3Ppam7vgaVa1iaRUc9Lp")
    @Headers("Authorization: Bearer "+token)
    Call<Track> getATrack();

    @GET("v1/tracks")
    @Headers("Authorization: Bearer "+token)
    Call<Tracks> getTracks(@Query("ids") String tracksid);

    @PUT("me/like")
    @Headers("Authorization: Bearer "+token)
    Call<ResponseBody> LikeATrack(@Query("id") String id);

    @DELETE("me/unlike")
    @Headers("Authorization: Bearer "+token)
    Call<ResponseBody> unLikeATrack(@Query("id") String id);


}
