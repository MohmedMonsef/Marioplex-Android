package com.example.spotify;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface EndPointAPI {
    String token = "BQDdLJXbjuLtey2J6jnXw4DoDAV0uiq36Wre_c5b2nFAeL4B9Mspbd7W2U-8bOFlAWamQLSiUiq0yZp9AFGxKXfnk08_CQp-xO-MMZ5Po9BamZVNI3Qb65OAdJpiTrLjRG94ARwSTnefrI60rFYONK9bSPZRPJhWWxaO3Hbl3w0oEDetXRKJOfEK67iyP9pi";
    @GET("v1/tracks/3n3Ppam7vgaVa1iaRUc9Lp")
    @Headers("Authorization: Bearer "+token)
    Call<Track> getATrack();

    @GET("v1/tracks")
    @Headers("Authorization: Bearer "+token)
    Call<Tracks> getTracks(@Query("ids") String tracksid);
}
