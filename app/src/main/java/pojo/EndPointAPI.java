package pojo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface EndPointAPI {
    String token = "BQARKkLp5zkvEP1tuAHC2N-NIgw5CVFk1NQtX-FqsFX1RWzNowZWp_Ju5v1cAbEFhrIryoldcgwzmGQ6YwG6IdJr7xGn6bfvCpwTP8qOI--8wnNvYBYr32-IKaMo91CRkbZw-XPQ0_pQ6qHvc2ZPzN5RS3gwXgDluV75JWqkCgJ0cnrcZ6FVFqVYgjVduc27";
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
