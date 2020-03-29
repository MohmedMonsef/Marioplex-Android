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
    String token = "BQDZ8o-klrMWkzXsitayYj1QWplKM1ubUQEORLRGKIHDaAYfbyNSZLkRkUed7KaulZmMQJ2ODO0QaFtGPASXvlP25ReJ8F3VlBq_bh4mWOLo4aJRbvoVLoplJRW7iKM2g1AfCHP-ZwBUQxCu3-aUOwdcfg1nZnmQ14d9nnGd_kH5b4Z3lg2RNDQ5ktPeH4Lj";
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
