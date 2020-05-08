package com.example.spotify.Interfaces;

import com.example.spotify.BackClasses.Backclasses.albumInform.AlbumObject;
import com.example.spotify.BackClasses.Backclasses.albumTrack.AlbumTracks;
import com.example.spotify.BackClasses.Backclasses.backcategory.Category;
import com.example.spotify.BackClasses.Backclasses.backcategoryplaylist.CategoryPlaylist;
import com.example.spotify.BackClasses.Backclasses.backnewrelease.Newreleases;
import com.example.spotify.BackClasses.Backclasses.backpopularalbum.PopularAlbum;
import com.example.spotify.BackClasses.Backclasses.backpopularartist.PopularArtist;
import com.example.spotify.BackClasses.Backclasses.backpopularplaylist.PopularPlaylist;
import com.example.spotify.BackClasses.Backclasses.backsearch.Search;
import com.example.spotify.login.apiClasses.FacebookLoginData;
import com.example.spotify.login.apiClasses.LoginCredentials;
import com.example.spotify.login.apiClasses.LoginResponse;
import com.example.spotify.login.apiClasses.SignUpData;
import com.example.spotify.login.apiClasses.updateProfile;
import com.example.spotify.login.apiClasses.userProfile;
import com.example.spotify.pojo.BasicPlaylist;
import com.example.spotify.pojo.PlaylistTracks;
import com.example.spotify.pojo.addTrackToPlaylistBody;
import com.example.spotify.pojo.createPlaylistBody;
import com.example.spotify.pojo.currentTrack;
import com.example.spotify.pojo.playlist;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EndPointAPI {
    String token = "BQDbws7mVPJV0YSZRUNJz1XtQUZTGExfM9qptNtHuQuUti7qx0Lci75BZ6hP2cK0QFpBRoRcFNgU6LZwl6l2cxzSyLk3V7ZumuoSLxVzav1abEDJQYEh6Qkc0t1S02C2hezOWhMITWGUkPxnIiIcfm9RXtMverQZJM2bPWk-FDzwv12vLae0BpB9xc6YDS21iH3SGQf6TxTAkxiGk51vdFBHIezKklCVCimV7B3xQaOeM2-ocTB1pWSA_LtBXsV2LGsI5NOc5ZehD9BoD3lv9jBBeJVwuGbUUA";

    //////////////////////////////login requests//////////////////////////////////////////
    @POST("api/Login")
    Call<LoginResponse> login(@Body LoginCredentials loginCredentials);

    @POST("api/sign_up")
    Call<LoginResponse> signUp(@Body SignUpData signUpData);

    @GET("api/me")
    Call<ArrayList<userProfile>> profile(@Header("x-auth-token") String token);

    @PUT("api/me/update")
    Call<ResponseBody> updateProfile(@Header("x-auth-token") String token,@Body updateProfile data);

    @GET("api/me/playlists")
    Call<playlist[]> myPlaylists(@Header("x-auth-token") String token);

    @POST("api/auth/facebookAndroid")
    Call<LoginResponse> facebookLogin(@Body FacebookLoginData facebookLoginData);
    ////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////home requests/////////////////////////////////////////



    ////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////our api requests//////////////////////////////////
    @POST("api/me/player/next-playing")
    Call<currentTrack> getNext(@Header("x-auth-token")String token1);

    @POST("api/me/player/prev-playing")
    Call<currentTrack> getPrevious(@Header("x-auth-token")String token1);

    @GET("api/me/player/currently-playing")
    Call<currentTrack> getCurrentlyPlaying(@Header("x-auth-token")String token1);

    @GET("api/me/playlists")
    Call<List<BasicPlaylist>> getCurrentUserPlaylists(@Header("x-auth-token")String token1);

    @GET("api/tracks/related/full-track/{track_id}")
    Call<List<currentTrack>> getSongTracks(@Path("track_id") String TrackID , @Header("x-auth-token")String token1);

    @POST("api/playlists/{playlist_id}/tracks")
    Call<Object> AddTrackToAPlaylist(@Path("playlist_id") String playlistID
                                      ,@Body addTrackToPlaylistBody t
                                      ,@Header("x-auth-token")String token1);

    @POST("api/users/playlists")
    Call<playlist> CreatePlaylist(@Body createPlaylistBody c
                                 ,@Header("x-auth-token")String token1); //check if fiels of Query


    @PUT("api/me/player/shuffle")
    Call<Void> toggleShuffle(@Query("state") Boolean state , @Header("x-auth-token")String token1);

    @POST("api/createQueue/{playlist_id}/{trackid}")
    Call<Void> CreateQueue(@Path("playlist_id") String playlist_id ,
                             @Path("trackid") String track_id ,
                             @Query("isPlaylist") Boolean isPlaylist ,
                             @Header("x-auth-token")String token1);


    @GET("api/playlists/{playlist_id}/tracks")
    Call<List<PlaylistTracks>> getPlaylistTracks(@Path("playlist_id") String playlist_id,
                                                @Header("x-auth-token")String token1);

    @PUT("api/me/like/{track_id}")
    Call<Void> LikeTrack(@Path("track_id") String track_id ,
                         @Header("x-auth-token")String token1);

    @DELETE("api/me/unlike/{track_id}")
    Call<Void> UNLikeTrack(@Path("track_id") String track_id ,
                           @Header("x-auth-token")String token1);


    @DELETE("api/playlists/{playlist_id}/followers")
    Call<Void> UNLikePlaylist(@Path("playlist_id") String playlistID ,
                              @Header("x-auth-token")String token1);

    @PUT("api/playlists/{playlist_id}/followers")
    Call<Void> LikePlaylist(@Path("playlist_id") String playlistID ,
                            @Header("x-auth-token")String token1);



    @GET("api/browse/categories?country=SE&locale=sv_SE&limit=10&offset=5")
    @Headers("Authorization: Bearer "+token)
    public Call<Category> getCategories();
//////*******************************Home Requests*************************/////////
    @GET("api/browse/new-releases?country=SE&limit=10&offset=0")
    public Call<Newreleases> getNewRelease();

    @GET("api/browse/popular-albums")
    public Call<PopularAlbum> getPopularAlbum();


    @GET("api/browse/popular-playlists")
    public Call<PopularPlaylist> getPopularPlaylist();

    @GET("api/browse/popular-artists")
    public Call<PopularArtist> getPopularArtist();

//////*******************************Search Requests*************************/////////

    @GET("api/search")
    public Call<Search> getSearch(@Query("name") String name, @Query("type") String type, @Header("x-auth-token") String token);
//////*******************************Category Requests*************************/////////

    @GET("api/browse/categories")
    public Call<Category> getCategories(@Header("x-auth-token") String token);

    @GET("api/browse/categories/:category_id/playlists")
    public Call<CategoryPlaylist> getCategoryPlaylist(@Path("category_id") String category_id, @Header("x-auth-token") String token);
//////*******************************Albums Requests*************************/////////
    @GET("api/albums/{album_id}")
    public Call<AlbumObject> getAlbumObject(@Path("album_id") String album_id, @Header("x-auth-token") String token);

    @GET("api/albums/{id}/tracks")
    public Call<AlbumTracks> getAlbumTracks(@Path("id") String album_id, @Header("x-auth-token") String token);

    @PUT("api/me/Albums")
    public Call<Void> LIKE_ALBUM(@Body String ids,@Header("x-auth-token") String token);
    //api/me/Albums
    //@DELETE("api/me/albums")
  //  public Call<Void> UN_LIKE_ALBUM(@Body String id,@Header("x-auth-token") String token);

}
