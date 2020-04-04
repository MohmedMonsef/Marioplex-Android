package com.example.fragspotify.BackClasses.BackInterfaces;

import com.example.fragspotify.BackClasses.Backclasses.backcategory.Category;
import com.example.fragspotify.BackClasses.Backclasses.backnewrelease.Newreleases;
import com.example.fragspotify.BackClasses.Backclasses.backsearch.Search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface backinterfaces
{
    String token="BQB-bDExnvUTX8218R5eEBIXI8eKv91gY6znWEyLOLfyY-JLkRUcg20y4IlGwktPdJ3dmmPKvjjqRwYba1KVnQpnpXYkt6AhSBBcU_1UrG2cqNY1KcCfzjcCB1EFGfr-emKBX4e54t15HOLpjQ6TMufJS5zsu_XacgjBZ_QFsjD8RUlE8YW5FZNA7QKqgP9tXYbhnyPEw8eALO7RZViVJKrOphGIc9ysNvwWv4yzYNvhWuRtF1SfMpicUqa28g7auDv52KfD0GGwBA1O8w7ydclFI3Ok8KA7lw";

    //get list of new backhome
    @GET("browse/new-releases?country=SE&limit=10&offset=0")
    //@Headers("Authorization: Bearer "+token)
    public Call<Newreleases> getNewRelease();


    @GET("v1/search")
    @Headers("Authorization: Bearer "+token)
    public Call<Search> getSearch(@Query("name") String name, @Query("type") String type, @Query("market") String market, @Query("limit") int limit, @Query("offset") int offset);

    @GET("v1/browse/categories?country=SE&locale=sv_SE&limit=10&offset=5")
    @Headers("Authorization: Bearer BQCy8FwJKOCxKsmpvloyQWr2n3Wik9ZQckF4jJLC2z47jA5uycHOn0caqv0MaGaVWdb7S-eaIYD3opU812dFvnS_DlwB5sG2-PbEANHN5FAbO7fEZhq7yvxI8ZYoYi83BX5BMDmAboftrl2VzMWXKv40APnKRoexFVSDkfDBQKQUeSadyBc8UPn408pQvJ23rnmvFPJPIXzcsUN-3gcjF5HhiygOL0YthZDC9spwqo83RRfPQV4Omj8EJF7vvgPLwH-rnWblMhw88kBS9lzsGdW2jjNUfdpfmw")
    public Call<Category> getCategories();

}
