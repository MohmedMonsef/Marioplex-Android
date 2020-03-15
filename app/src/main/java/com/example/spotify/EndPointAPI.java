package com.example.spotify;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface EndPointAPI {
    String token = "BQCmzRzVpBDmjHXmia8pzdtZN3IaXAcD2ZttqKXpV8-UAJWGKlXkjC5OI8xLPJwkjNra_dzKoI9sqnMtcYNhVX6rd0K1pNHDQccIn2bufVwXIs-NnpA4LHMUuVXqdZNUZDkF7bztPVLx8ql-2Gu_MBFuhIfKHHl32KKtC6ecivum27hm1oxCwVXu0vG5_rzW";
    @GET("v1/tracks/3n3Ppam7vgaVa1iaRUc9Lp")
    @Headers("Authorization: Bearer "+token)
    Call<Track> getATrack();

    @GET("v1/tracks")
    @Headers("Authorization: Bearer "+token)
    Call<Tracks> getTracks(@Query("ids") String tracksid);
}
