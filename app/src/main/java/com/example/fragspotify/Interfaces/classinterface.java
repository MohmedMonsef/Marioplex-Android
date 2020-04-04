package com.example.fragspotify.Interfaces;

import com.example.fragspotify.SpotifyClasses.CategoryModel.Category;
import com.example.fragspotify.SpotifyClasses.NewRelease;
import com.example.fragspotify.SpotifyClasses.SearchClasses.search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface classinterface
{
    String token="BQA22sJho0gzPYltgT9oSOcPjA27CKuEeEXiPgbLLs-EEHKu8_KDlDQskf_b4Y8VfRjR1yJpIWQBM-er9LtrxJgL7nxzisYayYT5u2R5zogk5MUoZ3KytxqAaleY2n8DHp96QX6oFrRqjNZvAt-rUTdL5cEEeR57ueFcqsebpmPGieHmIlknN1ptj019wWX24E6fmdpjE6wfB7MinodH2Owy6L2owZokQlv5YgxZmYe2SAmlyfDjtSzOdiXBZZVr8LLYgRhqTcMzJdQrZj16Nim8hciApPEERQ";

    //get list of new releases
    @GET("v1/browse/new-releases?country=SE&limit=10&offset=0")
    @Headers("Authorization: Bearer "+token)
    public Call<NewRelease> getNewRelease();


    @GET("v1/search")
    @Headers("Authorization: Bearer "+token)
    public Call<search> getSearch(@Query("q") String name,
                                  @Query("type") String type,
                                  @Query("market") String market,
                                  @Query("limit") int limit,
                                  @Query("offset") int offset);

    @GET("v1/browse/categories?country=SE&locale=sv_SE&limit=10&offset=5")
    @Headers("Authorization: Bearer "+token)
    public Call<Category> getCategories();


}
