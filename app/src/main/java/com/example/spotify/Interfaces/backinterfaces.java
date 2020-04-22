package com.example.spotify.Interfaces;

import com.example.spotify.BackClasses.Backclasses.backcategory.Category;
import com.example.spotify.BackClasses.Backclasses.backcategoryplaylist.CategoryPlaylist;
import com.example.spotify.BackClasses.Backclasses.backnewrelease.Newreleases;
import com.example.spotify.BackClasses.Backclasses.backpopularalbum.PopularAlbum;
import com.example.spotify.BackClasses.Backclasses.backpopularartist.PopularArtist;
import com.example.spotify.BackClasses.Backclasses.backpopularplaylist.PopularPlaylist;
import com.example.spotify.BackClasses.Backclasses.backsearch.Search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface backinterfaces
{
    String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZTgwYzZhZjE0Yzg1NjZkNmNkOWI0MDAiLCJwcm9kdWN0IjoiZnJlZSIsInVzZXJUeXBlIjoiQXJ0aXN0IiwiaWF0IjoxNTg2MDI2NjAyLCJleHAiOjQ3MzI1MTMwMDJ9.ztEjNCgbkyJ2-9WB6ojwLgDfhWsZ-ZGJVFUB8dYMz8s";

    //get list of new backhome
    @GET("api/browse/new-releases?country=SE&limit=10&offset=0")

    public Call<Newreleases> getNewRelease();

    @GET("api/browse/popular-albums")
    public Call<PopularAlbum> getPopularAlbum();


    @GET("api/browse/popular-playlists")
    public Call<PopularPlaylist> getPopularPlaylist();

    @GET("api/browse/popular-artists")
    public Call<PopularArtist> getPopularArtist();


    @GET("api/search")
    public Call<Search> getSearch(@Query("name") String name, @Query("type") String type, @Header("x-auth-token") String token);

    @GET("api/browse/categories")
    public Call<Category> getCategories(@Header("x-auth-token") String token);

    @GET("api/browse/categories/{category_id}/playlists")
    public Call<CategoryPlaylist> getCategoryPlaylist(@Path("category_id") String category_id,@Header("x-auth-token") String token);


}
