package com.example.spotify.BackClasses.BackInterfaces;

import com.example.spotify.BackClasses.Backclasses.backcategory.Category;
import com.example.spotify.BackClasses.Backclasses.backnewrelease.Newreleases;
import com.example.spotify.BackClasses.Backclasses.backsearch.Search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface backinterfaces
{
    String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZTgwYzZhZjE0Yzg1NjZkNmNkOWI0MDAiLCJwcm9kdWN0IjoiZnJlZSIsInVzZXJUeXBlIjoiQXJ0aXN0IiwiaWF0IjoxNTg2MDI2NjAyLCJleHAiOjQ3MzI1MTMwMDJ9.ztEjNCgbkyJ2-9WB6ojwLgDfhWsZ-ZGJVFUB8dYMz8s";

    //get list of new backhome
    @GET("browse/new-releases?country=SE&limit=10&offset=0")
    //@Headers("Authorization: Bearer "+token)
    public Call<Newreleases> getNewRelease();

    @GET("/search")
    //@Headers("x-auth-token"+token)
    public Call<Search> getSearch(@Query("name") String name, @Query("type") String type, @Header("x-auth-token") String token);

    @GET("/browse/categories")
    //@Headers("Authorization: Bearer BQCy8FwJKOCxKsmpvloyQWr2n3Wik9ZQckF4jJLC2z47jA5uycHOn0caqv0MaGaVWdb7S-eaIYD3opU812dFvnS_DlwB5sG2-PbEANHN5FAbO7fEZhq7yvxI8ZYoYi83BX5BMDmAboftrl2VzMWXKv40APnKRoexFVSDkfDBQKQUeSadyBc8UPn408pQvJ23rnmvFPJPIXzcsUN-3gcjF5HhiygOL0YthZDC9spwqo83RRfPQV4Omj8EJF7vvgPLwH-rnWblMhw88kBS9lzsGdW2jjNUfdpfmw")
    public Call<Category> getCategories(@Header("x-auth-token") String token);

}
