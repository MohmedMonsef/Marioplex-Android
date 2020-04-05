package com.example.spotify.Interfaces;

import com.example.spotify.SpotifyClasses.Track;
import com.example.spotify.SpotifyClasses.Tracks;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface EndPointAPI {
    String token = "BQA22sJho0gzPYltgT9oSOcPjA27CKuEeEXiPgbLLs-EEHKu8_KDlDQskf_b4Y8VfRjR1yJpIWQBM-er9LtrxJgL7nxzisYayYT5u2R5zogk5MUoZ3KytxqAaleY2n8DHp96QX6oFrRqjNZvAt-rUTdL5cEEeR57ueFcqsebpmPGieHmIlknN1ptj019wWX24E6fmdpjE6wfB7MinodH2Owy6L2owZokQlv5YgxZmYe2SAmlyfDjtSzOdiXBZZVr8LLYgRhqTcMzJdQrZj16Nim8hciApPEERQ";

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
