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
    String token="BQDI2DH2neciO1rfbmdM6gKOp8jHi8oS2W_DJHjl2sZpFaGLUDfFVMz6gBwroTPJlbOlG0eW9vXEqKmlGIZiik0ktvMWMB38sGthL5DYu5V_BTMauwxGQdBiq_ryLgj5kkn2hoR74IL5BvJyf8trLrAlSjx8BveNxe5B8quZzvNO0B85vbRn7B26bpa9YU6egNJIl31aCmrWOT96gKwGsV_vVaeFnp4X6a74_e75pdKqZ0jcy4z32F5M5EKT6qpjDJMrwHaT-D5GCfFy_08jalu8uPQvIg_PnQ";

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
