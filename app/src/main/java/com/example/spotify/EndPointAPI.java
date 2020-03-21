package com.example.spotify;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface EndPointAPI {
    String token ="BQADVTyluSOWDdqiu1BsdUFRk5EJajJryaBHeUvOBaWOWAxQrck2XpbE2ZWsreRu7_GK19C0pNcRDmrJRZuMg1T_qlyDQxpUzXkePqanG_nJ4sdNSd5i9ctQualA3570PqvZKb2dWdPjgKthvtKbkyoXm0m68X0HKtt4stY0u5uBEenXCA4uQKD50P368j-S";
    @GET("v1/tracks/3n3Ppam7vgaVa1iaRUc9Lp")
    @Headers("Authorization: Bearer "+token)
    Call<Track> getATrack();

    @GET("v1/tracks")
    @Headers("Authorization: Bearer "+token)
    Call<Tracks> getTracks(@Query("ids") String tracksid);
}
