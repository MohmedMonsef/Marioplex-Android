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
    String token = "BQCuLAJp8vgez0xzrQwqBbbXSqSeZqdff9bJPSPOqEL7OK52W1lN_Ny3dSC38HxAnGr_X7_o5Fw435vJvXCTcNIBEkCanW7Yp-ylFrQWTsUUfw5n4Gz4VIbUFfoyXrmP836lmoXnv4mXB-8WRX5l-E11HXSjOAPP-QFjLXgvNbZ3sPvgiHu8ElQMU7TQfiqF";

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
